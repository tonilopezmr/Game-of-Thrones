package es.npatarino.android.gotchallenge.characters.list.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import es.npatarino.android.gotchallenge.GotChallengeApplication;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.base.detail.view.DetailView;
import es.npatarino.android.gotchallenge.base.di.modules.ActivityModule;
import es.npatarino.android.gotchallenge.characters.di.CharactersModule;
import es.npatarino.android.gotchallenge.characters.di.DaggerCharactersComponent;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.characters.list.presenter.CharacterListByHousePresenter;
import es.npatarino.android.gotchallenge.characters.list.view.adapters.CharacterAdapter;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;

import javax.inject.Inject;
import java.util.List;

public class CharacterListByHouseFragment extends Fragment implements DetailView<List<GoTCharacter>> {

    private static final String TAG = CharacterListByHouseFragment.class.getSimpleName();
    private RecyclerView rv;
    private ContentLoadingProgressBar pb;
    private CharacterAdapter adp;
    private GoTHouse house;

    @Inject
    CharacterListByHousePresenter characterListByHousePresenter;


    public CharacterListByHouseFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        initDagger();

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        pb = (ContentLoadingProgressBar) rootView.findViewById(R.id.content_loading_progress_bar);

        characterListByHousePresenter.setView(this);
        characterListByHousePresenter.init(house);
        return rootView;
    }

    private void initDagger() {
        GotChallengeApplication app = (GotChallengeApplication) getActivity().getApplication();
        DaggerCharactersComponent.builder()
                .appComponent(app.getAppComponent())
                .activityModule(new ActivityModule(getActivity()))
                .charactersModule(new CharactersModule())
                .build().inject(this);
    }

    public void setHouse(GoTHouse house) {
        this.house = house;
    }


    @Override
    public void initUi() {
        adp = new CharacterAdapter(getActivity());
        rv.setAdapter(adp);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
    }

    @Override
    public void error() {
        Toast.makeText(getActivity().getApplicationContext(), "ERRRRRORRR ONEEE", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void show(List<GoTCharacter> list) {
        adp.addAll(list);
        adp.notifyDataSetChanged();
        pb.hide();
    }
}