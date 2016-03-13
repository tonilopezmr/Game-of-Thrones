package es.npatarino.android.gotchallenge.domain.GotHouseRepository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Antonio López.
 */
public class GotCharacterRepository implements Repository<GoTCharacter> {

    private OkHttpClient client;
    private String endPoint;

    public GotCharacterRepository(OkHttpClient client, String endPoint) {
        this.client = client;
        this.endPoint = endPoint;
    }

    @Override
    public List<GoTCharacter> getList() throws Exception {
        StringBuffer response = getCharactersFromUrl(endPoint);

        Type listType = new TypeToken<ArrayList<GoTCharacter>>() {}.getType();
        return new Gson().fromJson(response.toString(), listType);
    }

    protected StringBuffer getCharactersFromUrl(String endPoint) throws Exception {
        String url = endPoint.concat("/characters");
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return new StringBuffer(response.body().string());
    }

    public GoTCharacter read(GoTCharacter entity) throws Exception {
        List<GoTCharacter> characters = getList();
        int index =  characters.indexOf(entity);
        return index == -1? null :  characters.get(index);
    }

    public List<GoTCharacter> read(GoTHouse house) throws Exception{
        List<GoTCharacter> characters = getList();
        Iterator<GoTCharacter> iterator = characters.iterator();
        while (iterator.hasNext()){
            GoTCharacter character = iterator.next();
            if (!character.getHouseId().equals(house.getHouseId())){
                iterator.remove();
            }
        }
        return characters;
    }

}
