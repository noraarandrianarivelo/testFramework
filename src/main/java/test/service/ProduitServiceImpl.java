package test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.model.Produit;
import test.repository.ProduitRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitServiceImpl implements ProduitService {

    private final ProduitRepository produitRepository;

    @Autowired
    public ProduitServiceImpl(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    @Override
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    @Override 
    public Optional<Produit> getProduitById(int id){
        return produitRepository.findById(id);
    }

    @Override 
    public void saveProduit(Produit produit) {
        produitRepository.save(produit);
    }
}
