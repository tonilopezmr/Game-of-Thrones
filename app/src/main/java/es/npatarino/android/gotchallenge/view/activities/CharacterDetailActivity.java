package es.npatarino.android.gotchallenge.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.view.fragment.DescriptionFragment;

public class CharacterDetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    public static final String CHARACTER_IMAGE = "character.image";

    ImageView ivp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ivp = (ImageView) findViewById(R.id.iv_photo);

        final String d = getIntent().getStringExtra("description");
        final String n = getIntent().getStringExtra("name");
        final String i = getIntent().getStringExtra("imageUrl");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(n);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        DescriptionFragment descriptionFragment = new DescriptionFragment();
        descriptionFragment.setName(n);
        descriptionFragment.setDescription(d);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, descriptionFragment, "descriptionFragment")
                .commitAllowingStateLoss();

        Picasso.with(getApplicationContext()).load(i).into(ivp);
    }
}
