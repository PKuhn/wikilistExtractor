import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;


public class TableEntry {
    private Element tableEntry;
    private boolean isLiteral;
    private String textContent;

    TableEntry(Element tableCell) {
        textContent = tableCell.text();
        tableEntry = tableCell;
    }

    public boolean isLiteral() {
        return isLiteral;
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

    public String getRDFTitle() {
        String content = tableEntry.toString();
        if (content.contains("(page does not exist)")) {
            return "";
        }
        String title = StringUtils.substringBetween(content, "title=\"", "\"");
        if (title == null) {
            return "";
        }
        return title.replace(' ', '_');
    }
}
