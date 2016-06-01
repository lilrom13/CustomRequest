package com.marghe.customRequest;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.marghe.customRequest.Models.BaseModel;
import com.marghe.customRequest.Models.CallBacks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marghe on 01/06/16.
 */
public class CustomRequest<T extends BaseModel> {
    protected Context mContext;

    public CustomRequest(Context context) {
        mContext = context;
    }

    public void GetRequest(final Class<T> model, String url, final CallBacks callbacks) {
        List<BaseModel> modelList = new ArrayList<>();

        GetModelRequest request = new GetModelRequest(model, modelList, url, null, new Response.Listener<List<BaseModel>>() {
            @Override
            public void onResponse(List<BaseModel> response) {
                callbacks.OnRequestSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callbacks.OnRequestFail(error.getMessage());
            }
        });
        Singleton.getInstance(mContext).addToRequestQueue(request);
    }
}
