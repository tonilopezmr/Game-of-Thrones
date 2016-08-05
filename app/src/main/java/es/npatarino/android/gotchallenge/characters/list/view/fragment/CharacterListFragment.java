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
import es.npatarino.android.gotchallenge.base.di.modules.ActivityModule;
import es.npatarino.android.gotchallenge.base.list.view.ViewList;
import es.npatarino.android.gotchallenge.characters.di.CharactersModule;
import es.npatarino.android.gotchallenge.characters.di.DaggerCharactersComponent;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.characters.list.presenter.CharacterListPresenter;
import es.npatarino.android.gotchallenge.characters.list.view.adapters.CharacterAdapter;

import javax.inject.Inject;
import java.util.List;

public class CharacterListFragment extends Fragment implements ViewList<GoTCharacter> {

    private static final String TAG = CharacterListFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private ContentLoadingProgressBar progressBar;
    private CharacterAdapter adapter;

    @Inject
    CharacterListPresenter gotCharacterListPresenter;


    public CharacterListFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        initDagger();

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        progressBar = (ContentLoadingProgressBar) rootView.findViewById(R.id.content_loading_progress_bar);

        gotCharacterListPresenter.setView(this);
        gotCharacterListPresenter.init();
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

    @Override
    public void showList(List<GoTCharacter> list) {
        adapter.addAll(list);
        adapter.notifyDataSetChanged();
        progressBar.hide();
    }

    @Override
    public void initUi() {
        adapter = new CharacterAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void error() {
        Toast.makeText(getActivity().getApplicationContext(), "ERRRRRORRR ONEEE", Toast.LENGTH_SHORT).show();
    }
}
