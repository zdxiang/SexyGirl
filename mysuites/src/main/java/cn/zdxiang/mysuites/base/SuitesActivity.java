package cn.zdxiang.mysuites.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.umeng.analytics.MobclickAgent;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.ButterKnife;

/**
 * Created by JM on 2017/11/23 0023.下午 4:34
 * Description:
 */

public abstract class SuitesActivity extends AppCompatActivity {

    protected AVLoadingIndicatorView avLoadingIndicatorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    protected abstract int getLayoutId();

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
