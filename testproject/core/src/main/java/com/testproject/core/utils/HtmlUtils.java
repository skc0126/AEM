package com.testproject.core.utils;

public class HtmlUtils {

    public static String stripOuterTag(String html) {
        if (html == null || html.isEmpty()) {
            return "";
        }
        return html.replaceAll("^<[^>]+>|</[^>]+>$", "").trim();
    }
}