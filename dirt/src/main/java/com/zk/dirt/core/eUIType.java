package com.zk.dirt.core;

// https://procomponents.ant.design/components/schema#valuetype
public enum eUIType {
    none,
    auto,
    //密码输入框
    password,
    //金额输入框
    money,
    //文本域
    textarea,
    //日期
    date,
    //日期时间
    dateTime,
    //周
    dateWeek,
    //月
    dateMonth,
    //季度输入
    dateQuarter,
    //年份输入
    dateYear,
    //日期区间
    dateRange,
    //日期时间区间
    dateTimeRange,
    //时间
    time,
    //时间区间
    timeRange,
    //文本框
    text,
    //富文本
    richtext,
    //下拉框
    select,
    //树形下拉框
    treeSelect,
    //可下拉，可输入
    selectInput,
    selectInputMultipal,
    selectLiveInput,
    entitySelect,
    // 多选框
    checkbox,
    //星级组件
    rate,
    //单选框
    radio,
    //按钮单选框
    radioButton,
    //进度条
    progress,
    //百分比组件
    percent,
    //数字输入框
    digit,
    // 经纬度
    longlat,
    //秒格式化
    second,
    //头像
    avatar,
    //代码框
    code,
    //开关 switch, switch 是关键字
    switching,
    //相对于当前时间
    fromNow,
    //图片
    image,

    // 图片上传
    imageUploader,

    // 地图
    map,

    //代码框，但是带了 json 格式化
    jsonCode,
    //颜色选择器
    color,
    //级联选择器
    cascader, group, formList, slide, option;

    @Override
    public String toString() {
        // switch 是关键字， 但 antd 里定义的开关就是 switch ，转换一下。
        String string = super.toString();
        if(string.equals("switching"))return "switch";
        if(string.equals("none"))return null;
        return string;
    }


    public static void main(String[] args) {
        System.out.println(eUIType.switching);
    }


}