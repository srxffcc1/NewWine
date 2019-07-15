package com.jiudi.wine.adapter.vl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jiudi.wine.R;
import com.jiudi.wine.manager.AccountManager;
import com.jiudi.wine.manager.RequestManager;
import com.jiudi.wine.ui.fenxiao.TuanDuiActivity;
import com.jiudi.wine.ui.fenxiao.TuiGuangActivity;
import com.jiudi.wine.ui.user.WebActivity;
import com.jiudi.wine.ui.user.account.FenXiaoAccountActivity;
import com.jiudi.wine.ui.user.account.TextActivity;
import com.jiudi.wine.ui.user.account.TiXianActivity;
import com.jiudi.wine.ui.user.account.WorkEachActivity;
import com.m7.imkfsdk.KfStartHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by admin on 2017/5/16.
 */

public class VMineQuanYi2Adapter extends DelegateAdapter.Adapter {
    public Context context;
    private LayoutHelper helper;
    private View.OnClickListener listener;
    private boolean isdianzhu;
    private JSONObject jsondata;
    private KfStartHelper kefuhelper;
    private static final int REQUEST_CODE_PERMISSION_WRITE_STORAGE = 1001;
    private static final int REQUEST_CODE_UNKNOWN_APP = 100;
    private static final int REQUEST_CODE_OPENCHAT = 60;
    private LinearLayout mmline1;
    private ImageView head;
    private TextView name;
    private TextView level;
    private TextView passzxing;
    private LinearLayout mmline;
    private TextView goup;
    private LinearLayout needshowFeiudianzhu1;
    private TextView xieyitext;
    private TextView passxieyi;
    private FrameLayout needshowquanyi;
    private ImageView myshowgif;
    private LinearLayout needshowDianzhu;
    private LinearLayout shouyimingxi;
    private TextView leijishouyi;
    private TextView tleijishouyi;
    private TextView yitixianshouyi;
    private TextView tyitixianshouyi;
    private TextView weidaozhangshouyi;
    private TextView tweidaozhangshouyi;
    private TextView yuetitle;
    private TextView yuevalue;
    private TextView keyuetitle;
    private TextView keyuevalue;
    private TextView tixian;
    private LinearLayout kehuguanli;
    private TextView jiamengshangshuliang;
    private LinearLayout stepl;
    private TextView fensishuliang;
    private LinearLayout quanl;
    private TextView yaoqingren;
    private LinearLayout kefu;
    private LinearLayout needshowFeiudianzhu;
    private TextView canpassget;


    public VMineQuanYi2Adapter(Context context, LayoutHelper helper, View.OnClickListener listener, JSONObject jsondata) {
        this.context = context;
        this.helper = helper;
        this.listener = listener;
        this.jsondata = jsondata;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return this.helper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_quanyiup2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        mmline1 = (LinearLayout) holder.itemView.findViewById(R.id.mmline1);
        head = (ImageView) holder.itemView.findViewById(R.id.head);
        name = (TextView) holder.itemView.findViewById(R.id.name);
        level = (TextView) holder.itemView.findViewById(R.id.level);
        passzxing = (TextView) holder.itemView.findViewById(R.id.passzxing);
        mmline = (LinearLayout) holder.itemView.findViewById(R.id.mmline);
        goup = (TextView) holder.itemView.findViewById(R.id.goup);
        needshowFeiudianzhu1 = (LinearLayout) holder.itemView.findViewById(R.id.needshow_feiudianzhu1);
        xieyitext = (TextView) holder.itemView.findViewById(R.id.xieyitext);
        passxieyi = (TextView) holder.itemView.findViewById(R.id.passxieyi);
        needshowquanyi = (FrameLayout) holder.itemView.findViewById(R.id.needshowquanyi);
        myshowgif = (ImageView) holder.itemView.findViewById(R.id.myshowgif);
        needshowDianzhu = (LinearLayout) holder.itemView.findViewById(R.id.needshow_dianzhu);
        shouyimingxi = (LinearLayout) holder.itemView.findViewById(R.id.shouyimingxi);
        leijishouyi = (TextView) holder.itemView.findViewById(R.id.leijishouyi);
        tleijishouyi = (TextView) holder.itemView.findViewById(R.id.tleijishouyi);
        yitixianshouyi = (TextView) holder.itemView.findViewById(R.id.yitixianshouyi);
        tyitixianshouyi = (TextView) holder.itemView.findViewById(R.id.tyitixianshouyi);
        weidaozhangshouyi = (TextView) holder.itemView.findViewById(R.id.weidaozhangshouyi);
        tweidaozhangshouyi = (TextView) holder.itemView.findViewById(R.id.tweidaozhangshouyi);
        yuetitle = (TextView) holder.itemView.findViewById(R.id.yuetitle);
        yuevalue = (TextView) holder.itemView.findViewById(R.id.yuevalue);
        keyuetitle = (TextView) holder.itemView.findViewById(R.id.keyuetitle);
        keyuevalue = (TextView) holder.itemView.findViewById(R.id.keyuevalue);
        tixian = (TextView) holder.itemView.findViewById(R.id.tixian);
        kehuguanli = (LinearLayout)holder.itemView.findViewById(R.id.kehuguanli);
        jiamengshangshuliang = (TextView) holder.itemView.findViewById(R.id.jiamengshangshuliang);
        stepl = (LinearLayout) holder.itemView.findViewById(R.id.stepl);
        fensishuliang = (TextView) holder.itemView.findViewById(R.id.fensishuliang);
        quanl = (LinearLayout) holder.itemView.findViewById(R.id.quanl);
        yaoqingren = (TextView) holder.itemView.findViewById(R.id.yaoqingren);
        kefu = (LinearLayout) holder.itemView.findViewById(R.id.kefu);
        needshowFeiudianzhu = (LinearLayout) holder.itemView.findViewById(R.id.needshow_feiudianzhu);
        canpassget=(TextView) holder.itemView.findViewById(R.id.canpassget);
        String nolevel = null;
        try {
            nolevel = jsondata.getJSONObject("agent").optString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String finalNolevel = nolevel;
        tixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, TiXianActivity.class));
            }
        });
        kehuguanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, TuanDuiActivity.class));

            }
        });
        shouyimingxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, FenXiaoAccountActivity.class));
            }
        });
        passxieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent( context,TextActivity.class).putExtra("url",RequestManager.mBaseUrl+"api/Article/visit/id/16"));
            }
        });
        kefu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        kefuhelper = new KfStartHelper((Activity) context);

                context.startActivity(new Intent(context, WorkEachActivity.class));
//                kefuhelper.initSdkChat("e183f850-6650-11e9-b942-bf7a16e827df", "咨询", AccountManager.sUserBean.uid,REQUEST_CODE_OPENCHAT);//陈辰正式
            }
        });
        RequestOptions optionsgif = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE);//缓存全尺寸

        goup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onClick(v);


            }
        });
//            Glide.with(context).asGif().load(recommendImgBeans.get(0).pic).apply(optionsgif).into(tagholder.image0);
//        Glide.with(context).asGif().load(R.drawable.abbt).apply(optionsgif).into(myshowgif);
        passzxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.startActivity(new Intent(context, TuiGuangActivity.class));
            }
        });
//        myshowgif.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                context.startActivity(new Intent(context, WebActivity.class).putExtra("url", "https://i.eqxiu.com/s/BHOzngSZ?share_level=1&from_user=2019053014e6a20b&from_id=443ba141-3&share_time=1561629426288&from=groupmessage&isappinstalled=0"));
//            }
//        });
        String introduce=null;
        if(jsondata!=null){
            introduce=jsondata.optString("introduce");
        }
        if(isdianzhu){
            needshowDianzhu.setVisibility(View.VISIBLE);
            needshowFeiudianzhu.setVisibility(View.GONE);
            if("null".equals(introduce)||"".equals(introduce)||introduce==null){

                needshowFeiudianzhu.setVisibility(View.GONE);
            }else {
                needshowFeiudianzhu.setVisibility(View.VISIBLE);
            }
            needshowFeiudianzhu1.setVisibility(View.GONE);
            mmline.setVisibility(View.GONE);
            bindDataToView(jsondata,true);
        }else{
            needshowDianzhu.setVisibility(View.GONE);
            needshowFeiudianzhu.setVisibility(View.VISIBLE);
            needshowFeiudianzhu1.setVisibility(View.VISIBLE);
            mmline.setVisibility(View.VISIBLE);
            bindDataToView(jsondata,false);
        }
    }


    @Override
    public int getItemCount() {
        return 1;
    }

    public void bindDataToView(JSONObject data, boolean need) {
        String shenfen = "";
        if ("1".equals((AccountManager.sUserBean == null ? "0" : AccountManager.sUserBean.is_promoter))) {
            if ("2".equals(AccountManager.sUserBean.agent_id)) {
                shenfen = "钻石代理";
            } else if ("1".equals(AccountManager.sUserBean.agent_id)) {

                shenfen = "普通代理";
            } else {
                shenfen = "普通用户";
            }
        } else {
            shenfen = "普通用户";
        }

        try {
            if(isdianzhu){
                passzxing.setVisibility(View.VISIBLE);
                String nolevel = data.getJSONObject("agent").optString("name");
                if("初级代理".equals(nolevel)){
                    canpassget.setText(data.getJSONObject("promotion_text").getString("promotion_text1"));
                    shenfen="初级代理";
                } else if("中级代理".equals(nolevel)){
                    canpassget.setText(data.getJSONObject("promotion_text").getString("promotion_text2"));
                    shenfen="中级代理";
                }else {
                    shenfen="高级代理";
                }

            }else{

                passzxing.setVisibility(View.GONE);
                canpassget.setText(data.getJSONObject("promotion_text").getString("promotion_text"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        level.setText(shenfen);
//        code.setText("邀请码："+AccountManager.sUserBean.uid);
        name.setText(AccountManager.sUserBean.nickname);
        RequestOptions requestOptions = RequestOptions.circleCropTransform().error(R.drawable.head_defuat_circle);
        Glide.with(context).load((AccountManager.sUserBean.avatar.startsWith("http")) ? AccountManager.sUserBean.avatar : "http://" + AccountManager.sUserBean.avatar).apply(requestOptions).into(head);
        if (data != null) {
            if(need){
                try {
                    yuevalue.setText((data.getJSONObject("userInfo").getDouble("now_money")+data.getDouble("number"))+"元");
                    keyuevalue.setText(""+data.getString("allnumber")+"元");


                    leijishouyi.setText(data.getString("allnumber")+"元");
                    yitixianshouyi.setText(data.getString("extractNumber")+"元");
                    weidaozhangshouyi.setText(data.getString("number")+"元");

                    tleijishouyi.setText("今日：+"+data.getString("todayAllNumber")+"元");
                    tyitixianshouyi.setText("今日：+"+data.getString("todayExtractNumber")+"元");
                    tweidaozhangshouyi.setText("今日：+"+data.getString("todayNumber")+"元");

                    jiamengshangshuliang.setText(data.getString("directNum")+"位");
                    fensishuliang.setText(data.getString("teamNum")+"位");
                    yaoqingren.setText(data.getJSONObject("userInfo").optString("spread").replace("null",""));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void initView() {



    }

    public void setIsDianZhu(boolean isdianzhu) {
        this.isdianzhu = isdianzhu;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


}
