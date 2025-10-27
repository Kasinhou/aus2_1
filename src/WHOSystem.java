import structure.AVLTree;

import java.time.LocalDate;

public class WHOSystem {
    // atributy
    private AVLTree<Person> people = new AVLTree<>(1);
//    private AVLTree<PCRTest>

    //konstruktor (co do neho?)
    public WHOSystem() {
    }

    // 1. Vloženie výsledku PCR testu do systému.
    // TODO navratova hodnota, parameter boolean/PCR
    public void addTestResult() {

    }

    //2. Vyhľadanie výsledku testu (definovaný kódom PCR testu) pre pacienta (definovaný unikátnym číslom pacienta) a zobrazenie všetkých údajov.
    // TODO parametre?, zobrazenie cez navratovu hodnotu? String/PCRTest
    public boolean findTestResult(int testCode, String personID) {

        return false;
    }

    //3. Výpis všetkých uskutočnených PCR testov pre daného pacienta (definovaný unikátnym číslom pacienta) usporiadaných podľa dátumu a času ich vykonania.
    // TODO
    public void showAllTests(String personID) {

    }

    //4. Výpis všetkých pozitívnych testov uskutočnených za zadané časové obdobie pre zadaný okres (definovaný kódom okresu).
    // TODO casove obdobie? string/int
    public void showPositiveTestsInDistrict(int district, int from, int to) {

    }

    //5. Výpis všetkých testov uskutočnených za zadané časové obdobie pre zadaný okres (definovaný kódom okresu).
    // TODO casove obdobie? string/int
    public void showAllTestsInDistrict(int district, int from, int to) {

    }

    //6. Výpis všetkých pozitívnych testov uskutočnených za zadané časové obdobie pre zadaný kraj (definovaný kódom kraja).
    // TODO casove obdobie? string/int
    public void showPositiveTestsInRegion(int region, int from, int to) {

    }

    //7. Výpis všetkých testov uskutočnených za zadané časové obdobie pre zadaný kraj (definovaný kódom kraja).
    // TODO casove obdobie? string/int
    public void showAllTestsInRegion(int region, int from, int to) {

    }

    //8. Výpis všetkých pozitívnych testov uskutočnených za zadané časové obdobie.
    // TODO casove obdobie? string/int
    public void showPositiveTests(int from, int to) {

    }

    //9. Výpis všetkých testov uskutočnených za zadané časové obdobie.
    // TODO casove obdobie? string/int
    public void showAllTests(int from, int to) {

    }

    //10. Výpis chorých osôb v okrese (definovaný kódom okresu) k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ).
    // TODO parameters
    public void showPositivePeopleInDistrict(int district, LocalDate date, int daysAfter) {

    }

    //11. Výpis chorých osôb v okrese (definovaný kódom okresu) k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ), pričom choré osoby sú usporiadané podľa hodnoty testu.
    // TODO parameters
    public void showPositivePeopleInDistrictInOrder(int district, LocalDate date, int daysAfter) {

    }

    //12. Výpis chorých osôb v kraji (definovaný kódom kraja) k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ).
    // TODO parameters
    public void showPositivePeopleInRegion(int region, LocalDate date, int daysAfter) {

    }

    //13. Výpis chorých osôb k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ).
    // TODO parameters
    public void showPositivePeople(LocalDate date, int daysAfter) {

    }

    //14. Výpis jednej chorej osoby k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ) z každého okresu, ktorá má najvyššiu hodnotu testu.
    // TODO parameters
    public void showPositivePersonWithHighestValueFromDistricts(LocalDate date, int daysAfter) {

    }

    //15. Výpis okresov usporiadaných podľa počtu chorých osôb k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ).
    // TODO
    public void showDistrictsByPositiveCount(LocalDate date, int daysAfter) {

    }

    //16. Výpis krajov usporiadaných podľa počtu chorých osôb k zadanému dátumu, pričom osobu považujeme za chorú X dní od pozitívneho testu (X zadá užívateľ).
    // TODO
    public void showRegionsByPositiveCount(LocalDate date, int daysAfter) {

    }

    //17. Výpis všetkých testov uskutočnených za zadané časové obdobie na danom pracovisku (definované kódom pracoviska).
    // TODO
    public void showAllTestsInWorkspace(LocalDate date, int workspace) {

    }

    //18. Vyhľadanie PCR testu podľa jeho kódu.
    // TODO navratova hodnota, parameter?
    public void findTest(int testCode) {

    }

    //19. Vloženie osoby do systému.
    // TODO navratova hodnota
    public boolean addPerson(Person person) {
        return false;
    }

    //20. Trvalé a nevratné vymazanie výsledku PCR testu (napr. po chybnom vložení), test je definovaných svojim kódom.
    // TODO navratova hodnota, parameter
    public boolean removeTest(int testCode) {
        return false;
    }

    //21. Vymazanie osoby zo systému (definovaná unikátnym číslom pacienta) aj s jej výsledkami PCR testov.
    // TODO navratova hodnota
    public boolean removePersonAndTests(String personID) {
        return false;
    }
}
