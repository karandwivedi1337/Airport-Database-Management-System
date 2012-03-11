import java.io.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.sql.*;
import java.net.*;
import java.util.*;

/*
<applet code="manage" width=600 height=300>
</applet>
*/

public class manage extends Applet implements ActionListener, KeyListener{

Label l1,l2,l3;
Panel p1,p2,p3,p4;
TextField t1,t2;
TextArea ta;
Button b,back;
Button bb[];
Label ll[];


public void init(){
setBackground(Color.GRAY);

setLayout(new FlowLayout(FlowLayout.CENTER));

p1=new Panel();
p2=new Panel();
p3=new Panel();
p4=new Panel();

p1.setLayout(new FlowLayout(FlowLayout.CENTER));
p2.setLayout(new FlowLayout(FlowLayout.CENTER));
p3.setLayout(new FlowLayout(FlowLayout.CENTER));
p4.setLayout(new FlowLayout(FlowLayout.LEFT));

t1=new TextField(10);
t2=new TextField(20);
ta=new TextArea("",2,50);

l1=new Label("PNR:");
l2=new Label("Customer Name:");
l3=new Label("PNR Result:");

b=new Button("Submit");
back=new Button("Back");

p1.add(l1);
p1.add(t1);
p2.add(l2);
p2.add(t2);
p3.add(b);
p3.add(back);
p4.add(l3);
p4.add(ta);

add(p1);
addHorizontalLine(Color.GRAY);
add(p2);
addHorizontalLine(Color.GRAY);
add(p3);
addHorizontalLine(Color.GRAY);
add(p4);


b.addActionListener(this);
back.addActionListener(this);
t1.addKeyListener(this);
t2.addKeyListener(this);

}//constructor close


public void keyReleased(KeyEvent ke){}
public void keyTyped(KeyEvent ke){}


public void keyPressed(KeyEvent ke){
int key=ke.getKeyCode();

if(key==KeyEvent.VK_ENTER)
execute();
} //keyPressed close


public void actionPerformed(ActionEvent ae){
  
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



  } //ae.getActionCommand close

  else{

  if(t1.getText().equals("") || t2.getText().equals("")){
    JOptionPane.showMessageDialog(null,"Fields cannot be blank","Prompt",JOptionPane.INFORMATION_MESSAGE);}

    else{   //fetch pnr details
    execute();
    }  //else fetch pnr close
    
  }//else close
}//addactionListener close


public void execute(){
  
  setForeground(Color.WHITE);

    String pnr_id=t1.getText();
    String customer_name=t2.getText();

    try{
String query = URLEncoder.encode("select * from pnr where pnr_id=" + pnr_id + " and customer_name='" + customer_name +"'", "UTF-8");
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

//ta.setText(s.length()+"");


if(s.length()>3){
//ta.setText(s);

StringTokenizer st=new StringTokenizer(s,"\n");
int i=0;
String ans[]=new String[7];

while(st.hasMoreTokens()){
ans[i++]=st.nextToken();
}

String output="Origin: "+ans[0]+ " Destination: " + ans[3] + " Flight No: " + ans[4] + "\nStatus: Scheduled";
ta.setText(output);

}

else {
  s="No matching PNR's found";
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

public void addHorizontalLine(Color c)
   {
      // Add a Canvas 10000 pixels wide but
      // only 1-5 pixel high, which acts as
      // a horizontal line.
      Canvas line = new Canvas( );
      line.setSize(1000, 3);
      line.setBackground(c);
      add(line);
   }

} //class and main close