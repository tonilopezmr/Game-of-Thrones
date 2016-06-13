package es.npatarino.android.gotchallenge.domain.repository;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.House;
import rx.Observable;

public interface CharacterRepository extends ListRepository<GoTCharacter> {
    Observable<GoTCharacter> read(GoTCharacter entity);
    Observable<List<GoTCharacter>> read(House house);
}
