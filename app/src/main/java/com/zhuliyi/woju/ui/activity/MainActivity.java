package com.zhuliyi.woju.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.BaseActivity;
import com.zhuliyi.woju.common.CommonOnPageChangeListener;
import com.zhuliyi.woju.common.FragmentPagerAdapter;
import com.zhuliyi.woju.ui.fragment.CircleFragment;
import com.zhuliyi.woju.ui.fragment.FindFragment;
import com.zhuliyi.woju.ui.fragment.MineFragment;
import com.zhuliyi.woju.ui.fragment.NewsFragment;
import com.zhuliyi.woju.utils.ActivityManagerUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    public static final String TAG=MainActivity.class.getSimpleName();
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private LinearLayout[] ll_tab;
    private ImageView[] ivn_tab, iva_tab;
    private TextView[] tv_tab;


    private Long firstTime = 0L;
    private ArrayList<Fragment> fragmentList;//fragment列表
    private FragmentPagerAdapter pagerAdapter;
    private int currentPage=-1;
    private int red1 = 128, green1 = 128, blue1 = 128;//字体的颜色变化
    private int red2 = 36, green2 = 170, blue2 = 254;
    private int redDif = red2 - red1, greenDif = green2 - green1, blueDif = blue2 - blue1;
    private String[] mainTitles={"消息","圈子","发现","我的"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setTranslucentStatus(true);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        setListener();
        Log.d(MainActivity.class.getSimpleName(), "MainActivity ");
    }

    private void initView() {
        initToolbar();
        ll_tab = new LinearLayout[]{
                (LinearLayout) findViewById(R.id.linear1),
                (LinearLayout) findViewById(R.id.linear2),
                (LinearLayout) findViewById(R.id.linear3),
                (LinearLayout) findViewById(R.id.linear4)};
        iva_tab = new ImageView[]{
                (ImageView) findViewById(R.id.image_1a),
                (ImageView) findViewById(R.id.image_2a),
                (ImageView) findViewById(R.id.image_3a),
                (ImageView) findViewById(R.id.image_4a)};
        ivn_tab = new ImageView[]{
                (ImageView) findViewById(R.id.image_1n),
                (ImageView) findViewById(R.id.image_2n),
                (ImageView) findViewById(R.id.image_3n),
                (ImageView) findViewById(R.id.image_4n)};
        tv_tab = new TextView[]{
                (TextView) findViewById(R.id.text1),
                (TextView) findViewById(R.id.text2),
                (TextView) findViewById(R.id.text3),
                (TextView) findViewById(R.id.text4)};
        fragmentList = new ArrayList<>();
        fragmentList.add(new NewsFragment());
        fragmentList.add(new CircleFragment());
        fragmentList.add(new FindFragment());
        fragmentList.add(new MineFragment());
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(fragmentList.size());
        initTabFontColor(R.color.colorPrimary,R.color.text_low);
        setPagerPos(0);
    }

    private void initToolbar(){
        setToolbarVisible(true);
        toolbar.inflateMenu(R.menu.main_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.contact:
                        break;
                    case R.id.plus:
                        break;
                }
                return true;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    private void setListener(){
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        for (int i = 0; i < ll_tab.length; i++) {
            ll_tab[i].setOnClickListener(tabListener);
            ll_tab[i].setTag(i);
        }
    }
    private void setPagerPos(int pos){
        if(pos==currentPage)return;
        if(pos==0){
            toolbar.setNavigationIcon(R.drawable.house);
        }else {
            toolbar.setNavigationIcon(null);
        }
        for (int i = 0; i < fragmentList.size(); i++) {
            if (i == pos) {
                iva_tab[i].setAlpha(1f);
                ivn_tab[i].setAlpha(0f);
                tv_tab[i].setTextColor(Color.rgb(red2, green2, blue2));
            } else {
                iva_tab[i].setAlpha(0f);
                ivn_tab[i].setAlpha(1f);
                tv_tab[i].setTextColor(Color.rgb(red1, green1, blue1));
            }
        }
        setTitleName(mainTitles[pos]);
        currentPage = pos;
        viewPager.setCurrentItem(currentPage, false);
    }

    /**
     * 初始化tab字体颜色
     */
    private void initTabFontColor(int colorId,int grayId) {
        int deactivation = getResources().getColor(grayId);
        int activation = getResources().getColor(colorId);
        red1 = Color.red(deactivation);
        green1 = Color.green(deactivation);
        blue1 = Color.blue(deactivation);
        red2 = Color.red(activation);
        green2 = Color.green(activation);
        blue2 = Color.blue(activation);
        redDif = red2 - red1;
        greenDif = green2 - green1;
        blueDif = blue2 - blue1;
    }
    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 1500) {
            Toast.makeText(context, "再按一次退出", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            ActivityManagerUtils.getInstance().exit();
        }
    }

    /**
     * 菜单栏的点击事件
     */
    View.OnClickListener tabListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setPagerPos(Integer.valueOf(v.getTag().toString()));
        }
    };
    private class MyOnPageChangeListener extends CommonOnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //改变字体的颜色
            tv_tab[position].setTextColor(Color.rgb(
                    (int) (red2 - positionOffset * redDif),
                    (int) (green2 - positionOffset * greenDif),
                    (int) (blue2 - positionOffset * blueDif)));
            if (position != fragmentList.size() - 1) {
                tv_tab[position + 1].setTextColor(Color.rgb(
                        (int) (red1 + positionOffset * redDif),
                        (int) (green1 + positionOffset * greenDif),
                        (int) (blue1 + positionOffset * blueDif)));
            }

            //改变图片的透明度
            ivn_tab[position].setAlpha(positionOffset);
            iva_tab[position].setAlpha(1 - positionOffset);
            if (position != fragmentList.size() - 1) {
                iva_tab[position + 1].setAlpha(positionOffset);
                ivn_tab[position + 1].setAlpha(1 - positionOffset);
            }
        }

        @Override
        public void onPageSelected(int position) {
            setPagerPos(position);
        }
    }


}
