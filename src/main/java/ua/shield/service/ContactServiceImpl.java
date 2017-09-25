package ua.shield.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.shield.entity.Contact;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by sa on 22.09.17.
 */
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Override
    public Iterable<Contact> findAll() {
        long start = System.currentTimeMillis();
        Iterable<Contact> contacts = contactRepository.findAll();
        System.out.println("Quary: "+(System.currentTimeMillis()-start));
        return contacts;
    }

    @Override
    public List<Contact> findAllByFilter(String filterPattern) {
        Pattern pattern=Pattern.compile(filterPattern);
        long start = System.currentTimeMillis();
        List<Contact> listContact= ((List<Contact>)findAll()).stream().filter(e -> !pattern.matcher(e.getName()).matches()).collect(Collectors.toList());
        System.out.println(System.currentTimeMillis()-start);
        return listContact;
    }
}



