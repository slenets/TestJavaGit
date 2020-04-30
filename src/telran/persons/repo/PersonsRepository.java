package telran.persons.repo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import telran.persons.dto.Person;
//@Repository // without works as well
public interface PersonsRepository extends MongoRepository<Person, Integer>{ // spring will create default bean based on MongoRepository interface
	
}
