package net.mobindustry.emojilib.emoji;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.IOException;
import java.io.InputStream;

public class ImageLoaderHelper {

    private static class CustomImageDownloader extends BaseImageDownloader {

        public CustomImageDownloader(Context context) {
            super(context);
        }

        @Override
        public InputStream getStream(String imageUri, Object extra) throws IOException {
            return super.getStream(imageUri, extra);
        }
    }

    private static ImageLoader imageLoader;

    private static DisplayImageOptions defaultOptionsFadeIn = new DisplayImageOptions.Builder()
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .resetViewBeforeLoading(true)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .displayer(new FadeInBitmapDisplayer(500))
            .build();


    public static ImageLoader initImageLoader(Context context) {
        imageLoader = ImageLoader.getInstance();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .diskCacheSize(50 * 1024 * 1024)
                .defaultDisplayImageOptions(defaultOptionsFadeIn)
                .imageDownloader(new CustomImageDownloader(context)) // setup custom loader
                .build();

        imageLoader.init(config);
        return imageLoader;
    }

    public static void displayImageWithoutFadeIn(final String url, final ImageView imageView) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        imageLoader.displayImage(url, imageView, options);
    }
}
