package com.mazasoft.restapp.repositories;

import com.mazasoft.restapp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Collection<Person> findByEmail(String email);
}
