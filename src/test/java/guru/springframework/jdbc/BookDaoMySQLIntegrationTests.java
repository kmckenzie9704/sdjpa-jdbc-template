package guru.springframework.jdbc;

import guru.springframework.jdbc.dao.BookDAO;
import guru.springframework.jdbc.domain.Author;
import guru.springframework.jdbc.domain.Book;
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
public class BookDaoMySQLIntegrationTests {

    @Autowired
    BookDAO bookDAO;

    @Test
    void testGetBookById(){
        Book book = bookDAO.getById(1L);

        assertThat(book).isNotNull();
    }
    @Test
    void testGetBookByTitle(){
        Book book = bookDAO.getBookByTitle("Clean Code");

        assertThat(book).isNotNull();
    }

    @Test
    void testSaveNewBook(){
        Book book = new Book();
        book.setTitle("A New Book");
        book.setIsbn("1231552");
        book.setPublisher("Doubleday");
        Author author = new Author();
        author.setId(3L);

        book.setAuthorId(author.getId());
        Book saved = bookDAO.saveNewBook(book);

        assertThat(saved).isNotNull();
    }

    @Test
    void testUpdateBook(){
        Book book = new Book();
        book.setTitle("A");
        book.setIsbn("1231552");
        book.setPublisher("Doubleday");

        Author author = new Author();
        author.setId(3L);

        book.setAuthorId(author.getId());

        Book saved = bookDAO.saveNewBook(book);

        assertThat(saved).isNotNull();

        saved.setTitle("A New Book");
        Book updated = bookDAO.updateBook(saved);

        assertThat(updated.getTitle()).isEqualTo("A New Book");
    }

    @Test
    void testDeleteBook(){
        Book book = new Book();
        book.setTitle("A New Book");
        book.setIsbn("1231552");
        book.setPublisher("Doubleday");
        Book saved = bookDAO.saveNewBook(book);

        assertThat(saved).isNotNull();

        bookDAO.deleteBookById(saved.getId());

        assertThrows(EmptyResultDataAccessException.class, () -> {
            bookDAO.getById(saved.getId());
        });
    }

}
