package es.npatarino.android.gotchallenge.houses.di;

import dagger.Component;
import es.npatarino.android.gotchallenge.base.di.ActivityScope;
import es.npatarino.android.gotchallenge.base.di.components.ActivityComponent;
import es.npatarino.android.gotchallenge.base.di.components.AppComponent;
import es.npatarino.android.gotchallenge.base.di.modules.ActivityModule;
import es.npatarino.android.gotchallenge.base.list.interactor.GetListUseCase;
import es.npatarino.android.gotchallenge.houses.data.source.local.mapper.BddHouseMapper;
import es.npatarino.android.gotchallenge.houses.domain.HousesDomain;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import es.npatarino.android.gotchallenge.houses.list.presenter.HouseListPresenter;
import es.npatarino.android.gotchallenge.houses.list.view.fragment.HousesListFragment;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {HousesModule.class, ActivityModule.class})
public interface HousesComponent extends ActivityComponent {

    void inject(HousesListFragment fragment);

    BddHouseMapper provideBddHouseMapper();

    //datasource
    HousesDomain.NetworkDataSource houseRemoteDataSource();

    HousesDomain.LocalDataSource houseLocalDataSource();

    //repository
    HousesDomain.Repository gotHouseRepository();

    //usecase
    @House
    GetListUseCase<GoTHouse> gotHouseListUseCase();

    //presenter
    HouseListPresenter gotHouseListPresenter();
}
