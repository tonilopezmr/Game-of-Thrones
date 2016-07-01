package es.npatarino.android.gotchallenge.base.list.view;

import java.util.List;

import es.npatarino.android.gotchallenge.base.Mvp;

public interface ViewList<T> extends Mvp.View {
    void showList(List<T> list);
}
