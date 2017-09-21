package ua.shield.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.shield.entity.Contact;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sa on 19.09.17.
 */
@Service
public class ContactDaoImpl implements ContactDao {
    private static final String strSql = "select *from contacts";
    private static final int fetchSize = 10000;
    private DataSource dataSource;

    @Autowired
    public ContactDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    private void init() {
        updateCashe();
    }

    @Override
    public List<Contact> findAllByFilter(String filterPattern) {
        return Cashe.getInstance().getCashList().parallelStream().filter(e -> !e.getName().matches(filterPattern)).collect(Collectors.toList());
    }


    private List<Contact> findAll() {
        List<Contact> contactsList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
                    ResultSet.CONCUR_READ_ONLY);
            statement.setFetchSize(fetchSize);
            ResultSet resultSet = statement.executeQuery(strSql);
            while (resultSet.next()) {
                contactsList.add(new Contact(resultSet.getInt("id"), resultSet.getString("name")));
            }
            return contactsList;
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return contactsList;
    }

    @Scheduled(fixedDelay = 60000,initialDelay = 60000)
    private void updateCashe() {
        Cashe.getInstance().setCashList(findAll());
        System.out.println("Cache update");
    }
}
