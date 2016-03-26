package es.npatarino.android.gotchallenge.presenter;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.interactor.common.GetListUseCase;
import es.npatarino.android.gotchallenge.presenter.common.GotListPresenterImp;

/**
 * @author Antonio López.
 */
public class CharacterListPresenterImp extends GotListPresenterImp<GoTCharacter> implements CharacterListPresenter{
    public CharacterListPresenterImp(GetListUseCase<GoTCharacter> listUseCase) {
        super(listUseCase);
    }
}
