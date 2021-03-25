package vkllyr.dataSource;

/**
 * Description 从数据库查出的信息
 *
 * @author zhaolaiyuan
 * @version 1.0
 * @Date 2019年2019/6/24 15:26
 **/
public class ColumnInfo {

    // 列名
    private String columnName;

    // 列注释
    private String columnComment;

    // 列类型
    private JavaType javaType;

    // 是否可以为空
    private boolean isNull;

    // 是否主键
    private boolean isPrimaryKey = false;

    public ColumnInfo(String columnName) throws Exception {
        super();
        this.columnName = columnName;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public ColumnInfo setColumnComment(String columnComment) {
        this.columnComment = columnComment == null ? this.columnComment : columnComment;
        return this;
    }

    public JavaType getJavaType() {
        return javaType;
    }

    public ColumnInfo setJavaType(JavaType javaType) {
        this.javaType = javaType == null ? this.javaType : javaType;
        return this;
    }

    public boolean isNull() {
        return isNull;
    }

    public ColumnInfo setNull(Boolean aNull) {
        isNull = aNull == null ? this.isNull : aNull;
        return this;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName == null ? this.columnName : columnName;
    }

    @Override
    public String toString() {
        return "ColumnInfo{" +
                "columnName='" + columnName + '\'' +
                ", columnComment='" + columnComment + '\'' +
                ", javaType=" + javaType +
                ", isNull=" + isNull +
                ", isPrimaryKey=" + isPrimaryKey +
                '}';
    }
}
