package com.zk.dirt.core;

import lombok.Data;

import javax.validation.constraints.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data
public class DirtRuleAnnotationConvertor {

    // 是否为必选字段 // 从 @column 里取值
    //private Boolean required;

    // 错误信息，不设置时会通过模板自动生成
    //private String message;

    //DirtRuleType defaultField;    //仅在 type 为 array 类型时有效，用于指定数组元素的校验规则	rule

    //fields	仅在 type 为 array 或 object 类型时有效，用于指定子元素的校验规则	Record<string, rule>

    // string 类型时为字符串长度；number 类型时为确定数字； array 类型时为数组长度	number

    //Integer len;
    //
    //// 必须设置 type：string 类型为字符串最大长度；number 类型时为最大值；array 类型时为数组最大长度	number
    //long max;
    //
    //// 必须设置 type：string 类型为字符串最小长度；number 类型时为最小值；array 类型时为数组最小长度	number
    //long min;

    //String type;
    //
    //// 正则表达式匹配	RegExp
    //String pattern;
    //
    //// type	类型，常见有 string |number |boolean |url | email。更多请参考此处	string
    //// validateTrigger	设置触发验证时机，必须是 Form.Item 的 validateTrigger 的子集	string | string[]
    //// validator	自定义校验，接收 Promise 作为返回值。示例参考	(rule, value) => io.netty.util.concurrent.Promise
    ////Boolean warningOnly; //	仅警告，不阻塞表单提交	boolean	4.17.0
    //
    //
    ////	如果字段仅包含空格则校验不通过，只在 type: 'string' 时生效
    //Boolean whitespace;

    public static ArrayList<Map> parseRules(Field field) {
        ArrayList<Map> dirtRuleTypes = new ArrayList<Map>();
        Class<?> type = field.getType();

        {
            NotNull annotation = field.getAnnotation(NotNull.class);
            if (annotation != null){
                Map map = new HashMap();
                map.put("required",true);
                String msg =annotation.message();

                // TODO: is expression, dynamic resolve
                if (msg.startsWith("{") && msg.endsWith("}")) {
                    msg = "不可为 null";
                }
                map.put("message",msg);
                dirtRuleTypes.add(map);
            }

        }
        {
            NotEmpty annotation = field.getAnnotation(NotEmpty.class);
            if (annotation != null){
                Map map = new HashMap();
                map.put("required",true);
                String msg =annotation.message();

                // TODO: is expression, dynamic resolve
                if (msg.startsWith("{") && msg.endsWith("}")) {
                    msg = "不可为空字符串";
                }
                map.put("message",msg);
                dirtRuleTypes.add(map);
            }

        }
        {
            Size annotation = field.getAnnotation(Size.class);
            if (annotation != null) {
                Map map = new HashMap();
                map.put("min", annotation.min());
                map.put("max", annotation.max());
                map.put("type", "string");

                String msg = annotation.message();

                // TODO: is expression, dynamic resolve
                if (msg.startsWith("{") && msg.endsWith("}")) {
                    msg = String.format("%s length 必须在 [%d,%d] 区间",
                            field.getName(),
                            annotation.min(),
                            annotation.max());
                }
                map.put("message",msg);
                dirtRuleTypes.add(map);
            }
        }


        {
            Max annotation = field.getAnnotation(Max.class);
            if (annotation != null) {

                Map map = new HashMap();
                map.put("type", "number");
                map.put("max", annotation.value());

                String msg = annotation.message();

                if (msg.startsWith("{") && msg.endsWith("}")) {

                    msg = String.format("%s 必须小于等于 %d", field.getName(), annotation.value());
                }
                map.put("message",msg);
                dirtRuleTypes.add(map);
            }
        }

        {
            Min annotation = field.getAnnotation(Min.class);
            if (annotation != null) {

                Map map = new HashMap();
                map.put("type", "number");
                map.put("min", annotation.value());

                String msg = annotation.message();

                if (msg.startsWith("{") && msg.endsWith("}")) {

                    msg = String.format("%s 必须大于等于 %d", field.getName(), annotation.value());
                }
                map.put("message",msg);
                dirtRuleTypes.add(map);
            }
        }

        {
            Email annotation = field.getAnnotation(Email.class);
            if (annotation != null) {

                Map map = new HashMap();
                map.put("type", "email");

                String msg = annotation.message();
                if (msg.startsWith("{") && msg.endsWith("}")) {
                    msg = String.format("%s 必须是邮箱格式", field.getName());
                }
                map.put("message",msg);
                dirtRuleTypes.add(map);
            }
        }

        return dirtRuleTypes;

    }

}