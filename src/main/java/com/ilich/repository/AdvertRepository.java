package com.ilich.repository;

import com.ilich.exception.AdvertsNotFoundException;
import com.ilich.mapper.AdvertRowMapper;
import com.ilich.model.AdvertData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Repository
public class AdvertRepository {

    private static final String SELECT_ALL_ADVERTS = "SELECT * FROM adverts";
    private static final String SELECT_ADVERT_BY_ID = "SELECT * FROM adverts WHERE id_advert = ?";
    private static final String SELECT_ADVERTS_BY_USER_ID = "SELECT advert_info_id FROM adverts WHERE user_id = ?";
    private static final String INSERT_ADVERT = "INSERT INTO adverts SET advert_info_id = ?, user_id = ?, placing_date = ?";
    private static final String REMOVE_USER_ADVERTS = "DELETE FROM adverts WHERE user_id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AdvertRowMapper advertRowMapper;

    public AdvertRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<AdvertData> findAll() {
        List<AdvertData> adverts;
        try {
            adverts = jdbcTemplate.query(SELECT_ALL_ADVERTS, advertRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new AdvertsNotFoundException(e.getMessage());
        }
        return adverts;
    }

    public AdvertData findOne(long id) {
        return jdbcTemplate.queryForObject(SELECT_ADVERT_BY_ID, new Object[]{id}, advertRowMapper);
    }

    public List<Integer> usersAdverts(int id) {
        List<Integer> adverts;
        try {
            adverts = jdbcTemplate.queryForList(SELECT_ADVERTS_BY_USER_ID, Integer.class, id);
        } catch (EmptyResultDataAccessException e) {
            throw new AdvertsNotFoundException(e.getMessage());
        }
        return adverts;
    }

    public void add(AdvertData advert) {
        jdbcTemplate.update(INSERT_ADVERT, advert.getAdvertInfoId(), advert.getUserId(),
                new SimpleDateFormat().format(new Date()));
    }

    public void removeUserAdverts(long id) {
        jdbcTemplate.update(REMOVE_USER_ADVERTS, id);
    }
}
