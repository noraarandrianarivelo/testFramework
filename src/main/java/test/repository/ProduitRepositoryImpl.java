package test.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import test.model.Produit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ProduitRepositoryImpl implements ProduitRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProduitRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Produit> rowMapper = new RowMapper<Produit>() {
        @Override
        public Produit mapRow(ResultSet rs, int rowNum) throws SQLException {
            Produit p = new Produit();
            p.setId(rs.getInt("id"));
            p.setNom(rs.getString("nom"));
            p.setPrix(rs.getDouble("prix"));
            return p;
        }
    };

    @Override
    public List<Produit> findAll() {
        return jdbcTemplate.query("SELECT * FROM produit ORDER BY id", rowMapper);
    }

    @Override
    public Optional<Produit> findById(int id) {
        List<Produit> result = jdbcTemplate.query(
                "SELECT * FROM produit WHERE id = ?", rowMapper, id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public void save(Produit produit) {
        jdbcTemplate.update("INSERT INTO produit (nom, prix) VALUES (?, ?)",
                produit.getNom(), produit.getPrix());
    }

    @Override
    public void update(Produit produit) {
        jdbcTemplate.update("UPDATE produit SET nom = ?, prix = ? WHERE id = ?",
                produit.getNom(), produit.getPrix(), produit.getId());
    }

    @Override
    public void deleteById(int id) {
        jdbcTemplate.update("DELETE FROM produit WHERE id = ?", id);
    }
}
