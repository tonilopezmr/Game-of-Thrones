package es.npatarino.android.gotchallenge.presenter;

import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.common.interactor.GetListUseCase;
import es.npatarino.android.gotchallenge.presenter.common.ListPresenterImp;

public class CharacterListPresenterImp extends ListPresenterImp<GoTCharacter> implements CharacterListPresenter{
    public CharacterListPresenterImp(GetListUseCase<GoTCharacter> listUseCase) {
        super(listUseCase);
    }
}
