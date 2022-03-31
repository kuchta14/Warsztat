package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class TaskManager {
    static final String FILE_NAME = "tasks.csv";

    public static void main(String[] args) {

        String[][] table = tasks(FILE_NAME);
        Options(table);

    }

    public static String[][] tasks(String fileName) {

        File file = new File(fileName);
        Path file2 = Paths.get(fileName);
        int a = 0;

        String[][] task = null;

        Scanner scan = null;

        try {
            scan = new Scanner(file);

            try {
                task = new String[Files.readAllLines(file2).size()][Files.readAllLines(file2).get(0).split(",").length];
                a = Files.readAllLines(file2).size();
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < a; i++) {

                String[] split = scan.nextLine().split(",");

                for (int j = 0; j < split.length; j++) {
                    task[i][j] = split[j];
                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("Brak pliku");
            System.exit(0);
        }
        return task;
    }

    public static String[][] Options(String[][] table) {
        System.out.println(ConsoleColors.WHITE + "Please select an option:");
        System.out.println(ConsoleColors.GREEN + "add");
        System.out.println(ConsoleColors.RED + "remove");
        System.out.println(ConsoleColors.YELLOW + "list");
        System.out.println(ConsoleColors.WHITE + "exit");

        Scanner scan2 = new Scanner(System.in);
        String input = scan2.nextLine();
        switch (input) {
            case "add":
                add(table);
            case "remove":
                remove(table);
            case "list":
                list(table);
            case "exit":
                save(FILE_NAME,table);
                System.out.println(ConsoleColors.RED + "papa");
                System.exit(0);
            default:
                System.out.println(ConsoleColors.BLUE + "Please select a correct option.");
        }

        return Options(table);
    }

    public static String[][] add(String[][] table) {
        String[][] table2 = table;
        String[] tab = new String[3];

        System.out.println("Please add task description");
        Scanner scan3 = new Scanner(System.in);
        tab[0] = scan3.nextLine();
        System.out.println("Please add task due date");
        Scanner scan4 = new Scanner(System.in);
        tab[1] = scan4.nextLine();
        System.out.println("Is your task important: true/false");
        Scanner scan5 = new Scanner(System.in);
        tab[2] = scan5.nextLine();

        table2 = Arrays.copyOf(table2, table2.length + 1);
        table2[table2.length - 1] = new String[3];

        for (int j = 0; j < tab.length; j++) {
            table2[table2.length - 1][j] = tab[j];
        }

        return Options(table2);
    }

    public static String[][] remove(String[][] table) {


        System.out.println("Please select number to remove.");
        Scanner scan2 = new Scanner(System.in);
        String input1 = scan2.nextLine();

        if (NumberUtils.isParsable(input1) && Integer.parseInt(input1) >= 0) {
            try {
                table = ArrayUtils.remove(table, Integer.parseInt(input1));
                System.out.println("Value was successfully deleted.");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Element not exist in tab");
                remove(table);
            }
        } else {
            System.out.println("Incorrect argument passed. Please give number greater or equal 0");
            remove(table);
        }


        return Options(table);
    }

    public static String[][] list(String[][] table) {

        for (int i = 0; i < table.length; i++) {
            System.out.println(i + ": " + Arrays.toString(table[i]));
        }

        return Options(table);
    }

    public static void save(String fileName, String[][] tab) {

        Path dir = Paths.get(fileName);
        String[] lines = new String[tab.length];

        for (int i = 0; i < tab.length; i++) {

            lines[i] = String.join(",", tab[i]);
        }
        try {
            Files.write(dir, Arrays.asList(lines));
        } catch (IOException ex) {
            ex.printStackTrace();

        }

    }

}
