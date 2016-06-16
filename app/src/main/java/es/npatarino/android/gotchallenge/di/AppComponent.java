package es.npatarino.android.gotchallenge.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import es.npatarino.android.gotchallenge.data.caching.TimeProvider;
import es.npatarino.android.gotchallenge.data.caching.strategy.TTLCachingStrategy;
import es.npatarino.android.gotchallenge.data.source.local.entities.mapper.BddGoTCharacterMapper;
import es.npatarino.android.gotchallenge.data.source.remote.EndPoint;
import es.npatarino.android.gotchallenge.data.source.remote.JsonMapper;
import es.npatarino.android.gotchallenge.domain.datasource.local.CharacterLocalDataSource;
import es.npatarino.android.gotchallenge.domain.datasource.remote.CharacterRemoteDataSource;
import es.npatarino.android.gotchallenge.domain.repository.CharacterRepository;
import okhttp3.OkHttpClient;
import rx.Scheduler;

/**
 * @author Antonio López.
 */
@Singleton @Component(modules = AppModule.class)
public interface AppComponent {

    //repository for DaggerMock limitations
    CharacterRepository gotCharacterRepository();

    JsonMapper gotCharacterJsonMapper();
    BddGoTCharacterMapper bddGotCharacterMapper();
    TTLCachingStrategy cachingStrategy();
    TimeProvider timeProvider();

    //datasource
    CharacterRemoteDataSource characterRemoteDataSource();
    CharacterLocalDataSource characterLocalDataSource();


    @Named("executorThread") Scheduler executorThread();
    @Named("mainThread") Scheduler mainThread();
    OkHttpClient okHttpClient();
    EndPoint endPoint();
}
