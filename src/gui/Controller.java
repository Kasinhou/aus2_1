package gui;
import main.Generator;
import main.WHOSystem;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Controller {
    private WHOSystem who;
    private View view;
    private Generator generator;

    public Controller(WHOSystem who, View view) {
        this.who = who;
        this.view = view;
        this.generator = new Generator(this.who);
        this.createMainWindow();
    }

    private void createMainWindow() {
        this.view.getGenerateButton().addActionListener(e -> this.handleGenerateButton());
        this.view.getSaveButton().addActionListener(e -> this.handleSaveButton());
        this.view.getLoadButton().addActionListener(e -> this.handleLoadButton());
        this.view.getClearButton().addActionListener(e -> this.handleClearButton());
        JButton[] methodButtons = view.getMethodButtons();
        for (int i = 0; i < methodButtons.length; ++i) {
            int index = i;
            methodButtons[i].addActionListener(e -> this.handleMethod(index + 1));
        }
    }

    private void handleMethod(int index) {//tODO upravit ak pouzivatel nezada nieco a chce to spravit
        String message = "";
        switch (index) {
            case 1:
                boolean correctAddTest = this.who.addTestResult(LocalDate.parse(this.view.getTestDate().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        this.view.getTestPersonID(), Integer.parseInt(this.view.getTestCode()), Integer.parseInt(this.view.getWorkplace()),
                        Integer.parseInt(this.view.getRegion()), Integer.parseInt(this.view.getDistrict()),
                        Boolean.parseBoolean(this.view.getTestResult()), Double.parseDouble(this.view.getTestValue()), this.view.getNote());
                message = correctAddTest ? "Test successfully added." : "Test has not been added successfully.";
                break;
            case 2:
                message = this.who.showTestResult(Integer.parseInt(this.view.getTestCode()), this.view.getTestPersonID());
                break;
            case 3:
                message = this.who.showAllPersonTests(this.view.getTestPersonID());
                break;
            case 4:
                message = this.who.showPositiveTestsInDistrict(Integer.parseInt(this.view.getDistrict()),
                        LocalDate.parse(this.view.getTestDate().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        LocalDate.parse(this.view.getTestDateTo().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                break;
            case 5:
                message = this.who.showAllTestsInDistrict(Integer.parseInt(this.view.getDistrict()),
                        LocalDate.parse(this.view.getTestDate().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        LocalDate.parse(this.view.getTestDateTo().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                break;
            case 6:
                message = this.who.showPositiveTestsInRegion(Integer.parseInt(this.view.getRegion()),
                        LocalDate.parse(this.view.getTestDate().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        LocalDate.parse(this.view.getTestDateTo().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                break;
            case 7:
                message = this.who.showAllTestsInRegion(Integer.parseInt(this.view.getRegion()),
                        LocalDate.parse(this.view.getTestDate().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        LocalDate.parse(this.view.getTestDateTo().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                break;
            case 8:
                message = this.who.showPositiveTests(LocalDate.parse(this.view.getTestDate().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        LocalDate.parse(this.view.getTestDateTo().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                break;
            case 9:
                message = this.who.showAllTests(LocalDate.parse(this.view.getTestDate().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        LocalDate.parse(this.view.getTestDateTo().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                break;
            case 10:
                message = this.who.showPositivePeopleInDistrict(Integer.parseInt(this.view.getDistrict()),
                        LocalDate.parse(this.view.getTestDate().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        Integer.parseInt(this.view.getTestDaysAfter()));
                break;
            case 11:
                message = this.who.showPositivePeopleInDistrictInOrder(Integer.parseInt(this.view.getDistrict()),
                        LocalDate.parse(this.view.getTestDate().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        Integer.parseInt(this.view.getTestDaysAfter()));
                break;
            case 12:
                message = this.who.showPositivePeopleInRegion(Integer.parseInt(this.view.getRegion()),
                        LocalDate.parse(this.view.getTestDate().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        Integer.parseInt(this.view.getTestDaysAfter()));
                break;
            case 13:
                message = this.who.showPositivePeople(LocalDate.parse(this.view.getTestDate().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        Integer.parseInt(this.view.getTestDaysAfter()));
                break;
            case 14:
                message = this.who.showPositivePersonWithHighestValueFromDistricts(LocalDate.parse(this.view.getTestDate().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        Integer.parseInt(this.view.getTestDaysAfter()));
                break;
            case 15:
                message = this.who.showDistrictsByPositiveCount(LocalDate.parse(this.view.getTestDate().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        Integer.parseInt(this.view.getTestDaysAfter()));
                break;
            case 16:
                message = this.who.showRegionsByPositiveCount(LocalDate.parse(this.view.getTestDate().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        Integer.parseInt(this.view.getTestDaysAfter()));
                break;
            case 17:
                message = this.who.showAllTestsInWorkspace(LocalDate.parse(this.view.getTestDate().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        LocalDate.parse(this.view.getTestDateTo().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        Integer.parseInt(this.view.getWorkplace()));
                break;
            case 18:
                message = this.who.findTest(Integer.parseInt(this.view.getTestCode()));
                break;
            case 19:
                boolean correctAddPerson = this.who.addPerson(this.view.getPersonName(), this.view.getPersonSurname(),
                        LocalDate.parse(this.view.getPersonBirthday().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        this.view.getPersonID());
                message = correctAddPerson
                        ? "Successfully added person with id [" + this.view.getPersonID() + "]."
                        : "Unsuccessfully added - person with id [" + this.view.getPersonID() + "] is already in the system.";
                break;
            case 20:
                boolean correctDeletedTest = this.who.removeTest(Integer.parseInt(this.view.getTestCode()));
                message = correctDeletedTest
                        ? "Successfully deleted test [" +  this.view.getTestCode() + "]"
                        : "Unsuccessfully deleted test. [" +  this.view.getTestCode() + "]";
                break;
            case 21:
                boolean correctDeletedPerson = this.who.removePersonAndTests(this.view.getPersonID());
                message = correctDeletedPerson
                        ? "Successfully deleted person with id [" + this.view.getPersonID() + "] with all tests."
                        : "Unsuccessfully deleted person with id [" + this.view.getPersonID() + "] with tests.";
                break;
            default:
                this.view.getOutputArea().append("Something is probably wrong because there is no other button which can be clicked.");
        }
        // mozno clear input fields
        this.view.getOutputArea().setText(message);
    }

    private void handleGenerateButton() {
        this.generator.generatePeople(20);
        this.generator.generateTests(100);
    }

    private void handleSaveButton() {
        String message = this.who.saveDataToCSV();
        this.view.getOutputArea().setText(message);
    }

    private void handleLoadButton() {
        String message = this.who.loadDataFromCSV();
        this.view.getOutputArea().setText(message);
    }

    private void handleClearButton() {
        this.view.getOutputArea().setText("");
        this.view.clearInputFields();
    }
}
