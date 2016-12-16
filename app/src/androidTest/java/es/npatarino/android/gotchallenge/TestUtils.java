package es.npatarino.android.gotchallenge;

import android.support.annotation.NonNull;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import rx.Observable;

import java.util.LinkedList;
import java.util.List;

public class TestUtils {

    private static final String KHAL_DROGO_NAME = "Khal Drogo";
    private static final String KHAL_DROGO_URL =
            "https://s3-eu-west-1.amazonaws.com/npatarino/got/8310ebeb-cdda-4095-bd5b-f59266d44677.jpg";
    private static final String KHAL_DROGO_DESCRIPTION = "Any description is good";
    private static final String KHAL_DROGO_HOUSE_ID = "f96537a9";
    private static final String KHAL_DROGO_HOUSE_NAME = "House Khal Drogo";

    public static GoTHouse defaultGotHouse() {
        return new GoTHouse(KHAL_DROGO_HOUSE_ID,KHAL_DROGO_HOUSE_NAME,KHAL_DROGO_URL);
    }

    @NonNull
    public static List<GoTCharacter> getGoTCharacters(int numberOfGotCharacters) {
        List<GoTCharacter> characters = new LinkedList<>();
        for (int i = 0; i < numberOfGotCharacters; i++) {
            characters.add(defaultGotCharacter(i));
        }
        return characters;
    }

    public static Observable<List<GoTCharacter>> getCharacters(int numberOfGotCharacters) throws Exception {
        List<GoTCharacter> characters = getGoTCharacters(numberOfGotCharacters);
        return Observable.just(characters);
    }

    public static GoTCharacter defaultGotCharacter(int id) {
        GoTHouse house = defaultGotHouse();
        return new GoTCharacter(KHAL_DROGO_NAME + id, KHAL_DROGO_URL, KHAL_DROGO_DESCRIPTION, house);
    }

}
