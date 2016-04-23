package es.npatarino.android.gotchallenge.di;

import com.google.gson.Gson;
import com.tonilopezmr.interactorexecutor.Executor;
import com.tonilopezmr.interactorexecutor.MainThread;
import com.tonilopezmr.interactorexecutor.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.data.GotCharacterJsonMapper;
import es.npatarino.android.gotchallenge.data.GotCharacterRepositoryImp;
import es.npatarino.android.gotchallenge.domain.repository.GotCharacterRepository;
import es.npatarino.android.gotchallenge.view.executor.MainThreadImp;
import okhttp3.OkHttpClient;

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

    @Provides
    @Singleton
    public MainThread provideMainThread(){
        return new MainThreadImp();
    }

    @Provides
    @Singleton
    public Executor provideExecutor(){
        return new ThreadExecutor();
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
