package es.npatarino.android.gotchallenge.testingtools;

import android.graphics.Bitmap;
import com.squareup.picasso.Target;
import es.npatarino.android.gotchallenge.base.ui.imageloader.ImageLoader;

public class TestPicassoImageLoader extends ImageLoader {

  @Override
  public ImageLoaderBuilder builder() {
    return new Builder();
  }

  public class Builder extends ImageLoader.Builder {

    private Target target;

    @Override
    public void show() {
      if (target != null) {
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(100, 100, conf);
        target.onBitmapLoaded(bmp, null);
      }
    }

    @Override
    public ImageLoaderBuilder into(Object target) {
      this.target = ((Target) target);
      return this;
    }


    @Override
    public ImageLoaderBuilder circle() {
      return this;
    }
  }
}

