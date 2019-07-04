package com.jiudi.wine.ui.user.account;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.dengzq.simplerefreshlayout.SimpleRefreshLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jiudi.wine.R;
import com.jiudi.wine.adapter.recycler.RecyclerCommonAdapter;
import com.jiudi.wine.adapter.recycler.base.ViewHolder;
import com.jiudi.wine.base.BaseFragment;
import com.jiudi.wine.bean.YongJin;
import com.jiudi.wine.manager.RequestManager;
import com.jiudi.wine.net.RetrofitCallBack;
import com.jiudi.wine.net.RetrofitRequestInterface;
import com.jiudi.wine.util.SPUtil;
import com.jiudi.wine.widget.SimpleBottomView;
import com.jiudi.wine.widget.SimpleLoadView;
import com.jiudi.wine.widget.SimpleRefreshView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FenXiaoAccountFragment extends BaseFragment {
    private RecyclerView rvFragmentHomeAll;
    private RecyclerCommonAdapter<YongJin> myAdapter;
    private List<YongJin> mBeanList = new ArrayList<>();
    private com.dengzq.simplerefreshlayout.SimpleRefreshLayout simpleRefresh;
    private TextView titleCase;

    @Override
    protected int getInflateViewId() {
        return R.layout.fragment_fenxiao_account;
    }

    @Override
    public void initView() {

        rvFragmentHomeAll = (RecyclerView) findViewById(R.id.rv_fragment_home_all);
        simpleRefresh = (SimpleRefreshLayout) findViewById(R.id.simple_refresh);
        titleCase = (TextView) findViewById(R.id.title_case);
    }

    @Override
    public void initData() {
        simpleRefresh.setScrollEnable(true);
        simpleRefresh.setPullUpEnable(true);
        simpleRefresh.setPullDownEnable(true);
        simpleRefresh.setHeaderView(new SimpleRefreshView(mActivity));
        simpleRefresh.setFooterView(new SimpleLoadView(mActivity));
        simpleRefresh.setBottomView(new SimpleBottomView(mActivity));
        getOrderList();
    }
    private int page=0;
    private int limit=20;
    private boolean stoploadmore=false;
    private void getOrderList() {
        Map<String, String> map = new HashMap<>();
        map.put("type", getArguments().getString("type"));
        map.put("first", page + "");
        map.put("limit", limit+"");
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).getYongJinList(SPUtil.get("head", "").toString(),RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("msg");
                    if (code == 200) {
                        JSONObject data = res.getJSONObject("data");
                        JSONArray jsonArray = data.getJSONArray("list");
                        GsonBuilder builder = new GsonBuilder();
                        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                                return new Date(json.getAsJsonPrimitive().getAsLong());
                            }
                        });
                        Gson gson = builder.create();
                        Type yongjintype = new TypeToken<List<YongJin>>() {
                        }.getType();

                        String yonglists=jsonArray.toString();

                        List<YongJin> tmp=gson.fromJson(yonglists, yongjintype);
                        if(tmp==null||tmp.size()==0){
                            stoploadmore=true;
                        }
                        mBeanList.addAll(tmp);

                        titleCase.setText("累计收入"+data.optString("arrival_account").replace("null","0")+"元，未到账"+data.optString("outstanding_account").replace("null","0")+"元");
                    }
                    showRecycleView();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initEvent() {
        simpleRefresh.setOnSimpleRefreshListener(new SimpleRefreshLayout.OnSimpleRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        simpleRefresh.onRefreshComplete();
                        simpleRefresh.onLoadMoreComplete();
                    }
                },500);
                mBeanList.clear();
                page=0;
                getOrderList();
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        simpleRefresh.onRefreshComplete();
                        simpleRefresh.onLoadMoreComplete();
                    }
                },500);
                if(stoploadmore){

                    Toast.makeText(mActivity,"没有更多",Toast.LENGTH_SHORT).show();
                }else{
                    page=page+limit;
                    getOrderList();
                }

            }
        });
    }

    private void showRecycleView() {
        if (myAdapter == null) {

            myAdapter = new RecyclerCommonAdapter<YongJin>(mActivity, R.layout.item_fenxiaoaccount, mBeanList) {
                @Override
                protected void convert(ViewHolder holder, final YongJin carChoiceBean, int position) {
                    holder.setText(R.id.content,""+carChoiceBean.mark);
                    holder.setText(R.id.time,carChoiceBean.add_time);
                    holder.setText(R.id.money,("1".equals(carChoiceBean.pm)?"+":"-")+carChoiceBean.number+"元");
                    holder.setTextColor(R.id.money,"1".equals(carChoiceBean.pm)? Color.parseColor("#1e8f00") :Color.parseColor("#E9391C"));
                    holder.setText(R.id.status,("1".equals(carChoiceBean.status)?"已到账":"未到账"));
                    holder.setTextColor(R.id.status,"1".equals(carChoiceBean.status)? Color.parseColor("#1e8f00") :Color.parseColor("#E9391C"));
                }

            };

//            rvFragmentHomeAll.addItemDecoration(RecyclerViewDivider.with(getActivity()).color(Color.parseColor("#909090")).build());
            rvFragmentHomeAll.setAdapter(myAdapter);
            rvFragmentHomeAll.setLayoutManager(new LinearLayoutManager(mActivity));
        } else {
            myAdapter.notifyDataSetChanged();
        }
    }
}
