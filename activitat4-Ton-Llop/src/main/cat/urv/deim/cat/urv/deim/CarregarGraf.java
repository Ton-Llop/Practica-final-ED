package cat.urv.deim;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CarregarGraf {

    /**
     * Carrega un graf des d’un fitxer GraphML (amb aristes dirigides).
     * 
     * @param filepath Ruta al fitxer GraphML.
     * @return Un objecte Graf amb vèrtexs i arestes.
     * @throws Exception si hi ha errors de lectura/parsing.
     */
    public static Graf<String, Void, Void> carregarGraphML(String filepath) throws Exception {
        Graf<String, Void, Void> graf = new Graf<>();

        // Crear parser XML
        File inputFile = new File(filepath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();

        // Llegir nodes
        NodeList nodeList = doc.getElementsByTagName("node");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element nodeElement = (Element) nodeList.item(i);
            String id = nodeElement.getAttribute("id");
            graf.inserirVertex(id, null);
        }

        // Llegir arestes
        NodeList edgeList = doc.getElementsByTagName("edge");
        for (int i = 0; i < edgeList.getLength(); i++) {
            Element edgeElement = (Element) edgeList.item(i);
            String source = edgeElement.getAttribute("source");
            String target = edgeElement.getAttribute("target");

            graf.inserirAresta(source, target);
        }

        return graf;
    }

    public static Graf<String, Void, Void> carregarPajek(String filepath) throws Exception {
        Graf<String, Void, Void> graf = new Graf<>();
        Map<Integer, String> idMap = new HashMap<>();

        Scanner scanner = new Scanner(new File(filepath));
        boolean enVertices = false, enArcs = false;

        while (scanner.hasNextLine()) {
            String linea = scanner.nextLine().trim();

            if (linea.isEmpty()) continue;

            if (linea.toLowerCase().startsWith("*vertices")) {
                enVertices = true;
                enArcs = false;
                continue;
            }
            if (linea.toLowerCase().startsWith("*arcs")) {
                enVertices = false;
                enArcs = true;
                continue;
            }

            if (enVertices) {
                // Ex: 1 "A"
                String[] parts = linea.split("\\s+", 2);
                int idx = Integer.parseInt(parts[0]);
                String nom = parts[1].replaceAll("\"", "").trim();
                idMap.put(idx, nom);
                graf.inserirVertex(nom, null);
            } else if (enArcs) {
                // Ex: 1 2
                String[] parts = linea.split("\\s+");
                int from = Integer.parseInt(parts[0]);
                int to = Integer.parseInt(parts[1]);
                String origen = idMap.get(from);
                String desti = idMap.get(to);
                graf.inserirAresta(origen, desti);
            }
        }

        scanner.close();
        return graf;
    }
}
