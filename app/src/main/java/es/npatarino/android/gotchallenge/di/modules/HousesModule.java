package es.npatarino.android.gotchallenge.di.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.data.HouseRepositoryImp;
import es.npatarino.android.gotchallenge.data.caching.TimeProvider;
import es.npatarino.android.gotchallenge.data.caching.strategy.TTLCachingStrategy;
import es.npatarino.android.gotchallenge.data.source.local.HouseLocalDataSourceImp;
import es.npatarino.android.gotchallenge.data.source.local.entities.mapper.BddHouseMapper;
import es.npatarino.android.gotchallenge.data.source.remote.HouseRemoteDataSourceImp;
import es.npatarino.android.gotchallenge.di.Activity;
import es.npatarino.android.gotchallenge.domain.House;
import es.npatarino.android.gotchallenge.domain.datasource.local.HouseLocalDataSource;
import es.npatarino.android.gotchallenge.domain.datasource.remote.CharacterRemoteDataSource;
import es.npatarino.android.gotchallenge.domain.datasource.remote.HouseRemoteDataSource;
import es.npatarino.android.gotchallenge.domain.interactor.common.GetListUseCase;
import es.npatarino.android.gotchallenge.domain.repository.HouseRepository;
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
    public HouseLocalDataSource provideHouseLocalDataSource(TTLCachingStrategy cachingStrategy,
                                                            TimeProvider timeProvider,
                                                            BddHouseMapper mapper){
        return new HouseLocalDataSourceImp(cachingStrategy, timeProvider, mapper);
    }

    @Provides
    @Activity
    public HouseRemoteDataSource provideHouseRemoteDataSource(CharacterRemoteDataSource characterRemoteDataSource){
        return new HouseRemoteDataSourceImp(characterRemoteDataSource);
    }

    @Provides
    @Activity
    public HouseRepository provideGotHouseRepository(HouseRemoteDataSource remoteDataSource, HouseLocalDataSource localDataSource){
        return new HouseRepositoryImp(remoteDataSource, localDataSource);
    }

    @Provides
    @Activity
    @Named("house")
    public GetListUseCase<House> provideGotHouseListUseCase(@Named("executorThread") Scheduler executor,
                                                            @Named("mainThread") Scheduler uiThread,
                                                            HouseRepository repository){
        return new GetListUseCase<>(repository, uiThread, executor);
    }

    @Provides
    @Activity
    public HouseListPresenter provideGotHouseListPresenter(@Named("house") GetListUseCase<House> houseGetListUseCase){
        return new HouseListPresenterImp(houseGetListUseCase);
    }
}
