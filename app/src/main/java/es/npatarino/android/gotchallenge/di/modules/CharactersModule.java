package es.npatarino.android.gotchallenge.di.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.di.Activity;
import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.interactor.GetCharactersByHouseUseCase;
import es.npatarino.android.gotchallenge.domain.interactor.common.GetListUseCase;
import es.npatarino.android.gotchallenge.domain.repository.GotCharacterRepository;
import es.npatarino.android.gotchallenge.presenter.CharacterListPresenter;
import es.npatarino.android.gotchallenge.presenter.CharacterListPresenterImp;
import es.npatarino.android.gotchallenge.presenter.GotCharacterListByHousePresenter;
import es.npatarino.android.gotchallenge.presenter.GotCharacterListByHousePresenterImp;
import rx.Scheduler;

/**
 * @author Antonio López.
 */

@Module
public class CharactersModule {


    @Provides
    @Activity
    public GetCharactersByHouseUseCase provideCharactersByHouseUseCase(@Named("executorThread") Scheduler executor,
                                                                       @Named("mainThread") Scheduler uiThread,
                                                                       GotCharacterRepository repository){
        return new GetCharactersByHouseUseCase(repository, uiThread, executor);
    }

    @Provides
    @Activity
    @Named("character")
    public GetListUseCase<GoTCharacter> provideGotCharacterListUseCase(@Named("executorThread") Scheduler executor,
                                                                       @Named("mainThread") Scheduler uiThread,
                                                                       GotCharacterRepository repository){
        return new GetListUseCase<>(repository, uiThread, executor);
    }

    @Provides
    @Activity
    public CharacterListPresenter provideGotCharacterListPresenter(@Named("character")GetListUseCase<GoTCharacter> characterGetListUseCase){
        return new CharacterListPresenterImp(characterGetListUseCase);
    }

    @Provides
    @Activity
    public GotCharacterListByHousePresenter provideGotCharacterListByHousePresenter(GetCharactersByHouseUseCase useCase){
        return new GotCharacterListByHousePresenterImp(useCase);
    }
}
