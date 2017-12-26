package cn.zdxiang.sexygirl.ui.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.List;

import cn.zdxiang.sexygirl.R;
import cn.zdxiang.sexygirl.model.ContentList;
import cn.zdxiang.sexygirl.model.ContentListSub;
import cn.zdxiang.sexygirl.model.ImgUrlModel;

/**
 * Created by JM on 2017/11/28 0028.上午 11:44
 * Description:
 */

public class SexyGirlAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, SexyGirlViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    private Context context;
    private int size;

    public void setSize(int size) {
        this.size = size;
    }

    public SexyGirlAdapter(Context context, List<MultiItemEntity> data) {
        super(data);
        this.size = ScreenUtils.getScreenWidth() / 3;
        this.context = context;
//        setMultiTypeDelegate(new MultiTypeDelegate<ContentList>() {
//            @Override
//            protected int getItemType(ContentList contentList) {
//                return 0;
//            }
//        });
        addItemType(0, R.layout.item_sexygirl_level1);
        addItemType(1, R.layout.item_sexygirl_level2);
    }


    @Override
    protected void convert(final SexyGirlViewHolder holder, MultiItemEntity multiItemEntity) {

        switch (holder.getItemViewType()) {
            case 0:
                ContentList contentList = (ContentList) multiItemEntity;
                holder.setText(R.id.tv_image_type, contentList.getTitle());
                break;

            case 1:
                ContentListSub contentListSub = (ContentListSub) multiItemEntity;
                final ImageView imageView = holder.getView(R.id.imageView);
                imageView.setLayoutParams(new RelativeLayout.LayoutParams(size, size));
                Glide.with(context).load(contentListSub.getMiddle()).transition(new DrawableTransitionOptions().crossFade(300)).into(imageView);
                break;
        }
    }
}
