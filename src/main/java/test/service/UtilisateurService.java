package test.service;

import java.util.List;
import java.util.Optional;

import test.model.Utilisateur;

public interface UtilisateurService {

    List<Utilisateur> getAllUtilisateurs();

    Optional<Utilisateur> getUtilisateurById(int id);

    Optional<Utilisateur> findByUsernameAndPassword(String username, String password);

    void saveUtilisateur(Utilisateur utilisateur);

    void updateUtilisateur(Utilisateur utilisateur);

    void deleteUtilisateurById(int id);
}
