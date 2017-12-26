package cn.zdxiang.sexygirl.base;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import cn.zdxiang.sexygirl.R;

/**
 * @author jm
 * @date 16-12-6.下午2:57
 * @description BaseActivity
 */

public abstract class BaseActivity<P extends BasePresenterImpl> extends AppCompatActivity {

    protected P Presenter;

    protected Toolbar mToolbar;

//    protected WaitCustomDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Presenter = createPresenter();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initToolbar();
//        initTitle();
    }

    protected abstract P createPresenter();

    protected abstract int getLayoutId();

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(getIntent().getStringExtra("title") == null ? "" : getIntent().getStringExtra("title"));
            setSupportActionBar(mToolbar);
        }
    }

    public void setToolbarTitle(String title) {
        if (mToolbar != null) {
            mToolbar.setTitle(title);
        }
    }

//    private void initTitle() {
//        TextView title = (TextView) findViewById(R.id.tv_title);
//        if (title == null) return;
//        title.setText(getIntent().getStringExtra("title"));
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onPageStart(this.getClass().getSimpleName());
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
        MobclickAgent.onPause(this);
    }

    public void setFragment(int layout, Fragment fragment) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.default_fromright_in, R.anim.default_toleft_out, R.anim.default_fromleft_in, R.anim.default_toright_out);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(layout, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Destroy the alertDialog
     *
     * @param dialog alertDialog v7
     */
    public void destroyDialog(AlertDialog dialog) {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    @Override
    protected void onDestroy() {
//        dismissGlobalLoading();
        super.onDestroy();
    }
//
//    public void showGlobalLoading() {
//        if (mLoadingDialog != null) {
//            if (!this.isFinishing()) {
//                mLoadingDialog.showDialog();
//            }
//        }
//    }
//
//    public void setLoadingMessage(String message) {
//        if (mLoadingDialog != null && message != null) {
//            mLoadingDialog.setText(message);
//        }
//    }
//
//    public void dismissGlobalLoading() {
//        if (mLoadingDialog != null) {
//            mLoadingDialog.closeDialog();
//        }
//    }

    public void finishDelay(int delayMillis) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, delayMillis);
    }

//    public void startActivity(Intent intent) {
//        ActivityCompat.startActivity(this, intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
//    }
}
