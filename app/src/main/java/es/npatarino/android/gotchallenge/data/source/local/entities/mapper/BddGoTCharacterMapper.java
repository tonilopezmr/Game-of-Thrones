package es.npatarino.android.gotchallenge.data.source.local.entities.mapper;

import java.util.ArrayList;
import java.util.List;

import es.npatarino.android.gotchallenge.data.source.local.entities.BddGoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.mapper.TwoWaysMapper;

public class BddGoTCharacterMapper implements TwoWaysMapper<GoTCharacter, BddGoTCharacter>{

    @Override
    public GoTCharacter inverseMap(BddGoTCharacter model) {
        return new GoTCharacter(model.getName(),
                model.getImageUrl(),
                model.getDescription(),
                null,
                null,
                model.getHouseId());
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
        return new BddGoTCharacter(model.getName(), model.getImageUrl(), model.getDescription(), model.getHouseId());
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
