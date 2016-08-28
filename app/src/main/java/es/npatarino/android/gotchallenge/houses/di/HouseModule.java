package es.npatarino.android.gotchallenge.houses.di;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.base.caching.TimeProvider;
import es.npatarino.android.gotchallenge.base.caching.strategy.TTLCachingStrategy;
import es.npatarino.android.gotchallenge.base.network.EndPoint;
import es.npatarino.android.gotchallenge.characters.data.source.network.mapper.CharacterJsonMapper;
import es.npatarino.android.gotchallenge.houses.data.HouseRepository;
import es.npatarino.android.gotchallenge.houses.data.source.local.HouseLocalDataSource;
import es.npatarino.android.gotchallenge.houses.data.source.local.mapper.BddHouseMapper;
import es.npatarino.android.gotchallenge.houses.data.source.network.HouseNetworkDataSource;
import es.npatarino.android.gotchallenge.houses.domain.HousesDomain;
import okhttp3.OkHttpClient;

@Module
public class HouseModule {

    @Provides
    @HouseScope
    public BddHouseMapper provideBddHouseMapper() {
        return new BddHouseMapper();
    }

    @Provides
    @HouseScope
    public HousesDomain.LocalDataSource provideHouseLocalDataSource(TTLCachingStrategy cachingStrategy,
                                                                    TimeProvider timeProvider,
                                                                    BddHouseMapper mapper) {
        return new HouseLocalDataSource(cachingStrategy, timeProvider, mapper);
    }

    @Provides
    @HouseScope
    public HousesDomain.NetworkDataSource
    provideHouseRemoteDataSource(CharacterJsonMapper jsonMapper,
                                 EndPoint endPoint,
                                 OkHttpClient client) {
        return new HouseNetworkDataSource(jsonMapper, endPoint, client);
    }

    @Provides
    @HouseScope
    public HousesDomain.Repository provideGotHouseRepository(HousesDomain.NetworkDataSource networkDataSource,
                                                             HousesDomain.LocalDataSource localDataSource) {
        return new HouseRepository(networkDataSource, localDataSource);
    }

}
