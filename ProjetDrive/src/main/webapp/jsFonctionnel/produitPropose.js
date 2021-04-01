/**
 * Cette m?thode affiche les details de la page d'acceuil.
 *
 * On utilise la propri?t? 'responseText' de l'objet XMLHttpRequest afin
 * de r?cup?rer sous forme de texte le flux envoy? par le serveur.
 */

window.onload = afficheDetail();
function afficheDetail() {
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requ?te au serveur avec les param?tres ?ventuels.
    xhr.open("GET", "ServletProduitPropose?method=afficherProduits");

    // On pr?cise ce que l'on va faire quand on aura re?u la r?ponse du serveur.
    xhr.onload = function () {
        // Si la requ?te http s'est bien pass?e.
        if (xhr.status === 200) {
            afficherProduit();

            //Client
            if (xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue === "horsConnection") {
            } else {
                var elt2 = document.getElementById("connexion");
                elt2.innerHTML = "Bienvenue! " + xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue;
            }
        }
    };

    // Envoie de la requ?te.
    xhr.send();
}

function afficherProduit() {
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requ?te au serveur avec les param?tres ?ventuels.
    xhr.open("GET", "ServletProduitPropose?method=afficherProduits");

    // On pr?cise ce que l'on va faire quand on aura re?u la r?ponse du serveur.
    xhr.onload = function () {
        // Si la requ?te http s'est bien pass?e.
        if (xhr.status === 200) {
            console.log("reussi");
            var elt = document.getElementById("produitsTous");
            elt.innerHTML = "";
            for (var i = 0; i < xhr.responseXML.getElementsByTagName("src").length; i++) {
                var src = xhr.responseXML.getElementsByTagName("src")[i].firstChild.nodeValue;
                var prixUniteProd = xhr.responseXML.getElementsByTagName("prixUniteProd")[i].firstChild.nodeValue;
                var libProd = xhr.responseXML.getElementsByTagName("libProd")[i].firstChild.nodeValue;
                var idProd = xhr.responseXML.getElementsByTagName("idProd")[i].firstChild.nodeValue;
                var promoProd = xhr.responseXML.getElementsByTagName("promotionProd")[i].firstChild.nodeValue;
                var text = creerModuleProduit(i, src, prixUniteProd, libProd, idProd, promoProd);
                // El?ment html que l'on va mettre ? jour.
                elt.insertAdjacentHTML("beforeend", text);
            }
            
            //var qte1 = 1;
            for (var i = 0; i < xhr.responseXML.getElementsByTagName("src").length; i++) {
                var idProd = xhr.responseXML.getElementsByTagName("idProd")[i].firstChild.nodeValue;
                document.getElementById("btn_ajouter_liste" + idProd).addEventListener("click", ajouterALaliste);
            }

        }
    };

    // Envoie de la requ?te.
    xhr.send();
}

function creerModuleProduit(i, src, prixUniteProd, libProd, idProd, promo, prixPromo,srcLabel) {
    var infoPromo = " ";
    var promotion = " ";
    
    console.log(srcLabel);
    if(srcLabel === "nonlabel"){
        srcLabel = " ";
    }
    console.log(srcLabel);
    if(promo === "nonpromotion"){
        var imgPromo = "";
        var pu = prixUniteProd+"&#0128";
    }else{
        var imgPromo = "<img src='image/promo.png' class='new' alt='' />";
        var pu = "<s>" + prixUniteProd + "&#0128</s>";
        infoPromo = promo;
        if(prixPromo !== prixUniteProd){
            var promotion = prixPromo+"&#0128";
        }       
    }
    return ("<div class='col-sm-4'>"
            + "<div class='product-image-wrapper'>"
            + "<div class='single-products'>"
            + "<div class='productinfo text-center'>"
            + "<div id='image" + i + "'><img src='" + src + "' width=200px hight=150px>"
//            + "<span>"+srcLabel+"</span>"
            + "<p style='color:red;'>"+infoPromo+"</p>"
            + "<h2>PU: " + pu +" "+ promotion +"</h2>"
            + "<div height='50px'><p>" + libProd + "</p></div></div>"
            + "</div>"
            + "<div class='product-overlay'>"
            + "<div class='overlay-content'>"
            + "<a href='#' class='btn btn-default add-to-cart' name='" + idProd + "' id='btn_ajouter" + idProd + "'><i class='fa fa-shopping-cart'></i>Ajouter au panier</a>"
            + "</div>"
            + "</div>"
            + imgPromo
            + "</div>"
            +"<div>"+srcLabel+"</div>"
            + "</div>"
            + "</div>");
}


function ajouterALaliste() {
    alert(event.srcElement.name);
    //récupérer le nom de la liste
    //---------------------------chuan idPost------------------------------
    var idPro = event.srcElement.name;
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requï¿½te au serveur avec les paramï¿½tres ï¿½ventuels.
    xhr.open("GET", "ServletProduitPropose?method=ajouterProduit&idPro=" + idPro);
    //alert(idLst);
    // On prï¿½cise ce que l'on va faire quand on aura reï¿½u la rï¿½ponse du serveur.
    xhr.onload = function () {
        // Si la requï¿½te http s'est bien passï¿½e.
        if (xhr.status === 200) {
            alert("Ajoute réussi!");
            afficherProduit();
        }
    };
    xhr.send();
}





