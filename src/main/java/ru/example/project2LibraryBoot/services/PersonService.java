package ru.example.project2LibraryBoot.services;

import org.hibernate.Hibernate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.project2LibraryBoot.models.Book;
import ru.example.project2LibraryBoot.models.Person;
import ru.example.project2LibraryBoot.repositories.PersonRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public List<Person> index(){
        return personRepository.findAll();
    }
    public Person show(int id) {
        Optional<Person> foundPerson = personRepository.findById(id);

        return foundPerson.orElse(null);
    }
    public Optional<Person> show(String fullName){
         return personRepository.findPersonByFullName(fullName).stream().findAny();
    }
    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }
    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setPersonId(id);
        personRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(int id){
        personRepository.deleteById(id);
    }

    public List<Book> getBooksByPersonId(int personId) {
        Optional<Person> person = personRepository.findById(personId);
        if (person.isPresent()){
            Hibernate.initialize(person.get().getBookList());
            person.get().getBookList().forEach(book -> {
                long timeDifference = Math.abs(new Date().getTime() - book.getPassedTime().getTime());
                if (timeDifference > 864000000)
                    book.setExpired(true);
            });
            return person.get().getBookList();
        }
        else
            return Collections.emptyList();
    }

}
