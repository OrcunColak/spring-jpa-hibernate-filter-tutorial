package com.colak.springtutorial;

import com.colak.springtutorial.jpa.SampleEntity;
import com.colak.springtutorial.jpa.Translation;
import com.colak.springtutorial.repository.SampleEntityRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringTutorialApplicationTests {

    @Autowired
    private SampleEntityRepository repository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void populate() {
        SampleEntity entity = repository.save(new SampleEntity());
        assertThat(entity.getId()).isNotNull();

        Translation translationAr = new Translation();
        translationAr.setLocaleISOCode("ar");
        translationAr.setTranslation("مرحبًا");

        Translation translationFr = new Translation();
        translationFr.setLocaleISOCode("fr");
        translationFr.setTranslation("Bonjour");

        Translation translationEn = new Translation();
        translationEn.setLocaleISOCode("en");
        translationEn.setTranslation("Hello");

        List<Translation> translationList = entity.getTranslationList();
        translationList.add(translationAr);
        translationList.add(translationFr);
        translationList.add(translationEn);

        entity.setName("Sample");
        repository.saveAndFlush(entity);
    }

    @Test
    @Transactional
    void contextLoads() {
        runTest("en", "Hello");
        runTest("ar", "مرحبًا");
        runTest("fr", "Bonjour");
    }

    public void runTest(String lang, String translation) {
        entityManager.clear();
        LocaleContextHolder.setLocale(Locale.of(lang));

        SampleEntity entity = repository.getReferenceById(1L);
        List<Translation> translationList = entity.getTranslationList();

        assertThat(translationList).hasSize(1);
        assertThat(translationList.getFirst().getTranslation()).isEqualTo(translation);
    }

}
