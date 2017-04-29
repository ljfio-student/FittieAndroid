package me.fittie.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import me.fittie.app.data.BoardDayLoader;
import me.fittie.app.models.Item;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardDayFragment extends Fragment {
    private RecyclerView recyclerView;
    private CardAdapter adapter;
    private BoardDayLoader loader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_day, container, false);

        // Setup the recycler view
        recyclerView = (RecyclerView) view.findViewById(R.id.rv);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CardAdapter(loader);
        recyclerView.setAdapter(adapter);

        loader.load(getBaseContext());

        loader.addListener(pos -> {
            getActivity().runOnUiThread(() -> {
                adapter.notifyItemInserted(pos);
            });
        });

        return view;
    }

    private Context getBaseContext() {
        return getActivity().getBaseContext();
    }

    public void setLoader(BoardDayLoader loader) {
        this.loader = loader;

    }

    public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
        private BoardDayLoader loader;

        public CardAdapter(BoardDayLoader loader) {
            this.loader = loader;
        }

        @Override
        public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_item, parent, false);

            CardViewHolder viewHolder = new CardViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CardViewHolder holder, int position) {
            Item item = loader.getItem(position);

            holder.cardTitle.setText(item.getName());
            holder.cardTextBody.setText(item.getDescription());
            holder.boardId = item.getId();
        }

        @Override
        public int getItemCount() {
            return loader.getItemCount();
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
