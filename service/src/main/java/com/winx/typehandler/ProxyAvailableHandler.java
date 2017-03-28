package com.winx.typehandler;

import com.winx.enums.ProxyAvailable;
import com.winx.enums.ProxyType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author wangwenxiang
 * @create 2017-03-28.
 */
public class ProxyAvailableHandler extends BaseTypeHandler<ProxyAvailable> {

    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ProxyAvailable proxyAvailable, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, proxyAvailable.getCode());
    }

    public ProxyAvailable getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return ProxyAvailable.fromCode(resultSet.getInt(s));
    }

    public ProxyAvailable getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return ProxyAvailable.fromCode(resultSet.getInt(i));
    }

    public ProxyAvailable getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return ProxyAvailable.fromCode(callableStatement.getInt(i));
    }
}
