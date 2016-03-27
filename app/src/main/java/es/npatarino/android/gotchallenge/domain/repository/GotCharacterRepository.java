package es.npatarino.android.gotchallenge.domain.repository;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;

/**
 * @author Antonio López.
 */
public interface GotCharacterRepository extends ListRepository<GoTCharacter> {
    GoTCharacter read(GoTCharacter entity) throws Exception;
    List<GoTCharacter> read(GoTHouse house) throws Exception;
}
