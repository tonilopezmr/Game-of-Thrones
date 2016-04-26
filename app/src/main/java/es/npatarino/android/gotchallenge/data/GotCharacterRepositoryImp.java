package es.npatarino.android.gotchallenge.data;

import java.util.Iterator;
import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.domain.repository.GotCharacterRepository;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;

/**
 * @author Antonio López.
 */
public class GotCharacterRepositoryImp implements GotCharacterRepository {

    private OkHttpClient client;
    private String endPoint;
    private final GotCharacterJsonMapper characterJsonMapper;

    public GotCharacterRepositoryImp(OkHttpClient client, String endPoint, GotCharacterJsonMapper jsonMapper) {
        this.client = client;
        this.endPoint = endPoint;
        this.characterJsonMapper = jsonMapper;
    }

    @Override
    public Observable<List<GoTCharacter>> getList(){
        return Observable.create(subscriber -> {
            try {
                StringBuffer response = getCharactersFromUrl(endPoint);

                subscriber.onNext(characterJsonMapper.transformList(response.toString()));
            } catch (Exception e) {
                e.printStackTrace();
                subscriber.onError(e);
            }
            subscriber.onCompleted();
        });
    }

    protected StringBuffer getCharactersFromUrl(String endPoint) throws Exception {
        Request request = new Request.Builder()
                .url(endPoint)
                .build();

        Response response = client.newCall(request).execute();
        return new StringBuffer(response.body().string());
    }

    @Override
    public Observable<GoTCharacter> read(GoTCharacter entity){
        return getList().map(characters -> {
            int index =  characters.indexOf(entity);
            return index == -1? null :  characters.get(index);
        });
    }

    @Override
    public Observable<List<GoTCharacter>> read(GoTHouse house){
        return getList().map(characters -> {
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
