package cn.zdxiang.sexygirl.ui.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.umeng.analytics.MobclickAgent;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.http.okhttp.OkHttpUtils;

import org.litepal.crud.callback.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.BindView;
import cn.zdxiang.mysuites.ui.AdWebActivity;
import cn.zdxiang.mysuites.utils.ToastUtils;
import cn.zdxiang.sexygirl.MainActivity;
import cn.zdxiang.sexygirl.R;
import cn.zdxiang.sexygirl.base.BasePresenterImpl;
import cn.zdxiang.sexygirl.base.MvpFragment;
import cn.zdxiang.sexygirl.base.ResultCallback;
import cn.zdxiang.sexygirl.constant.APPKeys;
import cn.zdxiang.sexygirl.constant.AdCons;
import cn.zdxiang.sexygirl.constant.Apis;
import cn.zdxiang.sexygirl.model.CategoryDetails;
import cn.zdxiang.sexygirl.model.ContentList;
import cn.zdxiang.sexygirl.model.ContentListSub;
import cn.zdxiang.sexygirl.model.ImgUrlModel;
import cn.zdxiang.sexygirl.ui.adapter.MyImageTransAdapter;
import cn.zdxiang.sexygirl.ui.adapter.SexyGirlAdapter;
import cn.zdxiang.sexygirl.utils.Random2AdUtil;
import cn.zdxiang.sexygirl.widget.imagetrans.ImageTrans;
import cn.zdxiang.sexygirl.widget.imagetrans.MyImageLoad;
import cn.zdxiang.sexygirl.widget.imagetrans.ScaleType;
import cn.zdxiang.sexygirl.widget.imagetrans.listener.SourceImageViewParam;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by JM on 2017/11/27 0027.下午 9:27
 * Description:
 */

public class SexyGirlFragment extends MvpFragment implements BaseQuickAdapter.RequestLoadMoreListener {

    private static final String KEY_TITLE = "title";

    private static final String KEY_ID = "id";

    private SexyGirlAdapter adapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    private int imageId;

    private int mPage = 1;

    private int totalPage;

    private GridLayoutManager gridLayoutManager;

    public static SexyGirlFragment newInstance(String title, int id) {
        Bundle args = new Bundle();
        SexyGirlFragment fragment = new SexyGirlFragment();
        args.putString(KEY_TITLE, title);
        args.putInt(KEY_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    private boolean isClicking = false;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new SexyGirlAdapter(getContext(), null);
        recyclerView.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, recyclerView);
        gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.getItemViewType(position) == SexyGirlAdapter.TYPE_LEVEL_1 ? 1 : gridLayoutManager.getSpanCount();
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final BaseQuickAdapter baseQuickAdapter, final View view, final int position) {
                if (Random2AdUtil.random2Ad(getContext(),6)) return;
                if (isClicking) return;
                isClicking = true;
                if (adapter.getItemViewType(position) == SexyGirlAdapter.TYPE_LEVEL_0) return;
                //防止在图片未加载完成时点击导致报错
                if (view instanceof ImageView) {
                    ImageView v = (ImageView) view;
                    if (v.getDrawable() == null) {
                        return;
                    }
                }
                List<MultiItemEntity> data = adapter.getData();
                final List<String> imgList = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i) instanceof ContentListSub) {
                        ContentListSub entity = (ContentListSub) data.get(i);
                        imgList.add(entity.getBig());
                    } else {
                        ContentList entity = (ContentList) data.get(i);
                        imgList.add("下一组:\n" + entity.getTitle());
                    }
                }


                //从点击的position开始算起，获取到最近的title的position。
                int titlePosition = 0;
                for (int i = position; i < imgList.size(); i++) {
                    if (!imgList.get(i).startsWith("http")) {
                        titlePosition = i;
                        break;
                    }
                }
                final MyImageLoad myImageLoad = new MyImageLoad(getContext(), imgList.get(titlePosition));
                ImageTrans.with(getContext())
                        .setImageList(imgList)
                        .setImageLoad(myImageLoad)
                        .setNowIndex(position)
                        .setAdapter(new MyImageTransAdapter())
                        .setSourceImageViewParam(new SourceImageViewParam() {
                            @Override
                            public View getSourceView(int pos) {
                                int layoutPos = recyclerView.indexOfChild(view);
                                View view = recyclerView.getChildAt(layoutPos + pos - position);
                                if (view != null) return view;
                                return null;
                            }

                            @Override
                            public ScaleType getScaleType(int position) {
                                return ScaleType.CENTER_CROP;
                            }
                        })
                        .show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isClicking = false;
                    }
                }, 300);


            }
        });
    }

    @Override
    public void onFirstUserVisible() {
        super.onFirstUserVisible();
        imageId = getArguments().getInt(KEY_ID, 1);
        requestImages(imageId, 1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sexy_girl;
    }

    @Override
    protected BasePresenterImpl createPresenter() {
        return null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                gridLayoutManager.setSpanCount(3);
                adapter.setSize(ScreenUtils.getScreenWidth() / 3);
                break;

            case Configuration.ORIENTATION_LANDSCAPE:
                gridLayoutManager.setSpanCount(5);
                adapter.setSize(ScreenUtils.getScreenWidth() / 5);
                break;
        }
    }

    private void requestImages(int id, int page) {
        Map<String, String> params = new HashMap<>();
        params.put("showapi_appid", APPKeys.SHOWAPI_APPID);
        params.put("showapi_sign", APPKeys.SHOWAPI_SECRET);
        params.put("type", String.valueOf(id));
        params.put("page", String.valueOf(page));
        OkHttpUtils.get().url(Apis.DOMAIN + Apis.PATH_PICTURE_CATEGORY_DETAILS).params(params).build().execute(new ResultCallback<CategoryDetails>() {

            @Override
            public void onBefore(Request request, int id) {
                avi.smoothToShow();
            }

            @Override
            public void onAfter(int id) {
                avi.smoothToHide();
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtils.show(getContext(), e.getMessage());
            }

            @Override
            public void onResponse(CategoryDetails response, int id) {
                if (response.getShowapiResCode() == 0) {
                    CategoryDetails.ShowapiResBodyBean.PageEntity pagebean = response.getShowapiResBody().getPagebean();
                    List<ContentList> level1 = pagebean.getContentlist();

                    totalPage = pagebean.getAllPages();
                    List<MultiItemEntity> res = new ArrayList<>();
                    for (int i = 0; i < level1.size(); i++) {
                        ContentList contentList = level1.get(i);
                        contentList.setSubItems(contentList.getList());
                    }

                    res.addAll(level1);
                    if (mPage >= totalPage) {
                        adapter.loadMoreEnd();
                    } else {
                        //如果大于0则说明不是第一次加载
                        if (adapter.getItemCount() > 0) {
                            adapter.addData(level1);
                            adapter.loadMoreComplete();
                        } else {
                            adapter.setNewData(res);
                        }
                        adapter.expandAll();
                        mPage++;
                    }

                    for (int i = 0; i < level1.size(); i++) {
                        ContentList contentList = level1.get(i);
                        List<ContentListSub> list = contentList.getList();
                        for (int j = 0; j < list.size(); j++) {
                            ImgUrlModel imgUrlModel = new ImgUrlModel();
                            imgUrlModel.setUrl(list.get(j).getMiddle());
                            imgUrlModel.saveAsync().listen(new SaveCallback() {
                                @Override
                                public void onFinish(boolean b) {
                                    Log.d("saveInDb", "save==>" + b);
                                }
                            });
                        }
                    }
                } else {
                    adapter.loadMoreFail();
                    ToastUtils.show(getContext(), response.getShowapiResError());
                }
            }
        });
    }


    @Override
    public void onLoadMoreRequested() {
        requestImages(imageId, mPage);
    }

}
