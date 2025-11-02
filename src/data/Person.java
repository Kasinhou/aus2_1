package data;

import structure.AVLTree;
import structure.IBSTData;

import java.time.LocalDate;

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
        int result = this.personID.compareTo(comparedData.getPersonID());//TODO ignore case?
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

    // TODO treba alebo iba insert cez getter?
    public void addTest(TestByDate test) {
        this.testsByDate.insert(test);
    }

    //TODO mazat vsetky testy postupne alebo priradit null hodnotu
    public AVLTree<TestByDate> getTestsByDate() {
        return this.testsByDate;
    }

    public AVLTree<TestByCode> getTestsByCode() {
        return this.testsByCode;
    }

    public String getPersonInfo() {
        return "Pacient name: " + this.name + " " + this.surname + "\nDate of birth: " + this.dateOfBirth + "\nPacient ID: " + this.personID;
    }
}
