package es.npatarino.android.gotchallenge.data.source.remote;

import java.util.Iterator;
import java.util.List;

import es.npatarino.android.gotchallenge.domain.Character;
import es.npatarino.android.gotchallenge.domain.House;
import es.npatarino.android.gotchallenge.domain.datasource.remote.CharacterRemoteDataSource;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;

public class CharacterRemoteDataSourceImp implements CharacterRemoteDataSource {

    private OkHttpClient client;
    private EndPoint endPoint;
    private final JsonMapper characterJsonMapper;

    public CharacterRemoteDataSourceImp(JsonMapper characterJsonMapper,
                                        EndPoint endPoint,
                                        OkHttpClient client) {
        this.characterJsonMapper = characterJsonMapper;
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
    public Observable<List<Character>> getAll(){
        return Observable.create(subscriber -> {
            try {
                StringBuffer response = getCharactersFromUrl(endPoint.toString());

                subscriber.onNext(characterJsonMapper.transformList(response.toString()));
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onError(e);
            }
            subscriber.onCompleted();
        });
    }


    @Override
    public Observable<Character> read(Character entity){
        return getAll().map(characters -> {
            int index =  characters.indexOf(entity);
            return index == -1? null :  characters.get(index);
        });
    }

    @Override
    public Observable<List<Character>> read(House house){
        return getAll().map(characters -> {
            Iterator<Character> iterator = characters.iterator();
            while (iterator.hasNext()){
                Character character = iterator.next();
                if (!character.getHouseId().equals(house.getHouseId())){
                    iterator.remove();
                }
            }
            return characters;
        });
    }
}
