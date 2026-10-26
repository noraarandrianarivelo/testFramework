package test.repository;

import test.model.Produit;

import java.util.List;
import java.util.Optional;

public interface ProduitRepository {

    List<Produit> findAll();

    Optional<Produit> findById(int id);

    void save(Produit produit);

    void update(Produit produit);

    void deleteById(int id);
}
