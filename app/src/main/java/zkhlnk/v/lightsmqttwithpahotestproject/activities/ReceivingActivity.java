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

import zkhlnk.v.lightsmqttwithpahotestproject.R;
import zkhlnk.v.lightsmqttwithpahotestproject.adapters.ImageAdapter;
import zkhlnk.v.lightsmqttwithpahotestproject.mqtt.Client;
import zkhlnk.v.lightsmqttwithpahotestproject.utils.TypesConverter;

public class ReceivingActivity extends ActionBarActivity {

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

        // TODO: if client is received info, you may save it here into dataArray

        final GridView gridview = (GridView) findViewById(R.id.gridview);
        final ImageAdapter adapter = new ImageAdapter(this, NUMBER_OF_LIGHTBULB, R.drawable.lightbulb_on, R.drawable.lightbulb_off, dataArray);
        gridview.setAdapter(adapter);


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
