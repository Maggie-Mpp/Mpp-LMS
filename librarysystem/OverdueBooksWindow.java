package librarysystem;

import business.*;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OverdueBooksWindow extends JPanel implements LibWindow {
    public static final OverdueBooksWindow INSTANCE = new OverdueBooksWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private TextArea textArea;
    private JTextField memberIDText;
    private JTextField isbnText;

    private JTable recordsTable;
    private JButton overdueButton;
    private DefaultTableModel model;

    private OverdueBooksWindow() {
        super(new CardLayout());
        init();
    }

    public void init() {
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
    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AllIDsLabel = new JLabel("Overdue Books");
        Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(AllIDsLabel);
        isbnText = new JTextField(11);

        JLabel isbn = new JLabel("ISBN");
        topPanel.add(isbn);

        overdueButton = new JButton("Get Books");

        topPanel.add(isbnText);
        topPanel.add(overdueButton);
    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());

        recordsTable = new JTable();

        //scrollPane.setViewportView(recordsTable);
         model = new DefaultTableModel();


        model.addColumn("Title");
        model.addColumn("ISBN");
        model.addColumn("Copy number");
        model.addColumn("Checkout Date");
        model.addColumn("Was due before");
        model.addColumn("Member Name");
        model.addColumn("Passed by days");
        recordsTable.setModel(model);
        middlePanel.add(new JScrollPane(recordsTable));

        SystemController sc = new SystemController();
        overdueButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String isbnString = isbnText.getText();

                try {
                    showOverdueBooks(isbnString);
//

                } catch (LibrarySystemException lse) {

                    JOptionPane.showMessageDialog(OverdueBooksWindow.this,
                            lse.getMessage());
                }


            }
        });
    }

    private void showOverdueBooks(String isbn) throws  LibrarySystemException{
        // Use JTable to render member’s checkout record
        SystemController sc = new SystemController();

        ArrayList<String[]> memberOverdue;
        try {
            memberOverdue = sc.getOverdueBooks(isbn);

            if (memberOverdue != null) {
                for (String[] memOverdue : memberOverdue) {
                    model.addRow(memOverdue);
                    //System.out.println(format);
                }
            }

        } catch (LibrarySystemException e) {
            throw new RuntimeException(e);
        }





    }


    protected void clearJTextFields() {

        isbnText.setText("");
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