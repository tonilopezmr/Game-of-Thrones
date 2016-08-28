package es.npatarino.android.gotchallenge.characters.di;

import dagger.Subcomponent;
import es.npatarino.android.gotchallenge.characters.list.view.fragment.CharacterListByHouseFragment;
import es.npatarino.android.gotchallenge.characters.list.view.fragment.CharacterListFragment;
import es.npatarino.android.gotchallenge.common.di.activity.ActivityScope;

@ActivityScope
@Subcomponent(modules = {
        CharacterListActivityModule.class
})
public interface CharacterListActivityComponent {

    CharacterListByHouseFragment inject(CharacterListByHouseFragment fragment);
    CharacterListFragment inject(CharacterListFragment fragment);
}
