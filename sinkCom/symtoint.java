class symtoint{
  int convert(String twosymbols){
    if(twosymbols.length()==2 && (twosymbols.substring(0,1)).matches("[A-Ga-g]") && (twosymbols.substring(1,2)).matches("[1-7]")){
      char firstCh=twosymbols.charAt(0);
      String secondCh=twosymbols.substring(1,2);
      int result = ((int)(Character.toUpperCase(firstCh))-65)*7+Integer.parseInt(secondCh); //because of 16bit  0x41 = 65
        return result;
    }
    else{
      return -1;
    }
  }
}
//turn users' inputs into slot number
//A1 0*7+1=slot1
//G5 6*7+5=slot47
//A 41-41=0
//G 47-41=6
//please turn to class setup to see what am i talking about
