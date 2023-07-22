package org.leruk.spring.springmvcbooklibrary.util;

import org.leruk.spring.springmvcbooklibrary.dao.PersonDAO;
import org.leruk.spring.springmvcbooklibrary.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PersonDAO personDAO;

    @Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;

        if (personDAO.getPersonByFullName(person.getFullName()) != null)
            errors.rejectValue("fullName", "", "A person with that name already exists");
    }
}
