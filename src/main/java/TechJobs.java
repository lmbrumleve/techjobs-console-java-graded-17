import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    static Scanner in = new Scanner(System.in);
    static boolean noResults = false;
    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View jobs by (type 'x' to quit):", actionChoices);

            if (actionChoice == null) {
                break;
            } else if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
//                    List<String> unsortedItems = new ArrayList<>();
//                    System.out.println(unsortedItems);
                    for (String item : results) {
//                        unsortedItems.add(item);
                        System.out.println(item);
                    }
//                    Collections.sort(unsortedItems);
//                    for (String item : unsortedItems) {
//
//                        System.out.println(item);
//                    }


                }


            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);
//                System.out.println(searchField);
                // What is their search term?
                System.out.println("\nSearch term:");
                String searchTerm = in.nextLine();
//                System.out.println(searchTerm);
//                System.out.println(JobData.findAll(searchField));
//                System.out.println(JobData.findByColumnAndValue(searchField, searchTerm));

                if (searchField.equals("all")) {
//                    System.out.println(JobData.findByValue(searchTerm));
                    printJobs(JobData.findByValue(searchTerm));
                } else if (!JobData.findAll(searchField).contains(searchTerm.toLowerCase())) {
                    System.out.println("No Results");
                    noResults = true;
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
//                    System.out.println(JobData.findByColumnAndValue(searchField, searchTerm));
                }
            }
        }
    }

    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        int choiceIdx = -1;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        int i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {
            if(noResults) {
                System.out.println(menuHeader);
                noResults = false;
            } else {
                System.out.println("\n" + menuHeader);
            }
            // Print available choices
            for (int j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            if (in.hasNextInt()) {
                choiceIdx = in.nextInt();
                in.nextLine();
            } else {
                String line = in.nextLine();
                boolean shouldQuit = line.equals("x");
                if (shouldQuit) {
                    return null;
                }
            }

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Print a list of jobs
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {
            for (Map<String, String> job : someJobs) {
                System.out.println("\n*****");
                for (String key : job.keySet()) {
                    String value = job.get(key);
                    System.out.println(key + ": " + value);
                }
                System.out.println("*****");
            }
        }

}
