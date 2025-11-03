package gui;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private JTextField personName;
    private JTextField personSurname;
    private JTextField personBirthday;
    private JTextField personID;
    private JTextField testDate;
    private JTextField testDateTo;
    private JTextField testDaysAfter;
    private JTextField testPersonID;
    private JTextField testCode;
    private JTextField workplace;
    private JTextField region;
    private JTextField district;
    private JTextField testResult;
    private JTextField testValue;
    private JTextField note;

    private JButton generateButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton clearButton;

    private final JButton[] methodButtons = new JButton[21];

    private JTextArea outputArea;

    public View() {
        setTitle("WHO System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLayout(new BorderLayout());

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JPanel basicButtons = new JPanel();
        basicButtons.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        basicButtons.add(Box.createVerticalStrut(10));
        this.generateButton = new JButton("Generate");
        this.saveButton = new JButton("Save");
        this.loadButton = new JButton("Load");
        this.clearButton = new JButton("Clear");
        basicButtons.add(this.generateButton);
        basicButtons.add(this.saveButton);
        basicButtons.add(this.loadButton);
        basicButtons.add(this.clearButton);

        JPanel inputs = new JPanel(new GridLayout(18, 2, 5, 5));
        inputs.setBorder(BorderFactory.createTitledBorder("Inputs"));

        inputs.add(new JLabel("PERSON inputs"));
        inputs.add(new JLabel(""));
        this.personName = new JTextField(10);
        this.personSurname = new JTextField(10);
        this.personBirthday = new JTextField(10);
        this.personID = new JTextField(10);
        inputs.add(new JLabel("[19] First name"));
        inputs.add(this.personName);
        inputs.add(new JLabel("[19] Last name"));
        inputs.add(this.personSurname);
        inputs.add(new JLabel("[19] Date of Birth"));
        inputs.add(this.personBirthday);
        inputs.add(new JLabel("[19,21] Person ID"));
        inputs.add(this.personID);

        inputs.add(new JLabel(""));
        inputs.add(new JLabel(""));
        inputs.add(new JLabel("PCR TEST inputs"));
        inputs.add(new JLabel(""));
        this.testDate = new JTextField(10);
        this.testDateTo = new JTextField(10);
        this.testDaysAfter = new JTextField(10);
        this.testPersonID = new JTextField(10);
        this.testCode = new JTextField(10);
        this.workplace = new JTextField(10);
        this.region = new JTextField(10);
        this.district = new JTextField(10);
        this.testResult = new JTextField(10);
        this.testValue = new JTextField(10);
        this.note = new JTextField(10);
        inputs.add(new JLabel("[1,4-17] Date of test / date from"));
        inputs.add(this.testDate);
        inputs.add(new JLabel("[4-9,17] Date to (max)"));
        inputs.add(this.testDateTo);
        inputs.add(new JLabel("[10-16] Positive X days after"));
        inputs.add(this.testDaysAfter);
        inputs.add(new JLabel("[1-3] Person ID"));
        inputs.add(this.testPersonID);
        inputs.add(new JLabel("[1,2,18,20] Test Code"));
        inputs.add(this.testCode);
        inputs.add(new JLabel("[1,17] Workplace code"));
        inputs.add(this.workplace);
        inputs.add(new JLabel("[1,6,7,12] Region code"));
        inputs.add(this.region);
        inputs.add(new JLabel("[1,4,5,10,11] District code"));
        inputs.add(this.district);
        inputs.add(new JLabel("[1] Test result"));
        inputs.add(this.testResult);
        inputs.add(new JLabel("[1] Test value"));
        inputs.add(this.testValue);
        inputs.add(new JLabel("[1] Note"));
        inputs.add(this.note);

        JPanel methods = new JPanel();
        methods.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        methods.setBorder(BorderFactory.createTitledBorder("Methods"));
        for (int i = 0; i < this.methodButtons.length; ++i) {
            this.methodButtons[i] = new JButton((i + 1) + ".");
            methods.add(this.methodButtons[i]);
        }

        this.outputArea = new JTextArea();
        this.outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(this.outputArea);

        leftPanel.add(basicButtons);
        leftPanel.add(inputs);

        add(leftPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(methods, BorderLayout.SOUTH);
    }

    public String getPersonName() {
        return this.personName.getText();
    }

    public String getPersonSurname() {
        return this.personSurname.getText();
    }

    public String getPersonBirthday() {
        return this.personBirthday.getText();
    }

    public String getPersonID() {
        return this.personID.getText();
    }

    public String getTestDate() {
        return this.testDate.getText();
    }

    public String getTestDateTo() {
        return this.testDateTo.getText();
    }

    public String getTestDaysAfter() {
        return this.testDaysAfter.getText();
    }

    public String getTestPersonID() {
        return this.testPersonID.getText();
    }

    public String getTestCode() {
        return this.testCode.getText();
    }

    public String getWorkplace() {
        return this.workplace.getText();
    }

    public String getRegion() {
        return this.region.getText();
    }

    public String getDistrict() {
        return this.district.getText();
    }

    public String getTestResult() {
        return this.testResult.getText();
    }

    public String getTestValue() {
        return this.testValue.getText();
    }

    public String getNote() {
        return this.note.getText();
    }

    public void clearInputFields() {
        this.personName.setText("");
        this.personSurname.setText("");
        this.personBirthday.setText("");
        this.personID.setText("");
        this.testDate.setText("");
        this.testDateTo.setText("");
        this.testDaysAfter.setText("");
        this.testPersonID.setText("");
        this.testCode.setText("");
        this.workplace.setText("");
        this.region.setText("");
        this.district.setText("");
        this.testResult.setText("");
        this.testValue.setText("");
        this.note.setText("");
    }

    public JButton[] getMethodButtons() {
        return this.methodButtons;
    }

    public JButton getGenerateButton() {
        return this.generateButton;
    }

    public JButton getSaveButton() {
        return this.saveButton;
    }

    public JButton getLoadButton() {
        return this.loadButton;
    }

    public JButton getClearButton() {
        return this.clearButton;
    }

    public JTextArea getOutputArea() {
        return this.outputArea;
    }
}
