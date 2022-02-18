package com.lachlan.kingofthecourt.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.data.entity.Game;
import com.lachlan.kingofthecourt.databinding.RecyclerCourtBinding;
import com.lachlan.kingofthecourt.databinding.RecyclerHomeBinding;
import com.lachlan.kingofthecourt.fragments.CourtFragmentDirections;
import com.lachlan.kingofthecourt.fragments.HomeFragmentDirections;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder> {
    private List<Game> gamesList;

    public HomeRecyclerAdapter(List<Game> gamesList) {
        this.gamesList = gamesList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textDate;
        private TextView textTime;
        private AppCompatButton buttonViewGame;
        private RecyclerHomeBinding binding;

        public ViewHolder(RecyclerHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            buttonViewGame = binding.buttonViewGameHome;
            textDate = binding.textGameDateHome;
            textTime = binding.textGameTimeHome;
        }
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

        // Comparing the date of the game to the current date and removing from recycler view if the game date is in the past
        LocalDate localDate = LocalDate.now();
        Date gameDate = selectedGame.getDateTime();
        LocalDate localGameDate = Instant.ofEpochMilli(gameDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

        if (localDate.isAfter(localGameDate)) {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        }

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
}
