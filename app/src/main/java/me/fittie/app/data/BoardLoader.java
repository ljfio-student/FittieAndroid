package me.fittie.app.data;

import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import me.fittie.app.models.Board;
import me.fittie.app.models.Item;

/**
 * Created by luke on 29/04/2017.
 */

public abstract class BoardLoader extends DataLoader<Item, Pair<Integer, Integer>> {
    private int boardId;
    protected Consumer<Board> loadedListener;

    private Map<Pair<Integer, Integer>, Item> dataSet = new ConcurrentHashMap<>();
    private Map<Integer, AtomicInteger> dayCount = new ConcurrentHashMap<>();

    public BoardLoader(int boardId) {
        super();

        this.boardId = boardId;

        for (int i = 0; i < 7; i++) {
            dayCount.put(i, new AtomicInteger());
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

        dayCount.get(key.first).incrementAndGet();

        notifyListeners(key);

        Log.i("BoardLoader", String.format("Notifying listeners... %d %d added", key.first, key.second));
    }

    public Item getItem(Pair<Integer, Integer> key) {
        Log.i("BoardLoader", String.format("retrieving: %d %d", key.first, key.second));
        return dataSet.get(key);
    }

    public abstract BoardDayLoader getDayLoader(int day);

    public int getDaySize(int day) {
        return dayCount.get(day).get();
    }

    public void setLoadedListener(Consumer<Board> loadedListener) {
        this.loadedListener = loadedListener;
    }
}
