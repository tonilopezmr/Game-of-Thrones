package es.npatarino.android.gotchallenge.characters.list.view.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.characters.domain.model.GoTCharacter;
import es.npatarino.android.gotchallenge.common.navigation.DetailActivityNavigatorBuilder;
import es.npatarino.android.gotchallenge.common.view.activities.DetailActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.GotCharacterViewHolder> {

    private final List<GoTCharacter> gcs;
    private Activity activity;

    public CharacterAdapter(Activity activity) {
        this.gcs = new ArrayList<>();
        this.activity = activity;
    }

    public void addAll(Collection<GoTCharacter> collection) {
        for (int i = 0; i < collection.size(); i++) {
            gcs.add((GoTCharacter) collection.toArray()[i]);
        }
    }

    @Override
    public GotCharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GotCharacterViewHolder(LayoutInflater
                                                .from(parent.getContext())
                                                .inflate(R.layout.got_character_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final GotCharacterViewHolder holder, final int position) {
        final GoTCharacter character = gcs.get(position);
        holder.render(character);
        holder.imp.setOnClickListener(v -> moveToDetailActivity(holder, character));
    }

    private void moveToDetailActivity(GotCharacterViewHolder viewHolder, GoTCharacter character) {
        new DetailActivityNavigatorBuilder(activity)
                .makeTransition(viewHolder.itemView, DetailActivity.CHARACTER_IMAGE)
                .name(character.getName())
                .description(character.getDescription())
                .imageUrl(character.getImageUrl())
                .navigate();
    }

    @Override
    public int getItemCount() {
        return gcs.size();
    }

    class GotCharacterViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "GotCharacterViewHolder";
        ImageView imp;
        TextView tvn;

        GotCharacterViewHolder(View itemView) {
            super(itemView);
            imp = (ImageView) itemView.findViewById(R.id.ivBackground);
            tvn = (TextView) itemView.findViewById(R.id.tv_name);
        }

        void render(final GoTCharacter character) {
            Picasso.with(imp.getContext())
                    .load(character.getImageUrl())
                    .fit()
                    .into(imp);
            tvn.setText(character.getName());
        }
    }

}