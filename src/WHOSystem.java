import structure.AVLTree;

import java.time.LocalDate;
import java.util.ArrayList;

public class WHOSystem {
    // atributy TODO
    private AVLTree<Person> people;//2,3,19,21,10-14
    private AVLTree<TestByCode> testsByCode;//18,20
    private AVLTree<TestByDistrictDate> testsByDistrictDate;//5
    private AVLTree<TestByDistrictDate> positiveTestsByDistrictDate;//4,10,11(nova?)
    private AVLTree<TestsByRegionDate> testsByRegionDate;//7
    private AVLTree<TestsByRegionDate> positiveTestsByRegionDate;//6,12
    private AVLTree<TestByDate> testsByDate;//9
    private AVLTree<TestByDate> positiveTestsByDate;//8,13


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
    }

    // 1. Vloženie výsledku PCR testu do systému.
    public boolean addTestResult(LocalDate timeOfTest, String personID, int testCode,
                                 int workplace, int region, int district,
                                 boolean testResult, double testValue, String note) {
        Person person = this.people.find(new Person("", "", null, personID));
        PCRTest test = new PCRTest(timeOfTest, personID, testCode, workplace, region, district, testResult, testValue, note);
        //TODO pridat vsade ale kde co ako, do pozitivnych len pozitivne
        boolean insertedByPCode = person.getTestsByCode().insert(new TestByCode(test));
        boolean insertedByPDate = person.getTestsByDate().insert(new TestByDate(test));
        boolean insertedByCode = this.testsByCode.insert(new TestByCode(test));
        boolean insertedByDistrictDate = this.testsByDistrictDate.insert(new TestByDistrictDate(test));
        boolean insertedByRegionDate = this.testsByRegionDate.insert(new TestsByRegionDate(test));
        boolean insertedByDate = this.testsByDate.insert(new TestByDate(test));
        if (testResult) {
            boolean insertedPosByDistrictDate = this.positiveTestsByDistrictDate.insert(new TestByDistrictDate(test));
            boolean insertedPosByRegionDate = this.positiveTestsByRegionDate.insert(new TestsByRegionDate(test));
            boolean insertedPosByDate = this.positiveTestsByDate.insert(new TestByDate(test));
            if (!insertedPosByDistrictDate || !insertedPosByRegionDate || !insertedPosByDate) {
                System.out.println("Niekde sa test nedokazal vlozit");
                return false;
            }
        }
        if (!insertedByCode || !insertedByPDate || !insertedByPCode || !insertedByDistrictDate || !insertedByRegionDate || !insertedByDate) {
            System.out.println("Niekde sa test nedokazal vlozit");
            return false;
        }
        return true;
    }

    //2. Vyhľadanie výsledku testu (definovaný kódom PCR testu) pre pacienta (definovaný unikátnym číslom pacienta) a zobrazenie všetkých údajov.TODO navratova hodnota boolean
    public String showTestResult(int testCode, String personID) {
        Person person = this.people.find(new Person("", "", null, personID));
        if (person == null) {
            System.out.println("Osoba s tymto id sa nenasla");
            return "Osoba s tymto id sa nenasla";
        }
        PCRTest dummyTest = new PCRTest(null, personID, testCode, 0, 0, 0, false, 0.0, null);
        TestByCode test = person.getTestsByCode().find(new TestByCode(dummyTest));
        if (test == null) {
            return "Test s tymto id sa nenasiel";
        }
        return test.getTest().getTestInfo();
    }

    //3. Výpis všetkých uskutočnených PCR testov pre daného pacienta (definovaný unikátnym číslom pacienta) usporiadaných podľa dátumu a času ich vykonania.
    public String showAllPersonTests(String personID) {
        Person person = this.people.find(new Person("", "", null, personID));
        if (person == null) {
            System.out.println("Osoba s tymto id sa nenasla");
            return "Osoba neexistuje.";
        }
        ArrayList<TestByDate> tests = person.getTestsByDate().inOrder();
        StringBuilder output = new StringBuilder();
        for (TestByDate t : tests) {
            output.append("\n====================\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //4. Výpis všetkých pozitívnych testov uskutočnených za zadané časové obdobie pre zadaný okres (definovaný kódom okresu).
    public String showPositiveTestsInDistrict(int district, LocalDate from, LocalDate to) {
        PCRTest dummyTestMin = new PCRTest(from, null, Integer.MIN_VALUE, 0, 0, district, true, 0.0, null);
        PCRTest dummyTestMax = new PCRTest(to, null, Integer.MAX_VALUE, 0, 0, district, true, 0.0, null);
        ArrayList<TestByDistrictDate> tests = this.positiveTestsByDistrictDate.findInterval(new TestByDistrictDate(dummyTestMin), new TestByDistrictDate(dummyTestMax));
        StringBuilder output = new StringBuilder();
        for (TestByDistrictDate t : tests) {
            output.append("\n====================\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //5. Výpis všetkých testov uskutočnených za zadané časové obdobie pre zadaný okres (definovaný kódom okresu).
    public String showAllTestsInDistrict(int district, LocalDate from, LocalDate to) {
        PCRTest dummyTestMin = new PCRTest(from, null, Integer.MIN_VALUE, 0, 0, district, true, 0.0, null);
        PCRTest dummyTestMax = new PCRTest(to, null, Integer.MAX_VALUE, 0, 0, district, true, 0.0, null);
        ArrayList<TestByDistrictDate> tests = this.testsByDistrictDate.findInterval(new TestByDistrictDate(dummyTestMin), new TestByDistrictDate(dummyTestMax));
        StringBuilder output = new StringBuilder();
        for (TestByDistrictDate t : tests) {
            output.append("\n====================\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //6. Výpis všetkých pozitívnych testov uskutočnených za zadané časové obdobie pre zadaný kraj (definovaný kódom kraja).
    public String showPositiveTestsInRegion(int region, LocalDate from, LocalDate to) {
        PCRTest dummyTestMin = new PCRTest(from, null, Integer.MIN_VALUE, 0, region, 0, true, 0.0, null);
        PCRTest dummyTestMax = new PCRTest(to, null, Integer.MAX_VALUE, 0, region, 0, true, 0.0, null);
        ArrayList<TestsByRegionDate> tests = this.positiveTestsByRegionDate.findInterval(new TestsByRegionDate(dummyTestMin), new TestsByRegionDate(dummyTestMax));
        StringBuilder output = new StringBuilder();
        for (TestsByRegionDate t : tests) {
            output.append("\n====================\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //7. Výpis všetkých testov uskutočnených za zadané časové obdobie pre zadaný kraj (definovaný kódom kraja).
    public String showAllTestsInRegion(int region, LocalDate from, LocalDate to) {
        PCRTest dummyTestMin = new PCRTest(from, null, Integer.MIN_VALUE, 0, region, 0, true, 0.0, null);
        PCRTest dummyTestMax = new PCRTest(to, null, Integer.MAX_VALUE, 0, region, 0, true, 0.0, null);
        ArrayList<TestsByRegionDate> tests = this.testsByRegionDate.findInterval(new TestsByRegionDate(dummyTestMin), new TestsByRegionDate(dummyTestMax));
        StringBuilder output = new StringBuilder();
        for (TestsByRegionDate t : tests) {
            output.append("\n====================\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //8. Výpis všetkých pozitívnych testov uskutočnených za zadané časové obdobie.
    public String showPositiveTests(LocalDate from, LocalDate to) {
        PCRTest dummyTestMin = new PCRTest(from, null, Integer.MIN_VALUE, 0, 0, 0, true, 0.0, null);
        PCRTest dummyTestMax = new PCRTest(to, null, Integer.MAX_VALUE, 0, 0, 0, true, 0.0, null);
        ArrayList<TestByDate> tests = this.positiveTestsByDate.findInterval(new TestByDate(dummyTestMin), new TestByDate(dummyTestMax));
        StringBuilder output = new StringBuilder();
        for (TestByDate t : tests) {
            output.append("\n====================\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //9. Výpis všetkých testov uskutočnených za zadané časové obdobie.
    public String showAllTests(LocalDate from, LocalDate to) {
        PCRTest dummyTestMin = new PCRTest(from, null, Integer.MIN_VALUE, 0, 0, 0, true, 0.0, null);
        PCRTest dummyTestMax = new PCRTest(to, null, Integer.MAX_VALUE, 0, 0, 0, true, 0.0, null);
        ArrayList<TestByDate> tests = this.testsByDate.findInterval(new TestByDate(dummyTestMin), new TestByDate(dummyTestMax));
        StringBuilder output = new StringBuilder();
        for (TestByDate t : tests) {
            output.append("\n====================\n").append(t.getTest().getTestInfo());
        }
        return output.toString();
    }

    //10. Výpis chorých osôb v okrese (definovaný kódom okresu) k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ).
    public String showPositivePeopleInDistrict(int district, LocalDate date, int daysAfter) {
//        this.positiveTestsByDistrictDate
        return null;
    }

    //11. Výpis chorých osôb v okrese (definovaný kódom okresu) k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ), pričom choré osoby sú usporiadané podľa hodnoty testu.
    public String showPositivePeopleInDistrictInOrder(int district, LocalDate date, int daysAfter) {
//        this.positiveTestsByDistrictDate
        return null;
    }

    //12. Výpis chorých osôb v kraji (definovaný kódom kraja) k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ).
    public String showPositivePeopleInRegion(int region, LocalDate date, int daysAfter) {
//        this.positiveTestsByRegionDate
        return null;
    }

    //13. Výpis chorých osôb k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ).
    public String showPositivePeople(LocalDate date, int daysAfter) {
//        this.positiveTestsByDate
        return null;
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
    // TODO
    public String showAllTestsInWorkspace(LocalDate date, int workspace) {
        return null;
    }

    //18. Vyhľadanie PCR testu podľa jeho kódu.
    public String findTest(int testCode) {
        PCRTest dummyTest = new PCRTest(null, null, testCode, 0, 0, 0, false, 0.0, null);
        TestByCode test = this.testsByCode.find(new TestByCode(dummyTest));
        if (test == null) {
            return "Test s takymto kodom sa nenasiel";
        }
        return test.getTest().getTestInfo();
    }

    //19. Vloženie osoby do systému.
    // TODO este predtym generovanie ID? treba?
    public boolean addPerson(String name, String surname, LocalDate dateOfBirth, String personID) {
        Person person = new Person(name, surname, dateOfBirth, personID);
        boolean inserted = this.people.insert(person);
        if (!inserted) {
            System.out.println("osoba sa tam uz ocividne nachadza, teda person id");
            return false;
        }
        return true;
    }

    //20. Trvalé a nevratné vymazanie výsledku PCR testu (napr. po chybnom vložení), test je definovaných svojim kódom.
    // TODO navratova hodnota, treba zistit ostatne parametre a vymazat odvsadial
    public boolean removeTest(int testCode) {
        TestByCode findTest = this.testsByCode.find(new TestByCode(new PCRTest(null, null, testCode, 0, 0, 0, false, 0.0, null)));
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
        boolean delFromRegionDate = this.testsByRegionDate.delete(new TestsByRegionDate(removedTest));
        boolean delFromDate = this.testsByDate.delete(new TestByDate(removedTest));
        if (removedTest.getTestResult()) {
            boolean delFromPosDistrictDate = this.positiveTestsByDistrictDate.delete(new TestByDistrictDate(removedTest));
            boolean delFromPosRegionDate = this.positiveTestsByRegionDate.delete(new TestsByRegionDate(removedTest));
            boolean delFromPosDate = this.positiveTestsByDate.delete(new TestByDate(removedTest));
            if (!delFromPosDistrictDate || !delFromPosRegionDate || !delFromPosDate) {
                System.out.println("VELMI ZLE NIECO S MAZANIM");
                return false;
            }
        }
        return delFromByPCode && delFromByPDate && delFromCode && delFromDistrictDate && delFromRegionDate && delFromDate;
        // ostatne
//        return true;
    }

    //21. Vymazanie osoby zo systému (definovaná unikátnym číslom pacienta) aj s jej výsledkami PCR testov.
    // TODO ako mazat testy osob
    public boolean removePersonAndTests(String personID) {
        Person person = this.people.find(new Person("","",null, personID));
        if (person != null) {
//            person.getPersonsTests().delete(person.getPersonsTests().getRoot().getData());
        }
        return this.people.delete(person);
    }
}
