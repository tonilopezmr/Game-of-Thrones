package es.npatarino.android.gotchallenge.characters.list.presenter;

import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.characters.list.CharacterList;
import es.npatarino.android.gotchallenge.common.interactor.GetListUseCase;
import es.npatarino.android.gotchallenge.common.list.presenter.DefaultListPresenter;

public class CharacterListPresenter extends DefaultListPresenter<GoTCharacter> implements CharacterList.Presenter {
    public CharacterListPresenter(GetListUseCase<GoTCharacter> listUseCase) {
        super(listUseCase);
    }
}
