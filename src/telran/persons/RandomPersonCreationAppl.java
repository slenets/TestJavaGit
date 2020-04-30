package telran.persons;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.convert.support.ConfigurableConversionService;

import telran.persons.dto.Adress;
import telran.persons.dto.Child;
import telran.persons.dto.Employee;
import telran.persons.dto.Person;
import telran.persons.repo.PersonsRepository;
@SpringBootApplication
public class RandomPersonCreationAppl {
	private static final long N_PERSONS = 100;
	private static final int EMP_PROBABILITY = 30;
	private static final int MIN_CHILD_YEAR = 2014;
	private static final int MAX_CHILD_YEAR = 2020;
	private static final int N_GARTENS = 5;
	private static final int MIN_EMPL_YEAR = 1953;
	private static final int MAX_EMPL_YEAR = 2002;
	private static final int N_COMPANIES = 3;
	private static final int MIN_SALARY = 5500;
	private static final int MAX_SALARY = 40000;
	private static final int N_CITIES = 3;
	private static Random gen = new Random();

public static void main(String[] args) throws FileNotFoundException, IOException {
		List<Person> persons = getRandomPersons();
		ConfigurableApplicationContext ctx = 
				SpringApplication.run(RandomPersonCreationAppl.class, args);
		PersonsRepository repo = ctx.getBean(PersonsRepository.class);
		repo.saveAll(persons);
		
	}

	private static List<Person> getRandomPersons() {
		
		return Stream
				.generate(RandomPersonCreationAppl::getRandomPerson)
				.limit(N_PERSONS)
				.collect(Collectors.toList());
	}
	
	private static Person getRandomPerson()
	{
		Person person = getRandomCommonPerson();
		return getChance()<=EMP_PROBABILITY?
				getRandomEmpl(person):getRandomChild(person);
	}

	private static Person getRandomChild(Person person) {
		LocalDate birthDate = genRandomDate(MIN_CHILD_YEAR,MAX_CHILD_YEAR);
		String garten = "garten" + getRandomNumber(1, N_GARTENS);
		return new Child(person.getId(), person.getName(), 
				person.getAdress(), birthDate, garten);
	}

	private static LocalDate genRandomDate(int minYear, int maxYear) {
		int year = getRandomNumber(minYear, maxYear);
		int month = getRandomNumber(1, 12);
		int dayOfMonth = getRandomNumber(1, 28);
		return LocalDate.of(year, month, dayOfMonth);
	}

	private static Person getRandomEmpl(Person person) {
		LocalDate birthDate = genRandomDate(MIN_EMPL_YEAR,MAX_EMPL_YEAR);
		String company = "company"+getRandomNumber(1, N_COMPANIES);
		int salary = getRandomNumber(MIN_SALARY, MAX_SALARY);
		String title = getRandomTitle();
		return new Employee(person.getId(), person.getName(), 
				person.getAdress(), birthDate, company, salary, title);
	}

	private static String getRandomTitle() {
		String[] titles = {"WageEmployee","SalesPerson","SalesManager","Manager"};
		return titles[getRandomNumber(0, titles.length-1)];
	}

	private static int getChance() {
		
		return getRandomNumber(1, 100);
	}

	private static Person getRandomCommonPerson() {
		int id = getRandomNumber(1_000_000,9_999_999);
		String name = "name" + getRandomNumber(1,20);
		Adress adress = getRandomAdress();
		return new Person(id, name, adress, null);
	}

	private static Adress getRandomAdress() {
		String city = "city" + getRandomNumber(1, N_CITIES);
		String street = "street" + getRandomNumber(1, 20);
		int building = getRandomNumber(1, 30);
		int apt = getRandomNumber(1, 20);
		return new Adress(city, street, building, apt);
	}

	private static int getRandomNumber(int min, int max) {
		
		return gen.ints(1,min,max+1).findFirst().getAsInt();
	}
}
