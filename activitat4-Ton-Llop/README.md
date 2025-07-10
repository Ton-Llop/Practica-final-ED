[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/cawOqBwk)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=19332030)
# Activitat 4: Mètodes per mesurar la importància dels vèrtexs d’un graf

Aquesta darrera activitat és el projecte final de l’assignatura i té un pes del **50 % de la nota**. Haureu d’utilitzar la majoria de les estructures de dades desenvolupades en les activitats anteriors. No disposeu de cap codi base ni d’un conjunt de proves; per tant, haureu de construir el projecte des de zero i lliurar-lo a l’Activitat 4.

Aquest any implementareu dos algorismes que permeten calcular la importància d’un node dins d’un graf: **PageRank** i **Betweenness Centrality**.

* **PageRank**  
  L’algorisme PageRank permet *classificar* els vèrtexs d’un graf segons la importància derivada dels enllaços entrants. N’hem explicat el funcionament a la classe de teoria, però en trobareu una descripció més detallada a la Viquipèdia: <https://es.wikipedia.org/wiki/PageRank>.

* **Betweenness Centrality**  
  Aquesta mesura calcula la *centralitat* dels vèrtexs tenint en compte el nombre de camins mínims que passen per cadascun d’ells. Podeu consultar‐ne els detalls aquí: <https://en.wikipedia.org/wiki/Betweenness_centrality>.

## Objectius

A la vostra classe `Graf` haureu de crear dos mètodes que:

1. Calculin el rànquing de cada vèrtex segons PageRank.  
2. Calculin la Betweenness Centrality de cada vèrtex.  

Un cop obtinguts els resultats, els haureu d’exportar a un fitxer CSV.

## Requisits i recomanacions

* **Tipus de graf**  
  Només cal considerar grafs *dirigits* i *no etiquetats*.

* **Càrrega des de fitxer**  
  Haureu de ser capaços de carregar un graf a partir d'un fitxer. Actualment hi ha varis formats per a descriure l'estructura d'un graf, nosaltres us demanem que sigueu capaços de llegir un d'aquests dos formats i que els carregueu a la vostra estructura de tipus graf. Els dos formats que us proposem son el **GraphML** (https://en.wikipedia.org/wiki/GraphML) i el **Pajek NET**(https://gephi.org/users/supported-graph-formats/pajek-net-format/). IMPORTANT: Per a tractar el fitxer GraphML (que es una versió de XML) us deixem que feu servir alguna llibreria externa de Java, com Java XML per a facilitar-vos la lectura de les dades.

* **Conversió i visualització**  
  Si voleu convertir grafs entre diferents formats podeu fer servir eines com **Gephi** (https://gephi.org) o **Pajek** (http://mrvar.fdv.uni-lj.si/pajek/) per a convertir-los i/o visualitzar-los.

* **Paràmetres del PageRank**
  L'algorisme del pagerank té dos paràmetres que cal tenir en compte: per un costat cal definir el valor del *damping factor*, que per defecte acostuma a ser 0.85 però que s'hauria de poder canviar i provar amb altres valors, i el criteri de convergència per decidir quan ja s'ha arribat a un rànking que no canvia. Teniu més detalls dels dos paràmetres a la plana de la wikipedia.

* **Primeres proves**  
  Comenceu amb un graf petit (100 o 1000 vèrtexs) i, un cop els algorismes funcionin, podeu provar si escalen amb grafs més grans. Podeu fer servir qualsevol tipus de graf, no ha de ser necessàriament un graf que representi la web (Això sí, ha de ser un graf dirigit).

* **Fonts de grafs reals**
  Per a provar-ho amb grafs reals que representen part de la WWW hi ha varies pàgines per Internet den les quals hi ha grafs de diferents mides. Alguns exemples:
  - <https://networkrepository.com/polblogs.php>  
  - <https://snap.stanford.edu/data/index.html> (secció *Webgraphs*)

* **Sortida**  
  Genereu un CSV (separador `;`) amb tres columnes, ordenades pel **valor de pagerank**. Tingueu en compte que si el graf es gran possiblement necessitareu posar 6 o 8 decimals per a poder veure les diferències de valors entre vèrtexs.   

  ```
  id;pagerank;betweenness
  ```

  Com a tasca opcional, tambe es proposa poder exportar el graf en format GraphML, incloent com a propietat dels vèrtexs el valor corresponent corresponent al rànquing o a la centralitat. D'aquesta manera es podrà visualitzar el resultat dins d'eines com Gephi i obtenir visualitzacions semblants a aquesta, posant per exemple com a mida del graf el seu valor del vèrtex.

![exemple_visualitzacio_pagerank](https://upload.wikimedia.org/wikipedia/commons/f/fb/PageRanks-Example.svg)
  
* **Validació**  
  Properament rebreu un petit script en Python que calcularà els mateixos valors; podreu comparar‐los amb els vostres resultats.

## Criteris d’avaluació

* Que es facin servir de forma adequada les estructures dissenyades en les activitats prèvies de la classe
* Que s'hagi implementat correctament l'algortime del Pagerank i un mètode per a calcular la betweenness centrality.
* Que el codi sigui capaç de llegir grafs amb el format explicat anteriorment, i carregui les dades a les vostres estructures de dades.
* Que el codi sigui escalable per a poder executar xarxes el més grans possibles.
* Us demanem que ens presenteu dins del vostre projecte els resultats obtinguts per almenys 5 xarxes diferents, tot i que es valorarà que proveu amb xarxes diverses. Es valorarà la capacitat de l'algorisme de tractar amb xarxes grans (10K, 100K vèrtexs o més) i de tenir els resultats el mes òptims possibles (el més semblants als algoritmes ja implementats en altres llibreries).
