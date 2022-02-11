package com.sergax.crudjdbc.repository.jdbc;

import com.sergax.crudjdbc.model.Tag;
import com.sergax.crudjdbc.repository.TagRepository;
import com.sergax.crudjdbc.utils.ConnectionWithDb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcTagImpl implements TagRepository {
    private final String SqlSelect = "select * from tag";
    private final String SqlDelete = "delete from tag where tag_id = ?";
    private final String SqlUpdate = "update tag set name = ? where tag_id = ?";
    private final String SqlCreate = "insert into tag " +
            "(tag_id, name) " +
            "values" +
            "(?, ?)";

    @Override
    public Tag getById(Long id) {
        return getAll().stream().filter(tag -> id.equals(tag.getTag_id())).findFirst().orElse(null);
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
    public Tag update(Tag tag) {
        //need refactoring !!!!!!!!!!!!!!!!!!!!!!!!!!!!
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SqlUpdate)) {
            preparedStatement.setString(1, tag.getName());
            preparedStatement.setLong(2, tag.getTag_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tag;
    }

    @Override
    public Tag create(Tag tag) {
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SqlCreate)) {
            preparedStatement.setLong(1, generateId());
            preparedStatement.setString(2, tag.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tag;
    }

    @Override
    public List<Tag> getAll() {
        List<Tag> tags = new ArrayList<>();
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SqlSelect)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            tags = getTags(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }

    public List<Tag> getTags(ResultSet resultSet) {
        List<Tag> tags = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Tag tag = new Tag();
                tag.setTag_id((long) resultSet.getInt("tag_id"));
                tag.setName(resultSet.getString("name"));
                tags.add(tag);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }

    private long generateId() {
        return !getAll().isEmpty() ?
                getAll().stream().skip(getAll().size() - 1).findFirst().get().getTag_id() + 1
                : 1L;
    }
}