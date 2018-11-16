package com.maxence.epitech.epicture;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private String _clientId;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Bundle args = getArguments();
        _clientId = args.getString("clientId", null);

        Button login = (Button) view.findViewById(R.id.buttonLoginImgur);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_clientId != null) {
                    connectToImgur();
                }
            }
        });

        /*
        ImageView tmp = (ImageView) view.findViewById(R.id.imageView);
        Picasso.with(getContext())
                .load("https://i.imgur.com/BLrFocL_d.png?maxwidth=290&fidelity=grand")
                .into(tmp);
        */
        return view;
    }

    void connectToImgur() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://api.imgur.com/oauth2/authorize?client_id=" + _clientId + "&response_type=token"));
        startActivity(intent);
    }


}
