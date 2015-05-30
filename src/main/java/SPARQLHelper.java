import com.hp.hpl.jena.query.*;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedList;
import java.util.List;

public class SPARQLHelper {
    public List<String> getPropertiesForEntity(String name) {
        String queryString = buildPredicateQuery(name);
        List<String> properties = new LinkedList<>();
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);

        try {
            ResultSet predicates = qexec.execSelect();
            //ResultSetFormatter.out(System.out, results, query);
            while (predicates.hasNext()) {
                QuerySolution solution = predicates.next();
                String solString = solution.toString();
                String property = (StringUtils.substringBetween(solString, "property/", ">"));
                if (property != null) {
                    properties.add(property);
                }
            }
        }
        finally {
            qexec.close();
        }
        return properties;
    }


    private String buildPredicateQuery(String name) {
        String query =
                "SELECT ?predicate { " +
                        "<http://dbpedia.org/resource/" + name + "> ?predicate ?object. " +
                        "}";
        return query;
    }

}
