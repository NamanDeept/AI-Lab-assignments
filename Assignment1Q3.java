import java.util.*;
import java.io.*;

class Assignment1Q3 {
    static class Coordinate{
        int x;
        int y;
        Coordinate(int x,int y){
            this.x = x;
            this.y = y;
        }
    }
    public static void main(String args[] ) throws Exception {
       Scanner s = new Scanner(System.in);
       int t = s.nextInt();
       while(t-->0){
           int m = s.nextInt();
           int n = s.nextInt();
           int query = s.nextInt();
           String str ="";
           String [][]arr = new String[m][n];
           for(int i=0;i<m;i++){
               for(int j=0;j<n;j++){
                   arr[i][j] = s.next();
                   str+=arr[i][j]+" ";
               }
           }
           ArrayList<String>al = new ArrayList();
           
          al.add(str);
           while(query-->0){
               String queries = s.next();
               String s1 = s.next();
               String s2 = s.next();
               Coordinate p1 = findCoordinate(s1,arr);
               Coordinate p2 = findCoordinate(s2,arr);
               if(isValid(p1,p2,m,n)){
                   String nstr = swapped(p1,p2,arr);
                   if(al.contains(nstr)){
                     System.out.println("cycle "+nstr);  
                   }
                   else {
                       al.add(nstr);
                       System.out.println(nstr);
                   }
               }
               else System.out.println("illegal");
           }
          
       }
    }
    static Coordinate findCoordinate(String s,String [][]arr){
        int x_index=0,y_index=0;
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[i].length;j++){
                if(arr[i][j].equals(s)){
                    x_index=i;
                    y_index=j;
                    break;
                }
            }
        }
        Coordinate c = new Coordinate(x_index,y_index);
        return c;
    }
    
    static String swapped(Coordinate x_index,Coordinate y_index,String [][]arr){
        String output="";
        String temp = arr[x_index.x][x_index.y];
        arr[x_index.x][x_index.y] = arr[y_index.x][y_index.y];
        arr[y_index.x][y_index.y] = temp;
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[i].length;j++){
                output+=arr[i][j]+" ";
            }
        }
        return output;
    }
    
   static boolean isValid(Coordinate p1,Coordinate p2,int row,int col){
       if(Math.abs(p1.y-p2.y)==1 && p1.x==p2.x) return true;
       else if(Math.abs(p1.x-p2.x)==1 && p1.y==p2.y){
           if((p1.y==0 && p2.y==0)||(p1.y==col-1 && p2.y==col-1)) return true;
           else return false;
       }
       else return false;
   }
    
}
