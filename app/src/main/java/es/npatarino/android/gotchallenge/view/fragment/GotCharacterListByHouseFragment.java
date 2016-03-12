package es.npatarino.android.gotchallenge.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tonilopezmr.interactorexecutor.Executor;
import com.tonilopezmr.interactorexecutor.MainThread;
import com.tonilopezmr.interactorexecutor.ThreadExecutor;

import java.util.List;

import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.domain.GotHouseRepository.GotCharacterRepository;
import es.npatarino.android.gotchallenge.domain.interactor.GetCharactersByHouse;
import es.npatarino.android.gotchallenge.presenter.GotCharacterListByHousePresenter;
import es.npatarino.android.gotchallenge.presenter.GotCharacterListByHousePresenterImp;
import es.npatarino.android.gotchallenge.view.DetailView;
import es.npatarino.android.gotchallenge.view.adapters.GoTAdapter;
import es.npatarino.android.gotchallenge.view.executor.MainThreadImp;

/**
 * @author Antonio López.
 */

public class GotCharacterListByHouseFragment extends Fragment implements DetailView<List<GoTCharacter>> {

    private static final String TAG = "GoTListFragment";
    private RecyclerView rv;
    private ContentLoadingProgressBar pb;
    private GoTAdapter adp;
    private GotCharacterListByHousePresenter gotCharacterListByHousePresenter;
    private GoTHouse house;

    public GotCharacterListByHouseFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.rv);
        pb = (ContentLoadingProgressBar) rootView.findViewById(R.id.pb);
        initUi();

        //dagger everywhere
        Executor executor = new ThreadExecutor();
        MainThread mainThread = new MainThreadImp();
        GotCharacterRepository repository = new GotCharacterRepository();
        GetCharactersByHouse charactersByHouse = new GetCharactersByHouse(executor, mainThread, repository);
        gotCharacterListByHousePresenter = new GotCharacterListByHousePresenterImp(charactersByHouse);
        gotCharacterListByHousePresenter.setView(this);
        gotCharacterListByHousePresenter.init(house);
        return rootView;
    }

    public void setHouse(GoTHouse house) {
        this.house = house;
    }


    @Override
    public void initUi() {
        adp = new GoTAdapter(getActivity());
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