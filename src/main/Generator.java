package main;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Random;

//odniekial sa bude volat
public class Generator {
    private Faker faker;
    private Random random;
    private WHOSystem who;
    private ArrayList<String> peopleIDs;

    public Generator(WHOSystem who) {//mozno pocet vsetkych veci?
        this.faker = new Faker();
        this.random = new Random();
        this.who = who;
        this.peopleIDs = new ArrayList<>();
    }

    public void generatePeople(int count) {
        int i = 0;
        String name, surname, personID;
        LocalDate date;
        while (i != count) {
            name = this.faker.name().firstName();
            surname = this.faker.name().lastName();
            date = this.faker.date().birthday(5, 100).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            personID = this.faker.lorem().characters(6, true, true);//TODO unikatne, pomocny zoznam?
            if (this.who.addPerson(name, surname, date, personID)) {
                ++i;
                this.peopleIDs.add(personID);
            }
        }
    }

    public void generateTests(int count) {
        LocalDate timeOfTest;
        String personID;
        int testCode, workplace, region, district;
        boolean testResult;
        double testValue;
        String note;
        int i = 0;
        while (i != count) {
            timeOfTest = this.faker.date().birthday(0, 5).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            personID = this.peopleIDs.get(this.faker.number().numberBetween(0, this.peopleIDs.size()));
            testCode = this.faker.number().numberBetween(1, Integer.MAX_VALUE);//TODO unique UPRAVIIIT
            workplace = this.faker.number().numberBetween(1, Integer.MAX_VALUE);//TODO unique
            district = this.faker.number().numberBetween(1, Integer.MAX_VALUE);//TODO in region
            region = this.faker.number().numberBetween(1, Integer.MAX_VALUE);//TODO
            testResult = this.faker.bool().bool();
            testValue = this.faker.number().randomDouble(2, 10, 50);
            note = this.faker.medical().symptoms();
            if (this.who.addTestResult(timeOfTest, personID, testCode, workplace, region, district, testResult, testValue, note)) {
                ++i;//TODO mozno pridat unikatne niekde
            }
        }
    }
}
