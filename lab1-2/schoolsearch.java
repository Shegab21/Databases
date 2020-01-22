//Tania Kabiraj and Salma Hegab
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.lang.model.util.ElementScanner6;

import java.util.*;


public class schoolsearch {

    //order of old table: StLastName, StFirstName, Grade, Classroom, Bus, GPA, TLastName, TFirstName
    //order of new table: StLastName, StFirstName, Grade, Classroom, Bus, GPA
    static ArrayList<ArrayList<String>> table = new ArrayList<>();
    static ArrayList<ArrayList<String>> teachers = new ArrayList<>();

    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("list.txt");
        if(!file.exists())
            return;
        Scanner sc = new Scanner(file);
        
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            table.add(new ArrayList<>(Arrays.asList(line.split("\\s*,\\s*"))));
            if(table.get(table.size() - 1).size() != 6)
                return;
        }

        file = new File("teachers.txt");
        if(!file.exists())
            return;
        sc = new Scanner(file);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            teachers.add(new ArrayList<>(Arrays.asList(line.split("\\s*,\\s*"))));
            if(teachers.get(teachers.size() - 1).size() != 3)
                return;
        }

        System.out.println("Here are the possible search instructions:");
        System.out.println("• S[tudent]: <lastname> [B[us]]\n• T[eacher]: <lastname>\n• B[us]: <number>\n" + 
        "• G[rade]: <number> [H[igh]|L[ow]]\n• A[verage]: <number>\n• I[nfo]\n• A[nalyze]G[rade]: <lastname>\n• " +
        "A[nalyze]T[eacher]: <lastname>\n• A[nalyze]B[us]: <lastname>\n• Q[uit]");

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
            //the default would print out students
            //for the teachers, put a T[eacher] flag at the end
            else if(inputArray.length > 1 && inputArray[0].equals("C:") || inputArray[0].equals("Classroom:")) {
                if(inputArray.length > 2)
                {
                    classroomTeacher(inputArray[1]);
                }
                else
                {
                    classroomStudent(inputArray[1]);
                }
            }
            else if(inputArray.length > 1 && inputArray[0].equals("E:") || inputArray[0].equals("Enrollment:"))
            {
                enrollment();
            }
            /*else if(inputArray[0].equals("G:") || inputArray[0].equals("Grade:")) {
                
            }*/
            else if(inputArray.length > 1 && inputArray[0].equals("A:") || inputArray[0].equals("Average:")) {
                average(inputArray[1]);
            }
            else if(inputArray[0].equals("I") || inputArray[0].equals("Info")) {
                info();
            }
            else if(inputArray.length > 1 && inputArray[0].equals("AG:") || inputArray[0].equals("AnalyzeGrades:")) {
                analyzeGrade(inputArray[1]);
            }
            else if(inputArray.length > 1 && inputArray[0].equals("AT:") || inputArray[0].equals("AnalyzeTeacher:")) {
                analyzeTeacher(inputArray[1]);
            }
            else if(inputArray.length > 1 && inputArray[0].equals("AB:") || inputArray[0].equals("AnalyzeBus:")) {
                analyzeBus(inputArray[1]);
            }
            System.out.println("Please enter search instruction");
            input = sc.nextLine();
        }
    }

    private static void analyzeBus(String student) {
        String studentGrade = "";
        String bus = "";
        double totalGpa = 0;
        int count = 0;
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(0).equals(student)){
                studentGrade = table.get(i).get(5);
                bus = table.get(i).get(4);
            }
        }
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(4).equals(bus)) {
                count++;
                totalGpa += Double.parseDouble(table.get(i).get(5));
            }
        }
        System.out.println("Student GPA: " + studentGrade);
        System.out.println("Average GPA of students on bus " + bus + ": " + totalGpa/count);
    }

    private static void analyzeTeacher(String student) {
        String studentGrade = "";
        String classroom = "";
        String teacher = "";
        double totalGpa = 0;
        int count = 0;
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(0).equals(student)){
                studentGrade = table.get(i).get(5);
                classroom = table.get(i).get(3);
            }
        }
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(3).equals(classroom)) {
                count++;
                totalGpa += Double.parseDouble(table.get(i).get(5));
            }
        }
        for(int i = 0; i < teachers.size(); i++) {
            if(teachers.get(i).get(2).equals(classroom))
                teacher = teachers.get(i).get(0);
        }
        System.out.println("Student GPA: " + studentGrade);
        System.out.println("Average GPA of students with teacher " + teacher + ": " + totalGpa/count);
    }

    private static void analyzeGrade(String student) {
        String studentGrade = "";
        String grade = "";
        double totalGpa = 0;
        int count = 0;
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(0).equals(student)){
                studentGrade = table.get(i).get(5);
                grade = table.get(i).get(2);
            }
        }
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(2).equals(grade)) {
                count++;
                totalGpa += Double.parseDouble(table.get(i).get(5));
            }
        }
        System.out.println("Student GPA: " + studentGrade);
        System.out.println("Average GPA of students in grade " + grade + ": " + totalGpa/count);
    }

    public static void student(String lastname) {
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(0).equals(lastname)) {
                for(int j = 0; j < teachers.size(); j++) {
                    if(table.get(i).get(3).equals(teachers.get(j).get(2))){
                        System.out.println(table.get(i).get(0) + "," + table.get(i).get(1) + ","+ table.get(i).get(2) + ","
                        + table.get(i).get(3) + "," + teachers.get(j).get(0) + "," + teachers.get(j).get(1));
                    }
                }
                //     System.out.println(table.get(i).get(0) + "," + table.get(i).get(1) + ","+ table.get(i).get(2) + ","
                // + table.get(i).get(3) + "," + table.get(i).get(6) + "," + table.get(i).get(7));
            }
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
        // for(int i = 0; i < table.size(); i++) {
        //     if(table.get(i).get(6).equals(lastname))
        //         System.out.println(table.get(i).get(0) + "," + table.get(i).get(1));
        // }
        // System.out.println();
        String classroom = "";
        for(int i = 0; i < teachers.size(); i++) {
            if(teachers.get(i).get(0).equals(lastname)){
                classroom = teachers.get(i).get(2);
            }
        }
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(3).equals(classroom))
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

    public static void classroomTeacher(String classroom) {
        /*for(int i = 0; i < teachers.size(); i++) {
            if(teachers.get(i).get(2).equals(classroom))
                System.out.println(teachers.get(i).get(0) + "," + teachers.get(i).get(1));
        }
        System.out.println();*/
        ArrayList<String> teachersByClassroom = findClassroomTeacher(classroom);

        for(int i = 0; i < teachersByClassroom.size()-1; i=i+2) {
            System.out.println(teachersByClassroom.get(i) + "," + teachers.get(i+1));
        }
        System.out.println();
    }

    //given classroom returns array with teacher's name
    //teacher's name takes up two spots in the array
    public static ArrayList<String> findClassroomTeacher(String classroom) {
        //ArrayList<ArrayList<String>> teachersByClassroom = new ArrayList<>();
        ArrayList<String> teachersByClassroom = new ArrayList<>();
        //int countOfTeachers = 0;
        for(int i = 0; i < teachers.size(); i++) {
            if(teachers.get(i).get(2).equals(classroom)){
                //teachersByClassroom.add(new ArrayList<>(teachers.get(i).get(0),teachers.get(i).get(1)));
                teachersByClassroom.add(teachers.get(i).get(0));
                teachersByClassroom.add(teachers.get(i).get(1));
            }
        }
        return teachersByClassroom;
        //System.out.println();
    }


    public static void teacherByGrade(String grade) {
        //ArrayList<ArrayList<String>> teachersByClassroom = new ArrayList<>();
        ArrayList<String> classrooms = new ArrayList<>();
        ArrayList<String> teachersOfGrade = new ArrayList<>();
        //find classrooms with given grade
        for(int i = 0; i < table.size(); i++) {
            /*if(!teachersByClassroom.contains(table.get(i).get(3)) && table.get(i).get(2).equals(grade)){
                //System.out.println(table.get(i).get(0) + "," + table.get(i).get(1));
                teachersByClassroom.add(new ArrayList<String>(table.get(i).get(3),findClassroomTeacher(table.get(i).get(3))));
                
            }*/

            if(!classrooms.contains(table.get(i).get(3)) && table.get(i).get(2).equals(grade))
            {
                classrooms.add(table.get(i).get(3));
                teachersOfGrade.addAll(findClassroomTeacher(table.get(i).get(3)));
            }

        }

        for(int i = 0; i < teachersOfGrade.size()-1; i=i+2) {
            System.out.println(teachersOfGrade.get(i) + "," + teachers.get(i+1));
        }

        System.out.println();
    }

    public static void classroomStudent(String classroom) {
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(3).equals(classroom))
                System.out.println(table.get(i).get(0) + "," + table.get(i).get(1));
        }
        System.out.println();
    }

    public static void enrollment() {
        ArrayList<ArrayList<Integer>> classroomCount = new ArrayList<>();
        // first index of each inner array is classroom number and second is classroom count
        ArrayList<String> classroomsAdded = new ArrayList<>();
        for(int i = 0; i < table.size(); i++) {
            int classroomNumber = Integer.parseInt(table.get(i).get(3));
            if(classroomsAdded.contains(table.get(i).get(3)))
            {
                //classroomCount.indexOf(classroomNumber);
                for (ArrayList<Integer> list : classroomCount) {
                    if(list.get(0).equals(classroomNumber))
                    {
                        int oldCount = classroomCount.get(list.indexOf(classroomNumber)).get(1);
                        classroomCount.get(list.indexOf(classroomNumber)).set(1,oldCount + 1);
                        break;
                    }
                    
                }
            }
            else
            {
                classroomsAdded.add(table.get(i).get(3));
                classroomCount.add(new ArrayList<Integer>( 
                    Arrays.asList(classroomNumber, 1)));
            }

            

                //System.out.println(table.get(i).get(0) + "," + table.get(i).get(1) + ","+ table.get(i).get(4));
        }
        Collections.sort(classroomCount, new Comparator<ArrayList<Integer>>() {    
            //@Override
            public int compare(ArrayList<Integer> list1, ArrayList<Integer> list2) {
                return list1.get(0) - list2.get(0);
            }               
        });

        for(ArrayList<Integer> list : classroomCount)
        {

        }


        System.out.println();
        
    }

    public static void grade(String grade, String typeOfGPA) {
        if(typeOfGPA.equals("H") || typeOfGPA.equals("High"))
        {
            gradeWithHighestGPA(grade);
        }
        else if(typeOfGPA.equals("L") || typeOfGPA.equals("Low"))
        {
            gradeWithLowestGPA(grade);
        }
        else
        {
            //T[eacher]
            teacherByGrade(grade);

        }
        

    }

    public static void gradeWithHighestGPA(String grade) {

        //array index for the student with the highest GPA of those with the given grade
        int index = -1;
        double highestGPA = 0.0;
        //the out variable in case the highest gpa changes
        double gpaNewDouble = 0.0;
        String classroom = "";
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(2).equals(grade)){
                gpaNewDouble = Double.parseDouble(table.get(i).get(5));
                if(gpaNewDouble > highestGPA)
                {
                    highestGPA = gpaNewDouble;
                    index = i;
                    classroom = table.get(i).get(3);
                }
            }
        }
        for(int i = 0; i < teachers.size(); i++) {
            if(teachers.get(i).get(2).equals(classroom)) {
                System.out.println(table.get(index).get(0) + "," + table.get(index).get(1) + "," + table.get(index).get(5)
                            + "," + teachers.get(i).get(0) + "," + teachers.get(i).get(1) + "," + table.get(index).get(4));
                System.out.println();
                break;
            }
        }
        // System.out.println(table.get(index).get(0) + "," + table.get(index).get(1) + "," + table.get(index).get(5)
        //                     + "," + table.get(index).get(6) + "," + table.get(index).get(7) + "," + table.get(index).get(4));
        // System.out.println();
    }

    public static void gradeWithLowestGPA(String grade) {

        //array index for the student with the lowest GPA of those with the given grade
        int index = -1;
        double lowestGPA = 4.0;
        //the out variable in case the lowest gpa changes
        double gpaNewDouble = 0.0;
        String classroom = "";
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(2).equals(grade)){
                gpaNewDouble = Double.parseDouble(table.get(i).get(5));
                if(gpaNewDouble < lowestGPA)
                {
                    lowestGPA = gpaNewDouble;
                    index = i;
                    classroom = table.get(i).get(3);
                }
            }
        }
        for(int i = 0; i < teachers.size(); i++) {
            if(teachers.get(i).get(2).equals(classroom)) {
                System.out.println(table.get(index).get(0) + "," + table.get(index).get(1) + "," + table.get(index).get(5)
                            + "," + teachers.get(i).get(0) + "," + teachers.get(i).get(1) + "," + table.get(index).get(4));
                System.out.println();
                break;
            }
        }
        // System.out.println(table.get(index).get(0) + "," + table.get(index).get(1) + "," + table.get(index).get(5)
        //                     + "," + table.get(index).get(6) + "," + table.get(index).get(7) + "," + table.get(index).get(4));
        // System.out.println();

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
