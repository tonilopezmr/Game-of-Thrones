package es.npatarino.android.gotchallenge.characters.di;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.base.caching.TimeProvider;
import es.npatarino.android.gotchallenge.base.caching.strategy.TTLCachingStrategy;
import es.npatarino.android.gotchallenge.base.network.EndPoint;
import es.npatarino.android.gotchallenge.characters.data.CharacterRepository;
import es.npatarino.android.gotchallenge.characters.data.source.local.CharacterLocalDataSource;
import es.npatarino.android.gotchallenge.characters.data.source.local.BddGoTCharacterMapper;
import es.npatarino.android.gotchallenge.characters.data.source.network.CharacterNetworkDataSource;
import es.npatarino.android.gotchallenge.characters.data.source.network.CharacterJsonMapper;
import es.npatarino.android.gotchallenge.characters.domain.CharactersDomain;
import okhttp3.OkHttpClient;

@Module
public class CharacterModule {

    @Provides
    @CharacterScope
    public BddGoTCharacterMapper provideBddGotCharacterMapper() {
        return new BddGoTCharacterMapper();
    }

    @Provides
    @CharacterScope
    public CharactersDomain.LocalDataSource provideCharacterLocalDataSource(TTLCachingStrategy cachingStrategy,
                                                                            TimeProvider timeProvider,
                                                                            BddGoTCharacterMapper mapper) {
        return new CharacterLocalDataSource(cachingStrategy, timeProvider, mapper);
    }

    @Provides
    @CharacterScope
    public CharactersDomain.NetworkDataSource provideCharacterRemoteDataSource(OkHttpClient okHttpClient,
                                                                               EndPoint endPoint,
                                                                             CharacterJsonMapper characterJsonMapper) {
        return new CharacterNetworkDataSource(characterJsonMapper, endPoint, okHttpClient);
    }

    @Provides
    @CharacterScope
    public CharactersDomain.Repository
    provideGotCharacterRepository(CharactersDomain.NetworkDataSource networkDataSource,
                                  CharactersDomain.LocalDataSource localDataSource) {
        return new CharacterRepository(networkDataSource, localDataSource);
    }
}
