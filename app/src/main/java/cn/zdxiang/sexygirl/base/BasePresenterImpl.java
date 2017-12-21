package cn.zdxiang.sexygirl.base;

/**
 * @author jm
 * @date 16-12-6.下午3:16
 * @description BasePresenterImpl
 */

public class BasePresenterImpl<V> implements BasePresenter<V> {
    public V View;

    @Override
    public void attachView(V view) {
        this.View = view;
    }

    @Override
    public void detachView() {
//        this.View = null; 这个在快速replaceFragment后,View就变null了,但是OkHttp发出去的请求在在执行,最终会执行View的方法。所以暂时注释
    }
}
