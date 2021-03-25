package vkllyr.pageElement;

import vkllyr.baseElement.JavaType;

import java.util.Set;

public interface WithImportClass {

    Set<JavaType> getImportClassSet();

    default void addImportClass(JavaType importClass) {
        this.getImportClassSet().add(importClass);
    }

    default void merge(Set<JavaType> importClassSet) {
        this.getImportClassSet().addAll(importClassSet);
    }

    /**
     * Description 收集所有类型，包括泛型的类型也会递归收集
     *
     * @author zhaolaiyuan
     * Date 2020/11/19 13:59
     **/
    default void collectImportClass(JavaType javaType) {
        getImportClassSet().add(javaType);
        if (javaType.getGenericityList().size() != 0) {
            for (JavaType type : javaType.getGenericityList()) {
                collectImportClass(type);
            }
        }
    }


}
