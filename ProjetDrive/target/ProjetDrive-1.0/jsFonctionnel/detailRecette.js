/**
 * Cette m?thode affiche les details de la page d'acceuil.
 *
 * On utilise la propri?t? 'responseText' de l'objet XMLHttpRequest afin
 * de r?cup?rer sous forme de texte le flux envoy? par le serveur.
 */

window.onload = afficheDetailRecette();
function afficheDetailRecette() {
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requ?te au serveur avec les param?tres ?ventuels.
    xhr.open("GET", "ServletDetailRecette?method=afficher");

    // On pr?cise ce que l'on va faire quand on aura re?u la r?ponse du serveur.
    xhr.onload = function () {
        // Si la requ?te http s'est bien pass?e.
        if (xhr.status === 200) {
            
            //console.log("re src"+xhr.responseXML.getElementsByTagName("recetteLib")[0].firstChild.nodeValue);
           
            elt = document.getElementById("re_detail");
            var reSrc = xhr.responseXML.getElementsByTagName("reSrc")[0].firstChild.nodeValue;
            var recetteLib = xhr.responseXML.getElementsByTagName("recetteLib")[0].firstChild.nodeValue;
            var text = "";
            for (var i = 0; i < xhr.responseXML.getElementsByTagName("ingLib").length; i++) {
                var ing = xhr.responseXML.getElementsByTagName("ingLib")[i].firstChild.nodeValue;
                var qte = xhr.responseXML.getElementsByTagName("qte")[i].firstChild.nodeValue;
                var mesure = xhr.responseXML.getElementsByTagName("mesure")[i].firstChild.nodeValue;
                var prodLib = xhr.responseXML.getElementsByTagName("prodLib")[i].firstChild.nodeValue;
                var moyenRec = xhr.responseXML.getElementsByTagName("moyenRec")[i].firstChild.nodeValue;
                console.log(ing+"----"+qte+"----"+prodLib);
                text = text + "<tr>"+
                                    "<td><p>"+ing+"</p></td>"+                                         
                                    "<td><p>"+qte +" "+ mesure +"</p></td>"+
                                    "<td><p>"+prodLib+"</p></td>"+
                                "</tr>";
                
            }
            var txt=creerModuleTable(reSrc,recetteLib,text,moyenRec);
            elt.innerHTML = txt;
            //elt.insertAdjacentHTML("beforeend","");
            //Client
            document.getElementById("btn_re_ajouter").addEventListener("click", ajouterRecette);
            if (xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue === "horsConnection") {
                document.getElementById("btn_re_ajouter").style.disabled=true;
            } else {
                var elt2 = document.getElementById("connexion");
                elt2.innerHTML = "Bienvenue! " + xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue;
                document.getElementById("listeCourses").style.display = "block";
                document.getElementById("panier").style.display = "block";
                document.getElementById("cartcounter").style.display = "block";
                document.getElementById("btn_re_ajouter").style.disabled=false;
                //elt2.insertAdjacentHTML("afterbegin",xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue);
                
                afficherQte();
            }
            
        }
    };


    // Envoie de la requ?te.
    xhr.send();
}


function afficherQte() {
    console.log("zhixingle");
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();

        xhr.open("GET", "ServletAfficherNb");

        //On pr?cise ce que l'on va faire quand on aura re?u la r?ponse du serveur.
        xhr.onload = function () {
            // Si la requ?te http s'est bien pass?e.
            if (xhr.status === 200) {
                
                var quantitePanier = document.getElementById("cartcounter");
                //quantitePanier.innerHTML = 2;
                console.log(xhr.responseXML.getElementsByTagName("quantitePanier")[0].firstChild.nodeValue);
                quantitePanier.innerHTML = xhr.responseXML.getElementsByTagName("quantitePanier")[0].firstChild.nodeValue;
            }
        };
    
    // Envoie de la requ?te.
    xhr.send();
}

function creerModuleTable (reSrc,recetteLib, text,moyenRec){
    return("<div class='col-sm-6'>"+
                "<div class='view-product'>"+
                    "<img src='"+reSrc+"' alt='' /> "+
                "</div>"+
            "</div>"+
            "<div class='col-sm-6'>"+
                "<div class='recette-information'><!--/product-information-->"+
                    "<h2 class='title text-center'>"+recetteLib+"</h2>"+
                    "<span>"+
                        "<button type='button' class='btn btn-fefault cart' id='btn_re_ajouter'>"+
                           " <i class='fa fa-shopping-cart'></i>"+
                           " Ajouter au panier"+
                        "</button>"+
                    "</span>"+
                    "<div class='table-responsive cart_info'>"+
                        "<table class='table table-condensed'>"+
                            "<thead>"+
                                "<tr class='cart_menu'>"+
                                    "<td><h4>Ingr&#xE9;dients</h4></td>"+
                                    "<td><h4>Quantit&#xE9</h4></td>"+
                                    "<td><h4>Produit Recommand&#xE9;</h4></td>"+         
                                "</tr>"+
                            "</thead>"+
                            "<tbody>" + 
                                text + 
                            "</tbody>" +
                        "</table>"+
                    "</div>"+
                "</div>" +      
            "</div>"+
            
            "<div class='col-sm-12'><!--/recette-information-->"+
                "<br/>"+
                "<h2 class='title text-center'>Recette</h2>"+
                "<p style='text-align:justify'>"+moyenRec+"</p>"+
            "</div>");
}

function ajouterRecette(){
    var result = confirm("Vous voulez ajouter cette recette au panier ?");

    if (result){
        // Objet XMLHttpRequest.
        var xhr = new XMLHttpRequest();

        // Requ?te au serveur avec les param?tres ?ventuels.
        xhr.open("GET", "ServletDetailRecette?method=ajouter");

        // On pr?cise ce que l'on va faire quand on aura re?u la r?ponse du serveur.
        xhr.onload = function () {
            // Si la requ?te http s'est bien pass?e.
            if (xhr.status === 200) {
                var result2 = alert("La recette est bien ajoute dans le panier");
                afficherQte();
            }
        };
        
    }
    // Envoie de la requ?te.
    xhr.send();
}

/**
 * Lancement apr?s le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {
    //document.getElementById("btn_re_ajouter").addEventListener("click", ajouterRecette);
});

                           