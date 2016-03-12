package es.npatarino.android.gotchallenge.view;

import java.util.List;

/**
 * @author Antonio López.
 */
public interface ViewList<T> extends View {
    void showList(List<T> list);
}
