package zkhlnk.v.lightsmqttwithpahotestproject.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import zkhlnk.v.lightsmqttwithpahotestproject.R;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void sendButtonOnClick(View view) {
        Intent intent = new Intent(this, SendingActivity.class);
        startActivity(intent);
    }

    public void getButtonOnClick(View view) {
        Intent intent = new Intent(this, ReceivingActivity.class);
        startActivity(intent);
    }
}
