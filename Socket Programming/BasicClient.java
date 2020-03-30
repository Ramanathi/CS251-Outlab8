import java.net.*;
import java.io.*;
import java.nio.*;
import java.util.*;
public class BasicClient extends Thread{
    public static void main(String[] args) throws Exception{
        Socket s = new Socket();
        String fileName = args[0];
        try {
            Map<Integer,Integer> hm1=new HashMap<Integer,Integer>();
            Scanner scanner = new Scanner(new File(fileName));
            int count=0;
            int rand=0;
            int delta=0;
            while (scanner.hasNextLine()) {

                String line=scanner.nextLine();
                if(count==0) {count=1;rand=0;
                    String[] parts=line.split(" ");


                    if(! parts[0].equals("connect")){
                        System.out.println("No Server");
                        System.exit(1);
                    }
                    try(Socket s1 = new Socket(parts[1],Integer.parseInt(parts[2]))){
                        System.out.println("OK");
                        s1.close();
                    } catch (IOException s1){
                        System.out.println("No Server");
                        System.exit(1);
                    }
                    s = new Socket(parts[1],Integer.parseInt(parts[2]));
            
                }


                else{

                   String[] parts=line.split(" ");
                   
                   if(parts[0].equals("add")){
                    if(rand==1 || rand==2){
                    }
                    else{
                    Scanner sc1=new Scanner(s.getInputStream());
                    PrintStream p=new PrintStream(s.getOutputStream());
                    p.println(line);
                    String temp=sc1.nextLine();
                    //System.out.println(temp);
                    hm1.put(Integer.parseInt(parts[1]),Integer.parseInt(temp));}
                   }

                   else if(parts[0].equals("read")){
                    if(rand==1 || rand==2){
                    }
                    else{
                    Scanner sc1=new Scanner(s.getInputStream());
                    PrintStream p=new PrintStream(s.getOutputStream());
                    p.println(line);
                    String temp=sc1.nextLine();
                    String[] p1=temp.split("=");
                    int new1= Integer.parseInt(p1[1]);
                    int ulfa = Integer.parseInt(parts[1]);
                    int old =-1;
                    if(hm1.containsKey(ulfa)){
                        //old = Integer.parseInt(p1[1]); 
                        old = new1-hm1.get(ulfa);
                        //System.out.println(old);
                    }

                    else{
                        old = new1-0;
                    }
                    //hm1.put(Integer.parseInt(parts[1]),new1);
                    delta = delta + old;
                    System.out.println(p1[1]+" "+old);}
                   }

                   else if(parts[0].equals("disconnect")){
                    //count=-1;
                    rand =1;
                    Scanner sc1=new Scanner(s.getInputStream());
                    PrintStream p=new PrintStream(s.getOutputStream());
                    p.println(line);
                    String temp=sc1.nextLine();
                    System.out.println("OK");
                    s.close();
                    rand =1;
                   }

                   else if(parts[0].equals("sleep") && rand==1){
                    rand = 2;
                    Thread.sleep(Integer.parseInt(parts[1]));
                   }

                   else if(parts[0].equals("connect")){
                    rand=0;
                    try(Socket s1 = new Socket(parts[1],Integer.parseInt(parts[2]))){
                        System.out.println("OK");
                        s1.close();
                    } catch (IOException s1){
                        System.out.println("No Server");
                        System.exit(1);
                    }
                    s = new Socket(parts[1],Integer.parseInt(parts[2]));
                   }

                   else if(rand==1){}

               }
           }

            scanner.close();


            //Deltas should be printed
            

            System.out.println(delta);
            

            s.close();
        } catch (Exception e) {}
           
        
}
}