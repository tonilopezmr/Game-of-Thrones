package es.npatarino.android.gotchallenge.data.source.local;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import es.npatarino.android.gotchallenge.TestUtils;
import es.npatarino.android.gotchallenge.base.caching.TimeProvider;
import es.npatarino.android.gotchallenge.base.caching.strategy.TTLCachingStrategy;
import es.npatarino.android.gotchallenge.characters.data.source.local.CharacterLocalDataSource;
import es.npatarino.android.gotchallenge.characters.data.source.local.mapper.BddGoTCharacterMapper;
import es.npatarino.android.gotchallenge.characters.domain.CharactersDomain;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class CharacterLocalDataSourceTest {

    private BddGoTCharacterMapper mapper = new BddGoTCharacterMapper();
    private TTLCachingStrategy ttlCachingStrategy = new TTLCachingStrategy(2, TimeUnit.MINUTES);
    private TimeProvider timeProvider = new TimeProvider(InstrumentationRegistry.getTargetContext());

    private CharactersDomain.LocalDataSource dataSource;

    @Before
    public void setUp() {
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder(InstrumentationRegistry.getTargetContext())
                .name("test.realm")
                .schemaVersion(4)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

        dataSource =
                new CharacterLocalDataSource(ttlCachingStrategy, timeProvider, mapper);
    }

    @Test
    public void
    save_characters_and_get_all() {
        final List<GoTCharacter> goTCharacters = getGotCharacters(10);

        dataSource.save(goTCharacters);


        dataSource.getAll()
                .subscribe(list -> assertGotCharacterList(goTCharacters, list), throwable -> fail());

        dataSource.removeAll(goTCharacters);
    }

    @Test
    public void
    delete_characters() {
        final List<GoTCharacter> goTCharacters = getGotCharacters(10);

        dataSource.save(goTCharacters);
        dataSource.removeAll(goTCharacters);

        dataSource.getAll()
                .subscribe(list -> assertThat(list.size(), is(0)), throwable -> fail());
    }

    @Test
    public void
    caching_characters_is_not_expired() {
        final List<GoTCharacter> goTCharacters = getGotCharacters(10);

        dataSource.save(goTCharacters);

        assertFalse(dataSource.isExpired());
        dataSource.removeAll(goTCharacters);
    }


    private void assertGotCharacterList(List<GoTCharacter> expected, List<GoTCharacter> list) {
        assertThat(list.size(), is(expected.size()));

        for (int i = 0; i < list.size(); i++) {
            GoTCharacter charExpected = expected.get(i);
            GoTCharacter character = list.get(i);

            assertGotCharacter(charExpected, character);
        }
    }

    private void assertGotCharacter(GoTCharacter charExpected, GoTCharacter character) {
        assertThat(character.getName(), is(charExpected.getName() + " cache"));
        assertThat(character.getDescription(), is(charExpected.getDescription()));
        assertThat(character.getHouseId(), is(charExpected.getHouseId()));
        assertThat(character.getImageUrl(), is(charExpected.getImageUrl()));
        assertNull(character.getHouseImageUrl());
        assertNull(character.getHouseName());
    }

    private List<GoTCharacter> getGotCharacters(int numCharacters) {
        List<GoTCharacter> list = TestUtils.getGoTCharacters(numCharacters);

        for (int i = 0; i < list.size(); i++) {
            GoTCharacter character = list.get(i);
            character.setName(character.getName() + i);
        }

        return list;
    }

}