package es.npatarino.android.gotchallenge.common.di.activity;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.base.ui.messages.ErrorManager;
import es.npatarino.android.gotchallenge.base.ui.messages.SnackbarError;

@Module
public class ActivityModule {

    @Provides
    @ActivityScope
    public ErrorManager provideErrorManager() {
        return new SnackbarError();
    }
}
