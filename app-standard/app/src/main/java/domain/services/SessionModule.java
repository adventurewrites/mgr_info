package domain.services;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

@Module()
public class SessionModule {
    @Provides
    Persistence providePersistence() {
        return new Persistence();
    }

    @Provides
    Endpoints provideEndPoints() {
        return new RestAdapter.Builder()
                .setEndpoint(ConfigEndpoints.URL_BASE)
                .build().create(Endpoints.class);
    }

    @Provides
    Session provideSession(Endpoints endpoints, Persistence persistence) {
        return new Session(endpoints, persistence);
    }
}