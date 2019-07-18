package com.jiudi.wine.ui.main;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.github.nukc.LoadMoreWrapper.LoadMoreAdapter;
import com.github.nukc.LoadMoreWrapper.LoadMoreWrapper;
import com.jiudi.wine.R;
import com.jiudi.wine.adapter.vl.VBangKanHeadAdapter;
import com.jiudi.wine.adapter.vl.VHot2Adapter;
import com.jiudi.wine.adapter.vl.VHotGrid2Adapter;
import com.jiudi.wine.adapter.vl.VHotGridAdapter;
import com.jiudi.wine.adapter.vl.VHotHead2Adapter;
import com.jiudi.wine.adapter.vl.VHotSingle2Adapter;
import com.jiudi.wine.adapter.vl.VHotTabAdapter;
import com.jiudi.wine.adapter.vl.VLBannerAdapter;
import com.jiudi.wine.adapter.vl.VMBangKanAdapter;
import com.jiudi.wine.adapter.vl.VQuiltyHead2Adapter;
import com.jiudi.wine.base.BaseActivity;
import com.jiudi.wine.bean.MainGodsBean;
import com.jiudi.wine.manager.AccountManager;
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

public class KanJiaActivity extends BaseActivity implements View.OnClickListener, LoadMoreAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener{
    private android.widget.ImageView back;
    private android.widget.TextView looktitle;
    private RecyclerView recycler;
    private VirtualLayoutManager manager;
    private DelegateAdapter adapter;
    final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
    private LoadMoreAdapter mLoadMoreAdapter;
    private android.support.v4.widget.SwipeRefreshLayout swipeRefreshLayout;
    private VBangKanHeadAdapter vBangKanHeadAdapter;
    private VMBangKanAdapter vmBangKanAdapter;
    private VHotGridAdapter vHotGridAdapter;
    private List<MainGodsBean> mHotVlList = new ArrayList<>();

    private JSONObject bangkanjson;
    private int page = 0;
    private int limit = 20;
    private boolean stoploadmore = false;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_kill_detail;
    }

    @Override
    public void initView() {

        back = (ImageView) findViewById(R.id.back);
        looktitle = (TextView) findViewById(R.id.looktitle);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        recycler = (RecyclerView) findViewById(R.id.recycler);
    }

    @Override
    public void initData() {
        getDetail();
    }



    public void buildRecycleView(boolean needscroll) {

        if (adapter == null) {
            manager = new VirtualLayoutManager(mActivity);
            recycler.setLayoutManager(manager);
            adapter = new DelegateAdapter(manager);
            RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
            recycler.setRecycledViewPool(viewPool);
            viewPool.setMaxRecycledViews(0, 10);
            SingleLayoutHelper singleLayoutHelper = new SingleLayoutHelper();
            LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
            GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
            gridLayoutHelper.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return 1;
                }
            });
            gridLayoutHelper.setAutoExpand(false);

            GridLayoutHelper gridLayoutHelper2 = new GridLayoutHelper(1);
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


            vmBangKanAdapter = new VMBangKanAdapter(mActivity, singleLayoutHelper, this,bangkanjson);
            adapters.add(vmBangKanAdapter);


            vBangKanHeadAdapter = new VBangKanHeadAdapter(mActivity, singleLayoutHelper);
            adapters.add(vBangKanHeadAdapter);


            vHotGridAdapter = new VHotGridAdapter(mActivity, gridLayoutHelper, mHotVlList);
            adapters.add(vHotGridAdapter);


            adapter.setAdapters(adapters);
            recycler.setAdapter(adapter);
            mLoadMoreAdapter = LoadMoreWrapper.with(adapter)
                    .setLoadMoreEnabled(!stoploadmore)
                    .setListener(this)
                    .into(recycler);
        } else {
            try {
                vHotGridAdapter.notifyDataSetChanged();
                if (needscroll) {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    @Override
    public void initEvent() {
        swipeRefreshLayout.setOnRefreshListener(this);
    }
    private void getDetail() {
        Map<String, String> map = new HashMap<>();
        map.put("id", getIntent().getStringExtra("id"));
        map.put("bargainUid", "0");
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).getKanDetail(SPUtil.get("head", "").toString(),RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("msg");
                    if (code == 200) {
                        JSONObject data=res.getJSONObject("data");
                        bangkanjson=data;

                        getGodsList(false);
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
    private void getGodsList(final boolean needscroll) {
        Map<String, String> map = new HashMap<>();
        map.put("first", page + "");
        map.put("cId",  "0");
        map.put("limit", limit + "");
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).getGodsList(SPUtil.get("head", "").toString(), RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {


//                simpleRefresh.onRefreshComplete();
//                simpleRefresh.onLoadMoreComplete();
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("msg");
                    if (code == 200) {
                        JSONArray jsonArray = res.getJSONArray("data");
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                MainGodsBean bean = new MainGodsBean();
                                bean.id = jsonObject.optString("id");
                                bean.image = jsonObject.optString("image");
                                bean.store_name = jsonObject.optString("store_name");
                                bean.keyword = jsonObject.optString("keyword");
                                bean.sales = jsonObject.optInt("sales")+jsonObject.optInt("ficti");
                                bean.stock = jsonObject.optInt("stock");
                                bean.vip_price = jsonObject.optString("vip_price");
                                bean.price = jsonObject.optString("price");
                                bean.unit_name = jsonObject.optString("unit_name");
                                mHotVlList.add(bean);
                            }
                            buildRecycleView(needscroll);
                        } else {
//
                            noMoreData();
                            buildRecycleView(needscroll);
                            stoploadmore = true;
                        }


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

    public void noMoreData() {
        if (mLoadMoreAdapter != null) {
            mLoadMoreAdapter.setLoadMoreEnabled(false);
            mLoadMoreAdapter.setShowNoMoreEnabled(true);
            mLoadMoreAdapter.getOriginalAdapter().notifyDataSetChanged();
        }
    }
    @Override
    public void onRefresh() {
//        stoploadmore = false;
//        page = 0;
//        mHotVlList.clear();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onLoadMore(LoadMoreAdapter.Enabled enabled) {
        page = page + limit;
        getGodsList(false);
    }
}
