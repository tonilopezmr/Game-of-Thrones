package es.npatarino.android.gotchallenge.domain.datasource.local;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.House;
import rx.Observable;

public interface CharacterLocalDataSource {
    void save(List<GoTCharacter> save);
    boolean isExpired();
    void removeAll(List<GoTCharacter> remove);

    Observable<GoTCharacter> read(GoTCharacter entity);
    Observable<List<GoTCharacter>> read(House house);
    Observable<List<GoTCharacter>> getAll();
}
