package com.lachlan.kingofthecourt.ui.finder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lachlan.kingofthecourt.databinding.RecyclerCourtBinding;
import com.lachlan.kingofthecourt.model.Game;

import java.util.ArrayList;

public class CourtRecyclerAdapter extends RecyclerView.Adapter<CourtRecyclerAdapter.ViewHolder> {
    private ArrayList<Game> gamesList;

    public CourtRecyclerAdapter(ArrayList<Game> gamesList) {
        this.gamesList = gamesList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textName;
        private RecyclerCourtBinding binding;

        public ViewHolder(RecyclerCourtBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            textName = binding.textName;
        }
    }

    @NonNull
    @Override
    public CourtRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerCourtBinding binding =
                RecyclerCourtBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CourtRecyclerAdapter.ViewHolder holder, int position) {
//        User creator = gamesList.get(position).getCreator();
        holder.textName.setText(gamesList.get(position).getDateTime().toString()); //@TODO: Placeholder

    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }
}
