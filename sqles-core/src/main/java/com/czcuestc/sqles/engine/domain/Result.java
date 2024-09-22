package com.czcuestc.sqles.engine.domain;

import com.czcuestc.sqles.common.DataType;
import com.czcuestc.sqles.common.util.CollectionUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Result implements Serializable {
    private static final long serialVersionUID = 5795548576101386882L;
    protected Map<String, Integer> headerIndexes;
    protected List<Header> headers;
    protected List<ResultRowData> rows;

    public Result() {
        this.headerIndexes = new HashMap<>();
        this.headers = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    public Result(Result result) {
        this.headerIndexes = result.headerIndexes;
        this.headers = result.headers;
        this.rows = result.rows;
    }

    public Result(Header[] headers) {
        this();
        for (Header header : headers) {
            this.headerIndexes.put(header.getName(), this.headers.size());
            this.headers.add(header);
        }
    }

    public void newColumn(String columnName, DataType dataType) {
        Header header = new Header(columnName, dataType);
        this.headerIndexes.put(header.getName(), this.headers.size());
        this.headers.add(header);
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public ResultRow newResultRow() {
        ResultRowData row = new ResultRowData(headers.size());
        this.rows.add(row);
        return new ResultRow(headerIndexes, row);
    }

    public ResultRow getResultRow(int rowIndex) {
        ResultRowData row = this.rows.get(rowIndex);
        return new ResultRow(headerIndexes, row);
    }

    public Object getValue(int rowIndex, int columnIndex) {
        ResultRowData row = this.rows.get(rowIndex);
        return row.getValue(columnIndex);
    }

    public int size() {
        return rows.size();
    }

    public int sizeOfColumn() {
        if (CollectionUtil.isEmpty(headers)) return 0;
        return headers.size();
    }

    public Header getHeader(int columnIndex) {
        return headers.get(columnIndex);
    }

    public int getColumnIndex(String columnName) {
        Integer columnIndex = headerIndexes.get(columnName);
        if (columnIndex == null) return -1;
        return columnIndex;
    }

    public boolean containsColumn(String columnName) {
        return headerIndexes.containsKey(columnName);
    }

    public void clear() {
        this.rows.clear();
    }

//    public SortedResult sort(Pair<Integer, Integer>[] pairs) {
//        int[] sortedArray = SortUtil.sort(this, pairs);
////        List<ResultRowData> list = new ArrayList<>(sortedArray.length);
////        for (int index = 0; index < sortedArray.length; index++) {
////            ResultRowData resultRowData = this.rows.get(sortedArray[index]);
////            list.add(resultRowData);
////        }
//
//        SortedResult sortedResult = new SortedResult(this);
//        sortedResult.setSortedIndex(sortedArray);
//        return sortedResult;
//    }

    public Result limit(int limit, int offset) {
        int startIndex = offset;
        int endIndex = limit < 0 ? this.size() : Math.min(this.size(), limit + offset);
        List<ResultRowData> list = new ArrayList<>(limit);
        for (int index = startIndex; index < endIndex; index++) {
            ResultRowData resultRowData = this.rows.get(index);
            list.add(resultRowData);
        }
        return clone(rows);
    }

    public Result clone(List<ResultRowData> rows) {
        Result result = new Result();
        result.headers = this.headers;
        result.headerIndexes = this.headerIndexes;
        result.rows = rows;
        return result;
    }
}
