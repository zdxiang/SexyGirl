package cn.zdxiang.sexygirl.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by JM on 2017/11/28 0028.下午 5:26
 * Description:
 */

public class CategoryDetails {

    @SerializedName("showapi_res_code")
    private int showapiResCode;
    @SerializedName("showapi_res_error")
    private String showapiResError;
    @SerializedName("showapi_res_body")
    private ShowapiResBodyBean showapiResBody;

    public int getShowapiResCode() {
        return showapiResCode;
    }

    public void setShowapiResCode(int showapiResCode) {
        this.showapiResCode = showapiResCode;
    }

    public String getShowapiResError() {
        return showapiResError;
    }

    public void setShowapiResError(String showapiResError) {
        this.showapiResError = showapiResError;
    }

    public ShowapiResBodyBean getShowapiResBody() {
        return showapiResBody;
    }

    public void setShowapiResBody(ShowapiResBodyBean showapiResBody) {
        this.showapiResBody = showapiResBody;
    }

    public static class ShowapiResBodyBean implements Serializable{
        @SerializedName("ret_code")
        private int retCode;
        @SerializedName("pagebean")
        private PageEntity pagebean;

        public int getRetCode() {
            return retCode;
        }

        public void setRetCode(int retCode) {
            this.retCode = retCode;
        }

        public PageEntity getPagebean() {
            return pagebean;
        }

        public void setPagebean(PageEntity pagebean) {
            this.pagebean = pagebean;
        }

        public static class PageEntity implements Serializable {
            @SerializedName("allPages")
            private int allPages;
            @SerializedName("currentPage")
            private int currentPage;
            @SerializedName("allNum")
            private int allNum;
            @SerializedName("maxResult")
            private int maxResult;
            @SerializedName("contentlist")
            private List<ContentList> contentlist;

            public int getAllPages() {
                return allPages;
            }

            public void setAllPages(int allPages) {
                this.allPages = allPages;
            }

            public int getCurrentPage() {
                return currentPage;
            }

            public void setCurrentPage(int currentPage) {
                this.currentPage = currentPage;
            }

            public int getAllNum() {
                return allNum;
            }

            public void setAllNum(int allNum) {
                this.allNum = allNum;
            }

            public int getMaxResult() {
                return maxResult;
            }

            public void setMaxResult(int maxResult) {
                this.maxResult = maxResult;
            }

            public List<ContentList> getContentlist() {
                return contentlist;
            }

            public void setContentlist(List<ContentList> contentlist) {
                this.contentlist = contentlist;
            }

        }
    }
}
