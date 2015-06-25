import java.util.ArrayList;

class setup{
  sinkCom comEle[]; //save references of class sinkCom (name and location)
  boolean less=true; //make sure if there is anything left
  int guessTimes; //the variable that store how many times have you guess
  int lessShip; //combine with [boolean less]   when ==0 will turn less ot false
  void setlocAname(String[] comss){
    comEle=new sinkCom[comss.length]; //make sure how many slot comEle[] will have
    lessShip=comss.length; 
    for(int i=0;i<comss.length;i++){ //make all of the names in the comss[] convert to [Object sinkCom] then store in the comEle[]
      comEle[i]=new sinkCom(); //new new reference
      comEle[i].setName(comss[i]); //set ships' name
      int random=(int)(Math.random()*50); //random generate a slot of 49
      ArrayList<Integer> shipLocc=new ArrayList<Integer>(); //generate a changeable list to store ships' location
      while(true){ //stop until setup a ship successfully
      int vec=(int)(Math.random()*4); //random generate a direction(0-3)
        switch(vec){
          case 0: //left
          if(!((random%7)<3)&&random-2>0){ //make sure it will keep a line           //* What am I Talking About
            shipLocc.add(random);                                        //*A 1 2 3 4 5 6 7 
            shipLocc.add(random-1);                                      //*B 1 2 3 4 5 6 7
            shipLocc.add(random-2);                                      //*C 1 2 3 4 5 6 7
              break;                                                     //*E 1 2 3 4 5 6 7
          }else{continue;}                                               //*F 1 2 3 4 5 6 7
          case 1: //right                                                //*G 1 2 3 4 5 6 7
          if(!((random%7)>5)&&!((random%7)==0)&&random+2<=49){  //make sure it will keep a line          /***************************/
            shipLocc.add(random);
            shipLocc.add(random+1);
            shipLocc.add(random+2);
              break;
          }else{continue;}
          case 2: //dowm
          if(!((49-random)<14)&&random+14<=49){ //make sure it will keep a line
            shipLocc.add(random);
            shipLocc.add(random+7);
            shipLocc.add(random+14);
              break;
          }else{continue;}
          case 3: //up
          if(!((random-49)>(-14))&&random-14>0){ //make sure it will keep a line
            shipLocc.add(random);
            shipLocc.add(random-7);
            shipLocc.add(random-14);
              break;
          }else{continue;}
          default: //to catch invisiable error
          System.out.println("What is going on");
          break;
        }
          break;
      }
      comEle[i].setLoc(shipLocc);         
    }
  }
  void check(){
    while(less){ //rerun while there're ships left
      getInput in=new getInput();
      String input=in.getIn(); //get users' inputs(guesses)
      symtoint sti=new symtoint();
      int guess=sti.convert(input); //convert users' inputs to slot number(Integer)
        if(guess==-1){ //if input format incorrect rerun while{}
          System.out.println("input incorrect format");
          continue;
        }
      ArrayList<Integer> shipLocc; //store ships' locations
      guessTimes++;
        for(sinkCom ship : comEle){
          int index=ship.getLoc().indexOf(guess); //check if guess==location return index or return -1
          shipLocc=ship.getLoc(); //store ship's(only one) location to shipLocc to continue check easily
          if(index>=0){ //if guess==location
              System.out.println("hit " + ship.getName());
              shipLocc.remove(index); //remove the hitted slot from the arraylist
              ship.setLoc(shipLocc); //replace [Object sinkCom]'s location
              if(ship.getLoc().isEmpty()){ //if the whole ship is sink  
                lessShip--; //left ship count minus 1
                System.out.println("sink" + ship.getName());
              }
              if(lessShip==0){less=false; Win();} //if sink all of the ships
          }
          else if(index<0&&ship.getLoc().isEmpty()){}
          else{System.out.println("miss");} //if guess!=location
        }
    }
  }
  void Win(){
    System.out.println("You win \ntotal-guesses:" + guessTimes);
    System.exit(0); //the game's ending
  }
}
