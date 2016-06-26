package es.npatarino.android.gotchallenge.houses.di;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.characters.domain.CharactersDomain;
import es.npatarino.android.gotchallenge.houses.data.HouseRepository;
import es.npatarino.android.gotchallenge.common.caching.TimeProvider;
import es.npatarino.android.gotchallenge.common.caching.strategy.TTLCachingStrategy;
import es.npatarino.android.gotchallenge.houses.data.source.local.HouseLocalDataSource;
import es.npatarino.android.gotchallenge.houses.data.source.local.mapper.BddHouseMapper;
import es.npatarino.android.gotchallenge.houses.data.source.network.HouseNetworkDataSource;
import es.npatarino.android.gotchallenge.common.di.Activity;
import es.npatarino.android.gotchallenge.houses.domain.Houses;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import es.npatarino.android.gotchallenge.common.interactor.GetListUseCase;
import es.npatarino.android.gotchallenge.houses.list.presenter.HouseListPresenter;
import rx.Scheduler;

@Module public class HousesModule {

    @Provides
    @Activity
    public BddHouseMapper provideBddHouseMapper(){
        return new BddHouseMapper();
    }

    @Provides
    @Activity
    public Houses.LocalDataSource provideHouseLocalDataSource(TTLCachingStrategy cachingStrategy,
                                                              TimeProvider timeProvider,
                                                              BddHouseMapper mapper){
        return new HouseLocalDataSource(cachingStrategy, timeProvider, mapper);
    }

    @Provides
    @Activity
    public Houses.NetworkDataSource provideHouseRemoteDataSource(CharactersDomain.NetworkDataSource characterNetworkDataSource){
        return new HouseNetworkDataSource(characterNetworkDataSource);
    }

    @Provides
    @Activity
    public Houses.Repository provideGotHouseRepository(Houses.NetworkDataSource networkDataSource, Houses.LocalDataSource localDataSource){
        return new HouseRepository(networkDataSource, localDataSource);
    }

    @Provides
    @Activity
    @House
    public GetListUseCase<GoTHouse> provideGotHouseListUseCase(@Named("executorThread") Scheduler executor,
                                                               @Named("mainThread") Scheduler uiThread,
                                                               Houses.Repository repository){
        return new GetListUseCase<>(repository, uiThread, executor);
    }

    @Provides
    @Activity
    public HouseListPresenter provideGotHouseListPresenter(@House GetListUseCase<GoTHouse> houseGetListUseCase){
        return new HouseListPresenter(houseGetListUseCase);
    }
}
