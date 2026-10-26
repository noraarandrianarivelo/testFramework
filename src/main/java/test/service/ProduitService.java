package test.service;

import java.util.List;
import java.util.Optional;
import test.model.Produit;

public interface ProduitService {

    List<Produit> getAllProduits();
    Optional<Produit> getProduitById(int id);
    void saveProduit(Produit produit);
}
