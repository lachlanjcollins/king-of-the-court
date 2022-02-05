package com.lachlan.kingofthecourt;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lachlan.kingofthecourt.model.User;
import com.lachlan.kingofthecourt.ui.profile.EditProfileFragment;

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
        Database db = new Database();

        User user = mUser.getValue();

        user.setFirstName(fName);
        user.setLastName(lName);
        user.setPosition(position);

        db.updateUser(editProfileFragment, user);
    }
}
