package es.npatarino.android.gotchallenge.di.modules;

import com.google.gson.Gson;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.data.GotCharacterRepositoryImp;
import es.npatarino.android.gotchallenge.data.source.local.CharacterLocalDataSourceImp;
import es.npatarino.android.gotchallenge.data.source.remote.CharacterRemoteDataSourceImp;
import es.npatarino.android.gotchallenge.data.source.remote.EndPoint;
import es.npatarino.android.gotchallenge.data.source.remote.JsonMapper;
import es.npatarino.android.gotchallenge.di.Activity;
import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.datasource.local.CharacterLocalDataSource;
import es.npatarino.android.gotchallenge.domain.datasource.remote.CharacterRemoteDataSource;
import es.npatarino.android.gotchallenge.domain.interactor.GetCharactersByHouseUseCase;
import es.npatarino.android.gotchallenge.domain.interactor.common.GetListUseCase;
import es.npatarino.android.gotchallenge.domain.repository.GotCharacterRepository;
import es.npatarino.android.gotchallenge.presenter.CharacterListPresenter;
import es.npatarino.android.gotchallenge.presenter.CharacterListPresenterImp;
import es.npatarino.android.gotchallenge.presenter.GotCharacterListByHousePresenter;
import es.npatarino.android.gotchallenge.presenter.GotCharacterListByHousePresenterImp;
import okhttp3.OkHttpClient;
import rx.Scheduler;

/**
 * @author Antonio López.
 */

@Module
public class CharactersModule {

    @Provides
    @Activity
    public JsonMapper provideGotCharacterJsonMapper(){
        return new JsonMapper(new Gson());
    }

    @Provides
    @Activity
    public CharacterLocalDataSource provideCharacterLocalDataSource() {
        return new CharacterLocalDataSourceImp();
    }

    @Provides
    @Activity
    public CharacterRemoteDataSource provideCharacterRemoteDataSource(OkHttpClient okHttpClient, EndPoint endPoint, JsonMapper jsonMapper) {
        return new CharacterRemoteDataSourceImp(jsonMapper, endPoint, okHttpClient);
    }

    @Provides
    @Activity
    public GotCharacterRepository provideGotCharacterRepository(CharacterRemoteDataSource remoteDataSource, CharacterLocalDataSource localDataSource) {
        return new GotCharacterRepositoryImp(remoteDataSource, localDataSource);
    }

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
