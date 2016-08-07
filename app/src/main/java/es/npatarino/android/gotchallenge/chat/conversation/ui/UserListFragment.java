package es.npatarino.android.gotchallenge.chat.conversation.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.base.list.view.ViewList;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.User;

import java.util.List;

public class UserListFragment extends Fragment implements ViewList<User> {

    private RecyclerView recyclerView;
    private ContentLoadingProgressBar progressBar;
    private UserAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        progressBar = (ContentLoadingProgressBar) rootView.findViewById(R.id.content_loading_progress_bar);

        initUi();
        return rootView;
    }

    @Override
    public void showList(List<User> list) {
        adapter.addAll(list);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void initUi() {
        adapter = new UserAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void error() {
        Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
    }
}
