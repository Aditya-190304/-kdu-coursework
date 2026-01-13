package com.hospital.repository;

import com.hospital.model.ShiftType;
import com.hospital.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

@Repository
public class StaffingRepository {

    private static final Logger logger = LoggerFactory.getLogger(StaffingRepository.class);
    private final JdbcTemplate jdbcTemplate;


    public StaffingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Maps DB result to Java Object. Handles String -> UUID conversion for MySQL
    private static final RowMapper<User> USER_ROW_MAPPER = (ResultSet rs, int rowNum) -> User.builder()
            .id(UUID.fromString(rs.getString("id")))
            .username(rs.getString("username"))
            .loggedIn(rs.getBoolean("is_logged_in"))
            .timezone(rs.getString("timezone"))
            .tenantId(UUID.fromString(rs.getString("tenant_id")))
            .build();

    public void saveUser(User user) {
        String sql = "INSERT INTO users (id, username, is_logged_in, timezone, tenant_id) VALUES (?, ?, ?, ?, ?)";
        try {
            // MySQL driver will convert UUID.toString() for the VARCHAR column
            jdbcTemplate.update(sql, user.getId().toString(), user.getUsername(),
                    user.isLoggedIn(), user.getTimezone(), user.getTenantId().toString());
            logger.info("Saved user: {}", user.getId());
        } catch (DataAccessException e) {
            logger.error("Error saving user", e);
            throw e;
        }
    }

    public void saveShiftType(ShiftType type) {
        String sql = "INSERT INTO shift_types (id, name, description, is_active, tenant_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, type.getId().toString(), type.getName(), type.getDescription(),
                type.isActive(), type.getTenantId().toString());
    }

    public List<User> findUsersByTenant(UUID tenantId) {
        String sql = "SELECT * FROM users WHERE tenant_id = ?";
        return jdbcTemplate.query(sql, USER_ROW_MAPPER, tenantId.toString());
    }

    public int updateUserTimezone(UUID userId, String newTimezone) {
        String sql = "UPDATE users SET timezone = ? WHERE id = ?";
        return jdbcTemplate.update(sql, newTimezone, userId.toString());
    }

    public List<User> findAllUsers(int limit, int offset, String sortDir) {
        String direction = "DESC".equalsIgnoreCase(sortDir) ? "DESC" : "ASC";
        String sql = "SELECT * FROM users ORDER BY username " + direction + " LIMIT ? OFFSET ?";

        logger.debug("Pagination query: Limit {}, Offset {}", limit, offset);
        return jdbcTemplate.query(sql, USER_ROW_MAPPER, limit, offset);
    }
}