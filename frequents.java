import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;


/*
<applet code="frequents" width=450 height=400>
</applet>
*/

public class frequents extends Applet implements ActionListener, KeyListener{

Image img1,img2,img3,img4;
Label l1;
MediaTracker mt;
TextField tf;
TextArea ta;
Panel p1,p2,p3;
Button b,back;

public void init(){
setBackground(Color.gray);

setLayout(new FlowLayout());

p1=new Panel();
p2=new Panel();
p3=new Panel();

p1.setLayout(new FlowLayout(FlowLayout.LEFT));
p2.setLayout(new FlowLayout(FlowLayout.CENTER));
//p3.setLayout(new FlowLayout());


mt=new MediaTracker(this);
tf=new TextField(20);
ta=new TextArea();

l1=new Label("Customer Name:");

b=new Button("Fetch");
back=new Button("Back");

//img1=Toolkit.getDefaultToolkit().getImage("aai.gif");
//mt.addImage(img1,0);

addHorizontalLine(Color.gray);

p1.add(l1);
p1.add(tf);

p2.add(b);
p2.add(back);

p3.add(ta);

b.addActionListener(this);
back.addActionListener(this);
tf.addKeyListener(this);

add(p1);
addHorizontalLine(Color.gray);
addHorizontalLine(Color.gray);
add(p2);
addHorizontalLine(Color.gray);
addHorizontalLine(Color.gray);
add(p3);
}//init close


public void keyReleased(KeyEvent ke){}
public void keyTyped(KeyEvent ke){}


public void keyPressed(KeyEvent ke){
  int key=ke.getKeyCode();

if(key==KeyEvent.VK_ENTER){
  
   if(tf.getText().length()==0){
JOptionPane.showMessageDialog(null,"Enter a name","Prompt",JOptionPane.INFORMATION_MESSAGE);
}


else { find(); }

}//vk_enter close


  } //keyPressed close





public void actionPerformed(ActionEvent ae){


if(ae.getActionCommand().equals("Back")) {

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

else { 
  
  if(tf.getText().length()==0){
JOptionPane.showMessageDialog(null,"Enter a name","Prompt",JOptionPane.INFORMATION_MESSAGE);
}


else { find(); }

}

}


public void find(){
String ss="";

  try{
  String query = URLEncoder.encode("select * from frequents where customer_name='"+tf.getText()+"'", "UTF-8");
URL url = new URL(getCodeBase(), "hello?query=" + query + "&exeup=0" + "&num=4");

URLConnection servletconnection=url.openConnection();

servletconnection.setDoInput(true);  //require output from servlet

InputStream in=servletconnection.getInputStream();

int chh;
loop:while(1>0){
chh=in.read();
if(chh==-1) break loop;
else ss+=(char)chh;
}


  }

catch(MalformedURLException e){
ta.setText("Malformed URL Exception occured.");
}

catch(IOException e){
ta.setText("IO exception occured");
  }

  if(ss.length()>3){
  //ta.setText(ss);

  StringTokenizer st=new StringTokenizer(ss,"\n");
  int i=0;
  String memberid="", membername="", airlinename="", membership="";
  while(st.hasMoreTokens()){
    if(i==0) { memberid=st.nextToken(); i++; continue;}
    if(i==1) { membername=st.nextToken(); i++; continue;}
    if(i==2) { airlinename=st.nextToken(); i++; continue;}
    if(i==3) { membership=st.nextToken(); i++; continue;}
     i++;
     st.nextToken();
    }//has more tokens close

    ta.setText("Member ID:" +memberid + "\nFrequent Flyer:" + membername + "\nAirlines:" + airlinename + "\nMembership:" + membership);

}

  else { ta.setText("Frequent Flyer not found"); }

 //ta.setText(ss);


}



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


   public void paint(Graphics g){
    //if(img1!=null)
     // g.drawImage(img1,100,100,this);

     // else tf.setText("Image not available");

   }


}