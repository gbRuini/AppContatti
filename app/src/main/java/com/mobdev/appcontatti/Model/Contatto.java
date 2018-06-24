package com.mobdev.appcontatti.Model;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by gabriele on 05/06/18.
 */

public class Contatto implements Serializable {

    private int id;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;
    private String typeNumber;
    private String email;
    private String imageContact;
    private String company;
    private String imageBusinessCard;


    public Contatto() {
        }

    public Contatto(String name, String surname, String phoneNumber, String typeNumber) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.typeNumber = typeNumber;
    }

    public Contatto(String name, String surname, String phoneNumber, String typeNumber, String email) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.typeNumber = typeNumber;
        this.email = email;
    }

    public Contatto(String name, String surname, String phoneNumber, String typeNumber, String email, String address, String imageContact) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.typeNumber = typeNumber;
        this.email = email;
        this.address = address;
        this.imageContact = imageContact;
    }

    public Contatto(String name, String surname,String phoneNumber, String typeNumber, String email, String address, String company, String imageContact, String imageBusinessCard) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.typeNumber = typeNumber;
        this.email = email;
        this.address = address;
        this.company = company;
        this.imageContact = imageContact;
        this.imageBusinessCard = imageBusinessCard;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() { return  address; }

    public void setAddress(String address) {this.address = address; }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTypeNumber() {
        return typeNumber;
    }

    public void setTypeNumber(String typeNumber) {
        this.typeNumber = typeNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageContact() {
        return imageContact;
    }

    public void setImageContact(String imageContact) {
        this.imageContact = imageContact;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getImageBusinessCard() {
        return imageBusinessCard;
    }

    public void setImageBusinessCard(String imageBusinessCard) {
        this.imageBusinessCard = imageBusinessCard;
    }

    @Override
    public String  toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", address=" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", typeNumber='" + typeNumber + '\'' +
                ", email='" + email + '\'' +
                ", imageContact='" + imageContact + '\'' +
                '}';
    }

}
