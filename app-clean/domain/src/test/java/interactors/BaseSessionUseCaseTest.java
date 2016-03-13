package interactors;


import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import schedulers.ObserveOn;
import schedulers.SubscribeOn;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.when;

public abstract class BaseSessionUseCaseTest {
    @Mock protected ObserveOn observeOn;
    @Mock protected SubscribeOn subscribeOn;

    @Before public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(observeOn.getScheduler()).thenReturn(Schedulers.newThread());
        when(subscribeOn.getScheduler()).thenReturn(Schedulers.newThread());
    }
}
