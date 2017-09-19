package ua.shield.service;

import ua.shield.entity.Contact;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by sa on 19.09.17.
 */
public interface ContactDao {
    List<Contact> findAllByFilter(String filterPattern) throws SQLException;
}
