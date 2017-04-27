package me.fittie.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {
    private int userId;

    private RecyclerView recyclerView;

    public HomeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        userId = preferences.getInt("user_id", -1);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        ArrayList test = new ArrayList<String>();
        test.add("Test");

        RecyclerView.Adapter adapter = new CardAdapter(test);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private Context getBaseContext() {
        return getActivity().getBaseContext();
    }

    public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
        protected TextView cardTitle;
        private ArrayList<String> dataSet;

        public CardAdapter(ArrayList<String> dataSet) {
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
            holder.titleView.setText(dataSet.get(position));
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        public class CardViewHolder extends RecyclerView.ViewHolder {
            public TextView titleView;

            public CardViewHolder(View v) {
                super(v);

                titleView = (TextView) v.findViewById(R.id.card_title);
            }
        }
    }
}
