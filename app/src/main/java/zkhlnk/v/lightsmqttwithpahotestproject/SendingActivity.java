package zkhlnk.v.lightsmqttwithpahotestproject;

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

public class SendingActivity extends ActionBarActivity {

    private static final int NUMBER_OF_LIGHTBULB = 15;

    private boolean[] dataArray = new boolean[NUMBER_OF_LIGHTBULB];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending);

        final GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, NUMBER_OF_LIGHTBULB, R.drawable.lightbulb_off));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                //Toast.makeText(SendingActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                ImageView imageView = (ImageView) v;
                if (
                        imageView.getDrawable().getConstantState().equals
                                (getResources().getDrawable(R.drawable.lightbulb_off).getConstantState())
                        ) {
                    imageView.setImageResource(R.drawable.lightbulb_on);
                } else {
                    imageView.setImageResource(R.drawable.lightbulb_off);
                }
                dataArray[position] = !dataArray[position];
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_sending, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_send:
                Toast.makeText(SendingActivity.this, "Sending...", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
