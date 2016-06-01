package com.marghe.customRequest.Models;

import java.util.List;

/**
 * Created by marghe on 01/06/16.
 */
public interface CallBacks {
    void OnRequestSuccess(List<BaseModel> model);
    void OnRequestFail(String error);
}
