package vkllyr.baseElement;

import vkllyr.Enums.Permission;
import vkllyr.pageElement.WithComment;
import vkllyr.utils.StringLineBuilder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Description java类、接口、枚举、注解的总的父类
 *
 * @author zhaolaiyuan
 * Date 2020/11/17 15:40
 **/
public abstract class JavaClass implements WithComment {

    // 类名称
    private final String name;
    // 包名称
    private final String packageName;
    // 访问权限
    private final Permission permission;
    // 类注释
    private StringLineBuilder commant;
    // 文件上方需要导入的包，<类名，类型>
    private final Map<String, JavaType> importClassMap;
    // 这个set里面是需要写需要全路径类名的
    private final Set<JavaType> allNameSet;
    // 不太可能存在的尾巴(就是最后一个页内元素的注释部分)
    private String tail;

    protected JavaClass(String name, String packageName) {
        this.name = name;
        this.packageName = packageName;
        this.permission = Permission.PUBLIC;
        this.importClassMap = new HashMap<>();
        this.allNameSet = new HashSet<>();
    }

    protected JavaClass(String name, String packageName, Permission permission) {
        this.name = name;
        this.packageName = packageName;
        this.permission = permission;
        this.importClassMap = new HashMap<>();
        this.allNameSet = new HashSet<>();
    }


    public String getName() {
        return this.name;
    }

    public String getPackageName() {
        return this.packageName;
    }

    /**
     * Description 可以以后校验用
     *
     * @author zhaolaiyuan
     * Date 2020/11/17 16:28
     **/
    public Permission getPermission() {
        return this.permission;
    }

    /**
     * Description 得到全路径类名
     *
     * @author zhaolaiyuan
     * Date 2020/11/17 15:42
     **/
    public String getReferenceName() {
        return getPackageName() + '.' + getName();
    }

    @Override
    public void setComment(String comment) {
        this.commant = new StringLineBuilder(comment);
    }

    @Override
    public void setComment(StringLineBuilder comment) {
        this.commant = comment;
    }

    @Override
    public StringLineBuilder getComment() {
        return this.commant;
    }


    public void addImportClass(JavaType javaType) {
        this.importClassMap.compute(javaType.getName(), (k, v) -> {
            if (v != null) {
                // 如果已经有同名的，就放到全路径类名组
                this.allNameSet.add(javaType);
                // 设置当前页面用到的名称为全路径类名
                javaType.setContent(javaType.getPackageName() + '.' + javaType.getName());
            } else {
                v = javaType;
                javaType.setContent(javaType.getName());
            }
            return v;
        });

        // 如果还有泛型
        if (javaType.getGenericityList().size() != 0) {
            StringLineBuilder content = new StringLineBuilder(javaType.getContent());
            content.append('<');
            for (JavaType type : javaType.getGenericityList()) {
                this.addImportClass(type);
            }
            content.append(javaType.getGenericityList(), ", ", JavaType::getContent);
            content.append('>');
            javaType.setContent(content.getLine());
        }
    }

}
