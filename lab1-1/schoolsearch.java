//Tania Kabiraj and Salma Hegab
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.*;


public class schoolsearch {

    //order of table: StLastName, StFirstName, Grade, Classroom, Bus, GPA, TLastName, TFirstName
    static ArrayList<ArrayList<String>> table = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("students.txt");
        if(!file.exists())
            return;
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            table.add(new ArrayList<>(Arrays.asList(line.split(","))));
            if(table.get(table.size() - 1).size() != 8)
                return;
        }

        System.out.println("Here are the possible search instructions:");
        System.out.println("• S[tudent]: <lastname> [B[us]]\n• T[eacher]: <lastname>\n• B[us]: <number>\n" + 
        "• G[rade]: <number> [H[igh]|L[ow]]\n• A[verage]: <number>\n• I[nfo]\n• Q[uit]");

        sc = new Scanner(System.in);
        System.out.println("Please enter search instructions");
        String input = sc.nextLine();
        while(!input.equals("Q") && !input.equals("Quit")) {
            input += " ";
            String[] inputArray = input.split(" ");
            if(inputArray.length > 1 && inputArray[0].equals("S:") || inputArray[0].equals("Student:")) {
                if(inputArray.length > 2)
                {
                    student(inputArray[1], inputArray[2]);
                }
                else
                {
                    student(inputArray[1]);
                }
                
            }
            else if(inputArray.length > 1 && inputArray[0].equals("T:") || inputArray[0].equals("Teacher:")) {
                teacher(inputArray[1]);
            }
            else if(inputArray.length > 1 && inputArray[0].equals("G:") || inputArray[0].equals("Grade:")) {
                if(inputArray.length > 2)
                {
                    grade(inputArray[1], inputArray[2]);
                }
                else
                {
                    grade(inputArray[1]);
                }
                
            }
            else if(inputArray.length > 1 && inputArray[0].equals("B:") || inputArray[0].equals("Bus:")) {
                bus(inputArray[1]);
            }
            else if(inputArray[0].equals("G:") || inputArray[0].equals("Grade:")) {
                
            }
            else if(inputArray.length > 1 && inputArray[0].equals("A:") || inputArray[0].equals("Average:")) {
                average(inputArray[1]);
            }
            else if(inputArray[0].equals("I") || inputArray[0].equals("Info")) {
                info();
            }
            System.out.println("Please enter search instruction");
            input = sc.nextLine();
        }
    }

    public static void student(String lastname) {
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(0).equals(lastname))
                System.out.println(table.get(i).get(0) + "," + table.get(i).get(1) + ","+ table.get(i).get(2) + ","
                + table.get(i).get(3) + "," + table.get(i).get(6) + "," + table.get(i).get(7));
        }
        System.out.println();

    }

    public static void student(String lastname, String busNumber) {
        if(busNumber.equals("B") || busNumber.equals("Bus")){
            for(int i = 0; i < table.size(); i++) {
                if(table.get(i).get(0).equals(lastname))
                    System.out.println(table.get(i).get(0) + "," + table.get(i).get(1) + ","+ table.get(i).get(4));
            }
            System.out.println();
        }
    }

    public static void teacher(String lastname) {
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(6).equals(lastname))
                System.out.println(table.get(i).get(0) + "," + table.get(i).get(1));
        }
        System.out.println();

    }

    public static void grade(String grade) {
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(2).equals(grade))
                System.out.println(table.get(i).get(0) + "," + table.get(i).get(1));
        }
        System.out.println();
    }

    public static void grade(String grade, String typeOfGPA) {
        if(typeOfGPA.equals("H") || typeOfGPA.equals("High"))
        {
            gradeWithHighestGPA(grade);
        }
        else
        {
            gradeWithLowestGPA(grade);
        }
        

    }

    public static void gradeWithHighestGPA(String grade) {

        //array index for the student with the highest GPA of those with the given grade
        int index = -1;
        double highestGPA = 0.0;
        //the out variable in case the highest gpa changes
        double gpaNewDouble = 0.0;
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(2).equals(grade)){
                gpaNewDouble = Double.parseDouble(table.get(i).get(5));
                if(gpaNewDouble > highestGPA)
                {
                    highestGPA = gpaNewDouble;
                    index = i;
                }
            }
        }
        System.out.println(table.get(index).get(0) + "," + table.get(index).get(1) + "," + table.get(index).get(5)
                            + "," + table.get(index).get(6) + "," + table.get(index).get(7) + "," + table.get(index).get(4));
        System.out.println();
    }

    public static void gradeWithLowestGPA(String grade) {

        //array index for the student with the lowest GPA of those with the given grade
        int index = -1;
        double lowestGPA = 4.0;
        //the out variable in case the lowest gpa changes
        double gpaNewDouble = 0.0;
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(2).equals(grade)){
                gpaNewDouble = Double.parseDouble(table.get(i).get(5));
                if(gpaNewDouble < lowestGPA)
                {
                    lowestGPA = gpaNewDouble;
                    index = i;
                }
            }
        }
        System.out.println(table.get(index).get(0) + "," + table.get(index).get(1) + "," + table.get(index).get(5)
                            + "," + table.get(index).get(6) + "," + table.get(index).get(7) + "," + table.get(index).get(4));
        System.out.println();

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
        System.out.println(number + "," + gpa/count);
        System.out.println();
    }

    public static void bus(String number) {
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(4).equals(number))
                System.out.println(table.get(i).get(1) + "," + table.get(i).get(0) + "," +
                    table.get(i).get(2) + "," + table.get(i).get(3));
        }
        System.out.println();
    }
    
    public static void info() {
        int[] students = new int[7];
        for(int i = 0; i < table.size(); i++) {
            int grade = Integer.parseInt(table.get(i).get(2));
            students[grade] = students[grade] + 1;
        }
        for(int i = 0; i < students.length; i++) {
            System.out.println(i + ": " + students[i]);
        }
    }
}
