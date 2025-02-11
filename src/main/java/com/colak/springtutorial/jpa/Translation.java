package com.colak.springtutorial.jpa;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Translation")

@Getter
@Setter
public class Translation {

    @Id
    @GeneratedValue
    private Long id;

    private String localeISOCode;

    private String translation;
}