import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class schoolsearch {

    static ArrayList<ArrayList<String>> table = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("students.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            table.add(new ArrayList<>(Arrays.asList(line.split(","))));
        }

        sc = new Scanner(System.in);
        System.out.println("Please enter search instructions");
        String input = sc.nextLine();
        while(!input.equals("Q") && !input.equals("Quit")) {
            input += " ";
            String[] inputArray = input.split(" ");
            if(inputArray[0].equals("S:") || inputArray[0].equals("Student:")) {

            }
            else if(inputArray[0].equals("T:") || inputArray[0].equals("Teacher:")) {
                teacher(inputArray[1]);
            }
            else if(inputArray[0].equals("B:") || inputArray[0].equals("Bus:")) {
                
            }
            else if(inputArray[0].equals("G:") || inputArray[0].equals("Grade:")) {
                
            }
            else if(inputArray[0].equals("A:") || inputArray[0].equals("Average:")) {
                average(inputArray[1]);
            }
            else if(inputArray[0].equals("I") || inputArray[0].equals("Info")) {
                
            }
            System.out.println("Please enter search instruction");
            input = sc.nextLine();
        }
    }

    public static void teacher(String lastname) {
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(6).equals(lastname))
                System.out.println(table.get(i).get(0) + " " + table.get(i).get(1));
        }

    }

    public static void average(String number) {
        double gpa = 0;
        int count = 0;
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(2).equals(number)) {
                gpa += Double.parseDouble(table.get(i).get(5));
                count++;
            }
        }
        System.out.println("Grade level:" + number + " Average GPA: " + gpa/count);
    }
}