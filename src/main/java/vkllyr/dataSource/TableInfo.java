package vkllyr.dataSource;

import java.util.Date;
import java.util.List;

/**
 * Description TODO
 *
 * @author zhaolaiyuan
 * @version 1.0
 * @Date 2019年2019/6/24 15:26
 **/
public final class TableInfo {
    // 表名
    private String tableName;

    // 实体类名(大驼峰式命名)
    private String modelName;

    // 主键
    private ColumnInfo priColumn;

    // 主键是否自增
    private boolean isAutoInc;

    // 所有列
    private List<ColumnInfo> columnInfoList;

    // 表注释
    private String tableComment;

    // 表创建时间
    private Date createTime;

    public TableInfo(String tableName) {
        super();
        this.tableName = tableName;
        StringBuilder modelName = new StringBuilder();
        String[] tableNameArr = tableName.split("_");
        for (int i = 0; i < tableNameArr.length ; i++) {
            if (i == 0 && tableNameArr[i].equals("t")) {
                continue;
            }
            modelName.append(tableNameArr[i].substring(0, 1).toUpperCase());
            modelName.append(tableNameArr[i].substring(1).toLowerCase());
        }
        this.modelName = modelName.toString();
    }

    public String getTableName() {
        return tableName;
    }

    public String getModelName() {
        return modelName;
    }

    public ColumnInfo getPriColumn() {
        return priColumn;
    }

    public void setPriColumn(ColumnInfo priColumn) {
        this.priColumn = priColumn;
    }

    public boolean isAutoInc() {
        return isAutoInc;
    }

    public void setAutoInc(boolean autoInc) {
        isAutoInc = autoInc;
    }

    public List<ColumnInfo> getColumnInfoList() {
        return columnInfoList;
    }

    public void setColumnInfoList(List<ColumnInfo> columnInfoList) {
        this.columnInfoList = columnInfoList;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "TableInfo{" +
                "tableName='" + tableName + '\'' +
                ", modelName='" + modelName + '\'' +
                ", priColumn=" + priColumn +
                ", isAutoInc=" + isAutoInc +
                ", columnInfoList=" + columnInfoList +
                ", tableComment='" + tableComment + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
