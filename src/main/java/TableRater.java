import org.jsoup.nodes.Element;

import java.util.Arrays;
import java.util.List;

public class TableRater {

    public int[] rateTable(String[][] table, Element htmlTable) {
        int columnCount = table[0].length;
        int[] rating = new int[columnCount];

        for (int i = 0; i < columnCount; i++) {
            //String[] col = getColumn(table, i);
            rating[i] += Math.max(100 - i * 10, 0);
            //rating[i] += rateProperties(htmlTable, i);
        }
        int maxPosition = findMaxColumn(rating);
        System.out.println("The main column is: " + (maxPosition + 1));

        return rating;
    }

    private int findMaxColumn(int[] rating) {
        int max = 0;
        int maxPosition = 0;
        for (int i = 0; i < rating.length; i++) {
            if (rating[i] > max) {
                max = rating[i];
                maxPosition = i;
            }
        }
        return maxPosition;
    }

//    private int rateProperties(Element table, int columnIndex) {
//        String[] columnNames = getColumnNames(table);
//        SPARQLHelper helper = new SPARQLHelper();
//
//        List<String> titles = getLinkTitlesForColumn(table,columnIndex);
//        if (titles.size() == 0) {
//            return 0;
//        }
//        String titleWithoutWhiteSpace = titles.get(0).replaceAll("\\s","_");
//
//        List<String> properties = helper.getPropertiesForEntity(titleWithoutWhiteSpace);
//        if (columnTitlesContainsProperty(Arrays.asList(columnNames), properties)) {
//            return 100;
//        }
//        return 0;
//    }
}
