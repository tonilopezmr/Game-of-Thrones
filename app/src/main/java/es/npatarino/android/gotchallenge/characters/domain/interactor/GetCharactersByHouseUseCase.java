package es.npatarino.android.gotchallenge.characters.domain.interactor;

import java.util.List;

import es.npatarino.android.gotchallenge.characters.domain.CharactersDomain;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.houses.domain.model.House;
import es.npatarino.android.gotchallenge.common.interactor.UseCase;
import rx.Observable;
import rx.Scheduler;

public class GetCharactersByHouseUseCase extends UseCase<List<GoTCharacter>> {

    private final CharactersDomain.Repository repository;
    private House house;

    public GetCharactersByHouseUseCase(CharactersDomain.Repository repository,
                                       Scheduler uiThread,
                                       Scheduler executorThread) {
        super(uiThread, executorThread);
        this.repository = repository;
    }

    public Observable<List<GoTCharacter>> execute(House house) {
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
