package com.mcgrady.xwebview;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * <p>类说明</p>
 *
 * @author: mcgrady
 * @date: 2019/2/19
 */

public class SafeWebViewClient extends WebViewClient {

    /**
     * 当{@link WebView#getScale()}值发生改变时回调
     * @param view
     * @param oldScale
     * @param newScale
     */
    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
    }

    /**
     * 是否在 WebView 内加载页面
     * @param view
     * @param request
     * @return
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

        boolean handleByChild = null != client && client.shouldOverrideUrlLoading(view, url);
        if (handleByChild) {
            // 开放client接口给上层业务调用，如果返回true，表示业务已处理。
            return true;
        } else if (!isTouchByUser()) {
            // 如果业务没有处理，并且在加载过程中用户没有再次触摸屏幕，认为是301/302事件，直接交由系统处理。
            return super.shouldOverrideUrlLoading(view, url);
        } else {
            //否则，属于二次加载某个链接的情况，为了解决拼接参数丢失问题，重新调用loadUrl方法添加固有参数。
            loadUrl(url);
            return true;
        }

        return super.shouldOverrideUrlLoading(view, request);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return super.shouldOverrideUrlLoading(view, url);
    }

    /**
     * WebView 开始加载页面时回调，一次 Frame 加载对应一次回调
     * @param view
     * @param url
     * @param favicon
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    /**
     * WebView 完成加载页面时回调，一次 Frame 加载对应一次回调
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    /**
     * WebView 加载页面资源时会回调，每个资源产生的网络加载都会回调，除非本地有当前 url 对应有缓存。
     * @param view
     * @param url
     */
    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    /**
     * 可以拦截 WebView 某一次的 request 来返回我们自己加载的数据，这个方法在后面缓存会有很大作用。
     * @param view
     * @param request
     * @return
     */
    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return super.shouldInterceptRequest(view, request);
    }

    /**
     * WebView 访问 url 出错
     * @param view
     * @param request
     * @param error
     */
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
    }

    /**
     * WebView ssl 访问证书出错，{@link SslErrorHandler#cancel()}取消加载，{@link SslErrorHandler#proceed()}对然错误也继续加载
     * @param view
     * @param handler
     * @param error
     */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
    }
}
