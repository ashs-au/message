import java.util.Vector;

/*
  This is the self-loading & size-maintaining data queue for message text (using Vector 
  now, it's supposedly all thread-safe). All operations on this are called from main or
  a thread (to async load new text into the queue) started in the main app.

*/

public class StrVect {
    
    ///queue to hold parsed & cleaned-up chars from message text.
   
    //queue initial cap: 10
    Vector<String> q = new Vector<String>();
    boolean returnCode = false;
    char ch;
    
    //maintenance items:
    int maxQSize = 100;
    int qtrim = 5; // default amount to trim from array at once
    
    //index, maintained in this object:
    int index = 0;
    
    /*
      acceptable characters --- have to keep space here as it's the delimiter:
      UPPERCASE - it's easier to accept upper case letters & convert them when
      handing back the filetered string
    */
    String accept = " ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    //temp storage
    String t;
    String[] chopped; //for split string
    
    //functions  
    //query queue for it's current size - for iteration
    int qSize() {
      return q.size();
    }
    
    //query queue to see if there's any contents
    boolean hasContents() {
      if(q.size() > 0)
        return true;
      else
        return false;
    }
    
    //dump out contents of queue as a string
    String qDump() {
      return q.toString();
    }   
       
    //mass provision queue from an array of strings
    void qFill(String[] strs)  {
      q.clear(); //ugly hack - ensure the queue is emptied every time we reload it
      //process array line by line
      for(int k = 0;k < strs.length; k++) {
        //process line - cleanup unwanted chars
        t = filter(strs[k]);
        //split on any whitespace char
        chopped = t.split(" ");
        for(int n=0;n<chopped.length;n++) {
          setNext(chopped[n]);
          
          //debug: System.out.println("set:" + chopped[n] + " size now: " + q.size());    
        }
      }  
    }
    
    //filter-out chars method. Discard list of punctuation, extra whitespace etc.
    String filter(String s) {
      String result = "";
      for(int m = 0;m < s.length();m++) {
        if(accept.indexOf(s.charAt(m)) >= 0)
          result += s.charAt(m);
        }
        return result.toLowerCase();
    }
    
    //insert new string at end of queue
     boolean setNext(String st) {
       //check storage space first
       maintainQ();
       returnCode = q.add(st);
       //System.out.println("set:" + st + " size now: " + q.size());
       return returnCode;
    }
    
    //sequential access: return next char using internal indexer
    String getNext() {
      if(index >= q.size()) {         //check bounds
        index = 0;
      }
      return (String)q.get(index++);   //increment after returning 
    }
    
    //random access: return char from queue at index
    String getAt(int idx) {
      return (String)q.get(idx);
    }
    
    //maintain array when it gets too big
    void maintainQ() {
      if(q.size()>=maxQSize) {
        for(int j = 0; j< qtrim;j++) {
           q.remove(j);
        }
        //q.removeRange(0, qtrim);
       
      }
      //debug
      //System.out.println("maintenance, removing items. New size: " + q.size());
      //System.out.println(q.toString());
    }
   
}   

