package com.zk.dirt.core;

import lombok.Data;

import javax.validation.constraints.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data
public class DirtRuleAnnotationConvertor {

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