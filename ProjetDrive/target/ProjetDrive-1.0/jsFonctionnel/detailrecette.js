/**
 * Cette méthode affiche les details de la page d'acceuil.
 *
 * On utilise la propriété 'responseText' de l'objet XMLHttpRequest afin
 * de récupérer sous forme de texte le flux envoyé par le serveur.
 */

window.onload = afficheDetailRecette();
function afficheDetailRecette() {
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET", "ServletDetailRecette");

    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function () {
        // Si la requête http s'est bien passée.
        if (xhr.status === 200) {
            console.log("detail re 2°°");
            console.log("re src"+xhr.responseXML.getElementsByTagName("reSrc")[0].firstChild.nodeValue);
           
            elt = document.getElementById("re_detail");
            var reSrc = xhr.responseXML.getElementsByTagName("reSrc")[0].firstChild.nodeValue;
            var recetteLib = xhr.responseXML.getElementsByTagName("recetteLib")[0].firstChild.nodeValue;
            var text = "";
            for (var i = 0; i < xhr.responseXML.getElementsByTagName("ingLib").length; i++) {
                var ing = xhr.responseXML.getElementsByTagName("ingLib")[i].firstChild.nodeValue;
                var qte = xhr.responseXML.getElementsByTagName("qte")[i].firstChild.nodeValue;
                var mesure = xhr.responseXML.getElementsByTagName("mesure")[i].firstChild.nodeValue;
                var prodLib = xhr.responseXML.getElementsByTagName("prodLib")[i].firstChild.nodeValue;                
                console.log(ing+"----"+qte+"----"+prodLib);
                text = text + "<tr>"+
                                    "<td><p>"+ing+"</p></td>"+                                         
                                    "<td><p>"+qte +" "+ mesure +"</p></td>"+
                                    "<td><p>"+prodLib+"</p></td>"+
                                "</tr>";
                
            }
            var txt=creerModuleTable(reSrc,recetteLib,text);
            elt.innerHTML = txt;
            //elt.insertAdjacentHTML("beforeend","<div>product-information</div>");
            //Client
            if (xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue === "horsConnection") {
            } else {
                var elt2 = document.getElementById("connexion");
                elt2.innerHTML = "Bienvenue! " + xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue;
                //elt2.insertAdjacentHTML("afterbegin",xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue);
                afficherQte();
            }
            
        }
    };


    // Envoie de la requête.
    xhr.send();
}


function afficherQte() {
    console.log("zhixingle");
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();

        xhr.open("GET", "ServletAfficherNb");

        //On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
        xhr.onload = function () {
            // Si la requête http s'est bien passée.
            if (xhr.status === 200) {
                
                var quantitePanier = document.getElementById("cartcounter");
                //quantitePanier.innerHTML = 2;
                console.log(xhr.responseXML.getElementsByTagName("quantitePanier")[0].firstChild.nodeValue);
                quantitePanier.innerHTML = xhr.responseXML.getElementsByTagName("quantitePanier")[0].firstChild.nodeValue;
            }
        };
    
    // Envoie de la requête.
    xhr.send();
}

function creerModuleTable (reSrc,recetteLib, text){
    return("<div class='col-sm-6'>"+
                "<div class='view-product'>"+
                    "<img src='"+reSrc+"' alt='' /> "+
                "</div>"+
            "</div>"+
            "<div class='col-sm-6'>"+
                    "<div class='product-information'><!--/product-information-->"+
                        "<h2>"+recetteLib+"</h2>"+
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
                "</div>");
}

function ajouterRecette(){
    console.log("ajouter Re");
}

/**
 * Lancement après le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("btn_re_ajouter").addEventListener("click", ajouterRecette);
});

                           