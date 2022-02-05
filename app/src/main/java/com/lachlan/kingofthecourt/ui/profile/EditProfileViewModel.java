package com.lachlan.kingofthecourt.ui.profile;

import androidx.lifecycle.ViewModel;

import com.lachlan.kingofthecourt.Database;
import com.lachlan.kingofthecourt.model.User;

public class EditProfileViewModel extends ViewModel {

    public void updateUser(User user, String fName, String lName, String position) {

        //@TODO: Delete
        Database db = new Database();

        user.setFirstName(fName);
        user.setLastName(lName);
        user.setPosition(position);

    }

}
