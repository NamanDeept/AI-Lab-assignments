/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 *
 * @author Naman
 */
public class time_table_backtracking {
    static class Course{
        public String courseCode;
        public String instructor;
        public int duration;
        
        public Course(String courseCode,String instructor,int duration){
            this.courseCode = courseCode;
            this.duration = duration;
            this.instructor = instructor;
        }  
    }
    static class Batch{
        public String BatchName;
        public ArrayList<Course>courses;
        public Course [][]table;
        
        public Batch(String BatchName){
            this.BatchName = BatchName;
            courses = new ArrayList();
            table = new Course[5][7];
        }
        
        public void addCourse(Course course){
            courses.add(course);
        }
        public boolean isFilled(){
            boolean output = true;
            for (Course course : courses) {
                if (course.duration > 0) {
                    output = false;
                    break;
                }
            }
            return output;
        }
        
        public boolean constraint(Course course,int row,int col){
          if(table[row][col]==null){
              return false;
          }
            return !table[row][col].instructor.equals(course.instructor);
        }//&& !table[row][col].courseCode.equals(course.courseCode)
        
        public int getAllTime(){
            int netDuration =0;
            netDuration = courses.stream().map((course) -> course.duration).reduce(netDuration, Integer::sum);
            return netDuration;
        }
        
        public int getCourseLength(){
            return courses.size();
        }
    }
    
    static boolean constraintAll(ArrayList<Batch>batches,int range,Course course, int r,int c){
       boolean output = true;
        for (int i=0;i<range;i++) {
            if(!batches.get(i).constraint(course,r,c)){
                output = false;
                break;
            }
        }
       return output;
    }
    
    static boolean filledAll(ArrayList<Batch>batches){
        boolean output = true;
        for(Batch batche : batches){
            if(!batche.isFilled()){
                output = false;
                break;
            }
        }
        return output;
    }
    
    static boolean BacktrackingUtil(ArrayList<Batch>batches,int index,int row,int col){
        if(index==batches.size()){
            return true;
        }
                for(int l=0;l<batches.get(index).courses.size();l++){
                //System.out.println(constraintAll(batches,i,batches.get(i).courses.get(l),j,k));
                if(batches.get(index).courses.get(l).duration>0 && constraintAll(batches,index,batches.get(index).courses.get(l),row,col)){
                    batches.get(index).table[row][col] = batches.get(index).courses.get(l);
                    batches.get(index).courses.get(l).duration--;
                    if(batches.get(index).isFilled()){
                          if(BacktrackingUtil(batches,index+1,0,0))
                                 return true;
                    }
                    else if(col<6){
                        if(BacktrackingUtil(batches,index,row,col+1)){
                            return true;
                              }
                         }
                    else if(row<4){
                       if(BacktrackingUtil(batches,index,row+1,0))
                              return true;
                          }
                    else if(col==6 && row==4){
                        if(batches.get(index).isFilled()&&BacktrackingUtil(batches,index+1,0,0))
                                 return true;
                        return false;  
                      }
                                
                       batches.get(index).table[row][col] = null;
                       batches.get(index).courses.get(l).duration++; 
                        }
                    }
                
            
        
            return false;
        }
   
    
    static void printSolution(ArrayList<Batch>batches){
        batches.stream().map((batche) -> {
            System.out.println(batche.BatchName);
            return batche;
        }).forEach((batche) -> {
            for (int j = 0; j<5; j++) {
                for (int k = 0; k<7; k++) {
                    String batchCode = (batche.table[j][k]!=null)?batche.table[j][k].courseCode+" ":"NIL ";
                    System.out.print(batchCode);
                }
                System.out.println();
            }
        });
    }
    
    static void backtracking(ArrayList<Batch>batches){
        if(BacktrackingUtil(batches,0,0,0)==false){
            //printSolution(batches);
            System.out.println("No Solution Exists");
        }
        else {
            printSolution(batches);
        }
    }
    
    
    
    public static void main(String []args)throws Exception{
        Scanner scan = new Scanner(System.in);
        int test = scan.nextInt();
        while(test-- >0){
            int n = scan.nextInt();
            ArrayList<Batch>batches = new ArrayList();
            for(int i=0;i<n;i++){
                String batchName = scan.next();
                batches.add(new Batch(batchName));
                ArrayList<Course>courses = new ArrayList();
                int weeks = scan.nextInt();
                for(int j=0;j<weeks;j++){
                  String courseName = scan.next();
                  String instructorName = scan.next();
                  int duration = scan.nextInt();
                  courses.add(new Course(courseName,instructorName,duration));
                  batches.get(i).addCourse(new Course(courseName,instructorName,duration));
                }
                Collections.sort(courses, (Course o1, Course o2) -> o1.courseCode.compareTo(o2.courseCode));
            }
            Collections.sort(batches, (Batch b1, Batch b2) -> {
                int comparison = b1.BatchName.compareTo(b2.BatchName);
                if(comparison ==0){
                     comparison = b1.getAllTime()-b2.getAllTime();
                }
                return comparison;                
            });
            backtracking(batches);
            }
        }
    }

