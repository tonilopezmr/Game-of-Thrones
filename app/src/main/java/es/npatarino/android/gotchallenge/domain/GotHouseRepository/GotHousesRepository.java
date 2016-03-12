package es.npatarino.android.gotchallenge.domain.GotHouseRepository;

import java.util.ArrayList;
import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;

/**
 * @author Antonio López.
 */
public class GotHousesRepository implements Repository<GoTHouse>{
    
    private GotCharacterRepository repository;

    public GotHousesRepository() {
        repository = new GotCharacterRepository();
    }


    @Override
    public List<GoTHouse> getList() throws Exception {
        List<GoTCharacter> characters = repository.getList();
        ArrayList<GoTHouse> hs = new ArrayList<GoTHouse>();
        for (int i = 0; i < characters.size(); i++) {
            boolean b = false;
            for (int j = 0; j < hs.size(); j++) {
                if (hs.get(j).getHouseName().equalsIgnoreCase(characters.get(i).getHouseName())) {
                    b = true;
                }
            }
            if (!b) {
                if (characters.get(i).getHouseId() != null && !characters.get(i).getHouseId().isEmpty()) {
                    GoTHouse h = new GoTHouse();
                    h.setHouseId(characters.get(i).getHouseId());
                    h.setHouseName(characters.get(i).getHouseName());
                    h.setHouseImageUrl(characters.get(i).getHouseImageUrl());
                    hs.add(h);
                    b = false;
                }
            }
        }
        return hs;
    }

    @Override
    public GoTHouse readById(GoTHouse entity) throws Exception {
        List<GoTHouse> hs = getList();
        GoTHouse house = null;
        for (int i = 0, size = hs.size(); i < size && house==null; i++){
            GoTHouse item = hs.get(i);
            if (item.getHouseId().equals(entity.getHouseId())){
                house = item;
            }
        }
        return house;
    }
}
