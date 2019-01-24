package com.ilich.mapper;

import com.ilich.model.AdvertInfoData;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AdvertInfoRowMapper implements RowMapper<AdvertInfoData> {

    @Override
    public AdvertInfoData mapRow(ResultSet rs, int rowNum) throws SQLException {
        AdvertInfoData advertInfo = new AdvertInfoData();
        advertInfo.setCompany(rs.getString("company"));
        advertInfo.setModel(rs.getString("model"));
        advertInfo.setColor(rs.getString("color"));
        advertInfo.setPrice(rs.getInt("price"));
        advertInfo.setYearOfIssue(rs.getInt("year_of_issue"));
        return advertInfo;
    }
}
