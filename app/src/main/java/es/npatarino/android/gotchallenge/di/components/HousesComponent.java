package es.npatarino.android.gotchallenge.di.components;

import javax.inject.Named;

import dagger.Component;
import es.npatarino.android.gotchallenge.di.Activity;
import es.npatarino.android.gotchallenge.di.AppComponent;
import es.npatarino.android.gotchallenge.di.modules.ActivityModule;
import es.npatarino.android.gotchallenge.di.modules.HousesModule;
import es.npatarino.android.gotchallenge.domain.House;
import es.npatarino.android.gotchallenge.domain.datasource.local.HouseLocalDataSource;
import es.npatarino.android.gotchallenge.domain.datasource.remote.HouseRemoteDataSource;
import es.npatarino.android.gotchallenge.domain.interactor.common.GetListUseCase;
import es.npatarino.android.gotchallenge.domain.repository.HouseRepository;
import es.npatarino.android.gotchallenge.presenter.HouseListPresenter;
import es.npatarino.android.gotchallenge.view.fragment.HousesListFragment;

@Activity
@Component(dependencies = AppComponent.class, modules = {HousesModule.class, ActivityModule.class})
public interface HousesComponent extends ActivityComponent{

    void inject(HousesListFragment fragment);

    //datasource
    HouseRemoteDataSource houseRemoteDataSource();
    HouseLocalDataSource houseLocalDataSource();

    //repository
    HouseRepository gotHouseRepository();

    //usecase
    @Named("house") GetListUseCase<House> gotHouseListUseCase();

    //presenter
    HouseListPresenter gotHouseListPresenter();
}
