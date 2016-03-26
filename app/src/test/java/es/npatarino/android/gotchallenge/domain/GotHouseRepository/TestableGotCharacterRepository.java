package es.npatarino.android.gotchallenge.domain.GotHouseRepository;

import es.npatarino.android.gotchallenge.ResourceHelper;

/**
 * @author Antonio López.
 */
public class TestableGotCharacterRepository extends GotCharacterRepositoryImp {
    
    public TestableGotCharacterRepository(String endPoint) {
        super(null, endPoint);
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
