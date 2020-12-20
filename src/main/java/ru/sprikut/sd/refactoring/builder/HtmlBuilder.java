package ru.sprikut.sd.refactoring.builder;

public class HtmlBuilder {
    private StringBuilder builder = new StringBuilder();
    private String startTags = "<html><body>\n";
    private String endTags = "</body></html>\n";

    public void appendText(String str) {
        builder.append(str).append("\n");
    }

    public void appendH1Tag(String str) {
        builder.append("<h1>").append(str).append("</h1>\n");
    }

    public void clear() {
        startTags = "";
        endTags = "";
        builder = new StringBuilder();
    }

    @Override
    public String toString() {
        return startTags + builder.toString() + endTags;
    }
}