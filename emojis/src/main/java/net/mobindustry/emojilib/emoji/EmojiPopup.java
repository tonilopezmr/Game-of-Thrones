package net.mobindustry.emojilib.emoji;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.PopupWindow;

import net.mobindustry.emojilib.DpCalculator;
import net.mobindustry.emojilib.ObservableLinearLayout;
import net.mobindustry.emojilib.R;
import net.mobindustry.emojilib.Utils;

public class EmojiPopup extends PopupWindow implements ObservableLinearLayout.CallBack {

    private final ObservableLinearLayout parentView;
    private final WindowManager wm;
    private boolean keyboardVisible;
    private final Context ctx;
    private DpCalculator calc;

    private final SharedPreferences prefs;
    private EmojiKeyboardView view;

    public EmojiPopup(EmojiKeyboardView view, ObservableLinearLayout rootView) {
        super(view);
        this.view = view;
        this.parentView = rootView;
        ctx = view.getContext();
        calc = new DpCalculator(Utils.getDensity(ctx.getResources()));
        prefs = ctx.getSharedPreferences("EmojiPopup", Context.MODE_PRIVATE);

        rootView.setCallback(this);

        int keyboardHeight = rootView.getKeyboardHeight();
        wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height;
        if (keyboardHeight > 0) {
            height = keyboardHeight;
            saveKeyboardHiehgt(keyboardHeight);
        } else {
            height = guessKeyboardHeight();
        }

        setWidth(exactly(width));
        setHeight(exactly(height));

        keyboardVisible = keyboardHeight > 0;
        if (!keyboardVisible) {
            rootView.setPadding(0, 0, 0, height);
        }
    }

    private void saveKeyboardHiehgt(int keyboardHeight) {
        boolean portrait = isPortrait();
        prefs.edit()
                .putInt(getKeyForConfiguration(portrait), keyboardHeight)
                .commit();
    }

    private int guessKeyboardHeight() {
        boolean portrait = isPortrait();
        String prefKey = getKeyForConfiguration(portrait);
        return prefs.getInt(prefKey, calc.dp(portrait ? 240 : 150));
    }

    private String getKeyForConfiguration(boolean portrait) {
        String prefKey;
        prefKey = "keyboard_height_" + portrait;
        return prefKey;
    }

    private boolean isPortrait() {
        int orientation = ctx.getResources().getConfiguration().orientation;
        boolean portrait = orientation == Configuration.ORIENTATION_PORTRAIT;
        return portrait;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        parentView.setCallback(null);
        parentView.setPadding(0, 0, 0, 0);
    }

    public static EmojiPopup create(Activity ctx, ObservableLinearLayout parent, EmojiKeyboardView.CallBack cb) {
        LayoutInflater viewFactory = LayoutInflater.from(ctx);
        EmojiKeyboardView view = (EmojiKeyboardView) viewFactory.inflate(R.layout.view_emoji_keyboard, null, false);
        view.setCallback(cb);

        EmojiPopup res = new EmojiPopup(view, parent);
        res.showAtLocation(ctx.getWindow().getDecorView(), Gravity.BOTTOM | Gravity.LEFT, 0, 0);

        return res;
    }

    private static int exactly(int size) {
        return View.MeasureSpec.makeMeasureSpec(size, View.MeasureSpec.EXACTLY);
    }

    @Override
    public void onLayout(int keyboardHeight, boolean landscape) {
        boolean newKeyboardVisible = keyboardHeight > 0;
        if (keyboardVisible == newKeyboardVisible) {
            return;
        }
        keyboardVisible = newKeyboardVisible;
        dismiss();
        parentView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                parentView.getViewTreeObserver().removeOnPreDrawListener(this);
                return false;
            }
        });
        parentView.setPadding(0, 0, 0, 0);
    }
}
