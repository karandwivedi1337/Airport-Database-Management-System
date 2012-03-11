import java.applet.*;
import java.awt.*;
import java.net.*;
import java.awt.event.*;
import java.awt.Graphics.*;
import javax.swing.*;

/*
<applet code="login" width=300 height=300>
</applet>
*/

public class login extends Applet implements ActionListener, KeyListener{

Label l1,l2;
TextField namet,passt;
Button b;
Image img;

public void init(){
setLayout(new FlowLayout(FlowLayout.CENTER));
setBackground(Color.GRAY);
l1=new Label("User Name:", Label.LEFT);
l2=new Label("Password:  ", Label.LEFT);

namet=new TextField(20);
passt=new TextField(20);

passt.setEchoChar('*');

b=new Button("Sign In");

add(l1);
add(namet);
add(l2);
add(passt);
add(b);

b.addActionListener(this);
namet.addActionListener(this);
passt.addActionListener(this);

namet.addKeyListener(this);
passt.addKeyListener(this);

}

public void addHorizontalLine(Color c)
   {
      // Add a Canvas 10000 pixels wide but
      // only 1 pixel high, which acts as
      // a horizontal line.
      Canvas line = new Canvas();
      line.setSize(1000, 75);
      line.setBackground(c);
      add(line);
   }



public void start(){
//setBackground(Color.white);

//setBackground(Color.gray);
}


public void keyReleased(KeyEvent ke){}
public void keyTyped(KeyEvent ke){}


public void keyPressed(KeyEvent ke){
int key=ke.getKeyCode();

if(key==KeyEvent.VK_ENTER)
checklogin(namet.getText(),passt.getText());
} //keyPressed close


public void paint(Graphics g){
img = Toolkit.getDefaultToolkit().createImage("aai.jpeg");
g.drawImage(img, 0, 0, null);
} //paint close

public void actionPerformed(ActionEvent ae){

String str=ae.getActionCommand();
if(str.equals("Sign In"))
checklogin(namet.getText(),passt.getText());

}//actionPerformed Close



public void checklogin(String user, String pass){

if(user.equalsIgnoreCase("administrator") && pass.equals("password")) {

AppletContext ac=getAppletContext();
URL url=getCodeBase();

try{
ac.showDocument(new URL(url+ "loggedin.html"));
System.out.println(url.toString());
showStatus(url+"loggedin.html");
}

catch(MalformedURLException e){
showStatus("Database is down.");}


repaint();
}//if close

else JOptionPane.showMessageDialog(null,"Invalid Username-Password combination","Prompt",JOptionPane.ERROR_MESSAGE);
//showStatus("Invalid Username-Password combination");


}//checklogin close


}//class close

