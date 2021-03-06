package com.thmub.newbook.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.thmub.newbook.R;
import com.thmub.newbook.base.BaseMVPFragment;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.bean.type.StoreNodeType;
import com.thmub.newbook.bean.zhui.BookBean;
import com.thmub.newbook.bean.zhui.StoreBannerBean;
import com.thmub.newbook.bean.zhui.StoreNodeBean;
import com.thmub.newbook.manager.BookManager;
import com.thmub.newbook.presenter.BookStorePresenter;
import com.thmub.newbook.presenter.contract.BookStoreContract;
import com.thmub.newbook.ui.activity.BookDetailActivity;
import com.thmub.newbook.ui.activity.StoreNodeActivity;
import com.thmub.newbook.ui.adapter.StoreNodeAdapter;
import com.thmub.newbook.ui.adapter.holder.StoreNodeHolder;
import com.thmub.newbook.utils.NetworkUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by Zhouas666 on 2019-03-28
 * Github: https://github.com/zas023
 * <p>
 * 书城fragment
 */
public class BookStoreFragment extends BaseMVPFragment<BookStoreContract.Presenter>
        implements BookStoreContract.View {

    /***************************View*****************************/
    @BindView(R.id.store_rv_content)
    RecyclerView storeRvContent;
    @BindView(R.id.store_banner)
    Banner storeBanner;

    /***************************Variable*****************************/
    private StoreNodeAdapter mAdapter;

    private List<StoreNodeBean> mStoreNodes = new ArrayList<>();
    private List<StoreBannerBean> mStoreBanners;
    private List<String> mImages = new ArrayList<>();

    /***************************Initialization*****************************/
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book_store;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        for (StoreNodeType type : StoreNodeType.values()) {
            mStoreNodes.add(new StoreNodeBean(type.getTypeId(), type.getTypeName()));
        }
    }

    @Override
    protected void initWidget(Bundle savedInstanceState) {
        super.initWidget(savedInstanceState);

        //设置banner样式
        storeBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        storeBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(context).load(path).into(imageView);
            }
        });
        //设置banner动画效果
        storeBanner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        storeBanner.isAutoPlay(true);
        //设置轮播时间
        storeBanner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        storeBanner.setIndicatorGravity(BannerConfig.RIGHT);

        //Store Node
        mAdapter = new StoreNodeAdapter(new StoreNodeHolder.OnClickListener() {
            @Override
            public void onMore(StoreNodeBean item) {
                startActivity(new Intent(mContext, StoreNodeActivity.class)
                        .putExtra(StoreNodeActivity.EXTRA_STORE_NODE, item));
            }

            @Override
            public void onItemClick(BookBean book) {
                finishBookSearchBean(BookManager.getSearchBook(book));
            }
        });
        storeRvContent.setLayoutManager(new LinearLayoutManager(mContext));
        storeRvContent.setAdapter(mAdapter);

    }

    @Override
    protected void initClick() {
        super.initClick();
        storeBanner.setOnBannerListener(
                (pos) -> {
                    StoreBannerBean bean = mStoreBanners.get(pos);
                    if (bean.getType().equals("c-bookdetail"))
                        mPresenter.loadBookSearchBean(bean.getLink());
//                    if (bean.getType().equals("c-booklist"))
//                        BookListDetailActivity.startActivity(mContext, bean.getLink());
                });
    }

    @Override
    protected void processLogic() {
        super.processLogic();
        if (NetworkUtils.isNetWorkAvailable()) {
            mPresenter.loadStoreBanner();
        }
    }

    /*****************************Transaction******************************/
    @Override
    protected BookStoreContract.Presenter bindPresenter() {
        return new BookStorePresenter();
    }

    @Override
    public void finishStoreBanner(List<StoreBannerBean> items) {
        mStoreBanners = items;

        for (StoreBannerBean bean : mStoreBanners) {
            mImages.add(bean.getImg());
        }
        //设置图片集合
        storeBanner.setImages(mImages);
        storeBanner.start();

        //
        mAdapter.addItems(mStoreNodes);
    }

    @Override
    public void finishBookSearchBean(BookSearchBean bean) {
        startActivity(new Intent(mContext, BookDetailActivity.class)
                .putExtra(BookDetailActivity.EXTRA_BOOK, bean));
    }

    @Override
    public void showError(Throwable e) {

    }

    @Override
    public void complete() {

    }
}
