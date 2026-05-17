package crimedispatch;

import org.jgrapht.graph.*;
import org.jgrapht.util.SupplierUtil;

import java.io.*;
import java.util.Scanner;
import java.util.function.Supplier;

public class Graph {

    public static void main(String[] args) {

        Supplier<String> vertexSupplier = SupplierUtil.createStringSupplier();
        Supplier<DefaultWeightedEdge> edgeSupplier = DefaultWeightedEdge::new;

        DefaultDirectedWeightedGraph<String, DefaultWeightedEdge> road_network =
                new DefaultDirectedWeightedGraph<>(vertexSupplier, edgeSupplier);

        File road_network_file = new File(
                Graph.class.getResource("/road_network.csv").getFile()
        );

        try (Scanner reader = new Scanner(road_network_file)) {
            reader.nextLine(); // skips csv header

            while (reader.hasNextLine()) {
                String[] data_split = reader.nextLine().split(",");

                String Va = data_split[0];
                String Vb = data_split[1];
                double weight = Double.parseDouble(data_split[2]);

                road_network.addVertex(Va);
                road_network.addVertex(Vb);

                DefaultWeightedEdge edge = road_network.addEdge(Va, Vb);
                road_network.setEdgeWeight(edge, weight);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error occurred while reading file");
            e.printStackTrace();
        }

        System.out.println(road_network);
    }
}