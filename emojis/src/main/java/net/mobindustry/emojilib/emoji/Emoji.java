/*
 * This is the source code of Telegram for Android v. 1.3.2.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013.
 */

package net.mobindustry.emojilib.emoji;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.Log;

import net.mobindustry.emojilib.DpCalculator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.WeakHashMap;

public class Emoji {

    private static Emoji instance;
    private final HashMap<Long, DrawableInfo> rects = new HashMap<>();
    private final DpCalculator dpCalculator;
    private int drawImgSize, bigImgSize;
    private final Paint placeholderPaint;
    private final Bitmap emojiBmp[] = new Bitmap[5];
    private final boolean loadingEmoji[] = new boolean[5];

    private int[] cols = EmojiArrays.getCols();
    private char[] emojiChars = EmojiArrays.getEmojiChars();
    private long[][] data = EmojiArrays.getData();

    private final Context ctx;

    public Emoji(Context ctx, DpCalculator dpCalculator) {
        this.ctx = ctx;
        this.dpCalculator = dpCalculator;

        int emojiFullSize;
        if (this.dpCalculator.density <= 1.0f) {
            emojiFullSize = 30;
        } else if (this.dpCalculator.density <= 1.5f) {
            emojiFullSize = 45;
        } else if (this.dpCalculator.density <= 2.0f) {
            emojiFullSize = 60;
        } else {
            emojiFullSize = 90;
        }
        drawImgSize = this.dpCalculator.dp(20);
        bigImgSize = this.dpCalculator.dp(30);
        for (int j = 1; j < data.length; j++) {
            for (int i = 0; i < data[j].length; i++) {
                Rect rect = new Rect((i % cols[j - 1]) * emojiFullSize, (i / cols[j - 1]) * emojiFullSize, (i % cols[j - 1] + 1) * emojiFullSize, (i / cols[j - 1] + 1) * emojiFullSize);
                rects.put(data[j][i], new DrawableInfo(rect, (byte) (j - 1)));
            }
        }
        placeholderPaint = new Paint();
        placeholderPaint.setColor(0x00000000);

        this.instance = this;
    }

    public static Emoji getInstance() {
        return instance;
    }

    public void makeEmoji(EmojiCallback callback) {
        loadEmojiAsync(0);
        loadEmojiAsync(1);
        loadEmojiAsync(2);
        loadEmojiAsync(3);
        boolean loaded = loadEmoji(4);
        while (loaded) {
            callback.loaded();
            break;
        }
    }

    private boolean loadEmoji(final int page) {
        File maskedFile = getMaskedFile(page);
        if (maskedFile.exists()) {
            loadMasked(page, maskedFile);
        }
        try {
            float scale = 1.0f;
            int imageResize = 1;
            if (dpCalculator.density <= 1.0f) {
                scale = 2.0f;
                imageResize = 2;
            } else if (dpCalculator.density <= 1.5f) {
                scale = 3.0f;
                imageResize = 2;
            } else if (dpCalculator.density <= 2.0f) {
                scale = 2.0f;
            } else {
                scale = 3.0f;
            }

            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = imageResize;
            InputStream is;

            String imageName;
            imageName = String.format(Locale.US, "emoji%.01fx_a_%d.jpg", scale, page);
            is = ctx.getAssets().open("emoji/" + imageName);
            Bitmap alpha = BitmapFactory.decodeStream(is, null, opts);
            imageName = String.format(Locale.US, "emoji%.01fx_%d.jpg", scale, page);
            is = ctx.getAssets().open("emoji/" + imageName);
            Bitmap colors = BitmapFactory.decodeStream(is, null, opts);

            final Bitmap bitmap = compositeDrawableWithMask(colors, alpha);
            System.gc();

            dispatchPageLoaded(page, bitmap);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(maskedFile));
        } catch (Throwable x) {
            Log.e("Emoji", "Error loading emoji", x);
        }
        if (page == 4) {
            return true;
        } else {
            return false;
        }
    }

    private void dispatchPageLoaded(final int page, final Bitmap bitmap) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                emojiBmp[page] = bitmap;
                synchronized (weakness) {
                    for (EmojiDrawable k : weakness.keySet()) {
                        k.invalidateSelf();
                    }
                }
            }
        });
    }

    private void loadMasked(int page, File maskedFile) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bmp = BitmapFactory.decodeFile(maskedFile.getAbsolutePath(), opts);
        dispatchPageLoaded(page, bmp);
    }

    private File getMaskedFile(int page) {
        File filesDir = ctx.getFilesDir();
        return new File(filesDir, "emoji_masked_" + page);
    }

    private void loadEmojiAsync(final int page) {
        if (loadingEmoji[page]) {
            return;
        }
        loadingEmoji[page] = true;
        new Thread(new Runnable() {
            public void run() {
                loadEmoji(page);
                loadingEmoji[page] = false;
            }
        }).start();
    }

    public static Bitmap compositeDrawableWithMask(
            Bitmap rgbDrawable, Bitmap alphaDrawable) {
        Bitmap rgbBitmap = rgbDrawable;
        Bitmap alphaBitmap = alphaDrawable;
        int width = rgbBitmap.getWidth();
        int height = rgbBitmap.getHeight();
        if (width != alphaBitmap.getWidth() || height != alphaBitmap.getHeight()) {
            throw new IllegalStateException("image size mismatch!");
        }

        Bitmap destBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);

        int[] pixels = new int[width];
        int[] alpha = new int[width];
        for (int y = 0; y < height; y++) {
            rgbBitmap.getPixels(pixels, 0, width, 0, y, width, 1);
            alphaBitmap.getPixels(alpha, 0, width, 0, y, width, 1);

            for (int x = 0; x < width; x++) {
                pixels[x] = (pixels[x] & 0x00FFFFFF) | ((alpha[x] << 8) & 0xFF000000);
            }
            destBitmap.setPixels(pixels, 0, width, 0, y, width, 1);
        }
        return destBitmap;
    }

    public static final Object DUMB = new Object();
    private final WeakHashMap<EmojiDrawable, Object> weakness = new WeakHashMap<>();

    public EmojiDrawable getEmojiDrawable(long code) {
        DrawableInfo info = rects.get(code);
        if (info == null) {
            throw new NullPointerException("no info for code " + code);
        }
        EmojiDrawable ed = new EmojiDrawable(info);
        ed.setBounds(0, 0, drawImgSize, drawImgSize);
        synchronized (weakness) {
            weakness.put(ed, DUMB);
        }
        return ed;
    }

    public Drawable getEmojiBigDrawable(long code) {
        EmojiDrawable ed = getEmojiDrawable(code);
        if (ed == null) {
            return null;
        }
        ed.setBounds(0, 0, bigImgSize, bigImgSize);
        ed.fullSize = true;
        return ed;
    }

    private static Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG | Paint.ANTI_ALIAS_FLAG);

    public class EmojiDrawable extends Drawable {
        private DrawableInfo info;
        private boolean fullSize = false;

        public EmojiDrawable(DrawableInfo i) {
            info = i;
        }

        public Rect getDrawRect() {
            Rect b = copyBounds();
            int cX = b.centerX(), cY = b.centerY();
            b.left = cX - (fullSize ? bigImgSize : drawImgSize) / 2;
            b.right = cX + (fullSize ? bigImgSize : drawImgSize) / 2;
            b.top = cY - (fullSize ? bigImgSize : drawImgSize) / 2;
            b.bottom = cY + (fullSize ? bigImgSize : drawImgSize) / 2;
            return b;
        }

        @Override
        public void draw(Canvas canvas) {
            if (emojiBmp[info.page] == null) {
                loadEmojiAsync(info.page);
                canvas.drawRect(getBounds(), placeholderPaint);
                return;
            }
            Rect b;
            if (fullSize) {
                b = getDrawRect();
            } else {
                b = getBounds();
            }

            if (!canvas.quickReject(b.left, b.top, b.right, b.bottom, Canvas.EdgeType.AA)) {
                canvas.drawBitmap(emojiBmp[info.page], info.rect, b, paint);
            }
        }

        @Override
        public int getOpacity() {
            return 0;
        }

        @Override
        public void setAlpha(int alpha) {
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
        }
    }

    private static class DrawableInfo {
        public Rect rect;
        public byte page;

        public DrawableInfo(Rect r, byte p) {
            rect = r;
            page = p;
        }
    }

    private static boolean inArray(char c, char[] a) {
        for (char cc : a) {
            if (cc == c) {
                return true;
            }
        }
        return false;
    }

    public CharSequence replaceEmoji(CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return cs;
        }
        Spannable s;
        if (cs instanceof Spannable) {
            s = (Spannable) cs;
        } else {
            s = Spannable.Factory.getInstance().newSpannable(cs);
        }
        long buf = 0;
        int emojiCount = 0;
        try {
            for (int i = 0; i < cs.length(); i++) {
                char c = cs.charAt(i);
                if (c == 0xD83C || c == 0xD83D || (buf != 0 && (buf & 0xFFFFFFFF00000000L) == 0 && (c >= 0xDDE6 && c <= 0xDDFA))) {
                    buf <<= 16;
                    buf |= c;
                } else if (buf > 0 && (c & 0xF000) == 0xD000) {
                    buf <<= 16;
                    buf |= c;
                    EmojiDrawable d = getEmojiDrawable(buf);
                    if (d != null) {
                        EmojiSpan span = new EmojiSpan(d);
                        emojiCount++;
                        if (c >= 0xDDE6 && c <= 0xDDFA) {
                            s.setSpan(span, i - 3, i + 1, 0);
                        } else {
                            s.setSpan(span, i - 1, i + 1, 0);
                        }
                    }
                    buf = 0;
                } else if (c == 0x20E3) {
                    if (i > 0) {
                        char c2 = cs.charAt(i - 1);
                        if ((c2 >= '0' && c2 <= '9') || c2 == '#') {
                            buf = c2;
                            buf <<= 16;
                            buf |= c;
                            EmojiDrawable d = getEmojiDrawable(buf);
                            if (d != null) {
                                EmojiSpan span = new EmojiSpan(d);
                                emojiCount++;
                                s.setSpan(span, i - 1, i + 1, 0);
                            }
                            buf = 0;
                        }
                    }
                } else if (inArray(c, emojiChars)) {
                    EmojiDrawable d = getEmojiDrawable(c);
                    if (d != null) {
                        EmojiSpan span = new EmojiSpan(d);
                        emojiCount++;
                        s.setSpan(span, i, i + 1, 0);
                    }
                }
                if (emojiCount >= 50) {//wat?
                    break;
                }
            }
        } catch (Exception e) {
            Log.e("tmessages", "error loading emoji page", e);
            return cs;
        }
        return s;
    }

    public class EmojiSpan extends ImageSpan {
        public EmojiSpan(EmojiDrawable d) {
            super(d, DynamicDrawableSpan.ALIGN_BOTTOM);
        }
    }

    public static String toString(long code) {
        String str = "";
        for (int i = 0; ; i++) {
            if (i >= 4) {
                return str;
            }
            int j = (int) (0xFFFF & code >> 16 * (3 - i));
            if (j != 0) {
                str = str + (char) j;
            }
        }
    }

    public interface EmojiCallback {
        void loaded();
    }

}
