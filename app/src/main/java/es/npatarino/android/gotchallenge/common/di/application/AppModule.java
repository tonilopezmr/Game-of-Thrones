package es.npatarino.android.gotchallenge.common.di.application;

import android.content.Context;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.base.caching.TimeProvider;
import es.npatarino.android.gotchallenge.base.caching.strategy.TTLCachingStrategy;
import es.npatarino.android.gotchallenge.base.network.EndPoint;
import es.npatarino.android.gotchallenge.characters.data.source.network.mapper.CharacterJsonMapper;
import es.npatarino.android.gotchallenge.common.di.ExecutorThread;
import es.npatarino.android.gotchallenge.common.di.UiThread;
import okhttp3.OkHttpClient;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

@Module
public class AppModule {

    public static final String END_POINT =
            "https://raw.githubusercontent.com/tonilopezmr/Game-of-Thrones/master/app/src/test/resources/data.json";

    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    public Context provideAppContext() {
        return context;
    }

    @Provides
    @Singleton
    public TTLCachingStrategy provideCachingStrategy() {
        return new TTLCachingStrategy(2, TimeUnit.MINUTES);
    }

    @Provides
    @Singleton
    public TimeProvider provideTimeProvider() {
        return new TimeProvider(context);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(60 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(60 * 1000, TimeUnit.MILLISECONDS);

        return builder.build();
    }

    @Provides
    @Singleton
    public CharacterJsonMapper provideGotCharacterJsonMapper(Gson gson) {
        return new CharacterJsonMapper(gson);
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new Gson();
    }

    @Provides
    @ExecutorThread
    public Scheduler provideMainThread() {
        return Schedulers.newThread();
    }

    @Provides
    @UiThread
    public Scheduler provideExecutor() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    public EndPoint provideEndPoint() {
        return new EndPoint(END_POINT);
    }
}
