package librarysystem;


import business.*;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllRecordsWindow extends JPanel implements LibWindow {
    public static final AllRecordsWindow INSTANCE;

    static {
        try {
            INSTANCE = new AllRecordsWindow();
        } catch (LibrarySystemException e) {
            throw new RuntimeException(e);
        }
    }

    SystemController ci = new SystemController();

    private JPanel statitsiquePanel, booksCounterPanel, memberCounterPanel, recordCounterPanel,
            profilePicturePanel;
    private JLabel booksCounterLabel, memberCounterLabel, recordCounterLabel;

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private TextArea textArea;
    private JTextField memberIDText;
    private JLabel errorLabel;
    private JTable recordsTable;

    private boolean isInitialized = false;

    private AllRecordsWindow() throws LibrarySystemException {
        super(new CardLayout());
        init();
    }
    public void init() throws LibrarySystemException{
        //recordsTable.setDefaultEditor(Object.class, null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        defineLowerPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        mainPanel.add(lowerPanel, BorderLayout.SOUTH);
        add(mainPanel);
        isInitialized = true;
        try {
            showMemberCheckRecord();
        } catch (LibrarySystemException e) {
            throw new RuntimeException(e);
        }
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AllIDsLabel = new JLabel("Checkout Records");
        Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(AllIDsLabel);
    }
    public void defineMiddlePanel() throws LibrarySystemException{
        middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 4)); // set the layout to horizontal flow
        JLabel memberID = new JLabel("All Records");
        memberIDText = new JTextField(11);
        SystemController sc = new SystemController();
        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        middlePanel.add(errorLabel);


        JScrollPane scrollPane = new JScrollPane();
        recordsTable = new JTable();
        scrollPane.setViewportView(recordsTable);
        middlePanel.add(scrollPane);
        System.out.println("In main panel");
        showMemberCheckRecord();
    }

    private void showMemberCheckRecord() throws LibrarySystemException {
        // Use JTable to render member’s checkout records

        SystemController sc = new SystemController();
        ArrayList<String[]> allEntries = new ArrayList<String[]>();

        try {
            allEntries = sc.getAllCheckoutRecords();
            for(String[] a: allEntries)
                System.out.println(a);
            errorLabel.setText("");
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Title");
            model.addColumn("ISBN");
            model.addColumn("Number of copies");
            model.addColumn("Checkout Date");
            model.addColumn("Due Date");
            model.addColumn("Member Name");
            if (allEntries != null) {
                for(String[] a: allEntries) {
                    System.out.println(a);
                    model.addRow(a);
                }
            }
            recordsTable.setModel(model);
        }
        catch (LibrarySystemException e) {
//			errorLabel.setText(e.getMessage());
            JOptionPane.showMessageDialog(AllRecordsWindow.this,
                    e.getMessage());

        }


    }

    public void defineLowerPanel() {
        lowerPanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT);
        lowerPanel.setLayout(fl);
        JButton backButton = new JButton("<== Back to Main");
        addBackButtonListener(backButton);
//		lowerPanel.add(backButton);
    }

    public void setData(String data) {
        textArea.setText(data);
    }

    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
    }

    @Override
    public boolean isInitialized() {

        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;

    }


}
