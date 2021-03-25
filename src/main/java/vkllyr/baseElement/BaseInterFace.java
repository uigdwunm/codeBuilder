package vkllyr.baseElement;

import vkllyr.Enums.Permission;
import vkllyr.pageElement.Annotation;
import vkllyr.pageElement.ClassMethod;
import vkllyr.pageElement.ClassProperties;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BaseInterFace extends JavaClass {

    // 继承的接口
    private final Set<JavaClass> interFacesSet;
    // 类注解
    private final List<Annotation> annotationSet;
    // 方法列表
    private final List<ClassMethod> methodList;
    // 成员变量列表(接口可以有常量)
    private final List<ClassProperties> propertiesList;
    // 不太可能存在的尾巴(就是最后一个业内元素的注释部分)
    private String tail;

    public BaseInterFace(String packageName, String interFaceName) {
        this(interFaceName, packageName, Permission.PUBLIC);
    }

    public BaseInterFace(String name, String packageName, Permission permission) {
        super(name, packageName, permission);
        this.interFacesSet = new HashSet<>();
        this.annotationSet = new LinkedList<>();
        this.methodList = new LinkedList<>();
        this.propertiesList = new LinkedList<>();
    }


}
