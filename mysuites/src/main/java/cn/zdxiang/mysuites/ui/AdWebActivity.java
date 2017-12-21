package cn.zdxiang.mysuites.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import cn.zdxiang.mysuites.R;
import cn.zdxiang.mysuites.widget.MyWebView;


/**
 * Created by fuckyou on 1/19/16.
 * Settings page.
 */
public class AdWebActivity extends AppCompatActivity {

    private String webViewUrl;
    private String webViewTitle;
    private Toolbar mToolbar;
    private MyWebView myWebView;

    public static void start(Context context, String url, String title) {
        Intent intent = new Intent(context, AdWebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("url", url);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad_webview);
        init();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && !bundle.isEmpty()) {
            webViewTitle = bundle.getString("title");
            webViewUrl = bundle.getString("url");
            mToolbar.setTitle(webViewTitle);
            myWebView.loadUrl(webViewUrl);
        }
    }

    private void init() {
        myWebView = (MyWebView) findViewById(R.id.myWebView);
        myWebView.enableDownLoad(true);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        } else if (id == R.id.action_share) {
            Intent textIntent = new Intent(Intent.ACTION_SEND);
            textIntent.setType("text/plain");
            textIntent.putExtra(Intent.EXTRA_TEXT, webViewUrl);
            startActivity(Intent.createChooser(textIntent, "分享"));
            return true;
        } else if (id == R.id.menu_refresh) {
            myWebView.reload();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ad, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (myWebView.canGoBack()) {
                        myWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
