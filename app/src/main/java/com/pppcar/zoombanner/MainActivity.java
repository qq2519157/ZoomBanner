package com.pppcar.zoombanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.pppcar.zoombanner.adapter.MainAdapter;
import com.pppcar.zoombanner.base.BaseActivity;
import com.pppcar.zoombanner.databinding.ActivityMainBinding;
import com.pppcar.zoombanner.databinding.LayoutHomeHeaderBinding;
import com.pppcar.zoombanner.utils.StatusBarUtil;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.util.BannerUtils;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private int bannerMargin;
    private int bannerWidth;
    private int bannerHeight;
    private int mOffset = 0;
    private int mScrollY = 0;
    private LayoutHomeHeaderBinding mHeaderBinding;
    private MainAdapter mAdapter;

    private final List<Integer> bannerImages = Arrays.asList(
            R.mipmap.banner_1,
            R.mipmap.banner_2,
            R.mipmap.banner_3
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ActivityMainBinding.class);
        binding.refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        setupHeaderLayout();
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
                binding.refreshLayout.finishRefresh();
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
                    binding.searchContainer.setBackgroundColor(((255 * mScrollY / h) << 24) | color);
                }
                lastScrollY = scrollY;
            }
        });
        binding.searchContainer.setBackgroundColor(0);
    }

    private void initBanner() {
        mHeaderBinding.mainBanner.setAdapter(new BannerImageAdapter<Integer>(bannerImages) {
            @Override
            public void onBindView(BannerImageHolder holder, Integer data, int position, int size) {
                Glide.with(holder.itemView)
                        .load(data)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);
            }
        });
    }

    public void ZoomBanner(int scrollY) {
        if (bannerWidth == 0) {
            bannerWidth = mHeaderBinding.mainBanner.getWidth();
            bannerHeight = mHeaderBinding.mainBanner.getHeight();
        }
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(bannerWidth + scrollY * 2, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.leftMargin = -scrollY;
        lp.rightMargin = -scrollY;
        lp.height = bannerHeight + scrollY;
        mHeaderBinding.mainBanner.setLayoutParams(lp);
        bannerMargin = -scrollY;
    }

    public void resetBanner(int tempMargin) {
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(bannerWidth + tempMargin * 2, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.leftMargin = tempMargin;
        lp.rightMargin = tempMargin;
        lp.height = bannerHeight + tempMargin;
        mHeaderBinding.mainBanner.setLayoutParams(lp);
        bannerMargin = tempMargin;
    }
}
