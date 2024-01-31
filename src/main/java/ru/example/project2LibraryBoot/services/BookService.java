package ru.example.project2LibraryBoot.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.example.project2LibraryBoot.models.Book;
import ru.example.project2LibraryBoot.models.Person;
import ru.example.project2LibraryBoot.repositories.BookRepository;
import ru.example.project2LibraryBoot.repositories.PersonRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PersonRepository personRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PersonRepository personRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }



    public List<Book> index(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (sortByYear)
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("publicationYear"))).getContent();
        else
            return bookRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public List<Book> index(boolean sortByYear) {
        if (sortByYear)
            return bookRepository.findAll(Sort.by("publicationYear"));
        else
            return bookRepository.findAll();
    }

    public Book show(int id) {
        return bookRepository.findById(id).stream().findAny().orElse(null);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setBookId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }


    public Optional<Person> getBookOwner(int bookId) {
        Book book = bookRepository.findById(bookId).stream().findAny().orElse(null);
        Person person = book.getOwner();
        return Optional.ofNullable(person);
    }

    @Transactional
    public void release(int bookId) {
        Book book = bookRepository.findById(bookId).stream().findAny().orElse(null);
        book.setOwner(null);
        bookRepository.save(book);
    }

    @Transactional
    public void assign(int bookId, Person selectedPerson) {
        Book book = bookRepository.findById(bookId).stream().findAny().orElse(null);
        book.setOwner(selectedPerson);
        book.setPassedTime(new Date());
        bookRepository.save(book);
    }
    public List<Book> search(String bookName){
        return bookRepository.findByNameStartingWith(bookName);
    }


}
