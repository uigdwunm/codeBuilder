package vkllyr.baseElement;

import vkllyr.Enums.Permission;
import vkllyr.pageElement.*;

import java.util.*;

/**
 * Description
 * 内部类和同文件多个类没有考虑
 * 泛型没有考虑
 *
 * @author zhaolaiyuan
 * Date 2020/11/17 15:19
 **/
public class BaseClass extends JavaClass {

    // 是否是Fianl的
    private final boolean isFianl;
    // 继承的类
    private final JavaType extendClass;
    // 实现的接口
    private final Set<JavaClass> interFacesSet;
    // 类注解
    private final List<Annotation> annotationSet;
    // 成员变量列表
    private final List<ClassProperties> propertiesList;
    // 方法列表
    private final List<ClassMethod> methodList;

    public BaseClass(String packageName, String className) {
        this(className, packageName, Permission.PUBLIC, true, null);
    }

    public BaseClass(String name, String packageName, Permission permission, boolean isFianl, JavaType extendClass) {
        super(name, packageName, permission);
        this.isFianl = isFianl;
        this.extendClass = extendClass;
        this.annotationSet = new LinkedList<>();
        this.propertiesList = new LinkedList<>();
        this.methodList = new LinkedList<>();
        this.interFacesSet = new HashSet<>();
    }

}
