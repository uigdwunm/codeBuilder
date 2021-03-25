package vkllyr.baseElement;

import vkllyr.Enums.Permission;

import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Description 作为类型存在时使用
 * 
 * @author zhaolaiyuan
 * Date 2020/11/18 13:49
 **/
public class JavaType {
    private final String name;
    private final String packageName;
    private final Permission permission;
    // 不到最后页面，不用管泛型类型收集
    private final Deque<JavaType> genericityList;

    // 可能需要全路径显示，在当前页面下的样子，很显然如果并发会有问题（但不会有并发）
    // 最终页面会填充这个值，不能提前写死
    private String content;


    // java原生的类，可能包的路径有的不对，但是省事！！！
    public static final String LANG = "java.lang";
    private static final JavaType BASE_VOID = new JavaType("void", LANG);
    private static final JavaType BASE_INT = new JavaType("int", LANG);
    private static final JavaType BASE_LONG = new JavaType("long", LANG);
    private static final JavaType BASE_BYTE = new JavaType("byte", LANG);
    private static final JavaType BASE_CHAR = new JavaType("char", LANG);
    private static final JavaType BASE_FlOAT = new JavaType("float", LANG);
    private static final JavaType BASE_DOUBLE = new JavaType("double", LANG);
    private static final JavaType VOID = new JavaType("Void", LANG);
    private static final JavaType DATE = new JavaType("date", "java.util");
    // TODO 这种有泛型，得特殊处理
//    private static final JavaType LIST = new JavaType("List", "java.util");

    public JavaType(String className, String packageName, JavaType ... genericityArr) {
        this.name = className;
        this.packageName = packageName;
        this.permission = Permission.PUBLIC;
        this.genericityList = new LinkedList<>();
        Collections.addAll(this.genericityList, genericityArr);
    }

    public JavaType withGenericity(JavaType... genericityArr) {
        Collections.addAll(this.genericityList, genericityArr);
        return this;
    }

    public Deque<JavaType> getGenericityList() {
        return genericityList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return this.name;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public Permission getPermission() {
        return this.permission;
    }

}
