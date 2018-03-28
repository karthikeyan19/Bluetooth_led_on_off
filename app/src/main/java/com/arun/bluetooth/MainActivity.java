package com.arun.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final String address="B8:27:EB:0B:0E:01";
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    BluetoothSocket socket=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button switchButton=(Button)findViewById(R.id.button1);
          Button connectButton= (Button) findViewById(R.id.connect);
          connectButton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  connect(address);

              }
          });
//        BluetoothDevice bluetoothDevice=mBluetoothAdapter.getBondedDevices().iterator().next();
      //  Toast.makeText(MainActivity.this,bluetoothDevice.getName(),Toast.LENGTH_LONG).show();
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"HEllo",Toast.LENGTH_SHORT).show();
                if(switchButton.getText().equals("ON")){
                    switchButton.setText("OFF");
                    switchButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    sendDataToPairedDevice("1");
                }else{
                    switchButton.setText("ON");
                    switchButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    sendDataToPairedDevice("0");
                }
            }
        });

    }

    private void connect(String address){
        try {
            Log.d("hh",address);
            mBluetoothAdapter.cancelDiscovery();
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
            Toast.makeText(this,"Bl:"+device.getName(),Toast.LENGTH_SHORT).show();
            socket =device.createRfcommSocketToServiceRecord(UUID.fromString("58369c20-ecf6-11e3-ba36-82687f4fc15c"));
           // socket=device.createInsecureRfcommSocketToServiceRecord(UUID.fromString("58369c20-ecf6-11e3-ba36-82687f4fc15c"));
//            Method m = null;
//            try {
//                m = device.getClass().getMethod("createRfcommSocket",
//                        new Class[] { int.class });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                socket = (BluetoothSocket) m.invoke(device, 2);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            socket.connect();
            Toast.makeText(MainActivity.this,"Connected",Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendDataToPairedDevice(String message)  {
        byte[] toSend = message.getBytes();

        try {
            OutputStream mmOutStream = socket.getOutputStream();
            mmOutStream.write(toSend);
        } catch (Exception e) {

            Log.d("TAG", "Exception during write", e);
        }
    }
}
