import javax.swing.*;
import javax.swing.SwingUtilities;

public class mainController {

	static GUI_Manager GUI_obj;
	
	
	public static void main(String[] args) {
		
		// Entry point
		//System.out.println("Entry main function:");

		// GUI Creation on EDT
		SwingUtilities.invokeLater(new Runnable() {
			public void run()
			{
				GUI_obj = new GUI_Manager();
			}
			
		});
	}
}

