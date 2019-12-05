import java.util.*;
class action{
    String state;
    String path;
    action(String state,String path){
        this.state  = state;
        this.path = path;
    }
}
class Assignment2Q2 {
    static class Coordinate{
        int x;
        int y;
        public Coordinate(int x,int y){
            this.x = x;
            this.y = y;
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
    static HashMap<String,String> getAllPossibleStates(String [][]arr , String path){
        ArrayList<String>al = new ArrayList();
        HashMap<String,String>string = new HashMap();
        for(int i=arr.length-1;i>=0;i--){
            for(int j=arr[i].length-1;j>=0;j--){
                al.add(arr[i][j]);
            }
        }
        int row = arr.length;int col = arr[0].length;
        for(int i=arr.length-1;i>=0;i--){
            for(int j=arr[i].length-1;j>=0;j--){
                Coordinate c = new Coordinate(i,j);
                for(int k=(arr.length-i-1) *col+(arr[i].length-j-1);k<al.size();k++){
                    Coordinate z=findCoordinate(al.get(k),arr);
                    if(isValid(z,c,row,col)){
                        String x1 = arr[z.x][z.y];
                        String x2 = arr[c.x][c.y];
                        if(x1.compareTo(x2)>0){
                            String tem = x1;
                            x1=x2;
                            x2=tem;
                        }
                       string.put(swapped(z,c,arr),path+"swap "+x1+" "+x2 +"\n");
                }
            }
          }
        }
        return string;
    }
    
    static String swapped(Coordinate x_index,Coordinate y_index,String [][]arr){
        String str ="";
        String o1 = arr[x_index.x][x_index.y];
        String o2 = arr[y_index.x][y_index.y];
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[i].length;j++){
                if(arr[i][j]==o1){
                    str+=o2+" ";
                }
                else if(arr[i][j]==o2){
                    str+=o1+" ";
                }
                else str+=arr[i][j]+" ";
            }
        }
        return str;
    }
    
   static boolean isValid(Coordinate p1,Coordinate p2,int row,int col){
       if(Math.abs(p1.y-p2.y)==1 && p1.x==p2.x) return true;
       else if(Math.abs(p1.x-p2.x)==1 && p1.y==p2.y){
           if((p1.y==0 && p2.y==0)||(p1.y==col-1 && p2.y==col-1)) return true;
           else return false;
       }
       else return false;
   }
   
   static String convertArrayToString(String [][]arr){
       
       String result="";
       for(int i=0;i<arr.length;i++){
           for(int j=0;j<arr[i].length;j++){
               result+=arr[i][j]+" ";
           }
       }
       return result;
   }
   
   static String[][] convertStringtoArray(String string,int row,int col){
        String []singleDArray = string.split(" ");
        String [][]DDArray = new String[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                DDArray[i][j] = singleDArray[i*col+j];
            }
        }
        return DDArray;
   }
   
   static boolean checkFriend(ArrayList<String>friends,String [][]arr){
      boolean check = true;
      for(int i=0;i<friends.size();i++){
         String []friend = friends.get(i).split(" ");
         Coordinate c1 = findCoordinate(friend[0],arr);
         Coordinate c2 = findCoordinate(friend[1],arr);
        // System.out.println(c1.x+" "+c1.x);
         if(c1.x == c2.x && Math.abs(c1.y-c2.y)==1){
             check = false;
             break;
         }
      }
      return check;
   }
    static void computeSortedSwaps(String Source,int row,int col,ArrayList<String>friends)throws NullPointerException
    {
        int depth =0;
        Queue<action>fringe = new LinkedList();
        ArrayList<action>actions = new ArrayList();
        HashMap<String,Boolean>closed = new HashMap();
        fringe.add(new action(Source,""));
        closed.put(Source,true);
        action x=new action("","");
        while(fringe.size()!=0){
            depth++;
            x = fringe.remove();
            String str = x.state;
            String path = x.path;
            String friendz[][] = convertStringtoArray(str,row,col);
            if(checkFriend(friends,friendz)){
                break;
            }
            else{
                 String currentState = str;
                 String [][]arr =convertStringtoArray(currentState,row,col); 
                  HashMap<String,String>hashed = getAllPossibleStates(arr,path);
                  List<Map.Entry<String,String>>hash = new ArrayList(hashed.entrySet());
                  Collections.sort(hash,new Comparator<Map.Entry<String,String>>(){
                  public int compare(Map.Entry<String,String>o1,Map.Entry<String,String>o2){
                    return o1.getValue().compareTo(o2.getValue());
                 }
              });
          for(Map.Entry<String,String>h:hash){
            if(!closed.containsKey((String)h.getKey())){
                fringe.add(new action(h.getKey(),h.getValue()));
                closed.put((String)h.getKey(),true);
            }
           }
        }
        
      }
      String p = x.path;
      System.out.println(p);
    }
    public static void main(String args[]) throws Exception {
      Scanner s = new Scanner(System.in);
      int t=s.nextInt();
      while(t-->0)
      {
          int m = s.nextInt();
          int n = s.nextInt();
          int pro = m*n;
          String source="";
           for(int i=0;i<pro;i++){
              String ss =  s.next();
              source=source+ss+" ";
           }
           int friends = s.nextInt();
           ArrayList<String>frns = new ArrayList();
           for(int i=0;i<friends;i++){
               String f1 = s.next();
               String f2 = s.next();
               frns.add(f1+" "+f2);
           }
      
      computeSortedSwaps(source,m,n,frns);
      }
    }
}
