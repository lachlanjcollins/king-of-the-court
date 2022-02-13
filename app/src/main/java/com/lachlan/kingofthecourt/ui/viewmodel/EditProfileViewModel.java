package com.lachlan.kingofthecourt.ui.viewmodel;

import androidx.lifecycle.ViewModel;

import com.lachlan.kingofthecourt.data.database.RemoteDatabase;
import com.lachlan.kingofthecourt.data.entity.User;

public class EditProfileViewModel extends ViewModel {

    public void updateUser(User user, String fName, String lName, String position) {

        //@TODO: Delete
        RemoteDatabase db = new RemoteDatabase();

        user.setFirstName(fName);
        user.setLastName(lName);
        user.setPosition(position);

    }

}
