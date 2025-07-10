package cat.urv.deim;

import java.util.Map;


public class Main {
    public static void main(String[] args) throws Exception {

        System.out.println("Working directory: " + System.getProperty("user.dir"));

        Graf<String, Void, Void> graf = CarregarGraf.carregarGraphML("karate_directed.graphml");

        Map<String, Double> pagerank = graf.calcularPageRank(0.85, 1e-6);
        Map<String, Double> betweenness = graf.calcularBetweennessCentrality();

        System.out.println("=== PageRank ===");
        pagerank.forEach((k, v) -> System.out.printf("%s: %.8f%n", k, v));

        System.out.println("=== Betweenness Centrality ===");
        betweenness.forEach((k, v) -> System.out.printf("%s: %.8f%n", k, v));

        ExportadorCSV.exportar(pagerank, betweenness, "guardar_resultats.csv");

    }
}
