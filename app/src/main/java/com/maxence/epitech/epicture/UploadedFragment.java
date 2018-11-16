package com.maxence.epitech.epicture;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UploadedFragment extends Fragment {
    private String _token = null;
    private ImgurRequest _request = null;

    public UploadedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_uploaded, container, false);

        initializeFragmentData(getArguments(), view);
        _request = new ImgurRequest("starkymax/images", _token);

        return view;
    }

    private void initializeFragmentData(Bundle args, View view) {
        _token = args.getString("token", null);

        if (_token == null)
            Log.i("Token", "The token is NULL");
        else {

            TextView t = (TextView) view.findViewById(R.id.Account);
            t.setText(_token);
        }
    }

    private void actualizeView(List<ListArgs> list) {
        for (ListArgs item : list) {
            if (item.key == "bio") {
                TextView t = (TextView) getView().findViewById(R.id.BioText);
                t.setText(item.value);
            } else if (item.key == "avatar") {
                ImageView view = (ImageView) getView().findViewById(R.id.Avatar);
                Picasso.with(getContext())
                        .load(item.value)
                        .into(view);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        List<String> args = new ArrayList<String>() {{
            add("bio");
            add("avatar");
            add("reputation");
        }};

        _request.call(args);
        while (!_request.hasResponded());
        actualizeView(_request.getDataList());
    }

}
