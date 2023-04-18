其实我的想法很简单，能不能用一套数据模型驱动存储与展示。对于那种复杂的表格类界面。减少后台 UI 的开发调试成本。

第一想法就是与 JPA 搭配， JPA 抽象了存储， 那我要做的就是基于 JPA 抽象一套 UI。

用 JPA 的好处是 entity 的关系能够显示定义。 这样也可以做为驱动界面的要素。



普通的 CRUD 界面我直接生成前端代码不香么？

市面其实有很多 code generator。但问题在于：

1. 模板代码就是模板代码，通常很难达到生成就能用的程度。你还是得有前端熟悉的人去改，或者部署。
2. 针对行的自定义操作，前后端还是得写代码保证可用，后端得写 api，前端得写入口完成 api 调用。





业务 (Business) = 数据(Entity) +逻辑(Action)

Entity 面向对象，Action 面向事件驱动



然而使用 JPA 做为起点。也会有一系列问题。

1. 关系需要用来构建界面逻辑，那数据库的外键还需要吗？

**OneToMany  一对多，不可重复
ManyToOne  多对一，不可重复
OneToOne   一对一，不可重复
ManyToMany 多对多，关系任意，可重复

我的选择是不生成外键。 因为这为日后集成 mybatis 提供较好的兼容性。



2. 关系是用实体映射还是 id 映射？

   如果按传统 JPA 走。那肯定是实体映射合理。但 mybatis 怎么办？就是方便了一边。另一边必须麻烦。所以我还是选择实体映射。因为毕竟我现在是用 JPA 弄。 到做 mybatis 兼容时再说。



如果想快速存储一对多的 ids，而不加载实体，则需要使用  CascadeType.MERGE，用 CascadeType.ALL 不起作用。 讲真，这是 hibernate 的 bug。

> 具体见： https://stackoverflow.com/questions/13370221/persistentobjectexception-detached-entity-passed-to-persist-thrown-by-jpa-and-h
> Why? By saying "cascade ALL" on the child entity Transaction you require that every DB operation gets propagated to the parent entity Account. If you then do persist(transaction), persist(account) will be invoked as well.
> But only transient (new) entities may be passed to persist (Transaction in this case). The detached (or other non-transient state) ones may not (Account in this case, as it's already in DB).
> Therefore you get the exception "detached entity passed to persist". The Account entity is meant! Not the Transaction you call persist on.



3. 嵌套关系怎么处理

做个约定。要么关联，要么创建。api 要分开。体现在 UI 上会形成两次操作。


### 编程范式

1. 服务端 entity 对象测试
2. 基于事件驱动，可以非常快的与异步模型整合，且非常方便微服务拆分。 



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




## 服务端 TODO

1. [x] 搜索
   1. [x] 过滤 基于 spring-filter 的过滤
   1. [x] 分页
   1. [x] 排序
1. [x] 重写 url DSL 解释器
1. [x] 映射 JSR 303 注解校验到前端
1. JPA 支持
   1. [x] 多对一支持， 可筛选(输出 id)，创建
   1. [x] 多对多支持 (多对一的特例)
   1. [x] 一对一支持   可创建
   1. [x] 一对多支持,  可创建
1. [x] 自定义 action
1. [x] 在一对多，多对多时，界面上增加区分创建与关联
1. [x] 更新与创建关系时，只应该处理 id，不需要传递整个 entity
1. [x] 自动生成 以 id 为主导的关联代码，
    当前利用了 @JsonIdentityInfo，与 @JsonIdentityReference 两个注解。将 entity 转化成了 {id: xxx} 的形式。之所以用 IdObject， 是为了别名的扩展，
    如不显示 id，但要显示 name。但又觉得直接使用 id 也许会更方便，也可以统一 mbyatis 与 hibernate 的数据操作形式
1. [x] 树状结构组织，单纯的树状组织意义不大，很多时候树状结构，是为了方便用户的一种筛选条件。如左右而已，左树，右表，所以，不打算将树状态结构组织做成单独的功能。而是集成到筛选里。
1. [x] 关系支持查询
1. [x] 支持带循环结构关系显示 name，参看　IdNameObj，依赖　@JsonIdentityInfo，与 @JsonIdentityReference。
    　　　　如果不带循环，直接返回 name 即可。　　　　　
1. [x] schema 标准化
1. [x] 业务事件驱动
1. [x] UI 能匹配JPA 里的 entity 继承 
1. [x] 元数据支持，一定程度上使 mysql 表字段带有动态功能。
1. [ ] 支持 Map 映射
1. [ ] 虚拟 entity 支持
1. [ ] embeded 支持
1. [ ] 输出表单联动 schema
1. [ ] eFilterOperator 实现
1. [ ] action 动态返回显不显示的方案
1. [ ] 在获取数据时，只获取需要显示的数据， 尤其是关联关系，只返回 id，或者 id list/set
1. [ ] 抛出关键可 debug　的异常
1. [ ] json filter 替代 DSL ，更结构化点
## 前端
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
1. [ ] 省略掉 DirtField DirtEntity ，直接通过 meta 表配置出 schema


## 其他产品功能 TODO
1. [ ] excel 导出


## BUG
1. [x] 可用星期排序有 bug，会多出几条,　与 relation 有关
1. [x] 在 runtime 别依赖 paramter.getName()，比如  public void call(String name){...} "name" 这个名字很可能被编译器改成 arg0 或其他。
1. [x] 逻辑删除有问题， deleteInBatch 不走逻辑删除，先用 deleteAll，性能可优化
1. [ ] 前端颜色选择有问题
1. [ ] 因为将构建 header 的逻辑放到了 DirtEntity初始化时, 一些动态的 enum 在添加后是看不到的。需要做成 lambda 或接口形式延迟获得
1. [x] objectMapper 不是 bug 的 bug， https://github.com/FasterXML/jackson-databind/issues/2868
       aInteger 会被当成 ainteger , （一个小写开头的camelcase）， 但是 anInteger 却可以正常解析。之所以这样，是因为要顾及 JavaBeans spec，比如 getUrl/setUrl => property name: url
        

   所以最好的解决办法，就是，别只以一个小写开头写 field property

   



## TIPS
1. 后端与前端在数据传递时, 不要使用 Key primitive Number 类型,
   原因: js object 与 json 不对称会导致的 bug，
   比如: 从 java 返回 Map<Long,String>，序列化完就变成了 Map<String,String>，。
       json 的 key 只能是字符串.且要有双引号

### 元数据

所谓元数据，也就是描述数据的数据。

比如以下就是 mysql 元数据的描述，但通常这种元数据都是写死的。

```
name varchar(32) NOT NULL;
```

但如果我希望这些是动态的呢？

也就是说， 我能不能随时可以将 name 改成 title呢？ 能不能将 varchar(32) 改成  vharchar(64) 呢？能不能将NOT NULL 改成 NULL。

仔细想想，有很大坑在里面。 如果数据库已经定义好了数据类型。随时改元数据会导致严重的数据损坏的问题。比如将varchar(32) 改成 varchar(16)，丢失精度。

所以，在 Dirt 里提供的元数据管理不包含这类改变。 只包含前端元数据管理。 通常包含前端显示的别名，提交时的验证等。



## 参考

spring plugin 机制
https://www.cnblogs.com/m78-seven/p/15399971.html

spring ApplicationEvent 机制
https://www.cnblogs.com/m78-seven/p/15417797.html

 

JPA RSQL version 2
https://github.com/perplexhub/rsql-jpa-specification

spring-filter
https://github.com/turkraft/spring-filter

fluent-hibernate
https://github.com/v-ladynev/fluent-hibernate

智能表单
- Cross Device & High Performance Normal Form/Dynamic(JSON Schema) Form/Form Builder -- Support React/React Native/Vue 2/Vue 3
https://github.com/alibaba/formily

- 阿里巴巴 - 飞猪中后台「表单/表格/图表」开箱即用解决方案
https://xrender.fun/

alibab fusion
https://github.com/alibaba-fusion/next

https://qingflow.com/index
https://qingflow.com/help/docs/6114c2d7d601550046421ddf

antd admin
https://icode.best/i/36721736606141

charts
https://recharts.org/en-US/examples/PieChartWithPaddingAngle
https://github.com/d2-projects/d2-admin

脚手架市场
https://scaffold.ant.design/#/
https://gitee.com/JeeHuangBingGui/jeeSpringCloud

JsonSql.js
https://gitee.com/JeeHuangBingGui/JsonSql

事件驱动 flow-eda
https://linxfeng.github.io/flow-eda/#/introduce/overview


服务端脚手架
1. Guns
2. pig
3. RuoYi
4. Jeecg-boot
5. iBase4J
6. jeeSpringCloud

hiberate 5.4 reference 
https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#caching

https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#access
https://github.com/Blazebit/blaze-persistence#entity-view-usage



json-schemaeditor-antd 
是一个界面的好方案
https://gitee.com/furtherbank/json-schemaeditor-antd#https://furtherbank.gitee.io/json-schemaeditor-antd
