package es.npatarino.android.gotchallenge.di.components;

import javax.inject.Named;

import dagger.Component;
import es.npatarino.android.gotchallenge.di.Activity;
import es.npatarino.android.gotchallenge.di.AppComponent;
import es.npatarino.android.gotchallenge.di.modules.ActivityModule;
import es.npatarino.android.gotchallenge.di.modules.CharactersModule;
import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.interactor.GetCharactersByHouseUseCase;
import es.npatarino.android.gotchallenge.domain.interactor.common.GetListUseCase;
import es.npatarino.android.gotchallenge.presenter.GotListPresenterImp;
import es.npatarino.android.gotchallenge.view.fragment.GoTListFragment;
import es.npatarino.android.gotchallenge.view.fragment.GotCharacterListByHouseFragment;

/**
 * @author Antonio López.
 */
@Activity
@Component(dependencies = AppComponent.class, modules = {CharactersModule.class, ActivityModule.class})
public interface CharactersComponent extends ActivityComponent{

    void inject(GotCharacterListByHouseFragment fragment);
    void inject(GoTListFragment fragment);

    GetCharactersByHouseUseCase charactersByHouseUseCase();
    @Named("character") GetListUseCase<GoTCharacter> gotCharacterListUseCase();
    GotListPresenterImp<GoTCharacter> gotCharacterListPresenter();
}
