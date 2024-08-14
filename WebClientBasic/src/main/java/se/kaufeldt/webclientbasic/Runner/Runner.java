package se.kaufeldt.webclientbasic.Runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import se.kaufeldt.webclientbasic.Service.Person;
import se.kaufeldt.webclientbasic.Service.PersonService;

import java.util.List;
import java.util.Scanner;

@Configuration
public class Runner implements CommandLineRunner {
    @Autowired
    private PersonService personService;

    private Scanner input = new Scanner(System.in);

    @Override
    public void run(String[] args)  {
        boolean continueLoop = true;

        do {
            System.out.println("""
                                        
                    Meny
                                        
                    1. Get all persons.
                    2. Get person by ID.
                    3. Get persons by name.
                    4. Get persons by age.
                    5. Create person.
                    6. Update person.
                    7. Delete person.
                    8. Exit
                    """);
            System.out.print("Choose an option: ");
            int option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1 -> getAllPersons();
                case 2 -> getPersonById();
                case 3 -> getPersonByName();
                case 4 -> getPersonByAge();
                case 5 -> createPerson();
                case 6 -> updatePerson();
                case 7 -> deletePerson();
                case 8 -> continueLoop = false;
                default -> System.out.println("Invalid option, please try again.");
            }
        }while (continueLoop);
    }
    private void getAllPersons(){
        List<Person> persons = personService.getAllPersons();
        persons.forEach(System.out::println);
    }
    private void getPersonById(){
        System.out.print("Enter person ID: ");
        int id = input.nextInt();
        Person personById = personService.getPersonById(id);
        System.out.println(personById);
    }
    private void getPersonByName(){
        System.out.print("Enter name: ");
        String name = input.nextLine();
        List<Person> personsByName = personService.getPersonsByName(name);
        personsByName.forEach(System.out::println);
    }
    private void getPersonByAge(){
        System.out.print("Enter age: ");
        int age = input.nextInt();
        List<Person> personsByAge = personService.getPersonsByAge(age);
        personsByAge.forEach(System.out::println);
    }
    private void createPerson(){
        System.out.print("Enter name: ");
        String newName = input.nextLine();
        System.out.print("Enter age: ");
        int newAge = input.nextInt();
        Person newPerson = new Person();
        newPerson.setName(newName);
        newPerson.setAge(newAge);
        Person createdPerson = personService.createPerson(newPerson);
        System.out.println("Created: " + createdPerson);
    }
    private void updatePerson(){
        System.out.print("Enter person ID: ");
        int updateId = input.nextInt();
        input.nextLine();
        System.out.print("Enter new name: ");
        String updateName = input.nextLine();
        System.out.print("Enter new age: ");
        int updateAge = input.nextInt();
        Person updatePerson = new Person();
        updatePerson.setName(updateName);
        updatePerson.setAge(updateAge);
        Person updatedPerson = personService.updatePerson(updateId, updatePerson);
        System.out.println("Updated: " + updatedPerson);
    }
    private void deletePerson(){
        System.out.print("Enter person ID: ");
        int deleteId = input.nextInt();
        personService.deletePerson(deleteId);
        System.out.println("Deleted person with ID: " + deleteId);
    }
}
