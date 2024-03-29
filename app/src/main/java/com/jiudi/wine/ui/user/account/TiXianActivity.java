package com.jiudi.wine.ui.user.account;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jiudi.wine.R;
import com.jiudi.wine.base.BaseActivity;
import com.jiudi.wine.bean.TiXian;
import com.jiudi.wine.manager.AccountManager;
import com.jiudi.wine.manager.RequestManager;
import com.jiudi.wine.net.RetrofitCallBack;
import com.jiudi.wine.net.RetrofitRequestInterface;
import com.jiudi.wine.util.SPUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TiXianActivity extends BaseActivity {
    private android.widget.EditText name;
    private android.widget.EditText account;
    private android.widget.EditText money;
    private android.widget.TextView nowmoney;
    private android.widget.TextView passtixian;
    private TiXian tiXian;
    private int minExtractPrice;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_tixian;
    }

    @Override
    public void initView() {

        name = (EditText) findViewById(R.id.name);
        account = (EditText) findViewById(R.id.account);
        money = (EditText) findViewById(R.id.money);
        nowmoney = (TextView) findViewById(R.id.nowmoney);
        passtixian = (TextView) findViewById(R.id.passtixian);
    }

    @Override
    public void initData() {
        gettixian();
    }

    @Override
    public void initEvent() {
        passtixian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(Integer.parseInt(money.getText().toString())<minExtractPrice){
                        Toast.makeText(mActivity,"提现金额不得低于"+minExtractPrice,Toast.LENGTH_SHORT).show();
                    }else {
                        tixian();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(mActivity,"提现金额不得低于"+minExtractPrice,Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }
    private void gettixian() {
        final Map<String, String> map = new HashMap<>();
//        map.put("customer_id", AccountManager.sUserBean.getId());
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).gettixian(SPUtil.get("head", "").toString(),RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
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
                        Type orderDetailType = new TypeToken<TiXian>() {
                        }.getType();
                        tiXian=gson.fromJson(res.getJSONObject("data").getJSONObject("extractInfo").toString(),orderDetailType);
                        minExtractPrice=res.getJSONObject("data").getInt("minExtractPrice");
                        if(tiXian!=null){
                            name.setText(tiXian.real_name);
                            account.setText(tiXian.alipay_code);
                            nowmoney.setText("余额:¥"+(AccountManager.sUserBean.now_money+"").replace("null","0"));
                            money.setHint("请输入提现金额-"+"不得低于"+minExtractPrice+"元");

                        }

                    }else{
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
    private void tixian() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "alipay");
        map.put("real_name", name.getText().toString());
        map.put("alipay_code", account.getText().toString());
        map.put("price", money.getText().toString());
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).tixian(SPUtil.get("head", "").toString(),RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("msg");
                    if (code == 200) {
                        Toast.makeText(mActivity,info,Toast.LENGTH_SHORT).show();
                        gettixian();
                    }else{
                        Toast.makeText(mActivity,info,Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {
                Toast.makeText(mActivity,"后台出错",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
