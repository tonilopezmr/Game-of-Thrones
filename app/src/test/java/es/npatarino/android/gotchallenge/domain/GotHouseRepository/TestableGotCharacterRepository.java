package es.npatarino.android.gotchallenge.domain.GotHouseRepository;

import com.google.gson.Gson;

import es.npatarino.android.gotchallenge.ResourceHelper;
import es.npatarino.android.gotchallenge.data.GotCharacterJsonMapper;
import es.npatarino.android.gotchallenge.data.GotCharacterRepositoryImp;

/**
 * @author Antonio López.
 */
public class TestableGotCharacterRepository extends GotCharacterRepositoryImp {
    
    public TestableGotCharacterRepository(String endPoint) {
        super(null, endPoint, new GotCharacterJsonMapper(new Gson()));
    }

    @Override
    protected StringBuffer getCharactersFromUrl(String endPoint) throws Exception {
        ResourceHelper resHelper = new ResourceHelper();
        return resHelper.getContentFromFile(endPoint);
    }


    public static TestableGotCharacterRepository provideTestableGotCharacterRepository(String endPoint){
        return new TestableGotCharacterRepository(endPoint);
    }
}
