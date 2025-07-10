package cat.urv.deim;
import cat.urv.deim.exceptions.VertexNoTrobat;
import cat.urv.deim.exceptions.ArestaNoTrobada;
import cat.urv.deim.exceptions.ElementNoTrobat;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unused")
public class Graf<K, V, E> implements IGraf<K, V, E>{

    class Vertice {
        K clave;
        V valor;
        ArrayList<Aresta> adyacentes = new ArrayList<>();

        public Vertice(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
        }
    }

    class Aresta {
        K destino;
        E valor;

        public Aresta(K destino, E valor) {
            this.destino = destino;
            this.valor = valor;
        }
    }

    private Map<K, Vertice> vertices = new HashMap<>();

    // Metode per insertar un nou vertex al graf. El valor de K es l'identificador del vertex i V es el valor del vertex
    public void inserirVertex(K clave, V valor) {
        vertices.put(clave, new Vertice(clave, valor));  // sobrescriu si ja hi és
    }
    
    


    // Metode per a obtenir el valor d'un vertex del graf a partir del seu identificador
    public V consultarVertex(K clave) throws VertexNoTrobat {
        Vertice vertice = vertices.get(clave);
        if (vertice == null) {
            throw new VertexNoTrobat();
        }
        return vertice.valor;
    }

    // Metode per a esborrar un vertex del graf a partir del seu identificador
    // Aquest metode tambe ha d'esborrar totes les arestes associades a aquest vertex
    public void esborrarVertex(K clave) throws VertexNoTrobat {
        Vertice vertice = vertices.get(clave);
        if (vertice == null) {
            throw new VertexNoTrobat();
        }

        // Eliminar todas las aristas asociadas a este vértice
        for (Vertice v : vertices.values()) {
            v.adyacentes.removeIf(arestas -> arestas.destino.equals(clave));
        }

        // Eliminar el vértice del grafo
        vertices.remove(clave);
    }

    // Metode per a comprovar si hi ha algun vertex introduit al graf
    public boolean esBuida() {
        return vertices.isEmpty();
    }

    // Metode per a comprovar el nombre de vertexs introduits al graf
    public int numVertex() {
        return vertices.size();
    }

    // Metode per a obtenir totes les claus dels vertex de l'estrucutra
    public ArrayList<K> obtenirVertexIDs() {
        return new ArrayList<>(vertices.keySet());
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // Operacions per a treballar amb les arestes

    // Metode per a insertar una aresta al graf. Els valors de vertex 1 i vertex 2 son els vertex a connectar i E es el pes de la aresta
    // Si ja existeix l'aresta se li actualitza el seu pes
    public void inserirAresta(K v1, K v2, E pes) throws VertexNoTrobat {
        Vertice vertice1 = vertices.get(v1);
        Vertice vertice2 = vertices.get(v2);

        // Comprobar si ambos vértices existen
        if (vertice1 == null) {
            throw new VertexNoTrobat();
        }
        if (vertice2 == null) {
            throw new VertexNoTrobat();
        }

        // Comprobar si la arista ya existe entre los vértices
        boolean aristaExistente = false;
        for (Aresta aresta : vertice1.adyacentes) {
            if (aresta.destino.equals(v2)) {
                // Actualizar el peso de la arista existente
                aresta.valor = pes;
                aristaExistente = true;
                break;
            }
        }

        // Si la arista no existe, crear una nueva
        if (!aristaExistente) {
            vertice1.adyacentes.add(new Aresta(v2, pes));
        }
    }

    // Metode equivalent a l'anterior, afegint com a pes el valor null
    public void inserirAresta(K v1, K v2) throws VertexNoTrobat {
        inserirAresta(v1, v2, null);
    }

    // Metode per a saber si una aresta existeix a partir dels vertex que connecta
    public boolean existeixAresta(K v1, K v2) {
        Vertice vertice1 = vertices.get(v1);
        Vertice vertice2 = vertices.get(v2);
    
        // Si qualsevol dels dos no existeix → retornem false (no hi ha connexió)
        if (vertice1 == null || vertice2 == null) {
            return false;
        }
    
        for (Aresta aresta : vertice1.adyacentes) {
            if (aresta.destino.equals(v2)) {
                return true;
            }
        }
    
        return false;
    }
    

    // Metode per a obtenir el pes d'una aresta a partir dels vertex que connecta
    public E consultarAresta(K v1, K v2) throws VertexNoTrobat, ArestaNoTrobada {
        Vertice vertice1 = vertices.get(v1);
        Vertice vertice2 = vertices.get(v2);

        // Comprobar si ambos vértices existen
        if (vertice1 == null) {
            throw new VertexNoTrobat();
        }
        if (vertice2 == null) {
            throw new VertexNoTrobat();
        }

        // Buscar la arista entre los vértices
        for (Aresta aresta : vertice1.adyacentes) {
            if (aresta.destino.equals(v2)) {
                return aresta.valor; // Devuelve el peso de la arista
            }
        }

        // Si no se encuentra la arista
        throw new ArestaNoTrobada();
    }

    // Metode per a esborrar una aresta a partir dels vertex que connecta
    public void esborrarAresta(K v1, K v2) throws VertexNoTrobat, ArestaNoTrobada {
        Vertice vertice1 = vertices.get(v1);
        Vertice vertice2 = vertices.get(v2);

        // Comprobar si ambos vértices existen
        if (vertice1 == null) {
            throw new VertexNoTrobat();
        }
        if (vertice2 == null) {
            throw new VertexNoTrobat();
        }

        // Eliminar la arista en el vértice v1
        boolean aristaEliminada = vertice1.adyacentes.removeIf(arestas -> arestas.destino.equals(v2));

        // Si no se encontró la arista en v1
        if (!aristaEliminada) {
            throw new ArestaNoTrobada();
        }

        // Eliminar la arista en el vértice v2 (para grafos no dirigidos)
        vertice2.adyacentes.removeIf(arestas -> arestas.destino.equals(v1));
    }

    // Metode per a comptar quantes arestes te el graf en total
    public int numArestes() {
        int numArestes = 0;

        // Contamos las aristas de cada vértice
        for (Vertice vertice : vertices.values()) {
            numArestes += vertice.adyacentes.size();
        }

        // Si el grafo es no dirigido, hemos contado cada arista dos veces, así que la dividimos entre 2
        return numArestes;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // Metodes auxiliars per a treballar amb el graf

    // Metode per a saber si un vertex te veins (successors o antecessors)
    @Override
    public boolean vertexAillat(K v1) throws VertexNoTrobat {
        Vertice vertice = vertices.get(v1);

        // Comprobar si el vértice existe
        if (vertice == null) {
            throw new VertexNoTrobat();
        }

        // Verificar si tiene aristas conectadas
        return vertice.adyacentes.isEmpty();
    }

    // Metode per a saber quants successors te un vertex
    public int numSuccessors(K v1) throws VertexNoTrobat {
        Vertice vertice = vertices.get(v1);

        // Comprobar si el vértice existe
        if (vertice == null) {
            throw new VertexNoTrobat();
        }

        // Devolver el número de sucesores (aristas salientes)
        return vertice.adyacentes.size();
    }

    // Metode per a saber quants antecessors te un vertex
    public int numAntecessors(K v1) throws VertexNoTrobat {
        Vertice vertice = vertices.get(v1);

        // Comprobar si el vértice existe
        if (vertice == null) {
            throw new VertexNoTrobat();
        }

        int numAntecessors = 0;

        // Buscar en todos los vértices si alguno tiene aristas hacia v1
        for (Vertice v : vertices.values()) {
            for (Aresta aresta : v.adyacentes) {
                if (aresta.destino.equals(v1)) {
                    numAntecessors++;
                    break;
                }
            }
        }

        // Devolver el número de antecesores
        return numAntecessors;
    }

    // Metode per a saber quants veins te un vertex
    public int numVeins(K v1) throws VertexNoTrobat {
        Vertice vertice = vertices.get(v1);

        // Comprobar si el vértice existe
        if (vertice == null) {
            throw new VertexNoTrobat();
        }

        // Contamos los sucesores (adyacentes) y antecesores
        int numVeins = vertice.adyacentes.size();

        // Contar los antecesores, buscando en todos los vértices
        for (Vertice v : vertices.values()) {
            for (Aresta aresta : v.adyacentes) {
                if (aresta.destino.equals(v1)) {
                    numVeins++;
                    break;
                }
            }
        }

        // Devolver el número total de vecinos (sucesores + antecesores)
        return numVeins;
    }

    // Metode per a obtenir totes les claus dels predecessors d'un vertex
    public ArrayList<K> obtenirSuccessors(K v1) throws VertexNoTrobat {
        Vertice vertice = vertices.get(v1);

        // Comprobar si el vértice existe
        if (vertice == null) {
            throw new VertexNoTrobat();
        }

        // Crear una lista para almacenar las claves de los sucesores
        ArrayList<K> successors = new ArrayList<>();

        // Añadir las claves de los sucesores a la lista
        for (Aresta aresta : vertice.adyacentes) {
            successors.add(aresta.destino);
        }

        // Devolver la lista de claves de los sucesores
        return successors;
    }

    // Metode per a obtenir totes les claus dels predecessors d'un vertex
    public ArrayList<K> obtenirPredecesors(K v1) throws VertexNoTrobat {
        // Comprobar si el vértice existe
        Vertice vertice = vertices.get(v1);
        if (vertice == null) {
            throw new VertexNoTrobat();
        }

        // Crear la lista para almacenar los predecesores
        ArrayList<K> predecesors = new ArrayList<>();

        // Recorrer todos los vértices del grafo
        for (Vertice v : vertices.values()) {
            // Recorrer todas las aristas de cada vértice
            for (Aresta aresta : v.adyacentes) {
                // Si la arista tiene como destino el vértice v1, entonces v es un predecesor de v1
                if (aresta.destino.equals(v1)) {
                    predecesors.add(v.clave);
                }
            }
        }

        return predecesors;
    }


    // Metode per a obtenir totes les claus de vertex veins d'un vertex (antecessors i successors)
    public ArrayList<K> obtenirVeins(K v1) throws VertexNoTrobat {
        Vertice vertice = vertices.get(v1);

        // Comprobar si el vértice existe
        if (vertice == null) {
            throw new VertexNoTrobat();
        }

        // Crear una lista para almacenar las claves de los vecinos
        ArrayList<K> veins = new ArrayList<>();

        // Añadir las claves de los sucesores
        for (Aresta aresta : vertice.adyacentes) {
            veins.add(aresta.destino);
        }

        // Añadir las claves de los antecesores
        for (Vertice v : vertices.values()) {
            for (Aresta aresta : v.adyacentes) {
                if (aresta.destino.equals(v1)) {
                    veins.add(v.clave);
                    break;
                }
            }
        }

        // Devolver la lista de claves de los vértices vecinos
        return veins;
    }


    ////////////////////////////////////////////////////////////////////////////////////
    // Metodes OPCIONALS - Si es fa la part obligatoria la nota maxima sera un 8
    // Si s'implementen aquests tres metodes correctament es podra obtenir fins a 2 punts addicionals

    // Metode per a obtenir tots els nodes que estan connectats a un vertex
    // es a dir, nodes als que hi ha un cami directe (tenint en compte les direccions) des del vertex
    // El node que es passa com a parametre tambe es retorna dins de la llista!
    public ArrayList<K> obtenirNodesConnectats(K inici) throws VertexNoTrobat {
        if (!vertices.containsKey(inici)) {
            throw new VertexNoTrobat();
        }

        ArrayList<K> resultat = new ArrayList<>();
        Set<K> visitats = new HashSet<>();
        Queue<K> cua = new LinkedList<>();

        cua.add(inici);
        visitats.add(inici);

        while (!cua.isEmpty()) {
            K actual = cua.poll();
            resultat.add(actual);

            for (Aresta aresta : vertices.get(actual).adyacentes) {
                K veí = aresta.destino;
                if (!visitats.contains(veí)) {
                    visitats.add(veí);
                    cua.add(veí);
                }
            }
        }

        return resultat;
    }

    // Metode que calcula el nombre de components connexes del graf (una component connexa es un conjunt de nodes que estan connectats entre ells, independentment de les direccions de les arestes)
    public int numComponentsConnexes() {
        // Crear un conjunto para llevar el registro de los vértices visitados
        Set<K> visitats = new HashSet<>();

        // Variable para contar el número de componentes conexos
        int components = 0;

        // Recorrer todos los vértices del grafo
        for (K key : vertices.keySet()) {
            // Si el vértice no ha sido visitado, es un nuevo componente conexo
            if (!visitats.contains(key)) {
                // Realizar una DFS desde este vértice para marcar todos los vértices conectados
                dfs(key, visitats);
                // Incrementar el contador de componentes
                components++;
            }
        }

        return components;
    }

    // Función auxiliar para realizar la búsqueda DFS
    private void dfs(K vertexKey, Set<K> visitats) {
        // Marcar el vértice como visitado
        visitats.add(vertexKey);

        // Obtener el vértice del grafo usando su clave
        Vertice vertice = vertices.get(vertexKey);

        // Recorrer todos los vértices adyacentes (sucesores y antecesores)
        for (Aresta aresta : vertice.adyacentes) {
            if (!visitats.contains(aresta.destino)) {
                // Llamar recursivamente para realizar la DFS en los vértices conectados
                dfs(aresta.destino, visitats);
            }
        }

        // También se debe revisar los antecesores de 'vertexKey'
        for (Vertice v : vertices.values()) {
            for (Aresta aresta : v.adyacentes) {
                if (aresta.destino.equals(vertexKey) && !visitats.contains(v.clave)) {
                    dfs(v.clave, visitats);
                }
            }
        }
    }

    // Metode per a obtenir els nodes que composen la Component Connexa mes gran del graf
    @Override
    public ArrayList<K> obtenirComponentConnexaMesGran() {
        // Crear un conjunto para llevar el registro de los vértices visitados
        Set<K> visitats = new HashSet<>();

        // Lista para almacenar el componente conexo más grande
        ArrayList<K> componentMesGran = new ArrayList<>();

        // Recorrer todos los vértices del grafo
        for (K key : vertices.keySet()) {
            // Si el vértice no ha sido visitado, es un nuevo componente conexo
            if (!visitats.contains(key)) {
                // Lista temporal para almacenar los vértices de este componente conexo
                ArrayList<K> componentActual = new ArrayList<>();
                // Realizar una DFS desde este vértice para marcar todos los vértices conectados
                dfsObtenirComponent(key, visitats, componentActual);

                // Si el componente actual tiene más vértices que el componente más grande, lo actualizamos
                if (componentActual.size() > componentMesGran.size()) {
                    componentMesGran = componentActual;
                }
            }
        }

        return componentMesGran;
    }

    // Función auxiliar para realizar la búsqueda DFS y almacenar los vértices en el componente conexo
    private void dfsObtenirComponent(K vertexKey, Set<K> visitats, ArrayList<K> componentActual) {
        // Marcar el vértice como visitado
        visitats.add(vertexKey);
        // Añadir el vértice al componente actual
        componentActual.add(vertexKey);

        // Obtener el vértice del grafo usando su clave
        Vertice vertice = vertices.get(vertexKey);

        // Recorrer todos los vértices adyacentes (sucesores y antecesores)
        for (Aresta aresta : vertice.adyacentes) {
            if (!visitats.contains(aresta.destino)) {
                // Llamar recursivamente para realizar la DFS en los vértices conectados
                dfsObtenirComponent(aresta.destino, visitats, componentActual);
            }
        }

        // También se debe revisar los antecesores de 'vertexKey'
        for (Vertice v : vertices.values()) {
            for (Aresta aresta : v.adyacentes) {
                if (aresta.destino.equals(vertexKey) && !visitats.contains(v.clave)) {
                    dfsObtenirComponent(v.clave, visitats, componentActual);
                }
            }
   }
}
 /**
     * Calcula el PageRank de cada vèrtex del graf.
     * @param dampingFactor valor del damping (0.85)
     * @param tolerancia criteri de convergencia  (1e-6)
     * @return Un Map amb els vèrtexs i el seu PageRank corresponent.
         * @throws VertexNoTrobat 
     */
    public Map<K, Double> calcularPageRank(double dampingFactor, double tolerancia) throws VertexNoTrobat {
        int totalNodes = numVertex();
        if (totalNodes == 0) return new HashMap<>();  // graf buit
    
        // Inicialització: PR(x) = 1/N per a cada node
        Map<K, Double> rangActual = new HashMap<>();
        for (K nodeId : obtenirVertexIDs()) {
            rangActual.put(nodeId, 1.0 / totalNodes);
        }
    
        boolean haConvergit = false;
        while (!haConvergit) {
            Map<K, Double> rangActualitzat = new HashMap<>();
            double sumaDangling = 0.0;  // PR dels nodes sense successors
    
            // Calcular PR dels dangling nodes
            for (K node : obtenirVertexIDs()) {
                try {
                    if (numSuccessors(node) == 0) {
                        sumaDangling += rangActual.get(node);
                    }
                } catch (Exception ex) {
                    // El node hauria d'existir
                }
            }
    
            double variacioTotal = 0.0;
            for (K desti : obtenirVertexIDs()) {
                double contribucionsEntrants = 0.0;
                ArrayList<K> nodesEntrants = obtenirPredecesors(desti);
                for (K origen : nodesEntrants) {
                    try {
                        // Per cada node desti transferim PR/ nodes que apunta
                        int successorsOrigen = numSuccessors(origen);
                        if (successorsOrigen > 0) {
                            contribucionsEntrants += rangActual.get(origen) / successorsOrigen;
                        }
                    } catch (Exception ex) {
                        // Error inesperat
                    }
                }
    
                double nouValor = (1.0 - dampingFactor) / totalNodes +
                                  dampingFactor * ((sumaDangling / totalNodes) + contribucionsEntrants);
    
                rangActualitzat.put(desti, nouValor);
                variacioTotal += Math.abs(nouValor - rangActual.get(desti));
            }
    
            if (variacioTotal < tolerancia) {
                haConvergit = true;
            }
    
            rangActual = rangActualitzat;  // Preparar següent iteració
        }
    
        return rangActual;
    }
    
 /**
     * Calcula la Betweenness Centrality per a tots els vèrtexs del graf.
     * Només per a grafos dirigits i no ponderats.
     * 
     * @return Un Map amb la centralitat de cada vèrtex.
     */
    public Map<K, Double> calcularBetweennessCentrality() throws VertexNoTrobat {
        Map<K, Double> centralitat = new HashMap<>();
        ArrayList<K> tots = obtenirVertexIDs();

        // Inicialitzar a 0 totes les centralitats
        for (K v : tots) {
            centralitat.put(v, 0.0);
        }

        for (K s : tots) {
            // Estructures per aquest origen
            Stack<K> pila = new Stack<>();
            Map<K, List<K>> predecessors = new HashMap<>();
            Map<K, Integer> distancia = new HashMap<>();
            //Per comptar quants camins + curts arriben
            Map<K, Integer> sigma = new HashMap<>();

            for (K v : tots) {
                predecessors.put(v, new ArrayList<>());
                distancia.put(v, -1);
                sigma.put(v, 0);
            }

            distancia.put(s, 0);
            sigma.put(s, 1);

            // BFS
            Queue<K> cua = new LinkedList<>();
            cua.add(s);

            while (!cua.isEmpty()) {
                K v = cua.poll();
                pila.push(v);

                for (K w : obtenirSuccessors(v)) {
                    // Primer cop que arribo a w guardem la dist i fiquem a cua
                    if (distancia.get(w) == -1) {
                        distancia.put(w, distancia.get(v) + 1);
                        cua.add(w);
                    }
                    // Camí mínim addicional
                    // el sumem i fiquem V als seus predec.
                    if (distancia.get(w) == distancia.get(v) + 1) {
                        sigma.put(w, sigma.get(w) + sigma.get(v));
                        predecessors.get(w).add(v);
                    }
                }
            }

            // Acumulació de dependències
            Map<K, Double> delta = new HashMap<>();
            for (K v : tots) {
                delta.put(v, 0.0);
            }

            while (!pila.isEmpty()) {
                K w = pila.pop();
                for (K v : predecessors.get(w)) {
                    double c = ((double) sigma.get(v) / sigma.get(w)) * (1.0 + delta.get(w));
                    delta.put(v, delta.get(v) + c);
                }

                if (!w.equals(s)) {
                    centralitat.put(w, centralitat.get(w) + delta.get(w));
                }
            }
        }

        // Normalització per graf dirigit (dividir per (N-1)(N-2))
        int N = tots.size();
        if (N > 2) {
            for (K v : tots) {
                centralitat.put(v, centralitat.get(v) / ((N - 1) * (N - 2)));
            }
        }

        return centralitat;
    }
}

