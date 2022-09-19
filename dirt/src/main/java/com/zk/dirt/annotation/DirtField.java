package com.zk.dirt.annotation;

import com.zk.dirt.core.eDirtEntityRelation;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.entity.BaseIdEntity2;
import com.zk.dirt.experiment.eSubmitWidth;
import com.zk.dirt.intef.iEnumProvider;
import com.zk.dirt.intef.iListable;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
/*

一个 entity 里的 field 总共有 3 种数据结构需要输出
1. 表展示
    1. 动作
    2. 数据格式化，跳转
2. 搜索
    1. 搜索条件构造
       1. field 的范围
       2. field 部分
       3. field 精确
3. 提交
    1. 输入提示
 */
// https://procomponents.ant.design/
// https://ant.design/components/table-cn
@Target({METHOD, FIELD,PARAMETER})
@Retention(RUNTIME)
public @interface DirtField {
    // 表头名
    String title() default  "";
    // 表头宽度, -1: auto
    eSubmitWidth width() default eSubmitWidth.MD;

    // 表头排序
    int index() default 0;
    // 表头固定在左侧，相当于 excel 里的冻结
    String fixed() default  "";

    boolean ellipsis() default  false;

    boolean copyable() default  false;

    // 排序函数，本地排序使用一个函数(参考 Array.sort 的 compareFunction)，需要服务端排序可设为 true
    boolean sorter() default  true;

    Class<? extends iEnumProvider>[] enumProvider() default {};
    Class<? extends iListable>[] enumListableType() default {};

    DirtHQLSource[] sourceProvider() default {};

    boolean search() default  true;
    // 筛选表单，为 true 时使用 ProTable 自带的，为 false 时关闭本地筛选
    boolean onFilter() default  true;

    // 表头的筛选菜单项，当值为 true 时，自动使用 valueEnum 生成
    boolean filters() default  true;

    eUIType uiType() default eUIType.auto;


    boolean hideInTable() default false;


    // 提交表单相关，默认提交
    DirtSubmit[] dirtSubmit() default {@DirtSubmit};

    // 搜索表单相关
    DirtSearch[] dirtSearch() default {};

    eDirtEntityRelation relation() default  eDirtEntityRelation.None;

    String tooltip() default  "";//	string	会在 title 旁边展示一个 icon，鼠标浮动之后展示


    Class<? extends BaseIdEntity2>[] idOfEntity() default {};
}