package vkllyr.pageElement;

import vkllyr.Config;
import vkllyr.utils.StringLineBuilder;

public interface WithComment {

    void setComment(String comment);

    void setComment(StringLineBuilder comment);

    StringLineBuilder getComment();

    /**
     * 格式化为文档注释，会加上模板
     *
     * @author zhaolaiyuan
     * Date 2020/11/19 10:28
     **/
    default StringLineBuilder formatToDocumentationComment(Config config) {
        StringLineBuilder clone = getComment().clone();
        clone.tabEnd();
        clone.addLineToFirst("/*");
        // 加个空行
        clone.appendLine(" * ");
        // 加上模板信息
        clone.merge(config.getCommentTemplate());
        clone.foreach(x -> " * " + x);
        clone.appendLine(" **/");
        return clone;
    }

    /**
     * 格式化为多行注释
     *
     * @author zhaolaiyuan
     * Date 2020/11/19 10:28
     **/
    default StringLineBuilder formatToMultiComment(Config config) {
        StringLineBuilder clone = getComment().clone();
        clone.tabEnd();
        String head = clone.removeHead();
        clone.addLineToFirst("/*" + head);
        String tail = clone.removeTail();
        clone.appendLine(tail + "*/");
        return clone;
    }

    /**
     * 全都用单行注释
     *
     * @author zhaolaiyuan
     * Date 2020/11/19 10:01
     **/
    default StringLineBuilder formatToSingleComment(Config config) {
        StringLineBuilder clone = getComment().clone();
        clone.foreach(x -> "// " + x);
        return clone;
    }

}
