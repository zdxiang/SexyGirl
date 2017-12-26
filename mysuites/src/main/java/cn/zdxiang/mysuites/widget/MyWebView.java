package cn.zdxiang.mysuites.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import cn.zdxiang.mysuites.R;
import cn.zdxiang.mysuites.utils.DownloadUtil;
import cn.zdxiang.mysuites.utils.PreferencesUtils;
import cn.zdxiang.mysuites.utils.ToastUtils;

/**
 * Created by JM on 2017/11/8 0008.上午 11:12
 * Description:
 */

public class MyWebView extends RelativeLayout {

    public static final String KEY_CLICK_AD_SUCCESS_COUNT = "clickAdCount";


    private Context context;

    private WebView mWebView;

    private ProgressBar pbLoading;

    private RelativeLayout rlError;

    /**
     * 成功出发“广告点击成功”的次数
     */
    private int mClickAdSuccessCount = 0;

    private boolean enableDownLoad = false;


    public MyWebView(Context context) {
        super(context);
        init(context, null);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.webview_content, this, true);
        this.context = context;
        mClickAdSuccessCount = PreferencesUtils.getInt(context, KEY_CLICK_AD_SUCCESS_COUNT, 0);
        mWebView = (WebView) inflate.findViewById(R.id.webView);
        pbLoading = (ProgressBar) findViewById(R.id.pb);
        rlError = (RelativeLayout) findViewById(R.id.rl_error);
        initWebSetting();
    }

    public void setProgressDrawable(int id) {
        pbLoading.setProgressDrawable(context.getResources().getDrawable(id));
    }

    private void initWebSetting() {
        if (this.isInEditMode()) return;
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setAllowContentAccess(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.setWebViewClient(mWebViewClient);
    }

    public void reload() {
        mWebView.reload();
    }

    public boolean canGoBack() {
        return mWebView.canGoBack();
    }

    public void goBack() {
        mWebView.goBack();
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    /**
     * @param enable 是否开启下载，如果true，点击某些广告的时候会弹出下载
     */
    public void enableDownLoad(boolean enable) {
        enableDownLoad = enable;
    }

    private WebChromeClient mWebChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            pbLoading.setProgress(newProgress);
            pbLoading.setVisibility(newProgress >= 100 ? View.GONE : View.VISIBLE);
        }

        @Override
        public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
            return super.onJsBeforeUnload(view, url, message, result);
        }

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            if (consoleMessage.message() != null && consoleMessage.message().equals("广告点击成功")) {
                ToastUtils.show(getContext(), "砸蛋成功");
                mClickAdSuccessCount += 1;
                PreferencesUtils.putInt(getContext(), KEY_CLICK_AD_SUCCESS_COUNT, mClickAdSuccessCount);
            }
            return super.onConsoleMessage(consoleMessage);
        }
    };

    private WebViewClient mWebViewClient = new WebViewClient() {
        boolean isInErrorState = false;

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                webViewUrl = url;
            if (url.contains("apk")) {
                if (enableDownLoad) {
                    (new DownloadUtil()).enqueue(url, getContext().getApplicationContext());
                }
                return true;
            } else if (url.contains("tbopen://")) {
                if (checkPackage("com.taobao.taobao")) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri uri = Uri.parse(url);
                    intent.setData(uri);
                    context.startActivity(intent);
                }
                return true;
            } else {
                view.loadUrl(url);
                return true;
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            CookieSyncManager.getInstance().sync();
            if (!isInErrorState) {
                mWebView.setVisibility(View.VISIBLE);
                rlError.setVisibility(View.GONE);
            } else {
                mWebView.setVisibility(View.GONE);
                rlError.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            isInErrorState = true;
            rlError.setVisibility(View.VISIBLE);
            mWebView.setVisibility(View.GONE);
            rlError.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    isInErrorState = false;
                    mWebView.reload();
                }
            });
        }
    };


    /**
     * 检测该包名所对应的应用是否存在
     *
     * @param packageName
     * @return
     */

    public boolean checkPackage(String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
