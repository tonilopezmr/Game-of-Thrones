package es.npatarino.android.gotchallenge.presenter;

import es.npatarino.android.gotchallenge.domain.Character;
import es.npatarino.android.gotchallenge.domain.interactor.common.GetListUseCase;
import es.npatarino.android.gotchallenge.presenter.common.ListPresenterImp;

public class CharacterListPresenterImp extends ListPresenterImp<Character> implements CharacterListPresenter{
    public CharacterListPresenterImp(GetListUseCase<Character> listUseCase) {
        super(listUseCase);
    }
}
