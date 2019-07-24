package com.jiudi.wine.ui.main;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jiudi.wine.R;
import com.jiudi.wine.base.BaseFragment;
import com.jiudi.wine.bean.Fictitious;
import com.jiudi.wine.bean.RecommendTabBean;
import com.jiudi.wine.event.PassCartEvent;
import com.jiudi.wine.manager.AccountManager;
import com.jiudi.wine.manager.RequestManager;
import com.jiudi.wine.net.RetrofitCallBack;
import com.jiudi.wine.net.RetrofitRequestInterface;
import com.jiudi.wine.ui.cart.CartDetailActivity;
import com.jiudi.wine.ui.fenxiao.FenXiaoMenuActivity;
import com.jiudi.wine.ui.fenxiao.FenXiaoNoActivity;
import com.jiudi.wine.ui.user.account.LoginActivity;
import com.jiudi.wine.ui.user.account.TongZhiActivity;
import com.jiudi.wine.util.NetworkUtil;
import com.jiudi.wine.util.SPUtil;
import com.jiudi.wine.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主页
 */
public class HomeVV3Fragment extends BaseFragment {


//    private String[] titles = {"推荐", "日用百货", "个护清洁","数码电器"};
private List<RecommendTabBean> mRecommendTabList = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private LinearLayout needshow;
    private ImageView showhead;
    private TextView content;
    private Fictitious fictitious;
    private String[] titles;
    private FrameLayout flChange;
    private TabLayout mainTab;
    private TextView tongzhi;
    private android.widget.EditText searchTag;
    private TextView seach;


    @Override
    protected int getInflateViewId() {
        return R.layout.fragment_homevv3;
    }

    @Override
    public void initView() {

        needshow = (LinearLayout) findViewById(R.id.needshow);
        showhead = (ImageView) findViewById(R.id.showhead);
        content = (TextView) findViewById(R.id.content);
        flChange = (FrameLayout) findViewById(R.id.fl_change);
        mainTab = (TabLayout) findViewById(R.id.main_tab);
        tongzhi = (TextView) findViewById(R.id.tongzhi);
        searchTag = (EditText) findViewById(R.id.search_tag);
        seach = (TextView) findViewById(R.id.seach);
    }

    @Override
    public void initData() {


        buildFicition();
        getHomeBanner();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void buildTab(){
        mFragments.clear();
        mainTab.removeAllTabs();
        mFragments.add(new HomeBVFragment());
//        for (int i = 1; i <mRecommendTabList.size() ; i++) {
//            mFragments.add(new HomeCV2Fragment().setArgumentz("cId",mRecommendTabList.get(i).id+""));
//        }
//        titles = new String[mRecommendTabList.size()];
//        for (int i = 0; i <mRecommendTabList.size() ; i++) {
//            titles[i]=mRecommendTabList.get(i).cate_name;
//        }
//        for (int i = 0; i < mRecommendTabList.size(); i++) {
//            //插入tab标签
//            mainTab.addTab(mainTab.newTab().setText(mRecommendTabList.get(i).cate_name));
//        }
//        if(mRecommendTabList.size()>4){
//
//            mainTab.setTabMode(TabLayout.MODE_SCROLLABLE);
//        }else{
//
//            mainTab.setTabMode(TabLayout.MODE_FIXED);
//        }
//        mainTab.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                getFragmentManager().beginTransaction().replace(R.id.fl_change,mFragments.get(tab.getPosition())).commitAllowingStateLoss();
//            }
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
        getFragmentManager().beginTransaction().replace(R.id.fl_change,mFragments.get(0)).commitAllowingStateLoss();
    }

    private void getHomeBanner() {
        Map<String, String> map = new HashMap<>();
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).getFlash(SPUtil.get("head", "").toString(), RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("msg");
                    JSONObject data = res.getJSONObject("data");
                    if (code == 200) {
                        mRecommendTabList.clear();
                        JSONArray category = data.getJSONArray("category");
                        RecommendTabBean hotbean = new RecommendTabBean();
                        hotbean.id = "0";
                        hotbean.cate_name = "推荐";
                        mRecommendTabList.add(hotbean);
                        for (int i = 0; i < category.length(); i++) {
                            JSONObject jsonObject = category.getJSONObject(i);
                            RecommendTabBean bean = new RecommendTabBean();
                            bean.id = jsonObject.optString("id");
                            bean.cate_name = jsonObject.optString("cate_name");
                            mRecommendTabList.add(bean);
                        }
                        buildTab();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {
                if (!NetworkUtil.isConnected()) {
                    ToastUtil.showShort(mActivity, R.string.net_error);
                } else {
                    ToastUtil.showShort(mActivity, getString(R.string.net_error));
                }
            }
        });
    }



    @Override
    public void initEvent() {
        needshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fictitious.type==1) {
                    if ("1".equals((AccountManager.sUserBean == null ? "0" : AccountManager.sUserBean.is_promoter))) {
                        startActivity(new Intent(mActivity, FenXiaoMenuActivity.class));
                    } else {
                        startActivity(new Intent(mActivity, FenXiaoNoActivity.class));
                    }
                } else {
                    startActivity(new Intent(mActivity, CartDetailActivity.class).putExtra("id",fictitious.id+""));
                }
            }
        });

        searchTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(mActivity, SearchShopActivity.class));
            }
        });

        seach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EventBus.getDefault().post(new PassCartEvent());
//                startActivity(new Intent(mActivity, SearchMenuShopActivity.class));
            }
        });
        tongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                if(AccountManager.sUserBean==null)
                startActivity(new Intent(mActivity, LoginActivity.class));
            else
                startActivity(new Intent(mActivity, TongZhiActivity.class));
            }
        });
    }
    private void showGods(){
        needshow.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(needshow, "alpha", 0.0f, 1.0f).setDuration(2000);
        animator.start();
        content.setText("来自"+fictitious.city+"的"+fictitious.nickname+(fictitious.type==1?"升级成为代理":"购买了"+fictitious.store_name)+"");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideGods();
            }
        },5000);

    }
    private void hideGods(){
        AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
        alpha.setDuration(1000);
        needshow.startAnimation(alpha);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                needshow.setVisibility(View.GONE);
                buildFicition();
            }
        },1000);
    }
    public void buildFicition(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getFictitious();
            }
        },5000);
    }
    private void getFictitious() {
        Map<String, String> map = new HashMap<>();
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).getFictitious(SPUtil.get("head", "").toString(), RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {

                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("msg");
                    if (code == 200) {
                        GsonBuilder builder = new GsonBuilder();
                        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                                return new Date(json.getAsJsonPrimitive().getAsLong());
                            }
                        });
                        Gson gson = builder.create();
                        Type fictitiousType = new TypeToken<Fictitious>() {
                        }.getType();
                        fictitious=gson.fromJson(res.getJSONObject("data").toString(),fictitiousType);
                        showGods();
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
}
