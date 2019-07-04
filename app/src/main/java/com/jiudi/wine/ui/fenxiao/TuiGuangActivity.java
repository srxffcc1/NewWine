package com.jiudi.wine.ui.fenxiao;

import android.widget.ImageView;

import com.jiudi.wine.R;
import com.jiudi.wine.base.BaseActivity;

public class TuiGuangActivity extends BaseActivity {
    private android.widget.ImageView zxingImage;
    private android.widget.ImageView yingyin;
    public String erwei;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_erweima_fenxiang;
    }

    @Override
    public void initView() {

        zxingImage = (ImageView) findViewById(R.id.zxing_image);
        yingyin = (ImageView) findViewById(R.id.yingyin);
//        erwei="http://mall.jiudicar.com/wap/store/detail/id/4/spuid/"+ AccountManager.sUserBean.uid +".html";
//        Bitmap mBitmap = CodeUtils.createImage(erwei, 800, 800, null);
//        zxingImage.setImageBitmap(mBitmap);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }
}
