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

import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.databinding.RecyclerHomeBinding;
import com.lachlan.kingofthecourt.fragments.HomeFragmentDirections;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {
    private final List<Game> gamesList;

    public HomeRecyclerAdapter(List<Game> gamesList) {
        this.gamesList = gamesList;
    }

    @NonNull
    @Override
    public HomeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerHomeBinding binding =
                RecyclerHomeBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerAdapter.ViewHolder holder, int position) {
        Game selectedGame = gamesList.get(position);

        holder.textDate.setText(getFormattedDate(selectedGame));
        holder.textTime.setText(getFormattedTime(selectedGame));
        holder.buttonViewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                NavDirections nav = HomeFragmentDirections.actionNavigationHomeToNavigationGame(selectedGame, selectedGame.getLocationId());
                navController.navigate(nav);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    public String getFormattedDate(Game game) {
        DateFormat day = new SimpleDateFormat("EE");
        DateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        Date dateTime = game.getDateTime();
        return day.format(dateTime) + " " + date.format(dateTime);
    }

    public String getFormattedTime(Game game) {
        DateFormat time = new SimpleDateFormat("hh:mm:ss a");
        Date dateTime = game.getDateTime();
        return time.format(dateTime);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textDate;
        private final TextView textTime;
        private final AppCompatButton buttonViewGame;
        private final RecyclerHomeBinding binding;

        public ViewHolder(RecyclerHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            buttonViewGame = binding.buttonViewGameHome;
            textDate = binding.textGameDateHome;
            textTime = binding.textGameTimeHome;
        }
    }
}
