package es.npatarino.android.gotchallenge.common.ui.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;
import es.npatarino.android.gotchallenge.characters.list.ui.CharacterListByHouseFragment;
import es.npatarino.android.gotchallenge.base.ui.imageloader.ImageLoader;
import es.npatarino.android.gotchallenge.base.ui.imageloader.PicassoImageLoader;
import es.npatarino.android.gotchallenge.characters.detail.CharacterDescriptionFragment;

public class DetailActivity extends AppCompatActivity {

    public static final String CHARACTER_IMAGE = "character.image";
    public static final String HOUSE_IMAGE = "house.image";

    public static final String HOUSE_ID = "id";
    public static final String DESCRIPTION = "description";
    public static final String NAME = "name";
    public static final String IMAGE_URL = "imageUrl";

    private String transitionName;

    ImageLoader imageLoader = new PicassoImageLoader();

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
        initFragment(id, description, name, imageUrl);
        showImage(imageUrl);
    }

    private void showImage(String imageUrl) {
        ImageView photoImageView = (ImageView) findViewById(R.id.photoImageView);
        initTransitionInImageView(photoImageView);
        imageLoader.builder()
                .load(imageUrl)
                .into(photoImageView)
                .show();
    }

    private void initTransitionInImageView(ImageView ivp) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivp.setTransitionName(transitionName);
        }
    }

    private void initFragment(String id, String description, String name, String imageUrl) {
        if (id != null) {
            initGotCharacterListByHouseFragment(id, name, imageUrl);
        } else {
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
    private void initGotCharacterListByHouseFragment(String id, String name, String imageUrl) {
        GoTHouse house = new GoTHouse(id, name, imageUrl);

        CharacterListByHouseFragment characterFragment = new CharacterListByHouseFragment();
        characterFragment.setHouse(house);
        attachFragment(characterFragment);
        transitionName = HOUSE_IMAGE;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return true;
    }
}
