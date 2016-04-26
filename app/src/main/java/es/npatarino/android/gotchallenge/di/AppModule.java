package es.npatarino.android.gotchallenge.di;

import com.google.gson.Gson;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.data.GotCharacterJsonMapper;
import es.npatarino.android.gotchallenge.data.GotCharacterRepositoryImp;
import es.npatarino.android.gotchallenge.domain.repository.GotCharacterRepository;
import okhttp3.OkHttpClient;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Antonio López.
 */
@Module
public class AppModule {

    private static final String END_POINT = "https://raw.githubusercontent.com/tonilopezmr/Game-of-Thrones/master/app/src/test/resources/data.json";

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
    @Singleton
    public GotCharacterJsonMapper provideGotCharacterJsonMapper(){
        return new GotCharacterJsonMapper(new Gson());
    }

    @Provides
    @Singleton
    public GotCharacterRepository provideGotCharacterRepository(OkHttpClient okHttpClient, GotCharacterJsonMapper jsonMapper) {
        return new GotCharacterRepositoryImp(okHttpClient, END_POINT, jsonMapper);
    }
}
