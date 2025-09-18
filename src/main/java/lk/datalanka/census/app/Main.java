package lk.datalanka.census.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Console entry point with a menu that delegates to the controller.
 */
public final class Main {

    private Main() {}

    public static void main(String[] args) {
        AppController controller = new AppController();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addRegionInteractively(controller, scanner);
                    break;
                case "2":
                    controller.addSampleData();
                    break;
                case "3":
                    controller.printAllRegions();
                    break;
                case "4":
                    controller.runAgeGroupAnalysis();
                    break;
                case "5":
                    controller.runLiteracyAnalysis();
                    break;
                case "6":
                    controller.runIncomeAnalysis();
                    break;
                case "7":
                    controller.exportAllReports();
                    break;
                case "8":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Unknown option");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\nPopulation Analytics");
        System.out.println("1. Add region interactively");
        System.out.println("2. Load sample data");
        System.out.println("3. List all regions");
        System.out.println("4. Run Age Group analysis");
        System.out.println("5. Run Literacy analysis");
        System.out.println("6. Run Income analysis");
        System.out.println("7. Export all reports");
        System.out.println("8. Exit");
        System.out.print("Select: ");
    }

    private static void addRegionInteractively(AppController controller, Scanner scanner) {
        System.out.print("Type (URBAN/RURAL): ");
        String type = scanner.nextLine().trim();
        System.out.print("Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Population: ");
        int population = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Literacy rate (0-100): ");
        double literacy = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Average income (Rs. monthly): ");
        double income = Double.parseDouble(scanner.nextLine().trim());

        Map<String, Integer> ag = new HashMap<>();
        ag.put("0-14", askInt(scanner, "Age 0-14 count: "));
        ag.put("15-24", askInt(scanner, "Age 15-24 count: "));
        ag.put("25-34", askInt(scanner, "Age 25-34 count: "));
        ag.put("35-44", askInt(scanner, "Age 35-44 count: "));
        ag.put("45-64", askInt(scanner, "Age 45-64 count: "));
        ag.put("65+", askInt(scanner, "Age 65+ count: "));

        controller.addRegion(type, name, population, literacy, income, ag);
    }

    private static int askInt(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return Integer.parseInt(scanner.nextLine().trim());
    }
}


