import java.util.HashMap;
/*
  Map to translate chars to pitches. These are then called to tune the tones
  emitted. Deal with freq in hz here - not symbolic note names.
  
  char frequency map for 'european languages':
  e
  t
  a
  o
  n
  r
  i
  s
  h
  d
  l
  f
  c
  m
  u
  g
  p
  y
  w
  b
  v
  k
  x
  j
  q
  z
  1
  2
  3
  4
  5
  6
  7
  8
  9
  0
*/

public class C2P {

  private HashMap c2p = new HashMap();  
  Double df = new Double(0.0);
  float  freq; //hold the hz to output
  
  //ctor - fill the map with the char to 
  //pitches the map is arranged in alphanum order
  C2P() {
    c2p.put('a',208.0);
    c2p.put('b',93.0);
    c2p.put('c',440.0);
    c2p.put('d',156.0);
    c2p.put('e',165.0);
    c2p.put('f',139.0);
    c2p.put('g',110.0);
    c2p.put('h',370.0);
    c2p.put('i',311.0);
    c2p.put('j',78.0);
    c2p.put('k',82.0);
    c2p.put('l',415.0);
    c2p.put('m',124.0);
    c2p.put('n',247.0);
    c2p.put('o',220.0);
    c2p.put('p',554.0);
    c2p.put('q',831.0);
    c2p.put('r',278.0);
    c2p.put('s',330.0);
    c2p.put('t',185.0);
    c2p.put('u',494.0);
    c2p.put('v',659.0);
    c2p.put('w',622.0);
    c2p.put('x',740.0);
    c2p.put('y',104.0);
    c2p.put('z',69.0);
    c2p.put('0',41.0);
    c2p.put('1',880.0);
    c2p.put('2',62.0);
    c2p.put('3',988.0);
    c2p.put('4',55.0);
    c2p.put('5',1109.0);
    c2p.put('6',52.0);
    c2p.put('7',1245.0);
    c2p.put('8',46.0);
    c2p.put('9',1319.0);   
  }

  //unused - allows adding entries to map
  public void addEntry(char ch, float hz) {  
    c2p.put(ch,hz);
  }

  public float getFreq(char ch) { 
    df = (Double)c2p.get(ch);
    
    
    //check response just in case
    //if(freq == NULL) 
    //  freq = 440.0;
 
    return df.floatValue();
  }
}


