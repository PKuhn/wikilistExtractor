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
        for (int i = 0; i < table.getRowCount(); i++) {
            List<String> columnAsRDF = table.getColumnAsRDF(i);
        }
        matchColumns(0, 1, table);
    }

    private void matchColumns(int firstColumn, int secondColumn, RDFTable table) {
        int rowCount = table.getRowCount();
        SPARQLHelper helper = new SPARQLHelper();

        TableEntry entryOfFirstColumn = table.getElement(1, firstColumn);
        TableEntry entryOfSecondColumn = table.getElement(1, secondColumn);

        String name1 = entryOfFirstColumn.getRDFTitle();
        String name2 = entryOfSecondColumn.getRDFTitle();

        List<String> predicates = helper.getPredicatesBetweenEntities(name1, name2);

        /*for (int i = 1; i < rowCount; i++) {
            TableEntry entryOfFirstColumn = table.getElement(i, firstColumn);
            TableEntry entryOfSecondColumn = table.getElement(i, secondColumn);

            String name1 = entryOfFirstColumn.getRDFTitle();
            String name2 = entryOfSecondColumn.getRDFTitle();

            List<String> predicates = helper.getPredicatesBetweenEntities(name1, name2);
            System.out.println(predicates.toString());
        }*/
    }
}
