//Tania Kabiraj and Salma Hegab
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
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

        System.out.println("\nHere are the possible search instructions:\n");
        System.out.println("• S[tudent]: <lastname> [B[us]]\n• T[eacher]: <lastname>\n• B[us]: <number>\n" + 
        "• G[rade]: <number> [H[igh] | L[ow] | T[eacher]]\n• A[verage]: <number>\n" +
        "• C[lassroom]: <number> [T[eacher]]\n• E[nrollment]\n• I[nfo]\n• A[nalyze]G[rade]: <lastname>\n• " +
        "A[nalyze]T[eacher]: <lastname>\n• A[nalyze]B[us]: <lastname>\n• Q[uit]\n");

        sc = new Scanner(System.in);
        System.out.println("Please enter search instructions");
        String input = sc.nextLine();
        System.out.println();
        while(!input.equals("Q") && !input.equals("Quit")) {
            input += " ";
            String[] inputArray = input.split(" ");
            if(inputArray.length > 1 && inputArray.length < 4 && inputArray[0].equals("S:") || inputArray[0].equals("Student:")) {
                if(inputArray.length == 3)
                {
                    student(inputArray[1], inputArray[2]);
                    
                    
                }
                else
                {
                    student(inputArray[1]);
                    
                }
                
            }
            else if(inputArray.length == 2 && inputArray[0].equals("T:") || inputArray[0].equals("Teacher:")) {
                teacher(inputArray[1]);
            }
            else if(inputArray.length > 1 && inputArray.length < 4 && inputArray[0].equals("G:") || inputArray[0].equals("Grade:")) {
                if(inputArray.length == 3)
                {
                    grade(inputArray[1], inputArray[2]);
                }
                else
                {
                    grade(inputArray[1]);
                }
                
            }
            else if(inputArray.length == 2 && inputArray[0].equals("B:") || inputArray[0].equals("Bus:")) {
                bus(inputArray[1]);
            }
            //the default would print out students
            //for the teachers, put a T[eacher] flag at the end
            else if(inputArray.length > 1 && inputArray.length < 4 && inputArray[0].equals("C:") || inputArray[0].equals("Classroom:")) {
                if(inputArray.length == 2)
                {
                    classroomStudent(inputArray[1]);
                }
                else if(inputArray.length == 3 && inputArray[2].equals("T") || inputArray[2].equals("Teacher") )
                {
                    classroomTeacher(inputArray[1]);
                }
                else 
                {
                    System.out.println("Invalid input.\n");
                }
            }
            else if(inputArray.length == 1 && inputArray[0].equals("E") || inputArray[0].equals("Enrollment"))
            {
                enrollment();
            }
            /*else if(inputArray[0].equals("G:") || inputArray[0].equals("Grade:")) {
                
            }*/
            else if(inputArray.length == 2 && inputArray[0].equals("A:") || inputArray[0].equals("Average:")) {
                average(inputArray[1]);
            }
            else if(inputArray.length == 1 && inputArray[0].equals("I") || inputArray[0].equals("Info")) {
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
            else
            {
                System.out.println("Invalid input.\n");
            }
            System.out.println("Please enter search instruction");
            input = sc.nextLine();
            System.out.println();
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
        boolean contains = false;
        for(int i = 0; i < table.size(); i++) {
            
            if(table.get(i).get(0).equals(lastname)) {
                
                for(int j = 0; j < teachers.size(); j++) {
                    
                    if(table.get(i).get(3).equals(teachers.get(j).get(2))){
                        contains = true;
                        System.out.println(table.get(i).get(0) + "," + table.get(i).get(1) + ","+ table.get(i).get(2) + ","
                        + table.get(i).get(3) + "," + teachers.get(j).get(0) + "," + teachers.get(j).get(1));
                    }
                }
                //     System.out.println(table.get(i).get(0) + "," + table.get(i).get(1) + ","+ table.get(i).get(2) + ","
                // + table.get(i).get(3) + "," + table.get(i).get(6) + "," + table.get(i).get(7));
            }
        }
        if(!contains)
        {
            System.out.println("No students with this last name.");
        }
        System.out.println();

    }

    public static void student(String lastname, String busNumber) {
        boolean contains = false;
        if(busNumber.equals("B") || busNumber.equals("Bus")){
            for(int i = 0; i < table.size(); i++) {
                if(table.get(i).get(0).equals(lastname)){
                    contains = true;
                    System.out.println(table.get(i).get(0) + "," + table.get(i).get(1) + ","+ table.get(i).get(4));
                }
            }
            if(!contains)
            {
                System.out.println("No students with this last name.");
            }
            System.out.println();
        }
        else
        {
            System.out.println("Invalid input.\n");
        }
        
    }

    public static void teacher(String lastname) {
        // for(int i = 0; i < table.size(); i++) {
        //     if(table.get(i).get(6).equals(lastname))
        //         System.out.println(table.get(i).get(0) + "," + table.get(i).get(1));
        // }
        // System.out.println();
        String classroom = "";
        boolean contains = false;
        for(int i = 0; i < teachers.size(); i++) {
            if(teachers.get(i).get(0).equals(lastname)){
                classroom = teachers.get(i).get(2);
            }
        }
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(3).equals(classroom)){
                contains = true;
                System.out.println(table.get(i).get(0) + "," + table.get(i).get(1));
            }
        }
        if(!contains)
        {
            System.out.println("No students of the teacher with this last name.");
        }
        System.out.println();

    }

    public static void grade(String grade) {
        boolean contains = false;
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(2).equals(grade))
            {
                contains = true;
                System.out.println(table.get(i).get(0) + "," + table.get(i).get(1));
            }
        }
        if(!contains)
        {
            System.out.println("No students in this grade.");
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
        boolean contains = false;

        for(int i = 0; i < teachersByClassroom.size()-1; i=i+2) {
            contains = true;
            System.out.println(teachersByClassroom.get(i) + "," + teachersByClassroom.get(i+1));
        }
        if(!contains)
        {
            System.out.println("No teachers in this classroom.");
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
        boolean contains = false;
        //find classrooms with given grade
        for(int i = 0; i < table.size(); i++) {
            /*if(!teachersByClassroom.contains(table.get(i).get(3)) && table.get(i).get(2).equals(grade)){
                //System.out.println(table.get(i).get(0) + "," + table.get(i).get(1));
                teachersByClassroom.add(new ArrayList<String>(table.get(i).get(3),findClassroomTeacher(table.get(i).get(3))));
                
            }*/

            if(!classrooms.contains(table.get(i).get(3)) && table.get(i).get(2).equals(grade))
            {
                classrooms.add(table.get(i).get(3));
                //SALMA
                //System.out.println(table.get(i).get(3));
                //SALMA
                teachersOfGrade.addAll(findClassroomTeacher(table.get(i).get(3)));
            }

        }

        for(int i = 0; i < teachersOfGrade.size()-1; i=i+2) 
        {
            contains = true;
            System.out.println(teachersOfGrade.get(i) + "," + teachersOfGrade.get(i+1));
        }

        if(!contains)
        {
            System.out.println("No teachers in this grade.");
        }

        System.out.println();
    }

    public static void classroomStudent(String classroom) {
        boolean contains = false;
        for(int i = 0; i < table.size(); i++) {
            
            if(table.get(i).get(3).equals(classroom)){
                contains = true;
                System.out.println(table.get(i).get(0) + "," + table.get(i).get(1));
            }
        }
        if(!contains)
        {
            System.out.println("No students in this classroom.");
        }
        System.out.println();
    }

    public static void enrollment() {
        ArrayList<ArrayList<Integer>> classroomCount = new ArrayList<>();
        // first index of each inner array is classroom number and second is classroom count
        ArrayList<String> classroomsAdded = new ArrayList<>();
        boolean contains = false;
        for(int i = 0; i < table.size(); i++) {
            int classroomNumber = Integer.parseInt(table.get(i).get(3));
            //SALMA
            //System.out.println();
            //System.out.println(classroomNumber);
            //System.out.println(classroomsAdded);
            //SALMA
            if(classroomsAdded.contains(table.get(i).get(3)))
            {
                //classroomCount.indexOf(classroomNumber);
                for (ArrayList<Integer> list : classroomCount) {
                    //SALMA
                    //System.out.println(list.get(0));
                    //SALMA
                    if(list.get(0).equals(classroomNumber))
                    {
                        //SALMA
                        //System.out.println(list.get(0));
                        //SALMA
                        //SALMA
                        //System.out.println(oldCount);
                        //SALMA
                        int oldCount = classroomCount.get(classroomCount.indexOf(list)).get(1);
                        //SALMA
                        //System.out.println(oldCount);
                        //SALMA
                        classroomCount.get(classroomCount.indexOf(list)).set(1,oldCount + 1);
                        //SALMA
                        //System.out.println(classroomCount);
                        //SALMA
                        break;
                    }
                    
                }
            }
            else
            {
                classroomsAdded.add(table.get(i).get(3));
                classroomCount.add(new ArrayList<Integer>( 
                    Arrays.asList(classroomNumber, 1)));
                    //SALMA
                    //System.out.println("NEW: " + classroomCount);
                    //SALMA
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
            contains = true;
            System.out.println(list.get(0) + ": " + list.get(1));
        }

        if(!contains)
        {
            System.out.println("No students in this school.");
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
        else if(typeOfGPA.equals("T") || typeOfGPA.equals("Teacher"))
        {
            teacherByGrade(grade);

        }
        else
        {
            System.out.println("Invalid input.\n");
        }
        

    }

    public static void gradeWithHighestGPA(String grade) {

        boolean contains = false;
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
                contains = true;
                System.out.println(table.get(index).get(0) + "," + table.get(index).get(1) + "," + table.get(index).get(5)
                            + "," + teachers.get(i).get(0) + "," + teachers.get(i).get(1) + "," + table.get(index).get(4));
                System.out.println();
                break;
            }
        }
        if(!contains)
        {
            System.out.println("No students in this grade.\n");
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
        boolean contains = false;
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
                contains = true;
                System.out.println(table.get(index).get(0) + "," + table.get(index).get(1) + "," + table.get(index).get(5)
                            + "," + teachers.get(i).get(0) + "," + teachers.get(i).get(1) + "," + table.get(index).get(4));
                System.out.println();
                break;
            }
        }
        if(!contains)
        {
            System.out.println("No students in this grade.\n");
        }
        // System.out.println(table.get(index).get(0) + "," + table.get(index).get(1) + "," + table.get(index).get(5)
        //                     + "," + table.get(index).get(6) + "," + table.get(index).get(7) + "," + table.get(index).get(4));
        // System.out.println();

    }

    public static void average(String number) {
        double gpa = 0;
        int count = 0;
        boolean contains = false;
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(2).equals(number)) {
                contains = true;
                gpa += Double.parseDouble(table.get(i).get(5));
                count++;
            }
        }

        if(!contains)
        {
            System.out.println("No students in this grade.\n");
        }
        else
        {
            double averageGPA = gpa/count;
            DecimalFormat df = new DecimalFormat(".##");
            System.out.println(number + " , " + df.format(averageGPA));
            System.out.println();
        }
    }

    public static void bus(String number) {
        boolean contains = false;
        for(int i = 0; i < table.size(); i++) {
            if(table.get(i).get(4).equals(number)){
                contains = true;
                System.out.println(table.get(i).get(1) + "," + table.get(i).get(0) + "," +
                    table.get(i).get(2) + "," + table.get(i).get(3));
            }
        }
        if(!contains)
        {
            System.out.println("No students with this bus route.");
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
        System.out.println();
    }
}
