package com.sergax.crudjdbc.repository.jdbc;

import com.sergax.crudjdbc.model.Post;
import com.sergax.crudjdbc.model.PostStatus;
import com.sergax.crudjdbc.model.Tag;
import com.sergax.crudjdbc.repository.PostRepository;
import com.sergax.crudjdbc.utils.ConnectionWithDb;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class JdbcPostImpl implements PostRepository {
    private final String SqlSelect = "select * from post";
    private final String SqlDelete = "delete from post where id = ?";
    private final String SqlUpdate = "update post set content = ?, status = ? where id = ?";
    private final String SqlAdd = "insert into post " +
            "(id, content, status) " +
            "values" +
            "(?, ?, ?)";

    private final String SqlAddTags = "update post set tags_id = ? where id = ? ";
    private final String SqlGetTags = "select * from tag where id = ? ";

    @Override
    public Post getById(Long id) {
        return getAll().stream().filter(post -> id.equals(post.getId())).findFirst().orElse(null);
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
    public Post update(Post post) {
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SqlUpdate);
             PreparedStatement preparedStatementAddTags = ConnectionWithDb.getPreparedStatement(SqlAddTags)) {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setString(2, String.valueOf(post.getStatus()));
            preparedStatement.setLong(3, post.getId());
            preparedStatement.executeUpdate();

            //disable autocommit mode
            ConnectionWithDb.getInstance().getConnection().setAutoCommit(false);
            for (Long tagId : getIdTags(post)
            ) {
                preparedStatementAddTags.setLong(1, post.getId());
                preparedStatementAddTags.setLong(2, tagId);
                preparedStatementAddTags.executeUpdate();
            }
            ConnectionWithDb.getInstance().getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Post save(Post post) {
        Long newId = generateId();
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SqlAdd);
             PreparedStatement preparedStatementAddTags = ConnectionWithDb.getPreparedStatement(SqlAddTags)) {
            preparedStatement.setLong(1, newId);
            preparedStatement.setString(2, post.getContent());
            preparedStatement.setString(3, String.valueOf(post.getStatus()));
            preparedStatement.executeUpdate();

            ConnectionWithDb.getInstance().getConnection().setAutoCommit(false);
            for (Long tagId : getIdTags(post)
            ) {
                preparedStatementAddTags.setLong(1, post.getId());
                preparedStatementAddTags.setLong(2, tagId);
                preparedStatementAddTags.executeUpdate();
            }
            ConnectionWithDb.getInstance().getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SqlSelect)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            posts = getPost(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    protected List<Post> getPost(ResultSet resultSet) {
        List<Post> posts = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Post post = new Post();
                post.setId((long) resultSet.getInt("id"));
                post.setContent(resultSet.getString("content"));
                post.setTags(getTagsList(post.getId()));
                post.setStatus(PostStatus.valueOf("status"));
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    private long generateId() {
        return !getAll().isEmpty() ?
                getAll().stream().skip(getAll().size() - 1).findFirst().get().getId() + 1
                : 1L;
    }

    private List<Long> getIdTags(Post post) {
        List<Long> tagsId = new ArrayList<>();
        for (Tag postsTag : post.getTags()) {
            tagsId.add(postsTag.getId());
        }
        return tagsId;
    }

    private List<Tag> getTagsList(Long postsId) {
        JdbcTagImpl jdbcTag = new JdbcTagImpl();
        List<Tag> tagList = new ArrayList<>();
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SqlGetTags)) {
            preparedStatement.setLong(1, postsId);
            ResultSet resultSet = preparedStatement.executeQuery();
            tagList = jdbcTag.getTags(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tagList;
    }
}
