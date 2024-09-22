package com.czcuestc.sqles.engine.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class SortedResult extends Result {
    private int[] sortedIndex;

    public SortedResult(Result result) {
        super(result);
    }

    public int[] getSortedIndex() {
        return sortedIndex;
    }

    public void setSortedIndex(int[] sortedIndex) {
        this.sortedIndex = sortedIndex;
    }

    @Override
    public ResultRow getResultRow(int rowIndex) {
        int index = this.sortedIndex[rowIndex];
        ResultRowData row = this.rows.get(index);
        return new ResultRow(headerIndexes, row);
    }

    @Override
    public Object getValue(int rowIndex, int columnIndex) {
        int index = this.sortedIndex[rowIndex];
        ResultRowData row = this.rows.get(index);
        return row.getValue(columnIndex);
    }

    @Override
    public Result limit(int limit, int offset) {
        int startIndex = offset;
        int endIndex = limit < 0 ? this.size() : Math.min(this.size(), limit + offset);
        List<ResultRowData> list = new ArrayList<>(limit);
        for (int index = startIndex; index < endIndex; index++) {
            int rowIndex = sortedIndex[index];
            ResultRowData resultRowData = this.rows.get(rowIndex);
            list.add(resultRowData);
        }

        return clone(list);
    }
}
