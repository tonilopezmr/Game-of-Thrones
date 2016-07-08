package net.mobindustry.emojilib.emoji;

import android.content.SharedPreferences;

import net.mobindustry.emojilib.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecentSmiles {

    static final String PREF_RECENT_SMILES = "pref_recent_smiles";
    public static final String DIVIDER_ENTRY = ";";
    public static final String DIVIDER_TIME_CODE = ",";

    final Set<Entry> recent = new HashSet<>();
    private final SharedPreferences prefs;

    public RecentSmiles(SharedPreferences prefs) {
        this.prefs = prefs;
        String string = this.prefs.getString(PREF_RECENT_SMILES, "");
        String[] split = string.split(DIVIDER_ENTRY);
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if (s.length() == 0) continue;
            String[] timeCode = s.split(DIVIDER_TIME_CODE);
            long time = Long.parseLong(timeCode[0]);
            long code = Long.parseLong(timeCode[1]);
            recent.add(new Entry(time, code));
        }
    }

    public void emojiClicked(long emojiCode) {
        Entry e = new Entry(System.currentTimeMillis(), emojiCode);
        if (recent.contains(e)) {
            recent.remove(e);
        }
        recent.add(e);
        saveToPrefs();
    }

    public long[] get() {
        List<Entry> entries = getSortedRecentEmoji();
        long[] ids = new long[entries.size()];
        for (int i = 0; i < entries.size(); i++) {
            ids[i] = entries.get(i).code;
        }
        return ids;
    }

    public List<Entry> getSortedRecentEmoji() {
        ArrayList<Entry> res = new ArrayList<>(recent);
        Collections.sort(res, new Comparator<Entry>() {
            @Override
            public int compare(Entry lhs, Entry rhs) {
                return Utils.compare(rhs.time, lhs.time);
            }
        });

        return res;
    }

    private void saveToPrefs() {
        List<Entry> es = getSortedRecentEmoji();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < es.size(); i++) {
            Entry e = es.get(i);
            sb.append(e.time)
                    .append(DIVIDER_TIME_CODE)
                    .append(e.code);
            if (i != es.size() - 1) {
                sb.append(DIVIDER_ENTRY);
            }
        }
        prefs.edit()
                .putString(PREF_RECENT_SMILES, sb.toString())
                .apply();
    }

    class Entry {
        final long time;
        final long code;

        public Entry(long time, long code) {
            this.time = time;
            this.code = code;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Entry entry = (Entry) o;
            return code == entry.code;
        }

        @Override
        public int hashCode() {
            return (int) (code ^ (code >>> 32));
        }
    }
}
