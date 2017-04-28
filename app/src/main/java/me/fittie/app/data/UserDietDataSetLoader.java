package me.fittie.app.data;

import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import me.fittie.app.models.Diet;
import me.fittie.app.network.GsonGetRequest;
import me.fittie.app.network.NetWorker;
import me.fittie.app.network.response.DietResponseObject;
import me.fittie.app.network.response.UserDietsResponseObject;

/**
 * Created by Luke on 27/04/2017.
 */

public class UserDietDataSetLoader implements DataSetLoader<Diet> {
    private int userId;

    public UserDietDataSetLoader(int userId) {
        this.userId = userId;
    }

    public List<Diet> Load(NetWorker worker, Consumer<Integer> after) {
        CopyOnWriteArrayList<Diet> dataSet = new CopyOnWriteArrayList<>();

        // Load in data
        Map<String, String> params = new HashMap<>();
        params.put("basic", "true");

        String userDietUrl = String.format("https://api.fittie.me/user/%d/diet", userId);

        GsonGetRequest<UserDietsResponseObject> userDietRequest = new GsonGetRequest<>(
                userDietUrl, UserDietsResponseObject.class, worker.getDefaultHeaders(),
                (UserDietsResponseObject response) -> {
                    for (int id : response.diets) {
                        String dietUrl = String.format("https://api.fittie.me/diet/%d", id);

                        GsonGetRequest<DietResponseObject> dietRequest = new GsonGetRequest<>(
                                dietUrl, DietResponseObject.class, worker.getDefaultHeaders(), params,
                                (DietResponseObject dietResponse) -> {
                                    Diet diet = new Diet(id, dietResponse.name);
                                    dataSet.add(diet);

                                    after.accept(dataSet.indexOf(diet));
                                },
                                (VolleyError error) -> {});

                        worker.addToRequestQueue(dietRequest);
                    }
                },
                (VolleyError error) -> {}
        );

        worker.addToRequestQueue(userDietRequest);

        return dataSet;
    }
}
