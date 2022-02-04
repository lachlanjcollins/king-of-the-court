package com.lachlan.kingofthecourt.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileViewModel extends ViewModel {

    private FirebaseUser user;
    private MutableLiveData<String> mText;
    private MutableLiveData<String> email;

    public ProfileViewModel() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        mText = new MutableLiveData<>();
        email = new MutableLiveData<>();

        // @TODO: Redo the below logic within an "input details" page on first log in.

        if (user != null) {
            if (user.getEmail() != null) {
                email.setValue(user.getEmail());
            } else
                email.setValue(user.getProviderData().get(0).getEmail());
        }
        mText.setValue("This is profile fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<String> getEmail() {
        return email;
    }
}