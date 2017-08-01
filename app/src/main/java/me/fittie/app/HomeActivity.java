package me.fittie.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;
import me.fittie.app.data.UserDietLoader;
import me.fittie.app.data.UserRoutineLoader;
import com.github.ljfio.requester.RequestWorker;

public class HomeActivity extends AppCompatActivity {
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean logged_in = preferences.getBoolean("logged_in", false);

        // Check if logged in
        if (!logged_in) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            finish();

            startActivity(intent);
        } else {
            RequestWorker.getInstance(getBaseContext())
                    .getDefaultHeaders()
                    .put("Authorization", "Token " + preferences.getString("user_token", ""));

            userId = preferences.getInt("user_id", -4);
        }

        // Setup the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Setup the ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        // Update the TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Floating action button
        FabSpeedDial fabSpeedDial = (FabSpeedDial) findViewById(R.id.fab_home_dial);
        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                //TODO: Start some activity
                return false;
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        RequestWorker instance = RequestWorker.getInstance(getBaseContext());

        // Diet Loader + Fragment
        UserDietLoader loader = new UserDietLoader(userId);

        HomeActivityFragment dietFragment = new HomeActivityFragment();
        dietFragment.setLoader(loader);

        adapter.addFragment(dietFragment, "DIETS");

        // Routine Loader + Fragment
        UserRoutineLoader routineLoader = new UserRoutineLoader(userId);

        HomeActivityFragment routineFragment = new HomeActivityFragment();
        routineFragment.setLoader(routineLoader);

        adapter.addFragment(routineFragment, "ROUTINES");

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
