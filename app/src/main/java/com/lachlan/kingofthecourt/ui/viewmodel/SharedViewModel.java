package com.lachlan.kingofthecourt.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lachlan.kingofthecourt.data.database.RemoteDatabase;
import com.lachlan.kingofthecourt.data.entity.User;
import com.lachlan.kingofthecourt.fragments.EditProfileFragment;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<User> mUser;

    public SharedViewModel() {
        mUser = new MutableLiveData<>();
    }

    public void setUser(User user) {
        mUser.setValue(user);
    }

    public LiveData<User> getUser() {
        return mUser;
    }

    public void updateUser(EditProfileFragment editProfileFragment, String fName, String lName, String position) {
        RemoteDatabase db = new RemoteDatabase();

        User user = mUser.getValue();

        user.setFirstName(fName);
        user.setLastName(lName);
        user.setPosition(position);

        db.updateUser(editProfileFragment, user);
    }
}
