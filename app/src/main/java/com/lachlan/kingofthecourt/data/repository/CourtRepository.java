package com.lachlan.kingofthecourt.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.lachlan.kingofthecourt.data.dao.CourtDAO;
import com.lachlan.kingofthecourt.data.database.LocalDB;
import com.lachlan.kingofthecourt.data.database.RemoteDB;
import com.lachlan.kingofthecourt.data.entity.Court;
import com.lachlan.kingofthecourt.data.relation.CourtWithGames;

import java.util.List;

/**
 * A repository class that provides a level of abstraction between court data and various view model classes.
 *
 * @author Lachlan Collins
 * @version 20 February 2022
 */
public class CourtRepository {
    private final CourtDAO courtDAO;
    private final LiveData<List<Court>> allCourts;
    private final GameRepository gameRepository;
    private final RemoteDB remoteDB;

    public CourtRepository(Application application) {
        LocalDB localDB = LocalDB.getInstance(application);
        courtDAO = localDB.courtDAO();
        allCourts = courtDAO.getAll();
        gameRepository = new GameRepository(application);
        remoteDB = new RemoteDB();
    }

    public LiveData<List<Court>> getAllCourts() {
        remoteDB.getAllCourts(this);
        return allCourts;
    }

    public LiveData<Court> getCourtById(String courtId) {
        return courtDAO.findByID(courtId);
    }

    public LiveData<CourtWithGames> getGamesAtCourt(String courtId) {
        gameRepository.getAllGames();
        return courtDAO.getGamesAtCourt(courtId);
    }

    public void insertCourt(final Court court) {
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                courtDAO.insert(court);
            }
        });
    }

    public void deleteAll() {
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                courtDAO.deleteAll();
            }
        });
    }

    public void deleteCourt(final Court court) {
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                courtDAO.delete(court);
            }
        });
    }
}
