package com.jiudi.wine.ui.fenxiao;

import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.jiudi.wine.R;
import com.jiudi.wine.base.BaseActivity;
import com.jiudi.wine.bean.OrderEvent;
import com.jiudi.wine.bean.TabEntity;
import com.jiudi.wine.manager.AccountManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class TuanDuiActivity extends BaseActivity {
    private android.widget.RelativeLayout rlLayoutTopBackBar;
    private android.widget.LinearLayout llLayoutTopBackBarBack;
    private android.widget.TextView tvLayoutTopBackBarTitle;
    private android.widget.TextView tvLayoutTopBackBarEnd;
    private android.widget.TextView tvLayoutBackTopBarOperate;
    private com.flyco.tablayout.CommonTabLayout tl;
    private String[] mTitles = {"我的加盟商", "我的粉丝"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private android.widget.FrameLayout flChange;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_ordergood;
    }

    @Override
    public void initView() {

        rlLayoutTopBackBar = (RelativeLayout) findViewById(R.id.rl_layout_top_back_bar);
        llLayoutTopBackBarBack = (LinearLayout) findViewById(R.id.ll_layout_top_back_bar_back);
        tvLayoutTopBackBarTitle = (TextView) findViewById(R.id.tv_layout_top_back_bar_title);
        tvLayoutTopBackBarEnd = (TextView) findViewById(R.id.tv_layout_top_back_bar_end);
        tvLayoutBackTopBarOperate = (TextView) findViewById(R.id.tv_layout_back_top_bar_operate);
        tl = (CommonTabLayout) findViewById(R.id.tl);
        flChange = (FrameLayout) findViewById(R.id.fl_change);
        tvLayoutTopBackBarTitle.setText("客户管理");
    }

    @Override
    public void initData() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        mFragments.add(new TuanDuiFragment().setArgumentz("type","1"));
        mFragments.add(new TuanDuiFragment().setArgumentz("type","2"));
        tl.setTabData(mTabEntities,this,R.id.fl_change,mFragments);
        tl.setCurrentTab(getIntent().getIntExtra("type",0));

    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().post(new OrderEvent());
    }

    @Override
    public void initEvent() {
        if (AccountManager.sUserBean != null) {

        }else{
            finish();
        }

    }
}