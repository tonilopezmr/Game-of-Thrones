package es.npatarino.android.gotchallenge.domain.datasource.remote;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.House;
import rx.Observable;

public interface CharacterRemoteDataSource {
    Observable<GoTCharacter> read(GoTCharacter entity);
    Observable<List<GoTCharacter>> read(House house);
    Observable<List<GoTCharacter>> getAll();
}
