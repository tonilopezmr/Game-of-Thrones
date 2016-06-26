package es.npatarino.android.gotchallenge.characters.di;

import dagger.Component;
import es.npatarino.android.gotchallenge.characters.domain.interactor.GetCharactersByHouseUseCase;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.characters.list.presenter.CharacterListByHousePresenter;
import es.npatarino.android.gotchallenge.characters.list.presenter.CharacterListPresenter;
import es.npatarino.android.gotchallenge.characters.list.view.fragment.CharacterListByHouseFragment;
import es.npatarino.android.gotchallenge.characters.list.view.fragment.CharacterListFragment;
import es.npatarino.android.gotchallenge.common.di.Activity;
import es.npatarino.android.gotchallenge.common.di.components.ActivityComponent;
import es.npatarino.android.gotchallenge.common.di.components.AppComponent;
import es.npatarino.android.gotchallenge.common.di.modules.ActivityModule;
import es.npatarino.android.gotchallenge.common.interactor.GetListUseCase;

@Activity
@Component(dependencies = AppComponent.class, modules = {CharactersModule.class, ActivityModule.class})
public interface CharactersComponent extends ActivityComponent {

    void inject(CharacterListByHouseFragment fragment);
    void inject(CharacterListFragment fragment);


    //UseCase
    GetCharactersByHouseUseCase charactersByHouseUseCase();
    @Character
    GetListUseCase<GoTCharacter> gotCharacterListUseCase();

    //Presenter
    CharacterListPresenter gotCharacterListPresenter();
    CharacterListByHousePresenter gotCharacterListByHousePresenter();
}
