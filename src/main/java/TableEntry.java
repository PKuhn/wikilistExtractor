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

    public boolean isHeaderCell() {
        return tableEntry.toString().contains("<th");
    }

    public String getTextContent() {
        return textContent;
    }
}
