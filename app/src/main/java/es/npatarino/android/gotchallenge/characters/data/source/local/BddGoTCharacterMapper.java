package es.npatarino.android.gotchallenge.characters.data.source.local;

import es.npatarino.android.gotchallenge.BuildConfig;
import es.npatarino.android.gotchallenge.base.mapper.TwoWaysMapper;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;

import java.util.ArrayList;
import java.util.List;

public class BddGoTCharacterMapper implements TwoWaysMapper<GoTCharacter, BddGoTCharacter> {

  @Override
  public GoTCharacter inverseMap(BddGoTCharacter model) {
    return new GoTCharacter(BuildConfig.DEBUG ? model.getName() + " cache" : model.getName(),
        model.getImageUrl(),
        model.getDescription(),
        new GoTHouse(model.getHouseId(), "", ""));
  }

  @Override
  public List<GoTCharacter> inverseMap(List<BddGoTCharacter> listModel) {
    List<GoTCharacter> list = new ArrayList<>();

    for (int i = 0, size = listModel.size(); i < size; i++) {
      BddGoTCharacter bddGoTCharacter = listModel.get(i);
      list.add(inverseMap(bddGoTCharacter));
    }

    return list;
  }

  @Override
  public BddGoTCharacter map(GoTCharacter model) {
    BddGoTCharacter character = new BddGoTCharacter();
    character.setName(model.getName());
    character.setDescription(model.getDescription());
    character.setImageUrl(model.getImageUrl());
    character.setHouseId(model.getHouse().getId());
    return character;
  }

  @Override
  public List<BddGoTCharacter> map(List<GoTCharacter> listModel) {
    List<BddGoTCharacter> list = new ArrayList<>();

    for (int i = 0, size = listModel.size(); i < size; i++) {
      GoTCharacter goTCharacter = listModel.get(i);
      list.add(map(goTCharacter));
    }

    return list;
  }
}
