package xyz.litewars.litewars.api.database.hikaricp;

import java.sql.*;

public class DatabaseManager {
    private final HikariCPSupport support;

    public DatabaseManager(HikariCPSupport support) {
        this.support = support;
    }

    /**
     *
     * @param table : 查询的表名
     * @param line : 查询的行数
     * @param index : 索引
     * @param index_path : 索引的值
     * @return Object : 返回查询的结果
     * @throws SQLException : 可能会抛出SQL异常
     */
    public Object get (String table, String line, String index, String index_path) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = this.support.getDataSource().getConnection()) {
            preparedStatement = connection.prepareStatement("SELECT " + line + " FROM " + table + " WHERE " + index + " = ?;");
            preparedStatement.setString(1, index_path);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getObject(line);
            } else {
                return 0;
            }
        } finally {
            if (preparedStatement != null && resultSet != null) {
                resultSet.close();
                preparedStatement.close();
            }
        }
    }

    /**
     *
     * @param table : 查询的表名
     * @param line : 查询的行数
     * @param index : 索引
     * @param index_path : 索引的值
     * @return Integer : 返回查询的结果
     * @throws SQLException : 可能会抛出SQL异常
     */
    public int getInt (String table, String line, String index, String index_path) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = this.support.getDataSource().getConnection()) {
            preparedStatement = connection.prepareStatement("SELECT " + line + " FROM " + table + " WHERE " + index + " = ?;");
            preparedStatement.setString(1, index_path);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(line);
            } else {
                return 0;
            }
        } finally {
            if (preparedStatement != null && resultSet != null) {
                resultSet.close();
                preparedStatement.close();
            }
        }
    }

    /**
     *
     * @param table : 查询的表名
     * @param line : 查询的行数
     * @param index : 索引
     * @param index_path : 索引的值
     * @return Long : 返回查询的结果
     * @throws SQLException : 可能会抛出SQL异常
     */
    public long getLong (String table, String line, String index, String index_path) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = this.support.getDataSource().getConnection()) {
            preparedStatement = connection.prepareStatement("SELECT " + line + " FROM " + table + " WHERE " + index + " = ?;");
            preparedStatement.setString(1, index_path);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(line);
            } else {
                return 0L;
            }
        } finally {
            if (preparedStatement != null && resultSet != null) {
                resultSet.close();
                preparedStatement.close();
            }
        }
    }
    /**
     *
     * @param table : 查询的表名
     * @param line : 查询的行数
     * @param index : 索引
     * @param index_path : 索引的值
     * @return Float : 返回查询的结果
     * @throws SQLException : 可能会抛出SQL异常
     */
    public float getFloat (String table, String line, String index, String index_path) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = this.support.getDataSource().getConnection()) {
            preparedStatement = connection.prepareStatement("SELECT " + line + " FROM " + table + " WHERE " + index + " = ?;");
            preparedStatement.setString(1, index_path);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getFloat(line);
            } else {
                return 0.0F;
            }
        } finally {
            if (preparedStatement != null && resultSet != null) {
                resultSet.close();
                preparedStatement.close();
            }
        }
    }
    /**
     *
     * @param table : 查询的表名
     * @param line : 查询的行数
     * @param index : 索引
     * @param index_path : 索引的值
     * @return Boolean : 返回查询的结果
     * @throws SQLException : 可能会抛出SQL异常
     */
    public boolean getBoolean (String table, String line, String index, String index_path) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = this.support.getDataSource().getConnection()) {
            preparedStatement = connection.prepareStatement("SELECT " + line + " FROM " + table + " WHERE " + index + " = ?;");
            preparedStatement.setString(1, index_path);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(line) == 1;
            } else {
                return false;
            }
        } finally {
            if (preparedStatement != null && resultSet != null) {
                resultSet.close();
                preparedStatement.close();
            }
        }
    }
    /**
     *
     * @param table : 查询的表名
     * @param line : 查询的行数
     * @param index : 索引
     * @param index_path : 索引的值
     * @return String : 返回查询的结果
     * @throws SQLException : 可能会抛出SQL异常
     */
    public String getString (String table, String line, String index, String index_path) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try (Connection connection = this.support.getDataSource().getConnection()) {
            preparedStatement = connection.prepareStatement("SELECT " + line + " FROM " + table + " WHERE " + index + " = ?;");
            preparedStatement.setString(1, index_path);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(line);
            } else {
                return null;
            }
        } finally {
            if (preparedStatement != null && resultSet != null) {
                resultSet.close();
                preparedStatement.close();
            }
        }
    }

    public int insert(String table, String[] columns, Object[] values) throws SQLException {
        if (columns.length != values.length) {
            throw new IllegalArgumentException("columns and values must have the same length");
        }

        StringBuilder sql = new StringBuilder("INSERT INTO ").append(table).append(" (");
        for (int i = 0; i < columns.length; i++) {
            sql.append(columns[i]);
            if (i < columns.length - 1) {
                sql.append(", ");
            }
        }
        sql.append(") VALUES (");
        for (int i = 0; i < values.length; i++) {
            sql.append("?");
            if (i < values.length - 1) {
                sql.append(", ");
            }
        }
        sql.append(");");

        try (Connection connection = this.support.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setObject(i + 1, values[i]);
            }
            return preparedStatement.executeUpdate();
        }
    }

    /**
     * 更新指定表的数据
     *
     * @param table  表名
     * @param columns 列名数组
     * @param values 值数组
     * @param index  索引列名
     * @param indexValue 索引值
     * @return 更新的行数
     * @throws SQLException 可能会抛出SQL异常
     */
    public int update(String table, String[] columns, Object[] values, String index, Object indexValue) throws SQLException {
        if (columns.length != values.length) {
            throw new IllegalArgumentException("columns and values must have the same length");
        }

        StringBuilder sql = new StringBuilder("UPDATE ").append(table).append(" SET ");
        for (int i = 0; i < columns.length; i++) {
            sql.append(columns[i]).append(" = ?");
            if (i < columns.length - 1) {
                sql.append(", ");
            }
        }
        sql.append(" WHERE ").append(index).append(" = ?;");

        try (Connection connection = this.support.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < values.length; i++) {
                preparedStatement.setObject(i + 1, values[i]);
            }
            preparedStatement.setObject(columns.length + 1, indexValue);
            return preparedStatement.executeUpdate();
        }
    }

    /**
     * 删除指定表的数据
     *
     * @param table  表名
     * @param index  索引列名
     * @param indexValue 索引值
     * @return 删除的行数
     * @throws SQLException 可能会抛出SQL异常
     */
    public int delete(String table, String index, Object indexValue) throws SQLException {
        String sql = "DELETE FROM " + table + " WHERE " + index + " = ?;";

        try (Connection connection = this.support.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, indexValue);
            return preparedStatement.executeUpdate();
        }
    }

    /**
     * 创建一个表
     *
     * @param createTableSql 创建表的SQL语句
     * @throws SQLException 可能会抛出SQL异常
     */
    public void createTable(String createTableSql) throws SQLException {
        try (Connection connection = this.support.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(createTableSql)) {
            preparedStatement.execute();
        }
    }

    /**
     * 检查数据库中是否存在指定的表
     *
     * @param tableName 表名
     * @return 如果表存在则返回true，否则返回false
     * @throws SQLException 可能会抛出SQL异常
     */
    public boolean tableExists(String tableName) throws SQLException {
        try (Connection connection = this.support.getDataSource().getConnection()) {
            DatabaseMetaData dbMetaData = connection.getMetaData();
            try (ResultSet rs = dbMetaData.getTables(null, null, tableName, new String[]{"TABLE"})) {
                return rs.next();
            }
        }
    }

    /**
     * 检查数据库中是否已有数据
     *
     * @param checkColunms 需要检查的行数
     * @param table 需要检查的表
     * @return 如果数据存在则返回true,否则返回false
     */
    public boolean checkDataExists (String table, String checkColunms, Object data) {
        //SELECT * FROM FinanceSystem WHERE Player_UUID = ?
        try (Connection connection = support.getDataSource().getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE " + checkColunms + " = ?");
            statement.setObject(1, data);
            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public HikariCPSupport getSupport() {
        return support;
    }
}
