package es.npatarino.android.gotchallenge.houses.data.source.network;

import es.npatarino.android.gotchallenge.base.network.EndPoint;
import es.npatarino.android.gotchallenge.characters.data.source.network.CharacterJsonMapper;
import es.npatarino.android.gotchallenge.characters.data.source.network.CharacterNetworkDataSource;
import es.npatarino.android.gotchallenge.characters.domain.CharactersDomain;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.houses.domain.HousesDomain;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import okhttp3.OkHttpClient;
import rx.Observable;

import java.util.ArrayList;
import java.util.List;

public class HouseNetworkDataSource implements HousesDomain.NetworkDataSource {

  private CharactersDomain.NetworkDataSource dataSource;

  public HouseNetworkDataSource(CharacterJsonMapper jsonMapper,
                                EndPoint endPoint,
                                OkHttpClient client) {
    this.dataSource = new CharacterNetworkDataSource(jsonMapper, endPoint, client);
  }

  private void addHouseInList(GoTHouse house, ArrayList<GoTHouse> hs) {
    if (isValidHouse(house) && !hs.contains(house)) {
      hs.add(house);
    }
  }

  private boolean isValidHouse(GoTHouse house) {
    return house.getId() != null && !house.getId().isEmpty();
  }

  @Override
  public Observable<List<GoTHouse>> getAll() {
    return dataSource.getAll().map(characters -> {
      ArrayList<GoTHouse> hs = new ArrayList<GoTHouse>();
      for (int i = 0, size = characters.size(); i < size; i++) {
        GoTCharacter character = characters.get(i);
        GoTHouse house = character.getHouse();
        addHouseInList(house, hs);
      }
      return hs;
    });
  }

}
