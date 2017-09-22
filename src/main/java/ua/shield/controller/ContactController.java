package ua.shield.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.shield.entity.Contact;
import ua.shield.service.ContactService;

import java.util.List;


/**
 * Created by sa on 18.09.17.
 */
@RestController
public class ContactController {

    private ContactService service;

    @Autowired
    public ContactController(ContactService service) {
        this.service = service;
    }

    @RequestMapping(value = "/hello/contacts")
    public ResponseEntity<List<Contact>> contactList(@RequestParam(value = "nameFilter") final String pattern) {
       try {
            List<Contact> allByFilter = service.findAllByFilter(pattern);
            if ((allByFilter.size() > 0)) {
                return new ResponseEntity<>(allByFilter, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
