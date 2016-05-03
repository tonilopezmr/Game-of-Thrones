package es.npatarino.android.gotchallenge.domain.datasource.remote;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;

public interface CharacterRemoteDataSource {
    GoTCharacter read(GoTCharacter entity) throws Exception;
    List<GoTCharacter> read(GoTHouse house) throws Exception;
    List<GoTCharacter> getList() throws Exception;
}
