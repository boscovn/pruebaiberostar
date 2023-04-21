package com.example.demo.repository;

import com.example.demo.model.Superheroe;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest

class SuperheroeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    SuperheroeRepository repository;

    @Test
    public void should_store_a_superheroe() {
        Superheroe superheroe = repository.save(new Superheroe("nn"));
        assertThat(superheroe).hasFieldOrPropertyWithValue("name", "nn");
    }

    @Test
    public void should_find_all_superheroes() {
        Superheroe sup1 = new Superheroe("Name#1");
        entityManager.persist(sup1);

        Superheroe sup2 = new Superheroe("Name#2");
        entityManager.persist(sup2);

        Superheroe sup3 = new Superheroe("Name#3");
        entityManager.persist(sup3);

        Iterable<Superheroe> superheroes = repository.findAll();

        assertThat(superheroes).hasSize(3).contains(sup1, sup2, sup3);
    }

    @Test
    public void should_find_superheroe_by_id() {
        Superheroe sup1 = new Superheroe( "Name#1");
        entityManager.persist(sup1);

        Superheroe sup2 = new Superheroe("Name#2");
        entityManager.persist(sup2);

        Superheroe foundSuperheroe = repository.findById(sup2.getId()).get();

        assertThat(foundSuperheroe).isEqualTo(sup2);
    }

    @Test
    public void should_find_superheroes_by_name_containing_string() {
        Superheroe sup1 = new Superheroe("Spiderman");
        entityManager.persist(sup1);

        Superheroe sup2 = new Superheroe("Calico Electronico");
        entityManager.persist(sup2);

        Superheroe sup3 = new Superheroe( "Batman");
        entityManager.persist(sup3);

        Iterable<Superheroe> superheroes = repository.findByNameContaining("man");

        assertThat(superheroes).hasSize(2).contains(sup1, sup3);
    }

    @Test
    public void should_update_superheroe_by_id() {
        Superheroe sup1 = new Superheroe("Name#1");
        entityManager.persist(sup1);

        Superheroe sup2 = new Superheroe("Name#2");
        entityManager.persist(sup2);

        Superheroe updatedTut = new Superheroe("updated Name#2");

        Superheroe tut = repository.findById(sup2.getId()).get();
        tut.setName(updatedTut.getName());
        repository.save(tut);

        Superheroe checkTut = repository.findById(sup2.getId()).get();

        assertThat(checkTut.getId()).isEqualTo(sup2.getId());
        assertThat(checkTut.getName()).isEqualTo(updatedTut.getName());
    }

    @Test
    public void should_delete_superheroe_by_id() {
        Superheroe sup1 = new Superheroe("Name#1");
        entityManager.persist(sup1);

        Superheroe sup2 = new Superheroe("Name#2");
        entityManager.persist(sup2);

        Superheroe sup3 = new Superheroe("Name#3");
        entityManager.persist(sup3);

        repository.deleteById(sup2.getId());

        Iterable<Superheroe> superheroes = repository.findAll();

        assertThat(superheroes).hasSize(2).contains(sup1, sup3);
    }
}