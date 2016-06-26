package es.npatarino.android.gotchallenge.houses.di;

import dagger.Component;
import es.npatarino.android.gotchallenge.common.di.Activity;
import es.npatarino.android.gotchallenge.common.di.components.ActivityComponent;
import es.npatarino.android.gotchallenge.common.di.components.AppComponent;
import es.npatarino.android.gotchallenge.common.di.modules.ActivityModule;
import es.npatarino.android.gotchallenge.common.interactor.GetListUseCase;
import es.npatarino.android.gotchallenge.houses.data.source.local.mapper.BddHouseMapper;
import es.npatarino.android.gotchallenge.houses.domain.Houses;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import es.npatarino.android.gotchallenge.houses.list.presenter.HouseListPresenter;
import es.npatarino.android.gotchallenge.houses.list.view.fragment.HousesListFragment;

@Activity
@Component(dependencies = AppComponent.class, modules = {HousesModule.class, ActivityModule.class})
public interface HousesComponent extends ActivityComponent {

    void inject(HousesListFragment fragment);

    BddHouseMapper provideBddHouseMapper();

    //datasource
    Houses.NetworkDataSource houseRemoteDataSource();
    Houses.LocalDataSource houseLocalDataSource();

    //repository
    Houses.Repository gotHouseRepository();

    //usecase
    @House
    GetListUseCase<GoTHouse> gotHouseListUseCase();

    //presenter
    HouseListPresenter gotHouseListPresenter();
}
