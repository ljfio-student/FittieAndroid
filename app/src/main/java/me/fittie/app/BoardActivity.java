package me.fittie.app;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import me.fittie.app.data.BoardDayLoader;
import me.fittie.app.data.BoardLoader;
import me.fittie.app.data.DietLoader;

public class BoardActivity extends AppCompatActivity {
    private ViewPagerAdapter adapter;
    private BoardLoader loader;

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

        Bundle extras = getIntent().getExtras();
        int boardId = extras.getInt("id", -1);

        Log.i("BoardActivity", String.format("I've been given %d", boardId));

        loader = new DietLoader(boardId);
        loader.load(getBaseContext());

        // Setup the ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        // Update the TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Loop through each day and add to board
        for (int i = 0; i < dayStrings.length; i++) {
            BoardDayFragment fragment = new BoardDayFragment();

            BoardDayLoader dayLoader = loader.getDayLoader(i);

            fragment.setLoader(dayLoader);

            adapter.addFragment(fragment, getString(dayStrings[i]).toUpperCase());
        }

        viewPager.setAdapter(adapter);
    }
}
