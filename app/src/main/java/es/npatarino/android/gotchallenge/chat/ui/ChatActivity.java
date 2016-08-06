package es.npatarino.android.gotchallenge.chat.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import es.npatarino.android.gotchallenge.GotChallengeApplication;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.base.di.modules.ActivityModule;
import es.npatarino.android.gotchallenge.base.ui.CircleTransform;
import es.npatarino.android.gotchallenge.base.ui.Utilities;
import es.npatarino.android.gotchallenge.base.ui.messages.ErrorManager;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.conversation.presenter.ConversationPresenter;
import es.npatarino.android.gotchallenge.chat.conversation.view.ConversationView;
import es.npatarino.android.gotchallenge.chat.di.DaggerChatComponent;
import es.npatarino.android.gotchallenge.common.view.activities.DetailActivity;

import javax.inject.Inject;

public class ChatActivity extends AppCompatActivity implements ConversationView {

    public static final String CONVER_ID_KEY = "_conver_id_key";

    private View rootView;
    private Toolbar toolbar;

    @Inject
    ErrorManager errorManager;
    @Inject
    ConversationPresenter presenter;
    @Inject
    Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initDagger();

        rootView = findViewById(R.id.container);

        final String id = getIntent().getStringExtra(DetailActivity.HOUSE_ID);

        presenter.setView(this);
        presenter.init(new Conversation(id, null, null, null, null));
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initDagger() {
        GotChallengeApplication app = (GotChallengeApplication) getApplication();
        DaggerChatComponent.builder()
                .appComponent(app.getAppComponent())
                .activityModule(new ActivityModule(this))
                .build().inject(this);
    }

    @Override
    public void show(Conversation conversation) {
        getAvatarDrawable(conversation.getImageUrl());
        toolbar.setTitle(conversation.getName());
        toolbar.setSubtitle(conversation.getParticipants()
                .toString()
                .replace("[", "")
                .replace("]", "")); //Todo could be house words
    }

    private void getAvatarDrawable(String imageUrl) {
        int px = Utilities.dp(context, 50);
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                setAvatar(new BitmapDrawable(getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                setAvatar(errorDrawable);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                setAvatar(placeHolderDrawable);
            }
        };
        Picasso.with(getApplicationContext())
                .load(imageUrl)
                .transform(new CircleTransform())
                .resize(px, px)
                .centerCrop()
                .into(target);
        toolbar.setTag(target);
    }

    @Override
    public void initChat() {
        attachFragment(new ChatFragment());
    }

    @Override
    public void initUi() {
        initToolbar();
    }

    @Override
    public void error() {
        errorManager.showError(rootView, "ERROR :(");
    }

    private void attachFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment, "detail_activity_fragment")
                .commitAllowingStateLoss();
    }


    public void setAvatar(Drawable avatar) {
        if (avatar == null) return;

        toolbar.setLogo(avatar);
        toolbar.setTitleMarginStart(Utilities.dp(context, 30));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return true;
    }
}
