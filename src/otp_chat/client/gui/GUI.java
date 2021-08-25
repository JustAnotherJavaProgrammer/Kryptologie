package otp_chat.client.gui;

import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class GUI {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 750, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("OTPChat");
		changeContentPane(new ConnectPanel(this));
	}
	
	public void changeContentPane(Container contentPane) {
		boolean change = contentPane == frame.getContentPane();
		frame.setContentPane(contentPane);
		if (change)
			frame.requestFocus();
//		frame.pack();
		frame.revalidate();
		contentPane.requestFocusInWindow();
	}
	
	protected JFrame getFrame() {
		return frame;
	}

}
