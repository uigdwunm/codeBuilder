package vkllyr.pageElement;

import vkllyr.Config;
import vkllyr.Enums.Permission;
import vkllyr.baseElement.JavaType;
import vkllyr.utils.StringLineBuilder;
import vkllyr.utils.StringUtils;

import java.util.*;

/**
 * Description 业内元素之方法
 *
 * @author zhaolaiyuan
 * Date 2020/11/17 14:25
 **/
public final class ClassMethod implements WithImportClass, WithComment {
    // 方法名称
    private final String name;
    // 访问权限
    private final Permission permission;
    // 是否是Fianl的
    private final boolean isFianl;
    // 是否是static
    private final boolean isStatic;
    // 返回值类型
    private final JavaType returnType;
    // 方法注解
    private final List<Annotation> annotationSet = new LinkedList<>();
    // 方法参数
    private final Deque<Param> paramList = new LinkedList<>();
    // 要导入的包
    private final Set<JavaType> importClassSet = new HashSet<>();
    // 方法内容
    private StringLineBuilder methodBody;
    // 注释
    private StringLineBuilder comment;

    // 算是缓存，懒加载，防止重复生成
    private StringLineBuilder commonContent;

    public ClassMethod(String name, JavaType returnType) {
        this.name = name;
        this.permission = Permission.PUBLIC;
        this.isFianl = true;
        this.isStatic = true;
        this.returnType = returnType;
        this.importClassSet.add(returnType);
    }

    public void addParam(Param param) {
        this.paramList.add(param);
        this.collectImportClass(param.getType());
    }

    @Override
    public Set<JavaType> getImportClassSet() {
        return this.importClassSet;
    }

    public List<Annotation> getAnnotationSet() {
        return annotationSet;
    }

    public StringLineBuilder getMethodBody() {
        return methodBody;
    }

    public void setMethodBody(StringLineBuilder methodBody) {
        this.methodBody = methodBody;
    }

    public String getName() {
        return name;
    }

    public Permission getPermission() {
        return permission;
    }

    public boolean isFianl() {
        return isFianl;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public JavaType getReturnType() {
        return returnType;
    }

    public Deque<Param> getParamList() {
        return paramList;
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
        return comment;
    }

    /**
     * Description 生成普通的内容，给普通java类用的那种
     *
     * @author zhaolaiyuan
     * Date 2020/11/19 9:14
     **/
    public StringLineBuilder getContentAsCommon(Config config) {
        if (commonContent != null) {
            return commonContent;
        }
        StringLineBuilder slb = new StringLineBuilder();
        // 获取文档注释
        slb.merge(this.formatToDocumentationComment(config));
        // 获取方法注解
        for (Annotation annotation : annotationSet) {
            slb.appendLine(annotation.getContent());
            // 收集要导入的包
            this.importClassSet.addAll(annotation.getImportClassSet());
        }
        // 访问权限
        slb.append(permission).append(' ');
        // 是否final方法
        if (this.isFianl) {
            slb.append("final").append(' ');
        }
        // 是否静态方法
        if (this.isStatic) {
            slb.append("static").append(' ');
        }
        // 返回值
        slb.append(returnType.getContent()).append(' ');
        this.importClassSet.add(returnType);
        // 方法名
        slb.append(name).append('(');
        // 方法参数
        slb.append(paramList, ", ", x -> {
            this.importClassSet.add(x.getType());
            return x.getContent();
        });
        slb.append(") {");
        slb.line();

        // 方法体
        slb.merge(methodBody);
        // 结束
        slb.appendLine('}');
        slb.tabEnd();

        this.commonContent = slb;
        return slb;
    }

    /**
     * Description 生成给接口用的方法体，注意default在接口里是特殊的意义
     *
     * @author zhaolaiyuan
     * Date 2020/11/19 13:34
     **/
    public StringLineBuilder getContentAsInterface(Config config) {
        if (commonContent != null) {
            return commonContent;
        }
        StringLineBuilder slb = new StringLineBuilder();
        // 获取文档注释
        slb.merge(this.formatToDocumentationComment(config));
        // 获取注解
        for (Annotation annotation : annotationSet) {
            slb.appendLine(annotation.getContent());
            // 收集要导入的包
            this.importClassSet.addAll(annotation.getImportClassSet());
        }
        // 访问权限不用填，接口方法默认public
        if (Permission.DEFAULT.equals(permission)) {
            // 接口的默认方法
            slb.append(permission).append(' ');
            return interfaceMethod(slb);
        }
        // 是否静态方法
        if (this.isStatic) {
            slb.append("static").append(' ');
            return interfaceMethod(slb);
        }
        // 返回值
        slb.append(returnType.getContent()).append(' ');
        // 方法名
        slb.append(name).append('(');
        // 方法参数
        slb.append(paramList, ", ", x -> {
            this.importClassSet.add(x.getType());
            return x.getContent();
        });
        slb.append(");");
        slb.tabEnd();

        this.commonContent = slb;
        return slb;
    }

    /**
     * Description 默认或静态接口方法，从这里继续处理
     *
     * @author zhaolaiyuan
     * Date 2020/11/19 14:07
     **/
    private StringLineBuilder interfaceMethod(StringLineBuilder slb) {
        // 返回值
        slb.append(returnType.getContent()).append(' ');
        // 方法名
        slb.append(name).append('(');
        // 方法参数
        slb.append(paramList, ", ", x -> {
            this.importClassSet.add(x.getType());
            return x.getContent();
        });
        slb.append(") {");
        slb.line();

        // 方法体
        slb.merge(methodBody);
        // 结束
        slb.appendLine('}');
        slb.tabEnd();

        this.commonContent = slb;
        return slb;
    }

    /**
     * Description 生成抽象方法
     *
     * @author zhaolaiyuan
     * Date 2020/11/19 14:03
     **/
    public StringLineBuilder getContentAsAbstract(Config config) {
        if (commonContent != null) {
            return commonContent;
        }
        StringLineBuilder slb = new StringLineBuilder();
        // 获取文档注释
        slb.merge(this.formatToDocumentationComment(config));
        // 获取注解
        for (Annotation annotation : annotationSet) {
            slb.appendLine(annotation.getContent());
            // 收集要导入的包
            this.importClassSet.addAll(annotation.getImportClassSet());
        }
        // 访问权限
        if (Permission.PRIVATE.equals(permission)) {
            throw new RuntimeException("抽象方法不能为private");
        }
        slb.append(permission).append(' ');

        // 返回值
        slb.append(returnType.getContent()).append(' ');
        // 方法名
        slb.append(name).append('(');
        // 方法参数
        slb.append(paramList, ", ", x -> {
            this.importClassSet.add(x.getType());
            return x.getContent();
        });
        slb.append(");");
        slb.tabEnd();

        this.commonContent = slb;
        return slb;
    }

    /**
     * Description 专用于方法参数的内部类
     *
     * @author zhaolaiyuan
     * Date 2020/11/18 10:06
     **/
    private static class Param implements WithImportClass {
        private final String name;
        private final JavaType type;
        // 方法参数是可以有值的
        private final String value;
        // 参数注解
        private final List<Annotation> annotationSet;

        private final Set<JavaType> importClassSet;

        public Param(String name, JavaType type) {
            this.name = name;
            this.type = type;
            this.value = null;
            this.annotationSet = new LinkedList<>();
            this.importClassSet = new HashSet<>();
            this.importClassSet.add(type);
        }

        public Param(String name, JavaType type, String value) {
            this.name = name;
            this.type = type;
            this.value = value;
            this.annotationSet = new LinkedList<>();
            this.importClassSet = new HashSet<>();
            this.importClassSet.add(type);
        }

        public String getName() {
            return name;
        }

        public JavaType getType() {
            return type;
        }

        public String getValue() {
            return value;
        }

        public List<Annotation> getAnnotationSet() {
            return annotationSet;
        }

        public void addAnnotation(Annotation annotation) {
            this.annotationSet.add(annotation);
            this.importClassSet.addAll(annotation.getImportClassSet());
        }

        public String getContent() {
            return type.getContent() + " " + name + (value == null ? StringUtils.Empty : value);
        }

        @Override
        public Set<JavaType> getImportClassSet() {
            return this.importClassSet;
        }
    }

}
