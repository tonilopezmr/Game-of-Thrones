package es.npatarino.android.gotchallenge.common.view.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import es.npatarino.android.gotchallenge.characters.list.view.fragment.CharacterListFragment;
import es.npatarino.android.gotchallenge.houses.list.view.fragment.HousesListFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new CharacterListFragment();
        } else {
            return new HousesListFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Characters";
            case 1:
                return "Houses";
            default:
                return "";
        }
    }
}