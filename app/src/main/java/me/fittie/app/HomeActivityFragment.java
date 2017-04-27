package me.fittie.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import me.fittie.app.models.Diet;
import me.fittie.app.network.GsonGetRequest;
import me.fittie.app.network.NetWorker;
import me.fittie.app.network.response.DietResponseObject;
import me.fittie.app.network.response.UserDietsResponseObject;

/**
 * A placeholder fragment containing a simple view.
 * This saved my bacon: http://stackoverflow.com/questions/34579614/how-to-implement-recyclerview-with-cardview-rows-in-a-fragment-with-tablayout
 */
public class HomeActivityFragment extends Fragment {
    private int userId;

    private RecyclerView recyclerView;
    private CardAdapter adapter;

    public HomeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userId = preferences.getInt("user_id", -1);

        // Setup the recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        CopyOnWriteArrayList<Diet> dataSet = new CopyOnWriteArrayList<>();

        adapter = new CardAdapter(dataSet);
        recyclerView.setAdapter(adapter);

        loadDiets(dataSet);

        return view;
    }

    private void loadDiets(CopyOnWriteArrayList<Diet> dataSet) {
        // Load in data
        NetWorker instance = NetWorker.getInstance(getBaseContext());

        Map<String, String> params = new HashMap<>();
        params.put("basic", "true");

        String userDietUrl = String.format("https://api.fittie.me/user/%d/diet", userId);

        GsonGetRequest<UserDietsResponseObject> userDietRequest = new GsonGetRequest<>(
                userDietUrl, UserDietsResponseObject.class, instance.getDefaultHeaders(),
                (UserDietsResponseObject response) -> {
                    for (int id : response.diets) {
                        String dietUrl = String.format("https://api.fittie.me/diet/%d", id);

                        GsonGetRequest<DietResponseObject> dietRequest = new GsonGetRequest<>(
                                dietUrl, DietResponseObject.class, instance.getDefaultHeaders(), params,
                                (DietResponseObject dietResponse) -> {
                                    Diet diet = new Diet(id, dietResponse.name);
                                    dataSet.add(diet);

                                    getActivity().runOnUiThread(() -> {
                                        adapter.notifyItemInserted(dataSet.indexOf(diet));
                                    });
                                },
                                (VolleyError error) -> {});

                        instance.addToRequestQueue(dietRequest);
                    }
                },
                (VolleyError error) -> {}
        );

        instance.addToRequestQueue(userDietRequest);
    }

    private Context getBaseContext() {
        return getActivity().getBaseContext();
    }

    public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
        private CopyOnWriteArrayList<Diet> dataSet;

        public CardAdapter(CopyOnWriteArrayList<Diet> dataSet) {
            this.dataSet = dataSet;
        }

        @Override
        public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_item, parent, false);

            CardViewHolder viewHolder = new CardViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CardViewHolder holder, int position) {
            Diet diet = dataSet.get(position);
            holder.cardTitle.setText(diet.getName());
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        public class CardViewHolder extends RecyclerView.ViewHolder {
            public TextView cardTitle;
            public TextView cardTextBody;
            public Button cardButton;

            public CardViewHolder(View v) {
                super(v);

                cardTitle = (TextView) v.findViewById(R.id.card_title);
                cardTextBody = (TextView) v.findViewById(R.id.card_text_body);
                cardButton = (Button) v.findViewById(R.id.card_button);
            }
        }
    }
}
