package ru.example.project2LibraryBoot.util;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.example.project2LibraryBoot.models.Person;
import ru.example.project2LibraryBoot.services.PersonService;

@Component
public class PersonValidator implements Validator {
    private final PersonService personService;
    @Autowired
    public PersonValidator(PersonService personService) {

        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (personService.show(person.getFullName()).isPresent()){
            errors.rejectValue("fullName", "", "Человек с таким ФИО уже есть в базе");
        }
    }
}
