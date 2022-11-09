package com.zk.dirt.split.splitter;

import com.google.common.reflect.TypeToken;
import com.zk.dirt.split.intef.SmartParamSplitter;

/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 固定类型的 SmartParamSpliter <br />
 * 从泛型中获取支持类型
 */
abstract class AbstractFixTypeParamSplitter<P>
        extends AbstractParamSplitter<P>
        implements SmartParamSplitter<P> {
    private final Class supportType;

    protected AbstractFixTypeParamSplitter() {

        // 从泛型中获取支持的类型
        TypeToken<P> typeToken = new TypeToken<P>(getClass()) {};
        this.supportType = (Class) typeToken.getRawType();
    }

    @Override
    public final boolean support(Class<P> paramType) {
        if (paramType == null){
            return false;
        }
        return this.supportType.isAssignableFrom(paramType);
    }

}
