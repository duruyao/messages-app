package com.dry.messages;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that as manager of Regex.
 *
 * @author DuRuyao
 * @version 1.0
 * Create 2019/04/03 8:22 AM
 * Update [1] [yy/mm/dd hh:mm] [Name] [Description]
 */
public class RegexManager {
    private String text;
    private String patternStr;
    private Pattern pattern;
    private Matcher matcher;
    private boolean isFind;

    public RegexManager(String text, String patternStr) {
        this.text = text;
        this.patternStr = patternStr;
        this.pattern = Pattern.compile(this.patternStr);
        this.matcher = this.pattern.matcher(this.text);
        this.isFind = this.matcher.find();
    }

    public boolean isFind() {
        return this.isFind;
    }

    public boolean find() {
        return this.matcher.find();
    }

    public String replaceAll(String replaceStr) {
        String replaceText = this.text;
        if (isFind()) {
            replaceText = this.matcher.replaceAll(replaceStr);
        }
        return replaceText;
    }

    public String replaceFirst(String replaceStr) {
        String replaceText = this.text;
        if (isFind()) {
            replaceText = this.matcher.replaceFirst(replaceStr);
        }
        return replaceText;
    }

    public int findNumber() {
        int counter = this.isFind ? 1 : 0;
        while (find()) {
            counter++;
        }
        return counter;
    }
}
