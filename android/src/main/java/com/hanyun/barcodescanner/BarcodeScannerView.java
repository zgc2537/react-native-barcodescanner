/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hanyun.barcodescanner;

import com.google.zxing.Result;
import com.hanyun.barcodescanner.camera.CameraManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import java.io.IOException;

/**
 * This activity opens the camera and does the actual scanning on a background thread. It draws a
 * viewfinder to help the user place the barcode correctly, shows feedback as the image processing
 * is happening, and then overlays the results when a scan is successful.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class BarcodeScannerView extends FrameLayout implements SurfaceHolder.Callback {

  private static final String TAG = BarcodeScannerView.class.getSimpleName();

  private CameraManager cameraManager;
  private CaptureActivityHandler handler;
  private Result savedResultToShow;
  private boolean hasSurface;
  private BeepManager beepManager;
  private AmbientLightManager ambientLightManager;
  private Context mContext;
  private AudioManager mAudioMana;
  private SurfaceView mSurfaceView = null;
  private SurfaceHolder mSurfaceHolder = null;

  public Handler getHandler() {
    return handler;
  }

  CameraManager getCameraManager() {
    return cameraManager;
  }

  public BarcodeScannerView(Context context) {
    super(context);
    mContext = context;
    hasSurface = false;
    beepManager = new BeepManager(context);
    ambientLightManager = new AmbientLightManager(mContext);
    mSurfaceView = new SurfaceView(mContext);
    this.addView(mSurfaceView);
    mAudioMana = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
    cameraManager = new CameraManager(mContext);
    onResume();
  }


  protected void onResume() {
    Log.i(TAG, "BarcodeScannerView onResume");

    // CameraManager must be initialized here, not in onCreate(). This is necessary because we don't
    // want to open the camera driver and measure the screen size if we're going to show the help on
    // first launch. That led to bugs where the scanning rectangle was the wrong size and partially
    // off screen.

    handler = null;

//    if (prefs.getBoolean(PreferencesActivity.KEY_DISABLE_AUTO_ORIENTATION, true)) {
//      setRequestedOrientation(getCurrentOrientation());
//    } else {
//      setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
//    }
    //mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);


    beepManager.updatePrefs();
    ambientLightManager.start(cameraManager);

    mSurfaceHolder = mSurfaceView.getHolder();
    if (hasSurface) {
      // The activity was paused but not stopped, so the surface still exists. Therefore
      // surfaceCreated() won't be called, so init the camera here.
      initCamera(mSurfaceHolder);
    } else {
      Log.i(TAG, "mSurfaceHolder.addCallback(this)");
      // Install the callback and wait for surfaceCreated() to init the camera.
      mSurfaceHolder.addCallback(this);
    }
  }

//  private int getCurrentOrientation() {
//    int rotation = getWindowManager().getDefaultDisplay().getRotation();
//    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//      switch (rotation) {
//        case Surface.ROTATION_0:
//        case Surface.ROTATION_90:
//          return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
//        default:
//          return ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
//      }
//    } else {
//      switch (rotation) {
//        case Surface.ROTATION_0:
//        case Surface.ROTATION_270:
//          return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
//        default:
//          return ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
//      }
//    }
//  }

  protected void playBeepAndVibrate() {
      beepManager.playBeepSoundAndVibrate();
  }

  protected void onPause() {
    if (handler != null) {
      handler.quitSynchronously();
      handler = null;
    }
    ambientLightManager.stop();
    beepManager.close();
    cameraManager.closeDriver();
    //historyManager = null; // Keep for onActivityResult
    if (!hasSurface) {
      SurfaceView surfaceView = new SurfaceView(mContext);
      SurfaceHolder surfaceHolder = surfaceView.getHolder();
      surfaceHolder.removeCallback(this);
    }
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    switch (keyCode) {
      case KeyEvent.KEYCODE_FOCUS:
      case KeyEvent.KEYCODE_CAMERA:
        // Handle these events so they don't launch the Camera app
        return true;
      // Use volume up/down to turn on light
      case KeyEvent.KEYCODE_VOLUME_DOWN:
        mAudioMana.adjustSuggestedStreamVolume(
                AudioManager.ADJUST_LOWER,
                AudioManager.STREAM_MUSIC,
                AudioManager.FLAG_SHOW_UI | AudioManager.FLAG_VIBRATE);
        return true;
      case KeyEvent.KEYCODE_VOLUME_UP:
        mAudioMana.adjustSuggestedStreamVolume(
                AudioManager.ADJUST_RAISE,
                AudioManager.STREAM_MUSIC,
                AudioManager.FLAG_SHOW_UI | AudioManager.FLAG_VIBRATE);
        return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
    // Bitmap isn't used yet -- will be used soon
    if (handler == null) {
      savedResultToShow = result;
    } else {
      if (result != null) {
        savedResultToShow = result;
      }
      if (savedResultToShow != null) {
        Message message = Message.obtain(handler, R.id.decode_succeeded, savedResultToShow);
        handler.sendMessage(message);
      }
      savedResultToShow = null;
    }
  }

  @Override
  public void surfaceCreated(SurfaceHolder holder) {
    Log.i(TAG, "surfaceCreated in");
    if (holder == null) {
      Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
    }
    if (!hasSurface) {
      hasSurface = true;
      initCamera(holder);
    }
  }

  @Override
  public void surfaceDestroyed(SurfaceHolder holder) {
    hasSurface = false;
  }

  @Override
  public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

  }

  private void initCamera(SurfaceHolder surfaceHolder) {
    if (surfaceHolder == null) {
      throw new IllegalStateException("No SurfaceHolder provided");
    }
    if (cameraManager.isOpen()) {
      Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
      return;
    }
    try {
      Log.i(TAG, "START OPEN CAMERA");
      cameraManager.openDriver(surfaceHolder);
      // Creating the handler starts the preview, which can also throw a RuntimeException.
      if (handler == null) {
        handler = new CaptureActivityHandler(getId(), this, cameraManager);
      }
      decodeOrStoreSavedBitmap(null, null);
    } catch (IOException ioe) {
      Log.w(TAG, ioe);
    } catch (RuntimeException e) {
      // Barcode Scanner has seen crashes in the wild of this variety:
      // java.?lang.?RuntimeException: Fail to connect to camera service
      Log.w(TAG, "Unexpected error initializing camera", e);
    }
  }
  
  public void restartPreviewAfterDelay() {
    if (handler != null) {
      handler.restartPreviewAfterDelay();
    }
  }
}
