import java.util.*;
import java.io.*;
/* COP 3503C Assignment 5
This program is written by: Vincent Verapen */
//Helper class for Node and Edges
class Node{
  int nodeNumber = 0;
  int distanceFromCapital = 0;
  ArrayList<Edge> edges;
}
class Edge{
  Node node1, node2;
  int length;
}


class Dijkstras{
  static final int infinity = (int)1e9;
  //check Treasure function gets all treasures in Map from distance
  public static void checkTreasures(int distance, int[] shortestDistances, Edge[] edgeArray, int edges){
    int cityTreasures = 0;
    int onTheRoadTreasures = 0;
    for(int i = 0; i<shortestDistances.length; i++){
      if(shortestDistances[i] == distance){
        cityTreasures++;
      }
    }
    //visited array to help know which edges are visited
    Edge[] visitedArray = new Edge[edges];
    for(int i = 0; i<edgeArray.length; i++){
      //gets current node number and updates the edgeArray value to current Node distance values
      int nodeNumber1 = edgeArray[i].node1.nodeNumber;
      int nodeNumber2 = edgeArray[i].node2.nodeNumber;
      edgeArray[i].node1.distanceFromCapital = shortestDistances[nodeNumber1-1];
      edgeArray[i].node2.distanceFromCapital = shortestDistances[nodeNumber2-1];
      //if statement to check if the edge is greater than the distance remaining and if the curent nodes distance is < than the distance
      //as well as gets current check values to check if the treasure is in same spot on road or not
      if(edgeArray[i].length > distance && (edgeArray[i].node1.distanceFromCapital < distance || edgeArray[i].node2.distanceFromCapital < distance)){
        int check1 = distance - edgeArray[i].node1.distanceFromCapital;
        int check2 = distance - edgeArray[i].node2.distanceFromCapital;
        //System.out.println("check1: " + check1 + "check2: " + check2);
        //checks if nodes are a city with treasure and that they are not in same spot
        if(((check1 + check2) != edgeArray[i].length) && (check1 != 0 && check2 != 0) && (edgeArray[i].node1.distanceFromCapital < distance && edgeArray[i].node2.distanceFromCapital < distance))
        {
          onTheRoadTreasures+=2;
          continue;
        }
        onTheRoadTreasures++;
      }
    }
    System.out.println("In city: " + cityTreasures);
    System.out.println("On the road: "+ onTheRoadTreasures);
  }
  //helpepr function that never was really used
  public boolean isEdge(Node Node1, Node Node2){
    for(int i = 0; i<2; i++){
      if(Node1.edges.contains(Node2.edges.get(i)))
      {
        return true;
      }
    }
    return false;
  }
  //gets edge helper function should never return null
  public Edge getEdge(Node Node1, Node Node2){
    for(int i = 0; i<2; i++){
      if(Node1.edges.contains(Node2.edges.get(i)))
      {
        return Node2.edges.get(i);
      }
    }
    return null;
  }
  //dijstraks algorithm that returns array  of smallest distances to all nodes from capital
  public static int[] dijsktras(Node graph[], Edge[] edgeArray, int capital, int distance, int cities){
    graph[capital-1].distanceFromCapital = 0;
    int[] visitedArray = new int[cities];
    Arrays.fill(visitedArray, 0);
    int[] listOfDist = new int[cities];
    PriorityQueue<Integer> q = new PriorityQueue<Integer>();
    q.add(capital);
    int curDistance = 0;
    while(!q.isEmpty()){
      int nodeNum = q.poll();
      Node curNode = graph[nodeNum-1];
      if(visitedArray[curNode.nodeNumber-1] == 1){
          continue;
      }
      visitedArray[curNode.nodeNumber-1] = 1;
      curDistance = curNode.distanceFromCapital;
      listOfDist[curNode.nodeNumber-1] = curDistance;
      for(int i = 0 ; i<curNode.edges.size(); i++){
        //gets edge[i] from curNode
        Edge tempEdge = curNode.edges.get(i);
        //condition to see if curNode is equal to tempNode1 and tempNode2 is visited
        if(curNode == tempEdge.node1 && visitedArray[tempEdge.node2.nodeNumber-1] != 1){
          if((curDistance+tempEdge.length) < tempEdge.node2.distanceFromCapital){
              q.add(tempEdge.node2.nodeNumber);
              graph[tempEdge.node2.nodeNumber-1].distanceFromCapital = (curDistance+tempEdge.length);
            }
        }
        else if(curNode == tempEdge.node2 && visitedArray[tempEdge.node1.nodeNumber-1] != 1){
          if((curDistance+tempEdge.length) < tempEdge.node1.distanceFromCapital){
              q.add(tempEdge.node1.nodeNumber);
              graph[tempEdge.node1.nodeNumber-1].distanceFromCapital = (curDistance+tempEdge.length);
            }
        }
      } 
    }
    return listOfDist;
  }  
  public static void main(String[] args) {
    try{
    File newFile = new File("in.txt");//File Data
    Scanner s = new Scanner(newFile);
    int cities, roads, capital;
    cities = s.nextInt();
    roads = s.nextInt();
    capital = s.nextInt();

    //LinkedList<Node> graph[] = new LinkedList[cities];
    //set up nodesArray with every distance set to infinity and is adjency matrix because each node contains a ArrayList
    Node[] nodesArray = new Node[cities];
    for(int i = 0; i<cities; i++){
      nodesArray[i] = new Node();
      nodesArray[i].nodeNumber = i+1;
      nodesArray[i].distanceFromCapital = infinity;
      nodesArray[i].edges = new ArrayList<Edge>();
    }
    //edge Array to get list of Edges
    Edge[] edgeArray = new Edge[roads];
    for(int i = 0; i<roads; i++){
      edgeArray[i] = new Edge();
      int node1Number = s.nextInt();
      int node2Number = s.nextInt();
      edgeArray[i].node1 = nodesArray[node1Number-1];
      edgeArray[i].node2 = nodesArray[node2Number-1];
      edgeArray[i].length = s.nextInt();
      
      nodesArray[node1Number-1].edges.add(edgeArray[i]);
      nodesArray[node2Number-1].edges.add(edgeArray[i]);
      }
    int distance = s.nextInt();
    int[] listOfDistances = new int[cities];
    listOfDistances = dijsktras(nodesArray, edgeArray, capital, distance, cities);
    checkTreasures(distance, listOfDistances, edgeArray, roads);
    }catch(FileNotFoundException e){//catch FileNotFoundException
      e.printStackTrace();
    }
  }
}