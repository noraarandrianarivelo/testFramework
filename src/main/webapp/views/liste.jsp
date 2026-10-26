<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="test.model.Produit" %>
<%
    List<Produit> liste = (List<Produit>) request.getAttribute("liste");
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste</title>
</head>
<body>
    <h1>Bonjour de liste.jsp</h1>

    <p>Voici la liste des produits (issue de la base de donnees)</p>
    <%
        if (liste != null && !liste.isEmpty()) {
            for (Produit produit : liste) {
                out.println("<p>" + produit.getId() + " - " + produit.getNom() + " - " + produit.getPrix() + " Ar</p>");
            }
        } else {
            out.println("<p>Aucune donnée disponible.</p>");
        }
    %>
    
</body>
</html>