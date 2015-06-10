import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TableEntry {
    private Element tableEntry;
    private boolean isLink;
    private String textContent;
    private boolean isDbpediaEntity;

    TableEntry(Element tableCell) {
        textContent = stripTextBetweenBrackets(tableCell.text());
        tableEntry = tableCell;
        //isDbpediaEntity = checkDbpediaEntity();
    }

    public boolean isLink() {
        return tableEntry.toString().contains("href");
    }

    public String getRawContent() {
        return tableEntry.toString();
    }

    public boolean isHeaderCell() {
        return tableEntry.toString().contains("<th");
    }

    public String getTextContent() {
        return textContent;
    }

    private String stripTextBetweenBrackets(String text) {

        Pattern p = Pattern.compile("\\[(.*?)\\]");
        Matcher m = p.matcher(text);
        while(m.find())
        {
            String substring = m.group(1);
            text = text.replace("["+substring+"]", "");
        }
        return text;
    }

    public String getRDFTitle() {
        String content = stripTextBetweenBrackets(tableEntry.toString());
        if (content.contains("(page does not exist)")) {
            return "";
        }
        String title = StringUtils.substringBetween(content, "title=\"", "\"");

        if (title == null) {
            return "";
        }
        title = title.replace(' ', '_');
        return title;
    }

    public boolean isDbpediaEntity() {
        if (this.isHeaderCell()) {
            return false;
        }
        return checkDbpediaEntity();
    }

    private boolean checkDbpediaEntity() {
        SPARQLHelper helper = new SPARQLHelper();
        return helper.isDbpediaEntity(this);
    }
}
