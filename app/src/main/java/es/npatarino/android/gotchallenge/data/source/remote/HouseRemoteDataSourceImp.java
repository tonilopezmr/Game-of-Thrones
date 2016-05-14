package es.npatarino.android.gotchallenge.data.source.remote;

import java.util.ArrayList;
import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.domain.datasource.remote.CharacterRemoteDataSource;
import es.npatarino.android.gotchallenge.domain.datasource.remote.HouseRemoteDataSource;
import rx.Observable;

public class HouseRemoteDataSourceImp implements HouseRemoteDataSource{

    private CharacterRemoteDataSource dataSource;

    public HouseRemoteDataSourceImp(CharacterRemoteDataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void addHouseInList(GoTHouse goTHouse, ArrayList<GoTHouse> hs) {
        if (isValidHouse(goTHouse) && !hs.contains(goTHouse)) {
            hs.add(goTHouse);
        }
    }

    private boolean isValidHouse(GoTHouse house) {
        return house.getHouseId() != null && !house.getHouseId().isEmpty();
    }

    private GoTHouse getHouseFromCharacter(GoTCharacter goTCharacter) {
        GoTHouse h = new GoTHouse();
        h.setHouseId(goTCharacter.getHouseId());
        h.setHouseName(goTCharacter.getHouseName());
        h.setHouseImageUrl(goTCharacter.getHouseImageUrl());
        return h;
    }

    @Override
    public Observable<List<GoTHouse>> getAll() {
        return dataSource.getAll().map(characters -> {
            ArrayList<GoTHouse> hs = new ArrayList<GoTHouse>();
            for (int i = 0, size = characters.size(); i < size; i++) {
                GoTCharacter goTCharacter = characters.get(i);
                GoTHouse goTHouse = getHouseFromCharacter(goTCharacter);
                addHouseInList(goTHouse, hs);
            }
            return hs;
        });
    }


}
