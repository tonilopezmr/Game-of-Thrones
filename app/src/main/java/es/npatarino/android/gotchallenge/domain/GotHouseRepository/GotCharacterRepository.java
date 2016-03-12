package es.npatarino.android.gotchallenge.domain.GotHouseRepository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;

/**
 * @author Antonio López.
 */
public class GotCharacterRepository implements Repository<GoTCharacter> {

    @Override
    public List<GoTCharacter> getList() throws Exception {
        String url = "http://ec2-52-18-202-124.eu-west-1.compute.amazonaws.com:3000/characters";

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        Type listType = new TypeToken<ArrayList<GoTCharacter>>() {
        }.getType();
        final List<GoTCharacter> characters = new Gson().fromJson(response.toString(), listType);
        return characters;
    }

    public GoTCharacter read(GoTCharacter entity) throws Exception {
        List<GoTCharacter> characters = getList();
        GoTCharacter character = null;
        for (int i = 0, size = characters.size(); i < size && character==null; i++){
            GoTCharacter item = characters.get(i);
            if (item.getName().equals(entity.getName())){
                character = item;
            }
        }
        return character;
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
