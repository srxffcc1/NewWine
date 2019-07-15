package com.jiudi.wine.ui.user.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aykj.mustinsert.MustInsert;
import com.jiudi.wine.R;
import com.jiudi.wine.base.BaseActivity;
import com.jiudi.wine.bean.UserBean;
import com.jiudi.wine.event.WechatLoginEvent;
import com.jiudi.wine.global.LocalApplication;
import com.jiudi.wine.manager.AccountManager;
import com.jiudi.wine.manager.RequestManager;
import com.jiudi.wine.net.RetrofitCallBack;
import com.jiudi.wine.net.RetrofitRequestInterface;
import com.jiudi.wine.ui.main.MainNewActivity;
import com.jiudi.wine.util.CommonUtil;
import com.jiudi.wine.util.DialogUtil;
import com.jiudi.wine.util.MD5Util;
import com.jiudi.wine.util.NetworkUtil;
import com.jiudi.wine.util.SPUtil;
import com.jiudi.wine.util.ToastUtil;
import com.jiudi.wine.util.WechatUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Steven on 2017/12/21.
 */
public class LoginActivity extends BaseActivity {


    private ImageView back;
    private TextView register;
    private LinearLayout phoneL;
    private EditText phone;
    private ImageView pwdI;
    private LinearLayout yanzhengmaL;
    private EditText yanzhengma;
    private TextView yanzhengmaT;
    private TextView pwdT;
    private TextView login;
    private ImageView weixinlogin;
    private EditText pwd;
    private LinearLayout pwdL;
    private int loginflag = 2;//1-》手机密码 2-》手机验证码 3-》第三方
    private boolean mShowPwd = false;
    private String wx_code = "";
    private CountDownTimer mTimer;
    private View weixinloginb;
    private LinearLayout normalLogin;
    private LinearLayout tuijianlogin;
    private LinearLayout weixinloginc;
    private TextView changepass;
    private TextView passfuwu;
    private TextView passxieyi;


    @Override
    public boolean isNoNeedLogin() {
        return true;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }


    @Override
    public void initView() {

        back = (ImageView) findViewById(R.id.back);
        register = (TextView) findViewById(R.id.register);
        phoneL = (LinearLayout) findViewById(R.id.phone_l);
        phone = (EditText) findViewById(R.id.phone);
        pwdI = (ImageView) findViewById(R.id.pwd_i);
        yanzhengmaL = (LinearLayout) findViewById(R.id.yanzhengma_l);
        yanzhengma = (EditText) findViewById(R.id.yanzhengma);
        yanzhengmaT = (TextView) findViewById(R.id.yanzhengma_t);
        pwdT = (TextView) findViewById(R.id.pwd_t);
        login = (TextView) findViewById(R.id.login);
        weixinlogin = (ImageView) findViewById(R.id.weixinlogin);
        pwd = (EditText) findViewById(R.id.pwd);
        pwdL = (LinearLayout) findViewById(R.id.pwd_l);
        weixinloginb = (View) findViewById(R.id.weixinloginb);


        normalLogin = (LinearLayout) findViewById(R.id.normal_login);
        tuijianlogin = (LinearLayout) findViewById(R.id.tuijianlogin);
        weixinloginc = (LinearLayout) findViewById(R.id.weixinloginc);
        changepass = (TextView) findViewById(R.id.changepass);
        passfuwu = (TextView) findViewById(R.id.passfuwu);
        passxieyi = (TextView) findViewById(R.id.passxieyi);
    }

    @Override
    public void initData() {
        mTimer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                yanzhengmaT.setClickable(false);
                yanzhengmaT.setText(millisUntilFinished / 1000 + " S");
            }

            @Override
            public void onFinish() {
                yanzhengmaT.setClickable(true);
                yanzhengmaT.setText(R.string.get_verify_code);
            }
        };
        buildView();
    }

    public void buildView() {
        if (loginflag == 1) {
            pwdL.setVisibility(View.VISIBLE);
            yanzhengmaL.setVisibility(View.GONE);
            pwdT.setText("使用手机验证码登录");
        }
        if (loginflag == 2) {
            pwdL.setVisibility(View.GONE);
            yanzhengmaL.setVisibility(View.VISIBLE);
            pwdT.setText("使用密码登录");

        }
        if (loginflag == 3) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initEvent() {
        passxieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, TextActivity.class).putExtra("url", RequestManager.mBaseUrl + "api/Article/visit/id/16"));
            }
        });
        passfuwu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mActivity, TextActivity.class).putExtra("url", RequestManager.mBaseUrl + "api/Article/visit/id/17"));

            }
        });
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tuijianlogin.setVisibility(View.GONE);
                normalLogin.setVisibility(View.VISIBLE);
            }
        });
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(mActivity, RegisterActivity.class));
//            }
//        });
        pwdT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginflag == 1) {
                    loginflag = 2;
                } else {
                    loginflag = 1;
                }
                buildView();
            }
        });
        pwdI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShowPwd) {
                    pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    pwdI.setImageResource(R.drawable.eye_close_white);
                } else {
                    pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    pwdI.setImageResource(R.drawable.eye_open_white);
                }
                mShowPwd = !mShowPwd;
                pwd.setSelection(pwd.getText().toString().length());
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginflag == 1) {
                    if (MustInsert.checkAllText(mActivity, phone, pwd)) {

                        login();
                    }
                }
                if (loginflag == 2) {
                    if (MustInsert.checkAllText(mActivity, phone, yanzhengma)) {

                        login();
                    }
                }

            }
        });
        weixinloginc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginflag = 3;
                WechatUtil.wechatLogin(LocalApplication.mIWXApi);
            }
        });
        weixinloginb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginflag = 3;
                WechatUtil.wechatLogin(LocalApplication.mIWXApi);
            }
        });
        weixinlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginflag = 3;
                WechatUtil.wechatLogin(LocalApplication.mIWXApi);
            }
        });
        yanzhengmaT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phones = phone.getText().toString().trim();
                if (TextUtils.isEmpty(phones)) {
                    ToastUtil.showShort(mContext, getString(R.string.please_input_phone_num));
                } else if (!CommonUtil.isPhone(phones)) {
                    ToastUtil.showShort(mContext, getString(R.string.please_input_right_phone_nun));
                } else {
                    mTimer.start();
                    getCode(phone.getText().toString().trim());
                }
            }
        });
    }

    private void getCode(final String phone) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("type", "login");
        RequestManager.mRetrofitManager
                .createRequest(RetrofitRequestInterface.class)
                .getCode(RequestManager.encryptParams(map))
                .enqueue(new RetrofitCallBack() {
                    @Override
                    public void onSuccess(String response) {
                        DialogUtil.hideProgress();
                        try {
                            JSONObject res = new JSONObject(response);
                            int code = res.getInt("code");
                            String info = res.getString("data");

                            String msg = res.get("msg").toString();

                            if (code == 200) {
                                ToastUtil.showShort(mContext, info);

                            } else {
                                ToastUtil.showShort(mContext, msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        DialogUtil.hideProgress();
                        if (!NetworkUtil.isConnected()) {
                            ToastUtil.showShort(mActivity, getString(R.string.net_error));
                        } else {
                            ToastUtil.showShort(mActivity, getString(R.string.network_error));
                        }
                    }
                });
    }

    private void login() {
        Map<String, String> map = new HashMap<>();
        if (loginflag == 1) {
            map.put("account", phone.getText().toString());
            map.put("passwd", MD5Util.getMD5Str(pwd.getText().toString()));
        }
        if (loginflag == 2) {
            map.put("tel", phone.getText().toString());
            map.put("code", yanzhengma.getText().toString());
        }
        if (loginflag == 3) {

            map.put("wx_code", wx_code);
        }
        map.put("flag", loginflag + "");
        RequestManager.mRetrofitManager.createRequest(RetrofitRequestInterface.class).login(RequestManager.encryptParams(map)).enqueue(new RetrofitCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject res = new JSONObject(response);
                    int code = res.getInt("code");
                    String info = res.getString("msg");
                    if (code == 200) {
                        JSONObject data = res.getJSONObject("data");
                        AccountManager.sUserBean = new UserBean();
                        AccountManager.sUserBean.head = data.getString("token");
                        AccountManager.sUserBean.phone = phone.getText().toString();
                        AccountManager.sUserBean.passwd = pwd.getText().toString();
                        AccountManager.sUserBean.needshowdialog = false;
                        System.out.println(data);
                        int first = data.optInt("first");
                        String token = data.getString("token");
                        System.out.println("临时Token：" + token);
                        if (first == 1) {
                            ToastUtil.showShort(mContext, "需要完善信息");
                            SPUtil.put("head2", token);
                            AccountManager.sUserBean.needshowdialog = true;
                            startActivityForResult(new Intent(mActivity, RegisterActivity.class).putExtra("type", loginflag), 100);
                        } else {
                            ToastUtil.showShort(mContext, "登录成功");
                            SPUtil.put("head", token);
                            startActivity(new Intent(mActivity, MainNewActivity.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(mActivity, info, Toast.LENGTH_SHORT).show();
                        if (info.contains("不存在")) {
                            AccountManager.sUserBean = new UserBean();
                            AccountManager.sUserBean.phone = phone.getText().toString();
                            AccountManager.sUserBean.passwd = pwd.getText().toString();
                            startActivity(new Intent(mActivity, RegisterActivity.class).putExtra("type", loginflag));
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWechatLoginEvent(WechatLoginEvent wechatPayEvent) {
        wx_code = wechatPayEvent.getResult();
        if (wx_code == null) {
            loginflag = 2;
            buildView();
        } else {
            login();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        SPUtil.put("head", "");
//        if(AccountManager.sUserBean!=null){
//            AccountManager.sUserBean.head=null;
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                Intent intent = new Intent(mActivity, MainNewActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(mActivity, MainNewActivity.class);
                startActivity(intent);
                finish();
            }
        }

    }
}
