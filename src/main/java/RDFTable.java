import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RDFTable {
    ArrayList<TableEntry[]> table = new ArrayList<>();
    private int colCount;


    RDFTable(int columnCount) {
        colCount = columnCount;
    }

    public void insertRow(TableEntry[] row) {
        if (row.length != colCount) {
            //TODO throw useful exception
            System.out.println("wrong number of columns");
        }
        table.add(row);
    }

    public List<String> getColumnNames(int index) {
        List<String> columnNames = new LinkedList<>();
        TableEntry[] firstRow = table.get(0);
        for (TableEntry entry : firstRow) {
            columnNames.add(entry.getTextContent());
        }
        return columnNames;
    }

    public String[][] getTableAsTextArray() {
        int rowCount = table.size();
        String[][] textArray = new String[rowCount][colCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                textArray[i][j] = table.get(i)[j].getTextContent();
            }
        }

        return textArray;
    }

    public void printTable() {
        int rowCount = table.size();
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                System.out.println(table.get(i)[j].getTextContent() + "  ");
            }
            System.out.println();
        }
    }
}
