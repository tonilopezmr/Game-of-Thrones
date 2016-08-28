package es.npatarino.android.gotchallenge.characters.list.presenter;

import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.base.list.interactor.GetListUseCase;
import es.npatarino.android.gotchallenge.base.list.presenter.DefaultListPresenter;

public class CharacterListPresenter
        extends DefaultListPresenter<GoTCharacter> {
    public CharacterListPresenter(GetListUseCase<GoTCharacter> listUseCase) {
        super(listUseCase);
    }
}
