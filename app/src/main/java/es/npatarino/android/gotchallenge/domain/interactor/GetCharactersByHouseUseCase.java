package es.npatarino.android.gotchallenge.domain.interactor;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.domain.interactor.common.UseCase;
import es.npatarino.android.gotchallenge.domain.repository.GotCharacterRepository;
import rx.Observable;
import rx.Scheduler;

/**
 * @author Antonio López.
*/

public class GetCharactersByHouseUseCase extends UseCase<List<GoTCharacter>> {

    private final GotCharacterRepository repository;
    private GoTHouse house;

    public GetCharactersByHouseUseCase(GotCharacterRepository repository,
                                       Scheduler uiThread,
                                       Scheduler executorThread) {
        super(uiThread, executorThread);
        this.repository = repository;
    }

    public Observable<List<GoTCharacter>> execute(GoTHouse house) {
        this.house = house;
        return buildUseCaseObservable();
    }

    @Override
    protected Observable<List<GoTCharacter>> buildUseCaseObservable() {
        return repository.read(house)
                .observeOn(uiThread)
                .subscribeOn(executorThread);
    }
}
