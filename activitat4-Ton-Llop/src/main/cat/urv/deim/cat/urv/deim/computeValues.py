import sys
import os
import networkx as nx
import csv

def main(input_file):
    # Load the graph from the GraphML file
    G = nx.read_graphml(input_file)
    
    # Compute PageRank and Betweenness Centrality
    pagerank = nx.pagerank(G)
    betweenness = nx.betweenness_centrality(G)
    
    # Prepare data for CSV
    data = []
    for node in G.nodes():
        data.append({
            "id": node,
            "pagerank": pagerank[node],
            "betweenness": betweenness[node]
        })
    
    # Sort data by PageRank (descending)
    data.sort(key=lambda x: x["pagerank"], reverse=True)
    
    # Prepare output filename
    base_name = os.path.splitext(input_file)[0]
    output_file = "{}.csv".format(base_name)
    
    # Write to CSV with semicolon as delimiter
    with open(output_file, "w", newline='', encoding='utf-8') as csvfile:
        writer = csv.writer(csvfile, delimiter=';')
        writer.writerow(['id', 'pagerank', 'betweenness'])
        for row in data:
            writer.writerow([row["id"], row["pagerank"], row["betweenness"]])
    
    print("Results saved to {}".format(output_file))

if __name__ == "__main__":
    if len(sys.argv) != 2:
        print("Usage: python network_analysis.py <input.graphml>")
        sys.exit(1)
    main(sys.argv[1])
