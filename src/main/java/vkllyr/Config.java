package vkllyr;

import vkllyr.utils.StringLineBuilder;
import vkllyr.utils.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class Config {
    private StringLineBuilder commentTemplate;

    private String author;
    // 模板日期格式化，为null表示不需要展示日期
    private String templateDateFormarter;


    public StringLineBuilder getCommentTemplate() {
        if (commentTemplate != null) {
            return commentTemplate;
        }
        commentTemplate = new StringLineBuilder();
        if (StringUtils.isNotEmpty(author)) {
            commentTemplate.appendLine("@author " + this.author);
        }
        if (StringUtils.isNotEmpty(templateDateFormarter)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(templateDateFormarter);
            commentTemplate.appendLine("Date " + LocalDateTime.now().format(dateTimeFormatter));
        }
        return commentTemplate;
    }

}
