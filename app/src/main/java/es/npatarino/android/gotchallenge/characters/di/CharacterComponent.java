package es.npatarino.android.gotchallenge.characters.di;

import dagger.Subcomponent;

@CharacterScope
@Subcomponent( modules = CharacterModule.class )
public interface CharacterComponent {

    CharacterListActivityComponent plus(CharacterListActivityModule activityModule);

}
