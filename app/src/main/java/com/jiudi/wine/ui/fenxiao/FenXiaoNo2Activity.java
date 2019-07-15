package com.jiudi.wine.ui.fenxiao;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.github.nukc.LoadMoreWrapper.LoadMoreAdapter;
import com.jiudi.wine.R;
import com.jiudi.wine.adapter.vl.VHotGrid3Adapter;
import com.jiudi.wine.adapter.vl.VHotLinear3Adapter;
import com.jiudi.wine.adapter.vl.VHotSingle4Adapter;
import com.jiudi.wine.adapter.vl.VMineQuanYi2Adapter;
import com.jiudi.wine.adapter.vl.VMineQuanYiAdapter;
import com.jiudi.wine.base.BaseActivity;
import com.jiudi.wine.bean.GuideGodsBean;
import com.jiudi.wine.bean.MainGodsBean;
import com.jiudi.wine.manager.RequestManager;
import com.jiudi.wine.net.RetrofitCallBack;
import com.jiudi.wine.net.RetrofitRequestInterface;
import com.jiudi.wine.util.SPUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FenXiaoNo2Activity extends BaseActivity implements View.OnClickListener, LoadMoreAdapter.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener{


    private ImageView back;
    private android.support.v4.widget.SwipeRefreshLayout swipeRefreshLayout;
    private android.support.v7.widget.RecyclerView recycler;
    private VirtualLayoutManager manager;
    private DelegateAdapter adapter;
    final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

    private List<MainGodsBean> mHotVlList = new ArrayList<>();
    private List<GuideGodsBean> mGuidVlList = new ArrayList<>();
    private VHotLinear3Adapter vHotLinearAdapter;
    public VHotSingle4Adapter vHotSingle4Adapter;
    private VHotGrid3Adapter vHotGridAdapter;
    private VMineQuanYi2Adapter vMineQuanYiAdapter;
    private JSONObject jsondata;
    private boolean stoploadmore = false;
    private LoadMoreAdapter mLoadMoreAdapter;
    private int page = 0;
    private int limit = 20;
    private boolean isdianzhu;
    private android.widget.TextView looktitle;
    private String nolevel;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_up;
    }

    @Override
    public void initView() {

        back = (ImageView) findViewById(R.id.back);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        looktitle = (TextView) findViewById(R.id.looktitle);
    }

    @Override
    public void initData() {
        isdianzhu=getIntent().getBooleanExtra("isdianzhu",false);
        if(isdianzhu){
            looktitle.setText("代理权益");
        }else{
            looktitle.setText("升级代理");
        }
//        getGodsList(false);
        getFenXiao();
    }

    @Override
    public void initEvent() {

        swipeRefreshLayout.setOnRefreshListener(this);
    }
    public void buildRecycleView(boolean needscroll) {
        if(adapter==null){
            manager = new VirtualLayoutManager(mActivity);
            recycler.setLayoutManager(manager);
            adapter = new DelegateAdapter(manager);
            RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
            recycler.setRecycledViewPool(viewPool);
            viewPool.setMaxRecycledViews(0, 10);
            SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
            LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
            GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(5);
            gridLayoutHelper.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return 1;
                }
            });
            gridLayoutHelper.setAutoExpand(false);

            GridLayoutHelper gridLayoutHelper2 = new GridLayoutHelper(2);
            gridLayoutHelper2.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return 1;
                }
            });
            gridLayoutHelper2.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return 1;
                }
            });

            gridLayoutHelper2.setAutoExpand(false);
            StickyLayoutHelper stickyLayoutHelper = new StickyLayoutHelper();
            stickyLayoutHelper.setStickyStart(true);
            vMineQuanYiAdapter = new VMineQuanYi2Adapter(mActivity,singleLayoutHelper,this,jsondata);
            vMineQuanYiAdapter.setIsDianZhu(isdianzhu);
            adapters.add(vMineQuanYiAdapter);


            vHotLinearAdapter = new VHotLinear3Adapter(mActivity, linearLayoutHelper, mGuidVlList);
            adapters.add(vHotLinearAdapter);

            if(isdianzhu){
                vHotSingle4Adapter=new VHotSingle4Adapter(mActivity,singleLayoutHelper);
                adapters.add(vHotSingle4Adapter);
                vHotGridAdapter = new VHotGrid3Adapter(mActivity, gridLayoutHelper2, mHotVlList);
                adapters.add(vHotGridAdapter);
            }

            adapter.setAdapters(adapters);
            recycler.setAdapter(adapter);
//            mLoadMoreAdapter = LoadMoreWrapper.with(adapter)
//                    .setLoadMoreEnabled(!stoploadmore)
//                    .setListener(this)
//                    .into(recycler);
        }else {
//            try {
//                vHotGridAdapter.notifyDataSetChanged();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }

    }

    @Override
    public void onRefresh() {
//        stoploadmore=false;
//        page=0;
//        mHotVlList.clear();
//        getGodsList(false);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMore(LoadMoreAdapter.Enabled enabled) {
//        page=page+limit;
//        getGodsList(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.goup:

                manager.scrollToPositionWithOffset(2, 0);
                break;
        }
    }

    private void getFenXiao() {
        Map<String, String> map = new HashMap<>();
        if(isdianzhu){
            RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).getFenXiao(SPUtil.get("head", "").toString(),RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
                @Override
                public void onSuccess(String response) {
                    try {
                        JSONObject res = new JSONObject(response);
                        int code = res.getInt("code");
                        String info = res.getString("msg");
                        if (code == 200) {

                            JSONObject data=res.getJSONObject("data");
                            jsondata=data;
                            JSONArray promotion_product = data.getJSONArray("list");
                            for (int i = 0; i < promotion_product.length(); i++) {
                                JSONObject jsonObject = promotion_product.getJSONObject(i);
                                MainGodsBean bean = new MainGodsBean();
                                bean.id = jsonObject.optString("id");
                                bean.image = jsonObject.optString("image");
                                bean.store_name = jsonObject.optString("store_name");
                                bean.keyword = jsonObject.optString("keyword");
                                bean.sales = jsonObject.optInt("sales");
                                bean.stock = jsonObject.optInt("stock");
                                bean.vip_price = jsonObject.optString("price");
                                bean.price = jsonObject.optString("price");
                                bean.unit_name = jsonObject.optString("unit_name");
                                mHotVlList.add(bean);
                            }
                            GuideGodsBean guideGodsBean1 = new GuideGodsBean();
                            nolevel = data.getJSONObject("agent").optString("name");

                            JSONArray list1 = data.getJSONArray("promotion_product");
                            for (int i = 0; i < list1.length(); i++) {
                                JSONObject jsonObject = list1.getJSONObject(i);
                                MainGodsBean bean = new MainGodsBean();
                                bean.id = jsonObject.optString("id");
                                bean.image = jsonObject.optString("image");
                                bean.store_name = jsonObject.optString("store_name");
                                bean.keyword = jsonObject.optString("keyword");
                                bean.sales = jsonObject.optInt("sales");
                                bean.stock = jsonObject.optInt("stock");
                                bean.vip_price = jsonObject.optString("vip_price");
                                bean.price = jsonObject.optString("price");
                                bean.unit_name = jsonObject.optString("unit_name");
                                guideGodsBean1.listGods.add(bean);

                            }
                            guideGodsBean1.introduce=data.optString("introduce");
                            if("初级代理".equals(nolevel)){
                                guideGodsBean1.level="中级代理";
                                mGuidVlList.add(guideGodsBean1);
                            }
                            if("中级代理".equals(nolevel)){
                                guideGodsBean1.level="高级代理";
                                mGuidVlList.add(guideGodsBean1);
                            }



                            buildRecycleView(false);

                        }else {
                            Toast.makeText(mActivity,info,Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Throwable t) {

                }
            });

        }else{
            RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).getFenXiao2(SPUtil.get("head", "").toString(),RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
                @Override
                public void onSuccess(String response) {
                    try {
                        JSONObject res = new JSONObject(response);
                        int code = res.getInt("code");
                        String info = res.getString("msg");
                        if (code == 200) {
                            JSONObject data=res.getJSONObject("data");
                            jsondata=data;
                            GuideGodsBean guideGodsBean1=new GuideGodsBean();
                            guideGodsBean1.level="初级代理";
                            JSONArray list1 = data.getJSONArray("list");
                            for (int i = 0; i < list1.length(); i++) {
                                    JSONObject jsonObject = list1.getJSONObject(i);
                                    MainGodsBean bean = new MainGodsBean();
                                    bean.id = jsonObject.optString("id");
                                    bean.image = jsonObject.optString("image");
                                    bean.store_name = jsonObject.optString("store_name");
                                    bean.keyword = jsonObject.optString("keyword");
                                    bean.sales = jsonObject.optInt("sales");
                                    bean.stock = jsonObject.optInt("stock");
                                    bean.vip_price = jsonObject.optString("vip_price");
                                    bean.price = jsonObject.optString("price");
                                    bean.unit_name = jsonObject.optString("unit_name");
                                    guideGodsBean1.listGods.add(bean);

                                }
                            GuideGodsBean guideGodsBean2=new GuideGodsBean();
                            guideGodsBean2.level="中级代理";
                            JSONArray list2 = data.getJSONArray("list2");
                            for (int i = 0; i < list2.length(); i++) {
                                JSONObject jsonObject = list2.getJSONObject(i);
                                MainGodsBean bean = new MainGodsBean();
                                bean.id = jsonObject.optString("id");
                                bean.image = jsonObject.optString("image");
                                bean.store_name = jsonObject.optString("store_name");
                                bean.keyword = jsonObject.optString("keyword");
                                bean.sales = jsonObject.optInt("sales");
                                bean.stock = jsonObject.optInt("stock");
                                bean.vip_price = jsonObject.optString("vip_price");
                                bean.price = jsonObject.optString("price");
                                bean.unit_name = jsonObject.optString("unit_name");
                                guideGodsBean2.listGods.add(bean);

                            }
                            GuideGodsBean guideGodsBean3=new GuideGodsBean();
                            guideGodsBean3.level="高级代理";
                            JSONArray list3 = data.getJSONArray("list3");
                            for (int i = 0; i < list3.length(); i++) {
                                JSONObject jsonObject = list3.getJSONObject(i);
                                MainGodsBean bean = new MainGodsBean();
                                bean.id = jsonObject.optString("id");
                                bean.image = jsonObject.optString("image");
                                bean.store_name = jsonObject.optString("store_name");
                                bean.keyword = jsonObject.optString("keyword");
                                bean.sales = jsonObject.optInt("sales");
                                bean.stock = jsonObject.optInt("stock");
                                bean.vip_price = jsonObject.optString("vip_price");
                                bean.price = jsonObject.optString("price");
                                bean.unit_name = jsonObject.optString("unit_name");
                                guideGodsBean3.listGods.add(bean);

                            }
                            JSONObject artics=data.getJSONObject("artics");

                            guideGodsBean1.introduce=artics.optString("introduce1");
//                            guideGodsBean2.introduce=artics.optString("introduce2");
//                            guideGodsBean3.introduce=artics.optString("introduce3");





                            mGuidVlList.add(guideGodsBean1);
//                            mGuidVlList.add(guideGodsBean2);
//                            mGuidVlList.add(guideGodsBean3);

//                                JSONArray data = res.getJSONArray("data");
//                                for (int i = 0; i < data.length(); i++) {
//                                    JSONObject jsonObject = data.getJSONObject(i);
//                                    MainGodsBean bean = new MainGodsBean();
//                                    bean.id = jsonObject.optString("id");
//                                    bean.image = jsonObject.optString("image");
//                                    bean.store_name = jsonObject.optString("store_name");
//                                    bean.keyword = jsonObject.optString("keyword");
//                                    bean.sales = jsonObject.optInt("sales");
//                                    bean.stock = jsonObject.optInt("stock");
//                                    bean.vip_price = jsonObject.optString("vip_price");
//                                    bean.price = jsonObject.optString("price");
//                                    bean.unit_name = jsonObject.optString("unit_name");
//                                    mHotVlList.add(bean);
//                                }



                            buildRecycleView(false);

                        }else {
                            Toast.makeText(mActivity,info,Toast.LENGTH_SHORT).show();
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


    public void noMoreData() {
        if (mLoadMoreAdapter != null) {
            mLoadMoreAdapter.setLoadMoreEnabled(false);
            mLoadMoreAdapter.setShowNoMoreEnabled(true);
            mLoadMoreAdapter.getOriginalAdapter().notifyDataSetChanged();
        }
    }
}
