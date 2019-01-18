package com.ilich.repository;

import com.ilich.mapper.AdvertInfoRowMapper;
import com.ilich.model.AdvertInfoData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.Objects;

import static java.sql.Statement.RETURN_GENERATED_KEYS;


@Repository
public class AdvertInfoRepository {

     private static final String SELECT_ADVERT_INFO_BY_ID = "SELECT * FROM adverts_info WHERE id_adverts_info = ?";
    private static final String INSERT_ADVERT_INFO = "INSERT INTO adverts_info SET company = ?, model = ?, color = ?," +
            " year_of_issue = ?, price = ?";
    private static final String UPDATE_ADVERT_INFO = "UPDATE adverts_info SET company = ?, model = ?, color = ?," +
            " year_of_issue = ?, price = ? WHERE id_adverts_info = ?";
    private static final String DELETE_ADVERT_INFO = "DELETE FROM adverts_info WHERE id_adverts_info = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AdvertInfoRowMapper advertInfoRowMapper;

    public AdvertInfoRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public AdvertInfoData findOne(long id) {
        return jdbcTemplate.queryForObject(SELECT, new Object[]{id}, advertInfoRowMapper);
    }

    public long add(AdvertInfoData advertInfo) {
        final PreparedStatementCreator psc = connection -> {
            final PreparedStatement ps = connection.prepareStatement(INSERT, RETURN_GENERATED_KEYS);
            ps.setString(1, advertInfo.getCompany());
            ps.setString(2, advertInfo.getModel());
            ps.setString(3, advertInfo.getColor());
            ps.setInt(4, advertInfo.getYearOfIssue());
            ps.setInt(5, advertInfo.getPrice());
            return ps;
        };
        final KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, holder);
        return Objects.requireNonNull(holder.getKey()).longValue();
    }

    public void update(AdvertInfoData ai) {
        jdbcTemplate.update(UPDATE, ai.getCompany(), ai.getModel(), ai.getColor(),
                ai.getYearOfIssue(), ai.getPrice(), ai.getIdAdvertInfo());
    }

    public void remove(long id) {
        jdbcTemplate.update(DELETE, id);
    }
}
