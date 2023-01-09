package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorDAOImpl implements AuthorDAO {
    private final JdbcTemplate jdbcTemplate;

    public AuthorDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Author getById(Long Id) {

        String sql = "SELECT AUTHOR.id as id, first_name, last_name, book.id as book_id, BOOK.isbn, book.publisher, book.title from AUTHOR " +
                "LEFT OUTER JOIN BOOK ON AUTHOR.ID = BOOK.AUTHOR_ID WHERE AUTHOR.ID =?";

        return jdbcTemplate.query(sql, new AuthorExtractor(), Id);
    }

    @Override
    public Author getByName(String firstName, String lastName) {

        return jdbcTemplate.queryForObject("SELECT * FROM AUTHOR WHERE first_name = ? and last_name = ?", getRowMapper(), firstName, lastName);
    }

    @Override
    public Author saveNewAuthor(Author author) {
        jdbcTemplate.update("INSERT INTO AUTHOR (first_name, last_name) VALUES (?,?)", author.getFirstName(), author.getLastName());

        Long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        return this.getById(createdId);
    }

    @Override
    public Author updateAuthor(Author author) {
        jdbcTemplate.update("UPDATE AUTHOR SET FIRST_NAME = ?, LAST_NAME = ? WHERE ID = ?",
                author.getFirstName(), author.getLastName(), author.getId());



        return this.getById(author.getId());
    }

    @Override
    public void deleteAuthorById(Long id) {
        jdbcTemplate.update("DELETE FROM AUTHOR WHERE ID = ?", id);

    }

    private RowMapper<Author> getRowMapper(){
        return new AuthorMapper();
    }
}
