/*

 Sending the message: Customs House October 2010
 
 * run the loading of strings into the queue in a background thread
 * wrap a map allowing translation between english & morse code
 * add sonification to the morse (sync to graphics?)

 */
 
import ddf.minim.signals.*;
import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;
import ddf.minim.ugens.*;

Minim minim;
AudioOutput aout;
Buzzer tone;

//floats for pulse/silence duration s = short, l=long, g=gap:
float s = 0.08;
float l = 0.4;
float g = 0.1;

//float for hz value
float hz = 440.0;

//increment notes apart within each burst of code
float elapsed;

//overall time-keeper:
int count;
int wait;

//hard-coded text file containing messages
String srcURL = "http://localhost:8888/messages.txt";
String w;
String mch;
String[] lines;
StringBuffer encodedWord;

StrVect mq;
Loader loader;
E2M translator;
char ret;

C2P sono;

PFont f;  // Global font variable
float x;  // horizontal location of headline
int index;
String word;
boolean ready;

//---------------------------------
void setup() {
  size(400,200);
  f = createFont("Arial",24,true);  
  // Initialize headline offscreen to the right 
  x = width;
  count = 0;
  index =0; 
  
  frameRate(60);
  
  //audio
  minim = new Minim(this);
  aout = minim.getLineOut(Minim.MONO, 2048);
  tone = new Buzzer(440.0, 0.4, aout);

  sono = new C2P();
  
  mq = new StrVect();
  loader = new Loader("ldr");
  translator = new E2M();
  
  encodedWord = new StringBuffer();

  //content - array of chars to feed into queue. DO THIS ONCE (unthreaded) in order to prime to queue!
  //lines = loadStrings(srcURL);
  println("calling loadStrings()");
  mq.qFill(loadStrings(srcURL)); //-------------------this activity will be moved into a separate thread & start here

  //ready = true; //start off 'true' otherwise there's only a null to display!
  ready = true;
  loader.start(); //start the loader thread
  
}

//---------------------------------
void draw() {
  background(255);
  fill(0);

  if(count == 100) {
    //ready = true;
    //println("ready = true");
  }
  
  // Display headline at x  location
  textFont(f,24);        
  textAlign(LEFT);

  if(ready == true) {
    ready = false;
    count = 0;
    encodedWord.setLength(0);
    w = mq.getNext();
    
    /* 
    w = the next Str from the source
    - encode char & store out into ch array.
    - lookup freq assigned to this char & set new instr
    */
    
    //iter over the String & encode each char + get hz value
    for(int h = 0;h < w.length(); h++) {
      mch = translator.encodeChar(w.charAt(h));
      hz = sono.getFreq(w.charAt(h));
      
      tone = new Buzzer(hz, 0.4, aout);
      
      println("got next: " + mch + " " + hz);
      //encodedWord.append(mch);
    }
    //PLAY each char
    
    elapsed = 0.0;
    int z = 0;
    //aout.pauseNotes();
    do {
      if(mch.charAt(z) == '.') {
        aout.playNote(elapsed, s, tone);
       // println(elapsed + " short");
        elapsed += s;
      } else if(mch.charAt(z) == '-'){
        aout.playNote(elapsed, l, tone);
        //println(elapsed + " long");
        elapsed +=l;    
      }
      elapsed += g;
      z++;
    } while(z < mch.length());
    //aout.resumeNotes();
    //println("time: " + elapsed);
    //burn some cyles here, waiting...
    wait = (int)((elapsed * 1000) + 200.0);
    
  }
  //println("wait for: " + wait);
  delay(wait);
  ready = true;
}


void stop() {
  // always close audio I/O classes
  aout.close();
  // always stop your Minim object
  minim.stop();
 
  super.stop();
}

//---------------------------------
//LOADER class - still here until it's split into a separate file

public class Loader extends Thread {
  int ctr;
  public Loader(String str) {
    super(str);  
    ctr = 0;
  }

  // run 
  public void run() {
    while(true) {
      background(int(random(255)));
      //println("run: " + getName() + " " + ctr);
      ctr++;
      //lines = loadStrings(srcURL);
      mq.qFill(loadStrings(srcURL));
      try {
        sleep((long)(1000));  // 1 sec delay
      } 
      catch (InterruptedException e) {
      }
    }
  }

  public void dispose() {
    //
    println(getName()+ " quitting now");
  }
}

