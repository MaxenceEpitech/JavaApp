package com.maxence.epitech.epicture;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String clientId = "17b639510e8ac61";
    private String clientSecret = "6226d20830c3e7af5184c2a5fa27e96de4087028";
    private String redirectUri = "https://thisistheepictureproject";

    private boolean _isLogin = false;
    private String _token = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.i("cc", "Je suis ici");

        if (id == R.id.nav_camera) {
            if (_isLogin == false) {
                List<ListArgs> args = new ArrayList<ListArgs>() {{
                    add(new ListArgs("clientId", clientId));
                }};
                newFragmentView("Login", LoginFragment.class, args);
            } else {
                List<ListArgs> args = new ArrayList<ListArgs>() {{
                    add(new ListArgs("token", _token));
                }};
                Log.i("token = ", _token);
                newFragmentView("Account", AccountFragment.class, args);
            }
        } else if (id == R.id.nav_gallery) {
            List<ListArgs> args = new ArrayList<ListArgs>() {{
                add(new ListArgs("clientId", clientId));
                add(new ListArgs("token", _token));
            }};
            newFragmentView("Uploaded Images", UploadedFragment.class, args);

        } else if (id == R.id.nav_slideshow) {
            setTitle("nav_slideshow");

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else {
            setTitle("Undefined");
        }
        Log.i("INFOOO", "return success");
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("a", "b\n\n\n\n\n\n\n\n\n\n\n\n\n\nabxyzw");

        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(redirectUri)) {
            String url = uri.toString().replace("#", "?");

            Log.i("URL", url);
            uri = Uri.parse(url);

            _token = uri.getQueryParameter("access_token");
            _isLogin = true;
            if (_token != null) {
                Log.i("I", _token);
            } else {
                Log.i("Login Error","Wrong username or password.");
            }
        }
    }

    boolean newFragmentView(String title, Class fragmentClass, List<ListArgs> list) {
        Fragment fragment;

        setTitle(title);
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            if (list != null) {
                Bundle newArgs = new Bundle();
                for (ListArgs item : list) {
                    newArgs.putString(item.key, item.value);
                }
                fragment.setArguments(newArgs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.drawer_layout, fragment).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        Log.i("INFOOO", "Je quit");

        return true;
    }

}
