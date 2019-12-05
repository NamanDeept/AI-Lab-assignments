

import java.util.*;
class action{
    String state;
    String path;
    action(String state,String path){
        this.state  = state;
        this.path = path;
    }
}
class Assignment3Q2{
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
        for (String[] arr2 : arr) {
            for (String arr1 : arr2) {
                if (arr1 == null ? o1 == null : arr1.equals(o1)) {
                    str+=o2+" ";
                } else if (arr1 == null ? o2 == null : arr1.equals(o2)) {
                    str+=o1+" ";
                } else {
                    str += arr1 + " ";
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
   static String intersecting(HashMap<String,Boolean>closeSource,HashMap<String,Boolean>closeDestination){
      //String mystr[] = state.split(" ");
      String outputState="";
      for(Map.Entry h:closeSource.entrySet()){
          if(closeDestination.containsKey(h.getKey())){
             outputState = (String)h.getKey();
              break;
          }
      }
      return outputState;
   }
    static int computeSortedSwaps(String Source,String destination,int row,int col)throws NullPointerException
    {
        boolean flag = true;
        Queue<action>fringeSource = new LinkedList();
        Queue<action>fringeDestination =new LinkedList();
        HashMap<String,Boolean>closedSource = new HashMap();
        HashMap<String,Boolean>closedDestination = new HashMap();
        fringeSource.add(new action(Source,""));
        fringeDestination.add(new action(destination,""));
        closedSource.put(Source,true);
        closedDestination.put(destination,true);
        action actionSource =new action("","");
        action actionDestination = new action("","");
        HashMap<String,String>hashedSource=new HashMap();
        HashMap<String,String>hashedDestination=new HashMap();
        String commonNode="";
        while(fringeSource.size()!=0 && fringeDestination.size()!=0){
            actionSource = fringeSource.remove();
            actionDestination = fringeDestination.remove();
            String strSource = actionSource.state;
            String strDestination = actionDestination.state;
            String pathSource = actionSource.path;
            String pathDestination = actionDestination.path;
            
            String intersectedNode = intersecting(closedSource,closedDestination);
            if(!intersectedNode.equals("")){ commonNode = intersectedNode;break;}
            else{
                 String currentStateSource = strSource;
                 String currentStateDestination = strDestination;
                 String [][]arrSource = convertStringtoArray(currentStateSource,row,col);
                  hashedSource = getAllPossibleStates(arrSource,pathSource);
                  List<Map.Entry<String,String>>hashSource = new ArrayList(hashedSource.entrySet());
                  Collections.sort(hashSource, (Map.Entry<String,String>o1, Map.Entry<String,String>o2) -> o1.getValue().compareTo(o2.getValue()));
                  if(intersectedNode.equals(currentStateSource)){
                      break;
                  }
                // if(hashedSource.containsKey(strDestination)) break;
                  hashSource.stream().filter((h) -> (!closedSource.containsKey((String)h.getKey()))).map((h) -> {
                      fringeSource.add(new action(h.getKey(),h.getValue()));
                    return h;
                }).forEach((h) -> {
                    closedSource.put((String)h.getKey(),true);
                });
                 String [][]arrDestination = convertStringtoArray(currentStateDestination,row,col);
                  hashedDestination = getAllPossibleStates(arrDestination,pathDestination);
                  List<Map.Entry<String,String>>hashDestination = new ArrayList(hashedDestination.entrySet());
                  Collections.sort(hashDestination, (Map.Entry<String,String>o1, Map.Entry<String,String>o2) -> o1.getValue().compareTo(o2.getValue()));
                  hashDestination.stream().filter((h) -> (!closedDestination.containsKey((String)h.getKey()))).map((h) -> {
                      fringeDestination.add(new action(h.getKey(),h.getValue()));
                    return h;
                }).forEach((h) -> {
                    closedDestination.put((String)h.getKey(),true);
                });   
           }
        }
        Queue<action>fringeS = new LinkedList();
        HashMap<String,Boolean>closedS = new HashMap();
        fringeS.add(new action(Source,""));
         closedS.put(Source,true);
       action act=new action("","");
        while(fringeS.size()!=0){
            act = fringeS.remove();
            String str = act.state;
            String path = act.path;
            if(str.equals(commonNode)){
                break;
            }
            else{
                 String currentState = str;
                 String [][]arr = convertStringtoArray(currentState,row,col);
                  HashMap<String,String>hashed = getAllPossibleStates(arr,path);
                  List<Map.Entry<String,String>>hash = new ArrayList(hashed.entrySet());
                  Collections.sort(hash, (Map.Entry<String,String>o1, Map.Entry<String,String>o2) -> o1.getValue().compareTo(o2.getValue()));
                  hash.stream().filter((h) -> (!closedS.containsKey((String)h.getKey()))).map((h) -> {
                      fringeS.add(new action(h.getKey(),h.getValue()));
                    return h;
                }).forEach((h) -> {
                    closedS.put((String)h.getKey(),true);
                });
        }
        }
        Queue<action>fringeD = new LinkedList();
        HashMap<String,Boolean>closedD = new HashMap();
        fringeD.add(new action(destination,""));
         closedD.put(destination,true);
       action actD=new action("","");
        while(fringeD.size()!=0){
            actD = fringeD.remove();
            String str = actD.state;
            String path = actD.path;
            if(str.equals(commonNode)){
                break;
            }
            else{
                 String currentState = str;
                 String [][]arr = convertStringtoArray(currentState,row,col);
                  HashMap<String,String>hashed = getAllPossibleStates(arr,path);
                  List<Map.Entry<String,String>>hash = new ArrayList(hashed.entrySet());
                  Collections.sort(hash, (Map.Entry<String,String>o1, Map.Entry<String,String>o2) -> o1.getValue().compareTo(o2.getValue()));
                  hash.stream().filter((h) -> (!closedD.containsKey((String)h.getKey()))).map((h) -> {
                      fringeD.add(new action(h.getKey(),h.getValue()));
                    return h;
                }).forEach((h) -> {
                    closedD.put((String)h.getKey(),true);
                });
        }
        }
        
     
      
      int res=0;
      String path = act.path+actD.path;
      //System.out.println(path);
      for(int i=0;i<path.length();i++){
          if(path.charAt(i)=='\n'){
              res++;
          }
      }
      return res;
    }
    public static void main(String args[]) throws Exception {
      Scanner s = new Scanner(System.in);
      int t=s.nextInt();
      for(int i=0;i<t;i++)
      {
          int m = s.nextInt();
          int n = s.nextInt();
          String source="",destination="";
           for(int j=0;j<m*n;j++)
        {
      String ss =  s.next();
      source=source+ss+" ";
      }
      for(int j=0;j<m*n;j++)
      {
          String ss =  s.next();
          destination=destination+ss+" ";
      }
      System.out.println(computeSortedSwaps(source,destination,m,n));
      }
    }
    
}


