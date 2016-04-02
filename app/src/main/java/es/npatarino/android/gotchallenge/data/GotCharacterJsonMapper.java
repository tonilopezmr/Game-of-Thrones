package es.npatarino.android.gotchallenge.data;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;

/**
 * @author Antonio López.
 */
public class GotCharacterJsonMapper {

    private final Gson gson;

    public GotCharacterJsonMapper(Gson gson) {
        this.gson = gson;
    }

    public List<GoTCharacter> transformList(String characterJsonList) throws JsonSyntaxException{
        Type listType = new TypeToken<ArrayList<GoTCharacter>>() {}.getType();
        return this.gson.fromJson(characterJsonList, listType);
    }
}
