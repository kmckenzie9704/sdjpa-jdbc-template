package guru.springframework.jdbc.dao;

import guru.springframework.jdbc.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BookDAOImpl implements BookDAO {
    private final JdbcTemplate jdbcTemplate;

    public BookDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Book getById(Long Id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Book WHERE ID = ?", getRowMapper(), Id);
    }
    @Override
    public Book getBookByTitle(String title) {
        return jdbcTemplate.queryForObject("SELECT * FROM BOOK WHERE TITLE = ?", getRowMapper(), title);

    }

    @Override
    public Book saveNewBook(Book book) {

        jdbcTemplate.update("INSERT INTO BOOK (title, isbn, publisher, author_id) VALUES (?,?,?,?)", book.getTitle(), book.getIsbn(), book.getPublisher(), book.getAuthorId());

        Long createdId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        return this.getById(createdId);

    }

    @Override
    public Book updateBook(Book book) {

        jdbcTemplate.update("UPDATE BOOK SET TITLE = ?, ISBN = ?, PUBLISHER = ?, AUTHOR_ID = ? WHERE ID = ?",
                book.getTitle(), book.getIsbn(), book.getPublisher(), book.getAuthorId(), book.getId());

        return this.getById(book.getId());
    }

    @Override
    public void deleteBookById(Long id) {
        jdbcTemplate.update("DELETE FROM BOOK WHERE ID = ?", id);

    }

    private RowMapper<Book> getRowMapper(){
        return new BookMapper();
    }
}
