package se.kaufeldt.webclientbasic.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class PersonService {
    private final WebClient webClient;

    public PersonService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public List<Person> getAllPersons() {
        return webClient.get()
                .uri("/persons")
                .retrieve()
                .bodyToFlux(Person.class)
                .collectList()
                .block();
    }

    public Person getPersonById(int id) {
        return webClient.get()
                .uri("/persons/{id}", id)
                .retrieve()
                .bodyToMono(Person.class)
                .block();
    }

    public List<Person> getPersonsByName(String name) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/persons").queryParam("name", name).build())
                .retrieve()
                .bodyToFlux(Person.class)
                .collectList()
                .block();
    }

    public List<Person> getPersonsByAge(int age) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/persons").queryParam("age", age).build())
                .retrieve()
                .bodyToFlux(Person.class)
                .collectList()
                .block();
    }

    public Person createPerson(Person person) {
        return webClient.post()
                .uri("/persons")
                .body(Mono.just(person), Person.class)
                .retrieve()
                .bodyToMono(Person.class)
                .block();
    }

    public Person updatePerson(int id, Person person) {
        return webClient.put()
                .uri("/persons/{id}", id)
                .body(Mono.just(person), Person.class)
                .retrieve()
                .bodyToMono(Person.class)
                .block();
    }

    public void deletePerson(int id) {
        webClient.delete()
                .uri("/persons/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
