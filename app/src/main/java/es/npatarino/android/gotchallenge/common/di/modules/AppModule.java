package es.npatarino.android.gotchallenge.common.di.modules;

import android.content.Context;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.characters.data.CharacterRepository;
import es.npatarino.android.gotchallenge.characters.data.source.local.CharacterLocalDataSource;
import es.npatarino.android.gotchallenge.characters.data.source.local.mapper.BddGoTCharacterMapper;
import es.npatarino.android.gotchallenge.characters.data.source.network.CharacterNetworkDataSource;
import es.npatarino.android.gotchallenge.characters.data.source.network.mapper.CharacterJsonMapper;
import es.npatarino.android.gotchallenge.characters.domain.CharactersDomain;
import es.npatarino.android.gotchallenge.common.caching.TimeProvider;
import es.npatarino.android.gotchallenge.common.caching.strategy.TTLCachingStrategy;
import es.npatarino.android.gotchallenge.common.di.ExecutorThread;
import es.npatarino.android.gotchallenge.common.di.UiThread;
import es.npatarino.android.gotchallenge.common.network.EndPoint;
import okhttp3.OkHttpClient;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class AppModule {

    public static final String END_POINT = "https://raw.githubusercontent.com/tonilopezmr/Game-of-Thrones/master/app/src/test/resources/data.json";

    private Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public CharacterJsonMapper provideGotCharacterJsonMapper(){
        return new CharacterJsonMapper(new Gson());
    }

    @Provides
    public BddGoTCharacterMapper provideBddGotCharacterMapper(){
        return new BddGoTCharacterMapper();
    }

    @Provides
    @Singleton
    public TTLCachingStrategy provideCachingStrategy(){
        return new TTLCachingStrategy(2, TimeUnit.MINUTES);
    }

    @Provides
    @Singleton
    public TimeProvider provideTimeProvider(){
        return new TimeProvider(context);
    }

    @Provides
    @Singleton
    public CharactersDomain.LocalDataSource provideCharacterLocalDataSource(TTLCachingStrategy cachingStrategy,
                                                                            TimeProvider timeProvider,
                                                                            BddGoTCharacterMapper mapper) {
        return new CharacterLocalDataSource(cachingStrategy, timeProvider, mapper);
    }

    @Provides
    @Singleton
    public CharactersDomain.NetworkDataSource provideCharacterRemoteDataSource(OkHttpClient okHttpClient,
                                                                               EndPoint endPoint,
                                                                               CharacterJsonMapper characterJsonMapper) {
        return new CharacterNetworkDataSource(characterJsonMapper, endPoint, okHttpClient);
    }


    @Provides
    @Singleton
    public CharactersDomain.Repository provideGotCharacterRepository(CharactersDomain.NetworkDataSource networkDataSource,
                                                                     CharactersDomain.LocalDataSource localDataSource) {
        return new CharacterRepository(networkDataSource, localDataSource);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(){
        return new OkHttpClient();
    }

    @Provides @ExecutorThread
    public Scheduler provideMainThread(){
        return Schedulers.newThread();
    }

    @Provides @UiThread
    public Scheduler provideExecutor(){
        return AndroidSchedulers.mainThread();
    }

    @Provides
    public EndPoint provideEndPoint(){
        return new EndPoint(END_POINT);
    }
}
