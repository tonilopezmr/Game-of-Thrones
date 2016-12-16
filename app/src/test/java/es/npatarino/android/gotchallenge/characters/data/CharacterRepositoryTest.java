package es.npatarino.android.gotchallenge.characters.data;

import android.support.annotation.NonNull;
import es.npatarino.android.gotchallenge.characters.domain.CharactersDomain;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.observers.TestSubscriber;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CharacterRepositoryTest {

  private static final boolean EXPIRED = true;
  private static final boolean NOT_EXPIRED = false;

  @Mock
  CharactersDomain.NetworkDataSource networkDataSource;
  @Mock
  CharactersDomain.LocalDataSource localDataSource;

  CharactersDomain.Repository repository;

  @Before
  public void setUp() throws Exception {
    // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
    // inject the mocks in the test the initMocks method needs to be called.
    MockitoAnnotations.initMocks(this);

    repository = new CharacterRepository(networkDataSource, localDataSource);
  }

  @Test
  public void
  get_all_characters_from_network_and_save_in_cache() {
    TestSubscriber<List<GoTCharacter>> testSubscriber = new TestSubscriber<>();

    when(localDataSource.isExpired()).thenReturn(EXPIRED);
    when(networkDataSource.getAll()).thenReturn(getCharactersObservable(10));


    repository.getList()
        .subscribe(testSubscriber);

    testSubscriber.assertNoErrors();
    testSubscriber.assertCompleted();
    testSubscriber.assertValueCount(1);
    verify(localDataSource).save(any());
    verify(networkDataSource).getAll();
  }

  @Test
  public void
  get_from_local_when_save_throw_an_exception() {
    TestSubscriber<List<GoTCharacter>> testSubscriber = new TestSubscriber<>();

    when(localDataSource.isExpired()).thenReturn(EXPIRED);
    when(networkDataSource.getAll()).thenReturn(getCharactersObservable(10));
    when(localDataSource.getAll()).thenReturn(getCharactersObservable(1));
    doThrow(Exception.class).when(localDataSource).save(any(List.class));

    repository.getList()
        .subscribe(testSubscriber);

    testSubscriber.assertNoErrors();
    testSubscriber.assertCompleted();
    verify(localDataSource).getAll();
  }

  @Test
  public void
  get_from_local_when_cache_is_not_expired() {
    TestSubscriber<List<GoTCharacter>> testSubscriber = new TestSubscriber<>();

    when(localDataSource.isExpired()).thenReturn(NOT_EXPIRED);
    when(localDataSource.getAll()).thenReturn(getCharactersObservable(0));

    repository.getList()
        .subscribe(testSubscriber);

    testSubscriber.assertNoErrors();
    testSubscriber.assertCompleted();
    verify(localDataSource).getAll();
    verify(networkDataSource, never()).getAll();
  }

  @Test
  public void
  get_from_local_when_remoteDataSource_fail() {
    TestSubscriber<List<GoTCharacter>> testSubscriber = new TestSubscriber<>();

    when(localDataSource.isExpired()).thenReturn(EXPIRED);
    when(networkDataSource.getAll()).thenReturn(getErrorObservable());
    when(localDataSource.getAll()).thenReturn(getCharactersObservable(10));

    repository.getList()
        .subscribe(testSubscriber);

    testSubscriber.assertNoErrors();
    testSubscriber.assertCompleted();
    verify(networkDataSource).getAll();
    verify(localDataSource).getAll();
  }

  private List<GoTCharacter> getCharacters(int numCharacters) {
    List<GoTCharacter> characters = new ArrayList<>();
    for (int i = 0; i < numCharacters; i++) {
      characters.add(dummyCharacter());
    }
    return characters;
  }

  @NonNull
  private GoTCharacter dummyCharacter() {
    return new GoTCharacter(null, null, null, null);
  }

  @NonNull
  private Observable<List<GoTCharacter>> getCharactersObservable(int numCharacters) {
    return Observable.just(getCharacters(numCharacters));
  }

  @NonNull
  private Observable<List<GoTCharacter>> getErrorObservable() {
    return Observable.create(subscriber -> {
      subscriber.onError(new Exception());
      subscriber.onCompleted();
    });
  }
}