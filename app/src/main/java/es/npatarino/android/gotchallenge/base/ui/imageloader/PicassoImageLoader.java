package es.npatarino.android.gotchallenge.base.ui.imageloader;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;
import es.npatarino.android.gotchallenge.base.ui.CircleTransform;

public class PicassoImageLoader extends ImageLoader {

    public PicassoImageLoader() {
    }

    @Override
    public Builder Builder() {
        return new Builder();
    }

    public class Builder extends ImageLoader.Builder {

        private Transformation transformation;
        private Target target;

        private Builder() {
            super();
        }

        @Override
        public void show() {
            RequestCreator creator = Picasso.with(imageView != null ? imageView.getContext() : context)
                    .load(url);

            if (transformation != null) {
                creator.transform(transformation);
            }

            if (placeholder != null) {
                creator.placeholder(placeholder);
            }

            if (with > 0 && height > 0) {
                creator.resize(with, height);
            }

            if (centerCrop) {
                creator.centerCrop();
            }

            if (fit) {
                creator.fit();
            }

            if (target != null) {
                creator.into(target);
            } else {
                creator.into(imageView);
            }
        }

        public ImageLoaderBuilder into(Object target) {
            this.target = ((Target) target);
            return this;
        }

        @Override
        public ImageLoaderBuilder circle() {
            this.transformation = new CircleTransform();
            return this;
        }
    }
}
