package com.czcuestc.sqles.common.util;

import java.util.Objects;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class ObjectUtil {
    public static boolean equals(Object[] oldKeys, Object[] newKeys) {
        if (oldKeys == newKeys) return true;

        if (oldKeys == null || newKeys == null) {
            return false;
        }

        if (oldKeys.length != newKeys.length) return false;

        for (int i = 0; i < oldKeys.length; i++) {
            if (!Objects.equals(newKeys[i], oldKeys[i])) {
                return false;
            }
        }

        return true;
    }

    public static int compareTo(Comparable v1, Comparable v2) {
        if (v1 != null) {
            return v1.compareTo(v2);
        }
        if (v2 != null) {
            return -1;
        }

        return 0;
    }
}
