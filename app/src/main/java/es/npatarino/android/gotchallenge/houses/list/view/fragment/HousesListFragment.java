package es.npatarino.android.gotchallenge.houses.list.view.fragment;

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
import es.npatarino.android.gotchallenge.base.list.view.ViewList;
import es.npatarino.android.gotchallenge.houses.di.HouseListActivityModule;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import es.npatarino.android.gotchallenge.houses.list.presenter.HouseListPresenter;
import es.npatarino.android.gotchallenge.houses.list.view.adapters.HouseAdapter;

import javax.inject.Inject;
import java.util.List;

public class HousesListFragment extends Fragment implements ViewList<GoTHouse> {

    private static final String TAG = HousesListFragment.class.getSimpleName();
    private RecyclerView rv;
    private ContentLoadingProgressBar pb;
    private HouseAdapter adp;

    @Inject
    HouseListPresenter gotHouseListPresenter;

    public HousesListFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        initDagger();
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        rv = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        pb = (ContentLoadingProgressBar) rootView.findViewById(R.id.content_loading_progress_bar);

        gotHouseListPresenter.setView(this);
        gotHouseListPresenter.init();
        return rootView;
    }

    private void initDagger() {
        GotChallengeApplication.get(getContext())
                .getHouseComponent()
                .plus(new HouseListActivityModule())
                .inject(this);
    }

    @Override
    public void showList(List<GoTHouse> list) {
        adp.addAll(list);
        adp.notifyDataSetChanged();
        pb.hide();
    }

    @Override
    public void initUi() {
        adp = new HouseAdapter(getActivity());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.setHasFixedSize(true);
        rv.setAdapter(adp);
    }

    @Override
    public void error() {
        Toast.makeText(getActivity().getApplicationContext(), "ERRRRRORRR ONEEE", Toast.LENGTH_SHORT).show();
    }
}