package me.fittie.app.data;

import android.content.Context;

import com.android.volley.VolleyError;
import com.github.ljfio.requester.GetRequest;
import com.github.ljfio.requester.RequestWorker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import me.fittie.app.models.Diet;
import me.fittie.app.network.response.DietResponseObject;
import me.fittie.app.network.response.UserDietsResponseObject;

/**
 * Created by Luke on 27/04/2017.
 */

public class UserDietLoader extends DataLoader<Diet, Integer> {
    private int userId;
    private List<Diet> dataSet = new CopyOnWriteArrayList<>();

    public UserDietLoader(int userId) {
        this.userId = userId;
    }

    @Override
    public List<Diet> getDataSet() {
        return dataSet;
    }

    @Override
    public void load(Context context) {
        RequestWorker worker = RequestWorker.getInstance(context);

        // Load in data
        Map<String, String> params = new HashMap<>();
        params.put("basic", "true");

        String userDietUrl = String.format("https://api.fittie.me/user/%d/diet", userId);

        GetRequest<UserDietsResponseObject> userDietRequest = new GetRequest<>(
                userDietUrl, worker.getDefaultHeaders(), UserDietsResponseObject.class,
                (UserDietsResponseObject response) -> {
                    for (int id : response.diets) {
                        String dietUrl = String.format("https://api.fittie.me/diet/%d", id);

                        GetRequest<DietResponseObject> dietRequest = new GetRequest<>(
                                dietUrl, worker.getDefaultHeaders(), params, DietResponseObject.class,
                                (DietResponseObject dietResponse) -> {
                                    Diet diet = new Diet(id, dietResponse.name);
                                    dataSet.add(diet);

                                    notifyListeners(dataSet.indexOf(diet));
                                },
                                (VolleyError error) -> {});

                        worker.addToRequestQueue(dietRequest);
                    }
                },
                (VolleyError error) -> {}
        );

        worker.addToRequestQueue(userDietRequest);
    }
}
