package com.ilich.mapper;

import com.ilich.model.UserInfoData;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserInfoRowMapper implements RowMapper<UserInfoData> {

    @Override
    public UserInfoData mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserInfoData userInfo = new UserInfoData();
        userInfo.setName(rs.getString("name"));
        userInfo.setCity(rs.getString("city"));
        userInfo.setPhone(rs.getInt("phone"));
        userInfo.setUserId(rs.getInt("user_id"));
        return userInfo;
    }
}
