package es.npatarino.android.gotchallenge.presenter;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.view.DetailView;

/**
 * @author Antonio López.
 */
public interface GotCharacterListByHousePresenter extends Presenter<DetailView<List<GoTCharacter>>>{
    void init(GoTHouse viewModel);
}
