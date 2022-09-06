import java.util.*;
import java.io.*;

class BFS {
  
  public static int findTarget(int start, int target, char[][] charArray, int rows, int columns, HashMap<Character, ArrayList<Integer>> letterMap){
    
    int[] visitedArray = new int[rows*columns];
    
    Arrays.fill(visitedArray, -1);
    
    int[] xDirs = {0, 0, -1, 1};
    int[] yDirs = {-1, 1, 0, 0};
    
    LinkedList<Integer> q = new  LinkedList<Integer>();
    
    q.add(start);
    int distance = 0;
    while(!q.isEmpty()){
      int size = q.size();
      for(int x = 0 ; x<size; x++){
        int curNode = q.poll();
        if(curNode == target){
        return distance;
        }
        int currX = curNode%columns;
        int currY = curNode/columns;
        for(int i = 0; i<xDirs.length; i++){
        int nextX = curNode%columns + xDirs[i];
        int nextY = curNode/columns + yDirs[i];
        //gets current coordinates for the 
          
        if(nextX < 0 || nextX >= columns || nextY < 0 || nextY >= rows) continue;

          
        int coordinate = nextY*columns + nextX; 
        if(charArray[nextY][nextX] == '!') continue;

        if(visitedArray[coordinate] == 0) continue;
        
        q.add(coordinate);
        visitedArray[coordinate] = 0;
        }
        if(charArray[currY][currX] >= 'A' && charArray[currY][currX] <= 'Z'){
           ArrayList<Integer> letterArray = letterMap.get(charArray[currY][currX]);
           ArrayList<Integer> emptyArray = new ArrayList<Integer>();
           letterMap.replace(charArray[currY][currX], emptyArray);
           for(int j = 0; j<letterArray.size(); j++){
             if(visitedArray[letterArray.get(j)] == -1){
               q.add(letterArray.get(j));
             }
           }
         }
      }
      distance += 1;
    }
    return -1;
  }
  public static int findStart(char boardArray[][], int rows, int columns){
    for(int i = 0; i<rows; i++){
      for(int j = 0; j<columns; j++){
        if(boardArray[i][j] == '$'){
          return i*columns + j;
        }
      }
    }
    return -1;
  }
  public static int findEnd(char boardArray[][], int rows, int columns){
    for(int i = 0; i<rows; i++){
      for(int j = 0; j<columns; j++){
        if(boardArray[i][j] == '*'){
          return i*columns + j;
        }
      }
    }
    return -1;
  }
  //Adjacency Lists
  public static void main(String[] args) {
    //File Data//try to take in the file//File Data
        Scanner s = new Scanner(System.in);
        int rows = s.nextInt();
        int columns = s.nextInt();
        char[][] charArray = new char[rows][columns];
        for(int i = 0; i < rows; i++){
          String string = s.next();
          for(int j = 0; j< string.length(); j++){
           charArray[i][j] = string.charAt(j);
          }
        }
        HashMap<Character, ArrayList<Integer>> letterMap = new HashMap<>();
       for(int i = 0; i<26; i++){
        ArrayList<Integer> tempArray = new ArrayList<Integer>();
        letterMap.put((char)(i+'A'), tempArray);
        }
        for(int i = 0; i < rows; i++){
          for(int j = 0; j< columns; j++){
            if(charArray[i][j] >= 'A' && charArray[i][j] <= 'Z'){
            int coordinate = i*columns + j;
            ArrayList<Integer> tempArray = letterMap.get(charArray[i][j]);
            tempArray.add(coordinate);
            letterMap.put(charArray[i][j], tempArray);
           }
         }
       }
       int start = findStart(charArray, rows, columns);
       int target = findEnd(charArray, rows, columns);
       int distance = findTarget(start, target, charArray, rows, columns, letterMap);
       System.out.println(distance); 
  }
}