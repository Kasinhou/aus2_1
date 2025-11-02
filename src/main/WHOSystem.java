package main;

import structure.AVLTree;
import data.Person;
import data.PCRTest;
import data.TestByCode;
import data.TestByDistrictDate;
import data.TestByRegionDate;
import data.TestByDate;
import data.TestByWorkplaceDate;

import java.time.LocalDate;
import java.util.ArrayList;

public class WHOSystem {
    // atributy TODO
    private AVLTree<Person> people;//2,3,19,21,10-14
    private AVLTree<TestByCode> testsByCode;//18,20
    private AVLTree<TestByDistrictDate> testsByDistrictDate;//5
    private AVLTree<TestByDistrictDate> positiveTestsByDistrictDate;//4,10,11(nova?)
    private AVLTree<TestByRegionDate> testsByRegionDate;//7
    private AVLTree<TestByRegionDate> positiveTestsByRegionDate;//6,12
    private AVLTree<TestByDate> testsByDate;//9
    private AVLTree<TestByDate> positiveTestsByDate;//8,13
    private AVLTree<TestByWorkplaceDate> testsByWorkplace;


    //konstruktor (co do neho?)
    public WHOSystem() {
        this.people = new AVLTree<>();
        this.testsByCode = new AVLTree<>();
        this.testsByDistrictDate = new AVLTree<>();
        this.positiveTestsByDistrictDate = new AVLTree<>();
        this.testsByRegionDate = new AVLTree<>();
        this.positiveTestsByRegionDate = new AVLTree<>();
        this.testsByDate = new AVLTree<>();
        this.positiveTestsByDate = new AVLTree<>();
        this.testsByWorkplace = new AVLTree<>();
    }

    // 1. Vloženie výsledku PCR testu do systému.
    public boolean addTestResult(LocalDate timeOfTest, String personID, int testCode,
                                 int workplace, int region, int district,
                                 boolean testResult, double testValue, String note) {
        Person person = this.people.find(new Person("", "", null, personID));
        //TODO pridat osobu ako atribut
        PCRTest test = new PCRTest(timeOfTest, personID, testCode, workplace, region, district, testResult, testValue, note, person);
        //TODO pridat vsade ale kde co ako, do pozitivnych len pozitivne
        boolean insertedByPCode = person.getTestsByCode().insert(new TestByCode(test));
        boolean insertedByPDate = person.getTestsByDate().insert(new TestByDate(test));
        boolean insertedByCode = this.testsByCode.insert(new TestByCode(test));
        boolean insertedByDistrictDate = this.testsByDistrictDate.insert(new TestByDistrictDate(test));
        boolean insertedByRegionDate = this.testsByRegionDate.insert(new TestByRegionDate(test));
        boolean insertedByDate = this.testsByDate.insert(new TestByDate(test));
        boolean insertedByWorkplaceDate = this.testsByWorkplace.insert(new TestByWorkplaceDate(test));
        if (testResult) {
            boolean insertedPosByDistrictDate = this.positiveTestsByDistrictDate.insert(new TestByDistrictDate(test));
            boolean insertedPosByRegionDate = this.positiveTestsByRegionDate.insert(new TestByRegionDate(test));
            boolean insertedPosByDate = this.positiveTestsByDate.insert(new TestByDate(test));
            if (!insertedPosByDistrictDate || !insertedPosByRegionDate || !insertedPosByDate) {
                System.out.println("Niekde sa test nedokazal vlozit");
                return false;
            }
        }
        if (!insertedByCode || !insertedByPDate || !insertedByPCode || !insertedByDistrictDate || !insertedByRegionDate || !insertedByDate || !insertedByWorkplaceDate) {
            System.out.println("Niekde sa test nedokazal vlozit");
            return false;
        }
        System.out.println();
        System.out.println(test.getTestInfo());
        return true;
    }

    //2. Vyhľadanie výsledku testu (definovaný kódom PCR testu) pre pacienta (definovaný unikátnym číslom pacienta) a zobrazenie všetkých údajov.
    public String showTestResult(int testCode, String personID) {
        Person person = this.people.find(new Person("", "", null, personID));
        if (person == null) {
            System.out.println("Osoba s tymto id sa nenasla");
            return "Person ID was not found.";
        }
        PCRTest dummyTest = new PCRTest(null, personID, testCode, 0, 0, 0, false, 0.0, null, null);
        TestByCode test = person.getTestsByCode().find(new TestByCode(dummyTest));
        if (test == null) {
            return "Test code was not found.";
        }
        String message = "Test result with code [" + testCode + "] for person with ID [" + personID + "]:\n\nPerson info:\n";
        message += person.getPersonInfo() + "\nTest info:\n" + test.getTest().getTestInfo();
        return message;
    }

    //3. Výpis všetkých uskutočnených PCR testov pre daného pacienta (definovaný unikátnym číslom pacienta) usporiadaných podľa dátumu a času ich vykonania.
    public String showAllPersonTests(String personID) {
        Person person = this.people.find(new Person("", "", null, personID));
        if (person == null) {
            System.out.println("Osoba s tymto id sa nenasla");
            return "Person ID was not found.";
        }
        ArrayList<TestByDate> tests = person.getTestsByDate().inOrder();
        StringBuilder output = new StringBuilder();
        output.append("All tests for person with ID [").append(personID).append("]:\n\nPerson info:\n").append(person.getPersonInfo());
        output.append("\nNumber of tests: ").append(tests.size()).append("\n\nTests:\n");
        for (TestByDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //4. Výpis všetkých pozitívnych testov uskutočnených za zadané časové obdobie pre zadaný okres (definovaný kódom okresu). TODO zadane casove obdobie aj cas? aj pre ostatne
    public String showPositiveTestsInDistrict(int district, LocalDate from, LocalDate to) {
        PCRTest dummyTestMin = new PCRTest(from, null, Integer.MIN_VALUE, 0, 0, district, true, 0.0, null, null);
        PCRTest dummyTestMax = new PCRTest(to, null, Integer.MAX_VALUE, 0, 0, district, true, 0.0, null, null);
        ArrayList<TestByDistrictDate> tests = this.positiveTestsByDistrictDate.findInterval(new TestByDistrictDate(dummyTestMin), new TestByDistrictDate(dummyTestMax));
        StringBuilder output = new StringBuilder();
        output.append("All positive tests in district [").append(district).append("] from ").append(from).append(" to ").append(to);
        output.append("\n\nNumber of tests: ").append(tests.size()).append("\n\nTests:\n");
        for (TestByDistrictDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //5. Výpis všetkých testov uskutočnených za zadané časové obdobie pre zadaný okres (definovaný kódom okresu).
    public String showAllTestsInDistrict(int district, LocalDate from, LocalDate to) {
        PCRTest dummyTestMin = new PCRTest(from, null, Integer.MIN_VALUE, 0, 0, district, true, 0.0, null, null);
        PCRTest dummyTestMax = new PCRTest(to, null, Integer.MAX_VALUE, 0, 0, district, true, 0.0, null, null);
        ArrayList<TestByDistrictDate> tests = this.testsByDistrictDate.findInterval(new TestByDistrictDate(dummyTestMin), new TestByDistrictDate(dummyTestMax));
        StringBuilder output = new StringBuilder();
        output.append("All tests in district [").append(district).append("] from ").append(from).append(" to ").append(to);
        output.append("\n\nNumber of tests: ").append(tests.size()).append("\n\nTests:\n");
        for (TestByDistrictDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //6. Výpis všetkých pozitívnych testov uskutočnených za zadané časové obdobie pre zadaný kraj (definovaný kódom kraja).
    public String showPositiveTestsInRegion(int region, LocalDate from, LocalDate to) {
        PCRTest dummyTestMin = new PCRTest(from, null, Integer.MIN_VALUE, 0, region, 0, true, 0.0, null, null);
        PCRTest dummyTestMax = new PCRTest(to, null, Integer.MAX_VALUE, 0, region, 0, true, 0.0, null, null);
        ArrayList<TestByRegionDate> tests = this.positiveTestsByRegionDate.findInterval(new TestByRegionDate(dummyTestMin), new TestByRegionDate(dummyTestMax));
        StringBuilder output = new StringBuilder();
        output.append("All positive tests in region [").append(region).append("] from ").append(from).append(" to ").append(to);
        output.append("\n\nNumber of tests: ").append(tests.size()).append("\n\nTests:\n");
        for (TestByRegionDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //7. Výpis všetkých testov uskutočnených za zadané časové obdobie pre zadaný kraj (definovaný kódom kraja).
    public String showAllTestsInRegion(int region, LocalDate from, LocalDate to) {
        PCRTest dummyTestMin = new PCRTest(from, null, Integer.MIN_VALUE, 0, region, 0, true, 0.0, null, null);
        PCRTest dummyTestMax = new PCRTest(to, null, Integer.MAX_VALUE, 0, region, 0, true, 0.0, null, null);
        ArrayList<TestByRegionDate> tests = this.testsByRegionDate.findInterval(new TestByRegionDate(dummyTestMin), new TestByRegionDate(dummyTestMax));
        StringBuilder output = new StringBuilder();
        output.append("All tests in region [").append(region).append("] from ").append(from).append(" to ").append(to);
        output.append("\n\nNumber of tests: ").append(tests.size()).append("\n\nTests:\n");
        for (TestByRegionDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //8. Výpis všetkých pozitívnych testov uskutočnených za zadané časové obdobie.
    public String showPositiveTests(LocalDate from, LocalDate to) {
        PCRTest dummyTestMin = new PCRTest(from, null, Integer.MIN_VALUE, 0, 0, 0, true, 0.0, null, null);
        PCRTest dummyTestMax = new PCRTest(to, null, Integer.MAX_VALUE, 0, 0, 0, true, 0.0, null, null);
        ArrayList<TestByDate> tests = this.positiveTestsByDate.findInterval(new TestByDate(dummyTestMin), new TestByDate(dummyTestMax));
        StringBuilder output = new StringBuilder();
        output.append("All positive tests from ").append(from).append(" to ").append(to);
        output.append("\n\nNumber of tests: ").append(tests.size()).append("\n\nTests:\n");
        for (TestByDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //9. Výpis všetkých testov uskutočnených za zadané časové obdobie.
    public String showAllTests(LocalDate from, LocalDate to) {
        PCRTest dummyTestMin = new PCRTest(from, null, Integer.MIN_VALUE, 0, 0, 0, true, 0.0, null, null);
        PCRTest dummyTestMax = new PCRTest(to, null, Integer.MAX_VALUE, 0, 0, 0, true, 0.0, null, null);
        ArrayList<TestByDate> tests = this.testsByDate.findInterval(new TestByDate(dummyTestMin), new TestByDate(dummyTestMax));
        StringBuilder output = new StringBuilder();
        output.append("All tests from ").append(from).append(" to ").append(to);
        output.append("\n\nNumber of tests: ").append(tests.size()).append("\n\nTests:\n");
        for (TestByDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //10. Výpis chorých osôb v okrese (definovaný kódom okresu) k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ).
    // TODO DOROBIT OSOBU???
    public String showPositivePeopleInDistrict(int district, LocalDate date, int daysAfter) {
        PCRTest dummyTestMin = new PCRTest((date.minusDays(daysAfter)), null, Integer.MIN_VALUE, 0, 0, district, true, 0.0, null, null);
        PCRTest dummyTestMax = new PCRTest(date, null, Integer.MAX_VALUE, 0, 0, district, true, 0.0, null, null);
        ArrayList<TestByDistrictDate> tests = this.positiveTestsByDistrictDate.findInterval(new TestByDistrictDate(dummyTestMin), new TestByDistrictDate(dummyTestMax));

        StringBuilder output = new StringBuilder();
        output.append("Positive people from district [").append(district).append("] to date ").append(date).append("\n(person is positive ").append(daysAfter).append(" days after positive test).");
        output.append("\n\nNumber of people: ").append(tests.size()).append("\n\nPeople:\n");
        for (TestByDistrictDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getPerson().getPersonInfo());//TODO takto ???
        }
        return output.toString();
    }

    //11. Výpis chorých osôb v okrese (definovaný kódom okresu) k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ), pričom choré osoby sú usporiadané podľa hodnoty testu.
    public String showPositivePeopleInDistrictInOrder(int district, LocalDate date, int daysAfter) {
        return this.showPositivePeopleInDistrict(district, date, daysAfter);
    }

    //12. Výpis chorých osôb v kraji (definovaný kódom kraja) k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ).
    public String showPositivePeopleInRegion(int region, LocalDate date, int daysAfter) {
        PCRTest dummyTestMin = new PCRTest((date.minusDays(daysAfter)), null, Integer.MIN_VALUE, 0, region, 0, true, 0.0, null, null);
        PCRTest dummyTestMax = new PCRTest(date, null, Integer.MAX_VALUE, 0, region, 0, true, 0.0, null, null);
        ArrayList<TestByRegionDate> tests = this.positiveTestsByRegionDate.findInterval(new TestByRegionDate(dummyTestMin), new TestByRegionDate(dummyTestMax));

        StringBuilder output = new StringBuilder();
        output.append("Positive people from region [").append(region).append("] to date ").append(date).append("\n(person is positive ").append(daysAfter).append(" days after positive test).");
        output.append("\n\nNumber of people: ").append(tests.size()).append("\n\nPeople:\n");
        for (TestByRegionDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getPerson().getPersonInfo());
        }
        return output.toString();
    }

    //13. Výpis chorých osôb k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ).
    public String showPositivePeople(LocalDate date, int daysAfter) {
        PCRTest dummyTestMin = new PCRTest((date.minusDays(daysAfter)), null, Integer.MIN_VALUE, 0, 0, 0, true, 0.0, null, null);
        PCRTest dummyTestMax = new PCRTest(date, null, Integer.MAX_VALUE, 0, 0, 0, true, 0.0, null, null);
        ArrayList<TestByDate> tests = this.positiveTestsByDate.findInterval(new TestByDate(dummyTestMin), new TestByDate(dummyTestMax));

        StringBuilder output = new StringBuilder();
        output.append("Positive people to date ").append(date).append("\n(person is positive ").append(daysAfter).append(" days after positive test).");
        output.append("\n\nNumber of people: ").append(tests.size()).append("\n\nPeople:\n");
        for (TestByDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getPerson().getPersonInfo());
        }
        return output.toString();
    }

    //14. Výpis jednej chorej osoby k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ) z každého okresu, ktorá má najvyššiu hodnotu testu.
    public String showPositivePersonWithHighestValueFromDistricts(LocalDate date, int daysAfter) {
        return null;
    }

    //15. Výpis okresov usporiadaných podľa počtu chorých osôb k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ).
    public String showDistrictsByPositiveCount(LocalDate date, int daysAfter) {
        return null;
    }

    //16. Výpis krajov usporiadaných podľa počtu chorých osôb k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ).
    // TODO
    public String showRegionsByPositiveCount(LocalDate date, int daysAfter) {
        return null;
    }

    //17. Výpis všetkých testov uskutočnených za zadané časové obdobie na danom pracovisku (definované kódom pracoviska).
    public String showAllTestsInWorkspace(LocalDate from, LocalDate to, int workplace) {
        PCRTest dummyTestMin = new PCRTest(from, null, Integer.MIN_VALUE, workplace, 0, 0, false, 0.0, null, null);
        PCRTest dummyTestMax = new PCRTest(to, null, Integer.MAX_VALUE, workplace, 0, 0, false, 0.0, null, null);
        ArrayList<TestByWorkplaceDate> tests = this.testsByWorkplace.findInterval(new TestByWorkplaceDate(dummyTestMin), new TestByWorkplaceDate(dummyTestMax));
        StringBuilder output = new StringBuilder();
        output.append("All tests in workplace [").append(workplace).append("] from ").append(from).append(" to ").append(to);
        output.append("\n\nNumber of tests: ").append(tests.size()).append("\n\nTests:\n");
        for (TestByWorkplaceDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //18. Vyhľadanie PCR testu podľa jeho kódu.
    public String findTest(int testCode) {
        PCRTest dummyTest = new PCRTest(null, null, testCode, 0, 0, 0, false, 0.0, null, null);
        TestByCode test = this.testsByCode.find(new TestByCode(dummyTest));
        if (test == null) {
            return "Test code was not found.";
        }
        return "Test info for test with code [" + testCode + "]:\n" + test.getTest().getTestInfo();
    }

    //19. Vloženie osoby do systému.
    public boolean addPerson(String name, String surname, LocalDate dateOfBirth, String personID) {
        Person person = new Person(name, surname, dateOfBirth, personID);
        boolean inserted = this.people.insert(person);
        if (!inserted) {
            System.out.println("Person with this ID is already in the system.");
            return false;
        }
        System.out.println(person.getPersonInfo());
        return true;
    }

    //20. Trvalé a nevratné vymazanie výsledku PCR testu (napr. po chybnom vložení), test je definovaných svojim kódom.
    // TODO navratova hodnota, treba zistit ostatne parametre a vymazat odvsadial
    public boolean removeTest(int testCode) {
        TestByCode findTest = this.testsByCode.find(new TestByCode(new PCRTest(null, null, testCode, 0, 0, 0, false, 0.0, null, null)));
        if (findTest == null) {
            System.out.println("Test s takymto kodom sa nenasiel");
            return false;
        }
        PCRTest removedTest = findTest.getTest();
        System.out.println(removedTest.getTestInfo());
        boolean delFromByPCode = this.people.find(new Person("", "", null, removedTest.getPersonID())).getTestsByCode().delete(new TestByCode(removedTest));
        boolean delFromByPDate = this.people.find(new Person("", "", null, removedTest.getPersonID())).getTestsByDate().delete(new TestByDate(removedTest));
        boolean delFromCode = this.testsByCode.delete(new TestByCode(removedTest));
        boolean delFromDistrictDate = this.testsByDistrictDate.delete(new TestByDistrictDate(removedTest));
        boolean delFromRegionDate = this.testsByRegionDate.delete(new TestByRegionDate(removedTest));
        boolean delFromDate = this.testsByDate.delete(new TestByDate(removedTest));
        boolean delFromWorkplaceDate = this.testsByWorkplace.delete(new TestByWorkplaceDate(removedTest));
        if (removedTest.getTestResult()) {
            boolean delFromPosDistrictDate = this.positiveTestsByDistrictDate.delete(new TestByDistrictDate(removedTest));
            boolean delFromPosRegionDate = this.positiveTestsByRegionDate.delete(new TestByRegionDate(removedTest));
            boolean delFromPosDate = this.positiveTestsByDate.delete(new TestByDate(removedTest));
            if (!delFromPosDistrictDate || !delFromPosRegionDate || !delFromPosDate) {
                System.out.println("Something is wrong with deletion of test.");
                return false;
            }
        }
        return delFromByPCode && delFromByPDate && delFromCode && delFromDistrictDate && delFromRegionDate && delFromDate && delFromWorkplaceDate;
        // ostatne
//        return true;
    }

    //21. Vymazanie osoby zo systému (definovaná unikátnym číslom pacienta) aj s jej výsledkami PCR testov.
    // TODO ako mazat testy osob, takto?
    public boolean removePersonAndTests(String personID) {
        Person person = this.people.find(new Person("","",null, personID));
        if (person == null) {
            System.out.println("Person with this ID was not found.");
            return false;
        }
        ArrayList<TestByCode> personsTest = person.getTestsByCode().levelOrder();
        for (int i = personsTest.size() - 1; i >= 0; --i) {
            if (!this.removeTest(personsTest.get(i).getTest().getTestCode())) {
                System.out.println("Mazanie nejakeho testu neprebehlo uspesne " + i);
                return false;
            }
        }
        return this.people.delete(person);
    }

    // TODO
    public String saveDataToCSV() {
        return "Not yet done";
    }

    // TODO aj definovat odkial?
    public String loadDataFromCSV() {
        return "Not yet done";
    }
}
