package es.npatarino.android.gotchallenge.base.di.modules;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.base.di.ActivityScope;
import es.npatarino.android.gotchallenge.base.ui.messages.ErrorManager;
import es.npatarino.android.gotchallenge.base.ui.messages.SnackbarError;

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

    @Provides
    @ActivityScope
    ErrorManager provideErrorManager() {
        return new SnackbarError();
    }
}
