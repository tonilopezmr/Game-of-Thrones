package es.npatarino.android.gotchallenge.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import es.npatarino.android.gotchallenge.R;

/**
 * @author Antonio López.
 */
public class DescriptionFragment extends Fragment {

    private String name;
    private String description;


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)  {
        View rootView = inflater.inflate(R.layout.fragment_description, container, false);
        TextView tvn = (TextView) rootView.findViewById(R.id.tv_name);
        TextView tvd = (TextView) rootView.findViewById(R.id.tv_description);
        tvn.setText(name);
        tvd.setText(description);
        return rootView;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
