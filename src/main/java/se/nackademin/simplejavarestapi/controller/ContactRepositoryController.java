package se.nackademin.simplejavarestapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.nackademin.simplejavarestapi.model.ContactRepository;
import se.nackademin.simplejavarestapi.model.Contact;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/contact")
public class ContactRepositoryController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/all")
    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }

    @GetMapping("/add/{firstName},{lastName},{email},{city},{street}")
    public List<Contact> addContact(
            @PathVariable String firstName, @PathVariable String lastName, @PathVariable String email,
            @PathVariable String city, @PathVariable String street) {
        Contact contact = new Contact(firstName, lastName, email, city, street);
        contactRepository.save(contact);
        return contactRepository.findAll();
    }

    @GetMapping("/firstName/{firstName}")
    public List<Contact> getByFirstName(@PathVariable String firstName) {
        return contactRepository.findAll().stream().filter(contact ->
                contact.getFirstName().toLowerCase()
                        .contains(firstName.toLowerCase())).collect(Collectors.toList());
    }

    @GetMapping("/lastName/{lastName}")
    public List<Contact> getByLastName(@PathVariable String lastName) {
        return contactRepository.findAll().stream().filter(contact ->
                contact.getLastName().toLowerCase()
                        .contains(lastName.toLowerCase())).collect(Collectors.toList());
    }

    @GetMapping("/email/{email}")
    public List<Contact> getByEmail(@PathVariable String email) {
        return contactRepository.findAll().stream().filter(contact ->
                contact.getEmail().toLowerCase()
                        .contains(email.toLowerCase())).collect(Collectors.toList());
    }

    @GetMapping("/city/{city}")
    public List<Contact> getByCity(@PathVariable String city) {
        return contactRepository.findAll().stream().filter(contact ->
                contact.getCity().toLowerCase()
                        .contains(city.toLowerCase())).collect(Collectors.toList());
    }

    @GetMapping("/street/{street}")
    public List<Contact> getByStreet(@PathVariable String street) {
        return contactRepository.findAll().stream().filter(contact ->
                contact.getStreet().toLowerCase()
                        .contains(street.toLowerCase())).collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public Contact getById(@PathVariable String id) {
        try {
            Long inputId = Long.parseLong(id);
            return contactRepository.findOne(inputId);
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/anyMatch/{query}")
    public List<Contact> getByAnyMatch(@PathVariable String query) {
        return contactRepository.findAll().stream().filter(contact ->
                contact.getFirstName().toLowerCase().contains(query.toLowerCase()) ||
                        contact.getLastName().toLowerCase().contains(query.toLowerCase()) ||
                        contact.getEmail().toLowerCase().contains(query.toLowerCase()) ||
                        contact.getCity().toLowerCase().contains(query.toLowerCase()) ||
                        contact.getStreet().toLowerCase().contains(query.toLowerCase()) ||
                        contact.getId().toString().contains(query)).collect(Collectors.toList());
    }
}
