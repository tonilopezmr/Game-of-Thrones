package es.npatarino.android.gotchallenge.di;

import android.content.Context;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.characters.data.source.network.CharacterNetworkDataSourceImp;
import es.npatarino.android.gotchallenge.characters.data.source.network.mapper.CharacterJsonMapper;
import es.npatarino.android.gotchallenge.characters.domain.Characters;
import es.npatarino.android.gotchallenge.characters.data.CharacterRepository;
import es.npatarino.android.gotchallenge.common.caching.TimeProvider;
import es.npatarino.android.gotchallenge.common.caching.strategy.TTLCachingStrategy;
import es.npatarino.android.gotchallenge.characters.data.source.local.CharacterLocalDataSource;
import es.npatarino.android.gotchallenge.characters.data.source.local.mapper.BddGoTCharacterMapper;
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
    public Characters.LocalDataSource provideCharacterLocalDataSource(TTLCachingStrategy cachingStrategy,
                                                                      TimeProvider timeProvider,
                                                                      BddGoTCharacterMapper mapper) {
        return new CharacterLocalDataSource(cachingStrategy, timeProvider, mapper);
    }

    @Provides
    @Singleton
    public Characters.NetworkDataSource provideCharacterRemoteDataSource(OkHttpClient okHttpClient,
                                                                         EndPoint endPoint,
                                                                         CharacterJsonMapper characterJsonMapper) {
        return new CharacterNetworkDataSourceImp(characterJsonMapper, endPoint, okHttpClient);
    }


    @Provides
    @Singleton
    public Characters.Repository provideGotCharacterRepository(Characters.NetworkDataSource networkDataSource,
                                                               Characters.LocalDataSource localDataSource) {
        return new CharacterRepository(networkDataSource, localDataSource);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(){
        return new OkHttpClient();
    }

    @Provides @Named("executorThread")
    public Scheduler provideMainThread(){
        return Schedulers.newThread();
    }

    @Provides @Named("mainThread")
    public Scheduler provideExecutor(){
        return AndroidSchedulers.mainThread();
    }

    @Provides
    public EndPoint provideEndPoint(){
        return new EndPoint(END_POINT);
    }
}
