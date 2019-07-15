package com.jiudi.wine.ui.fenxiao;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jiudi.wine.R;
import com.jiudi.wine.base.BaseActivity;
import com.jiudi.wine.manager.AccountManager;
import com.jiudi.wine.util.WechatUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.ByteArrayOutputStream;

public class TuiGuangActivity extends BaseActivity {
    public String erwei;
    private ImageView back;
    private LinearLayout titleBar;
    private ImageView fenxiangim;
    private ImageView needzxing;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_erweima_fenxiang;
    }

    @Override
    public void initView() {

        back = (ImageView) findViewById(R.id.back);
        titleBar = (LinearLayout) findViewById(R.id.title_bar);
        fenxiangim = (ImageView) findViewById(R.id.fenxiangim);
        needzxing = (ImageView) findViewById(R.id.needzxing);

        erwei="http://wine.jiudicar.com/wap/index/index/spuid/"+ AccountManager.sUserBean.uid +"";
        Bitmap mBitmap = CodeUtils.createImage(erwei, 800, 800, null);
        needzxing.setImageBitmap(mBitmap);


    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        fenxiangim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MobclickAgent.onEvent(mActivity,"B_goods_right_top");
                new ShareAction(mActivity).setDisplayList(
                        SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                        .setShareboardclickCallback(new ShareBoardlistener() {
                            @Override
                            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

                                if(share_media==SHARE_MEDIA.WEIXIN){
                                    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
                                    byte[] tumb=bmpToByteArray(bmp,true);
                                    WechatUtil.wechatShare(erwei,"优质商品","汇集各类酒水网上直供平台，提供质优价低的酒水与全面贴心的服务",tumb, SendMessageToWX.Req.WXSceneSession);
                                }
                                if(share_media==SHARE_MEDIA.WEIXIN_CIRCLE){
                                    Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.logo);
                                    byte[] tumb=bmpToByteArray(bmp,true);
                                    WechatUtil.wechatShare(erwei,"优质商品","汇集各类酒水网上直供平台，提供质优价低的酒水与全面贴心的服务",tumb, SendMessageToWX.Req.WXSceneTimeline);
                                }
                            }
                        }).open();


//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("image/*");
//                intent.putExtra(Intent.EXTRA_SUBJECT, "分享商品");
//                intent.putExtra(Intent.EXTRA_TEXT, urlShare);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(Intent.createChooser(intent, "推荐商品"));
            }
        });
    }
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
