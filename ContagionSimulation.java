import java.awt.Frame;

public class ContagionSimulation 
{
	public static void main (String[] args)
   	{
      	//Instantiate the GUI part
      	Frame frm = new TheGUIPart(false);    
      	//Set the application's window width and height in pixels
      	frm.setSize (300, 800);  
      	//Make the window visible to the user
      	frm.setVisible (true);           
   	}
}
