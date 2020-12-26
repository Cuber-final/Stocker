package com.example.stocker.toolsOpe;

import javax.security.auth.callback.Callback;

public interface webCallBack extends Callback {
    void onSuccess(String response);
    void onFailed(Throwable ex);
}
