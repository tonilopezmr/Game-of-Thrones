package es.npatarino.android.gotchallenge.houses.di;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.characters.domain.CharactersDomain;
import es.npatarino.android.gotchallenge.base.caching.TimeProvider;
import es.npatarino.android.gotchallenge.base.caching.strategy.TTLCachingStrategy;
import es.npatarino.android.gotchallenge.base.di.ActivityScope;
import es.npatarino.android.gotchallenge.base.di.ExecutorThread;
import es.npatarino.android.gotchallenge.base.di.UiThread;
import es.npatarino.android.gotchallenge.base.interactor.GetListUseCase;
import es.npatarino.android.gotchallenge.houses.data.HouseRepository;
import es.npatarino.android.gotchallenge.houses.data.source.local.HouseLocalDataSource;
import es.npatarino.android.gotchallenge.houses.data.source.local.mapper.BddHouseMapper;
import es.npatarino.android.gotchallenge.houses.data.source.network.HouseNetworkDataSource;
import es.npatarino.android.gotchallenge.houses.domain.HousesDomain;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import es.npatarino.android.gotchallenge.houses.list.presenter.HouseListPresenter;
import rx.Scheduler;

@Module public class HousesModule {

    @Provides
    @ActivityScope
    public BddHouseMapper provideBddHouseMapper(){
        return new BddHouseMapper();
    }

    @Provides
    @ActivityScope
    public HousesDomain.LocalDataSource provideHouseLocalDataSource(TTLCachingStrategy cachingStrategy,
                                                                    TimeProvider timeProvider,
                                                                    BddHouseMapper mapper){
        return new HouseLocalDataSource(cachingStrategy, timeProvider, mapper);
    }

    @Provides
    @ActivityScope
    public HousesDomain.NetworkDataSource provideHouseRemoteDataSource(CharactersDomain.NetworkDataSource characterNetworkDataSource){
        return new HouseNetworkDataSource(characterNetworkDataSource);
    }

    @Provides
    @ActivityScope
    public HousesDomain.Repository provideGotHouseRepository(HousesDomain.NetworkDataSource networkDataSource, HousesDomain.LocalDataSource localDataSource){
        return new HouseRepository(networkDataSource, localDataSource);
    }

    @Provides
    @ActivityScope
    @House
    public GetListUseCase<GoTHouse> provideGotHouseListUseCase(@ExecutorThread Scheduler executor,
                                                               @UiThread Scheduler uiThread,
                                                               HousesDomain.Repository repository){
        return new GetListUseCase<>(repository, uiThread, executor);
    }

    @Provides
    @ActivityScope
    public HouseListPresenter provideGotHouseListPresenter(@House GetListUseCase<GoTHouse> houseGetListUseCase){
        return new HouseListPresenter(houseGetListUseCase);
    }
}
