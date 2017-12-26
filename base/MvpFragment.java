package cn.zdxiang.sexygirl.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import butterknife.ButterKnife;

/**
 * @author jm
 * @date 16-12-6.下午5:50
 * @description MvpFragment
 */

public abstract class MvpFragment<P extends BasePresenterImpl> extends BaseLazyFragment {

    protected P Presenter;

    protected View rootView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Presenter = createPresenter();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (Presenter != null) {
            Presenter.detachView();
        }
    }

    protected abstract int getLayoutId();

    protected abstract P createPresenter();


}

