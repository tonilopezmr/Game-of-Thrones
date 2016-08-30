package es.npatarino.android.gotchallenge.chat.ui;

import android.app.Activity;
import android.content.Intent;
import es.npatarino.android.gotchallenge.base.navigator.Navigator;

import java.lang.ref.WeakReference;

public class ChatActivityNavigator implements Navigator {

    private WeakReference<Activity> activity;
    private String id;

    public ChatActivityNavigator(Activity activity, String id) {
        this.activity = new WeakReference<Activity>(activity);
        this.id = id;
    }

    @Override
    public void navigate() {
        Intent intent = new Intent(activity.get(), ChatActivity.class);
        intent.putExtra(ChatActivity.CONVER_ID_KEY, id);
        activity.get().startActivity(intent);
    }
}
