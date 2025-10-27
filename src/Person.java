import structure.IBSTData;

import java.time.LocalDate;

public class Person implements IBSTData<Person> {
    // meno – reťazec
    // priezvisko – reťazec
    // dátum narodenia
    // unikátne číslo pacienta – reťazec
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String personID;
    private int comparator;//int/enum/string???

    public Person(int comparator) {
        this.comparator = comparator;
    }

    @Override
    public int compareTo(Person comparedData) {
        if (this.comparator == 0) { //podla mena
            if (this.name.length() < comparedData.getName().length()) {
                return -1;
            } else if (this.name.length() > comparedData.getName().length()) {
                return 1;
            }
        }
        return 0;
    }

    public String getName() {
        return this.name;
    }
}
