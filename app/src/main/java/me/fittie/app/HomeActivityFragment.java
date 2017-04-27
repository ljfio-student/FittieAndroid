package me.fittie.app;

import android.content.Context;
import android.content.Intent;
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

import me.fittie.app.data.DataSetLoader;
import me.fittie.app.models.Board;
import me.fittie.app.network.NetWorker;

/**
 * A placeholder fragment containing a simple view.
 * This saved my bacon: http://stackoverflow.com/questions/34579614/how-to-implement-recyclerview-with-cardview-rows-in-a-fragment-with-tablayout
 */
public class HomeActivityFragment<T extends Board> extends Fragment {
    private RecyclerView recyclerView;
    private CardAdapter adapter;
    private DataSetLoader<T> loader;

    public HomeActivityFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Setup the recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        NetWorker worker = NetWorker.getInstance(getBaseContext());

        List<? extends Board> dataSet = loader.Load(worker, (index) -> {
            getActivity().runOnUiThread(() -> {
                adapter.notifyItemInserted(index);
            });
        });

        adapter = new CardAdapter(dataSet);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void setLoader(DataSetLoader<T> loader) {
        this.loader = loader;
    }

    private Context getBaseContext() {
        return getActivity().getBaseContext();
    }

    public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
        private List<? extends Board> dataSet;

        public CardAdapter(List<? extends Board> dataSet) {
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
            Board board = dataSet.get(position);

            holder.cardTitle.setText(board.getName());
            holder.boardId = board.getId();

            holder.cardButton.setOnClickListener((View v) -> {
                Intent intent = new Intent(getActivity(), BoardActivity.class);

                Bundle args = new Bundle();
                args.putInt("id", holder.boardId);

                intent.putExtras(args);
                startActivity(intent);
            });
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
