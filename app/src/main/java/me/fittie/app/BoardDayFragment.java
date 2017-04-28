package me.fittie.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import me.fittie.app.models.Item;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardDayFragment extends Fragment {
    private RecyclerView recyclerView;
    private CardAdapter adapter;

    private List<? extends Item> dataSet;

    public BoardDayFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_day, container, false);

        // Setup the recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CardAdapter(dataSet);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void setDataSet(List<? extends Item> dataSet) {
        this.dataSet = dataSet;
    }

    public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
        private List<? extends Item> dataSet;

        public CardAdapter(List<? extends Item> dataSet) {
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
            Item item = dataSet.get(position);

            holder.cardTitle.setText(item.getName());
            holder.boardId = item.getId();
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }

        public class CardViewHolder extends RecyclerView.ViewHolder {
            public TextView cardTitle;
            public TextView cardTextBody;
            public Button cardButton;

            public Integer boardId;

            public CardViewHolder(View v) {
                super(v);

                cardTitle = (TextView) v.findViewById(R.id.card_title);
                cardTextBody = (TextView) v.findViewById(R.id.card_text_body);
                cardButton = (Button) v.findViewById(R.id.card_button);
            }
        }
    }
}
