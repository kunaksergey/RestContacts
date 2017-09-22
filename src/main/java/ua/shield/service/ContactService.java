package ua.shield.service;

import ua.shield.entity.Contact;

import java.util.List;

/**
 * Created by sa on 22.09.17.
 */
public interface ContactService {
    List<Contact> findAll();
    List<Contact> findAllByFilter(String filterPattern) throws RuntimeException;
}
