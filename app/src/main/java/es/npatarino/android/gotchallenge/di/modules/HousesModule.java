package es.npatarino.android.gotchallenge.di.modules;

import com.google.gson.Gson;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.data.GotHouseRepositoryImp;
import es.npatarino.android.gotchallenge.data.source.local.HouseLocalDataSourceImp;
import es.npatarino.android.gotchallenge.data.source.remote.CharacterRemoteDataSourceImp;
import es.npatarino.android.gotchallenge.data.source.remote.EndPoint;
import es.npatarino.android.gotchallenge.data.source.remote.HouseRemoteDataSourceImp;
import es.npatarino.android.gotchallenge.data.source.remote.JsonMapper;
import es.npatarino.android.gotchallenge.di.Activity;
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.domain.datasource.local.HouseLocalDataSource;
import es.npatarino.android.gotchallenge.domain.datasource.remote.HouseRemoteDataSource;
import es.npatarino.android.gotchallenge.domain.interactor.common.GetListUseCase;
import es.npatarino.android.gotchallenge.domain.repository.GotHouseRepository;
import es.npatarino.android.gotchallenge.presenter.HouseListPresenter;
import es.npatarino.android.gotchallenge.presenter.HouseListPresenterImp;
import okhttp3.OkHttpClient;
import rx.Scheduler;

/**
 * @author Antonio López.
 */
@Module public class HousesModule {

    @Provides
    @Activity
    public HouseLocalDataSource provideHouseLocalDataSource(){
        return new HouseLocalDataSourceImp();
    }

    @Provides
    @Activity
    public HouseRemoteDataSource provideHouseRemoteDataSource(OkHttpClient okHttpClient, EndPoint endPoint){
        return new HouseRemoteDataSourceImp(new CharacterRemoteDataSourceImp(new JsonMapper(new Gson()), endPoint, okHttpClient));
    }

    @Provides
    @Activity
    public GotHouseRepository provideGotHouseRepository(HouseRemoteDataSource remoteDataSource, HouseLocalDataSource localDataSource){
        return new GotHouseRepositoryImp(remoteDataSource, localDataSource);
    }

    @Provides
    @Activity
    @Named("house")
    public GetListUseCase<GoTHouse> provideGotHouseListUseCase(@Named("executorThread") Scheduler executor,
                                                               @Named("mainThread") Scheduler uiThread,
                                                               GotHouseRepository repository){
        return new GetListUseCase<>(repository, uiThread, executor);
    }

    @Provides
    @Activity
    public HouseListPresenter provideGotHouseListPresenter(@Named("house") GetListUseCase<GoTHouse> houseGetListUseCase){
        return new HouseListPresenterImp(houseGetListUseCase);
    }
}
