package CrimeDispatch;

import CrimeDispatch.Dijkstra;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * The entry point for the Birmingham Crime Pattern Analysis & Police Dispatch System.
 * Creates a MyController instance and launches the text-based user interface.
 */
public class Main {

    public static void main(String[] args) {
        //new TUI(new MyController());
        // System.out.println(new MyController().displayIncidentsByPriority("2026-04-14"));

        // Load graph

        // Initialised graph
        CrimeDispatch.DirectedWeightedGraph<CrimeDispatch.Vertex<String>, CrimeDispatch.Edge, Double> dwgraph = new CrimeDispatch.DirectedWeightedGraph();

        try (BufferedReader br = new BufferedReader(new FileReader("data/road_network.csv"))) {
            br.readLine(); // Skips header
            String line;

            while((line = br.readLine()) != null) {
                String[] data = line.split(",");
                CrimeDispatch.Vertex source = new CrimeDispatch.Vertex(data[0]);
                CrimeDispatch.Vertex target = new CrimeDispatch.Vertex(data[1]);

                dwgraph.addVertex(source);
                dwgraph.addVertex(target);
                dwgraph.addEdge(new CrimeDispatch.Edge<>(source, target, Double.parseDouble(data[2])));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.print(dwgraph.toString());

        System.out.print("\n\n\nFindest shortest path from Steelhouse Lane to Sparkhill\n");

        Dijkstra dijkstra = new Dijkstra(dwgraph);

        List<CrimeDispatch.Vertex> path = dijkstra.findShortestRoute(
                dwgraph.getVertex("Steelhouse Lane"),
                dwgraph.getVertex("Sparkhill")
        );

        for(CrimeDispatch.Vertex node : path) {
            System.out.printf("%s -> ", node.toString());
        }
        System.out.println();

        // System.out.print(new CrimeDispatch.MyController().analyseCrimeHotspots("2026-04-14"));
        }
    }
