package vkllyr.pageElement;

import vkllyr.Enums.Permission;
import vkllyr.baseElement.JavaType;
import vkllyr.utils.StringLineBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Description 业内元素之成员变量
 *
 * @author zhaolaiyuan
 * Date 2020/11/17 14:25
 **/
public final class ClassProperties implements WithImportClass, WithComment {
    // 方法名称
    private final String name;
    // 访问权限
    private final Permission permission;
    // 是否是Fianl的
    private final boolean isFianl;
    // 是否是static
    private final boolean isStatic;
    // 类型
    private final JavaType type;
    // 值
    private String value;
    // 要导入的包
    private final Set<JavaType> importClassSet = new HashSet<>();
    // 注释
    private StringLineBuilder comment;

    public ClassProperties(String name, JavaType type) {
        this.name = name;
        this.type = type;
        this.permission = Permission.PRIVATE;
        this.isFianl = false;
        this.isStatic = false;
    }

    @Override
    public void setComment(String comment) {
        this.comment = new StringLineBuilder(comment);
    }

    @Override
    public void setComment(StringLineBuilder comment) {
        this.comment = comment;
    }

    @Override
    public StringLineBuilder getComment() {
        return this.comment;
    }

    @Override
    public Set<JavaType> getImportClassSet() {
        return null;
    }
}
