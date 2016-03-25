package es.npatarino.android.gotchallenge.di;

import com.tonilopezmr.interactorexecutor.Executor;
import com.tonilopezmr.interactorexecutor.MainThread;
import com.tonilopezmr.interactorexecutor.ThreadExecutor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.domain.GotHouseRepository.GotCharacterRepositoryImp;
import es.npatarino.android.gotchallenge.view.executor.MainThreadImp;
import okhttp3.OkHttpClient;

/**
 * @author Antonio López.
 */
@Module
public class AppModule {

    private static final String END_POINT = "http://ec2-52-18-202-124.eu-west-1.compute.amazonaws.com:3000";

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
    public GotCharacterRepositoryImp provideGotCharacterRepository(OkHttpClient okHttpClient) {
        return new GotCharacterRepositoryImp(okHttpClient, END_POINT);
    }
}
