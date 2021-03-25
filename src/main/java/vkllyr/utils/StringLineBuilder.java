package vkllyr.utils;

import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Function;

public final class StringLineBuilder implements Cloneable {

    // 制表符用几个空格
    private final int tab;
    // 当前制表符数量
    private int tabCount;

    // 所有行的列表
    private Deque<CharSequence> lineList;
    // 当前tab层级下还未归档的行
    private Deque<CharSequence> currTabLineList;
    // 当前行内还未归档到lineList的字符
    private StringBuilder lineBuilder = new StringBuilder();

    public StringLineBuilder() {
        this.tab = 4;
    }

    public StringLineBuilder(CharSequence charSequence) {
        this();
        lineBuilder.append(charSequence);
    }

    public StringLineBuilder(int tab) {
        this.tab = tab;
    }

    public void tab() {
        ++tabCount;
    }

    public void tab(int count) {
        tabCount += count;
    }

    /**
     * Description 获取行数
     *
     * @author zhaolaiyuan
     * Date 2020/11/19 8:45
     **/
    public int size() {
        return lineList.size() + currTabLineList.size() + lineBuilder.length() > 0 ? 1 : 0;
    }
    /**
     * Description 当前层级归档
     *
     * @author zhaolaiyuan
     * Date 2020/11/17 17:53
     **/
    public void tabEnd() {
        this.line();

        if (currTabLineList.size() == 0) {
            return;
        }
        if (lineList == null) {
            lineList = new LinkedList<>();
        }
        char[] tabArr = new char[this.tab * this.tabCount];
        Arrays.fill(tabArr, ' ');
        String tab = new String(tabArr);
        // 归档
        while (currTabLineList.size() > 0) {
            this.lineList.add(tab + currTabLineList.removeFirst() + System.lineSeparator());
        }
        // 层级end，-1
        --tabCount;
    }

    /**
     * Description 归档不换行，只有一行的情况，比如注解
     *
     * @author zhaolaiyuan
     * Date 2020/11/17 17:53
     **/
    public void endWithoutLine() {
        this.line();
        if (currTabLineList.size() < 1) {
            return;
        }
        if (lineList == null) {
            lineList = new LinkedList<>();
        }
        char[] tabArr = new char[this.tab * this.tabCount];
        Arrays.fill(tabArr, ' ');
        String tab = new String(tabArr);
        // 归档
        while (currTabLineList.size() > 0) {
            this.lineList.add(tab + currTabLineList.removeFirst());
        }
    }

    /**
     * Description 获取单行(优先当前正在编辑的行, 其次行编辑器中第一行)
     *
     * @author zhaolaiyuan
     * Date 2020/11/18 15:09
     **/
    public String getLine() {
        if (lineBuilder.length() != 0) {
            return lineBuilder.toString();
        } else if (currTabLineList.size() != 0) {
            return currTabLineList.peekFirst().toString();
        } else if (lineList.size() != 0) {
            return lineList.peekFirst().toString();
        } else {
            throw new RuntimeException("什么鬼");
        }
    }

    // 换行
    public void line() {
        if (this.lineBuilder.length() == 0) {
            return;
        }
        if (currTabLineList == null) {
            currTabLineList = new LinkedList<>();
        }
        currTabLineList.add(this.lineBuilder);
        this.lineBuilder = new StringBuilder();
    }

    public StringLineBuilder append(CharSequence charSequence) {
        lineBuilder.append(charSequence);
        return this;
    }

    public StringLineBuilder append(char c) {
        lineBuilder.append(c);
        return this;
    }

    public StringLineBuilder append(Object o) {
        lineBuilder.append(o);
        return this;
    }

    public <T> StringLineBuilder append(Iterable<T> iterable, String delimiter) {
        return append(iterable, delimiter, x -> toString());
    }

    public <T> StringLineBuilder append(Iterable<T> iterable, String delimiter, Function<T, String> function) {
        Iterator<T> iterator = iterable.iterator();
        lineBuilder.append(function.apply(iterator.next()));
        while (iterator.hasNext()) {
            lineBuilder.append(delimiter).append(function.apply(iterator.next()));
        }
        return this;
    }

    public StringLineBuilder appendLine(CharSequence charSequence) {
        this.line();
        lineBuilder.append(charSequence);
        this.line();
        return this;
    }

    public <T> StringLineBuilder appendAllLine(Iterable<T> iterable, Function<T, String> function) {
        return appendAllLine(iterable, "", function);
    }

    public <T> StringLineBuilder appendAllLine(Iterable<T> iterable, String delimiter, Function<T, String> function) {
        this.line();
        Iterator<T> iterator = iterable.iterator();
        lineBuilder.append(function.apply(iterator.next()));
        while (iterator.hasNext()) {
            lineBuilder.append(delimiter);
            this.line();
            lineBuilder.append(function.apply(iterator.next()));
        }
        this.line();
        return this;
    }

    public StringLineBuilder appendLine(char c) {
        this.line();
        lineBuilder.append(c);
        this.line();
        return this;
    }

    /**
     * Description 默认只比当前加一个tab
     *
     * @author zhaolaiyuan
     * Date 2020/11/18 8:54
     **/
    public void merge(StringLineBuilder slb) {
        this.merge(slb, this.tabCount + 1);
    }

    public void merge(StringLineBuilder slb, int tabCount) {
        // 可能没有收尾
        slb.tabEnd();
        this.tabEnd();

        char[] tabArr = new char[slb.tab * tabCount];
        Arrays.fill(tabArr, ' ');
        String tab = new String(tabArr);
        for (CharSequence charSequence : slb.lineList) {
            this.lineList.add(tab + charSequence);
        }
    }

    /**
     * Description 在开头插入一行
     *
     * @author zhaolaiyuan
     * Date 2020/11/19 10:33
     **/
    public void addLineToFirst(String line) {
        lineList.addFirst(line);
    }

    public String removeHead() {
        this.tabEnd();
        return lineList.removeFirst().toString();
    }

    public String removeTail() {
        this.tabEnd();
        return lineList.removeLast().toString();
    }

    public void foreach(Function<CharSequence, String> function) {
        this.tabEnd();
        for (int i = 0; i < this.lineList.size(); i++) {
            CharSequence charSequence = lineList.removeFirst();
            lineList.addLast(function.apply(charSequence));
        }
    }

    @Override
    public String toString() {
        this.tabEnd();
        for (CharSequence charSequence : lineList) {
            // 借用下lineBuilder
            lineBuilder.append(charSequence);
        }
        String s = lineBuilder.toString();
        lineBuilder = new StringBuilder();
        return s;
    }

    @Override
    public StringLineBuilder clone() {
        try {
            return (StringLineBuilder) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
