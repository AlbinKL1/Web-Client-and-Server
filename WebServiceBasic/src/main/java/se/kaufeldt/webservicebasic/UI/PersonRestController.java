package se.kaufeldt.webservicebasic.UI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.kaufeldt.webservicebasic.Database.Person;
import se.kaufeldt.webservicebasic.Database.PersonRepository;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonRestController {
    @Autowired
    private PersonRepository personRepository;

    @GetMapping
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable int id) {
        Person person = personRepository.findById(id).orElse(null);
        if (person != null) {
            return ResponseEntity.ok(person);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(params = "name")
    public List<Person> getPersonsByName(@RequestParam String name) {
        return personRepository.findByName(name);
    }

    @GetMapping(params = "age")
    public List<Person> getPersonsByAge(@RequestParam int age) {
        return personRepository.findByAge(age);
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable int id, @RequestBody Person personDetails) {
        Person existingPerson = personRepository.findById(id).orElse(null);
        if (existingPerson != null) {
            existingPerson.setName(personDetails.getName());
            existingPerson.setAge(personDetails.getAge());
            return ResponseEntity.ok(personRepository.save(existingPerson));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable int id) {
        Person existingPerson = personRepository.findById(id).orElse(null);
        if (existingPerson != null) {
            personRepository.delete(existingPerson);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
