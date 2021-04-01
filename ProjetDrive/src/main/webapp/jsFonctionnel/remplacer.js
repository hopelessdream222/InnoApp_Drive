/**
 * Cette methode affiche les details de la page d'acceuil.
 *
 * On utilise la propriete 'responseText' de l'objet XMLHttpRequest afin
 * de recupurer sous forme de texte le flux envoye par le serveur.
 */

window.onload = afficheDetail();
function afficheDetail() {
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requete au serveur
    xhr.open("GET", "ServletRemplacerProd");

    // On precise ce que l'on va faire quand on aura recu la reponse du serveur.
    xhr.onload = function () {
        // Si la requete http s'est bien passee.
        if (xhr.status === 200) {
            console.log("reussi");
            
            //  Obtenir les infos Client
            if (xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue === "horsConnection") {
            } else {
                var elt2 = document.getElementById("connexion");
                elt2.innerHTML = "Bienvenue! " + xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue;
                afficherQte();
            }
            
            // Obtenir les infos d'un produit
            var elt = document.getElementById("prod_ou_sonDetail");
            elt.innerHTML = "<h2 class='title text-center' id='nosProds'>NOS PRODUITS</h2>" +
                    "<div id='produitsTous'>";

            for (var i = 0; i < xhr.responseXML.getElementsByTagName("src").length; i++) {
                var src = xhr.responseXML.getElementsByTagName("src")[i].firstChild.nodeValue;
                var prixUniteProd = xhr.responseXML.getElementsByTagName("prixUniteProd")[i].firstChild.nodeValue;
                var libProd = xhr.responseXML.getElementsByTagName("libProd")[i].firstChild.nodeValue;
                var idProd = xhr.responseXML.getElementsByTagName("idProd")[i].firstChild.nodeValue;
                var promoProd = xhr.responseXML.getElementsByTagName("promotionProd")[i].firstChild.nodeValue;
                var text = creerModuleProduit(i, src, prixUniteProd, libProd, idProd, promoProd);
                // Elï¿½ment html que l'on va mettre ï¿½ jour.
                elt.insertAdjacentHTML("beforeend", text);
            }
            elt.insertAdjacentHTML("beforeend", "</div>");
            
            // Quand on clique sur le bouton Ajouter au panier
            var qte1 = 1;
            for (var i = 0; i < xhr.responseXML.getElementsByTagName("src").length; i++) {
                var idProd = xhr.responseXML.getElementsByTagName("idProd")[i].firstChild.nodeValue;
                document.getElementById("btn_ajouter" + idProd).addEventListener("click", function () {
                    ajouter(qte1);
                });
                document.getElementById("btn_detail" + idProd).addEventListener("click", plusDetail);
            }
        }
    };

    // Envoie de la requete.
    xhr.send();
}

// Module pour afficher chaque produit
function creerModuleProduit(i, src, prixUniteProd, libProd, idProd, promo) {
    if (promo === "nonpromotion") {
        var imgPromo = "";
    } else {
        var imgPromo = "<img src='image/promo.png' class='new' alt='' />";
    }
    return ("<div class='col-sm-3'>"
            + "<div class='product-image-wrapper'>"
            + "<div class='single-products'>"
            + "<div class='productinfo text-center'>"
            + "<div id='image" + i + "'><img src='" + src + "' width=200px hight=150px>"
            + "<h2>Prix Unitaire: " + prixUniteProd + "&#0128</h2>"
            + "<div height='50px'><p>" + libProd + "</p></div></div>"
            + "</div>"
            + "<div class='product-overlay'>"
            + "<div class='overlay-content'>"
            + "<a href='#' class='btn btn-default add-to-cart' name='" + idProd + "' id='btn_ajouter" + idProd + "'><i class='fa fa-shopping-cart'></i>Ajouter au panier</a>"
            + "</div>"
            + "</div>"
            + imgPromo
            + "</div>"
            + "</div>"
            + "</div>");
}

// Afficher la quantite de produits dans le panier
function afficherQte() {
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "ServletAfficherNb");

    //On precise ce que l'on va faire quand on aura reçu la réponse du serveur.
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


// Ajouter le produit dans le panier
function ajouter(q) {
    console.log("qte"+q);
    var result = confirm("Vous voulez l'ajouter au panier ?");

    if (result) {
        // Objet XMLHttpRequest.
        var xhr = new XMLHttpRequest();
        // Requï¿½te au serveur avec les paramï¿½tres ï¿½ventuels.
        var produitchoisi = event.srcElement.name;

        console.log("produit" + produitchoisi);

        xhr.open("GET", "ServletAccueil?method=ajouterPanier&idP=" + produitchoisi + "&qte=" + q, true);

        // On prï¿½cise ce que l'on va faire quand on aura reï¿½u la rï¿½ponse du serveur.
        xhr.onload = function () {
            // Si la requï¿½te http s'est bien passï¿½e.
            if (xhr.status === 200) {
                var result2 = alert("Le produit est bien ajoute dans le panier");
                afficherQte();
            }
        };
    }
    // Envoie de la requï¿½te.
    xhr.send();
}

// Obtenir les details d'un produit
function plusDetail() {
    //rï¿½cupï¿½rer id de produit choisi
    var idProd = event.srcElement.name;
    console.log("id p choisi:" + idProd);

    //vider la partie ï¿½ droite : Nos Produit
    var eltRight = document.getElementById("prod_ou_sonDetail");

    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();

    xhr.open("GET", "ServletRechercheProd?idProd=" + idProd, true);
    xhr.onload = function () {
        // Si la requï¿½te http s'est bien passï¿½e.
        if (xhr.status === 200) {
            //obtenir le tableau de label
            var tabLabel = xhr.responseXML.getElementsByTagName("srcLabel");

            //dï¿½terminer si ce produit possï¿½de des labels ou pas
            var srcLabel = "";
            if (tabLabel[0].firstChild.nodeValue === "nonlabel") {
                // bu deng yu
            } else {
                for (j = 0; j < tabLabel.length; j++) {
                    srcLabel = srcLabel + "<img src='" + tabLabel[j].firstChild.nodeValue + "' alt='' width='80px' height='80px'/>";
                }
            }
            console.log(srcLabel);
            //determiner si ce produit possede le nuriScore ou pas
            var srcNS = "";
            if (xhr.responseXML.getElementsByTagName("srcNutriScore")[0].firstChild.nodeValue === "nonNS") {
                // bu deng yu
                console.log("pas de NS");
            } else {
                srcNS = "<img src='" + xhr.responseXML.getElementsByTagName("srcNutriScore")[0].firstChild.nodeValue + "' alt='' width='100px' height='60px'/>";
                console.log("NS " + srcNS);
            }
            //promotion
            var promotion = "";
            var logoPromo = "";
            var pourcent = xhr.responseXML.getElementsByTagName("promotionProd")[0].firstChild.nodeValue;
            if (pourcent !== "nonpromotion") {
                promotion = "<div class='promo'>PROMO : " + pourcent + "</div><br/>";
                logoPromo = "<img src='image/logopromo.jpg' class='newarrival' alt='' width='60px' height='60px'/>";
            }
            //composition
            var compo = "";
            if (xhr.responseXML.getElementsByTagName("compositionProd")[0].firstChild.nodeValue !== "noncomposition") {
                console.log(xhr.responseXML.getElementsByTagName("compositionProd")[0].firstChild.nodeValue);
                compo = "<p><b>Composition:</b> " + xhr.responseXML.getElementsByTagName("compositionProd")[0].firstChild.nodeValue + "</p>";
            }
            //taille ref
            var tailleRef = "";
            if (xhr.responseXML.getElementsByTagName("tailleProd")[0].firstChild.nodeValue !== "nontaille") {
                console.log(xhr.responseXML.getElementsByTagName("compositionProd")[0].firstChild.nodeValue !== "nontaille");
                tailleRef = "<p><b>Taille de r&#xE9;f&#xE9;rence:</b> " + xhr.responseXML.getElementsByTagName("tailleProd")[0].firstChild.nodeValue + "</p>";
            }
            xhr.responseXML.getElementsByTagName("promotionProd")[0].firstChild.nodeValue
            var txt = "<div class='product-details'><!--product-details-->" +
                    "<div class='col-sm-5'>" +
                    "<div class='view-product'>" +
                    "<img src='" + xhr.responseXML.getElementsByTagName("src")[0].firstChild.nodeValue + "' alt='' />" +
                    "</div>" +
                    "</div>" +
                    "<div class='col-sm-7'>" +
                    "<div class='product-information'><!--/product-information-->" +
                    logoPromo +
                    promotion +
                    "<h2>" + xhr.responseXML.getElementsByTagName("libProd")[0].firstChild.nodeValue + "</h2>" +
                    srcNS +
                    "<span>" +
                    "<span>" + xhr.responseXML.getElementsByTagName("prixUniteProd")[0].firstChild.nodeValue + "&#0128</span>" +
                    "<label>Quantity:</label>" +
                    "<input type='text' value='1' id='detail_qte'/>" +
                    "<button type='button' class='btn btn-fefault cart' name='" + xhr.responseXML.getElementsByTagName("idProd")[0].firstChild.nodeValue +
                    "' id='btn_detail_ajouter'>" +
                    "<i class='fa fa-shopping-cart'></i>" +
                    "Ajouter au panier" +
                    "</button>" +
                    "</span>" +
                    compo +
                    "<p><b>Condition:</b> " + xhr.responseXML.getElementsByTagName("condProd")[0].firstChild.nodeValue + "</p>" +
                    tailleRef +
                    "<div>" + srcLabel + "</div>" +
                    "</div><!--/product-information-->" +
                    "</div>" +
                    "</div><!--/product-details-->";
            eltRight.innerHTML = txt;
            document.getElementById("btn_detail_ajouter").addEventListener("click", function () {
                ajouter(document.getElementById("detail_qte").value);
            });

        }
    };

    // Envoie de la requete.
    xhr.send();

}


///**
// * Lancement aprï¿½s le chargement du DOM.
// */
//document.addEventListener("DOMContentLoaded", () => {
//    document.getElementById("btnRechercher").addEventListener("click", rechercher);
//});
