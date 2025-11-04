package main;

import com.github.javafaker.Faker;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Generator {
    private static final int PEOPLE_COUNT = 500;
    private static final int TESTS_COUNT = 2000;
    private static final int REGION_COUNT = 10;
    private static final int DISTRICT_COUNT = 50;
    private static final int WORKPLACE_COUNT = 100;

    private Faker faker;
    private Random random;
    private WHOSystem who;
    private ArrayList<String> peopleIDs;
    private Set<Integer> testCodes;

    private int[] districtsRegion;
    private int[] workplacesDistrict;

    public Generator(WHOSystem who) {
        this.faker = new Faker();
        this.random = new Random();
        this.who = who;
        this.peopleIDs = new ArrayList<>();
        this.testCodes = new HashSet<>();
    }

    /**
     * Generate people and adding them to the system.
     */
    public void generatePeople() {
        int i = 0;
        String name, surname, personID;
        LocalDate date;
        while (i < PEOPLE_COUNT) {
            name = this.faker.name().firstName();
            surname = this.faker.name().lastName();
            date = this.faker.date().birthday(5, 100).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            personID = this.faker.lorem().characters(7, true, true);
            if (this.who.addPerson(name, surname, date, personID)) {
                ++i;
                this.peopleIDs.add(personID);
            }
        }
        System.out.println("People generated.");
    }

    /**
     * Generate tests and adding them to the system.
     */
    public void generateTests() {
        LocalDate timeOfTest;
        String personID, note;
        int testCode, workplace, region, district;
        boolean testResult;
        double testValue;
        int i = 0;
        while (i < TESTS_COUNT) {
            do {
                testCode = this.faker.number().numberBetween(1, Integer.MAX_VALUE);
            } while (!this.testCodes.add(testCode));
            timeOfTest = this.faker.date().birthday(0, 5).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            personID = this.peopleIDs.get(this.faker.number().numberBetween(0, this.peopleIDs.size()));
            workplace = this.random.nextInt(WORKPLACE_COUNT) + 1;
            district = this.workplacesDistrict[workplace];
            region = this.districtsRegion[district];
            testResult = this.faker.bool().bool();
            testValue = testResult ? this.faker.number().randomDouble(2, 15, 30) : this.faker.number().randomDouble(2, 25, 45);
            note = this.faker.medical().symptoms();
            if (this.who.addTestResult(timeOfTest, personID, testCode, workplace, region, district, testResult, testValue, note)) {
                ++i;
            } else {
                System.out.println("Pri generovani testu sa zavolala metoda na insertnutie testu ale nepresla");
            }
        }
        System.out.println("Tests generated.");
    }

    /**
     * Random connections to region-district-workplace
     */
    public void connectRegionDistrictWorkplace() {
        this.districtsRegion = new int[DISTRICT_COUNT + 1];
        this.workplacesDistrict = new int[WORKPLACE_COUNT + 1];

        for (int i = 1; i <= WORKPLACE_COUNT; ++i) {
            int randDistrict = this.random.nextInt(DISTRICT_COUNT) + 1;
            this.workplacesDistrict[i] = randDistrict;
            this.who.addWorkplace(i);
        }
        for (int i = 1; i <= DISTRICT_COUNT; ++i) {
            int randRegion = this.random.nextInt(REGION_COUNT) + 1;
            this.districtsRegion[i] = randRegion;
            this.who.addDistrict(i);
        }
        for (int i = 1; i <= REGION_COUNT; ++i) {
            this.who.addRegion(i);
        }
    }
}
