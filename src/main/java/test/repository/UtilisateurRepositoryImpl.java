package test.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import test.model.*;

import java.util.List;

@Repository
public class UtilisateurRepositoryImpl implements UtilisateurRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired  
    public UtilisateurRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Utilisateur> rowMapper = new RowMapper<Utilisateur>() {
        @Override
        public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
            Utilisateur p = new Utilisateur();
            p.setId(rs.getInt("id"));
            p.setUsername(rs.getString("username"));
            p.setPassword(rs.getString("password"));
            p.setRole(rs.getString("role"));
            return p;
        }
    };

    @Override
    public List<test.model.Utilisateur> findAll() {
        return jdbcTemplate.query("SELECT * FROM utilisateur ORDER BY id", rowMapper);
    }

    @Override
    public Optional<test.model.Utilisateur> findById(int id) {
                List<Utilisateur> result = jdbcTemplate.query(
                "SELECT * FROM utilisateur WHERE id = ?", rowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public Optional<Utilisateur> findByUsernameAndPassword(String username, String password) {
        List<Utilisateur> result = jdbcTemplate.query(
                "SELECT * FROM utilisateur WHERE username = ? AND password = ?",
                rowMapper,
                username,
                password);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public void save(test.model.Utilisateur utilisateur) {
        jdbcTemplate.update("INSERT INTO utilisateur (username, password) VALUES (?, ?)",
                utilisateur.getUsername(), utilisateur.getPassword());
    }

    @Override
    public void update(test.model.Utilisateur utilisateur) {
        jdbcTemplate.update("UPDATE utilisateur SET username = ?, password = ? WHERE id = ?",
                utilisateur.getUsername(), utilisateur.getPassword(), utilisateur.getId());
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM utilisateur WHERE id = ?", id);
    }
    
}
