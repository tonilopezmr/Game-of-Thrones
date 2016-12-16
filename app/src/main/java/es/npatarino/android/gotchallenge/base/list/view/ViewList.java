package es.npatarino.android.gotchallenge.base.list.view;

import es.npatarino.android.gotchallenge.base.Mvp;

import java.util.List;

public interface ViewList<T> extends Mvp.View {
  void showList(List<T> list);
}
