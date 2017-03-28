package com.winx.typehandler;

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
public class ProxyTypeHandler extends BaseTypeHandler<ProxyType> {
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ProxyType proxyType, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, proxyType.getCode());
    }

    public ProxyType getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return ProxyType.fromCode(resultSet.getInt(s));
    }

    public ProxyType getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return ProxyType.fromCode(resultSet.getInt(i));
    }

    public ProxyType getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return ProxyType.fromCode(callableStatement.getInt(i));
    }
}
