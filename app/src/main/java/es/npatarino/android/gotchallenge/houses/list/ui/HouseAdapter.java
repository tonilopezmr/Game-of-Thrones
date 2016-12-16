package es.npatarino.android.gotchallenge.houses.list.ui;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.base.ui.imageloader.ImageLoader;
import es.npatarino.android.gotchallenge.chat.ui.ChatActivityNavigator;
import es.npatarino.android.gotchallenge.houses.domain.model.GoTHouse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HouseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private final List<GoTHouse> houseList;
  private Activity activity;
  private ImageLoader imageLoader;

  public HouseAdapter(ImageLoader imageLoader, Activity activity) {
    this.activity = activity;
    this.imageLoader = imageLoader;
    this.houseList = new ArrayList<>();
  }


  public void addAll(Collection<GoTHouse> collection) {
    for (int i = 0; i < collection.size(); i++) {
      houseList.add((GoTHouse) collection.toArray()[i]);
    }
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new GotHouseViewHolder(LayoutInflater
        .from(parent.getContext())
        .inflate(R.layout.got_house_row, parent, false));
  }

  @Override
  public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
    final GotHouseViewHolder gotCharacterViewHolder = (GotHouseViewHolder) holder;
    GotHouseViewHolder gotHouseViewHolder = (GotHouseViewHolder) holder;
    final GoTHouse house = houseList.get(position);
    gotCharacterViewHolder.render(house);
    gotHouseViewHolder.imp.setOnClickListener(v -> moveToDetailActivity(house));
  }

  private void moveToDetailActivity(GoTHouse house) {
    new ChatActivityNavigator(activity, house.getId())
        .navigate();
  }

  @Override
  public int getItemCount() {
    return houseList.size();
  }

  private class GotHouseViewHolder extends RecyclerView.ViewHolder {

    ImageView imp;

    public GotHouseViewHolder(View itemView) {
      super(itemView);
      imp = (ImageView) itemView.findViewById(R.id.ivBackground);
    }

    public void render(final GoTHouse house) {
      imageLoader.builder()
          .load(house.getImageUrl())
          .fit()
          .into(imp)
          .show();
    }
  }

}