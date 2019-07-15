package com.jiudi.wine.adapter.vl;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jiudi.wine.R;
import com.jiudi.wine.base.BaseActivity;
import com.jiudi.wine.bean.GuideGodsBean;
import com.jiudi.wine.ui.cart.CartDetailActivity;

import java.util.List;

/**
 * Created by admin on 2017/5/16.
 */

public class VHotLinear3Adapter extends DelegateAdapter.Adapter {
    public Context context;
    private LayoutHelper helper;
    private List<GuideGodsBean> mhotlist;
    private int fujian_px = 0;


    public VHotLinear3Adapter(Context context, LayoutHelper helper, List<GuideGodsBean> mhotlist) {
        this.context = context;
        this.helper = helper;
        this.mhotlist = mhotlist;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return this.helper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_quanyiup2_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        final GuideGodsBean carChoiceBean = mhotlist.get(position);
         TextView dlevel;
         TextView dintroduce;
         LinearLayout passmore;
         TextView dintroduce2;
         final ImageView dimage1;
         TextView dtitle1;
         TextView dmoney1;
         final ImageView dimage2;
         TextView dtitle2;
         TextView dmoney2;
         LinearLayout dimagel1;
         LinearLayout dimagel2;
        dlevel = (TextView) holder.itemView.findViewById(R.id.dlevel);
        dintroduce = (TextView) holder.itemView.findViewById(R.id.dintroduce);
        passmore = (LinearLayout) holder.itemView.findViewById(R.id.passmore);
        dintroduce2 = (TextView) holder.itemView.findViewById(R.id.dintroduce2);
        dimage1 = (ImageView) holder.itemView.findViewById(R.id.dimage1);
        dtitle1 = (TextView) holder.itemView.findViewById(R.id.dtitle1);
        dmoney1 = (TextView) holder.itemView.findViewById(R.id.dmoney1);
        dimage2 = (ImageView) holder.itemView.findViewById(R.id.dimage2);
        dtitle2 = (TextView) holder.itemView.findViewById(R.id.dtitle2);
        dmoney2 = (TextView) holder.itemView.findViewById(R.id.dmoney2);
        dimagel1=(LinearLayout) holder.itemView.findViewById(R.id.dimagel1);
        dimagel2=(LinearLayout) holder.itemView.findViewById(R.id.dimagel2);
        dlevel.setText(carChoiceBean.level+"权益");
        dintroduce.setText(carChoiceBean.introduce);
        dintroduce2.setText("购买以下任意商品即可升级成为"+carChoiceBean.level);
        RequestOptions options = new RequestOptions()
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL);//缓存全尺寸
        if(carChoiceBean.listGods.size()>1){
            dtitle1.setText(carChoiceBean.listGods.get(0).store_name);
            dmoney1.setText(carChoiceBean.listGods.get(0).price);
            dtitle2.setText(carChoiceBean.listGods.get(1).store_name);
            dmoney2.setText(carChoiceBean.listGods.get(1).price);
            Glide.with(context).load(carChoiceBean.listGods.get(0).image).apply(options).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    int swidth= (((BaseActivity)context).px-40)/2;
                    int height= (int) ((resource.getIntrinsicHeight()*1.0/resource.getIntrinsicWidth())*swidth);
                    dimage1.setLayoutParams(new LinearLayout.LayoutParams(swidth, height));
                    dimage1.setImageDrawable(resource);
                }
            });
            Glide.with(context).load(carChoiceBean.listGods.get(1).image).apply(options).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    int swidth= (((BaseActivity)context).px-40)/2;
                    int height= (int) ((resource.getIntrinsicHeight()*1.0/resource.getIntrinsicWidth())*swidth);
                    dimage2.setLayoutParams(new LinearLayout.LayoutParams(swidth, height));
                    dimage2.setImageDrawable(resource);
                }
            });
            dimagel1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, CartDetailActivity.class).putExtra("id",carChoiceBean.listGods.get(0).id));
                }
            });
            dimagel2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, CartDetailActivity.class).putExtra("id",carChoiceBean.listGods.get(1).id));
                }
            });
        }else if(carChoiceBean.listGods.size()>0){
            dtitle1.setText(carChoiceBean.listGods.get(0).store_name);
            dmoney1.setText(carChoiceBean.listGods.get(0).price);
            Glide.with(context).load(carChoiceBean.listGods.get(0).image).apply(options).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    int swidth= (((BaseActivity)context).px-40)/2;
                    int height= (int) ((resource.getIntrinsicHeight()*1.0/resource.getIntrinsicWidth())*swidth);
                    dimage1.setLayoutParams(new LinearLayout.LayoutParams(swidth, height));
                    dimage1.setImageDrawable(resource);
                }
            });
            dimagel1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, CartDetailActivity.class).putExtra("id",carChoiceBean.listGods.get(0).id));
                }
            });
        }







//         LinearLayout picturel;
//         final ImageView picture;
//         TextView title;
//         TextView secondTitle;
//         TextView showPrice;
//        picturel = (LinearLayout) holder.itemView.findViewById(R.id.picturel);
//        picture = (ImageView) holder.itemView.findViewById(R.id.picture);
//        title = (TextView) holder.itemView.findViewById(R.id.title);
//        secondTitle = (TextView) holder.itemView.findViewById(R.id.second_title);
//        showPrice = (TextView) holder.itemView.findViewById(R.id.show_price);
//
//
//        title.setText(carChoiceBean.store_name);
//        secondTitle.setText(carChoiceBean.keyword);
//        if(carChoiceBean.keyword==null||"".equals(carChoiceBean.keyword)){
//            secondTitle.setVisibility(View.GONE);
//        }else{
//            secondTitle.setVisibility(View.VISIBLE);
//        }
//        showPrice.setText("¥"+("1".equals((AccountManager.sUserBean==null?"0":AccountManager.sUserBean.is_promoter))?carChoiceBean.vip_price:carChoiceBean.price));
//
//
//        RequestOptions options = new RequestOptions()
//                .fitCenter()
//                .diskCacheStrategy(DiskCacheStrategy.ALL);//缓存全尺寸
////        System.out.println("推荐:"+carChoiceBean.image);
//        Glide.with(context).load(carChoiceBean.image).apply(options).into(new SimpleTarget<Drawable>() {
//            @Override
//            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                int swidth= (((BaseActivity)context).px-40)/2;
//                int height= (int) ((resource.getIntrinsicHeight()*1.0/resource.getIntrinsicWidth())*swidth);
//                picture.setLayoutParams(new LinearLayout.LayoutParams(swidth, height));
//                picture.setImageDrawable(resource);
////                picture.setImageResource(R.drawable.tmp_gods);
//            }
//        });
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                context.startActivity(new Intent(context, CartDetailActivity.class).putExtra("id",carChoiceBean.id));
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mhotlist.size();
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
