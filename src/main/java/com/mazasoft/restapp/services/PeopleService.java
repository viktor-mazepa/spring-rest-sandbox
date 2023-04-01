package com.mazasoft.restapp.services;

import com.mazasoft.restapp.exceptions.PersonNotFoundException;
import com.mazasoft.restapp.models.Person;
import com.mazasoft.restapp.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public Collection<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findById(Integer id) {
        Optional<Person> person = peopleRepository.findById(id);
        return person.orElseThrow(PersonNotFoundException::new);
    }

    public Optional<Person> findByEmail(String email) {
        Collection<Person> people = peopleRepository.findByEmail(email);
        return people.stream().findAny();
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(enrichPerson(person));
    }

    @Transactional
    public void update(Integer id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(Integer id) {
        peopleRepository.deleteById(id);
    }

    private Person enrichPerson(Person person) {
        person.setCreatedAt(LocalDateTime.now());
        person.setUpdatedAt(LocalDateTime.now());
        person.setCreatedWho("rest.admin");
        return person;
    }
}
