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

public class JdbcWriterImpl implements WriterRepository {
    private final String SQL_SELECT = "select * from writer left join post using(post_id)";
    private final String SQL_DELETE = "delete from writer where writer_id = ?";
    private final String SQL_UPDATE = "update writer set name = ? where id = ?";
    private final String SQL_ADD = "insert into writer " +
            "(writer_id, name) " +
            "values" +
            "(?, ?)";
    private final String SQL_ADD_POSTS = "insert into post " +
            "(writer_id) " +
            "values " +
            "(?)";
    private final String SQL_GET_POSTS = "select * from post where post_id = ? ";

    @Override
    public Writer getById(Long id) {
        return getAll().stream().filter(writer -> id.equals(writer.getWriter_id())).findFirst().orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SQL_DELETE)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Writer update(Writer writer) {
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SQL_UPDATE)) {
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
        Long newId = generateId();
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SQL_ADD);
             PreparedStatement preparedStatementAddPosts = ConnectionWithDb.getPreparedStatement(SQL_ADD_POSTS)) {
            preparedStatement.setLong(1, newId);
            preparedStatement.setString(2, writer.getName());
            preparedStatement.executeUpdate();

//            disable autocommit mode
            ConnectionWithDb.getInstance().getConnection().setAutoCommit(false);
            for (Long postsId : getIdPosts(writer)) {
                preparedStatementAddPosts.setLong(1, postsId);
                preparedStatementAddPosts.executeUpdate();
            }
            ConnectionWithDb.getInstance().getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return writer;
    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writers = new ArrayList<>();
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SQL_SELECT)) {
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
                Writer writer = new Writer();
                writer.setWriter_id((long) resultSet.getInt("writer_id"));
                writer.setName(resultSet.getString("name"));
                writer.setPosts(getPostsList(writer.getWriter_id()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return writers;
    }

    private List<Post> getPostsList(Long id) {
        JdbcPostImpl jdbcPost = new JdbcPostImpl();
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SQL_GET_POSTS)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            posts = jdbcPost.getPost(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    private List<Long> getIdPosts(Writer writer) {
        List<Long> postId = new ArrayList<>();
        for (Post posts : writer.getPosts()) {
            postId.add(posts.getPost_id());
        }
        return postId;
    }
}
