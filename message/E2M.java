import java.util.HashMap;

/*
  Map to translate chars to morse codes
*/

public class E2M {

  private HashMap e2m = new HashMap();   
  String morse;   //hold the converted char
  StringBuffer morseWord = new StringBuffer(); //hold the converted word
  
  //ctor - fill the map with the code translations. Note all lowercase
  //these may not be so usefull for sonification.
  E2M() {
    e2m.put('a',".-");
    e2m.put('b',"-...");
    e2m.put('c',"-.-.");
    e2m.put('d',"-..-");
    e2m.put('e',".");
    e2m.put('f',"..-.");
    e2m.put('g',"--.");
    e2m.put('h',"....");
    e2m.put('i',"..");
    e2m.put('j',".---");
    e2m.put('k',"-.-");
    e2m.put('l',".-..");
    e2m.put('m',"--");
    e2m.put('n',"-.");
    e2m.put('o',"---");
    e2m.put('p',".--.");
    e2m.put('q',"--.-");
    e2m.put('r',".-.");
    e2m.put('s',"...");
    e2m.put('t',"-");
    e2m.put('u',"..-");
    e2m.put('v',"...-");
    e2m.put('w',".--");
    e2m.put('x',"-..-");
    e2m.put('y',"-.--");
    e2m.put('z',"--..");
    e2m.put('0',"-----");
    e2m.put('1',".----");
    e2m.put('2',"..---");
    e2m.put('3',"...--");
    e2m.put('4',"....-");
    e2m.put('5',".....");
    e2m.put('6',"-....");
    e2m.put('7',"--...");
    e2m.put('8',"---..");
    e2m.put('9',"----.");   
  }

  public void addEntry(char ch, String code) {  
    e2m.put(ch,code);
  }

  public String getCode(char ch) {
    //String tmp;
    //lower-case the input char
    // Returns null if there is no number for the name.
    //tmp = 
    //System.out.println(ch + " " + tmp);
    return (String)e2m.get(ch);
  }
  
  /* the app in it's current form will only pass a char at a time
  to this object, so we can do a quick & dirty thing here */
  public String encodeChar(char c) {   
      morse = getCode(c);
    //System.out.println("encoding done: " + s + " " + morseWord.toString());
    return morse;
  }
   
  public String encodeString(String s) {
    
    morseWord.setLength(0); //ensure SB is empty
    for(int i = 0;i < s.length();i++) {
      //recode this to work as durations for audio
     
      morse = getCode(s.charAt(i));
      //System.out.println(morse);
      morseWord.append(morse);
      morseWord.append(' ');
      
    }
    //System.out.println("encoding done: " + s + " " + morseWord.toString());
    return morseWord.toString();
  }
}


