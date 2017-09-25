package ua.shield.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.shield.entity.Contact;

/**
 * Created by sa on 22.09.17.
 */

public interface ContactRepository extends CrudRepository<Contact,Integer> {
}
