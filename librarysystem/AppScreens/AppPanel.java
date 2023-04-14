package librarysystem.AppScreens;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import business.SystemController;
import dataaccess.Auth;
import librarysystem.*;

public class AppPanel extends JPanel {
	public static AppPanel INSTANCE = new AppPanel();
	// ITEN
	private JSplitPane splitPane;

	private JPanel leftSide;
	private JPanel rightSide;

	private JButton logoutBtn;

	private JList sideBarMenuList;

	private JPanel  addBookPanel, addMember, AllMemberIds, AllBook, allRecordsWindow, overdueBooksWindow;
	private CheckOutBookWindow checkOutBookWindow;
	private AddBookCopyWindow addBookCopy;
	private CheckoutRecordWindow checkoutRecord;
	private BookCopiesWindow bookCopiesWindow;
	private Editor editMember;
	private String[] listMenu = { "Add Member", "Add Book", "Add Book Copy", "Members", "Books",
			"CheckOut Book", "CheckOut Record", "Copies","Edit member", "All Checkout Records", "Overdue Books" };
	private String[] listAdminMenu = { "Add Member", "Add Book", "Add Book Copy", "Members", "Books",
			"Copies" ,"Edit member"};
	private String[] listLibrarianMenu = {  "CheckOut Book", "CheckOut Record" };

	private AppPanel() {
		super(new CardLayout());
		initComponents();
	}

	private String[] getRoleMenu() {
		if (SystemController.currentAuth == Auth.ADMIN) {
			return listAdminMenu;
		} else if (SystemController.currentAuth == Auth.LIBRARIAN) {
			return listLibrarianMenu;
		} else {
			return listMenu;
		}
	}

	public void setRoleMenu() {
		leftSide.removeAll();
		paintMenu();
//		goToDashBoard();
	}

	private void setLeftAppSidePanel() {
		leftSide = new JPanel();
		leftSide.setBackground(Color.GRAY);
		paintMenu();
	}

	public void paintMenu() {
		sideBarMenuList = new JList<String>(getRoleMenu());
		sideBarMenuList.setBackground(Color.GRAY);
		sideBarMenuList.setForeground(Color.white);
		sideBarMenuList.setSelectedIndex(0);

		sideBarMenuList.setFixedCellHeight(40);

		sideBarMenuList.setSelectionForeground(Color.WHITE);
		sideBarMenuList.setSelectionBackground(Color.lightGray);

		sideBarMenuList.addListSelectionListener(event -> {
			((CardLayout) (rightSide.getLayout())).show(rightSide,
					sideBarMenuList.getSelectedValue().toString());
		});



		DefaultListCellRenderer renderer = (DefaultListCellRenderer) sideBarMenuList.getCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);

		logoutBtn = new JButton("Logout");
		logoutBtn.setBackground(Color.WHITE);


		logoutBtn.addActionListener(e -> {
			Main.getInstance().navigateToLogin();
		});

		leftSide.add(sideBarMenuList);
		leftSide.add(logoutBtn);

	}

	private void setRightAppSidePanel() {
		rightSide = new JPanel(new CardLayout());
		rightSide.setBackground(Color.white);


		addBookPanel = AddBookWindow.INSTANCE;
		addMember = AddNewLibraryMemberWindow.INSTANCE;
		addBookCopy = AddBookCopyWindow.INSTANCE;
		AllMemberIds = AllMemberIdsWindow.INSTANCE;
		AllBook = AllBookIdsWindow.INSTANCE;
		checkOutBookWindow = CheckOutBookWindow.INSTANCE;
		checkoutRecord = CheckoutRecordWindow.INSTANCE;
		bookCopiesWindow = BookCopiesWindow.INSTANCE;
		editMember = Editor.getInstance();
		allRecordsWindow = AllRecordsWindow.INSTANCE;
		overdueBooksWindow = OverdueBooksWindow.INSTANCE;


		rightSide.add(listMenu[0], addMember);
		rightSide.add(listMenu[1], addBookPanel);
		rightSide.add(listMenu[2], addBookCopy);
		rightSide.add(listMenu[3], AllMemberIds);
		rightSide.add(listMenu[4], AllBook);
		rightSide.add(listMenu[5], checkOutBookWindow);
		rightSide.add(listMenu[6], checkoutRecord);
		rightSide.add(listMenu[7], bookCopiesWindow);
		rightSide.add(listMenu[8], editMember);
		rightSide.add(listMenu[9], allRecordsWindow);
		rightSide.add(listMenu[10], overdueBooksWindow);



	}



	private void initComponents() {

		setRightAppSidePanel();
		setLeftAppSidePanel();

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftSide, rightSide);
		splitPane.setDividerLocation(150);
		// Add the SplitPane to the Pane
		add(splitPane, BorderLayout.CENTER);

	}
}
