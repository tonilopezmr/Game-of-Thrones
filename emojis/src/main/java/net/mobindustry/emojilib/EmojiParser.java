package net.mobindustry.emojilib;

import android.text.Spannable;
import android.text.style.ForegroundColorSpan;

import net.mobindustry.emojilib.emoji.Emoji;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmojiParser {

    private final Emoji emoji;
    private final Map<String, CharSequence> cache = new HashMap<>();
    private final Pattern userReference = Pattern.compile("(?:^|[^a-zA-Z0-9_＠!@#$%&*])(?:(?:@|＠)(?!/))([a-zA-Z0-9/_]{1,15})(?:\\b(?!@|＠)|$)");

    public EmojiParser(Emoji emoji) {
        this.emoji = emoji;
    }

    public Spannable parse(String text) {
        CharSequence fromCache = cache.get(text);
        if (fromCache != null) {
            return (Spannable) fromCache;
        } else {
            CharSequence parsed = emoji.replaceEmoji(text);
            Matcher matcher = userReference.matcher(text);
            Spannable s;
            if (parsed instanceof Spannable) {
                s = (Spannable) parsed;
            } else {
                s = Spannable.Factory.getInstance().newSpannable(parsed);
            }

            while (matcher.find()) {
                s.setSpan(new ForegroundColorSpan(0xff427ab0), matcher.start(), matcher.end(), 0);
            }
            cache.put(text, s);

            return s;
        }
    }
}
