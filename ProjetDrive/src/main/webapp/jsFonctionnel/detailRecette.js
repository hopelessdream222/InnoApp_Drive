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
    xhr.open("GET", "ServletDetailRecette");

    // On pr?cise ce que l'on va faire quand on aura re?u la r?ponse du serveur.
    xhr.onload = function () {
        // Si la requ?te http s'est bien pass?e.
        if (xhr.status === 200) {
            elt = document.getElementById("re_detail");
   
            elt.innerHTML = "<div class='col-sm-5'>"+
                                "<div class='view-product'>"+
                                    "<img src='"+xhr.responseXML.getElementsByTagName("src")[i].firstChild.nodeValue+"' alt='' />"+
                                "</div>"+
                            "</div>"+
                            "<div class='col-sm-7'>"+
                                "<div class='product-information'><!--/product-information-->"+
                                "<h2>"+xhr.responseXML.getElementsByTagName("nomRecette")[i].firstChild.nodeValue+"</h2>"+
                                "<span>"+
                                    "<button type='button' class='btn btn-fefault cart' id='btn_re_ajouter'>"+
                                    "<i class='fa fa-shopping-cart'></i>"+
                                    "Ajouter au panier"+
                                    "</button>"+
                                "</span>"
                                "<div class='table-responsive cart_info'>"+
                                    "<table class='table table-condensed'>"+
                                        "<thead>"+
                                            "<tr class='cart_menu'>"+
                                                "<td><h4>Ingr&#xE9;dients</h4></td>"+
                                                "<td><h4>Quantit&#xE9</h4></td>"+
                                                "<td><h4>Produit Recommand&#xE9;</h4></td>"+         
                                            "</tr>"+
                                        "</thead>"+
                                        "<tbody>";
            for (var i = 0; i < xhr.responseXML.getElementsByTagName("ingLib").length; i++) {
                var ing = xhr.responseXML.getElementsByTagName("ingLib")[i].firstChild.nodeValue;
                var qte = xhr.responseXML.getElementsByTagName("qte")[i].firstChild.nodeValue;
                var prodLib = xhr.responseXML.getElementsByTagName("prodLib")[i].firstChild.nodeValue;
                elt.insertAdjacentHTML("beforeend","<tr>"+
                                                    "<td><p>"+ing+"</p></td>"+
                                                    "<td><p>"+qte+"</p></td>"+
                                                    "<td><<p>"+prodLib+"</p></td>"+
                                                    "</tr>");
                console.log(ing+"----"+qte+"----"+prodLib);
            }
            elt.insertAdjacentHTML("beforeend","</tbody></table></div></div></div>");

            //Client
            if (xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue === "horsConnection") {
            } else {
                var elt2 = document.getElementById("connexion");
                elt2.innerHTML = "Bienvenue! " + xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue;
                //elt2.insertAdjacentHTML("afterbegin",xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue);
            }
          
        }
    };

    // Envoie de la requ?te.
    xhr.send();
}

function ajouterRecette(){
    console.log("ajouterRecette"),
}

/**
 * Lancement apr?s le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("btn_re_ajouter").addEventListener("click", ajouterRecette);
});





