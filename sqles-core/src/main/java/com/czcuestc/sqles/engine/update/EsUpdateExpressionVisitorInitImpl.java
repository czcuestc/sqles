package com.czcuestc.sqles.engine.update;

import com.czcuestc.sqles.common.DataType;
import com.czcuestc.sqles.common.exceptions.EsException;
import com.czcuestc.sqles.common.exceptions.NotSupportException;
import com.czcuestc.sqles.common.util.CollectionUtil;
import com.czcuestc.sqles.common.util.StringUtil;
import com.czcuestc.sqles.engine.context.Context;
import com.czcuestc.sqles.engine.context.ValueContext;
import com.czcuestc.sqles.engine.select.MatchMode;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.conditional.XorExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.ParenthesedSelect;
import net.sf.jsqlparser.statement.select.Select;
import org.elasticsearch.index.query.*;

public class EsUpdateExpressionVisitorInitImpl implements ExpressionVisitor {
    private Context context;

    public EsUpdateExpressionVisitorInitImpl(Context context) {
        this.context = context;
    }

    @Override
    public void visit(BitwiseRightShift aThis) {
        throw new NotSupportException(aThis);
    }

    @Override
    public void visit(BitwiseLeftShift aThis) {
        throw new NotSupportException(aThis);
    }

    @Override
    public void visit(NullValue nullValue) {
        throw new NotSupportException(nullValue);
    }

    @Override
    public void visit(Function function) {
        throw new NotSupportException(function);
    }

    @Override
    public void visit(SignedExpression signedExpression) {
        ValueContext valueContext = new ValueContext(signedExpression.toString());
        context.push(valueContext);
    }

    @Override
    public void visit(JdbcParameter jdbcParameter) {
        Object value = context.getParameters().getParameter(jdbcParameter.getIndex() - 1);
        DataType type = context.getParameters().getType(jdbcParameter.getIndex() - 1);
        ValueContext valueContext = new ValueContext(value, type);
        context.push(valueContext);
    }

    @Override
    public void visit(JdbcNamedParameter jdbcNamedParameter) {
        throw new NotSupportException(jdbcNamedParameter);
    }

    @Override
    public void visit(DoubleValue doubleValue) {
        ValueContext valueContext = new ValueContext(doubleValue.getValue(), DataType.DOUBLE);
        context.push(valueContext);
    }


    @Override
    public void visit(LongValue longValue) {
        ValueContext valueContext = new ValueContext(longValue.getValue(), DataType.INT64);
        context.push(valueContext);
    }

    @Override
    public void visit(HexValue hexValue) {
        throw new NotSupportException(hexValue);
    }


    @Override
    public void visit(DateValue dateValue) {
        ValueContext valueContext = new ValueContext(dateValue.getValue(), DataType.DATE);
        context.push(valueContext);
    }


    @Override
    public void visit(TimeValue timeValue) {
        ValueContext valueContext = new ValueContext(timeValue.getValue(), DataType.TIME);
        context.push(valueContext);
    }


    @Override
    public void visit(TimestampValue timestampValue) {
        ValueContext valueContext = new ValueContext(timestampValue.getValue(), DataType.TIMESTAMP);
        context.push(valueContext);
    }


    @Override
    public void visit(Parenthesis parenthesis) {
        parenthesis.getExpression().accept(this);
    }

    @Override
    public void visit(StringValue stringValue) {
        ValueContext valueContext = new ValueContext(stringValue.getValue(), DataType.STRING);
        context.push(valueContext);
    }

    @Override
    public void visit(Addition addition) {
        throw new NotSupportException(addition);
    }

    @Override
    public void visit(Division division) {
        throw new NotSupportException(division);
    }

    @Override
    public void visit(IntegerDivision division) {
        throw new NotSupportException(division);
    }

    @Override
    public void visit(Multiplication multiplication) {
        throw new NotSupportException(multiplication);
    }

    @Override
    public void visit(Subtraction subtraction) {
        throw new NotSupportException(subtraction);
    }

    @Override
    public void visit(AndExpression andExpression) {
        andExpression.getLeftExpression().accept(this);
        ValueContext left = context.pop();

        andExpression.getRightExpression().accept(this);
        ValueContext right = context.pop();

        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.must(left.getBuilder());
        builder.must(right.getBuilder());

        ValueContext valueContext = new ValueContext(builder);
        context.push(valueContext);
    }

    @Override
    public void visit(OrExpression orExpression) {
        orExpression.getLeftExpression().accept(this);
        ValueContext left = context.pop();

        orExpression.getRightExpression().accept(this);
        ValueContext right = context.pop();

        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.should(left.getBuilder());
        builder.should(right.getBuilder());

        ValueContext valueContext = new ValueContext(builder);
        context.push(valueContext);
    }

    @Override
    public void visit(XorExpression orExpression) {
        throw new NotSupportException(orExpression);
    }

    @Override
    public void visit(Between between) {
        initExpression(between.getLeftExpression());
        ValueContext column = context.pop();
        initExpression(between.getBetweenExpressionStart());
        ValueContext start = context.pop();

        initExpression(between.getBetweenExpressionEnd());
        ValueContext end = context.pop();

        RangeQueryBuilder builder = QueryBuilders.rangeQuery(String.valueOf(column.getValue()))
                .gte(start.getValue())
                .lte(end.getValue());


        ValueContext valueContext = new ValueContext(builder);
        context.push(valueContext);
    }

    @Override
    public void visit(OverlapsCondition overlapsCondition) {
        throw new NotSupportException(overlapsCondition);
    }

    @Override
    public void visit(EqualsTo equalsTo) {
        equalsTo.getLeftExpression().accept(this);
        ValueContext left = context.pop();

        equalsTo.getRightExpression().accept(this);
        ValueContext right = context.pop();

        if (left.isSingleColumn()) {
            TermQueryBuilder builder = QueryBuilders.termQuery(String.valueOf(left.getValue()), right.getValue());
            ValueContext valueContext = new ValueContext(builder);
            context.push(valueContext);
            return;
        }
        if (right.isSingleColumn()) {
            TermQueryBuilder builder = QueryBuilders.termQuery(String.valueOf(right.getValue()), left.getValue());
            ValueContext valueContext = new ValueContext(builder);
            context.push(valueContext);
            return;
        }

        throw new EsException(StringUtil.format("{} syntax error", equalsTo));
    }

    @Override
    public void visit(GreaterThan greaterThan) {
        greaterThan.getLeftExpression().accept(this);
        ValueContext left = context.pop();

        greaterThan.getRightExpression().accept(this);
        ValueContext right = context.pop();

        if (left.isSingleColumn()) {
            RangeQueryBuilder builder = QueryBuilders.rangeQuery(String.valueOf(left.getValue()));
            builder.gt(right.getValue());
            ValueContext valueContext = new ValueContext(builder);
            context.push(valueContext);
            return;
        }
        if (right.isSingleColumn()) {
            RangeQueryBuilder builder = QueryBuilders.rangeQuery(String.valueOf(right.getValue()));
            builder.gt(left.getValue());
            ValueContext valueContext = new ValueContext(builder);
            context.push(valueContext);
            return;
        }

        throw new EsException(StringUtil.format("{} syntax error", greaterThan));
    }

    @Override
    public void visit(GreaterThanEquals greaterThanEquals) {
        greaterThanEquals.getLeftExpression().accept(this);
        ValueContext left = context.pop();

        greaterThanEquals.getRightExpression().accept(this);
        ValueContext right = context.pop();

        if (left.isSingleColumn()) {
            RangeQueryBuilder builder = QueryBuilders.rangeQuery(String.valueOf(left.getValue()));
            builder.gte(right.getValue());
            ValueContext valueContext = new ValueContext(builder);
            context.push(valueContext);
            return;
        }
        if (right.isSingleColumn()) {
            RangeQueryBuilder builder = QueryBuilders.rangeQuery(String.valueOf(right.getValue()));
            builder.gte(left.getValue());
            ValueContext valueContext = new ValueContext(builder);
            context.push(valueContext);
            return;
        }

        throw new EsException(StringUtil.format("{} syntax error", greaterThanEquals));
    }

    @Override
    public void visit(InExpression inExpression) {
        inExpression.getLeftExpression().accept(this);
        ValueContext left = context.pop();

        inExpression.getRightExpression().accept(this);
        ValueContext right = context.pop();

        if (left.isSingleColumn()) {
            if (inExpression.isNot()) {
                TermsQueryBuilder builder = QueryBuilders.termsQuery(String.valueOf(left.getValue()), right.getValues());
                BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
                boolQueryBuilder.mustNot(builder);

                ValueContext valueContext = new ValueContext(boolQueryBuilder);
                context.push(valueContext);
                return;
            } else {
                TermsQueryBuilder builder = QueryBuilders.termsQuery(String.valueOf(left.getValue()), right.getValues());
                ValueContext valueContext = new ValueContext(builder);
                context.push(valueContext);
                return;
            }
        }

        throw new EsException(StringUtil.format("{} syntax error", inExpression));
    }

    @Override
    public void visit(FullTextSearch fullTextSearch) {
        ExpressionList<Column> columns = fullTextSearch.getMatchColumns();
        if (CollectionUtil.isEmpty(columns)) {
            throw new EsException(StringUtil.format("{} syntax error", fullTextSearch));
        }

        Float boost = null;
        MultiMatchQueryBuilder.Type type = null;
        MatchMode matchMode = MatchMode.NONE;
        Integer slot = null;
        String[] columnString = new String[columns.size()];
        String searchString = null;
        try {
            for (int index = 0; index < columns.size(); index++) {
                columns.get(index).accept(this);
                ValueContext valueContext = context.pop();
                columnString[index] = String.valueOf(valueContext.getValue());
            }

            fullTextSearch.getAgainstValue().accept(this);
            ValueContext valueContext = context.pop();
            searchString = String.valueOf(valueContext.getValue());

            if (!StringUtil.isEmpty(fullTextSearch.getSearchModifier())) {
                String searchModifier = fullTextSearch.getSearchModifier();
                String[] pairs = StringUtil.split('&', searchModifier);
                for (String pair : pairs) {
                    String[] kv = StringUtil.split('=', pair);
                    if (kv[0].equalsIgnoreCase("mode")) {
                        matchMode = MatchMode.getMatchMode(kv[1]);
                    } else if (kv[0].equalsIgnoreCase("type")) {
                        type = MultiMatchQueryBuilder.Type.valueOf(kv[1].toUpperCase());
                    } else if (kv[0].equalsIgnoreCase("boost")) {
                        boost = Float.parseFloat(kv[1]);
                    } else if (kv[0].equalsIgnoreCase("slot")) {
                        slot = Integer.parseInt(kv[1]);
                    } else {
                        throw new EsException(StringUtil.format("{} syntax error", fullTextSearch));
                    }
                }
            }
        } catch (Exception e) {
            throw new EsException(StringUtil.format("{} syntax error", fullTextSearch));
        }

        QueryBuilder queryBuilder = null;
        switch (matchMode) {
            case NONE: {
                if (columns.size() == 1) {
                    MatchQueryBuilder matchQuery = QueryBuilders.matchQuery(columnString[0], searchString);
                    if (boost != null) {
                        matchQuery.boost(boost);
                    }
                    queryBuilder = matchQuery;
                } else {
                    MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(searchString, columnString);
                    if (type != null) {
                        multiMatchQuery = multiMatchQuery.type(type);
                    }
                    if (slot != null) {
                        multiMatchQuery.slop(slot);
                    }
                    if (boost != null) {
                        multiMatchQuery.boost(boost);
                    }
                    queryBuilder = multiMatchQuery;
                }
            }
            case MATCH: {
                MatchQueryBuilder matchQuery = QueryBuilders.matchQuery(columnString[0], searchString);
                if (boost != null) {
                    matchQuery.boost(boost);
                }
                queryBuilder = matchQuery;
            }
            break;
            case MULTI_MATCH: {
                MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(searchString, columnString);
                if (type != null) {
                    multiMatchQuery = multiMatchQuery.type(type);
                }
                if (slot != null) {
                    multiMatchQuery.slop(slot);
                }
                if (boost != null) {
                    multiMatchQuery.boost(boost);
                }
                queryBuilder = multiMatchQuery;
            }
            break;
            case MATCH_PHRASE: {
                MatchPhraseQueryBuilder matchPhraseQuery = QueryBuilders.matchPhraseQuery(columnString[0], searchString);
                if (boost != null) {
                    matchPhraseQuery.boost(boost);
                }
                if (slot != null) {
                    matchPhraseQuery.slop(slot);
                }
                queryBuilder = matchPhraseQuery;
            }
            break;
            case QUERY_STRING: {
                QueryStringQueryBuilder queryStringQuery = QueryBuilders.queryStringQuery(searchString);
                if (boost != null) {
                    queryStringQuery.boost(boost);
                }
                for (int index = 0; index < columnString.length; index++) {
                    queryStringQuery.field(columnString[index]);
                }

                queryBuilder = queryStringQuery;
            }
            break;
        }

        ValueContext valueContext = new ValueContext(queryBuilder);
        context.push(valueContext);
    }

    @Override
    public void visit(IsNullExpression isNullExpression) {
        isNullExpression.getLeftExpression().accept(this);
        ValueContext left = context.pop();

        QueryBuilder builder = null;
        if (left.isSingleColumn()) {
            String columnName = String.valueOf(left.getValue());
            if (isNullExpression.isNot()) {
                builder = QueryBuilders.existsQuery(columnName);
            } else {
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                ExistsQueryBuilder existsQueryBuilder = QueryBuilders.existsQuery(columnName);
                boolQueryBuilder.mustNot(existsQueryBuilder);
                builder = boolQueryBuilder;
            }

            if (builder != null) {
                ValueContext valueContext = new ValueContext(builder);
                context.push(valueContext);
                return;
            }
        }

        throw new EsException(StringUtil.format("{} syntax error", isNullExpression));
    }

    @Override
    public void visit(IsBooleanExpression isBooleanExpression) {
        isBooleanExpression.getLeftExpression().accept(this);
        ValueContext left = context.pop();

        QueryBuilder builder = null;
        if (left.isSingleColumn()) {
            String columnName = String.valueOf(left.getValue());
            if (!isBooleanExpression.isNot()) {
                builder = QueryBuilders.termQuery(columnName, isBooleanExpression.isTrue());
            } else {
                BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
                TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(columnName, isBooleanExpression.isTrue());
                boolQueryBuilder.mustNot(termQueryBuilder);
                builder = boolQueryBuilder;
            }

            if (builder != null) {
                ValueContext valueContext = new ValueContext(builder);
                context.push(valueContext);
                return;
            }
        }

        throw new EsException(StringUtil.format("{} syntax error", isBooleanExpression));
    }

    @Override
    public void visit(LikeExpression likeExpression) {
        likeExpression.getLeftExpression().accept(this);
        ValueContext left = context.pop();

        likeExpression.getRightExpression().accept(this);
        ValueContext right = context.pop();

        QueryBuilder builder = null;
        if (left.isSingleColumn()) {
            String queryString = String.valueOf(right.getValue());
            if (StringUtil.isPrefix(queryString)) {
                builder = QueryBuilders.prefixQuery(String.valueOf(left.getValue()), StringUtil.getPrefixQueryString(queryString));
            } else {
                builder = QueryBuilders.matchQuery(String.valueOf(left.getValue()), right.getValue());
            }

            if (builder != null) {
                ValueContext valueContext = new ValueContext(builder);
                context.push(valueContext);
                return;
            }
        }

        throw new EsException(StringUtil.format("{} syntax error", likeExpression));
    }

    @Override
    public void visit(MinorThan minorThan) {
        minorThan.getLeftExpression().accept(this);
        ValueContext left = context.pop();

        minorThan.getRightExpression().accept(this);
        ValueContext right = context.pop();

        if (left.isSingleColumn()) {
            RangeQueryBuilder builder = QueryBuilders.rangeQuery(String.valueOf(left.getValue()));
            builder.lt(right.getValue());
            ValueContext valueContext = new ValueContext(builder);
            context.push(valueContext);
            return;
        }
        if (right.isSingleColumn()) {
            RangeQueryBuilder builder = QueryBuilders.rangeQuery(String.valueOf(right.getValue()));
            builder.lt(left.getValue());
            ValueContext valueContext = new ValueContext(builder);
            context.push(valueContext);
            return;
        }

        throw new EsException(StringUtil.format("{} syntax error", minorThan));
    }

    @Override
    public void visit(MinorThanEquals minorThanEquals) {
        minorThanEquals.getLeftExpression().accept(this);
        ValueContext left = context.pop();

        minorThanEquals.getRightExpression().accept(this);
        ValueContext right = context.pop();

        if (left.isSingleColumn()) {
            RangeQueryBuilder builder = QueryBuilders.rangeQuery(String.valueOf(left.getValue()));
            builder.lte(right.getValue());
            ValueContext valueContext = new ValueContext(builder);
            context.push(valueContext);
            return;
        }
        if (right.isSingleColumn()) {
            RangeQueryBuilder builder = QueryBuilders.rangeQuery(String.valueOf(right.getValue()));
            builder.lte(left.getValue());
            ValueContext valueContext = new ValueContext(builder);
            context.push(valueContext);
            return;
        }

        throw new EsException(StringUtil.format("{} syntax error", minorThanEquals));
    }

    @Override
    public void visit(NotEqualsTo notEqualsTo) {
        notEqualsTo.getLeftExpression().accept(this);
        ValueContext left = context.pop();

        notEqualsTo.getRightExpression().accept(this);
        ValueContext right = context.pop();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (left.isSingleColumn()) {
            TermQueryBuilder builder = QueryBuilders.termQuery(String.valueOf(left.getValue()), right.getValue());
            boolQueryBuilder.mustNot(builder);
            ValueContext valueContext = new ValueContext(boolQueryBuilder);
            context.push(valueContext);
            return;
        }
        if (right.isSingleColumn()) {
            TermQueryBuilder builder = QueryBuilders.termQuery(String.valueOf(right.getValue()), left.getValue());
            boolQueryBuilder.mustNot(builder);
            ValueContext valueContext = new ValueContext(boolQueryBuilder);
            context.push(valueContext);
            return;
        }

        throw new EsException(StringUtil.format("{} syntax error", notEqualsTo));
    }

    @Override
    public void visit(DoubleAnd doubleAnd) {
        throw new NotSupportException(doubleAnd);
    }

    @Override
    public void visit(Contains contains) {
        throw new NotSupportException(contains);
    }

    @Override
    public void visit(ContainedBy containedBy) {
        throw new NotSupportException(containedBy);
    }

    @Override
    public void visit(ParenthesedSelect selectBody) {
        throw new NotSupportException(selectBody);
    }

    @Override
    public void visit(Column tableColumn) {
        ValueContext valueContext = new ValueContext(tableColumn.getColumnName());
        valueContext.setSingleColumn(true);
        context.push(valueContext);
    }

    @Override
    public void visit(CaseExpression caseExpression) {
//        initExpression(caseExpression.getSwitchExpression());
//        initExpression(caseExpression.getElseExpression());
//
//        List<WhenClause> whenClauses = caseExpression.getWhenClauses();
//        if (CollectionUtil.isEmpty(whenClauses)) return;
//
//        for (WhenClause whenClause : whenClauses) {
//            initExpression(whenClause);
//        }
        throw new NotSupportException(caseExpression);
    }

    @Override
    public void visit(WhenClause whenClause) {
//        initExpression(whenClause.getWhenExpression());
//        initExpression(whenClause.getThenExpression());
        throw new NotSupportException(whenClause);
    }

    @Override
    public void visit(ExistsExpression existsExpression) {
        throw new NotSupportException(existsExpression);
    }

    @Override
    public void visit(MemberOfExpression memberOfExpression) {
        throw new NotSupportException(memberOfExpression);
    }

    @Override
    public void visit(AnyComparisonExpression anyComparisonExpression) {
        throw new NotSupportException(anyComparisonExpression);
    }

    @Override
    public void visit(Concat concat) {
        throw new NotSupportException(concat);
    }

    @Override
    public void visit(Matches matches) {
        throw new NotSupportException(matches);
    }

    @Override
    public void visit(BitwiseAnd bitwiseAnd) {
        throw new NotSupportException(bitwiseAnd);
    }

    @Override
    public void visit(BitwiseOr bitwiseOr) {
        throw new NotSupportException(bitwiseOr);
    }

    @Override
    public void visit(BitwiseXor bitwiseXor) {
        throw new NotSupportException(bitwiseXor);
    }

    @Override
    public void visit(CastExpression cast) {
        throw new NotSupportException(cast);
    }

    @Override
    public void visit(Modulo modulo) {
        throw new NotSupportException(modulo);
    }

    @Override
    public void visit(AnalyticExpression aexpr) {
        throw new NotSupportException(aexpr);
    }

    @Override
    public void visit(ExtractExpression eexpr) {
        throw new NotSupportException(eexpr);
    }

    @Override
    public void visit(IntervalExpression iexpr) {
        throw new NotSupportException(iexpr);
    }

    @Override
    public void visit(OracleHierarchicalExpression oexpr) {
        throw new NotSupportException(oexpr);
    }

    @Override
    public void visit(RegExpMatchOperator rexpr) {
        rexpr.getLeftExpression().accept(this);
        ValueContext left = context.pop();

        rexpr.getRightExpression().accept(this);
        ValueContext right = context.pop();

        QueryBuilder builder = null;
        if (left.isSingleColumn()) {
            String columnName = String.valueOf(left.getValue());
            String queryString = String.valueOf(right.getValue());
            switch (rexpr.getOperatorType()) {
                case MATCH_CASESENSITIVE: {
                    builder = QueryBuilders.regexpQuery(columnName, queryString).caseInsensitive(false);
                }
                break;
                case MATCH_CASEINSENSITIVE: {
                    builder = QueryBuilders.regexpQuery(columnName, queryString).caseInsensitive(true);
                }
                break;
                default: {
                    throw new EsException(StringUtil.format("{} syntax error", rexpr));
                }
            }

            if (builder != null) {
                ValueContext valueContext = new ValueContext(builder);
                context.push(valueContext);
                return;
            }
        }

        throw new EsException(StringUtil.format("{} syntax error", rexpr));
    }

    @Override
    public void visit(JsonExpression jsonExpr) {
        throw new NotSupportException(jsonExpr);
    }

    @Override
    public void visit(JsonOperator jsonExpr) {
        throw new NotSupportException(jsonExpr);
    }

    @Override
    public void visit(UserVariable var) {
        throw new NotSupportException(var);
    }

    @Override
    public void visit(NumericBind bind) {
        throw new NotSupportException(bind);
    }

    @Override
    public void visit(KeepExpression aexpr) {
        throw new NotSupportException(aexpr);
    }

    @Override
    public void visit(MySQLGroupConcat groupConcat) {
        throw new NotSupportException(groupConcat);
    }

    @Override
    public void visit(ExpressionList<?> expressionList) {
        if (CollectionUtil.isEmpty(expressionList)) return;

        Object[] values = new Object[expressionList.size()];
        for (int index = 0; index < expressionList.size(); index++) {
            Expression expression = expressionList.get(index);
            initExpression(expression);
            ValueContext valueContext = context.pop();
            if(!valueContext.isSingleValue()) {
                throw new EsException(StringUtil.format("{} syntax error", expression));
            }

            values[index] = valueContext.getValue();
        }

        ValueContext valueContext = new ValueContext(values);
        context.push(valueContext);
    }

    @Override
    public void visit(RowGetExpression rowGetExpression) {
        throw new NotSupportException(rowGetExpression);
    }

    @Override
    public void visit(RowConstructor rowConstructor) {
        throw new NotSupportException(rowConstructor);
    }

    @Override
    public void visit(OracleHint hint) {
        throw new NotSupportException(hint);
    }

    @Override
    public void visit(TimeKeyExpression timeKeyExpression) {
        throw new NotSupportException(timeKeyExpression);
    }

    @Override
    public void visit(DateTimeLiteralExpression literal) {
        throw new NotSupportException(literal);
    }

    @Override
    public void visit(NotExpression aThis) {
        initExpression(aThis.getExpression());
    }

    @Override
    public void visit(NextValExpression aThis) {
        throw new NotSupportException(aThis);
    }

    @Override
    public void visit(CollateExpression aThis) {
        throw new NotSupportException(aThis);
    }

    @Override
    public void visit(SimilarToExpression aThis) {
        throw new NotSupportException(aThis);
    }

    @Override
    public void visit(ArrayExpression aThis) {
        throw new NotSupportException(aThis);
    }

    @Override
    public void visit(ArrayConstructor aThis) {
        throw new NotSupportException(aThis);
    }

    @Override
    public void visit(VariableAssignment aThis) {
        throw new NotSupportException(aThis);
    }

    @Override
    public void visit(XMLSerializeExpr aThis) {
        throw new NotSupportException(aThis);
    }

    @Override
    public void visit(TimezoneExpression aThis) {
        throw new NotSupportException(aThis);
    }

    @Override
    public void visit(JsonAggregateFunction aThis) {
        throw new NotSupportException(aThis);
    }

    @Override
    public void visit(JsonFunction aThis) {
        throw new NotSupportException(aThis);
    }

    @Override
    public void visit(ConnectByRootOperator aThis) {
        throw new NotSupportException(aThis);
    }

    @Override
    public void visit(OracleNamedFunctionParameter aThis) {
        throw new NotSupportException(aThis);
    }

    @Override
    public void visit(AllColumns allColumns) {
    }

    @Override
    public void visit(AllTableColumns allTableColumns) {

    }

    @Override
    public void visit(AllValue allValue) {
        throw new NotSupportException(allValue);
    }

    @Override
    public void visit(IsDistinctExpression isDistinctExpression) {
        throw new NotSupportException(isDistinctExpression);
    }

    @Override
    public void visit(GeometryDistance geometryDistance) {
        throw new NotSupportException(geometryDistance);
    }

    @Override
    public void visit(Select selectBody) {
        throw new NotSupportException(selectBody);
    }

    @Override
    public void visit(TranscodingFunction transcodingFunction) {
        throw new NotSupportException(transcodingFunction);
    }

    @Override
    public void visit(TrimFunction trimFunction) {
        throw new NotSupportException(trimFunction);
    }

    @Override
    public void visit(RangeExpression rangeExpression) {
        throw new NotSupportException(rangeExpression);

    }

    @Override
    public void visit(TSQLLeftJoin tsqlLeftJoin) {
        throw new NotSupportException(tsqlLeftJoin);
    }

    @Override
    public void visit(TSQLRightJoin tsqlRightJoin) {
        throw new NotSupportException(tsqlRightJoin);
    }

    private void initExpression(Expression expression) {
        expression.accept(this);
    }

    private void initBinaryExpression(BinaryExpression binaryExpression) {
        binaryExpression.getLeftExpression().accept(this);
        binaryExpression.getRightExpression().accept(this);
    }
}
