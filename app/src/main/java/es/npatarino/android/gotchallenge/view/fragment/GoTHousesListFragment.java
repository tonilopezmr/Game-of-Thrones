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
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.domain.GotHouseRepository.GotHouseRepository;
import es.npatarino.android.gotchallenge.domain.interactor.GetListUseCase;
import es.npatarino.android.gotchallenge.domain.interactor.GetListUseCaseImp;
import es.npatarino.android.gotchallenge.presenter.GotListPresenterImp;
import es.npatarino.android.gotchallenge.presenter.ListPresenter;
import es.npatarino.android.gotchallenge.view.ViewList;
import es.npatarino.android.gotchallenge.view.adapters.GoTHouseAdapter;
import es.npatarino.android.gotchallenge.view.executor.MainThreadImp;

/**
 * @author Antonio López.
 */
public class GoTHousesListFragment extends Fragment implements ViewList<GoTHouse> {

    private static final String TAG = "GoTHousesListFragment";
    private RecyclerView rv;
    private ContentLoadingProgressBar pb;
    private GoTHouseAdapter adp;
    private ListPresenter<GoTHouse> gotCharacterListPresenter;

    public GoTHousesListFragment() {
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
        GotHouseRepository repository = new GotHouseRepository();
        GetListUseCase<GoTHouse> goTHouseGetListUseCase = new GetListUseCaseImp<>(executor, mainThread, repository);
        gotCharacterListPresenter = new GotListPresenterImp<>(goTHouseGetListUseCase);
        gotCharacterListPresenter.setView(this);
        gotCharacterListPresenter.init();
        return rootView;
    }

    @Override
    public void showList(List<GoTHouse> list) {
        adp.addAll(list);
        adp.notifyDataSetChanged();
        pb.hide();
    }

    @Override
    public void initUi() {
        adp = new GoTHouseAdapter(getActivity());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        rv.setAdapter(adp);
    }

    @Override
    public void error() {
        Toast.makeText(getActivity().getApplicationContext(), "ERRRRRORRR ONEEE", Toast.LENGTH_SHORT).show();
    }
}