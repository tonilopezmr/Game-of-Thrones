package es.npatarino.android.gotchallenge.characters.list.presenter;

import es.npatarino.android.gotchallenge.base.detail.view.DetailView;
import es.npatarino.android.gotchallenge.base.list.presenter.BasePresenter;
import es.npatarino.android.gotchallenge.characters.domain.usecases.GetCharactersByHouseUseCase;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;

import java.util.List;

public class CharacterListByHousePresenter extends BasePresenter<DetailView<List<GoTCharacter>>> {

    private GetCharactersByHouseUseCase useCase;

    public CharacterListByHousePresenter(GetCharactersByHouseUseCase useCase) {
        this.useCase = useCase;
    }

    public void init(GoTHouse viewModel) {
        super.init();
        askForCharacters(viewModel);
    }

    private void askForCharacters(GoTHouse viewModel) {
        addSubscription(useCase.execute(viewModel)
                .subscribe(this::onCharactersReceived, this::onError));
    }

    private void onCharactersReceived(List<GoTCharacter> characters) {
        view.show(characters);
    }

    private void onError(Throwable error) {
        view.error();
    }
}
