package com.lachlan.kingofthecourt.ui.adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.lachlan.kingofthecourt.fragments.LoginFragment;
import com.lachlan.kingofthecourt.fragments.SignupFragment;

public class PagerAdapter extends FragmentStateAdapter {

    public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new LoginFragment();
        else
            return new SignupFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
