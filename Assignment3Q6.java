
import java.util.*;
class machine{
    String state;
    String path;
    machine(String state,String path){
        this.state  = state;
        this.path = path;
    }
    public String getState(){
        return this.state;
    } 
    public String getPath(){
        return this.path;
    }
    public int computeCost(){
        int result=0;
        HashMap<String,Integer>hmap = new HashMap();
        String swaps[] = path.split("\n");
        for (String swap : swaps) {
            String[] frnds = swap.split(" ");
            for(int j=1;j<frnds.length;j++){
                if(!hmap.containsKey(frnds[j])){
                    hmap.put(frnds[j], 1);
                }
                else{
                    int cnt = hmap.get(frnds[j]);
                    cnt++;
                    hmap.put(frnds[j],cnt);
                }
            }
        }//count the total counts in hashmap
        result = hmap.entrySet().stream().map((h) -> h.getValue()).map((val) -> (val*(val+1))/2).reduce(result, Integer::sum);
    return result;
    }
}
class Assignment3Q6 {
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
        Queue<machine>fringe = new LinkedList();
        ArrayList<machine>actions = new ArrayList();
        HashMap<String,Boolean>closed = new HashMap();
        fringe.add(new machine(Source,""));
        closed.put(Source,true);
        machine x=new machine("","");
        ArrayList<String>paths = new ArrayList();
        machine minimised = x;
        int minimumCost = Integer.MAX_VALUE;
        
        while(fringe.size()!=0){
            depth++;
            x = fringe.remove();
            String str = x.state;
            String path = x.path;
            int costValue = x.computeCost();
            String friendz[][] = convertStringtoArray(str,row,col);
            if(costValue<=minimumCost){
            if(checkFriend(friends,friendz)){
                minimised = x;
                actions.add(x);
                minimumCost = costValue;
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
                fringe.add(new machine(h.getKey(),h.getValue()));
                closed.put((String)h.getKey(),true);
            }
           }
        }
      }
      }
      for(machine action:actions){
          if(action.computeCost()==minimumCost){
              paths.add(action.path);
          }
      }
      String stz ="zz";
      int index =0;
      int countSwaps =0;
      for(int i=0;i<paths.size();i++){
          if(paths.get(i).split("\n").length > countSwaps) 
          {
              countSwaps = paths.get(i).split("\n").length;
          }
      }
      String opPat ="";
      for(int i=0;i<paths.size();i++){
          if(paths.get(i).split("\n").length == countSwaps){
           if(paths.get(i).compareTo(stz)<0){
               stz = paths.get(i);
           }        
          }
      }
      System.out.println(stz);
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