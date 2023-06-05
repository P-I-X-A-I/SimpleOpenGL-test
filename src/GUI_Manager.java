import javax.swing.*;

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;

public class GUI_Manager {

	JFrame mainWin_obj;
	GLJPanel glPane_obj;
	
	// constructor
	public GUI_Manager()
	{
		//
		System.out.println("GUI_Manager init, GUI creation start.");
		
		//
		mainWin_obj = new JFrame("3DP-DP");
		mainWin_obj.setSize(1000, 650);
		mainWin_obj.setBounds(200, 200, 1000, 650);
		mainWin_obj.setResizable(false);
		mainWin_obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWin_obj.setLayout(null);
		mainWin_obj.setVisible(true);

		GLProfile profile = GLProfile.get(GLProfile.GL4);
		GLCapabilities caps = new GLCapabilities(profile);
		glPane_obj = new GLJPanel(caps);
		
		
		// show window
	}
}
