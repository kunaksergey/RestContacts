package ua.shield.service;

import ua.shield.entity.Contact;

import java.util.List;

/**
 * Created by sa on 20.09.17.
 */
public class Cashe {
    private static Cashe cache;
    private List<Contact> cashList;
    public static Cashe getInstance(){
        if(cache==null)
            cache=new Cashe();
        return cache;
    }

    public List<Contact> getCashList() {
        return cashList;
    }

    public void setCashList(List<Contact> cashList) {
        this.cashList = cashList;
    }
}
