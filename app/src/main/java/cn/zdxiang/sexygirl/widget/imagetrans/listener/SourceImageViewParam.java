package cn.zdxiang.sexygirl.widget.imagetrans.listener;

import android.view.View;

import cn.zdxiang.sexygirl.widget.imagetrans.ScaleType;


/**
 * Created by fuckyou on 17/6/6.
 */

public interface SourceImageViewParam {
    /**
     * 获得图片的原始 imageView
     *
     * @param position
     */
    View getSourceView(int position);

    /**
     * 获得图片的原始scaleType
     *
     * @param position
     * @return
     */
    ScaleType getScaleType(int position);
}
