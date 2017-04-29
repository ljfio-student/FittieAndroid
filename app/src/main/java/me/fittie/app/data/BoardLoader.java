package me.fittie.app.data;

import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.fittie.app.models.Item;

/**
 * Created by luke on 29/04/2017.
 */

public abstract class BoardLoader extends DataSetLoader<Item, Pair<Integer, Integer>> {
    private int boardId;

    private Map<Pair<Integer, Integer>, Item> dataSet = new ConcurrentHashMap<>();
    private Map<Integer, Integer> dayCount = new ConcurrentHashMap<>();

    public BoardLoader(int boardId) {
        super();

        this.boardId = boardId;

        for (int i = 0; i < 7; i++) {
            dayCount.put(i, 0);
        }
    }

    public List<Item> getDataSet() {
        return new ArrayList<>(dataSet.values());
    }

    public int getBoardId() {
        return boardId;
    }

    public void addItem(Pair<Integer, Integer> key, Item value) {
        dataSet.put(key, value);

        dayCount.put(key.first, dayCount.get(key.first) + 1);

        notifyListeners(key);
    }

    public Item getItem(Pair<Integer, Integer> key) {
        return dataSet.get(key);
    }

    public abstract BoardDayLoader getDayLoader(int day);

    public int getDaySize(int day) {
        return dayCount.get(day);
    }
}
