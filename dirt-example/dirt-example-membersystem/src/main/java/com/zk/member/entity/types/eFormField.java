package com.zk.member.entity.types;

import com.zk.dirt.intef.iListable;

public enum eFormField implements iListable {

    auto("auto"),
    //密码输入框
    password("password"),
    //金额输入框
    money("money"),
    //文本域
    textarea("textarea"),
    //日期
    date("date"),
    //日期时间
    dateTime("dateTime"),
    //周
    dateWeek("dateWeek"),
    //月
    dateMonth("DateMonth"),
    //季度输入
    dateQuarter("dateQuarter"),
    //年份输入
    dateYear("dateYear"),
    //日期区间
    dateRange("dateRange"),
    //日期时间区间
    dateTimeRange("dateTimeRange"),
    //时间
    time("time"),
    //时间区间
    timeRange("timeRange"),
    //文本框
    text("text"),
    //下拉框
    select("select"),
    //树形下拉框
    treeSelect("treeSelect"),
    // 多选框
    checkbox("checkbox"),
    //星级组件
    rate("rate"),
    //单选框
    radio("radio"),
    //按钮单选框
    radioButton("radioButton"),
    //进度条
    progress("progress"),
    //百分比组件
    percent("percent"),
    //数字输入框
    digit("digit"),
    //秒格式化
    second("second"),
    //头像
    avatar("avatar"),
    //代码框
    code("code"),
    //开关 switch, switch 是关键字
    switching("switch"),
    //相对于当前时间
    fromNow("fromNow"),
    //图片
    image("image"),
    //代码框，但是带了 json 格式化
    jsonCode("jsonCode"),
    //颜色选择器
    color("code"),
    //级联选择器
    cascader("cascader");

    @Override
    public String toString() {
        // switch 是关键字， 但 antd 里定义的开关就是 switch ，转换一下。
        String string = super.toString();
        if(string.equals("switching"))return "switch";

        return string;
    }
    private  String string;


   eFormField(String string) {
      this.string = string;
   }

   @Override
   public String getText() {
      return string;
   }
}