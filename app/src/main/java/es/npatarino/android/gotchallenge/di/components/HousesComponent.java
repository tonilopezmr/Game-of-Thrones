package es.npatarino.android.gotchallenge.di.components;

import javax.inject.Named;

import dagger.Component;
import es.npatarino.android.gotchallenge.di.Activity;
import es.npatarino.android.gotchallenge.di.AppModule;
import es.npatarino.android.gotchallenge.di.modules.ActivityModule;
import es.npatarino.android.gotchallenge.di.modules.HousesModule;
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.domain.GotHouseRepository.GotHouseRepositoryImp;
import es.npatarino.android.gotchallenge.domain.interactor.common.GetListUseCase;
import es.npatarino.android.gotchallenge.presenter.GotListPresenterImp;
import es.npatarino.android.gotchallenge.view.fragment.GoTHousesListFragment;

/**
 * @author Antonio López.
 */
@Activity
@Component(dependencies = AppModule.class, modules = {HousesModule.class, ActivityModule.class})
public interface HousesComponent extends ActivityComponent{

    void inject(GoTHousesListFragment fragment);

    GotHouseRepositoryImp gotHouseRepository();
    @Named("house") GetListUseCase<GoTHouse> gotHouseListUseCase();
    GotListPresenterImp<GoTHouse> gotHouseListPresenter();
}
