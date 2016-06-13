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
import es.npatarino.android.gotchallenge.presenter.CharacterListPresenter;
import es.npatarino.android.gotchallenge.presenter.CharacterListByHousePresenter;
import es.npatarino.android.gotchallenge.view.fragment.CharacterListByHouseFragment;
import es.npatarino.android.gotchallenge.view.fragment.ListFragment;

@Activity
@Component(dependencies = AppComponent.class, modules = {CharactersModule.class, ActivityModule.class})
public interface CharactersComponent extends ActivityComponent{

    void inject(CharacterListByHouseFragment fragment);
    void inject(ListFragment fragment);


    //UseCase
    GetCharactersByHouseUseCase charactersByHouseUseCase();
    @Named("character") GetListUseCase<GoTCharacter> gotCharacterListUseCase();

    //Presenter
    CharacterListPresenter gotCharacterListPresenter();
    CharacterListByHousePresenter gotCharacterListByHousePresenter();
}
