<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="lib/css/appStyles.css" rel="stylesheet" type="text/css"/>
        <link href="lib/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <link href="lib/jquery-ui/jquery-ui.css" rel="stylesheet" type="text/css"/>        
        <script src="lib/jquery/jquery-2.1.3.min.js" type="text/javascript"></script>        
        <script src="lib/bootstrap/js/bootstrap.js" type="text/javascript"></script>
        <script src="lib/bootstrap/js/ui-bootstrap-tpls.js" type="text/javascript"></script>
        <script src="lib/jquery-ui/jquery-ui.js" type="text/javascript"></script>
        <title>Gestion des oeuvres</title>
    </head>
</html>
<script>
    function nombre(event)
    {
    var source = document.getElementById("prix"); //On récupère la balise
    var value = source.value; //ici, sa valeur
    if (isNaN(parseFloat(value))) //cas où la valeur n'est pas du tout un nombre
// Dans ce cas, parseFloat(value) est égal à NaN
        {
        source.value = ""; // la valeur devient nulle
        }
    else if (value != parseInt(value) + ".") // Cas ou ce n'est pas un nombre avec juste un point derrière (pour la virgule)
        {
        source.value = parseFloat(value); // La valeur devient la partie nombre
        }
    }
 </script>