package com.example.admin.nav.main_menu;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class ReleaseNotesActivity extends AppCompatActivity {


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
