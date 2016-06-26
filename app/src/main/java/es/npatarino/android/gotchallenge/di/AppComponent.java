package es.npatarino.android.gotchallenge.di;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;
import es.npatarino.android.gotchallenge.characters.data.source.network.mapper.CharacterJsonMapper;
import es.npatarino.android.gotchallenge.characters.domain.Characters;
import es.npatarino.android.gotchallenge.common.caching.TimeProvider;
import es.npatarino.android.gotchallenge.common.caching.strategy.TTLCachingStrategy;
import es.npatarino.android.gotchallenge.characters.data.source.local.mapper.BddGoTCharacterMapper;
import es.npatarino.android.gotchallenge.common.network.EndPoint;
import okhttp3.OkHttpClient;
import rx.Scheduler;

@Singleton @Component(modules = AppModule.class)
public interface AppComponent {

    //repository for DaggerMock limitations
    Characters.Repository gotCharacterRepository();

    CharacterJsonMapper gotCharacterJsonMapper();
    BddGoTCharacterMapper bddGotCharacterMapper();
    TTLCachingStrategy cachingStrategy();
    TimeProvider timeProvider();

    //datasource
    Characters.NetworkDataSource characterRemoteDataSource();
    Characters.LocalDataSource characterLocalDataSource();


    @Named("executorThread") Scheduler executorThread();
    @Named("mainThread") Scheduler mainThread();
    OkHttpClient okHttpClient();
    EndPoint endPoint();
}
