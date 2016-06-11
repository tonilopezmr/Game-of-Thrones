package es.npatarino.android.gotchallenge.domain.repository;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.Character;
import es.npatarino.android.gotchallenge.domain.House;
import rx.Observable;

public interface CharacterRepository extends ListRepository<Character> {
    Observable<Character> read(Character entity);
    Observable<List<Character>> read(House house);
}
