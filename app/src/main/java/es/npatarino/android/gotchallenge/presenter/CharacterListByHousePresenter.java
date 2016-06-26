package es.npatarino.android.gotchallenge.presenter;

import java.util.List;

import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.houses.domain.model.House;
import es.npatarino.android.gotchallenge.presenter.common.Presenter;
import es.npatarino.android.gotchallenge.view.DetailView;

public interface CharacterListByHousePresenter extends Presenter<DetailView<List<GoTCharacter>>> {
    void init(House viewModel);
}
