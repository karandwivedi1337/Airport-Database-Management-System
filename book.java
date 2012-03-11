import java.io.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

/*
<applet code="book" width=650 height=400>
</applet>
*/

public class book extends Applet implements ActionListener{

Panel p1,p2,p3,p4,p5;
Label l1,l2,l3,l4,l5;
Choice start,dest,num;
Button f,b,back;
TextArea ta;
TextField name,ch;
Random rno;

int ready=0;
int numanswers=0;


String ori="", des="";
int numpassengers=0;

String ans[]; //will contain full query result for a particular flight search (e.g. ans[0]="mumbai delhi IT101 kingfisher 09:00 11:30 2:30 ..." ans[1]="lko delhi KF202 kingfisher 01:00 02:10 1:10")

String anstime[];

public void init(){
setBackground(Color.GRAY);

setLayout(new FlowLayout());

p1=new Panel();
p2=new Panel();
p3=new Panel();
p4=new Panel();
p5=new Panel();

p4.setLayout(new FlowLayout(FlowLayout.CENTER));
p4.setLayout(new FlowLayout(FlowLayout.LEFT));
p5.setLayout(new FlowLayout(FlowLayout.CENTER));

f=new Button("Find Flights!");
b=new Button("Book");
back=new Button("Back");

l1=new Label("Origin:");
l2=new Label("Destination:");
l3=new Label("Passengers:");
l4=new Label("Enter your name:");
l5=new Label("Choice:");
ta=new TextArea("",10,70);

start=new Choice();
start.add("Bengaluru");
start.add("Chandigarh");
start.add("Delhi");
start.add("Hyderabad");
start.add("Jaipur");
start.add("Kolkata");
start.add("Lucknow");
start.add("Mumbai");
start.add("Panaji");
start.add("Patna");


dest=new Choice();
dest.add("Bengaluru");
dest.add("Chandigarh");
dest.add("Delhi");
dest.add("Hyderabad");
dest.add("Jaipur");
dest.add("Kolkata");
dest.add("Lucknow");
dest.add("Mumbai");
dest.add("Panaji");
dest.add("Patna");


num=new Choice();

name=new TextField(20);
ch=new TextField(1);

for(int i=1;i<=5;i++){
num.add(i+"");}

p1.add(l1);
p1.add(start);
p1.add(l2);
p1.add(dest);
p1.add(l3);
p1.add(num);

p2.add(f);

p3.add(ta);

p4.add(l4);
p4.add(name);
p4.add(l5);
p4.add(ch);

p5.add(b);
p5.add(back);

add(p1);
addHorizontalLine(Color.GRAY);
addHorizontalLine(Color.GRAY);
add(p2);
addHorizontalLine(Color.GRAY);
add(p3);
addHorizontalLine(Color.GRAY);
add(p4);
addHorizontalLine(Color.GRAY);
add(p5);

f.addActionListener(this);
b.addActionListener(this);
back.addActionListener(this);

rno=new Random(1);

//add(p2);

}//constructor close


public void actionPerformed(ActionEvent ae){

ori=start.getSelectedItem();
des=dest.getSelectedItem();
numpassengers=Integer.parseInt(num.getSelectedItem());

if(ae.getActionCommand().equals("Back")){


AppletContext ac=getAppletContext();
    URL url=getCodeBase();

try{
ac.showDocument(new URL(url+ "loggedin.html"));
System.out.println(url.toString());
//showStatus(url+"book.html");
}

catch(MalformedURLException e){
showStatus("Database is down.");}

}

else{


if((ae.getActionCommand()).equals("Find Flights!")){

if(ori.equals(des)){
JOptionPane.showMessageDialog(null,"Origin and Destination cannot be same","Prompt",JOptionPane.INFORMATION_MESSAGE);
}

else findthem();
} //find flights close

else { bookthem(); }

}//outer else close
}//actionPerformed close


public void findthem(){

    try{
String query = URLEncoder.encode("select * from routeplace where origin='" + ori + "'" + " and destination='" + des + "'", "UTF-8");
URL url = new URL(getCodeBase(), "hello?query=" + query + "&exeup=0" + "&num=4");

URLConnection servletconnection=url.openConnection();

servletconnection.setDoInput(true);  //require output from servlet


InputStream in=servletconnection.getInputStream();
String s="";
int ch;
loop:while(1>0){
ch=in.read();
if(ch==-1) break loop;
else s+=(char)ch;
}

//ta.setText(s.length()+"");
//JOptionPane.showMessageDialog(null,"s="+s+"--","Prompt",JOptionPane.INFORMATION_MESSAGE);


if(s.length()>3){

StringTokenizer st=new StringTokenizer(s,"\n");
int i=0;
numanswers=st.countTokens();
  //
//JOptionPane.showMessageDialog(null,"numanswers="+numanswers+"--","Prompt",JOptionPane.INFORMATION_MESSAGE);

String ans1[]=new String[numanswers];    //last /n is not considered //will contain partial query from routeplace

while(st.hasMoreTokens()){
ans1[i++]=st.nextToken();
}

i=0; //reinitialize i

String temp1="";

/*
for(int j=0;j<numanswers;j++){
  JOptionPane.showMessageDialog(null,"ans1["+j+"]="+ans1[j]+"--","Prompt",JOptionPane.INFORMATION_MESSAGE);
}


*/

//JOptionPane.showMessageDialog(null,"ans1["+(numanswers-1)+"]="+ans1[numanswers-1]+"--","Prompt",JOptionPane.INFORMATION_MESSAGE);

//String tt=ans1[numanswers-1];


//JOptionPane.showMessageDialog(null,"Going into loop--","Prompt",JOptionPane.INFORMATION_MESSAGE);
//for(int j=0;j<100000;j++);

//int rowsreturned=Integer.parseInt(ans1[numanswers-1]);

//JOptionPane.showMessageDialog(null,"rowsreturned="+ans1[numanswers-1]+""+"--","Prompt",JOptionPane.INFORMATION_MESSAGE);


ans=new String[numanswers/4];  //no of rows i.e. flights found for a particular search , their number=no of rows returned=ans1[numanswers-1]

for(int j=0;j<numanswers/4;j++){
  ans[j]="";}


String temp="";

//now separate values from ans1 into parts=no of rows
for(int j=0;j<numanswers-1;j++){
ans[j/4]+=ans1[j]+ "      ";}

populate();    //call populate to add routetime information to the obtained flight results

//ans[i]'s have now added details from routeplace relation

String prefill="SNo   Origin    Destination    Flight No.    Airlines    Cost    Duration    Departure    Arrival\n------------------------------------------------------------------------------------------------------------------------\n";

temp+=prefill;

for(int j=0;j<numanswers/4;j++){
temp+=(j+1)+".      "+ans[j]+"\n";}

//JOptionPane.showMessageDialog(null,"ans[]="+temp+"--","Prompt",JOptionPane.ERROR_MESSAGE);


ta.setText(temp);
ready=1;



} //if s.length>3 close

else {
  s="No matching flights found.";
  ta.setText(s); }


//String s=getCodeBase().toString();


}//try close

 catch(MalformedURLException e){
ta.setText("Malformed URL Exception occured.");
}

catch(IOException e){
ta.setText("IO exception occured");
  }




}


public void bookthem(){

//JOptionPane.showMessageDialog(null,"Inside Book Them","Prompt",JOptionPane.INFORMATION_MESSAGE);

if(ready==1){ //flights are found

   if(((name.getText()).length())>0){ //valid name is entered

       String fromch=ch.getText();
       fromch=fromch.trim();
       int enteredch=Integer.parseInt(fromch);

      if( !fromch.equals("null") && !fromch.equals("") && fromch!=null && !fromch.isEmpty() && enteredch>=1 && enteredch<=(numanswers/4)){  //choice entered is between 1 and no of flights found

      Random rand=new Random();
      int randno=rand.nextInt(50);
     double d;
      for(int jj=0;jj<(enteredch*5+randno);jj++){

       d=rno.nextDouble();

      }


  d=rno.nextDouble();
  d=d*1000000;
  int dd=(int)d;
  String s=dd+"";
  if(s.length()<6){
   int diff=6-s.length();
      for(int j=1;j<=diff;j++){
      s+="0";}
  }

  String selected=ans[enteredch-1];


  StringTokenizer st=new StringTokenizer(selected);
  int i=0;
  String bookorigin="", bookdestination="", bookflightno="", bookairlines="";
  while(st.hasMoreTokens()){

     String cc=st.nextToken();
     if(i==0){ bookorigin=cc; i++; continue;}
     if(i==1){ bookdestination=cc; i++; continue;}
     if(i==2){ bookflightno=cc; i++; continue;}
     if(i==3){ bookairlines=cc; i++; continue;}
     }

    //JOptionPane.showMessageDialog(null,"--"+bookorigin+"--"+bookdestination+"--"+bookflightno+"--","Prompt",JOptionPane.INFORMATION_MESSAGE);
  try{
  String query = URLEncoder.encode("insert into pnr values('"+bookorigin+"',"+s+",'"+name.getText()+"','"+bookdestination+"','"+bookflightno+"')", "UTF-8");
URL url = new URL(getCodeBase(), "hello?query=" + query + "&exeup=1" + "&num=5");

URLConnection servletconnection=url.openConnection();

servletconnection.setDoInput(true);  //require output from servlet

InputStream in=servletconnection.getInputStream();

String ss="";
int chh;
loop:while(1>0){
chh=in.read();
if(chh==-1) break loop;
else ss+=(char)chh;
}


  }//try to insert into pnr close

        catch(MalformedURLException e){
ta.setText("Malformed URL Exception occured.");
}

catch(IOException e){
ta.setText("IO exception occured");
  }


JOptionPane.showMessageDialog(null,"Booking Successful. Your PNR is:"+s,"Prompt",JOptionPane.INFORMATION_MESSAGE);
//JOptionPane.showMessageDialog(null,"insert into pnr values('"+bookorigin+"',"+s+",'"+name.getText()+"','"+bookdestination+"','"+bookflightno+"')","Prompt",JOptionPane.INFORMATION_MESSAGE);




          }//successful booking close
          else {JOptionPane.showMessageDialog(null,"Not a valid choice!","Prompt",JOptionPane.INFORMATION_MESSAGE); }

}
          else{ JOptionPane.showMessageDialog(null,"Name cannot be blank.","Prompt",JOptionPane.INFORMATION_MESSAGE);}

}

else { JOptionPane.showMessageDialog(null,"No flights found!","Prompt",JOptionPane.INFORMATION_MESSAGE); }


//JOptionPane.showMessageDialog(null,"ch="+ ch.getText() + "--","Prompt",JOptionPane.INFORMATION_MESSAGE);

}//bookthem function close



public void populate(){

       //JOptionPane.showMessageDialog(null,"Inside Populate Function","Prompt",JOptionPane.INFORMATION_MESSAGE);
       //System.out.println("Inside Populate function");
       //int noofrows=numanswers/4;
  anstime=new String[numanswers/4];   //will hold routetime info, like "cost duration dept_time, arr_time", "cost, duration dept_time, arr_time" ....

  String fnos[]=new String[numanswers/4];       //contains extracted flight nos from the obtained results

  for(int i=0;i<numanswers/4;i++){

     StringTokenizer st=new StringTokenizer(ans[i]);

     int jj=0;
     while(st.hasMoreTokens()){
     String cc=st.nextToken();

     if(jj==2){
       fnos[i]=cc;
       jj++;
       continue;
       }
     jj++;
     }//while st has more tokens close
      //JOptionPane.showMessageDialog(null,"--"+fnos[i]+"--","Prompt",JOptionPane.INFORMATION_MESSAGE);
     
}//i loop close


   for(int q=0;q<numanswers/4;q++){


  try{



    String query = URLEncoder.encode("select * from routetime where flight_no='"+fnos[q]+"'", "UTF-8");
URL url = new URL(getCodeBase(), "hello?query=" + query + "&exeup=0" + "&num=5");

URLConnection servletconnection=url.openConnection();

servletconnection.setDoInput(true);  //require output from servlet


InputStream in=servletconnection.getInputStream();
String s="";
int ch;
loop:while(1>0){
ch=in.read();
if(ch==-1) break loop;
else s+=(char)ch;
}

  //JOptionPane.showMessageDialog(null,s,"Prompt",JOptionPane.INFORMATION_MESSAGE);

  StringTokenizer st=new StringTokenizer(s,"\n");

  String temp="";
   for(int ii=0;ii<4;ii++){       //cost, duration dept_time, arr_time
    if(ii==0){
      temp+=(Integer.parseInt(st.nextToken()))*(Integer.parseInt(num.getSelectedItem())) + "   ";}
      else{
   temp+=st.nextToken() + "   ";}
  }//ii loop close
  
   //JOptionPane.showMessageDialog(null,temp,"Prompt",JOptionPane.INFORMATION_MESSAGE);

   ans[q]+=temp;



  }//try close

         catch(MalformedURLException e){
//ta.setText("Malformed URL Exception occured.");
}

catch(IOException e){
//ta.setText("IO exception occured");
  }

  }//q loop close
 
 



}//populate function close


public void addHorizontalLine(Color c)
   {
      // Add a Canvas 10000 pixels wide but
      // only 1 pixel high, which acts as
      // a horizontal line.
      Canvas line = new Canvas( );
      line.setSize(1000, 3);
      line.setBackground(c);

      add(line);
   }


} //class close