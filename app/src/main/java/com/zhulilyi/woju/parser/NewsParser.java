package com.zhulilyi.woju.parser;

import com.zhulilyi.woju.base.BaseParser;

/**
 * Created by zhuliyi on 2017/4/18.
 */

public class NewsParser extends BaseParser{
    public int type;//1聊天功能，2资金变动 ，3租房审核，4活动
    public int subType;
    public String imgUrl;
    public String title;
    public String time;
    public String content;
}
