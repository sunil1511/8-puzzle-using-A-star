/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package air;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 *
 * @author sunildupare
 */
public class puzz_8 {
 int m=0,n=0;
 ArrayList <node> open;
 ArrayList <node> close;
 ArrayList <node> output;
 node strt,goal,cur;
 Scanner sc=new Scanner(System.in);
   void input()
   {   strt=new node();
       goal=new node();
       cur=new node();
       open=new ArrayList<node>();
       close=new ArrayList<node>();
       output=new ArrayList<node>();
       strt.g=0;
       System.out.println("Enter start state(enter -1 for empty space):");
       for(int i=0;i<3;i++)
       {
           for(int j=0;j<3;j++)
           {
            strt.state[i][j]=sc.nextInt();
            if(strt.state[i][j]==-1)
            {
                m=i;
                n=j;
            }
           }
       }
       System.out.println("Enter goal state(enter -1 for empty space):");
       for(int i=0;i<3;i++)
       {
           for(int j=0;j<3;j++)
           {
            goal.state[i][j]=sc.nextInt();
           }
       }
       strt.h=calh(strt);
       open.add(strt);
       cur=strt;
       strt.f=strt.h;
       output.add(strt);
   }
   
   int calh(node s1)
   {
       int h=0;
       for(int i=0;i<3;i++)
       {
           for(int j=0;j<3;j++)
           {
               if(s1.state[i][j]!=goal.state[i][j])
               {
                   h++;
               }
           }
       }
       return h;
   }
   
   boolean isGoal(node s1)
   {   int flag=0;
       for(int i=0;i<3;i++)
       {
           for(int j=0;j<3;j++)
           {
               if(s1.state[i][j]!=goal.state[i][j])
               {
                   flag=1;
                   break;
               }
           }
       }
       if(flag==1)
       {
           return false;
       }
       else
       {
           return true;
       }
   }
   public static Comparator<node>f_valueComparator=new Comparator<node>(){
     @Override
     public int compare(node o1, node o2) { 
     return (o1.f-o2.f);
     }
   };
   boolean present(node s)
   {   int flag=0,c=0;
       for(int i=0;i<close.size();i++)
       {
           
           for(int j=0;j<3;j++)
           {   
               for(int k=0;k<3;k++)
               {
                    if(close.get(i).state[j][k]==s.state[j][k])
                    {
                       c++;
                    }
               }
           }
           if(c==9){
               return true;
           }
           c=0;
       }
           return false;
   }
   void emptySpace(node s)
   {
       for(int i=0;i<3;i++)
       {
           for(int j=0;j<3;j++)
           {
               if(s.state[i][j]==-1)
               {
                   m=i;n=j;
                   break;
               }
           }
       }
   }
   int[][] copy(int s2[][])          
   {   int s1[][]=new int[3][3];
       for(int i = 0;i<3;i++)
       {
           for(int j=0;j<3;j++)
           {
               s1[i][j]=s2[i][j];
           }
       }
       return s1;
   }
   void  process()
   {   input();
       int c=0;
       while(!open.isEmpty() && !isGoal(cur))
       {
           display(cur);
           close.add(cur);
           open.remove(cur);
           node temp=new node();
           temp.state=copy(cur.state);
           temp.g=cur.g;
           System.out.println("g="+cur.g+"\th="+cur.h+"\tf="+cur.f);
           if((m+1)<3)
           {    
                temp.state[m][n]=temp.state[(m+1)][n];
                temp.state[(m+1)][n]=-1;
                temp.g=cur.g+1;
                temp.h=calh(temp);
                temp.f=temp.g+temp.h;
                 if(!present(temp)){
                open.add(temp);
                }
           }
           temp=new node();
           temp.state=copy(cur.state);
           temp.g=cur.g;
           if((m-1)>=0)
           {    
                temp.state[m][n]=temp.state[(m-1)][n];
                temp.state[(m-1)][n]=-1;
                temp.g=cur.g+1;
                temp.h=calh(temp);
                temp.f=temp.g+temp.h;
                if(!present(temp)){
                open.add(temp);
                }
           }
           temp=new node();
           temp.state=copy(cur.state);
           temp.g=cur.g;
           if((n-1)>=0)
           {    
                temp.state[m][n]=temp.state[m][(n-1)];
                temp.state[m][(n-1)]=-1;
                temp.g=cur.g+1;
                temp.h=calh(temp);
                temp.f=temp.g+temp.h;
                if(!present(temp)){
                open.add(temp);
                }
           }
           temp=new node();
           temp.state=copy(cur.state);
           temp.g=cur.g;
           if((n+1)<3)
           {    
                temp.state[m][n]=temp.state[m][(n+1)];
                temp.state[m][(n+1)]=-1;
                temp.g=cur.g+1;
                temp.h=calh(temp);
                temp.f=temp.g+temp.h;
                if(!present(temp)){
                open.add(temp);
                }
           }
           Collections.sort(open,f_valueComparator);
           if(open.get(0).f==open.get(1).f)
           {
             if(open.get(0).g>open.get(1).g)
             {
                 cur=open.get(0);
             }
             else
             {
                 cur=open.get(1);
             }
           }
           else{
            cur=open.get(0);   
           }
           emptySpace(cur);
           checkOutput(cur);
       }
       display(cur);
       pathDisplay();
   }
   
   void checkOutput(node s)
   {
       for(int i=0;i<output.size();i++)
       {
           if(output.get(i).g==s.g)
           {
               for(int j=i;j<output.size();j++)
               {
               output.remove(j);
               }
           }
       }
       output.add(s);
   }
   void pathDisplay()
   {
       System.out.println("\n\nFinal Path:\n\n");
       for(int i=0;i<output.size();i++)
           {   System.out.println("Path:"+i);
               for(int j=0;j<3;j++)
               {
                   for(int k=0;k<3;k++)
                   {
                   System.out.print(output.get(i).state[j][k]+"\t");
                   }
                   System.out.println();
               }
               System.out.println();
           }
   }
   void display(node s)
   {   System.out.println("State:");
       for(int i=0;i<3;i++)
           {
               for(int j=0;j<3;j++)
               {
                   System.out.print(s.state[i][j]+"\t");
               }
               System.out.println();
           }
   }
   
    public static void main(String[] args) {
        puzz_8 p=new puzz_8();
        p.process();
    }
    
}
