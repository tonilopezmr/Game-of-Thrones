package es.npatarino.android.gotchallenge.domain.interactor;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.Character;
import es.npatarino.android.gotchallenge.domain.House;
import es.npatarino.android.gotchallenge.domain.interactor.common.UseCase;
import es.npatarino.android.gotchallenge.domain.repository.CharacterRepository;
import rx.Observable;
import rx.Scheduler;

public class GetCharactersByHouseUseCase extends UseCase<List<Character>> {

    private final CharacterRepository repository;
    private House house;

    public GetCharactersByHouseUseCase(CharacterRepository repository,
                                       Scheduler uiThread,
                                       Scheduler executorThread) {
        super(uiThread, executorThread);
        this.repository = repository;
    }

    public Observable<List<Character>> execute(House house) {
        this.house = house;
        return buildUseCaseObservable();
    }

    @Override
    protected Observable<List<Character>> buildUseCaseObservable() {
        return repository.read(house)
                .observeOn(uiThread)
                .subscribeOn(executorThread);
    }
}
