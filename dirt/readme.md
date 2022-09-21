Dirt 的目标是不丢失服务端数据关系。实现数据可视化。
与 JPA 搭配，不用关心存储。 JPA 抽象了存储， Dirt 就用来抽象 UI。这可以极大提高工程质量。

UI schema 模型大部分用 antd 相关名词做定义。其他 UI 按照输出 schema 映射即可。理论上不限制前端库。

## 设计
1. 业务 (Business) = 数据(Entity) +逻辑(Action)
2. Entity 面向对象，Action 面向事件驱动

### 面向对象

#### Entity

- member field 成员变量
  - java type 
    - -> ui Create/Update，最终的值一定要能映射到 java type
    - Search view，可组合成生成过滤条件
    - -> ui table view column 最终的值一定要能映射到 java type
  - field name
- member methods 成员函数 (@DirtAction)
  - 只传入参数，改变 entity 的数据，并持久化



> 一个最终的 string 类型数据，在前端有可能是一个 input，也可能是一个静态下拉，也可能是一个数据源。



#### Action

Action 应该仅针对 entity 的成员变量做处理。这就带来一个非常明显的好处。面向对象化。
Action 的类型：


### JPA 里关系概念
OneToMany  一对多，不可重复
ManyToOne  多对一，不可重复
OneToOne   一对一，不可重复
ManyToMany 多对多，关系任意，可重复

### 数据库

虽然使用 JPA，但不生成外键，也就是说，可以复用已有 entity 基于 mybatis 做传统开发。关系全在代码层维护。
至于 JPA 里的关系映射。全部当 ManyToMany 处理即可。唯一性业务自行决定即可。

如果想快速存储一对多的 ids，而不加载实体，则需要使用  CascadeType.MERGE，用 CascadeType.ALL 不起作用。 讲真，这是 hibernate 的 bug。
> 具体见： https://stackoverflow.com/questions/13370221/persistentobjectexception-detached-entity-passed-to-persist-thrown-by-jpa-and-h
> Why? By saying "cascade ALL" on the child entity Transaction you require that every DB operation gets propagated to the parent entity Account. If you then do persist(transaction), persist(account) will be invoked as well.
> But only transient (new) entities may be passed to persist (Transaction in this case). The detached (or other non-transient state) ones may not (Account in this case, as it's already in DB).
> Therefore you get the exception "detached entity passed to persist". The Account entity is meant! Not the Transaction you call persist on.

嵌套关系的处理是比较麻烦的。 做了两个方面的约定
1. 要么关联，要么创建。api 是分开的。在 UI 操作上会形成两步


### 编程范式

1. 服务端 entity 对象测试
2. 基于事件驱动，可以非常快的与异步模型整合，且非常方便微服务拆分。



## 服务端 TODO

[ ] 大架构图，描述核心架构

1. [x] 搜索
   1. [x] 过滤 基于 spring-filter 的过滤
   1. [x] 分页
   1. [x] 排序
1. [x] 重写 url DSL 解释器
1. [x] 映射 JSR 303 注解校验到前端
1. JPA 支持
   1. [x] 多对一支持， 可筛选(输出 id)，创建
   1. [x] 多对多支持 (多对一的特例) 可筛选(输出 id)，创建 
   1. [x] 一对一支持   可创建
   1. [x] 一对多支持,  可创建
1. [x] 自定义 action
1. [x] 在一对多，多对多时，界面上增加区分创建与关联
1. [x] 更新与创建关系时，只应该处理 id，不需要传递整个 entity
1. [ ] schema 标准化
1. [ ] 业务事件驱动
1. [x] 支持 JAVA 里其他映射类型，如 Map，Set，List
1. [ ] 虚拟 entity 支持
1. [ ] embeded 支持
1. [ ] 输出表单联动 schema
1. [ ] eFilterOperator 实现
1. [ ] action 动态返回显不显示的方案
1. [ ] 树状结构组织
1. [ ] 完善 search 字段的前端映射
1. [ ] 完善 relation 的映射
1. [ ] 当有 1 对多，多对1 ，1 对1 时，会生成唯一关系，是否要在 UI 层做明确表示
1. [ ] 关系支持查询
1. [ ] 在获取数据时，只获取需要显示的数据， 尤其是关联关系，只返回 id，或者 id list/set

## 前端 T
1. [x] 性能优化，如果 UI 未显示详情，不应该加载
1. [x] 表格批量操作
1. [x] 创建/修改表单自动处理多对一
1. [x] 创建/修改表单自动处理一对多
1. [x] 创建/修改表单自动处理一对一
1. [x] 一对多时，可能是创建，也可能是关联
1. [ ] 表单联动 schema
1. [ ] 多种页面展现形式
1. [ ] UI 无关，支持 mobile 与 pc ，响应式
1. [ ] 梳理自动布局的标准
1. [ ] 当前只支持一个参数名叫 args
1. [ ] 通过 ws，与服务端在同一个状态机里处理事件 

1. [ ] 动态生成 swagger，当前全是动态 api， 无法根据注解生成静态 swagger
1. [ ] 根据 entity validate rule 将校验前置



## 其他产品功能 TODO
1. [ ] excel 导出


## BUG
1. [ ] 可用星期排序有 bug，会多出几条
1. [ ] 在 runtime 别依赖 paramter.getName()，比如  public void call(String name){...} "name" 这个名字很可能被编译器改成 arg0 或其他。 
1. [ ] 逻辑删除有问题， deleteInBatch 不走逻辑删除，先用 deleteAll，性能可优化
1. [ ] 前端颜色选择有问题
1. [ ] 因为将构建 header 的逻辑放到了 DirtEntity初始化时, 一些动态的 enum 在添加后是看不到的。需要给接口
1. [ ] action 提交表单时，丢失了 rules
1. [x] objectMapper 不是 bug 的 bug， https://github.com/FasterXML/jackson-databind/issues/2868
       aInteger 会被当成 ainteger , （一个小写开头的camelcase）， 但是 anInteger 却可以正常解析。之所以这样，是因为要顾及 JavaBeans spec，比如 getUrl/setUrl => property name: url
        

   所以最好的解决办法，就是，别只以一个小写开头写 field property

   

根源跟踪：



```java
 com.fasterxml.jackson.databind.introspect.POJOPropertiesCollector
 protected void collectAll(...){
   ... 
	_addFields(props);  //这一步没问题，构造了部分 props
  _addMethods(props);  ///当 aInteger 时 这一步有问题
   ...
  }
```

aInteger 会解析出两个 props

一个 prop的 property name 是 aInteger， 但没有 getter setter， 

而另一个prop 的  property name  则为 ainteger ，且生成了 getter setter

如下：

```ini
"aInteger" -> "[Property 'aInteger'; ctors: null, field(s): [field com.zk.dirt.util.ArgsUtilTest$CamelCaseData#aInteger][visible=false,ignore=false,explicitName=false], 
getter(s): null, 
setter(s): null]"
```



```ini
"ainteger" -> "[Property 'ainteger'; ctors: null, field(s): null, 
getter(s): [method com.zk.dirt.util.ArgsUtilTest$CamelCaseData#getAInteger(0 params)][visible=true,ignore=false,explicitName=false], 
setter(s): [method com.zk.dirt.util.ArgsUtilTest$CamelCaseData#setAInteger(1 params)][visible=true,ignore=false,explicitName=false]]"
```



 而当 anInteger 时 props 只有一个值 

```ini
"anInteger" -> "[Property 'anInteger'; 
ctors: null, 
field(s): [field com.zk.dirt.util.ArgsUtilTest$CamelCaseData#anInteger][visible=false,ignore=false,explicitName=false], 
getter(s): [method com.zk.dirt.util.ArgsUtilTest$CamelCaseData#getAnInteger(0 params)][visible=true,ignore=false,explicitName=false], 
setter(s): [method com.zk.dirt.util.ArgsUtilTest$CamelCaseData#setAnInteger(1 params)][visible=true,ignore=false,explicitName=false]]"
```



而从 getter 推名字主要是以下方法

```
com.fasterxml.jackson.databind.util.BeanUtil
implName = BeanUtil.okNameForRegularGetter(m, m.getName(), _stdBeanNaming);
```



在  okNameForRegularGetter 里有这样的注释，说明了为什么要这样处理一个小写字母开头的 field。

```java
// 17-Dec-2014, tatu: As per [databind#653], need to follow more
//   closely Java Beans spec; specifically, if two first are upper-case,
//   then no lower-casing should be done.
if ((offset + 1) < end) {
  if (Character.isUpperCase(basename.charAt(offset+1))) {
    return basename.substring(offset);
  }
}
```

可以找到这个： https://github.com/fasterxml/jackson-databind/issues/653

```ini
JavaBeans spec:
getUrl/setUrl => property name: url
getURL/setURL => property name: URL

Jackson:
getUrl/setUrl => property name: url
getURL/setURL => property name: url
```

而 [JavaBeans spec](https://download.oracle.com/otn-pub/jcp/7224-javabeans-1.01-fr-spec-oth-JSpec/beans.101.pdf?AuthParam=1662705279_f47591283f3d893b23340a328b484cf0) 的确如此这样规定。

![image-20220909143521593](https://zk4bucket.oss-cn-beijing.aliyuncs.com/uPic/image-20220909143521593.png)



做个简单测试：

``` java
 @Test
    void testGetterName() {
        String ret = BeanUtil.okNameForRegularGetter(null, "getAInteger", true);
        System.out.println(ret);  // AInteger
         ret = BeanUtil.okNameForRegularGetter(null, "getAInteger", false);
        System.out.println(ret);  // ainteger
        ret = BeanUtil.okNameForRegularGetter(null, "getAnInteger", true);
        System.out.println(ret);  // anInteger
        ret = BeanUtil.okNameForRegularGetter(null, "getAnInteger", false);
        System.out.println(ret);  // anInteger
    }
```









## TIPS
1. js object 与 json 不对称容易导致的 bug，比如： 从 java 返回 Map<Long,String>，序列化完就变成了 Map<String,String>，要特别注意。
       json 的 key 只能是字符串！且要有双引号，object 可以



## 参考

### spring plugin 机制

https://www.cnblogs.com/m78-seven/p/15399971.html

### spring ApplicationEvent 机制

https://www.cnblogs.com/m78-seven/p/15417797.html

 

### JPA RSQL version 2

https://github.com/perplexhub/rsql-jpa-specification

* spring-filter
https://github.com/turkraft/spring-filter

fluent-hibernate
https://github.com/v-ladynev/fluent-hibernate

### 智能表单

- Cross Device & High Performance Normal Form/Dynamic(JSON Schema) Form/Form Builder -- Support React/React Native/Vue 2/Vue 3
https://github.com/alibaba/formily

- 阿里巴巴 - 飞猪中后台「表单/表格/图表」开箱即用解决方案
https://xrender.fun/

### alibab fusion

https://github.com/alibaba-fusion/next

https://qingflow.com/index
https://qingflow.com/help/docs/6114c2d7d601550046421ddf

### antd admin

https://icode.best/i/36721736606141

### charts

https://recharts.org/en-US/examples/PieChartWithPaddingAngle

https://github.com/d2-projects/d2-admin

### 脚手架市场

https://scaffold.ant.design/#/

https://gitee.com/JeeHuangBingGui/jeeSpringCloud

### JsonSql.js

https://gitee.com/JeeHuangBingGui/JsonSql

### 事件驱动 flow-eda

https://linxfeng.github.io/flow-eda/#/introduce/overview

### 服务端脚手架

1. Guns
2. pig
3. RuoYi
4. Jeecg-boot
5. iBase4J
6. jeeSpringCloud

hiberate 5.4 reference 
https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#caching