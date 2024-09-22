package com.czcuestc.sqles.common.exceptions;

import com.czcuestc.sqles.common.util.StringUtil;
import net.sf.jsqlparser.expression.Expression;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class NotSupportException extends RuntimeException {
    private static final long serialVersionUID = 6578211429934764273L;

    public NotSupportException(String function) {
        super(StringUtil.format("{} not supported", function));
    }

    public NotSupportException(Expression function) {
        super(StringUtil.format("{} not supported", function));
    }

    public NotSupportException(Throwable e) {
        super(e);
    }

    public NotSupportException(String message, Throwable e) {
        super(message, e);
    }
}
