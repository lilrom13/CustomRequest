package com.marghe.customRequest.Models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by marghe on 01/06/16.
 */
public abstract class BaseModel<T> {
    public abstract void fromJson(JSONObject json) throws JSONException;

}
