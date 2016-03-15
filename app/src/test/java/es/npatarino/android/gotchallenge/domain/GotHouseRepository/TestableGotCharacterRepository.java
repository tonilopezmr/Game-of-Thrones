package es.npatarino.android.gotchallenge.domain.GotHouseRepository;

import es.npatarino.android.gotchallenge.ResourceHelper;

/**
 * @author Antonio López.
 */
public class TestableGotCharacterRepository extends GotCharacterRepositoryImp {

    private static final String END_POINT = "normal_data.json";

    public TestableGotCharacterRepository(String endPoint) {
        super(null, endPoint);
    }

    @Override
    protected StringBuffer getCharactersFromUrl(String endPoint) throws Exception {
        ResourceHelper resHelper = new ResourceHelper();
        return resHelper.getContentFromFile(endPoint);
    }


    public static TestableGotCharacterRepository provideTestableGotCharacterRepository(){
        return new TestableGotCharacterRepository(END_POINT);
    }
}
