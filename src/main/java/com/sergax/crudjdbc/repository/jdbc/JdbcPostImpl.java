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
    private final String SqlSelect = "select * from post left join tag using(tag_id)";
    private final String SqlDelete = "delete from post where post_id = ?";
    private final String SqlUpdate = "update post set content = ?, status = ? where post_id = ?";
    private final String SqlCreate = "insert into post " +
            "(post_id, content, status) " +
            "values" +
            "(?, ?, ?)";
    private final String SqlAddTags = "update post " +
            "left join tag_post on (post.post_id = tag_post.post_id)" +
            "left join tag on (tag_post.tag_id = tag.tag_id)" +
            "set post.tag_id = ?";
    private final String SqlGetTags = "select tag_id, name from tag join post using(tag_id) where post_id = ?";

    @Override
    public Post getById(Long id) {
        return getAll().stream().filter(post -> id.equals(post.getPost_id())).findFirst().orElse(null);
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
            preparedStatement.executeUpdate();

//            disable autocommit mode
            ConnectionWithDb.getInstance().getConnection().setAutoCommit(false);
            for (Long tagId : getIdTags(post)
            ) {
                preparedStatementAddTags.setLong(1, tagId);
                preparedStatementAddTags.setLong(2, post.getPost_id());
                preparedStatementAddTags.executeUpdate();
            }
            ConnectionWithDb.getInstance().getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Post create(Post post) {
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SqlCreate);
             PreparedStatement preparedStatementAddTags = ConnectionWithDb.getPreparedStatement(SqlAddTags)) {

            preparedStatement.setLong(1, generateId());
            preparedStatement.setString(2, post.getContent());
            preparedStatement.setString(3, PostStatus.ACTIVE.name());
            preparedStatement.executeUpdate();

            ConnectionWithDb.getInstance().getConnection().setAutoCommit(false);
            for (Long tagId : getIdTags(post)
            ) {
                preparedStatementAddTags.setLong(1, tagId);
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
                post.setPost_id((long) resultSet.getInt("post_id"));
                post.setContent(resultSet.getString("content"));
                post.setTags(getTagsList(post.getPost_id()));
                post.setStatus(PostStatus.valueOf(resultSet.getString("status")));
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    private long generateId() {
        return !getAll().isEmpty() ?
                getAll().stream().skip(getAll().size() - 1).findFirst().get().getPost_id() + 1
                : 1L;
    }

    private List<Long> getIdTags(Post post) {
        List<Long> tagsId = new ArrayList<>();
        for (Tag postsTag : post.getTags()) {
            tagsId.add(postsTag.getTag_id());
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
