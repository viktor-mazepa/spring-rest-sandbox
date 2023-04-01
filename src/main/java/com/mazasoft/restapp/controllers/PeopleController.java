package com.mazasoft.restapp.controllers;

import com.mazasoft.restapp.dto.PersonDTO;
import com.mazasoft.restapp.exceptions.PersonCreationException;
import com.mazasoft.restapp.exceptions.PersonNotFoundException;
import com.mazasoft.restapp.models.Person;
import com.mazasoft.restapp.services.PeopleService;
import com.mazasoft.restapp.util.PersonErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    private final ModelMapper modelMapper;

    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public Collection<PersonDTO> getPeople() {
        return peopleService.findAll()
                .stream().map(this::convertToPersonDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable("id") int id) {
        return convertToPersonDTO(peopleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder message = new StringBuilder();

            Collection<FieldError> errors = bindingResult.getFieldErrors();
            errors.forEach(fieldError -> message.append(fieldError.getField()).append("-")
                    .append(fieldError.getDefaultMessage())
                    .append(";"));
            throw new PersonCreationException(message.toString());
        }

        peopleService.save(convertToPerson(personDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handlePersonNotFoundException(PersonNotFoundException exception) {
        PersonErrorResponse response = new PersonErrorResponse("Person with id was not found!", new Date());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleCreationException(PersonCreationException exception) {
        PersonErrorResponse response = new PersonErrorResponse(exception.getMessage(), new Date());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    private PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

}
