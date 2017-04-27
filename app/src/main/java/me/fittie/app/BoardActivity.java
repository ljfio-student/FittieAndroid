package me.fittie.app;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BoardActivity extends AppCompatActivity {
    private final int[] dayStrings = {
            R.string.board_day_monday,
            R.string.board_day_tuesday,
            R.string.board_day_wednesday,
            R.string.board_day_thursday,
            R.string.board_day_friday,
            R.string.board_day_saturday,
            R.string.board_day_sunday
    };

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

        // Loop through each day and add to board
        for (int i = 0; i < 7; i++) {
            // TODO: Get data
            adapter.addFragment(new BoardDayFragment(), getString(dayStrings[i]).toUpperCase());
        }

        viewPager.setAdapter(adapter);
    }

}
