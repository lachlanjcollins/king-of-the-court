package com.lachlan.kingofthecourt.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.lachlan.kingofthecourt.databinding.RecyclerCourtBinding;
import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.fragments.CourtFragmentDirections;

import java.util.ArrayList;

public class CourtRecyclerAdapter extends RecyclerView.Adapter<CourtRecyclerAdapter.ViewHolder> {
    private ArrayList<Game> gamesList;
    private Court court;

    public CourtRecyclerAdapter(ArrayList<Game> gamesList, Court court) {
        this.gamesList = gamesList;
        this.court = court;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textName;
        private TextView textPlayers;
        private AppCompatButton buttonViewGame;
        private RecyclerCourtBinding binding;

        public ViewHolder(RecyclerCourtBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            buttonViewGame = binding.buttonViewGame;
            textName = binding.textName;
            textPlayers = binding.textPlayers;
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
//        holder.textName.setText(gamesList.get(position).getDateTime().toString());
//        holder.textPlayers.setText(gamesList.get(position).getPlayers().size() + " / 10 Players");
        holder.buttonViewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game selectedGame = gamesList.get(position);
                NavController navController = Navigation.findNavController(view);
                NavDirections nav = CourtFragmentDirections.actionNavigationCourtToNavigationGame(selectedGame, court);
                navController.navigate(nav);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }
}
