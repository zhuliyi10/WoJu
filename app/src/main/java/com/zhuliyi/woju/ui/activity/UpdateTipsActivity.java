package com.zhuliyi.woju.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhuliyi.woju.R;
import com.zhuliyi.woju.base.SwipeBackActivity;
import com.zhuliyi.woju.parser.UpdateTipsParser;
import com.zhuliyi.woju.widget.list.TurnCardListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 更新记录activity
 * Created by zhuliyi on 2017/5/16.
 */

public class UpdateTipsActivity extends SwipeBackActivity {
    @BindView(R.id.card_list)
    TurnCardListView cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tips);
        ButterKnife.bind(this);
        textTitle.setText("更新记录");
        cardList.setOnTurnListener(new TurnCardListView.OnTurnListener() {
            @Override
            public void onTurned(int position) {
//                ToastUtil.showShort( "position = " + position);
            }
        });

        final int[] colors = {0xffFF9800, 0xff3F51B5, 0xff673AB7, 0xff006064, 0xffC51162, 0xffFFEB3B, 0xff795548, 0xff9E9E9E};
        final List<UpdateTipsParser>data=new ArrayList<>();
        UpdateTipsParser item=new UpdateTipsParser();
        item.versionNum="1.0.0_2";
        item.updateTime="2017-6-18";
        item.updateContent="1、实现个人信息里的头像、蜗信号昵称号、个性签名、二维码名片、性别、生日、身份认证的修改；\n\n2、实现设置页面修改密码、手机号、绑定解绑qq和微博；\n\n3、设置版本更新的记录页面和蜗信介绍";
        data.add(item);
        item=new UpdateTipsParser();
        item.versionNum="1.0.0_1";
        item.updateTime="2017-5-30";
        item.updateContent="1、简单实现首页四个页面；\n\n2、实现简单的登陆逻辑；\n\n3、实现第三方登陆如qq和微博登陆\n";
        data.add(item);
        cardList.setAdapter(new BaseAdapter() {


            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View child, ViewGroup parent) {
                if (child == null) {
                    child = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_update_tips, parent, false);
                }
                UpdateTipsParser item=data.get(position);
                ((TextView) child.findViewById(R.id.text_version)).setText(item.versionNum);
                ((TextView) child.findViewById(R.id.text_content)).setText(item.updateContent);
                ((TextView) child.findViewById(R.id.text_time)).setText(item.updateTime);
                child.findViewById(R.id.image).setBackgroundColor(colors[position%colors.length]);
                return child;
            }
        });
    }
}
