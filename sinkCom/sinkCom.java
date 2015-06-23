import java.util.ArrayList;
 
public class sinkCom{
  ArrayList<Integer> shipLoc = new ArrayList<Integer>();
  String N;
    void setLoc(ArrayList<Integer> arraylist){shipLoc=arraylist;}
    void setName(String name){N=name;}
    ArrayList<Integer> getLoc(){return shipLoc;}
    String getName(){return N;}
    public static void main(String args[]){
      setup setting=new setup();
      getInput in=new getInput();
      new symtoint();
      setting.setlocAname(in.getFirst());
      setting.check();    
    }
}
