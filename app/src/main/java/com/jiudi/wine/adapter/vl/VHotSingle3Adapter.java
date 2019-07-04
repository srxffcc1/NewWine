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

public class VHotSingle3Adapter extends DelegateAdapter.Adapter {
    public Context context;
    private LayoutHelper helper;
    private LinearLayoutManager manager;
    private List<RecommendHotBean> mHotRecommendList;
    private RecyclerCommonAdapter<RecommendHotBean> mCarBeanAdapter;


    public VHotSingle3Adapter(Context context, LayoutHelper helper, List<RecommendHotBean> mHotRecommendList) {
        this.context = context;
        this.helper = helper;
        this.mHotRecommendList = mHotRecommendList;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return this.helper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart_hotr, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(manager==null){
            RecyclerView hrecycle;
            hrecycle = (RecyclerView) holder.itemView.findViewById(R.id.hrecycle);
            manager = new LinearLayoutManager(context);
            manager.setOrientation(OrientationHelper.HORIZONTAL);
            hrecycle.setLayoutManager(manager);
            mCarBeanAdapter = new RecyclerCommonAdapter<RecommendHotBean>(context, R.layout.item_cart_hotr2, mHotRecommendList) {

                @Override
                protected void convert(com.jiudi.wine.adapter.recycler.base.ViewHolder holder, final RecommendHotBean recommendHotBean, int position) {
                    int swidth= (((BaseActivity)context).px-40-40-40)/4;
                    LinearLayout hlc=holder.itemView.findViewById(R.id.hlc);
                    ImageView img=holder.itemView.findViewById(R.id.picture);
                    holder.setText(R.id.title,recommendHotBean.title);
                    holder.setText(R.id.show_price,"¥"+("1".equals((AccountManager.sUserBean==null?"0":AccountManager.sUserBean.is_promoter))?recommendHotBean.vip_price:recommendHotBean.price));
                    hlc.setLayoutParams(new LinearLayout.LayoutParams(swidth, LinearLayout.LayoutParams.WRAP_CONTENT));
//                    img.setLayoutParams(new LinearLayout.LayoutParams(swidth-100, LinearLayout.LayoutParams.WRAP_CONTENT));
                    RequestOptions options = new RequestOptions()
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.ALL);//缓存全尺寸
                    Glide.with(context).load(recommendHotBean.pic).apply(options).into(img);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            context.startActivity(new Intent(context, CartDetailActivity.class).putExtra("id",recommendHotBean.product_id));
                        }
                    });



                }

            };
            hrecycle.setAdapter(mCarBeanAdapter);
        }


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