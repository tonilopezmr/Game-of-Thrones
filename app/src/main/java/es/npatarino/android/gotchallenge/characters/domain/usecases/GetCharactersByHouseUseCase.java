package es.npatarino.android.gotchallenge.characters.domain.usecases;

import es.npatarino.android.gotchallenge.base.list.usecases.GetListUseCase;
import es.npatarino.android.gotchallenge.characters.domain.CharactersDomain;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import rx.Observable;
import rx.Scheduler;

import java.util.List;

public class GetCharactersByHouseUseCase extends GetListUseCase<GoTCharacter> {

  private final CharactersDomain.Repository repository;
  private GoTHouse house;

  public GetCharactersByHouseUseCase(CharactersDomain.Repository repository,
                                     Scheduler uiThread,
                                     Scheduler executorThread) {
    super(repository, uiThread, executorThread);
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
