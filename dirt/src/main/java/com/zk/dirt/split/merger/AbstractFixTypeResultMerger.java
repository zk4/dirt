package com.zk.dirt.split.merger;

import com.google.common.reflect.TypeToken;
import com.zk.dirt.split.intef.SmartResultMerger;

/**
 * Created by taoli on 2022/7/6.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 *
 * 从泛型中获取类型，用于进行 support
 */
abstract class AbstractFixTypeResultMerger<R>
        extends AbstractResultMerger<R>
        implements SmartResultMerger<R> {
    private final Class supportType;

    public AbstractFixTypeResultMerger() {
        TypeToken<R> typeToken = new TypeToken<R>(getClass()) {};
        this.supportType = (Class) typeToken.getRawType();
    }

    @Override
    public final boolean support(Class<R> resultType) {
        if (resultType == null){
            return false;
        }
        return this.supportType.isAssignableFrom(resultType);
    }
}
