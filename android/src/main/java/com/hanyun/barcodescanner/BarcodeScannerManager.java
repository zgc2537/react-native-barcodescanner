package com.hanyun.barcodescanner;

import android.view.View;
import android.util.Log;

import javax.annotation.Nullable;

import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

public class BarcodeScannerManager extends ViewGroupManager<BarcodeScannerView> implements LifecycleEventListener {
    private static final String REACT_CLASS = "RNBarcodeScannerView";

    private static final String DEFAULT_TORCH_MODE = "off";
    private static final String DEFAULT_CAMERA_TYPE = "back";

    private BarcodeScannerView mScannerView;
    
    private CameraParameters mCameraParams = null;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactProp(name = "playBeep")
    public void setPlayBeep(BarcodeScannerView view, @Nullable String playBeep) {
      if (playBeep != null) {
          mCameraParams.setPlayBeep(playBeep.equals("on"));
      }
    }

    @ReactProp(name = "vibrate")
    public void setVibrate(BarcodeScannerView view, @Nullable String vibrate) {
      if (vibrate != null) {
          mCameraParams.setVibrate(vibrate.equals("on"));
      }
    }

    @ReactProp(name = "torchOn")
    public void setTorchOn(BarcodeScannerView view, @Nullable String torchOn) {
      if (torchOn != null) {
          mCameraParams.setTorchOn(torchOn.equals("on"));
      }
    }

    @ReactProp(name = "autoFocus")
    public void setAutoFocus(BarcodeScannerView view, @Nullable String autoFocus) {
      if (autoFocus != null) {
          mCameraParams.setAutoFocus(autoFocus.equals("on"));
      }
    }

    @ReactProp(name = "invertScan")
    public void setInvertScan(BarcodeScannerView view, @Nullable String invertScan) {
      if (invertScan != null) {
          mCameraParams.setInvertScan(invertScan.equals("on"));
      }
    }

    @ReactProp(name = "bulkScan")
    public void setBulkScan(BarcodeScannerView view, @Nullable String bulkScan) {
      if (bulkScan != null) {
          mCameraParams.setBulkScan(bulkScan.equals("on"));
      }
    }

    @ReactProp(name = "bulkScanDelay")
    public void setBulkScanDelay(BarcodeScannerView view, @Nullable String bulkScanDelay) {
      if (bulkScanDelay != null) {
          mCameraParams.setBulkScanDelay(Long.parseLong(bulkScanDelay));
      }
    }

    @ReactProp(name = "decode1DProduct")
    public void setDecode1DProduct(BarcodeScannerView view, @Nullable String decode1DProduct) {
      if (decode1DProduct != null) {
          mCameraParams.setDecode1DProduct(decode1DProduct.equals("on"));
      }
    }

    @ReactProp(name = "decode1DIndustrial")
    public void setDecode1DIndustrial(BarcodeScannerView view, @Nullable String decode1DIndustrial) {
      if (decode1DIndustrial != null) {
          mCameraParams.setDecode1DIndustrial(decode1DIndustrial.equals("on"));
      }
    }

    @ReactProp(name = "decodeQr")
    public void setDecodeQr(BarcodeScannerView view, @Nullable String decodeQr) {
      if (decodeQr != null) {
          mCameraParams.setDecodeQr(decodeQr.equals("on"));
      }
    }

    @ReactProp(name = "decodeDataMatrix")
    public void setDecodeDataMatrix(BarcodeScannerView view, @Nullable String decodeDataMatrix) {
        if (decodeDataMatrix != null) {
            mCameraParams.setDecodeDataMatrix(decodeDataMatrix.equals("on"));
        }
    }

    @ReactProp(name = "decodeAztec")
    public void setDecodeAztec(BarcodeScannerView view, @Nullable String decodeAztec) {
      if (decodeAztec != null) {
          mCameraParams.setDecodeAztec(decodeAztec.equals("on"));
      }
    }

    @ReactProp(name = "decodePdf417")
    public void setDecodePdf417(BarcodeScannerView view, @Nullable String decodePdf417) {
      if (decodePdf417 != null) {
          mCameraParams.setDecodePdf417(decodePdf417.equals("on"));
      }
    }

    @Override
    public BarcodeScannerView createViewInstance(ThemedReactContext context) {
        context.addLifecycleEventListener(this);
        mScannerView = new BarcodeScannerView(context);
        mCameraParams = new CameraParameters(context);
        return mScannerView;
    }

    @Override
    public void onHostResume() {
        Log.i(REACT_CLASS, "onHostResume");
        mScannerView.onResume();
    }

    @Override
    public void onHostPause() {
        Log.i(REACT_CLASS, "onHostPause");
        mScannerView.onPause();
    }

    @Override
    public void onHostDestroy() {

    }

    @Override
    public void addView(BarcodeScannerView parent, View child, int index) {
        parent.addView(child, index + 1);   // index 0 for camera preview reserved
    }
}
