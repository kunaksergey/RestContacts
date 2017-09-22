package ua.shield.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ua.shield.entity.Contact;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sa on 22.09.17.
 */
@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Override
    @Cacheable("contacts")
    public List<Contact> findAll() {
        return (List<Contact>) contactRepository.findAll();
    }

    @Override
    public List<Contact> findAllByFilter(String filterPattern) {
        return findAll().parallelStream().filter(e -> !e.getName().matches(filterPattern)).collect(Collectors.toList());
    }
}



