package es.npatarino.android.gotchallenge.domain.repository;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import es.npatarino.android.gotchallenge.data.CharacterRepositoryImp;
import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.datasource.local.CharacterLocalDataSource;
import es.npatarino.android.gotchallenge.domain.datasource.remote.CharacterRemoteDataSource;
import rx.Observable;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CharacterRepositoryTest {

    public static final boolean EXPIRED = true;
    public static final boolean NOT_EXPIRED = false;

    @Mock
    CharacterRemoteDataSource remoteDataSource;
    @Mock
    CharacterLocalDataSource localDataSource;

    CharacterRepository repository;

    @Before
    public void setUp() throws Exception {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        repository = new CharacterRepositoryImp(remoteDataSource, localDataSource);
    }

    @Test public void
    get_all_characters_from_network_and_save_in_cache() throws Exception {
        when(localDataSource.isExpired()).thenReturn(EXPIRED);
        when(remoteDataSource.getAll()).thenReturn(getCharactersObservable(10));


        repository.getList()
                .subscribe(list -> assertThat(list.size(), is(10)), throwable -> fail());

        verify(localDataSource).save(getCharacters(10));
    }

    @Test
    public void
    get_from_local_when_save_throw_an_exception() {
        when(localDataSource.isExpired()).thenReturn(EXPIRED);
        when(remoteDataSource.getAll()).thenReturn(getCharactersObservable(10));
        when(localDataSource.getAll()).thenReturn(getCharactersObservable(1));
        doThrow(Exception.class).when(localDataSource).save(any(List.class));

        repository.getList()
                .subscribe(list -> assertThat(list.size(), is(1)), throwable -> fail());

        verify(localDataSource).getAll();
    }

    @Test
    public void
    get_from_local_when_cache_is_not_expired() {
        when(localDataSource.isExpired()).thenReturn(NOT_EXPIRED);
        when(localDataSource.getAll()).thenReturn(getCharactersObservable(0));

        repository.getList()
                .subscribe(list -> assertThat(list.size(), is(0)), throwable -> fail());

        verify(localDataSource).getAll();
    }

    @Test
    public void
    get_from_local_when_remoteDataSource_fail() {
        when(localDataSource.isExpired()).thenReturn(NOT_EXPIRED);
        when(remoteDataSource.getAll()).thenReturn(getErrorObservable());
        when(localDataSource.getAll()).thenReturn(getCharactersObservable(10));

        repository.getList()
                .subscribe(list -> assertThat(list.size(), is(10)), throwable -> fail());

        verify(localDataSource).getAll();
    }

    private List<GoTCharacter> getCharacters(int numCharacters){
        List<GoTCharacter> characters = new ArrayList<>();
        for (int i = 0; i < numCharacters; i++){
            characters.add(new GoTCharacter());
        }
        return characters;
    }

    @NonNull
    private Observable<List<GoTCharacter>> getCharactersObservable(int numCharacters){
        return Observable.just(getCharacters(numCharacters));
    }

    @NonNull
    private Observable<List<GoTCharacter>> getEmptyHouseListObservable(){
        return Observable.just(new ArrayList<>());
    }

    @NonNull
    private Observable<List<GoTCharacter>> getErrorObservable(){
        return Observable.create(subscriber -> {
            subscriber.onError(new Exception());
            subscriber.onCompleted();
        });
    }
}