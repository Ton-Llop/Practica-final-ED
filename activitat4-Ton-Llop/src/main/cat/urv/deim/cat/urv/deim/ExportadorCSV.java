package cat.urv.deim; 

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class ExportadorCSV {

    /**
     * Exporta els valors de PageRank i Betweenness Centrality a un fitxer CSV.
     * 
     * @param pagerank Map amb valors de PageRank
     * @param betweenness Map amb valors de Centralitat
     * @param filepath Ruta del fitxer de sortida
     * @throws Exception en cas d’error d’escriptura
     */
    public static void exportar(Map<String, Double> pagerank, Map<String, Double> betweenness, String filepath) throws Exception {
        // Crear llista de tuples per ordenar
        List<String> ids = new ArrayList<>(pagerank.keySet());

        // Ordenar per pagerank descendent
        ids.sort((a, b) -> Double.compare(pagerank.get(b), pagerank.get(a)));

        PrintWriter writer = new PrintWriter(new FileWriter(filepath));
        writer.println("id;pagerank;betweenness");

        for (String id : ids) {
            double pr = pagerank.get(id);
            double bc = betweenness.getOrDefault(id, 0.0);
            writer.printf(Locale.US, "%s;%.8f;%.8f%n", id, pr, bc);
        }

        writer.close();
    }
}