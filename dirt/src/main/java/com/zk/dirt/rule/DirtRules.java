package com.zk.dirt.rule;

import lombok.Data;

import javax.validation.constraints.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data
public class DirtRules {

    public static ArrayList<Map> parseRules(Field field) {
        ArrayList<Map> dirtRuleTypes = new ArrayList<Map>();
        Class<?> type = field.getType();

        {
            NotNull annotation = field.getAnnotation(NotNull.class);
            if (annotation != null){
                String msg =annotation.message();
                Map map = createRequired(msg);
                dirtRuleTypes.add(map);
            }

        }
        {
            NotEmpty annotation = field.getAnnotation(NotEmpty.class);
            if (annotation != null){
                String msg =annotation.message();
                Map map = createNotEmpty(msg);
                dirtRuleTypes.add(map);
            }

        }
        String name = field.getName();
        {
            Size annotation = field.getAnnotation(Size.class);
            if (annotation != null) {
                int min = annotation.min();
                int max = annotation.max();
                String msg = annotation.message();
                Map map = createSize(name, min, max, msg);
                dirtRuleTypes.add(map);
            }
        }


        {
            Max annotation = field.getAnnotation(Max.class);
            if (annotation != null) {

                long value = annotation.value();
                String msg = annotation.message();
                Map map = createMax(name, value, msg);
                dirtRuleTypes.add(map);
            }
        }

        {
            Min annotation = field.getAnnotation(Min.class);
            if (annotation != null) {

                String msg = annotation.message();
                long value = annotation.value();
                Map map = createMin(name, msg, value);
                dirtRuleTypes.add(map);
            }
        }

        {
            Email annotation = field.getAnnotation(Email.class);
            if (annotation != null) {

                String msg = annotation.message();
                Map map = createEmail(name, msg);
                dirtRuleTypes.add(map);
            }
        }

        return dirtRuleTypes;

    }

    public static Map createEmail(String name, String msg) {
        Map map = new HashMap();
        map.put("type", "email");

        if (msg.startsWith("{") && msg.endsWith("}")) {
            msg = String.format("%s 必须是邮箱格式", name);
        }
        map.put("message",msg);
        return map;
    }

    public static Map createMin(String name, String msg, long value) {
        Map map = new HashMap();
        map.put("type", "number");
        map.put("min", value);


        if (msg.startsWith("{") && msg.endsWith("}")) {

            msg = String.format("%s 必须大于等于 %d", name, value);
        }
        map.put("message",msg);
        return map;
    }

    public static Map createMax(String name, long value, String msg) {
        Map map = new HashMap();
        map.put("type", "number");
        map.put("max", value);


        if (msg.startsWith("{") && msg.endsWith("}")) {

            msg = String.format("%s 必须小于等于 %d", name, value);
        }
        map.put("message",msg);
        return map;
    }

    public static Map createSize(String name, int min, int max, String msg) {
        Map map = new HashMap();
        map.put("min", min);
        map.put("max", max);
        map.put("type", "string");

        // TODO: is expression, dynamic resolve
        if (msg.startsWith("{") && msg.endsWith("}")) {
            msg = String.format("%s length 必须在 [%d,%d] 区间",
                    name,
                    min,
                    max);
        }
        map.put("message",msg);
        return map;
    }

    public static Map createNotEmpty(String msg) {
        Map map = new HashMap();
        map.put("required",true);

        // TODO: is expression, dynamic resolve
        if (msg.startsWith("{") && msg.endsWith("}")) {
            msg = "不可为空字符串";
        }
        map.put("message",msg);
        return map;
    }

    public static Map createRequired(String msg) {
        Map map = new HashMap();
        map.put("required",true);

        // TODO: is expression, dynamic resolve
        if (msg.startsWith("{") && msg.endsWith("}")) {
            msg = "不可为 null";
        }
        map.put("message",msg);
        return map;
    }

}