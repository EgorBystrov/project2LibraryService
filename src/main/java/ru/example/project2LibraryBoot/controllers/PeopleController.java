package ru.example.project2LibraryBoot.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.example.project2LibraryBoot.models.Person;
import ru.example.project2LibraryBoot.services.BookService;
import ru.example.project2LibraryBoot.services.PersonService;
import ru.example.project2LibraryBoot.util.PersonValidator;


@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonService personService;
    private final BookService bookService;
    private final PersonValidator personValidator;
    @Autowired
    public PeopleController(PersonService personService, BookService bookService, PersonValidator personValidator) {
        this.personService = personService;
        this.bookService = bookService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model){
        //Получим всех людей из DAO и передадим на отображение в представление
        model.addAttribute("people", personService.index());
        return "people/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        //Получим одного человека по id из DAO и передадим на отображение в представление
        model.addAttribute("person", personService.show(id));
        model.addAttribute("books", personService.getBooksByPersonId(id));
        return "people/show";
    }
    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "people/new";

        personService.save(person);
        return "redirect:/people";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("person", personService.show(id));
        return "people/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id){
        //personValidator.validate(person, bindingResult);
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors())
            return "people/edit";

        personService.update(id, person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personService.delete(id);
        return "redirect:/people";
    }


}
