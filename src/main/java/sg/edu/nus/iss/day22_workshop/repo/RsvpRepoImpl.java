package sg.edu.nus.iss.day22_workshop.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import sg.edu.nus.iss.day22_workshop.model.RSVP;

@Repository
public class RsvpRepoImpl {

        @Autowired
        JdbcTemplate jdbcTemplate;

        private final String findAllSQL = "select * from rsvp";

        private final String findByNameSQL = "select * from rsvp where full_name like '%?%'";

        private final String insertSQL = "insert into rsvp (full_name, email, phone, confirmation_date, comments) "
                        + "values (?, ?, ?, ?, ?)";

        private final String updateSQL = "update rsvp " +
                        "set full_name = ?, email = ?, phone = ?, " +
                        "confirmation_date = ?, comments = ?" +
                        "where id = ?";

        private final String countSQL = "select count(*) as cnt from rsvp";

        public List<RSVP> findAll() {

                List<RSVP> resultList = new ArrayList<RSVP>();
                resultList = jdbcTemplate.query(findAllSQL, BeanPropertyRowMapper.newInstance(RSVP.class));
                return resultList;
        }

        public List<RSVP> findByName(String name) {
                List<RSVP> resultList = new ArrayList<RSVP>();
                resultList = jdbcTemplate.query(findByNameSQL, new Object[] { name },
                                BeanPropertyRowMapper.newInstance(RSVP.class));
                return resultList;
        }

        public Boolean save(RSVP rsvp) {
                Integer result = jdbcTemplate.update(insertSQL, rsvp.getFullName(), rsvp.getEmail(), rsvp.getPhone(),
                                rsvp.getConfirmationDate(), rsvp.getComments());

                return result > 0 ? true : false;
        }

        public Boolean update(RSVP rsvp) {
                Integer result = jdbcTemplate.update(updateSQL, rsvp.getFullName(), rsvp.getEmail(), rsvp.getPhone(),
                                rsvp.getConfirmationDate(), rsvp.getComments(), rsvp.getId());
                return result > 0 ? true : false;
        }

        public Integer countAll() {
                Integer result = jdbcTemplate.queryForObject(countSQL, Integer.class);
                return result;
        }

}
