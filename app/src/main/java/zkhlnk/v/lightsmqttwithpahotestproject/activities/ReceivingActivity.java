package zkhlnk.v.lightsmqttwithpahotestproject.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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

public class ReceivingActivity extends ActionBarActivity {

    private static final int NUMBER_OF_LIGHTBULB = 30;

    private boolean[] dataArray = new boolean[NUMBER_OF_LIGHTBULB];
    private String dataArrayKeyString = "DataArray";

    private Client client;

    GridView gridview;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending);

        if (savedInstanceState != null) {
            dataArray = savedInstanceState.getBooleanArray(dataArrayKeyString);
        }

        gridview = (GridView) findViewById(R.id.gridview);
        adapter = new ImageAdapter(this, NUMBER_OF_LIGHTBULB, R.drawable.lightbulb_on, R.drawable.lightbulb_off, dataArray);
        gridview.setAdapter(adapter);

        if (client == null) {
            try {
                client = new Client("tcp://iot.eclipse.org:1883", "ONPU.DIST.Receiver", true);
                client.setCallback(new Callback());
            } catch (MqttException e) {
                Toast.makeText(ReceivingActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
        try {
            client.subscribe("ONPU/DIST/Lights", Client.BEST_QOS);
        } catch (MqttException e) {
            Toast.makeText(ReceivingActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                try {
                    client.unsubscribe();
                } catch (MqttException e) {
                    Toast.makeText(ReceivingActivity.this, e.toString(), Toast.LENGTH_LONG).show();
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

    private class Callback implements MqttCallback {
        @Override
        public void connectionLost(Throwable throwable) {
            Toast.makeText(ReceivingActivity.this, "Lost connection.", Toast.LENGTH_LONG).show();
        }

        @Override
        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
            byte[] payload = mqttMessage.getPayload();
            dataArray = TypesConverter.toBooleanA(payload);
            adapter.setmThumbIds(dataArray, R.drawable.lightbulb_on, R.drawable.lightbulb_off);
            adapter.notifyDataSetChanged();
        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        }
    }
}
