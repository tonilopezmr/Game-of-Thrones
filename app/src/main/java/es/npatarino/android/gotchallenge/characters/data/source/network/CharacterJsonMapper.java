package es.npatarino.android.gotchallenge.characters.data.source.network;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CharacterJsonMapper {

  private final Gson gson;

  public CharacterJsonMapper(Gson gson) {
    this.gson = gson;
  }

  public List<GoTCharacter> transformList(String characterJsonList) throws JsonSyntaxException {
    TypeToken<ArrayList<JSONGoTCharacter>> typeToken = new TypeToken<ArrayList<JSONGoTCharacter>>() {
    };
    Type listType = typeToken.getType();
    return getCharacters(characterJsonList, listType);
  }

  private List<GoTCharacter> getCharacters(String characterJsonList, Type listType) {
    ArrayList<GoTCharacter> result = new ArrayList<>();
    List<JSONGoTCharacter> jsonCharacters = this.gson.fromJson(characterJsonList, listType);
    for (JSONGoTCharacter character : jsonCharacters) {
      GoTHouse house = new GoTHouse(character.houseId, character.houseName, character.houseImageUrl);
      GoTCharacter goTCharacter = new GoTCharacter(character.name, character.imageUrl, character.description, house);
      result.add(goTCharacter);
    }
    return result;
  }
}
