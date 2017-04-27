package me.fittie.app.data;

import java.util.List;
import java.util.function.Consumer;

import me.fittie.app.network.NetWorker;

/**
 * Created by Luke on 27/04/2017.
 */

public interface DataSetLoader<T> {
    public List<T> Load(NetWorker worker, Consumer<Integer> after);
}
