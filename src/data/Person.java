package data;

import structure.AVLTree;
import structure.IBSTData;

import java.time.LocalDate;

/**
 * Class which represents data Person with its attributes and compare method to use in structures.
 */
public class Person implements IBSTData<Person> {
    private AVLTree<TestByCode> testsByCode;//1,2
    private AVLTree<TestByDate> testsByDate;//3,

    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String personID;

    public Person(String name, String surname, LocalDate dateOfBirth, String personID) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.personID = personID;
        this.testsByDate = new AVLTree<>();
        this.testsByCode = new AVLTree<>();
    }

    @Override
    public int compareTo(Person comparedData) {
        int result = this.personID.compareTo(comparedData.getPersonID());
        if (result < 0) {
            return -1;
        } else if (result > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    public String getPersonID() {
        return this.personID;
    }

    public AVLTree<TestByDate> getTestsByDate() {
        return this.testsByDate;
    }

    public AVLTree<TestByCode> getTestsByCode() {
        return this.testsByCode;
    }

    /**
     * Used for output for user.
     * @return information about person
     */
    public String getPersonInfo() {
        return "Patient name: " + this.name + " " + this.surname + "\nDate of birth: " + this.dateOfBirth + "\nPatient ID: " + this.personID;
    }

    /**
     * This method is used to convert data in order to save person to csv file.
     * @return attributes divided by ;
     */
    public String getData() {
        return String.join(";", this.name, this.surname, this.dateOfBirth.toString(), this.personID);
    }
}
