package me.fittie.app;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import me.fittie.app.models.Item;
import me.fittie.app.models.Meal;
import me.fittie.app.network.GsonRequestBuilder;
import me.fittie.app.network.NetWorker;
import me.fittie.app.network.request.ItemRequestObject;
import me.fittie.app.network.response.DietResponseObject;

public class BoardActivity extends AppCompatActivity {
    private ConcurrentHashMap<Integer, CopyOnWriteArrayList<Item>> dataSet;

    private final int[] dayStrings = {
            R.string.board_day_monday,
            R.string.board_day_tuesday,
            R.string.board_day_wednesday,
            R.string.board_day_thursday,
            R.string.board_day_friday,
            R.string.board_day_saturday,
            R.string.board_day_sunday
    };

    private void makeDataSet() {
        dataSet = new ConcurrentHashMap<>();

        for(int i = 0; i < dayStrings.length; i++) {
            dataSet.put(i, new CopyOnWriteArrayList<>());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        Bundle extras = getIntent().getExtras();
        int boardId = extras.getInt("id", -1);

        Log.i("BoardActivity", String.format("I've been given %d", boardId));

        makeDataSet();

        NetWorker worker = NetWorker.getInstance(getBaseContext());

        GsonRequestBuilder.get(DietResponseObject.class)
                .setHeaders(worker.getDefaultHeaders())
                .setUrl(String.format("https://api.fittie.me/diet/%d", boardId))
                .setListener((DietResponseObject response) -> {
                    runOnUiThread(() -> {
                        setTitle(response.name);
                    });

                    // TODO: Make this more efficient (caching?)
                    response.meals.forEach(meal -> {
                        GsonRequestBuilder.get(ItemRequestObject.class)
                                .setHeaders(worker.getDefaultHeaders())
                                .setUrl(String.format("https://api.fittie.me/meal/%d", meal.id))
                                .setListener((ItemRequestObject item) -> {
                                    Meal newMeal = new Meal(meal.id, item.name, meal.order);
                                    dataSet.get(meal.day).add(newMeal);
                                })
                                .execute(worker);
                    });
                })
                .setErrorListener((VolleyError error) -> {
                    Log.e("BoardActivity", error.toString());
                })
                .execute(worker);

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
        for (int i = 0; i < dayStrings.length; i++) {
            BoardDayFragment fragment = new BoardDayFragment();
            fragment.setDataSet(dataSet.get(i));
            adapter.addFragment(fragment, getString(dayStrings[i]).toUpperCase());
        }

        viewPager.setAdapter(adapter);
    }

}
