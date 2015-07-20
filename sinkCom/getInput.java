import java.util.Scanner;

class getInput{
  Scanner scanner;
  sinkCom coms[]; 
    
    String[] getFirst(){ //input ships' names 
      scanner=new Scanner(System.in);
      System.out.println("Please input coms' names & split with *SPACE*");
      String[] coms=(scanner.nextLine()).split(" "); //a b c turn to {"a","b","c'}
        return coms;
    }
    
    String getIn(){ //input guess slot
      System.out.println("Please guess");
      scanner=new Scanner(System.in);
        return scanner.nextLine();
    }    
} 
