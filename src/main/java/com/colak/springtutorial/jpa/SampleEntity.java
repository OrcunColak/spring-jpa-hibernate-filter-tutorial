package com.colak.springtutorial.jpa;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Sample")

@FilterDef(name = SampleEntity.FILTER_NAME, parameters = @ParamDef(name = SampleEntity.PARAM_NAME, type = String.class))

@Getter
@Setter
public class SampleEntity {

    public static final String FILTER_NAME = "localeFilter";
    public static final String PARAM_NAME = "currentLocaleISOCode";

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // Select Translation using the Hibernate filter
    @Filter(name = SampleEntity.FILTER_NAME, condition = "localeISOCode=:" + SampleEntity.PARAM_NAME)
    @OneToMany(cascade = CascadeType.ALL)
    private List<Translation> translationList = new ArrayList<>();

}