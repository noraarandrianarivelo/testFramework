package test.repository;

import test.model.Utilisateur;
import java.util.*;

public interface  UtilisateurRepository {
    List<Utilisateur> findAll();

    Optional<Utilisateur> findById(int id);

    Optional<Utilisateur> findByUsernameAndPassword(String username, String password);

    void save(Utilisateur utilisateur);

    void update(Utilisateur utilisateur);

    void deleteById(int id);
}
