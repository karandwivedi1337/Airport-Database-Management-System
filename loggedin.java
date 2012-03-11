import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
//import javax.swing.*;

/*
<applet code="loggedin" width=400 height=300>
</applet>
*/

public class loggedin extends Applet implements ItemListener, ActionListener, KeyListener{

Panel p1,p2,p3;
CheckboxGroup c;
Checkbox c1,c2;
Button b,logout;



public void init(){
setBackground(Color.GRAY);
p1=new Panel();
p2=new Panel();
p3=new Panel();


p1.setLayout(new FlowLayout(FlowLayout.LEFT));
p2.setLayout(new FlowLayout(FlowLayout.LEFT));

p3.setLayout(new FlowLayout(FlowLayout.RIGHT));
c=new CheckboxGroup();
c1=new Checkbox("Search for Flights",c,true);
c2=new Checkbox("Manage booking",c,false);

b=new Button("Proceed");
logout=new Button("Logout");


c1.addItemListener(this);
c2.addItemListener(this);
b.addActionListener(this);
logout.addActionListener(this);

p1.add(c1);
p2.add(c2);
p3.add(b);
p3.add(logout);

add(p1);
addHorizontalLine(Color.GRAY);
add(p2);
addHorizontalLine(Color.GRAY);
add(p3);
c1.addKeyListener(this);
c2.addKeyListener(this);
b.addKeyListener(this);

}

public void keyReleased(KeyEvent ke){}
public void keyTyped(KeyEvent ke){}


public void keyPressed(KeyEvent ke){
  int key=ke.getKeyCode();

if(key==KeyEvent.VK_ENTER)
transfer(c.getSelectedCheckbox().getLabel());

  } //keyPressed close



public void start(){
}


public void itemStateChanged(ItemEvent ie){
repaint();}

public void actionPerformed(ActionEvent ae){
  
  if(ae.getActionCommand().equals("Logout")){

      AppletContext ac=getAppletContext();
    URL url=getCodeBase();

try{
ac.showDocument(new URL(url+ "login.html"));
System.out.println(url.toString());
//showStatus(url+"book.html");
}

catch(MalformedURLException e){
showStatus("Database is down.");}



  }

else{
transfer(c.getSelectedCheckbox().getLabel());}
}


public void transfer(String s){
  
  if(s.equals("Search for Flights")){
    
    AppletContext ac=getAppletContext();
    URL url=getCodeBase();

try{
ac.showDocument(new URL(url+ "book.html"));
System.out.println(url.toString());
showStatus(url+"book.html");
}

catch(MalformedURLException e){
showStatus("Database is down.");}



  }
  
  else{
    
    
     AppletContext ac=getAppletContext();
    URL url=getCodeBase();

try{
ac.showDocument(new URL(url+ "manage.html"));
System.out.println(url.toString());
showStatus(url+"manage.html");
}

catch(MalformedURLException e){
showStatus("Database is down.");}

  }


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
}

}





