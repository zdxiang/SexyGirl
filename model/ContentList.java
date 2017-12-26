package cn.zdxiang.sexygirl.model;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import cn.zdxiang.sexygirl.ui.adapter.SexyGirlAdapter;

/**
 * Created by JM on 2017/12/2 0002.下午 4:36
 * Description:
 */

public class ContentList extends AbstractExpandableItem<ContentListSub> implements MultiItemEntity, Serializable {

    @SerializedName("typeName")
    private String typeName;
    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private int type;
    @SerializedName("itemId")
    private String itemId;
    @SerializedName("ct")
    private String ct;

    @SerializedName("list")
    private List<ContentListSub> list;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public List<ContentListSub> getList() {
        return list;
    }

    public void setList(List<ContentListSub> list) {
        this.list = list;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return SexyGirlAdapter.TYPE_LEVEL_0;
    }
}