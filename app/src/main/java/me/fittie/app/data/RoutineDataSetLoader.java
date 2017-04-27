package me.fittie.app.data;

import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import me.fittie.app.models.Routine;
import me.fittie.app.network.GsonGetRequest;
import me.fittie.app.network.NetWorker;
import me.fittie.app.network.response.RoutineResponseObject;
import me.fittie.app.network.response.UserRoutinesResponseObject;

/**
 * Created by Luke on 27/04/2017.
 */

public class RoutineDataSetLoader implements DataSetLoader<Routine> {
    private int userId;

    public RoutineDataSetLoader(int userId) {
        this.userId = userId;
    }

    public List<Routine> Load(NetWorker worker, Consumer<Integer> after) {
        CopyOnWriteArrayList<Routine> dataSet = new CopyOnWriteArrayList<>();

        // Load in data
        Map<String, String> params = new HashMap<>();
        params.put("basic", "true");

        String userRoutineUrl = String.format("https://api.fittie.me/user/%d/routine", userId);

        GsonGetRequest<UserRoutinesResponseObject> userRoutineRequest = new GsonGetRequest<>(
                userRoutineUrl, UserRoutinesResponseObject.class, worker.getDefaultHeaders(),
                (UserRoutinesResponseObject response) -> {
                    for (int id : response.routines) {
                        String routineUrl = String.format("https://api.fittie.me/routine/%d", id);

                        GsonGetRequest<RoutineResponseObject> routineRequest = new GsonGetRequest<>(
                                routineUrl, RoutineResponseObject.class, worker.getDefaultHeaders(), params,
                                (RoutineResponseObject routineResponse) -> {
                                    Routine routine = new Routine(id, routineResponse.name);
                                    dataSet.add(routine);

                                    after.accept(dataSet.indexOf(routine));
                                },
                                (VolleyError error) -> {});

                        worker.addToRequestQueue(routineRequest);
                    }
                },
                (VolleyError error) -> {}
        );

        worker.addToRequestQueue(userRoutineRequest);

        return dataSet;
    }
}
