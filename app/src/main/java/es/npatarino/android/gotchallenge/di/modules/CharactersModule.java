package es.npatarino.android.gotchallenge.di.modules;

import com.tonilopezmr.interactorexecutor.Executor;
import com.tonilopezmr.interactorexecutor.MainThread;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.di.Activity;
import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GotHouseRepository.GotCharacterRepositoryImp;
import es.npatarino.android.gotchallenge.domain.interactor.GetCharactersByHouseUseCase;
import es.npatarino.android.gotchallenge.domain.interactor.common.GetListUseCase;
import es.npatarino.android.gotchallenge.domain.interactor.common.GetListUseCaseImp;
import es.npatarino.android.gotchallenge.presenter.GotListPresenterImp;

/**
 * @author Antonio López.
 */

@Module
public class CharactersModule {

    @Provides
    @Activity
    public GetCharactersByHouseUseCase provideCharactersByHouseUseCase(Executor executor, MainThread mainThread, GotCharacterRepositoryImp repository){
        return new GetCharactersByHouseUseCase(executor, mainThread, repository);
    }

    @Provides
    @Activity
    @Named("character")
    public GetListUseCase<GoTCharacter> provideGotCharacterListUseCase(Executor executor, MainThread mainThread, GotCharacterRepositoryImp repository){
        return new GetListUseCaseImp<>(executor, mainThread, repository);
    }

    @Provides
    @Activity
    public GotListPresenterImp<GoTCharacter> provideGotCharacterListPresenter(@Named("character")GetListUseCase<GoTCharacter> characterGetListUseCase){
        return new GotListPresenterImp<>(characterGetListUseCase);
    }
}
