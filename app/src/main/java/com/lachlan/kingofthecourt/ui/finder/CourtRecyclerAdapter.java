package com.lachlan.kingofthecourt.ui.finder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.lachlan.kingofthecourt.R;
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
        private AppCompatButton buttonViewGame;
        private RecyclerCourtBinding binding;

        public ViewHolder(RecyclerCourtBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            buttonViewGame = binding.buttonViewGame;
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
        holder.textName.setText(gamesList.get(position).getDateTime().toString()); //@TODO: Placeholder
        holder.buttonViewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Game selectedGame = gamesList.get(position);
                Log.e("TEST", "THe game selected is: " + selectedGame.getDateTime().toString());
                NavController navController = Navigation.findNavController(view);
                NavDirections nav = CourtFragmentDirections.actionNavigationCourtToNavigationGame(selectedGame);
                navController.navigate(R.id.action_navigation_court_to_navigation_game);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }
}
