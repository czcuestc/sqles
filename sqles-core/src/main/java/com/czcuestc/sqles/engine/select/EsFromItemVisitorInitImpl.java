package com.czcuestc.sqles.engine.select;

import com.czcuestc.sqles.common.util.StringUtil;
import com.czcuestc.sqles.engine.context.SelectContext;
import com.czcuestc.sqles.meta.IndexInfo;
import com.czcuestc.sqles.meta.container.IndexInfoManager;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.*;

public class EsFromItemVisitorInitImpl implements FromItemVisitor {
    private SelectContext context;

    public EsFromItemVisitorInitImpl(SelectContext context) {
        this.context = context;
    }

    @Override
    public void visit(Table table) {
        if (table.getAlias() != null) {
            context.setAliasTableName(table.getAlias().getName());
        }

        context.setTableName(table.getName());

        initContext();
    }

    @Override
    public void visit(ParenthesedSelect selectBody) {

    }

    @Override
    public void visit(LateralSubSelect lateralSubSelect) {

    }

    @Override
    public void visit(TableFunction tableFunction) {

    }

    @Override
    public void visit(ParenthesedFromItem aThis) {

    }

    private void initContext() {
        if (!IndexInfoManager.getInstance().containsKey(context.getTableName())) {
            throw new RuntimeException(StringUtil.concat(context.getTableName(), "index not exist"));
        }

        IndexInfo indexInfo = IndexInfoManager.getInstance().getIndexInfo(context.getTableName());
        context.setIndexInfo(indexInfo);
    }
}
