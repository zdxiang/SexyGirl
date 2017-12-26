package cn.zdxiang.sexygirl.model;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by JM on 2017/12/8 0008.下午 4:57
 * Description:
 */

public class ImgUrlModel extends DataSupport {

    @Column(ignore = false, unique = true, nullable = false, defaultValue = "0")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
