package me.fittie.app.data;

import android.content.Context;

import com.android.volley.VolleyError;
import com.github.ljfio.requester.GetRequest;
import com.github.ljfio.requester.RequestWorker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import me.fittie.app.models.Routine;
import me.fittie.app.network.response.RoutineResponseObject;
import me.fittie.app.network.response.UserRoutinesResponseObject;

/**
 * Created by Luke on 27/04/2017.
 */

public class UserRoutineLoader extends DataLoader<Routine,Integer> {
    private int userId;
    private List<Routine> dataSet = new CopyOnWriteArrayList<>();

    public UserRoutineLoader(int userId) {
        this.userId = userId;
    }

    @Override
    public List<Routine> getDataSet() {
        return dataSet;
    }

    @Override
    public void load(Context context) {
        RequestWorker worker = RequestWorker.getInstance(context);

        // Load in data
        Map<String, String> params = new HashMap<>();
        params.put("basic", "true");

        String userRoutineUrl = String.format("https://api.fittie.me/user/%d/routine", userId);

        GetRequest<UserRoutinesResponseObject> userRoutineRequest = new GetRequest<>(
                userRoutineUrl, worker.getDefaultHeaders(), UserRoutinesResponseObject.class,
                (UserRoutinesResponseObject response) -> {
                    for (int id : response.routines) {
                        String routineUrl = String.format("https://api.fittie.me/routine/%d", id);

                        GetRequest<RoutineResponseObject> routineRequest = new GetRequest<>(
                                routineUrl, worker.getDefaultHeaders(), params, RoutineResponseObject.class,
                                (RoutineResponseObject routineResponse) -> {
                                    Routine routine = new Routine(id, routineResponse.name);
                                    dataSet.add(routine);

                                    notifyListeners(dataSet.indexOf(routine));
                                },
                                (VolleyError error) -> {});

                        worker.addToRequestQueue(routineRequest);
                    }
                },
                (VolleyError error) -> {}
        );

        worker.addToRequestQueue(userRoutineRequest);
    }
}
