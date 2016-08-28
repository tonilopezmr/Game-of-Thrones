package net.mobindustry.emojilib;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Spannable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;

import net.mobindustry.emojilib.emoji.Emoji;
import net.mobindustry.emojilib.emoji.EmojiKeyboardView;
import net.mobindustry.emojilib.emoji.EmojiPopup;
import net.mobindustry.emojilib.emoji.ImageLoaderHelper;

public class EmojiPanel {

    private EditText input;
    private ImageView smiles;
    private ImageView sendButton;

    private ObservableLinearLayout linearLayout;

    private FrameLayout root;
    private View panel;

    public static final int LEVEL_SMILE = 1;
    public static final int LEVEL_ARROW = 0;

    private Emoji emoji;
    private EmojiParser parser;
    @Nullable
    private EmojiPopup emojiPopup;

    private Activity activity;
    private EmojiClickCallback clickCallback;

    public EmojiPanel(Activity activity, FrameLayout root, EmojiClickCallback clickCallback) {
        this.activity = activity;
        this.root = root;
        this.clickCallback = clickCallback;
        ImageLoaderHelper.initImageLoader(activity);

        panel = View.inflate(activity, R.layout.emoji_panel, null);
        linearLayout = (ObservableLinearLayout) panel.findViewById(R.id.observable_layout);
        input = (EditText) panel.findViewById(R.id.message_edit_text);
        input.requestFocus();
        smiles = (ImageView) panel.findViewById(R.id.smiles);
        sendButton = (ImageView) panel.findViewById(R.id.attach);
    }

    public void iconsInit(int leveledSmileIconResourceId, int sendButtonResourceId) {
        smiles.setImageResource(leveledSmileIconResourceId);
        sendButton.setImageResource(sendButtonResourceId);
        smiles.setImageLevel(LEVEL_SMILE);
    }

    public void iconsInit() {
        smiles.setImageResource(R.drawable.ic_send_smile_levels_default);
        sendButton.setImageResource(R.drawable.ic_send);
        smiles.setImageLevel(LEVEL_SMILE);
    }

    public void init() {
        root.addView(panel);
        makeEmoji(new Emoji.EmojiCallback() {
            @Override
            public void loaded() {
                parser = new EmojiParser(emoji);
                sendButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickCallback.sendClicked(parser.parse(input.getText().toString()));
                        input.setText("");
                    }
                });

                smiles.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (emojiPopup != null) {
                            smiles.setImageLevel(LEVEL_SMILE);
                            emojiPopup.dismiss();
                        } else {
                            emojiPopup = EmojiPopup.create(activity, linearLayout, new EmojiKeyboardView.CallBack() {
                                @Override
                                public void backspaceClicked() {
                                    input.dispatchKeyEvent(new KeyEvent(0, KeyEvent.KEYCODE_DEL));
                                }

                                @Override
                                public void emojiClicked(long code) {
                                    String strEmoji = emoji.toString(code);
                                    Editable text = input.getText();
                                    text.append(emoji.replaceEmoji(strEmoji));
                                }

                                @Override
                                public void stickerCLicked(String stickerFilePath) {
                                    clickCallback.stickerClicked(stickerFilePath);
                                }
                            });
                            emojiPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                                @Override
                                public void onDismiss() {
                                    smiles.setImageLevel(LEVEL_SMILE);
                                    emojiPopup = null;
                                }
                            });
                            smiles.setImageLevel(LEVEL_ARROW);
                            assert emojiPopup != null;
                        }
                    }
                });
            }
        });
    }

    private void makeEmoji(Emoji.EmojiCallback callback) {
        emoji = new Emoji(activity, new DpCalculator(Utils.getDensity(activity.getResources())));
        emoji.makeEmoji(callback);
    }

    public boolean dissmissEmojiPopup() {
        smiles.setImageLevel(LEVEL_SMILE);
        if (emojiPopup == null) {
            return false;
        }
        emojiPopup.dismiss();
        emojiPopup = null;
        Utils.hideKeyboard(input);
        return true;
    }

    public boolean isEmojiAttached() {
        if (emojiPopup == null) {
            return false;
        } else {
            return true;
        }
    }

    public EmojiParser getParser() {
        return new EmojiParser(emoji);
    }

    public interface EmojiClickCallback {
        void sendClicked(Spannable span);

        void stickerClicked(String path);
    }
}
