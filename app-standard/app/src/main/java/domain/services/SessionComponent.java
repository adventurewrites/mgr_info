package domain.services;

import javax.inject.Singleton;

import dagger.Component;
import ui.activities.BaseActivity;
import ui.fragments.BaseFragment;

@Singleton
@Component(modules = {SessionModule.class})
public interface SessionComponent {
    Session provideSession();

    void injectClient(BaseActivity baseActivity);

    void injectClient(BaseFragment baseFragment);
}