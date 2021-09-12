package com.query;

import com.query.resource.QueryResource;

import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        QueryResource queryResource = new QueryResource();
        Scanner scanner = new Scanner(System.in);
        String ch = "y";
        do {
            System.out.println("Provide path for json file: ");
            String path = "";//scanner.next();

            path = "src/main/resources/query.json";
            displayQuery(queryResource.createQuery(path));

            System.out.println("Do you want to continue(y/Y) ?");
            ch = scanner.next().trim();
        } while (ch.equalsIgnoreCase("y"));
    }

    private static void displayQuery(List<String> query) {
        System.out.println("\nBelow are the queries formed: ");
        for (String sql : query) {
            System.out.println("Query: " + sql);
        }
    }
}
