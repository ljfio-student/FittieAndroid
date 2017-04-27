package me.fittie.app;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);


        // Setup the ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        // Update the TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new BoardDayFragment(), getString(R.string.board_day_monday).toUpperCase());
        adapter.addFragment(new BoardDayFragment(), getString(R.string.board_day_tuesday).toUpperCase());
        adapter.addFragment(new BoardDayFragment(), getString(R.string.board_day_wednesday).toUpperCase());
        adapter.addFragment(new BoardDayFragment(), getString(R.string.board_day_thursday).toUpperCase());
        adapter.addFragment(new BoardDayFragment(), getString(R.string.board_day_friday).toUpperCase());
        adapter.addFragment(new BoardDayFragment(), getString(R.string.board_day_saturday).toUpperCase());
        adapter.addFragment(new BoardDayFragment(), getString(R.string.board_day_sunday).toUpperCase());

        viewPager.setAdapter(adapter);
    }

}
