package com.marghe.customRequest;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import com.marghe.customRequest.Models.BaseModel;


/**
 * Created by romain on 30/05/2016.
 */
public class GetModelRequest<T extends List, U extends BaseModel> extends Request<T> {
    private final Class<U> cls;
    private final T modelList;
    private final Map<String, String> headers;
    private final Response.Listener<T> listener;

    public GetModelRequest(Class<U> cls, T modelList, String url, Map<String, String> headers,
                           Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.cls = cls;
        this.modelList = modelList;
        this.headers = headers;
        this.listener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                U tmp = cls.newInstance();
                tmp.fromJson(jsonArray.getJSONObject(i));

                modelList.add(tmp);
            }
            return Response.success(modelList, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        } catch (InstantiationException e) {
            return Response.error(new ParseError(e));
        } catch (IllegalAccessException e) {
            return Response.error(new ParseError(e));
        }
    }
}
