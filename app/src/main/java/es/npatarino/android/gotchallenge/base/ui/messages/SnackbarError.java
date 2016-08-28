package es.npatarino.android.gotchallenge.base.ui.messages;

import android.support.design.widget.Snackbar;
import android.view.View;

public class SnackbarError implements ErrorManager {
    @Override
    public void showError(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}
