package es.npatarino.android.gotchallenge.houses.data.source.local.mapper;

import es.npatarino.android.gotchallenge.BuildConfig;
import es.npatarino.android.gotchallenge.base.mapper.TwoWaysMapper;
import es.npatarino.android.gotchallenge.houses.data.source.local.entities.BddHouse;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;

import java.util.ArrayList;
import java.util.List;

public class BddHouseMapper implements TwoWaysMapper<GoTHouse, BddHouse> {

  @Override
  public GoTHouse inverseMap(BddHouse model) {
    return new GoTHouse(model.getHouseId(),
        BuildConfig.DEBUG ? model.getHouseName() + " cache" : model.getHouseName(),
        model.getHouseImageUrl());
  }

  @Override
  public List<GoTHouse> inverseMap(List<BddHouse> listModel) {
    List<GoTHouse> list = new ArrayList<>();

    for (int i = 0, size = listModel.size(); i < size; i++) {
      BddHouse bddHouse = listModel.get(i);
      list.add(inverseMap(bddHouse));
    }

    return list;
  }

  @Override
  public BddHouse map(GoTHouse model) {
    BddHouse house = new BddHouse();
    house.setHouseName(model.getName());
    house.setHouseId(model.getId());
    house.setHouseImageUrl(model.getImageUrl());
    return house;
  }

  @Override
  public List<BddHouse> map(List<GoTHouse> listModel) {
    List<BddHouse> list = new ArrayList<>();

    for (int i = 0, size = listModel.size(); i < size; i++) {
      GoTHouse goTCharacter = listModel.get(i);
      list.add(map(goTCharacter));
    }

    return list;
  }
}
