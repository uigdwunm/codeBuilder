package vkllyr.dataSource;


import vkllyr.utils.StringUtils;

public enum JavaType {
    INTEGER("Integer", "int",
            "import org.apache.commons.lang3.ObjectUtils;" + System.lineSeparator() + "import org.apache.commons.lang3.math.NumberUtils;" + System.lineSeparator()),
    LONG("Long", "long",
            "import org.apache.commons.lang3.ObjectUtils;" + System.lineSeparator() + "import org.apache.commons.lang3.math.NumberUtils;" + System.lineSeparator()),
    DATE("LocalDateTime", "",
            "import java.time.LocalDateTime;" + System.lineSeparator()),
    STRING("String", "String",
            "import org.apache.commons.lang3.StringUtils;" + System.lineSeparator()),
    FLOAT("Float", "float",
            "import org.apache.commons.lang3.ObjectUtils;" + System.lineSeparator() + "import org.apache.commons.lang3.math.NumberUtils;" + System.lineSeparator()),
    BOOLEAN("Boolean", "boolean",
            "import org.apache.commons.lang3.BooleanUtils;" + System.lineSeparator()),
    BIGDECIMAL("BigDecimal", "BigDecimal",
            "import java.math.BigDecimal;" + System.lineSeparator()),
    OTHER(StringUtils.Empty, "Object",
            StringUtils.Empty);

    private String typeName;
    private String basicTypeName;
    private String importLine;

    JavaType(String typeName, String basicTypeName, String importLine) {
        this.typeName = typeName;
        this.basicTypeName = basicTypeName;
        this.importLine = importLine;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getBasicTypeName() {
        return basicTypeName;
    }

    public String getImportLine() {
        return importLine;
    }

    public static JavaType seek(String dataType, int length) throws Exception {
        switch (dataType) {
            case "varchar":
            case "text":
                return STRING;
            case "bigint":
                return LONG;
            case "date":
            case "datetime":
                return DATE;
            case "decimal":
                return BIGDECIMAL;
            case "float":
                return FLOAT;
            case "int":
                return INTEGER;
            case "tinyint":
//                if (length == 1) {
//                    return BOOLEAN;
//                } else {
                    return INTEGER;
//                }
            default:
                System.err.println("未知类型：" + dataType);
                throw new Exception("未知类型");
        }
    }
}
