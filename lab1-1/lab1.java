import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class lab1 {

    static ArrayList<ArrayList<String>> table = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("students.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            table.add(new ArrayList<>(Arrays.asList(line.split(","))));
        }

        sc = new Scanner(System.in);
        String input = sc.nextLine();
        while(!input.equals("Q") && !input.equals("Quit")) {
            input += " ";
            String[] inputArray = input.split(" ");
            if(inputArray[0].equals("S") || inputArray[0].equals("Student")) {

            }
            else if(inputArray[0].equals("T") || inputArray[0].equals("Teacher")) {
                
            }
            else if(inputArray[0].equals("B") || inputArray[0].equals("Bus")) {
                
            }
            else if(inputArray[0].equals("G") || inputArray[0].equals("Grade")) {
                
            }
            else if(inputArray[0].equals("A") || inputArray[0].equals("Average")) {
                
            }
            else if(inputArray[0].equals("I") || inputArray[0].equals("Info")) {
                
            }
            input = sc.nextLine();
        }
    }
}