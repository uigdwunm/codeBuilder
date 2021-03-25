package vkllyr.pageElement;

import vkllyr.baseElement.JavaType;
import vkllyr.utils.StringLineBuilder;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Description 页内元素之注解
 *
 * @author zhaolaiyuan
 * Date 2020/11/17 17:06
 **/
public final class Annotation implements WithImportClass {
    // 注解名，不带‘@’
    private final String name;
    // 所属的包
    private final String packageName;
    // 要导入的包
    private Set<JavaType> importClassSet;
    // 属性列表
    private final List<Param> paramList;

    public Annotation(String annotationName, String packageName) {
        this.name = annotationName;
        this.packageName = packageName;
        this.importClassSet = new HashSet<>();
        this.paramList = new LinkedList<>();
        // 先把自己加到要导入的包中
        importClassSet.add(new JavaType(name, packageName));
    }

    @Override
    public Set<JavaType> getImportClassSet() {
        return this.importClassSet;
    }

    @Override
    public void addImportClass(JavaType importClass) {
        importClassSet.add(importClass);
    }

    public String getContent() {
        return this.getLineBuilder().toString();
    }

    private StringLineBuilder getLineBuilder() {
        StringLineBuilder slb = new StringLineBuilder();
        // 没有参数的情况
        if (paramList.size() == 0) {
            slb.append('@').append(name).endWithoutLine();
            return slb;
        }

        // 单值的情况
        Param param;
        if (paramList.size() == 1 && (param = paramList.get(0)).name == null) {
            slb.append('@').append(name)
                    .append('(').append(param.value).append(')')
                    .endWithoutLine();
            return slb;
        }

        // 多个参数的情况
        slb.append('@').append(name)
                .append('(').append(paramList, ", ").append(')')
                .endWithoutLine();
        return slb;
    }

    /**
     * Description 专用于注解的内部类参数
     *
     * @author zhaolaiyuan
     * Date 2020/11/17 17:07
     **/
    public static class Param {
        private final String name;
        private final String value;

        public Param(String name, String value) {
            this.name = name;
            this.value = value;
        }

        private Param(String value) {
            this.name = null;
            this.value = value;
        }

        @Override
        public String toString() {
            return this.name + " = " + value;
        }
    }
}
