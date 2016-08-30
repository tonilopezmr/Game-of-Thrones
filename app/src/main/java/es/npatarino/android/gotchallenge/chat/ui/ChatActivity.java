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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import es.npatarino.android.gotchallenge.GotChallengeApplication;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.base.ui.CircleTransform;
import es.npatarino.android.gotchallenge.base.ui.Utilities;
import es.npatarino.android.gotchallenge.base.ui.messages.ErrorManager;
import es.npatarino.android.gotchallenge.chat.conversation.model.Conversation;
import es.npatarino.android.gotchallenge.chat.conversation.presenter.ConversationPresenter;
import es.npatarino.android.gotchallenge.chat.di.ChatActivityModule;
import es.npatarino.android.gotchallenge.common.navigation.DetailActivityNavigatorBuilder;

import javax.inject.Inject;

public class ChatActivity extends AppCompatActivity implements ConversationPresenter.View {

    public static final String CONVER_ID_KEY = "_conver_id_key";
    public static final String CHAT_ACTIVITY_FRAGMENT = "chat_activity_fragment";

    private android.view.View rootView;
    private Toolbar toolbar;

    @Inject
    ErrorManager errorManager;
    @Inject
    ConversationPresenter presenter;
    @Inject
    Context context;

    private Conversation conversation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initDagger();

        final String id = getIntent().getStringExtra(CONVER_ID_KEY);

        presenter.setView(this);
        presenter.init(new Conversation(id, null, null, null, null));
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setOnClickListener(view -> moveToDetailActivity(conversation));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void moveToDetailActivity(Conversation conversation) {
        new DetailActivityNavigatorBuilder(this)
                .id(conversation.getId())
                .name(conversation.getName())
                .imageUrl(conversation.getImageUrl())
                .navigate();
    }

    private void initDagger() {
        GotChallengeApplication.get(this)
                .getConversationComponent()
                .plus(new ChatActivityModule())
                .inject(this);
    }

    @Override
    public void show(Conversation conversation) {
        this.conversation = conversation;
        getAvatarDrawable(conversation.getImageUrl());
        toolbar.setTitle(conversation.getName());
        toolbar.setSubtitle(conversation.getUsers()
                .toString()
                .replace("[", "")
                .replace("]", "")); //Todo could be house words
    }

    private void getAvatarDrawable(String imageUrl) {
        int px = Utilities.dp(context, 40);
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
        rootView = findViewById(R.id.container);
        initToolbar();
    }

    @Override
    public void error() {
        errorManager.showError(rootView, "ERROR :(");
    }

    private void attachFragment(Fragment fragment) {
        Bundle args = new Bundle();
        args.putString(ChatActivity.CONVER_ID_KEY, conversation.getId());
        fragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment, CHAT_ACTIVITY_FRAGMENT)
                .commitAllowingStateLoss();
    }


    public void setAvatar(Drawable avatar) {
        if (avatar == null) return;

        toolbar.setLogo(avatar);
        toolbar.setTitleMarginStart(Utilities.dp(context, 25));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(CHAT_ACTIVITY_FRAGMENT);
        if (fragment != null && fragment instanceof OnBackListener) {
            boolean isOnBackFragment = ((OnBackListener) fragment).onBackListener();
            if (isOnBackFragment) {
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GotChallengeApplication gotChallengeApplication = GotChallengeApplication.get(getApplicationContext());
        gotChallengeApplication.releaseConversationComponent();
        gotChallengeApplication.releaseMessageComponent();
    }
}
