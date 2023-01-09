package guru.springframework.jdbc;


import guru.springframework.jdbc.dao.AuthorDAO;
import guru.springframework.jdbc.domain.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("local")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(basePackages = {"guru.springframework.jdbc.dao"})
public class AuthorDaoMySQLIntegrationTest {

    @Autowired
    AuthorDAO authorDAO;

    @Test
    void testGetAuthorById(){
        Author author = authorDAO.getById(1L);

        assertThat(author).isNotNull();
    }
    @Test
    void testGetAuthorByName(){
        Author author = authorDAO.getByName("Craig", "Walls");

        assertThat(author).isNotNull();
    }

    @Test
    void testSaveNewAuthor(){
        Author author = new Author();
        author.setFirstName("Ken");
        author.setLastName("Mc22222");
        Author saved = authorDAO.saveNewAuthor(author);

        System.out.println("New Id is: " + saved.getId());

        assertThat(saved).isNotNull();
    }

    @Test
    void testUpdateNewAuthor(){
        Author author = new Author();
        author.setFirstName("Ken");
        author.setLastName("M");
        Author saved = authorDAO.saveNewAuthor(author);

        assertThat(saved).isNotNull();

        saved.setLastName("McKenzie");
        Author updated = authorDAO.updateAuthor(saved);

        assertThat(updated.getLastName()).isEqualTo("McKenzie");
    }

    @Test
    void testDeleteNetAuthor(){
        Author author = new Author();
        author.setFirstName("Ken");
        author.setLastName("M");
        Author saved = authorDAO.saveNewAuthor(author);

        assertThat(saved).isNotNull();

        authorDAO.deleteAuthorById(saved.getId());

        assertThrows(EmptyResultDataAccessException.class, () -> {
            authorDAO.getById(saved.getId());
        });

    }

}


