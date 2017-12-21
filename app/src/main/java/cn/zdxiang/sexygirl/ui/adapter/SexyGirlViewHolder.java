package cn.zdxiang.sexygirl.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;

import butterknife.ButterKnife;

/**
 * Created by JM on 2017/11/28 0028.下午 5:47
 * Description:
 */

public class SexyGirlViewHolder extends BaseViewHolder {

    private TextView tvTitle;

    private ImageView imageView;

    public SexyGirlViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
