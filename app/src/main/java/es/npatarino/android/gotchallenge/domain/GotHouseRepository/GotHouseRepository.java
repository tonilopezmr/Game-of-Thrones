package es.npatarino.android.gotchallenge.domain.GotHouseRepository;

import java.util.ArrayList;
import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;

/**
 * @author Antonio López.
 */
public class GotHouseRepository implements Repository<GoTHouse>{
    
    private GotCharacterRepository repository;

    public GotHouseRepository(GotCharacterRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<GoTHouse> getList() throws Exception {
        List<GoTCharacter> characters = repository.getList();
        ArrayList<GoTHouse> hs = new ArrayList<GoTHouse>();
        for (int i = 0; i < characters.size(); i++) {
            GoTCharacter goTCharacter = characters.get(i);
            GoTHouse goTHouse = getHouseFromCharacter(goTCharacter);
            if (!hs.contains(goTHouse) && isValidHouse(goTHouse)) {
                hs.add(goTHouse);
            }
        }
        return hs;
    }

    private boolean isValidHouse(GoTHouse house) {
        return house.getHouseId() != null && !house.getHouseId().isEmpty();
    }

    private GoTHouse getHouseFromCharacter(GoTCharacter goTCharacter) {
        GoTHouse h = new GoTHouse();
        h.setHouseId(goTCharacter.getHouseId());
        h.setHouseName(goTCharacter.getHouseName());
        h.setHouseImageUrl(goTCharacter.getHouseImageUrl());
        return h;
    }
}
