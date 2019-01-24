package com.ilich.repository;

import com.ilich.exception.UserNotFoundException;
import com.ilich.mapper.UserAuthDataRowMapper;
import com.ilich.model.UserAuthData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import static com.ilich.util.PropertyStrings.USER_NOT_FOUND;


@Repository
public class UserAuthDataRepository {

    private static final String SELECT_BY_USERNAME = "SELECT * FROM users_auth_data WHERE username = ?";
    private static final String SELECT_PASSWORD = "SELECT password FROM users_auth_data WHERE id_auth_data = ?";
    private static final String INSERT = "INSERT INTO users_auth_data SET username = ?, password = ?";
    private static final String UPDATE_PASSWORD = "UPDATE users_auth_data SET password = ? WHERE id_auth_data = ?";
    private static final String REMOVE_USER_DATA = "DELETE FROM users_auth_data WHERE id_auth_data = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserAuthDataRowMapper userAuthDataRowMapper;

    public UserAuthDataRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public UserAuthData checkUser(String username) {
        UserAuthData data;
        try {
            data = jdbcTemplate.queryForObject(SELECT_BY_USERNAME, userAuthDataRowMapper, username);
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(USER_NOT_FOUND);
        }
        return data;
    }

    public String findPassword(int idUser) {
        return jdbcTemplate.queryForObject(SELECT_PASSWORD, String.class, idUser);
    }

    public void add(UserAuthData data) {
        jdbcTemplate.update(INSERT, data.getUsername(), data.getPassword());
    }

    public void update(String password, int idUser) {
        jdbcTemplate.update(UPDATE_PASSWORD, password, idUser);
    }

    public void remove(int idUser) {
        jdbcTemplate.update(REMOVE_USER_DATA, idUser);
    }
}
