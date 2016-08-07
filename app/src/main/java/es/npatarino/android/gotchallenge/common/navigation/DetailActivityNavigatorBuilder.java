package es.npatarino.android.gotchallenge.common.navigation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import es.npatarino.android.gotchallenge.base.navigator.Navigator;
import es.npatarino.android.gotchallenge.common.view.activities.DetailActivity;

import java.lang.ref.WeakReference;

public class DetailActivityNavigatorBuilder implements Navigator {

    private WeakReference<Activity> activity;
    private View transitionView;
    private String transitionName;
    private String id;
    private String name;
    private String description;
    private String imageUrl;

    public DetailActivityNavigatorBuilder(Activity activity) {
        this.activity = new WeakReference<Activity>(activity);
    }

    public DetailActivityNavigatorBuilder makeTransition(View itemView, String transitionName) {
        this.transitionView = itemView;
        this.transitionName = transitionName;
        return this;
    }

    public DetailActivityNavigatorBuilder id(String id) {
        this.id = id;
        return this;
    }

    public DetailActivityNavigatorBuilder name(String name) {
        this.name = name;
        return this;
    }

    public DetailActivityNavigatorBuilder description(String description) {
        this.description = description;
        return this;
    }


    public DetailActivityNavigatorBuilder imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @Override
    public void navigate() {
        Bundle bundle = getAnimationBundle();

        Intent intent = getIntent();
        ActivityCompat.startActivity(activity.get(), intent, bundle);
    }

    private Bundle getAnimationBundle() {
        Bundle bundle;

        if (transitionView == null) {
            bundle = new Bundle();
        } else {
            bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity.get(), transitionView, transitionName).toBundle();
        }

        return bundle;
    }

    @NonNull
    private Intent getIntent() {
        Intent intent = new Intent(activity.get(), DetailActivity.class);

        if (id == null){
            intent.putExtra(DetailActivity.NAME, name);
            intent.putExtra(DetailActivity.DESCRIPTION, description);
            intent.putExtra(DetailActivity.IMAGE_URL, imageUrl);
        } else {
            intent.putExtra(DetailActivity.HOUSE_ID, id);
            intent.putExtra(DetailActivity.NAME, name);
            intent.putExtra(DetailActivity.IMAGE_URL, imageUrl);
        }

        return intent;
    }
}
