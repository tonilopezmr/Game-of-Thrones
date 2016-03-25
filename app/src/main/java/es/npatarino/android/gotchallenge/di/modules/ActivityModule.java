package es.npatarino.android.gotchallenge.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.di.Activity;

/**
 * @author Antonio López.
 */
@Module public class ActivityModule {
    private final Context mContext;

    public ActivityModule(Context mContext) {

        this.mContext = mContext;
    }

    @Provides
    @Activity
    Context provideActivityContext() {
        return mContext;
    }
}
