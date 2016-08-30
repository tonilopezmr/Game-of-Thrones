package es.npatarino.android.gotchallenge.houses.di;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.base.list.usecases.GetListUseCase;
import es.npatarino.android.gotchallenge.common.di.ExecutorThread;
import es.npatarino.android.gotchallenge.common.di.UiThread;
import es.npatarino.android.gotchallenge.common.di.activity.ActivityModule;
import es.npatarino.android.gotchallenge.common.di.activity.ActivityScope;
import es.npatarino.android.gotchallenge.houses.domain.HousesDomain;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import es.npatarino.android.gotchallenge.houses.list.presenter.HouseListPresenter;
import rx.Scheduler;

@Module
public class HouseListActivityModule extends ActivityModule {

    @Provides
    @ActivityScope
    @House
    public GetListUseCase<GoTHouse> provideGotHouseListUseCase(@ExecutorThread Scheduler executor,
                                                               @UiThread Scheduler uiThread,
                                                               HousesDomain.Repository repository) {
        return new GetListUseCase<>(repository, uiThread, executor);
    }

    @Provides
    @ActivityScope
    public HouseListPresenter provideGotHouseListPresenter(@House GetListUseCase<GoTHouse> houseGetListUseCase) {
        return new HouseListPresenter(houseGetListUseCase);
    }

}
