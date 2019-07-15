package com.jiudi.wine.adapter.vl;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jiudi.wine.R;
import com.jiudi.wine.adapter.recycler.RecyclerCommonAdapter;
import com.jiudi.wine.base.BaseActivity;
import com.jiudi.wine.bean.RecommendHotBean;
import com.jiudi.wine.manager.AccountManager;
import com.jiudi.wine.ui.cart.CartDetailActivity;

import java.util.List;

/**
 * Created by admin on 2017/5/16.
 */

public class VHotSingle4Adapter extends DelegateAdapter.Adapter {
    public Context context;
    private LayoutHelper helper;
    private LinearLayoutManager manager;
    private RecyclerCommonAdapter<RecommendHotBean> mCarBeanAdapter;


    public VHotSingle4Adapter(Context context, LayoutHelper helper) {
        this.context = context;
        this.helper = helper;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return this.helper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_quanyiup2_tip, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    private void initView() {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
