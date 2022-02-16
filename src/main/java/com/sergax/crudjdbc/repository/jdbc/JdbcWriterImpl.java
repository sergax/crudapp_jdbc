package com.sergax.crudjdbc.repository.jdbc;

import com.sergax.crudjdbc.model.Post;
import com.sergax.crudjdbc.model.Writer;
import com.sergax.crudjdbc.repository.WriterRepository;
import com.sergax.crudjdbc.utils.ConnectionWithDb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JdbcWriterImpl implements WriterRepository {
    private final String SQL_SELECT = "select * from writer join post using(post_id)";
    private final String SQL_DELETE = "delete from writer where writer_id = ?";
    private final String SQL_UPDATE = "update writer set name = ? where writer_id = ?";
    private final String SQL_ADD = "insert into writer " +
            "(writer_id, name, post_id) " +
            "values" +
            "(?, ?, ?)";
    private final String SQL_GET_POSTS = "select * from post where post_id = ? ";

    @Override
    public Writer getById(Long id) {
        return getAll().stream().filter(writer -> id.equals(writer.getWriter_id())).findFirst().orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        try (PreparedStatement preparedStatement = ConnectionWithDb.getConnection().prepareStatement(SQL_DELETE)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Writer update(Writer writer) {
        try (PreparedStatement preparedStatement = ConnectionWithDb.getConnection().prepareStatement(SQL_UPDATE)) {
            preparedStatement.setString(1, writer.getName());
            preparedStatement.setLong(2, writer.getWriter_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return writer;
    }

    @Override
    public Writer create(Writer writer) {
        try (PreparedStatement preparedStatement = ConnectionWithDb.getConnection().prepareStatement(SQL_ADD)) {
            preparedStatement.setLong(1, generateId());
            preparedStatement.setString(2, writer.getName());
            preparedStatement.setLong(3, getPostsId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return writer;
    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writers = new ArrayList<>();
        try (PreparedStatement preparedStatement = ConnectionWithDb.getConnection().prepareStatement(SQL_SELECT)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            writers = getWriter(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return writers;
    }

    private Long generateId() {
        return !getAll().isEmpty() ?
                getAll().stream().skip(getAll().size() - 1).findFirst().get().getWriter_id() + 1
                : 1L;
    }

    private List<Writer> getWriter(ResultSet resultSet) {
        List<Writer> writers = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Long id = resultSet.getLong("post_id");
                Writer writer = new Writer();
                writer.setWriter_id((long) resultSet.getInt("writer_id"));
                writer.setName(resultSet.getString("name"));
                writer.setPosts(getPostsList(id));
                writers.add(writer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return writers;
    }

    private List<Post> getPostsList(Long id) {
        JdbcPostImpl jdbcPost = new JdbcPostImpl();
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = ConnectionWithDb.getConnection().prepareStatement(SQL_GET_POSTS)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            posts = jdbcPost.getPost(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    private Long getPostsId() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLong();
    }
}
