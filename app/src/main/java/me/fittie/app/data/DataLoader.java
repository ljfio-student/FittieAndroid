package me.fittie.app.data;

import android.content.Context;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Created by Luke on 27/04/2017.
 */

public abstract class DataLoader<T, U> {
    private List<Consumer<U>> listeners;

    public DataLoader() {
        listeners = new CopyOnWriteArrayList<>();
    }

    public void addListener(Consumer<U> listener) {
        listeners.add(listener);
    }

    protected void notifyListeners(U item) {
        listeners.forEach(l -> {
            l.accept(item);
        });
    }

    public abstract void load(Context context);
    public abstract List<T> getDataSet();
}
