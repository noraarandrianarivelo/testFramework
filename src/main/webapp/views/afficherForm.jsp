<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulaire</title>
</head>
<body>
    <form action="upload" method="post" enctype="multipart/form-data">
    <%-- enctype="multipart/form-data" --%>
        <%-- <label for="nom">Nom: </label>
        <input type="text" name="produits[0].nom" >
        <br>
        <label for="prix">Prix: </label>
        <input type="number" name="produits[0].prix">
        <br>
        <label for="nom">Nom: </label>
        <input type="text" name="produits[1].nom">
        <br>
        <label for="prix">Prix: </label>
        <input type="number" name="produits[1].prix"> --%>
        <%-- <br> --%>
        <label for="fichier">Fichier: </label>
        <input type="file" name="fichiers[0]" id="fichier">
        <br>
        <label for="fichier">Fichier: </label>
        <input type="file" name="fichiers[1]" id="fichier">
        <br>

        <br>
        <input type="submit" value="Valider">
    </form>
</body>
</html>
