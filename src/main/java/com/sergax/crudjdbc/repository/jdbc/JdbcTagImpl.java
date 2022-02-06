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
    private final String SqlDelete = "delete from tag where id = ?";
    private final String SqlUpdate = "update tag set name = ? where id = ?";
    private final String SqlAdd = "insert into tag " +
            "(id, name) " +
            "values" +
            "(?, ?)";

    @Override
    public Tag getById(Long id) {
        return getAll().stream().filter(tag -> id.equals(tag.getId())).findFirst().orElse(null);
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
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SqlUpdate)) {
            preparedStatement.setString(1, tag.getName());
            preparedStatement.setLong(2, tag.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tag;
    }

    @Override
    public Tag save(Tag tag) {
        try (PreparedStatement preparedStatement = ConnectionWithDb.getPreparedStatement(SqlAdd)) {
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
                tag.setId((long) resultSet.getInt("id"));
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
                getAll().stream().skip(getAll().size() - 1).findFirst().get().getId() + 1
                : 1L;
    }
}
