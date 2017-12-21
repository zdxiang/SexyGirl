package cn.zdxiang.sexygirl.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

import cn.zdxiang.sexygirl.model.ContentListSub;

/**
 * Created by JM on 2017/11/30 0030.下午 7:33
 * Description:
 */

public class ImageAdapter extends PagerAdapter {

    private List<ContentListSub> list;

    public ImageAdapter(List<ContentListSub> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ImageView imageView = new ImageView(container.getContext());
        Glide.with(container.getContext()).load(list.get(position).getBig()).transition(new DrawableTransitionOptions().crossFade(300)).into((ImageView) imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
