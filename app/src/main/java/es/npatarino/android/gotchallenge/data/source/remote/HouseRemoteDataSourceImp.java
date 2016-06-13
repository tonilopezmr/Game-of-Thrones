package es.npatarino.android.gotchallenge.data.source.remote;

import java.util.ArrayList;
import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.House;
import es.npatarino.android.gotchallenge.domain.datasource.remote.CharacterRemoteDataSource;
import es.npatarino.android.gotchallenge.domain.datasource.remote.HouseRemoteDataSource;
import rx.Observable;

public class HouseRemoteDataSourceImp implements HouseRemoteDataSource{

    private CharacterRemoteDataSource dataSource;

    public HouseRemoteDataSourceImp(CharacterRemoteDataSource dataSource) {
        this.dataSource = dataSource;
    }

    private void addHouseInList(House house, ArrayList<House> hs) {
        if (isValidHouse(house) && !hs.contains(house)) {
            hs.add(house);
        }
    }

    private boolean isValidHouse(House house) {
        return house.getHouseId() != null && !house.getHouseId().isEmpty();
    }

    private House getHouseFromCharacter(GoTCharacter character) {
        House h = new House();
        h.setHouseId(character.getHouseId());
        h.setHouseName(character.getHouseName());
        h.setHouseImageUrl(character.getHouseImageUrl());
        return h;
    }

    @Override
    public Observable<List<House>> getAll() {
        return dataSource.getAll().map(characters -> {
            ArrayList<House> hs = new ArrayList<House>();
            for (int i = 0, size = characters.size(); i < size; i++) {
                GoTCharacter character = characters.get(i);
                House house = getHouseFromCharacter(character);
                addHouseInList(house, hs);
            }
            return hs;
        });
    }


}
