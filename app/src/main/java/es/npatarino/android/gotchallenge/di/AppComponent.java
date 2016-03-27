package es.npatarino.android.gotchallenge.di;

import com.tonilopezmr.interactorexecutor.Executor;
import com.tonilopezmr.interactorexecutor.MainThread;

import javax.inject.Singleton;

import dagger.Component;
import es.npatarino.android.gotchallenge.domain.repository.GotCharacterRepositoryImp;
import okhttp3.OkHttpClient;

/**
 * @author Antonio López.
 */
@Singleton @Component(modules = AppModule.class)
public interface AppComponent {

    Executor executor();
    MainThread mainThread();
    OkHttpClient okHttpClient();
    GotCharacterRepositoryImp gotCharacterRepository();
}
