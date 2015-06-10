import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class TableParser {

    public RDFTable createRDFTable(String pageName) {
        Element table = getTableElementByName(pageName);
        return  parseToRDFTable(table);
    }

    public RDFTable parseToRDFTable(Element table) {
        Elements rows = table.select("tr");

        int colCount = rows.get(1).select("td").size();
        RDFTable result = new RDFTable(colCount);

        for (Element row : rows) {
            Elements cells;

            if (row.toString().contains("<th")) {
                cells = row.select("th");
            } else {
                cells = row.select("td");
            }

            ArrayList<TableEntry> entryRow = new ArrayList<>();

            for (Element cell : cells) {
                TableEntry entry = new TableEntry(cell);
                entryRow.add(entry);
            }

            result.insertRow(entryRow.toArray(new TableEntry[entryRow.size()]));
        }

        return result;
    }


    //TODO refactor name
    public TableEntry[][] getTable(Element table) {
        //select relevant rows
        Elements rows = table.select("tr");
        int rowCount = rows.size();

        // select column count from second row since first is header <th>
        int colCount = rows.get(1).select("td").size();

        //TODO change data structure to something that allows dynamic row count
        TableEntry[][] result = new TableEntry[rowCount - 1][colCount];

        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            //TODO check if row is valid before adding to the Array
            Elements cols = row.select("td");
            for (int j = 0; j < cols.size(); j++) {
                result[i - 1][j] = new TableEntry(cols.get(j));
            }
        }
        return result;
    }

    public Element getTableElementByName(String pageName) {
        String url = "http://en.wikipedia.org/wiki/" + pageName;
        Element table;
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //get first table in document
        table = doc.select("table").get(0);
        if (table.select("tr").size() <= 3) {
            table = doc.select("table").get(1);
        }
        return table;
    }

    private String getTitleFromLink(Element cell) {
        String content = cell.toString();

        if (content.contains("(page does not exist)")) {
            return "";
        }
        String title = StringUtils.substringBetween(content, "title=\"", "\"");
        if (title == null) {
            return "";
        }
        return title;
    }

    public List<String> getLinkTitlesForColumn(Element table, int index) {
        Elements rows = table.select("tr");
        List<String> linkTitles = new LinkedList<>();
        for (Element row : rows.subList(1, rows.size())) {
            Elements cols = row.select("td");
            if (row.toString().contains("<th") || cols.size() < index) {
                continue;
            }
            Element cell = cols.get(index);

            String title = getTitleFromLink(cell);
            if (!title.equals("")) {
                linkTitles.add(title);
            }
        }
        return linkTitles;
    }

}
