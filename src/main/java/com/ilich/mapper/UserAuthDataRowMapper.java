package com.ilich.mapper;

import com.ilich.model.UserAuthData;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserAuthDataRowMapper implements RowMapper<UserAuthData> {

    @Override
    public UserAuthData mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserAuthData userAuthData = new UserAuthData();
        userAuthData.setIdUser(rs.getInt("id_auth_data"));
        userAuthData.setUsername(rs.getString("username"));
        userAuthData.setPassword(rs.getString("password"));
        return userAuthData;
    }
}
