package es.npatarino.android.gotchallenge.data.source.local.entities.mapper;

import java.util.ArrayList;
import java.util.List;

import es.npatarino.android.gotchallenge.data.source.local.entities.BddHouse;
import es.npatarino.android.gotchallenge.domain.House;
import es.npatarino.android.gotchallenge.domain.mapper.TwoWaysMapper;

public class BddHouseMapper implements TwoWaysMapper<House, BddHouse> {

    @Override
    public House inverseMap(BddHouse model) {
        return new House(model.getHouseId(), model.getHouseName(), model.getHouseImageUrl());
    }

    @Override
    public List<House> inverseMap(List<BddHouse> listModel) {
        List<House> list = new ArrayList<>();

        for (int i = 0, size = listModel.size(); i < size; i++) {
            BddHouse bddHouse = listModel.get(i);
            list.add(inverseMap(bddHouse));
        }

        return list;
    }

    @Override
    public BddHouse map(House model) {
        return new BddHouse(model.getHouseId(), model.getHouseName(), model.getHouseImageUrl());
    }

    @Override
    public List<BddHouse> map(List<House> listModel) {
        List<BddHouse> list = new ArrayList<>();

        for (int i = 0, size = listModel.size(); i < size; i++) {
            House goTCharacter = listModel.get(i);
            list.add(map(goTCharacter));
        }

        return list;
    }
}
