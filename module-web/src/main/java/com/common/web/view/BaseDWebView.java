package com.common.web.view;

public interface BaseDWebView {

    void addJavascriptObject(Object object, String namespace);

    void removeJavascriptObject(String namespace);

    void callHandler(String method, Object[] args);

    <T> void callHandler(String method, OnReturnValue<T> handler);

}