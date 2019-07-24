package com.jiudi.wine.ui.main;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.jiudi.wine.R;
import com.jiudi.wine.base.BaseActivity;
import com.jiudi.wine.base.BaseFragment;
import com.jiudi.wine.bean.Fictitious;
import com.jiudi.wine.bean.RecommendTabBean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 主页
 */
public class HomeMMActivity extends BaseActivity {


    @Override
    protected int getContentViewId() {
        return R.layout.activity_homemm;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

        getSupportFragmentManager().beginTransaction().replace(R.id.allmiao,new HomeMMFragment()).commitAllowingStateLoss();
    }

    @Override
    public void initEvent() {

    }
}
