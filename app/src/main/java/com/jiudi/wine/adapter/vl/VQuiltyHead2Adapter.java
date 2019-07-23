package com.jiudi.wine.adapter.vl;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.jiudi.wine.R;
import com.jiudi.wine.bean.RecommendTitleBean;
import com.jiudi.wine.manager.AccountManager;
import com.jiudi.wine.ui.fenxiao.FenXiaoMenuActivity;
import com.jiudi.wine.ui.fenxiao.FenXiaoNoActivity;
import com.jiudi.wine.ui.main.KanJiaListActivity;
import com.jiudi.wine.ui.user.AllQuanActivity;
import com.jiudi.wine.ui.user.account.LoginActivity;
import com.jiudi.wine.ui.user.account.WorkEachActivity;

/**
 * Created by admin on 2017/5/16.
 */

public class VQuiltyHead2Adapter extends DelegateAdapter.Adapter {
    public Context context;
    private LayoutHelper helper;
    private RecommendTitleBean mcarttitlebean;


    public VQuiltyHead2Adapter(Context context, LayoutHelper helper, RecommendTitleBean mcarttitlebean) {
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
                .inflate(R.layout.item_cart_qulity2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
         TextView ct1;
         TextView ct2;
         TextView ct3;
         TextView ct4;
        ct1 = (TextView) holder.itemView.findViewById(R.id.ct1);
        ct2 = (TextView) holder.itemView.findViewById(R.id.ct2);
        ct3 = (TextView) holder.itemView.findViewById(R.id.ct3);
        ct4 = (TextView) holder.itemView.findViewById(R.id.ct4);


        ct1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AccountManager.sUserBean==null) {
                    context.startActivity(new Intent(context, LoginActivity.class));
                }else{
                    if ("1".equals((AccountManager.sUserBean == null ? "0" : AccountManager.sUserBean.is_promoter))) {
                        context.startActivity(new Intent(context, FenXiaoMenuActivity.class));
                    } else {
                        context.startActivity(new Intent(context, FenXiaoNoActivity.class));
                    }
                }
            }
        });


        ct2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AccountManager.sUserBean==null)
                    context.startActivity(new Intent(context, LoginActivity.class));
                else
                    context.startActivity(new Intent(context, WorkEachActivity.class));
            }
        });

        ct3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AccountManager.sUserBean==null)
                context.startActivity(new Intent(context, LoginActivity.class));
            else
            context.startActivity(new Intent(context, KanJiaListActivity.class));
            }
        });

        ct4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AccountManager.sUserBean==null)
                    context.startActivity(new Intent(context, LoginActivity.class));
                else
                    context.startActivity(new Intent(context, AllQuanActivity.class));
            }
        });

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
