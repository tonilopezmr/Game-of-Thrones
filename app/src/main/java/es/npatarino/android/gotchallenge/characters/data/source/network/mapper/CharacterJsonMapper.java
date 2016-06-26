package es.npatarino.android.gotchallenge.characters.data.source.network.mapper;

import com.google.gson.Gson;

import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.common.network.JsonMapper;

public class CharacterJsonMapper extends JsonMapper<GoTCharacter>{

    public CharacterJsonMapper(Gson gson) {
        super(gson);
    }
}
