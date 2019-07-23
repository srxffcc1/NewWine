package com.jiudi.wine.adapter.vl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jiudi.wine.R;
import com.jiudi.wine.bean.CartAttrValue;
import com.jiudi.wine.manager.AccountManager;
import com.jiudi.wine.manager.RequestManager;
import com.jiudi.wine.net.RetrofitCallBack;
import com.jiudi.wine.net.RetrofitRequestInterface;
import com.jiudi.wine.ui.cart.PayDingDanActivity;
import com.jiudi.wine.util.DateTimeUtil;
import com.jiudi.wine.util.SPUtil;
import com.jiudi.wine.util.TimeUtil;
import com.jiudi.wine.util.WechatUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by admin on 2017/5/16.
 */

public class VMBangKanAdapter extends DelegateAdapter.Adapter {
    public Activity context;
    private LayoutHelper helper;
    private View.OnClickListener listener;
    private boolean isdianzhu;

    public void setJsondata(JSONObject jsondata) {
        this.jsondata = jsondata;
    }

    private JSONObject jsondata;
    private static final int REQUEST_CODE_PERMISSION_WRITE_STORAGE = 1001;
    private static final int REQUEST_CODE_UNKNOWN_APP = 100;
    private static final int REQUEST_CODE_OPENCHAT = 60;
    ImageView kImage;
    TextView kTitle;
    TextView kPrice;
    TextView kKkk;
    LinearLayout timetextneedshow;
    TextView hour;
    TextView min;
    TextView sec;
    TextView kYikan;
    ProgressBar progressz;
    TextView passKan;
    LinearLayout addBang;
    private String urlshare;


    public VMBangKanAdapter(Activity context, LayoutHelper helper, View.OnClickListener listener, JSONObject jsondata) {
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
                .inflate(R.layout.activity_kill_detail_up, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        try {
            urlshare = "http://test.jiudicar.com/wap/store/cut_con/id/"+jsondata.getJSONObject("bargain").getString("id")+"/bargainUid/"+jsondata.getJSONObject("userInfoBargain").getString("uid")+".html";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String minp = null;
        try {
            minp =jsondata.getJSONObject("bargain").getString("min_price");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        kImage = (ImageView) holder.itemView.findViewById(R.id.k_image);
        kTitle = (TextView) holder.itemView.findViewById(R.id.k_title);
        kPrice = (TextView) holder.itemView.findViewById(R.id.k_price);
        kKkk = (TextView) holder.itemView.findViewById(R.id.k_kkk);
        timetextneedshow = (LinearLayout) holder.itemView.findViewById(R.id.timetextneedshow);
        hour = (TextView) holder.itemView.findViewById(R.id.hour);
        min = (TextView) holder.itemView.findViewById(R.id.min);
        sec = (TextView) holder.itemView.findViewById(R.id.sec);
        kYikan = (TextView) holder.itemView.findViewById(R.id.k_yikan);
        progressz = (ProgressBar) holder.itemView.findViewById(R.id.progressz);
        passKan = (TextView) holder.itemView.findViewById(R.id.pass_kan);
        addBang = (LinearLayout) holder.itemView.findViewById(R.id.add_bang);

        final String finalMinp = minp;
        passKan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getpricePercent() ==100){


                    orderKan(getKanId(),getProductId());

                }else {
                    Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);
                    byte[] tumb=bmpToByteArray(bmp,true);
                    WechatUtil.wechatShare(urlshare,"帮我砍个价","我在同城快酒上发现意外好货，一起来砍价低至"+ finalMinp +"元拿",tumb, SendMessageToWX.Req.WXSceneSession);
//                new ShareAction(context).setDisplayList(
//                        SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
//                        .setShareboardclickCallback(new ShareBoardlistener() {
//                            @Override
//                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
//
//                                if(share_media==SHARE_MEDIA.WEIXIN){
//
//                                }
//                                if(share_media==SHARE_MEDIA.WEIXIN_CIRCLE){
//                                    Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);
//                                    byte[] tumb=bmpToByteArray(bmp,true);
//                                    WechatUtil.wechatShare(urlshare,"帮我砍个价","同城快酒,优质体验",tumb, SendMessageToWX.Req.WXSceneTimeline);
//                                }
//                            }
//                        }).open();
                }

            }
        });
        bindDataToView(jsondata,false);

    }

    private String getProductId() {
        String result="";
        try {
            result=jsondata.getJSONObject("bargain").getString("product_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getKanId() {
        String result="";
        try {
            result=jsondata.getJSONObject("bargain").getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private int getpricePercent(){
        int pricePercent=0;
        try {
            pricePercent=Integer.parseInt(jsondata.getString("pricePercent"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return pricePercent;
    }
    public void buildListView(JSONObject bean) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_kill_detail_up_item, addBang, false);
         ImageView bkImage;
         TextView bkTitle;
         TextView bkSectitle;
         TextView bkK;
        bkImage = (ImageView) view.findViewById(R.id.bk_image);
        bkTitle = (TextView) view.findViewById(R.id.bk_title);
        bkSectitle = (TextView) view.findViewById(R.id.bk_sectitle);
        bkK = (TextView) view.findViewById(R.id.bk_k);
        try {
            bkTitle.setText(bean.getString("nickname")+"");
            bkK.setText(bean.getString("nickname")+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            bkK.setText("砍了"+bean.getString("price")+"元");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestOptions requestOptions = RequestOptions.circleCropTransform().error(R.drawable.head_defuat_circle);
        String avater= "";
        try {
            avater = bean.getString("avater");
        } catch (JSONException e) {
        }
        Glide.with(context).load((avater.startsWith("http")) ? avater : "http://" + avater).apply(requestOptions).into(bkImage);
        if(getpricePercent()==100){
            passKan.setText("立即购买");
        }
        addBang.addView(view);

    }
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public void bindDataToView(final JSONObject data, boolean need) {

        addBang.removeAllViews();
        try {


            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);//缓存全尺寸

            Glide.with(context).load(data.getJSONObject("bargain").getString("image")).apply(options).into(kImage);
            kYikan.setText("已砍"+data.getString("selfCutPrice")+",还差"+data.getString("price")+"元");
            kTitle.setText(data.getJSONObject("bargain").getString("title"));
            kPrice.setText("原价"+data.getJSONObject("bargain").getString("price")+"元");
            kKkk.setText("砍到"+data.getJSONObject("bargain").getString("min_price")+"元拿");
            int bili= (int) (data.getDouble("pricePercent")/100*1000);
            progressz.setProgress(bili);
            Timer timer;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String[] array= new String[0];
                                try {
                                    array = DateTimeUtil.getDistanceTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), TimeUtil.formatLongAll(data.getJSONObject("bargain").getString("stop_time")));
                                    hour.setText(array[1]);
                                    min.setText(array[2]);
                                    sec.setText(array[3]);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 0, 1000);

            JSONObject userHelpList = data.getJSONObject("userHelpList");
            Iterator iterator = userHelpList.keys();
            while (iterator.hasNext()) {
                String key = iterator.next() + "";
                buildListView(userHelpList.getJSONObject(key));
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initView() {


    }


    private void orderKan(String bargainId,String productId) {
        Map<String, String> map = new HashMap<>();
        map.put("bargainId",bargainId);
        map.put("cartNum", "1");
        map.put("productId", productId);
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).orderKan(SPUtil.get("head", "").toString(),RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("msg");
                    if (code == 200) {
                        String cartId = res.getJSONObject("data").getString("cartId");
                        context.startActivity(new Intent(context, PayDingDanActivity.class).putExtra("cartId", cartId));
                    }else{
                        Toast.makeText(context,info,Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {

                Toast.makeText(context,"未登录",Toast.LENGTH_SHORT).show();
            }
        });
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
