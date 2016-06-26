package es.npatarino.android.gotchallenge.di.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.characters.domain.Characters;
import es.npatarino.android.gotchallenge.houses.data.HouseRepository;
import es.npatarino.android.gotchallenge.common.caching.TimeProvider;
import es.npatarino.android.gotchallenge.common.caching.strategy.TTLCachingStrategy;
import es.npatarino.android.gotchallenge.houses.data.source.local.HouseLocalDataSource;
import es.npatarino.android.gotchallenge.houses.data.source.local.mapper.BddHouseMapper;
import es.npatarino.android.gotchallenge.houses.data.source.network.HouseNetworkDataSource;
import es.npatarino.android.gotchallenge.di.Activity;
import es.npatarino.android.gotchallenge.houses.domain.Houses;
import es.npatarino.android.gotchallenge.houses.domain.model.House;
import es.npatarino.android.gotchallenge.common.interactor.GetListUseCase;
import es.npatarino.android.gotchallenge.presenter.HouseListPresenter;
import es.npatarino.android.gotchallenge.presenter.HouseListPresenterImp;
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
    public Houses.NetworkDataSource provideHouseRemoteDataSource(Characters.NetworkDataSource characterNetworkDataSource){
        return new HouseNetworkDataSource(characterNetworkDataSource);
    }

    @Provides
    @Activity
    public Houses.Repository provideGotHouseRepository(Houses.NetworkDataSource networkDataSource, Houses.LocalDataSource localDataSource){
        return new HouseRepository(networkDataSource, localDataSource);
    }

    @Provides
    @Activity
    @Named("house")
    public GetListUseCase<House> provideGotHouseListUseCase(@Named("executorThread") Scheduler executor,
                                                            @Named("mainThread") Scheduler uiThread,
                                                            Houses.Repository repository){
        return new GetListUseCase<>(repository, uiThread, executor);
    }

    @Provides
    @Activity
    public HouseListPresenter provideGotHouseListPresenter(@Named("house") GetListUseCase<House> houseGetListUseCase){
        return new HouseListPresenterImp(houseGetListUseCase);
    }
}
