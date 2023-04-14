package librarysystem;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import business.SystemController;

public class DashboardWindow extends JPanel {
	public static final DashboardWindow INSTANCE = new DashboardWindow();
	SystemController ci = new SystemController();

	private JPanel mainPanel, statitsiquePanel, booksCounterPanel, memberCounterPanel, recordCounterPanel,
			profilePicturePanel;
	private JLabel booksCounterLabel, memberCounterLabel, recordCounterLabel;

	private DashboardWindow() {
		super(new CardLayout());
		init();
	}

	public void init() {
		setProfilePicture();

	}

	private void setProfilePicture() {
		String currDirectory = System.getProperty("user.dir");
		String pathToImage = currDirectory + "/librarysystem/home.jpg";
		ImageIcon image = new ImageIcon(pathToImage);

		JLabel icon = new JLabel(image);
		add(icon, BorderLayout.CENTER);

	}

}
