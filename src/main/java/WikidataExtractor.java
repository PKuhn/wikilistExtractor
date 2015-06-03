public class WikidataExtractor {
    public static void main(String[] args) {
        WikidataExtractor extr = new WikidataExtractor();
        extr.extractPage("List_of_Donalds");
    }

    public void extractPage(String pageName) {
        //Todo add recognize kind of page
        TableExtractor extractor = new TableExtractor(pageName);
        extractor.extract();
    }

}
