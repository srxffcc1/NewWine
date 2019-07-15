package com.jiudi.wine.adapter.vl;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.jiudi.wine.R;
import com.jiudi.wine.bean.CartTitleBean;

/**
 * Created by admin on 2017/5/16.
 */

public class VIntroduceHeadAdapter extends DelegateAdapter.Adapter {
    public Context context;
    private LayoutHelper helper;
    private CartTitleBean mcarttitlebean;



    public VIntroduceHeadAdapter(Context context, LayoutHelper helper, CartTitleBean mcarttitlebean) {
        this.context = context;
        this.helper = helper;
        this.mcarttitlebean = mcarttitlebean;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return this.helper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart_introduce_head, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
         TextView name;
         TextView proAddress;
         TextView packNomals;
         TextView nw;
         TextView saveCondition;
         TextView vol;
        if("null".equals(mcarttitlebean.name)||mcarttitlebean.name==null){
            holder.itemView.findViewById(R.id.noneed).setVisibility(View.GONE);
        }
        name = (TextView) holder.itemView.findViewById(R.id.name);
        proAddress = (TextView)  holder.itemView.findViewById(R.id.pro_address);
        packNomals = (TextView)  holder.itemView.findViewById(R.id.pack_nomals);
        nw = (TextView)  holder.itemView.findViewById(R.id.nw);
        saveCondition = (TextView)  holder.itemView.findViewById(R.id.save_condition);
        vol = (TextView)  holder.itemView.findViewById(R.id.vol);
        name.setText(mcarttitlebean.name);
        packNomals.setText(mcarttitlebean.pack_nomals);
        nw.setText(mcarttitlebean.nw);
        vol.setText(mcarttitlebean.vol);
        proAddress.setText(mcarttitlebean.pro_address);
        saveCondition.setText(mcarttitlebean.save_condition);
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
