import java.util.LinkedList;
import java.util.List;

public class PredicateMatcher {
    public static void main(String[] args) {
        String pageName = "List_of_best-selling_books";
        PredicateMatcher matcher = new PredicateMatcher();
        matcher.findMatchingPredicates(pageName);
    }

    public void findMatchingPredicates(String pageName) {
        TableParser parser = new TableParser();
        //Element table = parser.getTableElementByName(pageName);
        RDFTable table = parser.createRDFTable(pageName);
        int rowCount = table.getRowCount();
        List<String> tableNames = table.getColumnNames();

        /*for (int i = 0; i < table.getColumnCount() -1; i++) {
            for (int j = i + 1; j < table.getColumnCount(); j++) {
                int matchedCount = matchColumns(i, j, table);
                float percentage = matchedCount/rowCount;
                if (percentage > 0.3) {
                    System.out.print("matched column " + tableNames.get(i) + " with  " + tableNames.get(j));
                    System.out.println("with percantage: " + percentage);
                }
            }
        }*/
        for (int i = 1; i < table.getColumnCount(); i++) {
            int matchedCount = matchColumns(0, i, table);
        }
    }

    private int matchColumns(int firstColumn, int secondColumn, RDFTable table) {
        int rowCount = table.getRowCount();
        SPARQLHelper helper = new SPARQLHelper();

        int matchedEntries = 0;

        for (int i = 1; i < rowCount; i++) {
            TableEntry entryOfFirstColumn = table.getElement(i, firstColumn);
            TableEntry entryOfSecondColumn = table.getElement(i, secondColumn);

            String name1 = entryOfFirstColumn.getRDFTitle();
            String name2 = entryOfSecondColumn.getRDFTitle();

            List<String> predicates = new LinkedList<>();

            if (name2.equals("")) {
                String literal = entryOfSecondColumn.getTextContent();
                predicates = helper.getPredicatesBetweenEntityAndLiteral(name1, literal);
                System.out.println("Finding predicates between: " + name1 + " and " + literal);

            } else {
                System.out.println("Finding predicates between: " + name1 + " and " + name2);
                predicates = helper.getPredicatesBetweenEntities(name1, name2);
            }
            System.out.println(predicates.toString());
            if (predicates.size() != 0) {
                matchedEntries++;
            }
        }

        return matchedEntries;
    }
}
