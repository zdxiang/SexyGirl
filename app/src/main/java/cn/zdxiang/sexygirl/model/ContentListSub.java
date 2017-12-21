package cn.zdxiang.sexygirl.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.zdxiang.sexygirl.ui.adapter.SexyGirlAdapter;

/**
 * Created by JM on 2017/12/1 0001.下午 5:52
 * Description:
 */

public class ContentListSub implements MultiItemEntity, Serializable {

    @SerializedName("big")
    private String big;
    @SerializedName("small")
    private String small;
    @SerializedName("middle")
    private String middle;

    public ContentListSub(String big, String small, String middle) {
        this.big = big;
        this.small = small;
        this.middle = middle;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    @Override
    public int getItemType() {
        return SexyGirlAdapter.TYPE_LEVEL_1;
    }
}
