package es.npatarino.android.gotchallenge.data.source.remote;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import es.npatarino.android.gotchallenge.di.AppModule;
import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.datasource.remote.CharacterRemoteDataSource;
import okhttp3.OkHttpClient;
import rx.observers.TestSubscriber;

public class CharacterRemoteDataSourceTest {

    private CharacterRemoteDataSource remoteDataSource;

    @Before
    public void setUp() throws Exception {
        JsonMapper jsonMapper = new JsonMapper(new Gson());
        OkHttpClient okHttpClient = new OkHttpClient();
        EndPoint endPoint = new EndPoint(AppModule.END_POINT);

        remoteDataSource = new CharacterRemoteDataSourceImp(jsonMapper, endPoint, okHttpClient);
    }

    @Test
    public void
    get_all_got_characters() {
        TestSubscriber<List<GoTCharacter>> testSubscriber = new TestSubscriber<>();

        remoteDataSource.getAll().subscribe(testSubscriber);

        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
    }
}