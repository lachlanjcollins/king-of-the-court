package com.lachlan.kingofthecourt;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lachlan.kingofthecourt.model.User;

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
}
