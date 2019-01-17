package com.ilich.mapper;

import com.ilich.model.AdvertData;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AdvertRowMapper implements RowMapper<AdvertData> {

    @Override
    public AdvertData mapRow(ResultSet rs, int rowNum) throws SQLException {
        AdvertData advert = new AdvertData();
        advert.setIdAdvert(rs.getInt("id_advert"));
        advert.setUserId(rs.getInt("user_id"));
        advert.setAdvertInfoId(rs.getInt("advert_info_id"));
        advert.setPlacingDate(rs.getString("placing_date"));
        return advert;
    }
}
