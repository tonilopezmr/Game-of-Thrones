package es.npatarino.android.gotchallenge.presenter;

import java.util.List;

import es.npatarino.android.gotchallenge.domain.Character;
import es.npatarino.android.gotchallenge.domain.House;
import es.npatarino.android.gotchallenge.presenter.common.Presenter;
import es.npatarino.android.gotchallenge.view.DetailView;

public interface CharacterListByHousePresenter extends Presenter<DetailView<List<Character>>> {
    void init(House viewModel);
}
