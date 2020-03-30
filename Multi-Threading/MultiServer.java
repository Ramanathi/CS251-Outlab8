// Java program to illustrate  
// ThreadPool 
import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.net.*;
import java.util.concurrent.*;
 
  
// Task class to be executed (Step 1) 
class ClientHandler implements Runnable    
{ 
    final Scanner sc; 
    final PrintStream p; 
    final Socket s;
    final Map<Integer,Integer> hm; 
    

    // Constructor 
    public ClientHandler(Socket s, Scanner sc, PrintStream p, ConcurrentHashMap<Integer,Integer> hm) 
    { 
        this.s = s; 
        this.sc = sc; 
        this.p = p;
        this.hm = hm; 
    } 
      
    // Prints task name and sleeps for 1s 
    // This Whole process is repeated 5 times 
    public synchronized void run() 
    { 
        while (true) 
        { 
            try { String num;

                
                while(true)
         {
        num=sc.nextLine();
         String[] parts = num.split(" ");
         int temp=0;
         //int temp1 =0;
         if(parts[0].equals("add")){
            int number=Integer.parseInt(parts[1]);
            if(hm.containsKey(number)){
                int b=hm.get(number);
                hm.put(number,b+1);
                temp=b+1;
            }
            else{
                hm.put(number,1);
                temp=1;
            }
            PrintStream p=new PrintStream(s.getOutputStream());
         p.println(temp);
            System.out.println("ADD "+ parts[1]);
         }


          else if(parts[0].equals("disconnect")){
            
            //temp1=1;
         PrintStream p=new PrintStream(s.getOutputStream());
         p.println("1");
         System.out.println("DIS");
         return;
         //Thread.interrupt();
        
         }


         else if(parts[0].equals("read")){
            int number=Integer.parseInt(parts[1]);
            if(hm.containsKey(number)){
                temp=hm.get(number);
                
            }
            else{
                temp=0;
            }
            PrintStream p=new PrintStream(s.getOutputStream());
         p.println("Weight of key="+Integer.toString(temp));
            System.out.println("READ "+parts[1]+" "+Integer.toString(temp));
         }


        
     }

            } catch (Exception e) { 
                //e.printStackTrace(); 
            } 
        }
    } 
} 
public class MultiServer 
{ 
     // Maximum number of threads in thread pool 
    static final int MAX_T = 3;              
    
    public static void main(String[] args) throws IOException 
    { 
        //int portNumber = Integer.parseInt(args[0]);
        System.out.println("Listening on 5000"); 
        ServerSocket s1 = new ServerSocket(5000); 
        ConcurrentHashMap<Integer,Integer> hm = new ConcurrentHashMap<Integer,Integer>();
        //Map<Integer,Integer> hm=new HashMap<Integer,Integer>();
        final int MAX_T = 3;
        //ExecutorService pool = Executors.newFixedThreadPool(MAX_T);   
 
        while (true) 
        { 
            Socket ss = null;
            
            try
            { 
                // socket object to receive incoming client requests 
                ss = s1.accept();
                
                Scanner sc = new Scanner(ss.getInputStream()); 
                PrintStream p = new PrintStream(ss.getOutputStream());
                ExecutorService pool = Executors.newFixedThreadPool(MAX_T);
                Runnable t1 = new ClientHandler(ss, sc, p, hm);
                pool.execute(t1); 
            } 
            catch (Exception e){ 
            } 
        } 
    }
} 