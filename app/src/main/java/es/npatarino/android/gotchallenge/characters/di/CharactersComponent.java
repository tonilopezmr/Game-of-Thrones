package es.npatarino.android.gotchallenge.characters.di;

import javax.inject.Named;

import dagger.Component;
import es.npatarino.android.gotchallenge.characters.list.CharacterList;
import es.npatarino.android.gotchallenge.characters.list.view.fragment.CharacterListFragment;
import es.npatarino.android.gotchallenge.common.di.Activity;
import es.npatarino.android.gotchallenge.common.di.components.AppComponent;
import es.npatarino.android.gotchallenge.common.di.components.ActivityComponent;
import es.npatarino.android.gotchallenge.common.di.modules.ActivityModule;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.characters.domain.interactor.GetCharactersByHouseUseCase;
import es.npatarino.android.gotchallenge.common.interactor.GetListUseCase;
import es.npatarino.android.gotchallenge.characters.list.view.fragment.CharacterListByHouseFragment;

@Activity
@Component(dependencies = AppComponent.class, modules = {CharactersModule.class, ActivityModule.class})
public interface CharactersComponent extends ActivityComponent {

    void inject(CharacterListByHouseFragment fragment);
    void inject(CharacterListFragment fragment);


    //UseCase
    GetCharactersByHouseUseCase charactersByHouseUseCase();
    @Named("character") GetListUseCase<GoTCharacter> gotCharacterListUseCase();

    //Presenter
    CharacterList.Presenter gotCharacterListPresenter();
    CharacterList.ByHousePresenter gotCharacterListByHousePresenter();
}
