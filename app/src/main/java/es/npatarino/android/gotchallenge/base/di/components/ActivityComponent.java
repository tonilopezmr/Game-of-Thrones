package es.npatarino.android.gotchallenge.base.di.components;

import android.content.Context;

import dagger.Component;
import es.npatarino.android.gotchallenge.base.di.ActivityScope;
import es.npatarino.android.gotchallenge.base.di.modules.ActivityModule;
import es.npatarino.android.gotchallenge.base.ui.messages.ErrorManager;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Context context();
    ErrorManager errorManager();
}
