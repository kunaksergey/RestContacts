package ua.shield.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.shield.entity.Contact;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sa on 19.09.17.
 */
@Service
public class ContactDaoImpl implements ContactDao {
    private static final String strSql = "select *from contacts";
    private static final int fetchSize = 10000;
    private static List<Contact> cashList;

    private DataSource dataSource;

    @Autowired
    public ContactDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<Contact> findAllByFilter(String filterPattern) throws SQLException {
        List<Contact> contactsList = new LinkedList<>();
        List<Contact> cashInner = new ArrayList<>();
        if (cashList == null) {
            Connection connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            Statement statement = connection.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
                    java.sql.ResultSet.CONCUR_READ_ONLY);
            statement.setFetchSize(fetchSize);
            ResultSet resultSet = statement.executeQuery(strSql);
            while (resultSet.next()) {
                cashInner.add(new Contact(resultSet.getInt("id"), resultSet.getString("name")));
                if (!resultSet.getString("name").matches(filterPattern))
                    contactsList.add(new Contact(resultSet.getInt("id"), resultSet.getString("name")));
            }
            setCashList(cashInner);
            resultSet.close();
            statement.close();
            connection.close();
        } else {
            contactsList = cashList.parallelStream().filter(e -> !e.getName().matches(filterPattern)).collect(Collectors.toList());
        }
        return contactsList;
    }

    private static void setCashList(List<Contact> cash) {
        synchronized (ContactDaoImpl.class) {
            if (cashList == null)
                ContactDaoImpl.cashList = cash;
        }
    }
}
