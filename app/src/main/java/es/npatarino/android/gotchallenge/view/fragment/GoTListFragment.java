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
import es.npatarino.android.gotchallenge.domain.GotHouseRepository.GotCharacterRepository;
import es.npatarino.android.gotchallenge.domain.interactor.common.GetListUseCase;
import es.npatarino.android.gotchallenge.domain.interactor.common.GetListUseCaseImp;
import es.npatarino.android.gotchallenge.presenter.GotListPresenterImp;
import es.npatarino.android.gotchallenge.presenter.ListPresenter;
import es.npatarino.android.gotchallenge.view.ViewList;
import es.npatarino.android.gotchallenge.view.adapters.GoTAdapter;
import es.npatarino.android.gotchallenge.view.executor.MainThreadImp;
import okhttp3.OkHttpClient;

/**
 * @author Antonio López.
 */
public class GoTListFragment extends Fragment implements ViewList<GoTCharacter> {

    private static final String TAG = "GoTListFragment";
    private RecyclerView rv;
    private ContentLoadingProgressBar pb;
    private ListPresenter<GoTCharacter> gotCharacterListPresenter;
    private GoTAdapter adp;

    public GoTListFragment() {
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
        String endPoint = "http://ec2-52-18-202-124.eu-west-1.compute.amazonaws.com:3000";
        GotCharacterRepository repository = new GotCharacterRepository(new OkHttpClient(), endPoint);
        GetListUseCase<GoTCharacter> goTCharacterGetListUseCase = new GetListUseCaseImp<>(executor, mainThread, repository);
        gotCharacterListPresenter = new GotListPresenterImp<>(goTCharacterGetListUseCase);
        gotCharacterListPresenter.setView(this);
        gotCharacterListPresenter.init();
        return rootView;
    }

    @Override
    public void showList(List<GoTCharacter> list) {
        adp.addAll(list);
        adp.notifyDataSetChanged();
        pb.hide();
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
}
