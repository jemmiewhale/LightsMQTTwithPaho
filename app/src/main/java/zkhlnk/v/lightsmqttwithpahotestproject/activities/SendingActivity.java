package zkhlnk.v.lightsmqttwithpahotestproject.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttException;

import zkhlnk.v.lightsmqttwithpahotestproject.adapters.ImageAdapter;
import zkhlnk.v.lightsmqttwithpahotestproject.R;
import zkhlnk.v.lightsmqttwithpahotestproject.mqtt.Client;
import zkhlnk.v.lightsmqttwithpahotestproject.utils.TypesConverter;

public class SendingActivity extends ActionBarActivity {

    private static final int NUMBER_OF_LIGHTBULB = 30;

    private boolean[] dataArray = new boolean[NUMBER_OF_LIGHTBULB];
    private String dataArrayKeyString = "DataArray";

    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending);

        if (savedInstanceState != null) {
            dataArray = savedInstanceState.getBooleanArray(dataArrayKeyString);
        }

        final GridView gridview = (GridView) findViewById(R.id.gridview);
        final ImageAdapter adapter = new ImageAdapter(this, NUMBER_OF_LIGHTBULB, R.drawable.lightbulb_on, R.drawable.lightbulb_off, dataArray);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ImageView imageView = (ImageView) v;
                if (
                        imageView.getDrawable().getConstantState().equals
                                (getResources().getDrawable(R.drawable.lightbulb_off).getConstantState())
                        ) {
                    imageView.setImageResource(R.drawable.lightbulb_on);
                    adapter.setResource(position, R.drawable.lightbulb_on);
                } else {
                    imageView.setImageResource(R.drawable.lightbulb_off);
                    adapter.setResource(position, R.drawable.lightbulb_off);
                }
                dataArray[position] = !dataArray[position];
            }
        });

        if (client == null) {
            client = new Client("tcp://iot.eclipse.org:1883", "ONPU.DIST.Sender", true);
            // TODO Change if needed
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sending, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_send:
                Toast.makeText(SendingActivity.this, "Sending...", Toast.LENGTH_SHORT).show();

                try {
                    byte[] payload = TypesConverter.toByta(dataArray);
                    client.publish("ONPU/DIST/Lights", Client.BEST_QOS, payload);
                    // TODO Change if needed
                } catch (MqttException e) {
                    Toast.makeText(SendingActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }

                return true;
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
}
