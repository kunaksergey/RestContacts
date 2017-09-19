package ua.shield.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.shield.entity.Contact;
import ua.shield.service.ContactDao;

import java.sql.SQLException;
import java.util.List;


/**
 * Created by sa on 18.09.17.
 */
@RestController
public class ContactController {

    private ContactDao contactDao;

    @Autowired
    public ContactController(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    @RequestMapping("/hello/contacts")
    public ResponseEntity<List<Contact>> contactList(@RequestParam(value = "nameFilter") final String pattern) {
        List<Contact> allByFilter;
        try {
            allByFilter = contactDao.findAllByFilter(pattern);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if ((allByFilter.size() > 0)) {
            return new ResponseEntity<>(allByFilter, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
