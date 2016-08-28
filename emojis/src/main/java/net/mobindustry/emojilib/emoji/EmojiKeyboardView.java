package net.mobindustry.emojilib.emoji;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;

import net.mobindustry.emojilib.DpCalculator;
import net.mobindustry.emojilib.R;
import net.mobindustry.emojilib.Utils;

import java.util.List;


public class EmojiKeyboardView extends LinearLayout {

    private final RecentSmiles recent;
    private ViewPager pager;
    private DpCalculator calc = new DpCalculator(Utils.getDensity(getResources()));
    private Emoji emoji;
    private PagerSlidingTabStrip strip;
    private View backspace;
    private final LayoutInflater viewFactory;
    private CallBack callback;
    private Context context;
    private Stickers stickers;

    public EmojiKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        recent = new RecentSmiles(context.getSharedPreferences("RecentEmoji", Context.MODE_PRIVATE));
        viewFactory = LayoutInflater.from(context);
        emoji = Emoji.getInstance();
        stickers = new Stickers(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        pager = ((ViewPager) findViewById(R.id.pager));
        strip = (PagerSlidingTabStrip) findViewById(R.id.tabs_strip);
        strip.setShouldExpand(true);
        Adapter adapter = new Adapter(getContext());
        pager.setAdapter(adapter);
        strip.setViewPager(pager);
        backspace = findViewById(R.id.backspace);
        backspace.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.backspaceClicked();
            }
        });
        if (adapter.recentIds.length == 0) {
            pager.setCurrentItem(1);
        }
    }

    public void setCallback(CallBack callback) {
        this.callback = callback;
    }

    class Adapter extends PagerAdapter implements PagerSlidingTabStrip.IconTabProvider {
        final int[] icons = new int[]{
                R.drawable.ic_smiles_recent_selector,
                R.drawable.ic_smiles_smiles_selector,
                R.drawable.ic_smiles_flower_selector,
                R.drawable.ic_smiles_bell_selector,
                R.drawable.ic_smiles_car_selector,
                R.drawable.ic_smiles_grid_selector,
                R.drawable.ic_smiles_stickers_selector,
        };
        private final LayoutInflater viewFactory;
        private Context context;
        private long[] recentIds;

        public Adapter(Context context) {
            this.context = context;
            recentIds = EmojiKeyboardView.this.recent.get();
            viewFactory = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return /*recent*/ 1 +
                    /*emoji*/ 5 +
                    /* stickers*/ 1;
        }

        @Override
        public GridView instantiateItem(ViewGroup container, int position) {
            if (position == 0) {

                final long[] longs = recentIds;
                GridView gridPage = createGridPage(container, position, new EmojiPageAdapter(longs), R.dimen.emoji_size);
                return gridPage;
            } else if (position == getCount() - 1) {
                final List<String> ss = stickers.getStickers();
                GridView res = createGridPage(container, position, new StickerAdapter(ss), R.dimen.sticker_size);
                res.setVerticalSpacing(calc.dp(16));
                res.setClipToPadding(false);
                int dip8 = calc.dp(8);
                res.setPadding(dip8 / 2, dip8, dip8 / 2, dip8);
                return res;
            } else {
                final long[] data = EmojiArrays.getData()[position];
                GridView gridPage = createGridPage(container, position, new EmojiPageAdapter(data), R.dimen.emoji_size);
                return gridPage;
            }

        }

        private GridView createGridPage(ViewGroup container, int position1, BaseAdapter adapter, int columnSizeResId) {
            int columnWidth = getContext().getResources().getDimensionPixelSize(columnSizeResId);
            GridView view = (GridView) viewFactory.inflate(R.layout.keyboard_page_emoji, container, false);
            view.setColumnWidth(columnWidth);
            view.setAdapter(adapter);
            container.addView(view);
            view.setTag(position1);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getPageIconResId(int i) {
            return icons[i];
        }
    }

    class EmojiPageAdapter extends BaseAdapter {

        private long[] longs;

        public EmojiPageAdapter(long[] longs) {
            this.longs = longs;
        }

        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = viewFactory.inflate(R.layout.grid_item_emoji, parent, false);
            return new VH(v);
        }

        public void onBindViewHolder(VH holder, int position) {
            holder.o = longs[position];
            Drawable d = emoji.getEmojiBigDrawable(longs[position]);
            holder.img.setImageDrawable(d);
        }

        @Override
        public int getCount() {
            return longs.length;
        }

        @Override
        public Object getItem(int position) {
            return longs[position];
        }

        @Override
        public long getItemId(int position) {
            return longs[position];
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            VH vh;
            if (convertView == null) {
                vh = onCreateViewHolder(parent, 0);
                vh.img.setTag(vh);
            } else {
                vh = (VH) convertView.getTag();
            }
            onBindViewHolder(vh, position);
            return vh.img;
        }
    }

    class VH {
        Object o;
        final ImageView img;

        public VH(View itemView) {
            img = (ImageView) itemView.findViewById(R.id.img);
            img.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Log", o.toString());
                    if (o instanceof String) {
                        stickerClicked((String) o);

                    } else {
                        long emojiCode = (Long) VH.this.o;
                        callback.emojiClicked(emojiCode);
                        recent.emojiClicked(emojiCode);
                    }
                }
            });
        }
    }

    private void stickerClicked(String sticker) {
        callback.stickerCLicked(sticker);
    }

    class StickerAdapter extends BaseAdapter {
        final List<String> data;

        StickerAdapter(List<String> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public String getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            VH vh;
            if (convertView == null) {
                View v = viewFactory.inflate(R.layout.grid_item_sticker, parent, false);
                vh = new VH(v);
                v.setTag(vh);
            } else {
                vh = (VH) convertView.getTag();
            }

            onBindVH(vh, position);
            return vh.img;
        }

        private void onBindVH(final VH vh, int position) {
            final String s = getItem(position);
            ImageLoaderHelper.displayImageWithoutFadeIn("file://" + s, vh.img);
            vh.o = s;
        }
    }

    public interface CallBack {
        void backspaceClicked();

        void emojiClicked(long code);

        void stickerCLicked(String stickerFilePath);
    }
}
