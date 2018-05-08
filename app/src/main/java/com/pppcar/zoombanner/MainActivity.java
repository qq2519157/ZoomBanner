package com.pppcar.zoombanner;

import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.pppcar.zoombanner.adapter.mainAdapter;
import com.pppcar.zoombanner.entity.BannerBean;
import com.pppcar.zoombanner.net.ApiWrapper;
import com.pppcar.zoombanner.net.RetrofitUtil;
import com.pppcar.zoombanner.utils.MyImageLoader;
import com.pppcar.zoombanner.utils.StatusBarUtil;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    private Banner mBanner;
    private SmartRefreshLayout mSmartRefreshLayout;
    private LinearLayout mSearchContainer;
    private RecyclerView mRecyclerView;
    private int bannerMargin;
    private int bannerWidth;
    private int bannerHeight;
    private int mOffset = 0;
    private int mScrollY = 0;
    private NestedScrollView mNesScrollview;
    private View mHeaderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSearchContainer = findViewById(R.id.search_container);
        mSmartRefreshLayout = findViewById(R.id.refresh_layout);
        mNesScrollview = findViewById(R.id.scrollView);
        mRecyclerView = findViewById(R.id.rv);
        mSmartRefreshLayout.setRefreshHeader(new PhoenixHeader(this));
        setupHeaderLayout();
        //状态栏透明和间距处理
        StatusBarUtil.immersive(this);
        StatusBarUtil.setPaddingSmart(this, mSearchContainer);
        initBanner();
        initRv();
        initEvent();
    }

    private void setupHeaderLayout() {
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.layout_home_header, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mHeaderView.setLayoutParams(params);
        mBanner=mHeaderView.findViewById(R.id.main_banner);
    }
    private void initRv() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<String> texts=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            texts.add("新产生的数据"+i);
        }
        mainAdapter adapter = new mainAdapter(this, mHeaderView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setAdapter(adapter);
        adapter.refresh(texts);
    }

    private void initEvent() {
        mSmartRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderPulling(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mBanner.stopAutoPlay();
                mOffset = offset / 2;
                if (mSearchContainer!=null) {
                    mSearchContainer.setAlpha(1 - Math.min(percent, 1));
                }
                ZoomBanner(offset);

            }


            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                super.onRefresh(refreshlayout);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(bannerWidth, LinearLayout.LayoutParams.MATCH_PARENT);
                lp.leftMargin = 0;
                lp.rightMargin = 0;
                // 图片的LayoutParams
                mBanner.setLayoutParams(lp);

            }

            @Override
            public void onHeaderReleasing(RefreshHeader header, float percent, int offset, int bottomHeight, int extendHeight) {
                mOffset = offset / 2;
                mSearchContainer.setAlpha(1 - Math.min(percent, 1));
                if (bannerMargin < 0) {
                    resetBanner((int) (bannerMargin * (percent)));
                }
                if (percent == 0) {
                    mBanner.startAutoPlay();
                }
            }

            @Override
            public void onFooterReleasing(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {
                super.onFooterReleasing(footer, percent, offset, footerHeight, extendHeight);
            }
        });
        mNesScrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(150);
            private int color = ContextCompat.getColor(MyApplication.getInstance(), R.color.colorPrimary) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY = scrollY > h ? h : scrollY;
//                    buttonBar.setAlpha(1f * mScrollY / h);
                    mSearchContainer.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                   /* if (mScrollY<=h) {
                        ZoomBanner(mScrollY);
                    }*/
                }
                lastScrollY = scrollY;
            }
        });
//        buttonBar.setAlpha(0);
        mSearchContainer.setBackgroundColor(0);
    }

    private void initBanner() {
        ApiWrapper.getApiService()
                .getBanner()
                .compose(RetrofitUtil.getInstance().<List<BannerBean>>applySchedulers())
                .subscribe(new Subscriber<List<BannerBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<BannerBean> bannerBeans) {
                        bindData(bannerBeans);
                    }
                });
    }

    private void bindData(List<BannerBean> bannerBeans) {
        List<String> imgs = new ArrayList<>();
        for (BannerBean resIndexBanner : bannerBeans) {
            imgs.add(resIndexBanner.getImgUrl());
        }
        mBanner.setImageLoader(new MyImageLoader())
                .setDelayTime(3000)
                .setImages(imgs)
                .start();
    }

    public void ZoomBanner(int scrollY) {
        // 缩放图片
        if (bannerWidth == 0) {
            bannerWidth = mBanner.getWidth();
            bannerHeight = mBanner.getHeight();
        }
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(bannerWidth + scrollY * 2, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.leftMargin = -scrollY;
        lp.rightMargin = -scrollY;
        lp.height = bannerHeight + scrollY;
        // 图片的LayoutParams
        mBanner.setLayoutParams(lp);
        bannerMargin = -scrollY;
    }

    public void resetBanner(int tempMargin) {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(bannerWidth + tempMargin * 2, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.leftMargin = tempMargin;
        lp.rightMargin = tempMargin;
        lp.height = bannerHeight + tempMargin;
        // 图片的LayoutParams
        mBanner.setLayoutParams(lp);
        bannerMargin = tempMargin;
    }
}
