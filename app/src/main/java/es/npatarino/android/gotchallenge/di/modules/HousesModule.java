package es.npatarino.android.gotchallenge.di.modules;

import com.tonilopezmr.interactorexecutor.Executor;
import com.tonilopezmr.interactorexecutor.MainThread;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.di.Activity;
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.domain.GotHouseRepository.GotCharacterRepositoryImp;
import es.npatarino.android.gotchallenge.domain.GotHouseRepository.GotHouseRepositoryImp;
import es.npatarino.android.gotchallenge.domain.interactor.common.GetListUseCase;
import es.npatarino.android.gotchallenge.domain.interactor.common.GetListUseCaseImp;
import es.npatarino.android.gotchallenge.presenter.HouseListPresenter;
import es.npatarino.android.gotchallenge.presenter.HouseListPresenterImp;

/**
 * @author Antonio López.
 */
@Module public class HousesModule {

    @Provides
    @Activity
    public GotHouseRepositoryImp provideGotHouseRepository(GotCharacterRepositoryImp repositoryImp){
        return new GotHouseRepositoryImp(repositoryImp);
    }

    @Provides
    @Activity
    @Named("house")
    public GetListUseCase<GoTHouse> provideGotHouseListUseCase(Executor executor, MainThread mainThread, GotHouseRepositoryImp repository){
        return new GetListUseCaseImp<>(executor, mainThread, repository);
    }

    @Provides
    @Activity
    public HouseListPresenter provideGotHouseListPresenter(@Named("house") GetListUseCase<GoTHouse> houseGetListUseCase){
        return new HouseListPresenterImp(houseGetListUseCase);
    }
}
