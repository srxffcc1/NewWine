package com.jiudi.wine.ui.fenxiao;

import android.content.Intent;
import android.widget.ImageView;

import com.jiudi.wine.R;
import com.jiudi.wine.base.BaseActivity;

public class FenXiaoNoActivity extends BaseActivity {


    private ImageView passlibao;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_fenxiaono;
    }

    @Override
    public void initView() {

        passlibao = (ImageView) findViewById(R.id.passlibao);
    }

    @Override
    public void initData() {
        startActivity(new Intent(mActivity, FenXiaoNo2Activity.class).putExtra("id","4"));
        finish();
    }

    @Override
    public void initEvent() {
//        passlibao.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(mActivity, CartDetailActivity.class).putExtra("id","4"));
//            }
//        });
    }
}
