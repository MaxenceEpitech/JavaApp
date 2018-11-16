package com.maxence.epitech.epicture;

import android.arch.core.util.Function;
import android.content.Context;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImgurRequest {

    private boolean _responded = false;
    private List<ListArgs> _dataList = null;

    private String _url = null;
    private String _token = null;
    private Request _request = null;
    private String _responseBody = null;

    private JSONObject _json;
    private JSONObject _jsonParser;

    public ImgurRequest(String url, String token) {
        _url = new String("https://api.imgur.com/3/account/" + url);
        _token = token;

        if (_token != null) {
            _request = new Request.Builder()
                    .header("Authorization", "Bearer " + _token)
                    .url(_url)
                    .build();
        } else {
            _request = new Request.Builder()
                    .url(_url)
                    .build();
        }
    }

    public void call(final List<String> queryArgs) {
        OkHttpClient client = new OkHttpClient();

        client.newCall(_request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("THIS IS A TAG", "An error has occurred " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                JSONObject json_LL = null;
                _responseBody = response.body().string();
                Log.i("reponse ", _responseBody);
                _dataList = new ArrayList<ListArgs>() {{
                    for (String item : queryArgs) {
                        String toFind = getFromJson(item);
                        if (toFind == null) {
                            add(new ListArgs(null, null));
                        } else {
                            add(new ListArgs(item, toFind));
                        }
                    }
                }};
                _responded = true;
            }
        });
    }

    public boolean hasResponded() {
        return _responded;
    }

    public List<ListArgs> getDataList() {
        return _dataList;
    }

    private String getFromJson(String toFind) {
        try {
            _json = new JSONObject(_responseBody);
            _jsonParser = _json.getJSONObject("data");
            return _jsonParser.getString(toFind);
        } catch (JSONException e) {

        }
        return null;
    }

    private String getFromResponse(String key) {

        if (_responseBody == null)
                Log.i("Debug", "Reponse is null");

        JSONObject jsonObject;
        if (key == null || _responseBody == null)
            return null;
        try {

            jsonObject = new JSONObject(_responseBody);
            JSONObject myResponse = jsonObject.getJSONObject("data");
            if (myResponse.getString(key) != null)
                Log.i("erkceu", myResponse.getString(key));

        } catch (JSONException e) {
            Log.e("Json Error = ", "" + e);
        }
        return null;
    }
}
