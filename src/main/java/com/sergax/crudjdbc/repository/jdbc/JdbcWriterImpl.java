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
    private final String SqlSelect = "select * from writer";
    private final String SqlDelete = "delete from writer where id = ?";
    private final String SqlUpdate = "update writer set name = ? where id = ?";
    private final String SqlAdd = "insert into writer " +
            "(id, name) " +
            "values" +
            "(?, ?)";
    private final String SqlAddPosts = "update writer set posts_id = ? where id = ? ";
    private final String SqlGetPosts = "select * from post where id = ? ";

    @Override
    public Writer getById(Long id) {
        return getAll().stream().filter(writer -> id.equals(writer.getWriter_id())).findFirst().orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SqlDelete)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Writer update(Writer writer) {
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SqlUpdate)) {
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
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SqlAdd);
             PreparedStatement preparedStatementAddPosts = ConnectionWithDb.getPreparedStatement(SqlAddPosts)) {
            preparedStatement.setLong(1, newId);
            preparedStatement.setString(2, writer.getName());
            preparedStatement.executeUpdate();

            //disable autocommit mode
//            ConnectionWithDb.getInstance().getConnection().setAutoCommit(false);
//            for (Long postsId : getIdPosts(writer)) {
//                preparedStatementAddPosts.setLong(1, newId);
//                preparedStatementAddPosts.setLong(2, postsId);
//                preparedStatementAddPosts.executeUpdate();
//            }
            ConnectionWithDb.getInstance().getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return writer;
    }

    @Override
    public List<Writer> getAll() {
        List<Writer> writers = new ArrayList<>();
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SqlSelect)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            writers = getWriter(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return writers;
    }

    private long generateId() {
        return !getAll().isEmpty() ?
                getAll().stream().skip(getAll().size() - 1).findFirst().get().getWriter_id() + 1
                : 1L;
    }

    private List<Writer> getWriter(ResultSet resultSet) {
        List<Writer> writers = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Writer writer = new Writer();
                writer.setWriter_id((long) resultSet.getInt("id"));
                writer.setName(resultSet.getString("name"));
//                writer.setPosts(getPostsList(writer.getWriter_id()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return writers;
    }

    private List<Post> getPostsList(Long writerId) {
        JdbcPostImpl jdbcPost = new JdbcPostImpl();
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SqlGetPosts)) {
            preparedStatement.setLong(1, writerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            posts = jdbcPost.getPost(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }
//
//    private List<Long> getIdPosts(Writer writer) {
//        List<Long> postId = new ArrayList<>();
//        for (Post posts : writer.getPosts()) {
//            postId.add(posts.getPost_id());
//        }
//        return postId;
//    }
}
