package es.npatarino.android.gotchallenge.base.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.base.di.ActivityScope;

@Module public class ActivityModule {
    private final Context mContext;

    public ActivityModule(Context mContext) {

        this.mContext = mContext;
    }

    @Provides
    @ActivityScope
    Context provideActivityContext() {
        return mContext;
    }
}
