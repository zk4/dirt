package com.zk.dirt.annotation;

import com.zk.dirt.core.eDirtEntityRelation;
import com.zk.dirt.core.eUIType;
import com.zk.dirt.entity.iID;
import com.zk.dirt.experiment.eSubmitWidth;
import com.zk.dirt.intef.iDataSource;
import com.zk.dirt.intef.iEnumText;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD,PARAMETER})
@Retention(RUNTIME)
public @interface DirtField {
    // 表头名
    String title() default  "";
    // 表头宽度, -1: auto
    eSubmitWidth width() default eSubmitWidth.MD;

    // subTree　节点的　名字, 这个一般配合 cascade uitype  使用.
    String subTreeName() default  "";


    // 不是所有字段都需要改 meta。
    // 由开发者主动标识哪些完全不可改。
    boolean metable() default  true;

    // 表头排序
    int index() default 0;

    enum eFixedType implements  iEnumText<String> {
        NONE(""),
        LEFT("left"),
        RIGHT("right");

        eFixedType(String txt) {
            this.txt = txt;
        }

        String txt;
        @Override
        public String getText() {
            return txt;
        }
    }
    // 表头固定在左侧，相当于 excel 里的冻结
    eFixedType fixed() default  eFixedType.NONE;

    // 超出是否省略
    boolean ellipsis() default  true;

    // 是否可复制
    boolean copyable() default  false;

    // 排序函数，本地排序使用一个函数(参考 Array.sort 的 compareFunction)，需要服务端排序可设为 true
    boolean sorter() default  true;

    // 以下两者任选一，不要同时选，
    // 基于类做下拉数据源
    //Class<? extends iEnumProvider>[] enumProvider() default {};
    // 基于 enum 做数据源
    //Class<? extends iEnumText>[] enumListableType() default {};

    // 搜索表单相关
    DirtDepends[] depends() default {};

    // UI组件的源数据
    DirtHQLSource[] sourceProvider() default {};

    Class<? extends iDataSource>[] dataSource() default  {};

    boolean search() default  true;
    // 筛选表单，为 true 时使用 ProTable 自带的，为 false 时关闭本地筛选
    boolean onFilter() default  true;

    // 表头的筛选菜单项，当值为 true 时，自动使用 valueEnum 生成
    boolean filters() default  true;

    // 界面的建议解析组件名
    eUIType uiType() default eUIType.auto;

    // 是否在表头里隐藏
    boolean hideInTable() default false;

    // 提交表单相关，默认提交
    DirtSubmit[] dirtSubmit() default {@DirtSubmit};

    // 搜索表单相关
    DirtSearch[] dirtSearch() default {};

    // 实体关系，会自动从 JPA 注解推测，之所以还存在于这，是为了日后兼容 mybatis
    eDirtEntityRelation relation() default  eDirtEntityRelation.None;

    //	string	会在 title 旁边展示一个 icon，鼠标浮动之后展示
    String tooltip() default  "";

    // 为兼容 mybatis，如果只有 id ，说明 id 所关系的实体
    Class<? extends iID>[] idOfEntity() default {};

    // @Dprecated，使用 provider 类，
    // 原因： 1 注解的返回值限制较多，2. 有可能返回数组，3 要与下拉匹配
    int initialValue() default 0;
}