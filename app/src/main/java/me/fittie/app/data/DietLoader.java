package me.fittie.app.data;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.android.volley.VolleyError;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import me.fittie.app.models.Diet;
import me.fittie.app.models.Item;
import me.fittie.app.models.Meal;
import me.fittie.app.network.GsonRequestBuilder;
import me.fittie.app.network.NetWorker;
import me.fittie.app.network.request.ItemRequestObject;
import me.fittie.app.network.response.DietResponseObject;

/**
 * Created by luke on 29/04/2017.
 */

public class DietLoader extends BoardLoader {

    public DietLoader(int boardId) {
        super(boardId);
    }

    @Override
    public void load(Context context) {
        NetWorker worker = NetWorker.getInstance(context);
        SQLDatabaseHelper helper = new SQLDatabaseHelper(context);

        GsonRequestBuilder.get(DietResponseObject.class)
                .setHeaders(worker.getDefaultHeaders())
                .setUrl(String.format("https://api.fittie.me/diet/%d", getBoardId()))
                .setListener((DietResponseObject response) -> {
                    loadedListener.accept(new Diet(getBoardId(), response.name));

                    // TODO: Update the page
                    Log.i("DietLoader", "Got me: " + response.name);

                    // TODO: Make this more efficient (caching?)
                    response.meals.forEach(meal -> {
                        try {
                            Dao<Meal, Integer> mealDao = helper.getMealDao();
                            Item existing = mealDao.queryForId(meal.id);

                            if (existing != null) {
                                Meal newMeal = new Meal(meal.id, existing.getName(), existing.getDescription(), meal.day, meal.order);
                                addItem(Pair.create(meal.day, meal.order), newMeal);

                                // TODO: Notify that we have added to the UI
                                Log.i("DietLoader", "Meal added via SQL");
                            } else {
                                GsonRequestBuilder.get(ItemRequestObject.class)
                                        .setHeaders(worker.getDefaultHeaders())
                                        .setUrl(String.format("https://api.fittie.me/meal/%d", meal.id))
                                        .setListener((ItemRequestObject item) -> {
                                            Meal newMeal = new Meal(meal.id, item.name, item.description, meal.day, meal.order);
                                            addItem(Pair.create(meal.day, meal.order), newMeal);

                                            // TODO: Notify that we have added to the UI
                                            Log.i("DietLoader", "Meal added via SQL");

                                            try {
                                                mealDao.create(newMeal);
                                            } catch (SQLException ex) {
                                                Log.e("BoardActivity", ex.toString());
                                            }
                                        })
                                        .execute(worker);
                            }
                        } catch (SQLException ex) {
                            Log.e("BoardActivity", ex.toString());
                        }
                    });
                })
                .setErrorListener((VolleyError error) -> {
                    Log.e("BoardActivity", error.toString());
                })
                .execute(worker);
    }

    @Override
    public BoardDayLoader getDayLoader(int day) {
        return new DietDayLoader(this, day);
    }
}
