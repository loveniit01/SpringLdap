package com.example.demo.model;

import org.springframework.ldap.odm.annotations.*;
import lombok.Data;

import javax.naming.Name;

@Data
@Entry(objectClasses = { "person", "top" })
public final class Person {

//    private static final String BASE_DN = Constant.BASE_DN;

    @Id
    private Name dn;

    @DnAttribute(value="uid")
    private String uid;

    @Attribute(name="cn")
    private String fullName;

    @Attribute(name="sn")
    private String lastName;

    @Attribute(name="manager")
    private String manager;
    
    @DnAttribute(value="ou")
    @Transient
    private String group;
    
    private String description;
}
