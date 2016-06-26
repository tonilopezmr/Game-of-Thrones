package es.npatarino.android.gotchallenge.di.components;

import javax.inject.Named;

import dagger.Component;
import es.npatarino.android.gotchallenge.houses.data.source.local.mapper.BddHouseMapper;
import es.npatarino.android.gotchallenge.di.Activity;
import es.npatarino.android.gotchallenge.di.AppComponent;
import es.npatarino.android.gotchallenge.di.modules.ActivityModule;
import es.npatarino.android.gotchallenge.di.modules.HousesModule;
import es.npatarino.android.gotchallenge.houses.domain.Houses;
import es.npatarino.android.gotchallenge.houses.domain.model.House;
import es.npatarino.android.gotchallenge.common.interactor.GetListUseCase;
import es.npatarino.android.gotchallenge.houses.list.HouseList;
import es.npatarino.android.gotchallenge.houses.list.view.fragment.HousesListFragment;

@Activity
@Component(dependencies = AppComponent.class, modules = {HousesModule.class, ActivityModule.class})
public interface HousesComponent extends ActivityComponent{

    void inject(HousesListFragment fragment);

    BddHouseMapper provideBddHouseMapper();

    //datasource
    Houses.NetworkDataSource houseRemoteDataSource();
    Houses.LocalDataSource houseLocalDataSource();

    //repository
    Houses.Repository gotHouseRepository();

    //usecase
    @Named("house") GetListUseCase<House> gotHouseListUseCase();

    //presenter
    HouseList.Presenter gotHouseListPresenter();
}
