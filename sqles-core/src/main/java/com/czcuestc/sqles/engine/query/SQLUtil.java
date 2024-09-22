package com.czcuestc.sqles.engine.query;

import com.czcuestc.sqles.common.exceptions.NotSupportException;
import com.czcuestc.sqles.common.exceptions.SystemException;
import com.czcuestc.sqles.common.util.StringUtil;
import com.czcuestc.sqles.engine.context.Context;
import com.czcuestc.sqles.engine.context.Parameters;
import com.czcuestc.sqles.engine.context.SelectContext;
import com.czcuestc.sqles.engine.context.SqlType;
import com.czcuestc.sqles.engine.delete.EsDeleteStatementVisitor;
import com.czcuestc.sqles.engine.domain.Result;
import com.czcuestc.sqles.engine.domain.SqlResult;
import com.czcuestc.sqles.engine.insert.EsInsertStatementVisitor;
import com.czcuestc.sqles.engine.select.EsSelectStatementVisitor;
import com.czcuestc.sqles.engine.update.EsUpdateStatementVisitor;
import com.czcuestc.sqles.util.EntityUtil;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.Statements;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.update.Update;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class SQLUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SQLUtil.class);

    public static SqlResult execute(String datasource, String sql) {
        try {
            Statements statements = CCJSqlParserUtil.parseStatements(sql);
            if (statements.size() > 1) {
                throw new NotSupportException(sql);
            }

            beforeMethodExecution();
            Statement statement = statements.getStatements().get(0);
            if (statement instanceof PlainSelect) {
                SelectContext context = new SelectContext();
                context.setDatasource(datasource);
                EsSelectStatementVisitor selectStatementVisitor = new EsSelectStatementVisitor(context);
                statement.accept(selectStatementVisitor);
                SqlResult sqlResult = new SqlResult();
                Result result = context.getResult();
                sqlResult.setSqlType(SqlType.QUERY);
                sqlResult.setResult(result);
                sqlResult.setCount(context.getCount());
                return sqlResult;
            }

            if (statement instanceof Insert) {
                Context context = new Context();
                context.setDatasource(datasource);
                EsInsertStatementVisitor insertStatementVisitor = new EsInsertStatementVisitor(context);
                statement.accept(insertStatementVisitor);
                SqlResult sqlResult = new SqlResult();
                sqlResult.setSqlType(SqlType.INSERT);
                sqlResult.setCount(context.getCount());
                return sqlResult;
            }


            if (statement instanceof Delete) {
                Context context = new Context();
                context.setDatasource(datasource);
                EsDeleteStatementVisitor deleteStatementVisitor = new EsDeleteStatementVisitor(context);
                statement.accept(deleteStatementVisitor);
                SqlResult sqlResult = new SqlResult();
                sqlResult.setSqlType(SqlType.DELETE);
                sqlResult.setCount(context.getCount());
                return sqlResult;
            }
        } catch (Exception e) {
            LOGGER.error("sql {} execute error", sql, e);
            throw new SystemException(StringUtil.format("sql {} execute error", sql), e);
        } finally {
            afterMethodExecution();
        }

        throw new SystemException(StringUtil.format("sql {} syntax error", sql));
    }

    public static SqlResult execute(String datasource, String sql, Parameters parameters) {
        try {
            Statements statements = CCJSqlParserUtil.parseStatements(sql);
            if (statements.size() > 1) {
                throw new NotSupportException(sql);
            }

            beforeMethodExecution();
            Statement statement = statements.getStatements().get(0);
            if (statement instanceof PlainSelect) {
                SelectContext context = new SelectContext();
                context.setDatasource(datasource);
                context.setParameters(parameters);
                EsSelectStatementVisitor selectStatementVisitor = new EsSelectStatementVisitor(context);
                statement.accept(selectStatementVisitor);
                SqlResult sqlResult = new SqlResult();
                Result result = context.getResult();
                sqlResult.setSqlType(SqlType.QUERY);
                sqlResult.setResult(result);
                sqlResult.setCount(context.getCount());
                return sqlResult;
            }

            if (statement instanceof Insert) {
                Context context = new Context();
                context.setDatasource(datasource);
                context.setParameters(parameters);
                EsInsertStatementVisitor insertStatementVisitor = new EsInsertStatementVisitor(context);
                statement.accept(insertStatementVisitor);
                SqlResult sqlResult = new SqlResult();
                sqlResult.setSqlType(SqlType.INSERT);
                sqlResult.setCount(context.getCount());
                return sqlResult;
            }


            if (statement instanceof Delete) {
                Context context = new Context();
                context.setDatasource(datasource);
                context.setParameters(parameters);
                EsDeleteStatementVisitor deleteStatementVisitor = new EsDeleteStatementVisitor(context);
                statement.accept(deleteStatementVisitor);
                SqlResult sqlResult = new SqlResult();
                sqlResult.setSqlType(SqlType.DELETE);
                sqlResult.setCount(context.getCount());
                return sqlResult;
            }

            if (statement instanceof Update) {
                Context context = new Context();
                context.setDatasource(datasource);
                context.setParameters(parameters);
                EsUpdateStatementVisitor updateStatementVisitor = new EsUpdateStatementVisitor(context);
                statement.accept(updateStatementVisitor);
                SqlResult sqlResult = new SqlResult();
                sqlResult.setSqlType(SqlType.UPDATE);
                sqlResult.setCount(context.getCount());
                return sqlResult;
            }
        } catch (Exception e) {
            LOGGER.error("sql {} execute error", sql, e);
            throw new SystemException(StringUtil.format("sql {} execute error", sql), e);
        } finally {
            afterMethodExecution();
        }

        throw new SystemException(StringUtil.format("sql {} syntax error", sql));
    }

    public static <T> List<T> search(String datasource, String sql) {
        try {
            Statements statements = CCJSqlParserUtil.parseStatements(sql);
            if (statements.size() > 1) {
                throw new NotSupportException(sql);
            }

            beforeMethodExecution();
            Statement statement = statements.getStatements().get(0);

            SelectContext context = new SelectContext();
            context.setDatasource(datasource);
            EsSelectStatementVisitor selectStatementVisitor = new EsSelectStatementVisitor(context);
            statement.accept(selectStatementVisitor);

            if (selectStatementVisitor.getOutput() != null) {
                List<T> list = new ArrayList<>((int) selectStatementVisitor.getOutput().getTotalHits().value);
                for (SearchHit searchHit : selectStatementVisitor.getOutput()) {
                    Object entity = EntityUtil.toEntity(searchHit.getSourceAsString(), context.getIndexInfo().getEntityInfo());
                    EntityUtil.setScore(context.getIndexInfo().getEntityInfo(), entity, searchHit.getScore());
                    EntityUtil.setHighlight(context.getIndexInfo().getEntityInfo(), entity, searchHit.getHighlightFields());
                    list.add((T) entity);
                }
                return list;
            }

            return null;
        } catch (Exception e) {
            LOGGER.error("sql {} execute error", sql, e);
            throw new SystemException(StringUtil.format("sql {} execute error", sql), e);
        } finally {
            afterMethodExecution();
        }
    }

    public static  String sqlToDsl(String datasource,String sql) {
        try {
            Statements statements = CCJSqlParserUtil.parseStatements(sql);
            if (statements.size() > 1) {
                throw new NotSupportException(sql);
            }

            beforeMethodExecution();
            Statement statement = statements.getStatements().get(0);
            SelectContext context = new SelectContext();
            context.setDatasource(datasource);
            EsSelectStatementVisitor selectStatementVisitor = new EsSelectStatementVisitor(context);
            statement.accept(selectStatementVisitor);
            return context.getSearchSourceBuilder().toString();
        } catch (Exception e) {
            LOGGER.error("sql {} execute error", sql, e);
            throw new SystemException(StringUtil.format("sql {} execute error", sql), e);
        } finally {
            afterMethodExecution();
        }
    }

    private static void beforeMethodExecution() {
    }

    private static void afterMethodExecution() {

    }
}
