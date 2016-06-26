package es.npatarino.android.gotchallenge.common.di.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.common.di.Activity;

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
