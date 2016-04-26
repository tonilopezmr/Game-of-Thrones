package es.npatarino.android.gotchallenge.domain.repository;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import rx.Observable;

/**
 * @author Antonio López.
 */
public interface GotCharacterRepository extends ListRepository<GoTCharacter> {
    Observable<GoTCharacter> read(GoTCharacter entity);
    Observable<List<GoTCharacter>> read(GoTHouse house);
}
