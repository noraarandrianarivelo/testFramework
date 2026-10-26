package test.Controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import java.time.LocalDate;

import org.springframework.context.ApplicationContext;

import mg.itu.annotation.*;
import mg.itu.view.ModelAndView;
import mg.itu.util.*;
import test.model.Produit;
import test.model.Utilisateur;
import test.service.ProduitService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import java.util.Optional;

@Controller
public class Test {
    @UrlMapping(value = "/", method = "GET")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("home");
        return modelAndView;
    }

    @UrlMapping(value = "/salama", method = "GET")
    public ModelAndView salama() {
        ModelAndView modelAndView = new ModelAndView("salama");
        return modelAndView;
    }

    @UrlMapping(value = "/liste", method = "GET")
    public ModelAndView liste(ApplicationContext applicationContext) {

        List<Produit> produits = applicationContext.getBean(ProduitService.class).getAllProduits();

        ModelAndView mv = new ModelAndView("liste");
        mv.addAttribut("liste", produits);

        return mv;
    }

    @UrlMapping(value = "/akory", method = "GET")
    public ModelAndView akoryaby() {
        ModelAndView modelAndView = new ModelAndView("salama");
        return modelAndView;
    }

    @UrlMapping(value = "/coucou", method = "GET")
    public ModelAndView coucou() {
        ModelAndView modelAndView = new ModelAndView("coucou");
        return modelAndView;
    }

    @UrlMapping(value = "/test", method = "GET")
    @ApiRest
    public Optional<Produit> go(ApplicationContext applicationContext) {
        return applicationContext.getBean(ProduitService.class).getProduitById(1);
    }

    @UrlMapping(value = "/test2", method = "POST")
    public String go2() {
        return "POST /test2";
    }

    @UrlMapping(value = "/test2", method = "GET")
    @ApiRest
    public List<Produit> liste2(ApplicationContext applicationContext) {

        List<Produit> produits = applicationContext.getBean(ProduitService.class).getAllProduits();

        return produits;
    }

    @UrlMapping(value = "/testeko", method = "GET")
    @ApiRest
    public String valiny() {
        return "[{\"message\": \"GET /testeko\"}]";
    }

    @Auth
    @UrlMapping(value = "/form", method = "GET")
    public ModelAndView afficherForm() {
        ModelAndView modelAndView = new ModelAndView("afficherForm");
        return modelAndView;
    }

    @UrlMapping(value = "/save", method = "POST")
    @ApiRest
    public String save(ApplicationContext applicationContext,
            List<Produit> produits, MapFile fichier) {
        for (Produit produit : produits) {
            applicationContext.getBean(ProduitService.class).saveProduit(produit);
        }

        return "Fichier enregistré: " + fichier.getNom() + "." + fichier.getExtension();
    }

    // @UrlMapping(value = "/upload", method = "POST")
    // @ApiRest
    // public String upload(MapFile fichier) {
    //     fichier.getBytes();
    //     return "Fichier enregistré: " + fichier.getNom() + "." + fichier.getExtension() ;
    // }

    // @UrlMapping(value = "/upload", method = "POST")
    // @ApiRest
    // public String upload(MapFile fichier) throws IOException {
    //     if (fichier.getBytes() == null || fichier.getBytes().length == 0) {
    //         return "Aucun fichier choisi";
    //     }

    //     // Dossier de destination (créé s'il n'existe pas)
    //     Path dossier = Paths.get(System.getProperty("catalina.base"), "uploads/test");
    //     Files.createDirectories(dossier);

    //     // getFileName() retire tout chemin éventuel (protection contre ../../)
    //     String nomSur = Paths.get(fichier.getNom()).getFileName().toString();

    //     // Préfixe pour ne pas écraser un fichier du même nom
    //     Path destination = dossier.resolve(System.currentTimeMillis() + "_" + nomSur);

    //     Files.write(destination, fichier.getBytes());

    //     return "Fichier enregistré: " + destination.toAbsolutePath();
    // }

    @UrlMapping(value = "/upload", method = "POST")
    @ApiRest
    public String upload(List<MapFile> fichiers) throws IOException {
        if (fichiers == null || fichiers.isEmpty()) {
            return "Aucun fichier choisi";
        }

        // Dossier de destination (créé s'il n'existe pas)
        Path dossier = Paths.get(System.getProperty("catalina.base"), "uploads/test");
        Files.createDirectories(dossier);

        // getFileName() retire tout chemin éventuel (protection contre ../../)
        for(MapFile fichier : fichiers) {
        String nomSur = Paths.get(fichier.getNom()).getFileName().toString();

        // Préfixe pour ne pas écraser un fichier du même nom
        Path destination = dossier.resolve(System.currentTimeMillis() + "_" + nomSur);  

        Files.write(destination, fichier.getBytes());
        }

        return "Fichier enregistré";
    }

    
    @UrlMapping (value = "/session", method = "GET")
    @ApiRest
    public String session(Session session) {
        session.setAttribute("date", LocalDate.now().toString());
        session.setAttribute("nom", "Rakoto");
        session.setAttribute("userId", 23);
        return session.getAllAttributesMap().toString();
    }

    @UrlMapping (value = "/sessionTest", method = "GET")
    @ApiRest
    public String session2(Session session) {
        return session.getAttribute("date").toString();
    }

    @Auth(roles = {"user"})
    @UrlMapping (value = "/removeSession", method = "GET")
    @ApiRest 
    public String removeSession(Session session) {
        session.removeAttribute("username");
        return session.getAllAttributesMap().toString();
    }

    @UrlMapping (value = "/sessions", method = "GET")
    @ApiRest 
    public String getSessions(Session session) {
        return session.getAllAttributesMap().toString();
    }


    @UrlMapping (value = "/login", method = "GET") 
    public ModelAndView showLogin() {
        ModelAndView modelAndView = new ModelAndView("connexion");
        return modelAndView;
    }

    @UrlMapping (value = "/connect", method = "POST")
    public ModelAndView connect(ApplicationContext applicationContext, Utilisateur utilisateur,  Session session) {

        Optional<test.model.Utilisateur> utilisateurOpt = applicationContext
            .getBean(test.service.UtilisateurService.class)
            .findByUsernameAndPassword(utilisateur.getUsername(), utilisateur.getPassword());

        if (utilisateurOpt.isPresent()) {
            session.setAttribute("userId", utilisateurOpt.get().getId());
            session.setAttribute("role", utilisateurOpt.get().getRole());
            session.setAttribute("username", utilisateurOpt.get().getUsername());
            return new ModelAndView("liste");
        } else {
            ModelAndView modelAndView = new ModelAndView("connexion");
            modelAndView.addAttribut("error", "Nom d'utilisateur ou mot de passe incorrect.");
            return modelAndView;
        }
    }

    @Auth
    @UrlMapping(value = "/logout", method = "GET")
    public ModelAndView logout(Session session) {
        session.removeAttribute("userId");
        session.removeAttribute("role");
        return new ModelAndView("connexion");
    }
    
    // @UrlMapping(value="/test2", method="GET")
    // public String go4(){
    // return ("GET /test2");
    // }

}
