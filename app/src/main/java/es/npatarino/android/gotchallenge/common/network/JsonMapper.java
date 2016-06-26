package es.npatarino.android.gotchallenge.common.network;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;

public class JsonMapper<T> {

    private final Gson gson;

    public JsonMapper(Gson gson) {
        this.gson = gson;
    }

    public List<T> transformList(String characterJsonList) throws JsonSyntaxException{
        Type listType = new TypeToken<ArrayList<T>>() {}.getType();
        return this.gson.fromJson(characterJsonList, listType);
    }
}
