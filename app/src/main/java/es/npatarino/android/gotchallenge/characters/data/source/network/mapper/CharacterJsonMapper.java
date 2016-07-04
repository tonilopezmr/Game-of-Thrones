package es.npatarino.android.gotchallenge.characters.data.source.network.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CharacterJsonMapper {

    private final Gson gson;

    public CharacterJsonMapper(Gson gson) {
        this.gson = gson;
    }

    public List<GoTCharacter> transformList(String characterJsonList) throws JsonSyntaxException {
        TypeToken<ArrayList<GoTCharacter>> typeToken = new TypeToken<ArrayList<GoTCharacter>>() {
        };
        Type listType = typeToken.getType();
        return this.gson.fromJson(characterJsonList, listType);
    }
}
