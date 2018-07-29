package hariharan.theroboticlabs.com.wirechat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by hariharan on 7/21/18.
 */

public class SectionsPageAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    private ArrayList<String> fragmentTitles;

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragmentTitles = new ArrayList<>();
    }

    public void add(Fragment fragment, String title) {
        fragments.add(fragment);
        fragmentTitles.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
