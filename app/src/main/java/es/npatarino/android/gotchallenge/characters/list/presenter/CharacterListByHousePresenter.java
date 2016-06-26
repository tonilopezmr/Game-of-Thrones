package es.npatarino.android.gotchallenge.characters.list.presenter;

import java.util.List;

import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.characters.list.CharacterList;
import es.npatarino.android.gotchallenge.houses.domain.model.House;
import es.npatarino.android.gotchallenge.characters.domain.interactor.GetCharactersByHouseUseCase;
import es.npatarino.android.gotchallenge.common.detail.view.DetailView;
import rx.Subscription;

public class CharacterListByHousePresenter implements CharacterList.ByHousePresenter {

    private DetailView<List<GoTCharacter>> view;
    private GetCharactersByHouseUseCase useCase;

    private Subscription charactersSubscription;

    public CharacterListByHousePresenter(GetCharactersByHouseUseCase useCase) {
        this.useCase = useCase;
    }

    @Override
    public void init() {
        view.initUi();
    }

    @Override
    public void setView(DetailView<List<GoTCharacter>> view) {
        if (view == null) new IllegalArgumentException("oh my god... you are **");
        this.view = view;
    }

    @Override
    public void onDestroy() {
        charactersSubscription.unsubscribe();
    }

    @Override
    public void init(House viewModel) {
        init();
        askForCharacters(viewModel);
    }

    private void askForCharacters(House viewModel) {
        charactersSubscription = useCase.execute(viewModel)
                .subscribe(this::onCharactersReceived, this::onError);
    }

    private void onCharactersReceived(List<GoTCharacter> characters){
        view.show(characters);
    }

    private void onError(Throwable error){
        view.error();
    }
}
