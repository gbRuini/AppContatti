package com.mobdev.appcontatti.Model;

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


    public Contatto() {

    }

    public Contatto(String name, String surname, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }

    public Contatto(String name, String surname, String phoneNumber, String email) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Contatto(String name, String surname,String phoneNumber, String email, String address) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
      //  this.typeNumber = typeNumber;
        this.email = email;
        this.address = address;
       // this.imageContact = imageContact;
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
