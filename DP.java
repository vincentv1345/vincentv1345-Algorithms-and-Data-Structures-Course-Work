import java.util.*;
import java.io.*;
/*
 COP 3503C Assignment 6
This program is written by: Vincent Verapen
*/
class DP{
  //bottom up method
  public static int bottomUpTabulation(int[][] array, int n){
    //creates a 2-d dp matrix containing best values from
    //bottom up tabulation 
    int[][] dp = new int[2][n];
    int previousNumber = 0;
    for(int i = n-1; i>=0; i--){

      if(i == n-1){
        dp[0][i] = array[0][i];
        dp[1][i] = array[1][i];
      }
      else if(Math.max(array[0][i] + array[1][i+1], array[1][i] + array[0][i+1]) > Math.max(dp[0][i+1], dp[1][i+1])){ 
        dp[0][i] = dp[1][i+1] + array[0][i];
        dp[1][i] = dp[0][i+1] + array[1][i];
        if(dp[0][i] < dp[1][i]){
          if((dp[0][i+1] > array[0][i])){
            array[0][i] = dp[0][i+1];
            dp[0][i] = array[0][i];
            }
          
        }
        else{
          if((dp[1][i+1] < array[1][i])){
            array[1][i] = dp[1][i+1];
            }
        }
      }

      else{
        dp[0][i] = array[0][i] + dp[1][i+1];
        dp[1][i] = array[1][i]+dp[0][i+1];
      }
    }

    
    previousNumber = Math.max(dp[1][0], dp[0][0]);
    return previousNumber;
  }
  
  public static void main(String[] args) {
    try{
    File newFile = new File("in.txt");
    Scanner s = new Scanner(newFile);
    int n = s.nextInt();
    //Set up matrix
    int[][] studentArray = new int[2][n];
    for(int i = 0; i<2; i++){
      for(int j = 0; j<n; j++){
        studentArray[i][j] = s.nextInt();
      }
    }
   int bestNum = bottomUpTabulation(studentArray, n);
    
    System.out.println(bestNum);
    s.close();
    }catch(FileNotFoundException e){
      e.printStackTrace();
    }
  }
}