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

AppRegistry.registerComponent('RNBarcodeScanner', () => BarcodeScannerExampleApp);