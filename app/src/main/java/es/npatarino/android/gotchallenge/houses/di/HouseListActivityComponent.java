package es.npatarino.android.gotchallenge.houses.di;

import dagger.Subcomponent;
import es.npatarino.android.gotchallenge.common.di.activity.ActivityScope;
import es.npatarino.android.gotchallenge.houses.list.view.fragment.HousesListFragment;

@ActivityScope
@Subcomponent(modules = HouseListActivityModule.class)
public interface HouseListActivityComponent {

    HousesListFragment inject(HousesListFragment housesListFragment);
}
