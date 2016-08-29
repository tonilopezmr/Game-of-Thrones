package es.npatarino.android.gotchallenge.base.ui.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.squareup.picasso.Target;

public abstract class ImageLoader {
    public abstract ImageLoaderBuilder Builder();

    public interface ImageLoaderBuilder {
        void show();

        ImageLoaderBuilder with(Context context);
        ImageLoaderBuilder load(String url);
        ImageLoaderBuilder load(Integer resourceId);
        ImageLoaderBuilder into(ImageView imageView);
        ImageLoaderBuilder into(Object target);
        ImageLoaderBuilder placeHolder(Drawable placeholder);
        ImageLoaderBuilder resize(int targetWidth, int targetHeight);
        ImageLoaderBuilder centerCrop();
        ImageLoaderBuilder fit();

        ImageLoaderBuilder circle();
    }

    public abstract class Builder implements ImageLoaderBuilder {

        protected String url;
        protected Integer resourceId;
        protected ImageView imageView;
        protected Drawable placeholder;
        protected Context context;
        protected boolean centerCrop;
        protected boolean fit;
        protected int with;
        protected int height;

        public Builder(){
        }

        @Override
        public ImageLoaderBuilder with(Context context) {
            this.context = context;
            return this;
        }

        @Override
        public ImageLoaderBuilder load(String url) {
            this.url = url;
            return this;
        }

        @Override
        public ImageLoaderBuilder load(Integer resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        @Override
        public ImageLoaderBuilder into(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        @Override
        public ImageLoaderBuilder placeHolder(Drawable placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        @Override
        public ImageLoaderBuilder resize(int targetWidth, int targetHeight) {
            this.with = targetWidth;
            this.height = targetHeight;
            return this;
        }

        @Override
        public ImageLoaderBuilder centerCrop() {
            this.centerCrop = true;
            return this;
        }

        @Override
        public ImageLoaderBuilder fit() {
            this.fit = true;
            return this;
        }
    }
}