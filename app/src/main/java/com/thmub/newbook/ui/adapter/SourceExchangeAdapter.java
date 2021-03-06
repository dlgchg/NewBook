package com.thmub.newbook.ui.adapter;

import android.app.Activity;

import com.thmub.newbook.base.adapter.IViewHolder;
import com.thmub.newbook.base.adapter.QuickAdapter;
import com.thmub.newbook.bean.BookSearchBean;
import com.thmub.newbook.ui.adapter.holder.SourceExchangeHolder;

import java.lang.ref.WeakReference;
/**
 * Created by Zhouas666 on 2019-04-11
 * Github: https://github.com/zas023
 */
public class SourceExchangeAdapter extends QuickAdapter<BookSearchBean> {

    private WeakReference<Activity> activityRef;

    public SourceExchangeAdapter(Activity activity) {
        this.activityRef = new WeakReference<>(activity);
    }

    @Override
    protected IViewHolder<BookSearchBean> createViewHolder(int viewType) {
        return new SourceExchangeHolder();
    }

    @Override
    public synchronized void addItem(BookSearchBean value) {
        mList.add(value);
        activityRef.get().runOnUiThread(() -> {
            notifyDataSetChanged();
        });
    }
}
