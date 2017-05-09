package com.zhulilyi.woju.parser;

import com.zhulilyi.woju.base.BaseParser;

import java.util.List;

/**
 * 圈子列表
 * Created by zhuliyi on 2017/4/25.
 */

public class CircleParser extends BaseParser{

    public List<CircleListParser> circleList;

    public class CircleListParser{
        public String headUrl;
        public String title;
        public String time;
        public String content;
        public List<CirclePicParser>imgList;
    }
    public class CirclePicParser{
        public String imgUrl;
    }
}
