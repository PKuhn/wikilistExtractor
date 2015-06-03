import java.util.Arrays;

public class TableExtractor {

    private String[][] tableAsString;

    TableExtractor(String pageName) {
        TableParser parser = new TableParser();
        printStringTable(tableAsString);
    }

    private void printStringTable(String[][] table) {
        for (String[] row : table) {
            System.out.println(Arrays.toString(row));
        }
    }

    public void extract() {

    }
}
