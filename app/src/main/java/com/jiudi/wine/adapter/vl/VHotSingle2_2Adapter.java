package com.jiudi.wine.adapter.vl;

import android.app.Activity;
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
import com.jiudi.wine.bean.GodMiaoSha;
import com.jiudi.wine.bean.RecommendHotBean;
import com.jiudi.wine.manager.AccountManager;
import com.jiudi.wine.ui.cart.CartDetailActivity;
import com.jiudi.wine.ui.main.HomeMMActivity;
import com.jiudi.wine.ui.main.KanJiaListActivity;
import com.jiudi.wine.ui.user.account.LoginActivity;
import com.jiudi.wine.util.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admin on 2017/5/16.
 */

public class VHotSingle2_2Adapter extends DelegateAdapter.Adapter {
    public Context context;
    private LayoutHelper helper;
    private LinearLayoutManager manager;
    private List<GodMiaoSha> mmiaoshalist;
    private RecyclerCommonAdapter<GodMiaoSha> mCarBeanAdapter;
    private Timer timer;
    private String destime;
    TextView day;
    TextView hour;
    TextView min;
    TextView sec;



    public VHotSingle2_2Adapter(Context context, LayoutHelper helper,List<GodMiaoSha> mmiaoshalist) {
        this.context = context;
        this.helper = helper;
        this.mmiaoshalist = mmiaoshalist;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return this.helper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart_hotrc, parent, false);
        return new ViewHolder(view);
    }
    private  String getTimeZeroString(int fixday) {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH) - fixday, 0, 0, 0);
        Date beginOfDate = cal.getTime();

        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(beginOfDate);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(manager==null){
            destime=getTimeZeroString(-1);

            hour = (TextView) holder.itemView.findViewById(R.id.hour);
            min = (TextView) holder.itemView.findViewById(R.id.min);
            sec = (TextView) holder.itemView.findViewById(R.id.sec);

            if (timer == null) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            if(context!=null){
                                ((Activity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String[] array= DateTimeUtil.getDistanceTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),destime);
//                                        day.setText(array[0]);
                                        hour.setText(array[1]);
                                        min.setText(array[2]);
                                        sec.setText(array[3]);
                                    }
                                });
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, 0, 1000);
            }

            RecyclerView hrecycle;
            hrecycle = (RecyclerView) holder.itemView.findViewById(R.id.hrecycle);
            manager = new LinearLayoutManager(context);
            manager.setOrientation(OrientationHelper.HORIZONTAL);
            hrecycle.setLayoutManager(manager);
            mCarBeanAdapter = new RecyclerCommonAdapter<GodMiaoSha>(context, R.layout.item_cart_hotr3, mmiaoshalist) {

                @Override
                protected void convert(com.jiudi.wine.adapter.recycler.base.ViewHolder holder, final GodMiaoSha recommendHotBean, int position) {
                    int swidth= (((BaseActivity)context).px-40)/4;
                    LinearLayout hlc=holder.itemView.findViewById(R.id.hlc);
                    ImageView img=holder.itemView.findViewById(R.id.picture);
                    holder.setText(R.id.title,recommendHotBean.title);
                    holder.setText(R.id.show_price,recommendHotBean.price);
                    hlc.setLayoutParams(new LinearLayout.LayoutParams(swidth, LinearLayout.LayoutParams.WRAP_CONTENT));
//                    img.setLayoutParams(new LinearLayout.LayoutParams(swidth-100, LinearLayout.LayoutParams.WRAP_CONTENT));
                    RequestOptions options = new RequestOptions()
                            .fitCenter()
                            .diskCacheStrategy(DiskCacheStrategy.ALL);//缓存全尺寸
                    Glide.with(context).load(recommendHotBean.image).apply(options).into(img);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(AccountManager.sUserBean==null)
                                context.startActivity(new Intent(context, LoginActivity.class));
                            else
                                context.startActivity(new Intent(context, HomeMMActivity.class));
//                            context.startActivity(new Intent(context, CartDetailActivity.class).putExtra("id",recommendHotBean.product_id));
                        }
                    });



                }

            };
            hrecycle.setAdapter(mCarBeanAdapter);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(AccountManager.sUserBean==null)
                        context.startActivity(new Intent(context, LoginActivity.class));
                    else
                        context.startActivity(new Intent(context, HomeMMActivity.class));
                }
            });
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
