package es.npatarino.android.gotchallenge.common.view.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import es.npatarino.android.gotchallenge.characters.list.view.fragment.CharacterListByHouseFragment;
import es.npatarino.android.gotchallenge.characters.detail.view.fragments.CharacterDescriptionFragment;

public class DetailActivity extends AppCompatActivity{

    private static final String TAG = "DetailActivity";
    public static final String CHARACTER_IMAGE = "character.image";
    public static final String HOUSE_IMAGE = "house.image";

    public static final String HOUSE_ID = "id";
    public static final String DESCRIPTION = "description";
    public static final String NAME = "name";
    public static final String IMAGE_URL = "imageUrl";

    private String transitionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        transitionName = CHARACTER_IMAGE;
        final String id = getIntent().getStringExtra(HOUSE_ID);
        final String description = getIntent().getStringExtra(DESCRIPTION);
        final String name = getIntent().getStringExtra(NAME);
        final String imageUrl = getIntent().getStringExtra(IMAGE_URL);

        initToolbar(name);
        initFragment(id, description, name);
        showImage(imageUrl);
    }

    private void showImage(String imageUrl) {
        ImageView ivp = (ImageView) findViewById(R.id.iv_photo);
        initTransitionInImageView(ivp);
        Picasso.with(getApplicationContext()).load(imageUrl).into(ivp);
    }

    private void initTransitionInImageView(ImageView ivp) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivp.setTransitionName(transitionName);
        }
    }

    private void initFragment(String id, String description, String name) {
        if (id != null){
            initGotCharacterListByHouseFragment(id, name);
        }else{
            initDescriptionFragment(description, name);
        }
    }

    private void attachFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment, "detail_activity_fragment")
                .commitAllowingStateLoss();
    }

    private void initToolbar(String name) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @NonNull
    private void initDescriptionFragment(String description, String name) {
        CharacterDescriptionFragment descriptionFragment = new CharacterDescriptionFragment();
        descriptionFragment.setName(name);
        descriptionFragment.setDescription(description);
        attachFragment(descriptionFragment);
    }

    @NonNull
    private void initGotCharacterListByHouseFragment(String id, String name) {
        GoTHouse house = new GoTHouse();
        house.setHouseId(id);
        house.setHouseName(name);

        CharacterListByHouseFragment characterFragment= new CharacterListByHouseFragment();
        characterFragment.setHouse(house);
        attachFragment(characterFragment);
        transitionName = HOUSE_IMAGE;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return true;
    }
}
