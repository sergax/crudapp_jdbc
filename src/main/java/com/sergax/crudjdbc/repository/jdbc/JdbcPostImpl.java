package com.sergax.crudjdbc.repository.jdbc;

import com.sergax.crudjdbc.controller.PostController;
import com.sergax.crudjdbc.model.Post;
import com.sergax.crudjdbc.model.PostStatus;
import com.sergax.crudjdbc.model.Tag;
import com.sergax.crudjdbc.repository.PostRepository;
import com.sergax.crudjdbc.utils.ConnectionWithDb;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Scanner;


public class JdbcPostImpl implements PostRepository {
    private final String SQL_SELECT = "select * from post " +
            "left join tag_post using(post_id) " +
            "left join tag using (tag_id) ";
    private final String SQL_DELETE = "delete from post where post_id = ?";
    private final String SQL_UPDATE = "update post set content = ?, status = ? where post_id = ?";
    private final String SQL_CREATE = "insert into post " +
            "(post_id, content, status) " +
            "values" +
            "(?, ?, ?)";
    private final String SQL_ADD_TAGS = "insert into tag_post " +
            "(tag_id, post_id) " +
            "values " +
            "(?, ?)";
    private final String SQL_GET_TAGS = "select * from tag join tag_post using(tag_id) where post_id = ?";
    private final String SQL_DELETE_ALL = "truncate table post";

    @Override
    public Post getById(Long id) {
        return getAll().stream().filter(post -> id.equals(post.getPost_id())).findFirst().orElse(null);
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

    public void deleteAll() {
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SQL_DELETE_ALL)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Post update(Post post) {
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SQL_UPDATE);
             PreparedStatement preparedStatementAddTags = ConnectionWithDb.getPreparedStatement(SQL_ADD_TAGS)) {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setString(2, String.valueOf(post.getStatus()));
            preparedStatement.setLong(3, post.getPost_id());
            preparedStatement.executeUpdate();

//          disable autocommit mode
//            ConnectionWithDb.getInstance().getConnection().setAutoCommit(false);
//            for (Long tagId : getIdTags(post)
//            ) {
//                preparedStatementAddTags.setLong(1, tagId);
//                preparedStatementAddTags.executeUpdate();
//            }
//            ConnectionWithDb.getInstance().getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Post create(Post post) {
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SQL_CREATE);
             PreparedStatement preparedStatementAddTags = ConnectionWithDb.getPreparedStatement(SQL_ADD_TAGS)) {

            preparedStatement.setLong(1, generateId());
            preparedStatement.setString(2, post.getContent());
            preparedStatement.setString(3, String.valueOf(post.getStatus()));
            preparedStatement.executeUpdate();

            ConnectionWithDb.getInstance().getConnection().setAutoCommit(false);
            for (Long tagId : getIdTags(post)) {
                preparedStatementAddTags.setLong(1, tagId);
                preparedStatementAddTags.setLong(2, getPostsId());
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
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SQL_SELECT)) {
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

    private Long getPostsId() {
        Scanner scanner = new Scanner(System.in);
        Long idNew = scanner.nextLong();
        return idNew;
    }

    private List<Tag> getTagsList(Long postsId) {
        JdbcTagImpl jdbcTag = new JdbcTagImpl();
        List<Tag> tagList = new ArrayList<>();
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SQL_GET_TAGS)) {
            preparedStatement.setLong(1, postsId);
            ResultSet resultSet = preparedStatement.executeQuery();
            tagList = jdbcTag.getTags(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tagList;
    }
}
