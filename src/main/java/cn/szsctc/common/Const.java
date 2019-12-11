package cn.szsctc.common;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.experimental.Accessors;


public interface Const {
    /**
     * 默认的编码集合
     */
    String DEFAULT_CHARSET = "utf-8";
    int RESULT_SUC = 0;
    int DKL_TYPE_KEY = 0;
    int DKL_STAUTS_SUC = 1;
}
