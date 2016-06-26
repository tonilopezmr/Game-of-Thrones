package es.npatarino.android.gotchallenge.characters.di;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.characters.domain.CharactersDomain;
import es.npatarino.android.gotchallenge.common.di.Activity;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.characters.domain.interactor.GetCharactersByHouseUseCase;
import es.npatarino.android.gotchallenge.common.interactor.GetListUseCase;
import es.npatarino.android.gotchallenge.characters.list.presenter.CharacterListPresenter;
import es.npatarino.android.gotchallenge.characters.list.presenter.CharacterListByHousePresenter;
import rx.Scheduler;


@Module
public class CharactersModule {


    @Provides
    @Activity
    public GetCharactersByHouseUseCase provideCharactersByHouseUseCase(@Named("executorThread") Scheduler executor,
                                                                       @Named("mainThread") Scheduler uiThread,
                                                                       CharactersDomain.Repository repository){
        return new GetCharactersByHouseUseCase(repository, uiThread, executor);
    }

    @Provides
    @Activity
    @Character
    public GetListUseCase<GoTCharacter> provideGotCharacterListUseCase(@Named("executorThread") Scheduler executor,
                                                                       @Named("mainThread") Scheduler uiThread,
                                                                       CharactersDomain.Repository repository){
        return new GetListUseCase<>(repository, uiThread, executor);
    }

    @Provides
    @Activity
    public CharacterListPresenter provideGotCharacterListPresenter(@Character GetListUseCase<GoTCharacter> characterGetListUseCase){
        return new CharacterListPresenter(characterGetListUseCase);
    }

    @Provides
    @Activity
    public CharacterListByHousePresenter provideGotCharacterListByHousePresenter(GetCharactersByHouseUseCase useCase){
        return new CharacterListByHousePresenter(useCase);
    }
}
