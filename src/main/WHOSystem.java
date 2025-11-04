package main;

import data.*;
import structure.AVLTree;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Model, actual logic behind what are operations doing.
 */
public class WHOSystem {
    private AVLTree<Person> people;
    private AVLTree<TestByCode> testsByCode;
    private AVLTree<TestByDistrictDate> testsByDistrictDate;
    private AVLTree<TestByDistrictDate> positiveTestsByDistrictDate;
    private AVLTree<TestByRegionDate> testsByRegionDate;
    private AVLTree<TestByRegionDate> positiveTestsByRegionDate;
    private AVLTree<TestByDate> testsByDate;
    private AVLTree<TestByDate> positiveTestsByDate;
    private AVLTree<TestByWorkplaceDate> testsByWorkplace;

    private ArrayList<Region> regions;
    private ArrayList<District> districts;
    private ArrayList<Workplace> workplaces;

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

        this.regions = new ArrayList<>();
        this.districts = new ArrayList<>();
        this.workplaces = new ArrayList<>();
    }

    // 1. Vloženie výsledku PCR testu do systému.
    public boolean addTestResult(LocalDate timeOfTest, String personID, int testCode,
                                 int workplace, int region, int district,
                                 boolean testResult, double testValue, String note) {
        Person person = this.people.find(new Person("", "", null, personID));
        if (person == null) {
            return false;
        }
        PCRTest test = new PCRTest(timeOfTest, personID, testCode, workplace, region, district, testResult, testValue, note, person);
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
                System.out.println("Positive test was not inserted somewhere.");
                return false;
            }
        }
        if (!insertedByCode || !insertedByPDate || !insertedByPCode || !insertedByDistrictDate || !insertedByRegionDate || !insertedByDate || !insertedByWorkplaceDate) {
            System.out.println("Test was not inserted somewhere.");
            return false;
        }
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
        message += person.getPersonInfo() + "\n\nTest info:\n" + test.getTest().getTestInfo();
        return message;
    }

    //3. Výpis všetkých uskutočnených PCR testov pre daného pacienta (definovaný unikátnym číslom pacienta) usporiadaných podľa dátumu a času ich vykonania.
    public String showAllPersonTests(String personID) {
        Person person = this.people.find(new Person("", "", null, personID));
        if (person == null) {
            return "Person ID was not found.";
        }
        ArrayList<TestByDate> tests = person.getTestsByDate().inOrder();
        StringBuilder output = new StringBuilder();
        output.append("All tests for person with ID [").append(personID).append("]:\n\nPerson info:\n").append(person.getPersonInfo());
        output.append("\nNumber of tests: ").append(tests.size()).append("\n\nTests:");
        for (TestByDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //4. Výpis všetkých pozitívnych testov uskutočnených za zadané časové obdobie pre zadaný okres (definovaný kódom okresu).
    public String showPositiveTestsInDistrict(int district, LocalDate from, LocalDate to) {
        PCRTest dummyTestMin = new PCRTest(from, null, Integer.MIN_VALUE, 0, 0, district, true, 0.0, null, null);
        PCRTest dummyTestMax = new PCRTest(to, null, Integer.MAX_VALUE, 0, 0, district, true, 0.0, null, null);
        ArrayList<TestByDistrictDate> tests = this.positiveTestsByDistrictDate.findInterval(new TestByDistrictDate(dummyTestMin), new TestByDistrictDate(dummyTestMax));
        StringBuilder output = new StringBuilder();
        output.append("All positive tests in district [").append(district).append("] from ").append(from).append(" to ").append(to);
        output.append("\n\nNumber of tests: ").append(tests.size()).append("\n\nTests:");
        for (TestByDistrictDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getPerson().getPersonInfo());
            output.append("\nTest\n").append(t.getTest().getTestInfo());
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
        output.append("\n\nNumber of tests: ").append(tests.size()).append("\n\nTests:");
        for (TestByDistrictDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getPerson().getPersonInfo());
            output.append("\nTest\n").append(t.getTest().getTestInfo());
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
        output.append("\n\nNumber of tests: ").append(tests.size()).append("\n\nTests:");
        for (TestByRegionDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getPerson().getPersonInfo());
            output.append("\nTest\n").append(t.getTest().getTestInfo());
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
        output.append("\n\nNumber of tests: ").append(tests.size()).append("\n\nTests:");
        for (TestByRegionDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getPerson().getPersonInfo());
            output.append("\nTest\n").append(t.getTest().getTestInfo());
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
        output.append("\n\nNumber of tests: ").append(tests.size()).append("\n\nTests:");
        for (TestByDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getPerson().getPersonInfo());
            output.append("\nTest\n").append(t.getTest().getTestInfo());
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
        output.append("\n\nNumber of tests: ").append(tests.size()).append("\n\nTests:");
        for (TestByDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getPerson().getPersonInfo());
            output.append("\nTest\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //10. Výpis chorých osôb v okrese (definovaný kódom okresu) k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ).
    public String showPositivePeopleInDistrict(int district, LocalDate date, int daysAfter) {
        PCRTest dummyTestMin = new PCRTest((date.minusDays(daysAfter)), null, Integer.MIN_VALUE, 0, 0, district, true, 0.0, null, null);
        PCRTest dummyTestMax = new PCRTest(date, null, Integer.MAX_VALUE, 0, 0, district, true, 0.0, null, null);
        ArrayList<TestByDistrictDate> tests = this.positiveTestsByDistrictDate.findInterval(new TestByDistrictDate(dummyTestMin), new TestByDistrictDate(dummyTestMax));

        StringBuilder output = new StringBuilder();
        output.append("Positive people from district [").append(district).append("] to date ").append(date).append("\n(person is positive ").append(daysAfter).append(" days after positive test).");
        output.append("\n\nNumber of people: ").append(tests.size()).append("\n\nPeople:");
        for (TestByDistrictDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getPerson().getPersonInfo());
            output.append("\nTest\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //11. Výpis chorých osôb v okrese (definovaný kódom okresu) k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ), pričom choré osoby sú usporiadané podľa hodnoty testu.
    public String showPositivePeopleInDistrictInOrder(int district, LocalDate date, int daysAfter) {
        PCRTest dummyTestMin = new PCRTest((date.minusDays(daysAfter)), null, Integer.MIN_VALUE, 0, 0, district, true, 0.0, null, null);
        PCRTest dummyTestMax = new PCRTest(date, null, Integer.MAX_VALUE, 0, 0, district, true, 0.0, null, null);
        ArrayList<TestByDistrictDate> tests = this.positiveTestsByDistrictDate.findInterval(new TestByDistrictDate(dummyTestMin), new TestByDistrictDate(dummyTestMax));
        AVLTree<District> orderedDistrictsByValue = new AVLTree<>();

        for (TestByDistrictDate t : tests) {
            PCRTest current = t.getTest();
            District d = new District(district);
            d.setTest(current);
            d.setValue(current.getTestValue());
            orderedDistrictsByValue.insert(d);
        }

        StringBuilder output = new StringBuilder();
        output.append("Positive people from district [").append(district).append("] to date ").append(date).append(" ordered by test value.\n(person is positive ").append(daysAfter).append(" days after positive test).");
        output.append("\n\nNumber of people: ").append(tests.size()).append("\n\nPeople:");
        for (District d : orderedDistrictsByValue.inOrder()) {
            output.append("\n--------------------\n").append(d.getTest().getPerson().getPersonInfo());
            output.append("\nTest:\n").append(d.getTest().getTestInfo());
        }
        return output.toString();
    }

    //12. Výpis chorých osôb v kraji (definovaný kódom kraja) k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ).
    public String showPositivePeopleInRegion(int region, LocalDate date, int daysAfter) {
        PCRTest dummyTestMin = new PCRTest((date.minusDays(daysAfter)), null, Integer.MIN_VALUE, 0, region, 0, true, 0.0, null, null);
        PCRTest dummyTestMax = new PCRTest(date, null, Integer.MAX_VALUE, 0, region, 0, true, 0.0, null, null);
        ArrayList<TestByRegionDate> tests = this.positiveTestsByRegionDate.findInterval(new TestByRegionDate(dummyTestMin), new TestByRegionDate(dummyTestMax));

        StringBuilder output = new StringBuilder();
        output.append("Positive people from region [").append(region).append("] to date ").append(date).append("\n(person is positive ").append(daysAfter).append(" days after positive test).");
        output.append("\n\nNumber of people: ").append(tests.size()).append("\n\nPeople:");
        for (TestByRegionDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getPerson().getPersonInfo());
            output.append("\nTest\n").append(t.getTest().getTestInfo());
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
        output.append("\n\nNumber of people: ").append(tests.size()).append("\n\nPeople:");
        for (TestByDate t : tests) {
            output.append("\n--------------------\n").append(t.getTest().getPerson().getPersonInfo());
            output.append("\nTest\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //14. Výpis jednej chorej osoby k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ) z každého okresu, ktorá má najvyššiu hodnotu testu.
    public String showPositivePersonWithHighestValueFromDistricts(LocalDate date, int daysAfter) {
        PCRTest dummyTestMin, dummyTestMax;
        ArrayList<TestByDistrictDate> tests;
        AVLTree<District> positiveMaxValueDistrict = new AVLTree<>();
        for (District d : this.districts) {
            int code = d.getDistrictCode();
            dummyTestMin = new PCRTest((date.minusDays(daysAfter)), null, Integer.MIN_VALUE, 0, 0, code, true, 0.0, null, null);
            dummyTestMax = new PCRTest(date, null, Integer.MAX_VALUE, 0, 0, code, true, 0.0, null, null);
            tests = this.positiveTestsByDistrictDate.findInterval(new TestByDistrictDate(dummyTestMin), new TestByDistrictDate(dummyTestMax));
            double maxValue = 0.0;
            PCRTest maxTest = null;
            for (TestByDistrictDate t : tests) {
                PCRTest current = t.getTest();
                if (current != null && Double.compare(current.getTestValue(), maxValue) > 0) {
                    maxTest = current;
                    maxValue = current.getTestValue();
                }
            }
            District compDistrict = new District(code);
            compDistrict.setTest(maxTest);
            positiveMaxValueDistrict.insert(compDistrict);
        }
        StringBuilder output = new StringBuilder();
        output.append("People with the highest value in districts to date ").append(date).append("\n(person is positive ").append(daysAfter).append(" days after positive test).");
        output.append("\n\nDistrict code | person with highest value");
        for (District districtMaxValue : positiveMaxValueDistrict.inOrder()) {
            if (districtMaxValue.getTest() != null) {
                output.append("\n\nDistrict ").append(districtMaxValue.getDistrictCode()).append(":\n");
                output.append(districtMaxValue.getTest().getPerson().getPersonInfo()).append("\nTest\n").append(districtMaxValue.getTest().getTestInfo());
            } else {
                output.append("\n\nDistrict ").append(districtMaxValue.getDistrictCode()).append(" : None");
            }
        }
        return output.toString();
    }

    //15. Výpis okresov usporiadaných podľa počtu chorých osôb k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ).
    public String showDistrictsByPositiveCount(LocalDate date, int daysAfter) {
        PCRTest dummyTestMin, dummyTestMax;
        ArrayList<TestByDistrictDate> tests;
        AVLTree<District> positiveCountDistrict = new AVLTree<>();
        for (District d : this.districts) {
            int code = d.getDistrictCode();
            dummyTestMin = new PCRTest((date.minusDays(daysAfter)), null, Integer.MIN_VALUE, 0, 0, code, true, 0.0, null, null);
            dummyTestMax = new PCRTest(date, null, Integer.MAX_VALUE, 0, 0, code, true, 0.0, null, null);
            tests = this.positiveTestsByDistrictDate.findInterval(new TestByDistrictDate(dummyTestMin), new TestByDistrictDate(dummyTestMax));
            District compDistrict = new District(code);
            compDistrict.setCount(tests.size());
            positiveCountDistrict.insert(compDistrict);
        }
        StringBuilder output = new StringBuilder();
        output.append("Positive people in districts to date ").append(date).append("\n(person is positive ").append(daysAfter).append(" days after positive test).");
        output.append("\n\nDistrict code | number of positive people");
        for (District districtCount : positiveCountDistrict.inOrder()) {
            output.append("\nDistrict ").append(districtCount.getDistrictCode()).append(" : ").append(districtCount.getCount());
        }
        return output.toString();
    }

    //16. Výpis krajov usporiadaných podľa počtu chorých osôb k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ).
    public String showRegionsByPositiveCount(LocalDate date, int daysAfter) {
        PCRTest dummyTestMin, dummyTestMax;
        ArrayList<TestByRegionDate> tests;
        AVLTree<Region> positiveCountRegion = new AVLTree<>();
        for (Region d : this.regions) {
            int code = d.getRegionCode();
            dummyTestMin = new PCRTest((date.minusDays(daysAfter)), null, Integer.MIN_VALUE, 0, code, 0, true, 0.0, null, null);
            dummyTestMax = new PCRTest(date, null, Integer.MAX_VALUE, 0, code, 0, true, 0.0, null, null);
            tests = this.positiveTestsByRegionDate.findInterval(new TestByRegionDate(dummyTestMin), new TestByRegionDate(dummyTestMax));
            Region compRegion = new Region(code);
            compRegion.setCount(tests.size());
            positiveCountRegion.insert(compRegion);
        }
        StringBuilder output = new StringBuilder();
        output.append("Positive people in regions to date ").append(date).append("\n(person is positive ").append(daysAfter).append(" days after positive test).");
        output.append("\n\nRegion code | number of positive people");
        for (Region regionCount : positiveCountRegion.inOrder()) {
            output.append("\nRegion ").append(regionCount.getRegionCode()).append(" : ").append(regionCount.getCount());
        }
        return output.toString();
    }

    //17. Výpis všetkých testov uskutočnených za zadané časové obdobie na danom pracovisku (definované kódom pracoviska).
    public String showAllTestsInWorkspace(LocalDate from, LocalDate to, int workplace) {
        PCRTest dummyTestMin = new PCRTest(from, null, Integer.MIN_VALUE, workplace, 0, 0, false, 0.0, null, null);
        PCRTest dummyTestMax = new PCRTest(to, null, Integer.MAX_VALUE, workplace, 0, 0, false, 0.0, null, null);
        ArrayList<TestByWorkplaceDate> tests = this.testsByWorkplace.findInterval(new TestByWorkplaceDate(dummyTestMin), new TestByWorkplaceDate(dummyTestMax));
        StringBuilder output = new StringBuilder();
        output.append("All tests in workplace [").append(workplace).append("] from ").append(from).append(" to ").append(to);
        output.append("\n\nNumber of tests: ").append(tests.size()).append("\n\nTests:");
        for (TestByWorkplaceDate t : tests) {
            output.append("\n--------------------\n").append("Person\n").append(t.getTest().getPerson().getPersonInfo()).append("\nTest\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //18. Vyhľadanie PCR testu podľa jeho kódu.
    public String findTest(int testCode) {
        PCRTest dummyTest = new PCRTest(null, null, testCode, 0, 0, 0, false, 0.0, null, null);
        TestByCode test = this.testsByCode.find(new TestByCode(dummyTest));
        if (test == null) {
            return "Test code " + testCode + "was not found.";
        }
        return "Test info for test with code [" + testCode + "]:\nPerson\n" + test.getTest().getPerson().getPersonInfo() + "\nTest\n" + test.getTest().getTestInfo();
    }

    //19. Vloženie osoby do systému.
    public boolean addPerson(String name, String surname, LocalDate dateOfBirth, String personID) {
        Person person = new Person(name, surname, dateOfBirth, personID);
        boolean inserted = this.people.insert(person);
        if (!inserted) {
            System.out.println("Person with this ID is already in the system.");
            return false;
        }
        return true;
    }

    //20. Trvalé a nevratné vymazanie výsledku PCR testu (napr. po chybnom vložení), test je definovaných svojim kódom.
    public boolean removeTest(int testCode) {
        TestByCode findTest = this.testsByCode.find(new TestByCode(new PCRTest(null, null, testCode, 0, 0, 0, false, 0.0, null, null)));
        if (findTest == null) {
            System.out.println("Test with this test code was not found.");
            return false;
        }
        PCRTest removedTest = findTest.getTest();
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
    }

    //21. Vymazanie osoby zo systému (definovaná unikátnym číslom pacienta) aj s jej výsledkami PCR testov.
    public boolean removePersonAndTests(String personID) {
        Person person = this.people.find(new Person("","",null, personID));
        if (person == null) {
            System.out.println("Person with this ID was not found.");
            return false;
        }
        ArrayList<TestByCode> personsTest = person.getTestsByCode().levelOrder();
        for (int i = personsTest.size() - 1; i >= 0; --i) {
            if (!this.removeTest(personsTest.get(i).getTest().getTestCode())) {
                System.out.println("Deletion of some test was not successfull " + i);
                return false;
            }
        }
        return this.people.delete(person);
    }

    /**
     * Saves data (3 files) from system to csv.
     */
    public String saveDataToCSV() {
        ArrayList<Person> peopleToSave = this.people.levelOrder();
        ArrayList<TestByCode> testsToSave = this.testsByCode.levelOrder();
        String peopleFileName = "people.csv";
        String testsFileName = "tests.csv";
        String rdwFileName = "rdw.csv";

        try {
            PrintWriter peoplePrintWriter = new PrintWriter(new FileWriter(peopleFileName));
            PrintWriter testsPrintWriter = new PrintWriter(new FileWriter(testsFileName));
            PrintWriter rdwPrintWriter = new PrintWriter(new FileWriter(rdwFileName));

            // saving people to csv
            peoplePrintWriter.println("Name;Surname;Date of birth;person ID");
            for (Person person : peopleToSave) {
                peoplePrintWriter.println(person.getData());
            }
            peoplePrintWriter.close();

            // saving tests to csv
            testsPrintWriter.println("Date of test;Person ID;Test code;Workplace;Region;District;Test result;Test value;Note");
            for (TestByCode test : testsToSave) {
                testsPrintWriter.println(test.getTest().getData());
            }
            testsPrintWriter.close();

            // saving regions, districts and workplaces
            rdwPrintWriter.println("Codes for regions, districts, workplaces");
            StringBuilder codes = new StringBuilder();
            for (Region region : this.regions) {
                codes.append(region.getRegionCode()).append(";");
            }
            if (!codes.isEmpty()) {
                codes.setLength(codes.length() - 1);
            }
            rdwPrintWriter.println(codes);
            codes.setLength(0);
            for (District district : this.districts) {
                codes.append(district.getDistrictCode()).append(";");
            }
            if (!codes.isEmpty()) {
                codes.setLength(codes.length() - 1);
            }
            rdwPrintWriter.println(codes);
            codes.setLength(0);
            for (Workplace workplace : this.workplaces) {
                codes.append(workplace.getWorkplaceCode()).append(";");
            }
            if (!codes.isEmpty()) {
                codes.setLength(codes.length() - 1);
            }
            rdwPrintWriter.println(codes);
            rdwPrintWriter.close();

            return "People, tests, regions, districts and workplaces data saved successfully to " + peopleFileName + ", " + testsFileName + " and " + rdwFileName;
        } catch (IOException e) {
            return "Error when saving data: " + e.getMessage();
        }
    }

    /**
     * Load data (from 3 files) into the system and structures.
     */
    public String loadDataFromCSV() {
        String peopleFileName = "people.csv";
        String testsFileName = "tests.csv";
        String rdwFileName = "rdw.csv";
        try {
            BufferedReader peopleReader = new BufferedReader(new FileReader(peopleFileName));
            BufferedReader testsReader = new BufferedReader(new FileReader(testsFileName));
            BufferedReader rdwReader = new BufferedReader(new FileReader(rdwFileName));

            // loading people from people.csv
            String rowPerson;
            peopleReader.readLine();//header
            while ((rowPerson = peopleReader.readLine()) != null) {
                String[] data = rowPerson.split(";");
                if (data.length == 4) {
                    this.addPerson(data[0], data[1], LocalDate.parse(data[2]), data[3]);
                } else {
                    System.out.println("Incorrect person row: " + rowPerson);
                }
            }
            peopleReader.close();

            // loading tests from tests.csv
            String rowTest;
            testsReader.readLine();//header
            while ((rowTest = testsReader.readLine()) != null) {
                String[] data = rowTest.split(";");
                if (data.length == 9) {
                    this.addTestResult(LocalDate.parse(data[0]), data[1], Integer.parseInt(data[2]),
                            Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]),
                            Boolean.parseBoolean(data[6]), Double.parseDouble(data[7]), data[8]);
                } else {
                    System.out.println("Incorrect test row: " + rowTest);
                }
            }
            testsReader.close();

            //loading regions, districts, workplaces
            rdwReader.readLine();
            String[] regionCodes = rdwReader.readLine().split(";");
            for (String code : regionCodes) {
                if (!code.isEmpty()) {
                    this.regions.add(new Region(Integer.parseInt(code)));
                }
            }
            String[] districtCodes = rdwReader.readLine().split(";");
            for (String code : districtCodes) {
                if (!code.isEmpty()) {
                    this.districts.add(new District(Integer.parseInt(code)));
                }
            }
            String[] workplaceCodes = rdwReader.readLine().split(";");
            for (String code : workplaceCodes) {
                if (!code.isEmpty()) {
                    this.workplaces.add(new Workplace(Integer.parseInt(code)));
                }
            }
            rdwReader.close();

            return "People, tests, regions, districts and workplaces data load successfully from " + peopleFileName + ", " + testsFileName + " and " + rdwFileName;
        } catch (FileNotFoundException e) {
            return "File was not found: " + e.getMessage();
        } catch (IOException e) {
            return "Error when loading data: " + e.getMessage();
        }
    }

    public void addRegion(int code) {
        this.regions.add(new Region(code));
    }

    public void addDistrict(int code) {
        this.districts.add(new District(code));
    }

    public void addWorkplace(int code) {
        this.workplaces.add(new Workplace(code));
    }
}
