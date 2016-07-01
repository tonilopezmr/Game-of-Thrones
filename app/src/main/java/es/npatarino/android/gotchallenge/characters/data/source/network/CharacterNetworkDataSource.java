package es.npatarino.android.gotchallenge.characters.data.source.network;

import java.util.Iterator;
import java.util.List;

import es.npatarino.android.gotchallenge.characters.data.source.network.mapper.CharacterJsonMapper;
import es.npatarino.android.gotchallenge.characters.domain.CharactersDomain;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.base.network.EndPoint;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;

public class CharacterNetworkDataSource implements CharactersDomain.NetworkDataSource {

    private OkHttpClient client;
    private EndPoint endPoint;
    private final CharacterJsonMapper characterCharacterJsonMapper;

    public CharacterNetworkDataSource(CharacterJsonMapper characterCharacterJsonMapper,
                                      EndPoint endPoint,
                                      OkHttpClient client) {
        this.characterCharacterJsonMapper = characterCharacterJsonMapper;
        this.endPoint = endPoint;
        this.client = client;
    }


    protected StringBuffer getCharactersFromUrl(String endPoint) throws Exception {
        Request request = new Request.Builder()
                .url(endPoint)
                .build();

        Response response = client.newCall(request).execute();
        return new StringBuffer(response.body().string());
    }

    @Override
    public Observable<List<GoTCharacter>> getAll() {
        return Observable.create(subscriber -> {
            try {
                StringBuffer response = getCharactersFromUrl(endPoint.toString());

                subscriber.onNext(characterCharacterJsonMapper.transformList(response.toString()));
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onError(e);
            }
            subscriber.onCompleted();
        });
    }


    @Override
    public Observable<GoTCharacter> read(GoTCharacter entity){
        return getAll().map(characters -> {
            int index =  characters.indexOf(entity);
            return index == -1? null :  characters.get(index);
        });
    }

    @Override
    public Observable<List<GoTCharacter>> read(GoTHouse house){
        return getAll().map(characters -> {
            Iterator<GoTCharacter> iterator = characters.iterator();
            while (iterator.hasNext()){
                GoTCharacter character = iterator.next();
                if (!character.getHouseId().equals(house.getHouseId())){
                    iterator.remove();
                }
            }
            return characters;
        });
    }
}
