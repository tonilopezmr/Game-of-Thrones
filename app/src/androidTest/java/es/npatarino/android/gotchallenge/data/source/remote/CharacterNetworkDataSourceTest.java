package es.npatarino.android.gotchallenge.data.source.remote;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import es.npatarino.android.gotchallenge.characters.data.source.network.CharacterNetworkDataSourceImp;
import es.npatarino.android.gotchallenge.characters.domain.Characters;
import es.npatarino.android.gotchallenge.common.network.EndPoint;
import es.npatarino.android.gotchallenge.characters.data.source.network.mapper.CharacterJsonMapper;
import es.npatarino.android.gotchallenge.di.AppModule;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import okhttp3.OkHttpClient;
import rx.observers.TestSubscriber;

public class CharacterNetworkDataSourceTest {

    private Characters.NetworkDataSource networkDataSource;

    @Before
    public void setUp() throws Exception {
        CharacterJsonMapper characterJsonMapper = new CharacterJsonMapper(new Gson());
        OkHttpClient okHttpClient = new OkHttpClient();
        EndPoint endPoint = new EndPoint(AppModule.END_POINT);

        networkDataSource = new CharacterNetworkDataSourceImp(characterJsonMapper, endPoint, okHttpClient);
    }

    @Test
    public void
    get_all_got_characters() {
        TestSubscriber<List<GoTCharacter>> testSubscriber = new TestSubscriber<>();

        networkDataSource.getAll().subscribe(testSubscriber);

        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
    }
}