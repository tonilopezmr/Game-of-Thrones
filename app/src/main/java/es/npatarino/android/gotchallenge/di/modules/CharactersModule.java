package es.npatarino.android.gotchallenge.di.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.characters.domain.Characters;
import es.npatarino.android.gotchallenge.di.Activity;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.characters.domain.interactor.GetCharactersByHouseUseCase;
import es.npatarino.android.gotchallenge.common.interactor.GetListUseCase;
import es.npatarino.android.gotchallenge.presenter.CharacterListPresenter;
import es.npatarino.android.gotchallenge.presenter.CharacterListPresenterImp;
import es.npatarino.android.gotchallenge.presenter.CharacterListByHousePresenter;
import es.npatarino.android.gotchallenge.presenter.CharacterListByHousePresenterImp;
import rx.Scheduler;


@Module
public class CharactersModule {


    @Provides
    @Activity
    public GetCharactersByHouseUseCase provideCharactersByHouseUseCase(@Named("executorThread") Scheduler executor,
                                                                       @Named("mainThread") Scheduler uiThread,
                                                                       Characters.Repository repository){
        return new GetCharactersByHouseUseCase(repository, uiThread, executor);
    }

    @Provides
    @Activity
    @Named("character")
    public GetListUseCase<GoTCharacter> provideGotCharacterListUseCase(@Named("executorThread") Scheduler executor,
                                                                       @Named("mainThread") Scheduler uiThread,
                                                                       Characters.Repository repository){
        return new GetListUseCase<>(repository, uiThread, executor);
    }

    @Provides
    @Activity
    public CharacterListPresenter provideGotCharacterListPresenter(@Named("character")GetListUseCase<GoTCharacter> characterGetListUseCase){
        return new CharacterListPresenterImp(characterGetListUseCase);
    }

    @Provides
    @Activity
    public CharacterListByHousePresenter provideGotCharacterListByHousePresenter(GetCharactersByHouseUseCase useCase){
        return new CharacterListByHousePresenterImp(useCase);
    }
}
