package com.lachlan.kingofthecourt.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.lachlan.kingofthecourt.data.dao.CourtDAO;
import com.lachlan.kingofthecourt.data.database.LocalDB;
import com.lachlan.kingofthecourt.data.database.RemoteDB;
import com.lachlan.kingofthecourt.data.entity.Court;

import java.util.List;

public class CourtRepository {
    private CourtDAO courtDAO;
    private LiveData<List<Court>> allCourts;
    private RemoteDB remoteDB;

    public CourtRepository(Application application) {
        LocalDB localDB = LocalDB.getInstance(application);
        courtDAO = localDB.courtDAO();
        allCourts = courtDAO.getAll();
        remoteDB = new RemoteDB();
    }

    public LiveData<List<Court>> getAllCourts() {
        remoteDB.getAllCourts(this);

        return allCourts;
    }

    public void insertCourt(final Court court){
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                courtDAO.insert(court);
            }
        });
    }

    public void deleteAll(){
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                courtDAO.deleteAll();
            }
        });
    }

    public void deleteCourt(final Court court){
        LocalDB.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                courtDAO.delete(court);
            }
        });
    }
}
