package guru.springframework.jdbc.dao;
import guru.springframework.jdbc.domain.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book>{

@Override
public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book   = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setPublisher(rs.getString("publisher"));
        return book;
        }

}
