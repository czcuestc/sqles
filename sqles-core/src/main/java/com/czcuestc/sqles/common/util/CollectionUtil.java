package com.czcuestc.sqles.common.util;

import java.util.Collection;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class CollectionUtil {
    public static boolean isEmpty(Collection collection) {
        return (collection == null || collection.size() == 0);
    }
}
