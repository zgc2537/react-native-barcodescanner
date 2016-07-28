# react-native-barcodescanner

### Introduction
This component is modified by cutting some parts of https://github.com/zxing/zxing, and puting it into the https://github.com/ideacreation/react-native-barcodescanner
When the original scanner scan barcode in portrait mode, it have problem in android.
This component corrects the problem and adds some optional properties.
And this component is just for portrait mode.
The IOS part is not change.

Warning
The jar file which is in the android.libs can not be removed. It's code is not the same to the original code.

A barcode scanner component for react native android. The library uses https://github.com/zxing/zxing to decode the barcodes. For iOS you can use https://github.com/lwansbrough/react-native-camera.

### Version 3.0.0

With version 3.0.0 react-native-barcodescanner doesn't depend anymore on https://github.com/dm77/barcodescanner, but directly on https://github.com/zxing/zxing. The code is still heavily influenced by it but simplified for our use case. The viewfinder is rendered in javascript and not anymore directly in java.

### React Native dependencies

- Version 0.1.4 for React Native <=0.18
- Version 1.x.x for React Native >=0.19
- Version 3.x.x for React Native >=0.25

### Installation

```bash
npm i --save react-native-barcodescanner
```

### Add it to your android project

* In `android/settings.gradle`

  ```gradle
  ...
  include ':react-native-barcodescanner', ':app'
  project(':react-native-barcodescanner').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-barcodescanner/android')
  ```

* In `android/app/build.gradle`

  ```gradle
  ...
  dependencies {
      ...
      compile project(':react-native-barcodescanner')
  }
  ```

* register module (in MainActivity.java)

  Add the following **import** statement:
  ```Java
  import com.eguma.barcodescanner.BarcodeScannerPackage;
  ```

  ...and then add `BarcodeScannerPackage` to exported package list *(MainActivity.java#getPackages)*:

  ```Java
  public class MainActivity extends ReactActivity {
      // (...)

      @Override
      protected List<ReactPackage> getPackages() {
        return Arrays.<ReactPackage>asList(
          new MainReactPackage(),
          new BarcodeScannerPackage()
        );
      }
  }
  ```

## Example
```javascript
import React, {
  Component
} from 'react';
import { ToastAndroid, AppRegistry } from 'react-native';
import BarcodeScanner from 'react-native-barcodescanner';

class BarcodeScannerExampleApp extends Component {
  constructor(props) {
    super(props);

    this.state = {
        playBeep: "on",
        vibrate: "on",
        bulkScan : "on",
    };
  }

  barcodeReceived(e) {
    console.log('Barcode: ' + e.data);
    console.log('Type: ' + e.type);
    ToastAndroid.show(('Barcode: ' + e.data + ', Type:' + e.type), ToastAndroid.SHORT);
  }

  render() {
    return (
      <BarcodeScanner
        onBarCodeRead={this.barcodeReceived}
        style={{ flex: 1 }}
        playBeep={this.state.playBeep}
        vibrate={this.state.vibrate}
        bulkScan={this.state.bulkScan}
      />
    );
  }
}

AppRegistry.registerComponent('BarcodeScannerExampleApp', () => BarcodeScannerExampleApp);
```

## Properties

#### `onBarCodeRead`

Will call the specified method when a barcode is detected in the camera's view.
Event contains `data` (barcode value) and `type` (barcode type).
The following barcode types can be recognised:

```java
BarcodeFormat.UPC_A
BarcodeFormat.UPC_E
BarcodeFormat.EAN_13
BarcodeFormat.EAN_8
BarcodeFormat.RSS_14
BarcodeFormat.CODE_39
BarcodeFormat.CODE_93
BarcodeFormat.CODE_128
BarcodeFormat.ITF
BarcodeFormat.CODABAR
BarcodeFormat.QR_CODE
BarcodeFormat.DATA_MATRIX
BarcodeFormat.PDF_417
```

#### `playBeep`

Values:
`on`,
`off` (default)

Use the `playBeep` property to specify whether play beep or not.

#### `vibrate`

Values:
`on`,
`off` (default)

Use the `vibrate` property to specify whether vibrate or not.

#### `torchOn`

Values:
`on`,
`off` (default)

Use the `torchOn` property to specify whether use torch or not.

#### `autoFocus`

Values:
`on`, (default)
`off`

Use the `autoFocus` property to specify whether camera focus automatically or not.

#### `invertScan`

Values:
`on`,
`off` (default)

Use the `invertScan` property to specify whether invert color of scan result or not.

#### `bulkScan`

Values:
`on`,
`off` (default)

Use the `bulkScan` property to specify whether scan in batches or separately.
When scan separately, call method called restartPreviewAfterDelay of BarcodeScannerView to activate the next scan

#### `bulkScanDelay`

Values:
`1000L` (default)

Use the `bulkScanDelay` property to specify the delay of interval of scanning in batches.

#### `decode1DProduct`

Values:
`on`, (default)
`off`

Use the `decode1DProduct` property to specify whether support id-industrial decoding or not.

#### `decode1DIndustrial`

Values:
`on`, (default)
`off`

Use the `decode1DIndustrial` property to specify whether support id-industrial decoding or not.

#### `decodeQr`

Values:
`on`, (default)
`off`

Use the `decodeQr` property to specify the whether support qr decoding or not.

#### `decodeDataMatrix`

Values:
`on`, (default)
`off`

Use the `decodeDataMatrix` property to specify whether support datamatrix decoding or not.

#### `decodeAztec`

Values:
`on`,
`off` (default)

Use the `decodeAztec` property to specify whether support aztec decoding or not.

#### `decodePdf417`

Values:
`on`,
`off` (default)

Use the `decodePdf417` property to specify whether support pdf417 decoding or not.

### Viewfinder

The viewfinder is a child react component of the barcodescanner component. if you don't need the viewfinder (e.g. because you want your own child components to render) or you want your own viewfinder you can disable it with `showViewFinder={false}`.

The following properties can be used to style the viewfinder:

`viewFinderBackgroundColor`,
`viewFinderBorderColor`,
`viewFinderBorderWidth`,
`viewFinderBorderLength`,
`viewFinderShowLoadingIndicator`,

All color values are strings (e.g. '#eee' or 'rgba(0, 0, 0, 0.3)', default: 'white'). `viewFinderHeight` (default: 200), `viewFinderWidth` (default: 200), `viewFinderBorderWidth` (default: 2)and `viewFinderBorderLength` (default: 30) are numbers, `viewFinderShowLoadingIndicator` is either `true` or `false` (default) and shows a ActivityIndicatorIOS or a ProgressBarAndroid centered in the viewfinder depending on the platform.
