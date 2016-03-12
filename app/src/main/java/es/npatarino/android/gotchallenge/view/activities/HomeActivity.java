package es.npatarino.android.gotchallenge.view.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tonilopezmr.interactorexecutor.Executor;
import com.tonilopezmr.interactorexecutor.MainThread;
import com.tonilopezmr.interactorexecutor.ThreadExecutor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.domain.GoTCharacter;
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.domain.GotHouseRepository.GotCharacterRepository;
import es.npatarino.android.gotchallenge.domain.GotHouseRepository.GotHouseRepository;
import es.npatarino.android.gotchallenge.domain.interactor.GetListUseCase;
import es.npatarino.android.gotchallenge.domain.interactor.GetListUseCaseImp;
import es.npatarino.android.gotchallenge.presenter.GotListPresenterImp;
import es.npatarino.android.gotchallenge.presenter.ListPresenter;
import es.npatarino.android.gotchallenge.view.ViewList;
import es.npatarino.android.gotchallenge.view.executor.MainThreadImp;

public class HomeActivity extends AppCompatActivity {

    SectionsPagerAdapter spa;
    ViewPager vp;
    Toolbar toolbar;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUi();
    }

    public SectionsPagerAdapter getSpa() {
        return spa;
    }

    public void setSpa(SectionsPagerAdapter spa) {
        this.spa = spa;
    }

    public ViewPager getVp() {
        return vp;
    }

    public void setVp(ViewPager vp) {
        this.vp = vp;
    }


    public void initUi() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSpa(new SectionsPagerAdapter(getSupportFragmentManager()));

        setVp((ViewPager) findViewById(R.id.container));
        getVp().setAdapter(getSpa());

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(getVp());
    }


    public static class GoTListFragment extends Fragment implements ViewList<GoTCharacter>  {

        private static final String TAG = "GoTListFragment";
        private RecyclerView rv;
        private ContentLoadingProgressBar pb;
        private ListPresenter<GoTCharacter> gotCharacterListPresenter;
        private  GoTAdapter adp;

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
            GotCharacterRepository repository = new GotCharacterRepository();
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

    public static class GoTHousesListFragment extends Fragment implements ViewList<GoTHouse>{

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

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new GoTListFragment();
            } else {
                return new GoTHousesListFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Characters";
                case 1:
                    return "Houses";
            }
            return null;
        }
    }

    static class GoTAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final List<GoTCharacter> gcs;
        private Activity activity;

        public GoTAdapter(Activity activity) {
            this.gcs = new ArrayList<>();
            this.activity = activity;
        }

        void addAll(Collection<GoTCharacter> collection) {
            for (int i = 0; i < collection.size(); i++) {
                gcs.add((GoTCharacter) collection.toArray()[i]);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GotCharacterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.got_character_row, parent, false));
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            final GotCharacterViewHolder gotCharacterViewHolder = (GotCharacterViewHolder) holder;
            final GoTCharacter character = gcs.get(position);
            gotCharacterViewHolder.render(character);
            final GotCharacterViewHolder viewHolder =((GotCharacterViewHolder) holder);
            viewHolder.imp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    moveToDetailActivity(gotCharacterViewHolder, character);
                }
            });
        }

        private void moveToDetailActivity(GotCharacterViewHolder viewHolder, GoTCharacter character){
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, viewHolder.itemView, DetailActivity.CHARACTER_IMAGE);

            Intent intent = new Intent(viewHolder.itemView.getContext(), DetailActivity.class);
            intent.putExtra("description", character.getDescription());
            intent.putExtra("name", character.getName());
            intent.putExtra("imageUrl", character.getImageUrl());
            ActivityCompat.startActivity(activity, intent, options.toBundle());
        }

        @Override
        public int getItemCount() {
            return gcs.size();
        }

        class GotCharacterViewHolder extends RecyclerView.ViewHolder {

            private static final String TAG = "GotCharacterViewHolder";
            ImageView imp;
            TextView tvn;

            public GotCharacterViewHolder(View itemView) {
                super(itemView);
                imp = (ImageView) itemView.findViewById(R.id.ivBackground);
                tvn = (TextView) itemView.findViewById(R.id.tv_name);
            }

            public void render(final GoTCharacter goTCharacter) {
                Picasso.with(imp.getContext()).load(goTCharacter.getImageUrl()).into(imp);
                tvn.setText(goTCharacter.getName());
            }
        }

    }

    static class GoTHouseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final List<GoTHouse> gcs;
        private Activity a;

        public GoTHouseAdapter(Activity activity) {
            this.gcs = new ArrayList<>();
            a = activity;
        }

        void addAll(Collection<GoTHouse> collection) {
            for (int i = 0; i < collection.size(); i++) {
                gcs.add((GoTHouse) collection.toArray()[i]);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new GotCharacterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.got_house_row, parent, false));
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            GotCharacterViewHolder gotCharacterViewHolder = (GotCharacterViewHolder) holder;
            gotCharacterViewHolder.render(gcs.get(position));
        }

        @Override
        public int getItemCount() {
            return gcs.size();
        }

        class GotCharacterViewHolder extends RecyclerView.ViewHolder {

            private static final String TAG = "GotCharacterViewHolder";
            ImageView imp;

            public GotCharacterViewHolder(View itemView) {
                super(itemView);
                imp = (ImageView) itemView.findViewById(R.id.ivBackground);
            }

            public void render(final GoTHouse goTHouse) {
                Picasso.with(imp.getContext()).load(goTHouse.getHouseImageUrl()).into(imp);
            }
        }

    }
}
