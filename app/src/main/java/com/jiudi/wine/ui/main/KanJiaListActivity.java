package com.jiudi.wine.ui.main;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jiudi.wine.R;
import com.jiudi.wine.base.BaseActivity;
import com.jiudi.wine.bean.InvolvedKanJiaGods;
import com.jiudi.wine.bean.NormalKanJiaGods;
import com.jiudi.wine.manager.RequestManager;
import com.jiudi.wine.net.RetrofitCallBack;
import com.jiudi.wine.net.RetrofitRequestInterface;
import com.jiudi.wine.util.DateTimeUtil;
import com.jiudi.wine.util.SPUtil;
import com.jiudi.wine.util.TimeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class KanJiaListActivity extends BaseActivity {
    private int page = 0;
    private int limit = 100;
    private android.widget.LinearLayout yikancontent;
    private android.widget.LinearLayout nokancontent;
    private List<InvolvedKanJiaGods> InvolvedKanJiaGodsList =new ArrayList<>();
    private List<NormalKanJiaGods> NormalKanJiaGodsList=new ArrayList<>();



    @Override
    protected int getContentViewId() {
        return R.layout.activity_kill_list;
    }

    @Override
    public void initView() {

        yikancontent = (LinearLayout) findViewById(R.id.yikancontent);
        nokancontent = (LinearLayout) findViewById(R.id.nokancontent);


    }

    @Override
    public void initData() {
        getKanList();
    }

    @Override
    public void initEvent() {

    }

    private void getKanList() {
        Map<String, String> map = new HashMap<>();
        map.put("first", page + "");
        map.put("limit", limit + "");
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).getKanList(SPUtil.get("head", "").toString(), RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("msg");
                    if (code == 200) {
                        JSONObject data=res.getJSONObject("data");
                        JSONObject bargain=data.getJSONObject("bargain");
                        JSONArray involved=bargain.getJSONArray("involved");
                        for (int i = 0; i <involved.length() ; i++) {
                            JSONObject jsonObject=involved.getJSONObject(i);
                            InvolvedKanJiaGods kanJiaGods=new InvolvedKanJiaGods();
                            kanJiaGods.id=jsonObject.optString("id");
                            kanJiaGods.bargain_price=jsonObject.optString("bargain_price");
                            kanJiaGods.title=jsonObject.optString("title");
                            kanJiaGods.price=jsonObject.optString("price");
                            kanJiaGods.bargain_price_min=jsonObject.optString("bargain_price_min");
                            kanJiaGods.stop_time=jsonObject.optString("stop_time");
                            kanJiaGods.image=jsonObject.optString("image");
                            InvolvedKanJiaGodsList.add(kanJiaGods);
                        }
                        JSONArray normal=bargain.getJSONArray("normal");
                        for (int i = 0; i <normal.length() ; i++) {
                            JSONObject jsonObject=normal.getJSONObject(i);
                            NormalKanJiaGods kanJiaGods=new NormalKanJiaGods();
                            kanJiaGods.id=jsonObject.optString("id");
                            kanJiaGods.product_id=jsonObject.optString("product_id");
                            kanJiaGods.title=jsonObject.optString("title");
                            kanJiaGods.price=jsonObject.optString("price");
                            kanJiaGods.min_price=jsonObject.optString("min_price");
                            kanJiaGods.stop_time=jsonObject.optString("stop_time");
                            kanJiaGods.image=jsonObject.optString("image");
                            NormalKanJiaGodsList.add(kanJiaGods);
                        }
                        buildList();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

    public void buildList(){
        for (int i = 0; i < InvolvedKanJiaGodsList.size() ; i++) {
            buildListView1(InvolvedKanJiaGodsList.get(i));
        }
        for (int i = 0; i <NormalKanJiaGodsList.size() ; i++) {
            buildListView2(NormalKanJiaGodsList.get(i));
        }

    }
    public void buildListView1(final InvolvedKanJiaGods bean){
        View view= LayoutInflater.from(this).inflate(R.layout.activity_kill_list_item1,yikancontent,false);

         android.widget.ImageView kanImage;
         android.widget.TextView kanTitle;
         android.widget.TextView kanPrice;
         LinearLayout timetextneedshow;
         final android.widget.TextView hour;
         final android.widget.TextView min;
         final android.widget.TextView sec;
         android.widget.TextView passKan;


        kanImage = (ImageView) view.findViewById(R.id.kan_image);
        kanTitle = (TextView)  view.findViewById(R.id.kan_title);
        kanPrice = (TextView)  view.findViewById(R.id.kan_price);
        timetextneedshow = (LinearLayout)  view.findViewById(R.id.timetextneedshow);
        hour = (TextView)  view.findViewById(R.id.hour);
        min = (TextView)  view.findViewById(R.id.min);
        sec = (TextView)  view.findViewById(R.id.sec);
        passKan = (TextView)  view.findViewById(R.id.pass_kan);
        if(!isFinishing()){
            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);//缓存全尺寸

            Glide.with(mActivity).load(bean.image).apply(options).into(kanImage);
            kanTitle.setText(bean.title);
            kanPrice.setText("已砍"+bean.price+"元");

            Timer timer;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        if(!isFinishing()){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String[] array= DateTimeUtil.getDistanceTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), TimeUtil.formatLongAll(bean.stop_time));
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
            passKan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mActivity,KanJiaActivity.class).putExtra("id",bean.id));
                }
            });
        }



        yikancontent.addView(view);
    }
    public void buildListView2(final NormalKanJiaGods bean){
        View view= LayoutInflater.from(this).inflate(R.layout.activity_kill_list_item2,nokancontent,false);

         ImageView kanImage;
         TextView kanTitle;
         TextView kanYuan;
         LinearLayout timetextneedshow;
         TextView kanDao;
         TextView passKan;


        kanImage = (ImageView)view. findViewById(R.id.kan_image);
        kanTitle = (TextView) view.findViewById(R.id.kan_title);
        kanYuan = (TextView) view.findViewById(R.id.kan_yuan);
        timetextneedshow = (LinearLayout) view.findViewById(R.id.timetextneedshow);
        kanDao = (TextView) view.findViewById(R.id.kan_dao);
        passKan = (TextView) view.findViewById(R.id.pass_kan);

        if(!isFinishing()){
            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);//缓存全尺寸

            Glide.with(mActivity).load(bean.image).apply(options).into(kanImage);
            kanTitle.setText(bean.title);
            kanYuan.setText("原价"+bean.price+"元");
            kanDao.setText("砍到"+bean.min_price+"元拿");
        }
        passKan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity,KanJiaActivity.class).putExtra("id",bean.id));
            }
        });
        nokancontent.addView(view);

    }
}
