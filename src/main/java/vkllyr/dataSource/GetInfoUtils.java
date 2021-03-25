package vkllyr.dataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import static vkllyr.Main.*;

/**
 * Description TODO
 *
 * @author zhaolaiyuan
 * @version 1.0
 * @Date 2019年2019/6/24 16:18
 **/
public class GetInfoUtils {

    /**
     * Description TODO 所有列
     *
     * @author zhaolaiyuan
     * @Date 2019/7/15 15:56
     **/
    public Map<String, ColumnInfo> columnInfoMap = new HashMap<>();


    public static void main(String[] args) throws Exception {
        TableInfo t_bills = GetInfoUtils.getInfo("t_bills", new String[]{"is_delete", "gmt_create", "gmt_modified"});
        System.out.println(t_bills);
    }

    public static TableInfo getInfo(String tableName, String[] ignoreColumnArr) throws Exception {
        TableInfo ti = new TableInfo(tableName);
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, userName, passWord);
        Statement statement = conn.createStatement();
        String sql =
                "SELECT " +
                // 1 表名
                "t.table_name, " +
                // 2 表创建时间
                "t.create_time, " +
                // 3 表注释
                "t.table_comment " +
                "FROM information_schema.tables t " +
                "WHERE t.table_type = 'BASE TABLE' " +
                "  AND t.TABLE_NAME = '" + tableName + '\'' +
                "  AND t.table_schema = 'haocheemai'";
        ResultSet rs = statement.executeQuery(sql);
        rs.next();
        // 表创建时间
        ti.setCreateTime(rs.getTimestamp(2));
        // 表注释
        ti.setTableComment(rs.getString(3));

        sql = " SELECT " +
                // 1  表名
                "       c.table_name, " +
                // 2  列名
                "       c.column_name, " +
                // 3  排序字段
                "       c.ordinal_position, " +
                // 4  默认值
                "       c.column_default, " +
                // 5  是否可以为空
                "       c.is_nullable, " +
                // 6  类型
                "       c.data_type, " +
                // 7  字符类型长度
                "       c.character_maximum_length, " +
                // 8  数值类型长度
                "       c.numeric_precision, " +
                // 9  数值类型小数点后长度
                "       c.numeric_scale, " +
                // 10 列的键（主键PRI，联合键MUL，唯一UNI）
                "       c.column_key, " +
                // 11 标志自动增长的auto_increment，其他的不知道干啥的
                "       c.extra, " +
                // 12 备注
                "       c.column_comment " +
                " FROM information_schema.columns c " +
                " WHERE c.table_schema = 'haocheemai' " +
                " AND c.table_name = '" + tableName + '\'' +
                " ORDER BY field(c.column_key, 'PRI') DESC, c.ordinal_position";
        rs = statement.executeQuery(sql);
        List<ColumnInfo> columnInfoList = new ArrayList<>();
        while (rs.next()) {
            String columnName = rs.getString(2);
            if (Arrays.asList(ignoreColumnArr).contains(columnName)) {
                continue;
            }
            // 列名
            ColumnInfo ci = new ColumnInfo(columnName);
            // 是否可以为空
            ci.setNull("YES".equals(rs.getString(5)));
            // 列注释
            ci.setColumnComment(rs.getString(12));
            // 类型
            ci.setJavaType(JavaType.seek(rs.getString(6), rs.getInt(8)));
            columnInfoList.add(ci);
            // 是否主键
            if ("PRI".equals(rs.getString(10))) {
                ci.setPrimaryKey(true);
                // 主键
                ti.setPriColumn(ci);
                // 是否自动增长
                ti.setAutoInc("auto_increment".equals(rs.getString(11)));
            }
        }
        // 添加所有列
        ti.setColumnInfoList(Collections.unmodifiableList(columnInfoList));

        return ti;
    }
}
