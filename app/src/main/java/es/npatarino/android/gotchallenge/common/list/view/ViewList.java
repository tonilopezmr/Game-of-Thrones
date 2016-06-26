package es.npatarino.android.gotchallenge.common.list.view;

import java.util.List;

import es.npatarino.android.gotchallenge.common.Mvp;

public interface ViewList<T> extends Mvp.View {
    void showList(List<T> list);
}
