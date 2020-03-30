import java.net.*;
import java.io.*;
import java.util.*;

public class BasicServer {
    public static void main(String[] args) throws Exception {
        Map<Integer,Integer> hm=new HashMap<Integer,Integer>();
        
        //int portNumber = Integer.parseInt(args[0]);
        System.out.println("Listening on 5000");
         ServerSocket s1=new ServerSocket(5000);
         while(true){
         Socket ss=s1.accept();
         Scanner sc = new Scanner(ss.getInputStream());
         try 
         //while(true)
         {
         String num;

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
            PrintStream p=new PrintStream(ss.getOutputStream());
         p.println(temp);
            System.out.println("ADD "+ parts[1]);
         }


          else if(parts[0].equals("disconnect")){
            
            //temp1=1;
         PrintStream p=new PrintStream(ss.getOutputStream());
         p.println("1");
         System.out.println("DIS");
        
         }


         else if(parts[0].equals("read")){
            int number=Integer.parseInt(parts[1]);
            if(hm.containsKey(number)){
                temp=hm.get(number);
                
            }
            else{
                temp=0;
            }
            PrintStream p=new PrintStream(ss.getOutputStream());
         p.println("Weight of key="+Integer.toString(temp));
            System.out.println("READ "+parts[1]+" "+Integer.toString(temp));
         }


        
     }
         
    }catch(Exception e){}
}
}
}
