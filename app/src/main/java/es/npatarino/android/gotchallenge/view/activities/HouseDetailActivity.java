package es.npatarino.android.gotchallenge.view.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.domain.GoTHouse;
import es.npatarino.android.gotchallenge.view.fragment.GotCharacterListByHouseFragment;

/**
 * @author Antonio López.
 */
public class HouseDetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    public static final String HOUSE_IMAGE = "house.image";

    ImageView ivp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivp = (ImageView) findViewById(R.id.iv_photo);

        initImageView(ivp);

        final String id = getIntent().getStringExtra("id");
        final String n = getIntent().getStringExtra("name");
        final String i = getIntent().getStringExtra("imageUrl");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(n);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        GoTHouse house = new GoTHouse();
        house.setHouseId(id);
        house.setHouseName(n);

        GotCharacterListByHouseFragment houseFragment = new GotCharacterListByHouseFragment();
        houseFragment.setHouse(house);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, houseFragment, "descriptionFragment")
                .commitAllowingStateLoss();

        Picasso.with(getApplicationContext()).load(i).into(ivp);
    }

    private static void initImageView(ImageView ivp) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ivp.setTransitionName(HOUSE_IMAGE);
        }
    }
}
