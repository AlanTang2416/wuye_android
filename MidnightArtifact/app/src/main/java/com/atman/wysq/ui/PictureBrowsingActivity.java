package com.atman.wysq.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.widget.PictureBrowsViewPager;
import com.base.baselibs.iimp.TimeCountInterface;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.TimeCount;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/22 11:46
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class PictureBrowsingActivity extends MyBaseActivity implements TimeCountInterface {

    @Bind(R.id.view_pager)
    PictureBrowsViewPager viewPager;
    @Bind(R.id.showpic_bar_title_tx)
    TextView showpicBarTitleTx;
    @Bind(R.id.showpic_bar_title_rl)
    RelativeLayout showpicBarTitleRl;
    @Bind(R.id.showpic_root_bar_rl)
    RelativeLayout showpicRootBarRl;

    private Context mContext = PictureBrowsingActivity.this;

    private String[] mImageUrl = null;
    private List<String> imagePath = null;
    private int num;
    private TimeCount timeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disableLoginCheck();
        setContentView(R.layout.activity_picturebrowsing);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        hideTitleBar();

        imagePath = new ArrayList<String>();
        mImageUrl = getIntent().getStringExtra("image").split(",");
        num = getIntent().getIntExtra("num", 0);
        for (int i = 0, j = mImageUrl.length; i < j; i++) {
            System.out.println(">>>>>>mImageUrl[i]:" + mImageUrl[i]);
            imagePath.add(mImageUrl[i]);
        }
        showpicBarTitleTx.setText((num + 1) + "/" + imagePath.size());

        viewPager.setAdapter(new SamplePagerAdapter());
        viewPager.setCurrentItem(num);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                showpicBarTitleTx.setText((position + 1) + "/" + imagePath.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                showpicRootBarRl.setVisibility(View.GONE);
            }
        });

//        timeCount = new TimeCount(5 * 1000, 1000, this);
//        timeCount.start();
    }

    @Override
    public void onTimeOut() {
        showpicRootBarRl.setVisibility(View.GONE);
    }

    @Override
    public void onBackTick(long millisUntilFinished) {

    }

    class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imagePath.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, final int position) {
            final PhotoView photoView = new PhotoView(container.getContext());
            photoView.post(new Runnable(){
                @Override
                public void run(){
                    ImageLoader.getInstance().displayImage(imagePath.get(position), photoView, MyBaseApplication.getApplication().getOptionsNot());
                }
            });

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeCount!=null) {
            timeCount.cancel();
        }
    }

    @OnClick({R.id.showpic_bar_back_iv, R.id.showpic_bar_back_ll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.showpic_bar_back_iv:
            case R.id.showpic_bar_back_ll:
                finish();
                break;
        }
    }
}
