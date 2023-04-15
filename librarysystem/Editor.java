package librarysystem;

import business.*;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


 class EditMemberDetailWindow  extends  JPanel implements LibWindow{

    public static final EditMemberDetailWindow INSTANCE = new EditMemberDetailWindow();
    SystemController ci = new SystemController();
    private boolean isInitialized = false;
    public static LibraryMember libraryMember;
    private JPanel mainPanel;
    private JTextField firstNameTextField;
    private JLabel firstnameLabel;
    private JTextField lastNameField;
    private JLabel lastNameLabel;

    private JTextField telephoneField;
    private JLabel telephoneLabel;
    private JLabel saveLibraryMemberHeadingLabel;
    private JButton saveButton;
    private JTextField streetTextField;
    private JTextField cityTextField;
    private JLabel addressLabel;
    private JLabel streetLabel;
    private JLabel cityLabel;
    private JLabel stateLabel;
    private JTextField stateTextField;
    private JTextField zipcodeTextField;
    private JLabel zipCodeLabel;




    public boolean isInitialized() {
        return isInitialized;
    }
    public void isInitialized(boolean val) {
        isInitialized = val;
    }
    private JTextField messageBar = new JTextField();

    private void constructComponents() {

        //construct components
        saveButton = new JButton ("save member");
        firstNameTextField = new JTextField (libraryMember.getFirstName() ,8);

        firstnameLabel = new JLabel ("First Name");
        lastNameField = new JTextField("",0);
        lastNameField = new JTextField (libraryMember.getLastName(),0);
        lastNameLabel = new JLabel ("Last Name");

        telephoneField = new JTextField (libraryMember.getTelephone(),5);
        telephoneLabel = new JLabel ("Telephone");
        saveLibraryMemberHeadingLabel = new JLabel ("Edit Member Details");
        Util.adjustLabelFont(saveLibraryMemberHeadingLabel, Color.BLUE.darker(), true);

        streetTextField = new JTextField (libraryMember.getAddress().getStreet(),5);
        cityTextField = new JTextField (libraryMember.getAddress().getCity(),5);
        addressLabel = new JLabel ("Address");
        streetLabel = new JLabel ("Street");
        cityLabel = new JLabel ("City");
        stateLabel = new JLabel ("State");
        stateTextField = new JTextField (libraryMember.getAddress().getState(),5);
        zipcodeTextField = new JTextField (libraryMember.getAddress().getZip(),5);
        zipCodeLabel = new JLabel ("Zipcode");

    }

    private void addComponents() {

        add (firstNameTextField);

        add (firstnameLabel);
        add (lastNameField);
        add (lastNameLabel);

        add (telephoneField);
        add (telephoneLabel);
        add (saveLibraryMemberHeadingLabel);

        add (streetTextField);
        add (cityTextField);
        add (addressLabel);
        add (streetLabel);
        add (cityLabel);
        add (stateLabel);
        add (stateTextField);
        add (zipcodeTextField);
        add (zipCodeLabel);
        add (saveButton);
    }

    private void setBounds() {
        //set component bounds (only needed by Absolute Positioning)
        firstNameTextField.setBounds (125, 110, 145, 25);
        firstnameLabel.setBounds (45, 110, 65, 25);
        lastNameField.setBounds (125, 165, 145, 25);
        lastNameLabel.setBounds (45, 165, 100, 25);

        telephoneField.setBounds (390, 110, 140, 25);
        telephoneLabel.setBounds (315, 110, 100, 25);
        saveLibraryMemberHeadingLabel.setBounds (250, 30, 155, 50);
        saveButton.setBounds (270, 355, 115, 30);
        streetTextField.setBounds (125, 260, 145, 25);
        cityTextField.setBounds (390, 260, 145, 25);
        addressLabel.setBounds (270, 215, 100, 25);
        streetLabel.setBounds (50, 260, 100, 25);
        cityLabel.setBounds (325, 255, 100, 25);
        stateLabel.setBounds (50, 310, 100, 25);
        stateTextField.setBounds (125, 310, 145, 25);
        zipcodeTextField.setBounds (390, 315, 150, 25);
        zipCodeLabel.setBounds (320, 315, 100, 25);


    }


    /* This class is a singleton */
    private EditMemberDetailWindow () {
        super(new BorderLayout());


    }



    public void init() {

        mainPanel = new JPanel();
        mainPanel.setLayout(null);

        constructComponents();

        setBounds();
        addComponents();
        //    		Add listeners
        saveLibraryMemberListener(saveButton);
        add(mainPanel);
        isInitialized(true);
        mainPanel.setSize(821,650);
        setSize(821,650);
    }



    private void clearFields() {
        firstNameTextField.setText("");
        lastNameField.setText("");
        telephoneField.setText("");
        streetTextField.setText("");
        cityTextField.setText("");
        stateTextField.setText("");
        zipcodeTextField.setText("");
    }



    private void saveLibraryMemberListener(JButton butn) {
        butn.addActionListener(evt -> {

                Address currAddress= new Address(streetTextField.getText(), cityTextField.getText(),
                        stateTextField.getText(), zipcodeTextField.getText());
                LibraryMember currLM=new LibraryMember(libraryMember.getMemberId(),firstNameTextField.getText(), lastNameField.getText(), telephoneField.getText(),currAddress);
                ci.editAMember(currLM);
                JOptionPane.showMessageDialog(EditMemberDetailWindow.this,"the member \""+libraryMember.getMemberId()+"\"  details edited successfully");
                AllMemberIdsWindow.INSTANCE.reloadMember();
                Editor.getInstance().navigateToMainEdit();
                clearFields();




        });
    }


}

 class EditMemberMainWindow extends JPanel implements LibWindow {
    public static final EditMemberMainWindow INSTANCE = new EditMemberMainWindow();
    SystemController ci = new SystemController();

    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;

    private JTextField memberIDText;


    private boolean isInitialized = false;

    private EditMemberMainWindow() {
        super(new CardLayout());
        init();
    }

    public void init() {
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.GREEN);

        mainPanel.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();


        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(middlePanel, BorderLayout.CENTER);
        isInitialized = true;
        mainPanel.setSize(821,650);
        setSize(821,650);
        add(mainPanel);

    }


    public void defineTopPanel() {

        topPanel = new JPanel();
        topPanel.add(Box.createRigidArea(new Dimension(0,120)), BorderLayout.NORTH);

        JLabel AllIDsLabel = new JLabel("Edit member");
        Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(AllIDsLabel,BorderLayout.CENTER);
    }


    public void defineMiddlePanel() {
        middlePanel = new JPanel();

        middlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0)); // set the layout to horizontal flow
        JLabel memberID = new JLabel("Member ID");
        memberIDText = new JTextField(11);
        JButton editButn = new JButton("edit");
        SystemController sc = new SystemController();
        editButn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String memberID = memberIDText.getText();

                    try {
                        LibraryMember  libraryMember = sc.getMemberById(memberID);
                        EditMemberDetailWindow.libraryMember=libraryMember;
                        Editor.getInstance().navigateToEditorDetails();
                        clearFields();


                    } catch (LibrarySystemException ex) {
//
                        JOptionPane.showMessageDialog(EditMemberMainWindow.this,
                                ex.getMessage());
                    }

            }
        });
        middlePanel.add(memberID);
        middlePanel.add(memberIDText);
        middlePanel.add(editButn);
    }

     private void clearFields() {
         memberIDText.setText("");

     }
//    private void edit(String memberID) throws LibrarySystemException {
//        SystemController sc = new SystemController();
//
//        try {
//          LibraryMember  libraryMember = sc.getMemberById(memberID);
//            EditMemberDetailWindow.libraryMember=libraryMember;
//            Editor.getInstance().navigateToEditorDetails(libraryMember);
//            clearFields();
//
//
//        } catch (LibrarySystemException e) {
////
//            JOptionPane.showMessageDialog(EditMemberMainWindow.this,
//                    e.getMessage());
//        }
//
//
//
//    }


    @Override
    public boolean isInitialized() {

        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;

    }

}


 public class Editor extends JPanel {


    private static final long serialVersionUID = 1L;
    private static Editor mainApp = new Editor();
    private JPanel mainPanel;
    private EditMemberMainWindow editMemberMainWindow;
    private EditMemberDetailWindow editMemberDetailWindow;
    private String[] listPanel = { "Edit", "Edit member" };
    public static LibraryMember libraryMember;

    private Editor() {
        super(new CardLayout());
        initComponents();
        setVisible(true);
//        centerFrameOnDesktop(this);


    }

    private void initComponents() {

        initLayouts();

    }



    private void initLayouts() {

        mainPanel = new JPanel(new CardLayout());
        mainPanel.setBackground(Color.GREEN);
        editMemberMainWindow = EditMemberMainWindow.INSTANCE;
        editMemberDetailWindow = EditMemberDetailWindow.INSTANCE;

        mainPanel.add(listPanel[0],  editMemberMainWindow);

        mainPanel.add(listPanel[1], editMemberDetailWindow);

        add(mainPanel);
        mainPanel.setSize(821,650);
        setSize(821,650);
    }
     public static void centerFrameOnDesktop(Component f) {
         Toolkit toolkit = Toolkit.getDefaultToolkit();
         int height = toolkit.getScreenSize().height;
         int width = toolkit.getScreenSize().width;
         int frameHeight = f.getSize().height;
         int frameWidth = f.getSize().width;
         f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
     }

    public void navigateToMainEdit() {
        ((CardLayout) (mainPanel.getLayout())).show(mainPanel, listPanel[0]);
    }

    public void navigateToEditorDetails() {
        editMemberDetailWindow.init();
        ((CardLayout) (mainPanel.getLayout())).show(mainPanel, listPanel[1]);
    }

    public static Editor getInstance() {
        return mainApp;
    }





}
