package com.pppcar.zoombanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.hjq.http.EasyHttp;
import com.hjq.http.lifecycle.ActivityLifecycle;
import com.hjq.http.listener.HttpCallback;
import com.pppcar.zoombanner.adapter.MainAdapter;
import com.pppcar.zoombanner.base.BaseActivity;
import com.pppcar.zoombanner.databinding.ActivityMainBinding;
import com.pppcar.zoombanner.databinding.LayoutHomeHeaderBinding;
import com.pppcar.zoombanner.network.HttpData;
import com.pppcar.zoombanner.network.api.MainApi;
import com.pppcar.zoombanner.network.beans.MainResult;
import com.pppcar.zoombanner.utils.StatusBarUtil;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.util.BannerUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private int bannerMargin;
    private int bannerWidth;
    private int bannerHeight;
    private int mOffset = 0;
    private int mScrollY = 0;
    private LayoutHomeHeaderBinding mHeaderBinding;
    private MainAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ActivityMainBinding.class);
        binding.refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        setupHeaderLayout();
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, binding.searchContainer);
        initBanner();
        initRv();
        initEvent();
    }

    private void setupHeaderLayout() {
        mHeaderBinding = LayoutHomeHeaderBinding.inflate(LayoutInflater.from(this));
        mHeaderBinding.mainBanner.addBannerLifecycleObserver(this);
    }

    private void initRv() {
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MainAdapter(this, mHeaderBinding.getRoot());
        binding.rv.setHasFixedSize(true);
        binding.rv.setNestedScrollingEnabled(false);
        binding.rv.setAdapter(mAdapter);
    }

    private void initEvent() {
        binding.refreshLayout.setOnMultiListener(new SimpleMultiListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                mHeaderBinding.mainBanner.stop();
                mOffset = offset / 2;
                binding.searchContainer.setAlpha(1 - Math.min(percent, 1));
                ZoomBanner(offset);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(bannerWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                lp.leftMargin = 0;
                lp.rightMargin = 0;
                // 图片的LayoutParams
                mHeaderBinding.mainBanner.setLayoutParams(lp);
            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {
                if (bannerMargin < 0) {
                    resetBanner(0);
                }
                mHeaderBinding.mainBanner.start();
            }

        });
        binding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initBanner();
            }
        });
        binding.scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = BannerUtils.dp2px(150);
            private int color = ContextCompat.getColor(MyApplication.getInstance(), R.color.colorPrimary) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
//                    buttonBar.setAlpha(1f * mScrollY / h);
                    binding.searchContainer.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                   /* if (mScrollY<=h) {
                        ZoomBanner(mScrollY);
                    }*/
                }
                lastScrollY = scrollY;
            }
        });
//        buttonBar.setAlpha(0);
        binding.searchContainer.setBackgroundColor(0);
    }

    private void initBanner() {
        EasyHttp
                .get(new ActivityLifecycle(this))
                .api(new MainApi())
                .request(new HttpCallback<HttpData<MainResult>>(this) {
                    @Override
                    public void onSucceed(HttpData<MainResult> result) {
                        super.onSucceed(result);
                        MainResult data = result.getData();
                        if (data != null) {
                            List<MainResult.ResIndexBannersDTO> banners = data.getResIndexBanners();
                            if (banners != null && banners.size() > 0) {
                                bindData(banners);
                            }
                            List<MainResult.NewsDTO> news = data.getNews();
                            if (news !=null && news.size() > 0) {
                                mAdapter.refresh(news);
                            }
                        }
                        binding.refreshLayout.finishRefresh();
                    }
                });
    }

    private void bindData(List<MainResult.ResIndexBannersDTO> bannerBeans) {
        mHeaderBinding.mainBanner.setAdapter(new BannerImageAdapter<MainResult.ResIndexBannersDTO>(bannerBeans) {
            @Override
            public void onBindView(BannerImageHolder holder, MainResult.ResIndexBannersDTO data, int position, int size) {
                //图片加载自己实现
                Glide.with(holder.itemView)
                        .load(data.getImgUrl())
                        .error(R.mipmap.default_banner)
                        .placeholder(R.mipmap.default_banner)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);
            }

        });
    }

    public void ZoomBanner(int scrollY) {
        // 缩放图片
        if (bannerWidth == 0) {
            bannerWidth = mHeaderBinding.mainBanner.getWidth();
            bannerHeight = mHeaderBinding.mainBanner.getHeight();
        }
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(bannerWidth + scrollY * 2, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.leftMargin = -scrollY;
        lp.rightMargin = -scrollY;
        lp.height = bannerHeight + scrollY;
        // 图片的LayoutParams
        mHeaderBinding.mainBanner.setLayoutParams(lp);
        bannerMargin = -scrollY;
    }

    public void resetBanner(int tempMargin) {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(bannerWidth + tempMargin * 2, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.leftMargin = tempMargin;
        lp.rightMargin = tempMargin;
        lp.height = bannerHeight + tempMargin;
        // 图片的LayoutParams
        mHeaderBinding.mainBanner.setLayoutParams(lp);
        bannerMargin = tempMargin;
    }
}
