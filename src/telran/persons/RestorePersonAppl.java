package telran.persons;
import java.io.IOException;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import telran.persons.dto.Person;
import telran.persons.repo.PersonsRepository;
@SpringBootApplication
public class RestorePersonAppl {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		ConfigurableApplicationContext ctx = SpringApplication.run(RestorePersonAppl.class);
		PersonsRepository repo = ctx.getBean(PersonsRepository.class);
		List<Person> persons = repo.findAll();
		persons.forEach(System.out::println);
	}

}
