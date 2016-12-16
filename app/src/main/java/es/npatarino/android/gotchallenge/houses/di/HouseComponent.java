package es.npatarino.android.gotchallenge.houses.di;

import dagger.Subcomponent;

@HouseScope
@Subcomponent(modules = HouseModule.class)
public interface HouseComponent {

  HouseListActivityComponent plus(HouseListActivityModule activityModule);
}
