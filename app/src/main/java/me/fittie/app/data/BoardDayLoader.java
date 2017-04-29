package me.fittie.app.data;

import android.content.Context;
import android.util.Pair;

import java.util.List;

import me.fittie.app.models.Item;

/**
 * Created by luke on 29/04/2017.
 */

public abstract class BoardDayLoader extends DataSetLoader<Item, Integer> {
    private int day;
    private BoardLoader loader;

    public BoardDayLoader(BoardLoader loader, int day) {
        this.loader = loader;
        this.day = day;

        loader.addListener(key -> {
            if (key.first == day) {
                notifyListeners(key.second);
            }
        });
    }

    @Override
    public void load(Context context) {
        // TODO: Do nothing as this is done in BoardLoader
    }

    @Override
    public List<Item> getDataSet() {
        return null;
    }

    public Item getItem(int position) {
        return loader.getItem(Pair.create(day, position));
    }

    public int getItemCount() {
        return loader.getDaySize(day);
    }
}
