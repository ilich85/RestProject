package com.ilich.repository;

import com.ilich.mapper.UserInfoRowMapper;
import com.ilich.model.UserInfoData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;


@Repository
public class UserInfoRepository {

    private static final String SELECT_USER_INFO_BY_ID = "SELECT * FROM users_info WHERE user_id = ?";
    private static final String INSERT_USER_INFO = "INSERT INTO users_info set name = ?, city = ?, phone = ?, user_id = ?";
    private static final String UPDATE_USER_INFO = "UPDATE users_info set name = ?, city = ?, phone = ? WHERE user_id = ?";
    private static final String DELETE_USER_INFO = "DELETE FROM users_info WHERE user_id = ?";
    
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserInfoRowMapper userInfoRowMapper;

    public UserInfoRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public UserInfoData findOne(int id) {
        return jdbcTemplate.queryForObject(SELECT, new Object[]{id}, userInfoRowMapper);
    }

    public void add(UserInfoData ui) {
        jdbcTemplate.update(INSERT, ui.getName(), ui.getCity(), ui.getPhone(), ui.getUserId());
    }

    public void update(UserInfoData info) {
        jdbcTemplate.update(UPDATE, info.getName(), info.getCity(), info.getPhone(), info.getUserId());
    }

    public void remove(int idUser) {
        jdbcTemplate.update(DELETE, idUser);
    }
}
