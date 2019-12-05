import java.util.*;
class action{
    String state;
    String path;
    //int depth;
    action(String state,String path){
        this.state  = state;
        this.path = path;
    }
}
class Assignment3Q1 {
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
        for (String[] arr1 : arr) {
            for (String arr11 : arr1) {
                if (arr11.equals(o1)) {
                    str+=o2+" ";
                } else if (arr11.equals(o2)) {
                    str+=o1+" ";
                } else {
                    str += arr11 + " ";
                }
            }
        }
        return str;
    }
    
   static boolean isValid(Coordinate p1,Coordinate p2,int row,int col){
       if(Math.abs(p1.y-p2.y)==1 && p1.x==p2.x) return true;
       else if(Math.abs(p1.x-p2.x)==1 && p1.y==p2.y){
           return (p1.y==0 && p2.y==0)||(p1.y==col-1 && p2.y==col-1);
       }
       else return false;
   }
   
   static String convertArrayToString(String [][]arr){
       
       String result="";
        for (String[] arr1 : arr) {
           for (String arr11 : arr1) {
               result += arr11 + " ";
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
        for (String friend1 : friends) {
            String[] friend = friend1.split(" ");
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
   
   static void IDDFS(action source,int row,int col,ArrayList<String>friends){
       int lim =0;
       while(true){
           if(DLS(source,lim,row,col,friends)){
               break;
           }
           else lim++;
       }
       //return opPath;
   }
   static boolean DLS(action source,int limit,int row,int col,ArrayList<String>friends){
       String friendz[][] = convertStringtoArray(source.state,row,col);
       if(checkFriend(friends,friendz)){
           System.out.println(source.path);
           return true;
       }
       if(limit <=0) return false;
       String arr[][] = convertStringtoArray(source.state,row,col);
           
        HashMap<String,String>hashed = getAllPossibleStates(arr,source.path);
        List<Map.Entry<String,String>>hash = new ArrayList(hashed.entrySet());
        Collections.sort(hash, (Map.Entry<String,String>o1, Map.Entry<String,String>o2) -> o1.getValue().compareTo(o2.getValue()));
        // System.out.println(hash);
        if (hash.stream().map((h) -> new action(h.getKey(),h.getValue())).anyMatch((ac) -> (DLS(ac,limit-1,row,col,friends)))) {
            return true;
        }
         return false;
       }
      public static void main(String args[]) throws Exception {
      Scanner s = new Scanner(System.in);
      int t=s.nextInt();
      for(int i=0;i<t;i++)
      {
          int m = s.nextInt();
          int n = s.nextInt();
          String source="";
           for(int j=0;j<m*n;j++)
            {
            String ss =  s.next();
            source=source+ss+" ";
            }
      
      action sourceAction = new action(source,"");
      //ation destinationAction = new action(destination,"");
      int friends = s.nextInt();
           ArrayList<String>frns = new ArrayList();
           for(int k=0;k<friends;k++){
               String f1 = s.next();
               String f2 = s.next();
               frns.add(f1+" "+f2);
           }
      IDDFS(sourceAction,m,n,frns);
      }
    }
    
}

   

