package zkhlnk.v.lightsmqttwithpahotestproject.activities;

import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import zkhlnk.v.lightsmqttwithpahotestproject.R;
import zkhlnk.v.lightsmqttwithpahotestproject.adapters.ImageAdapter;
import zkhlnk.v.lightsmqttwithpahotestproject.mqtt.Client;
import zkhlnk.v.lightsmqttwithpahotestproject.utils.TypesConverter;


public class AutomaticSendingActivity extends ActionBarActivity {

    private boolean[] dataArray;
    private String dataArrayKeyString = "DataArray";

    private ImageAdapter adapter;

    private Client client;

    private AutomaticSendingThread task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic_sending);

        Resources res = getResources();

        if (savedInstanceState != null) {
            dataArray = savedInstanceState.getBooleanArray(dataArrayKeyString);
        }

        if (dataArray == null) {
            dataArray = new boolean[res.getInteger(R.integer.number_of_lightbulb)];
        }

        final GridView gridview = (GridView) findViewById(R.id.gridview);
        adapter = new ImageAdapter(this,
                                   res.getInteger(R.integer.number_of_lightbulb),
                                   R.drawable.lightbulb_on,
                                   R.drawable.lightbulb_off,
                                   dataArray);
        gridview.setAdapter(adapter);

        task = new AutomaticSendingThread();
        task.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        task.kill();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_automatic_sending, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                try {
                    if (client.isConnected()) {
                        client.unsubscribe();
                    }
                } catch (MqttException e) {
                    Toast.makeText(AutomaticSendingActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBooleanArray(dataArrayKeyString, dataArray);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dataArray = savedInstanceState.getBooleanArray(dataArrayKeyString);
    }

    private class AutomaticSendingThread extends Thread {

        private volatile boolean isRunning = true;

        @Override
        public void run() {
            Resources res = getResources();
            int numberOfLightbulb = res.getInteger(R.integer.number_of_lightbulb);
            boolean[] dataArray = new boolean[numberOfLightbulb];

            if (client == null) {
                try {
                    client = new Client("tcp://iot.eclipse.org:1883", "ONPU.DIST.Sender", true);
                    client.setCallback(new Callback());
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }

            while (isRunning) {
                for (int i = 0; i < numberOfLightbulb; i++) {
                    dataArray[i] = (Math.random() >= 0.5);
                }
                adapter.setmThumbIds(dataArray, R.drawable.lightbulb_on, R.drawable.lightbulb_off);

                runOnUiThread(new Thread(new Runnable() {
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                }
                ));

                try {
                    byte[] payload = TypesConverter.toByta(dataArray);
                    client.publish("ONPU/DIST/Lights", Client.BEST_QOS, payload);
                } catch (MqttException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void kill() {
            isRunning = false;
        }

    }

    private class Callback implements MqttCallback {
        @Override
        public void connectionLost(Throwable throwable) {
            Toast.makeText(AutomaticSendingActivity.this, "Lost connection.", Toast.LENGTH_LONG).show();
        }

        @Override
        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        }
    }
}
