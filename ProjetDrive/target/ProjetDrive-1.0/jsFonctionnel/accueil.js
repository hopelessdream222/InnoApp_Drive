/**
 * Cette m�thode affiche les details de la page d'acceuil.
 *
 * On utilise la propri�t� 'responseText' de l'objet XMLHttpRequest afin
 * de r�cup�rer sous forme de texte le flux envoy� par le serveur.
 */

window.onload = afficheDetail();
function afficheDetail() {
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requ�te au serveur avec les param�tres �ventuels.
    xhr.open("GET", "ServletDetailProd");

    // On pr�cise ce que l'on va faire quand on aura re�u la r�ponse du serveur.
    xhr.onload = function () {
        // Si la requ�te http s'est bien pass�e.
        if (xhr.status === 200) {
            console.log("reussi");
            var elt = document.getElementById("prod_ou_sonDetail");
            elt.innerHTML = "<h2 class='title text-center' id='nosProds'>NOS PRODUITS</h2>"+
                            "<div id='produitsTous'>";
            for (var i = 0; i < xhr.responseXML.getElementsByTagName("src").length; i++) {
                var src = xhr.responseXML.getElementsByTagName("src")[i].firstChild.nodeValue;
                var prixUniteProd = xhr.responseXML.getElementsByTagName("prixUniteProd")[i].firstChild.nodeValue;
                var libProd = xhr.responseXML.getElementsByTagName("libProd")[i].firstChild.nodeValue;
                var idProd = xhr.responseXML.getElementsByTagName("idProd")[i].firstChild.nodeValue;
                var promoProd = xhr.responseXML.getElementsByTagName("promotionProd")[i].firstChild.nodeValue;
                var text = creerModuleProduit(i, src, prixUniteProd, libProd, idProd, promoProd);
                // El�ment html que l'on va mettre � jour.
                elt.insertAdjacentHTML("beforeend",text);
            }
            elt.insertAdjacentHTML("beforeend","</div>");
            //var qte1 = 1;
            for (var i = 0; i < xhr.responseXML.getElementsByTagName("src").length; i++) {
                var idProd = xhr.responseXML.getElementsByTagName("idProd")[i].firstChild.nodeValue;
                document.getElementById("btn_ajouter" + idProd).addEventListener("click", function(){ajouter(1);});
                document.getElementById("btn_detail"+idProd).addEventListener("click", plusDetail);
            }

            //Client
            if (xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue === "horsConnection") {
            } else {
                var elt2 = document.getElementById("connexion");
                elt2.innerHTML = "Bienvenue! " + xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue;
                //elt2.insertAdjacentHTML("afterbegin",xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue);
            }

            var elt3 = document.getElementById("accordian");

            for (var x = 0; x < xhr.responseXML.getElementsByTagName("rayonProd").length; x++) {
                // El�ment html que l'on va mettre � jour.
                var rayonId = xhr.responseXML.getElementsByTagName("rayonId")[x].firstChild.nodeValue;
                //elt3.insertAdjacentHTML("beforeend","<div name='lien' id='"+ xhr.responseXML.getElementsByTagName("rayonProd")[x].firstChild.nodeValue +"'>"+xhr.responseXML.getElementsByTagName("rayonProd")[x].firstChild.nodeValue+"</div><br/>");
                var str = "<div class='panel panel-default'>" +
                        "<div class='panel-heading' id='rayon_" + rayonId + "'>" +
                        "<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordian' href='#rayon" + rayonId + "' name='lien' id='" + rayonId + "'><span class='badge pull-right'><i class='fa fa-plus'></i></span>" + xhr.responseXML.getElementsByTagName("rayonProd")[x].firstChild.nodeValue + "</a></h4>" +
                        "</div>" +
                        "</div>";
                elt3.insertAdjacentHTML("beforeend", str);
                afficherCategories(xhr.responseXML.getElementsByTagName("rayonId")[x].firstChild.nodeValue);

            }
            console.log("reussi------");

            var elt4 = document.getElementById("recette_accueil");
            var elt5 = document.getElementById("recette_accueil_2");
            elt4.innerHTML="";
            elt5.innerHTML="";
            for (var y = 0; y < xhr.responseXML.getElementsByTagName("recetteId").length; y++) {
                // El�ment html que l'on va mettre � jour.
                var recetteId = xhr.responseXML.getElementsByTagName("recetteId")[y].firstChild.nodeValue;
                var recetteSrc = xhr.responseXML.getElementsByTagName("recetteSrc")[y].firstChild.nodeValue;
                var recetteLib = xhr.responseXML.getElementsByTagName("recetteNom")[y].firstChild.nodeValue;
                //elt3.insertAdjacentHTML("beforeend","<div name='lien' id='"+ xhr.responseXML.getElementsByTagName("rayonProd")[x].firstChild.nodeValue +"'>"+xhr.responseXML.getElementsByTagName("rayonProd")[x].firstChild.nodeValue+"</div><br/>");
                var text2 = creerModuleRecette(recetteId, recetteSrc, recetteLib) ;
                elt4.insertAdjacentHTML("beforeend", text2);
                elt5.insertAdjacentHTML("beforeend", text2);
            }

            for (var z = 0; z < xhr.responseXML.getElementsByTagName("recetteId").length; z++) {
                var idRe = xhr.responseXML.getElementsByTagName("recetteId")[z].firstChild.nodeValue;
                document.getElementById("btn_voir_detail" + idRe).addEventListener("click", allerDetailRecette);
            }
        }
    };

    // Envoie de la requ�te.
    xhr.send();
}

function creerModuleRecette(recetteId, recetteSrc, recetteLib) {
    return ("<div class='col-sm-4'>"
                +"<div class='product-image-wrapper'>"
                    +"<div class='single-products'>"
                        +"<div class='productinfo text-center'>"
                            +"<img src='"+recetteSrc+"' alt='' />"
                            +"<div>"+recetteId+"</div>"
                            +"<p>"+recetteLib+"</p>"
                            +"<a href='#' class='btn btn-default add-to-cart' name='" + recetteId + "' id='btn_voir_detail" + recetteId + "'><i class='fa fa-plus-square'></i>Plus de D&#xE9;tail</a>"
                        +"</div>"

                    +"</div>"
                +"</div>"
            +"</div>");
}

function creerModuleProduit(i, src, prixUniteProd, libProd, idProd,promo) {
    if(promo === "nonpromotion"){
        var imgPromo = "";
    }else{
        var imgPromo = "<img src='image/promo.png' class='new' alt='' />";
    }
    return ("<div class='col-sm-4'>"
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
            + "<div class='choose'>"
            + "<ul class='av nav-pills nav-justified'>"
            + "<li><a href='#' name='"+idProd+"' id='btn_detail" + idProd + "'><i class='fa fa-plus-square'></i>Plus de D&#xE9;tail</a></li>"
            + "</ul>"
            + "</div>"
            + "</div>"
            + "</div>");
}

function allerDetailRecette (){
	// Objet XMLHttpRequest.
	var xhr = new XMLHttpRequest();

        var idRecette = event.srcElement.name;

	// Requête au serveur avec les paramètres éventuels.
	xhr.open("GET","ServletChoisirRecette?idRecette="+idRecette);

	// On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
	xhr.onload = function(){
            // Si la requête http s'est bien passée.
            if (xhr.status === 200){
                alert("guoqule!");
                window.location.href="DetailRecette";

            }
	};

	// Envoie de la requête.
	xhr.send();
	}

function afficherCategories(rayonChoisi) {
    //this get VALUE

    var xhr = new XMLHttpRequest();

    xhr.open("GET", "ServletRechercheCate?rayon=" + rayonChoisi);
    //alert(rayonChoisi);
    // On pr�cise ce que l'on va faire quand on aura re�u la r�ponse du serveur.
    xhr.onload = function () {
        // Si la requ�te http s'est bien pass�e.
        if (xhr.status === 200) {
            //alert(" cat 200");
            var elt = document.getElementById("rayon_" + rayonChoisi);
//            elt.insertAdjacentHTML("afterend","</ul></div></div>");
            var tab = xhr.responseXML.getElementsByTagName("categorie");

            var text = "<div id='rayon" + rayonChoisi + "' class='panel-collapse collapse'><div class='panel-body'><ul>";

            for (var i = 0; i < tab.length; i++) {
                var categorieId = xhr.responseXML.getElementsByTagName("categorieId")[i].firstChild.nodeValue;
//                elt.insertAdjacentHTML("afterend","<li><a href='#'>"+tab[i].firstChild.nodeValue+"</a></li>");
                text = text + "<li><a href='#' name='lienCate' value='" + categorieId + "' id='" + categorieId + "'>" + tab[i].firstChild.nodeValue + "</a></li>";

            }
//            elt.insertAdjacentHTML("afterend","<div id='sportswear' class='panel-collapse collapse'><div class='panel-body'><ul>");
            elt.insertAdjacentHTML("afterend", text + "</ul></div></div>");

            var elt4 = document.getElementsByName("lienCate");
            for (j = 0; j < elt4.length; j++) {
                elt4[j].addEventListener("click", afficherProduits);
            }

        }
    };

    // Envoie de la requ�te.
    xhr.send();
}

function afficherProduits() {

    var xhr = new XMLHttpRequest();
    var cateChoisi = event.srcElement.id;
    console.log("categorie:"+cateChoisi);
    xhr.open("GET", "ServletRechercheProdUnCate?cateId=" + cateChoisi);

    // On pr�cise ce que l'on va faire quand on aura re�u la r�ponse du serveur.
    xhr.onload = function () {
        // Si la requ�te http s'est bien pass�e.
        if (xhr.status === 200) {
            var elt2 = document.getElementById("prod_ou_sonDetail");
            elt2.innerHTML = "<h2 class='title text-center' id='nosProds'>NOS PRODUITS</h2>"+
                            "<div id='produitsTous'>";
            //var elt = document.getElementById("nosProds");
            for (var i = 0; i < xhr.responseXML.getElementsByTagName("src").length; i++) {
                var src = xhr.responseXML.getElementsByTagName("src")[i].firstChild.nodeValue;
                var prixUniteProd = xhr.responseXML.getElementsByTagName("prixUniteProd")[i].firstChild.nodeValue;
                var libProd = xhr.responseXML.getElementsByTagName("libProd")[i].firstChild.nodeValue;
                var idProd = xhr.responseXML.getElementsByTagName("idProd")[i].firstChild.nodeValue;
                var promoProd = xhr.responseXML.getElementsByTagName("promotionProd")[i].firstChild.nodeValue;
                var text = creerModuleProduit(i, src, prixUniteProd, libProd, idProd, promoProd);
                // El�ment html que l'on va mettre � jour.
                elt2.insertAdjacentHTML("beforeend", text);
            }
            elt2.insertAdjacentHTML("beforeend","</div>");
            //var qte1 = 1;
            for (var i = 1; i <= xhr.responseXML.getElementsByTagName("src").length; i++) {
                var idProd = xhr.responseXML.getElementsByTagName("idProd")[i - 1].firstChild.nodeValue;
                document.getElementById("btn_ajouter" + idProd).addEventListener("click", function(){ajouter(1);});
                document.getElementById("btn_detail"+idProd).addEventListener("click", plusDetail);
            }

        }
    };

    // Envoie de la requ�te.
    xhr.send();
}

function ajouter(q) {
    console.log("qte"+q);
    var result = confirm("Vous voulez l'ajouter au panier ?");

    if (result) {
        // Objet XMLHttpRequest.
        var xhr = new XMLHttpRequest();
        // Requ�te au serveur avec les param�tres �ventuels.
        var produitchoisi = event.srcElement.name;

        console.log("produit" + produitchoisi);

        xhr.open("GET", "ServletAjouterPanier?idP=" + produitchoisi + "&qte=" + q, true);

        // On pr�cise ce que l'on va faire quand on aura re�u la r�ponse du serveur.
        xhr.onload = function () {
            // Si la requ�te http s'est bien pass�e.
            if (xhr.status === 200) {
                var result2 = alert("Le produit est bien ajoute dans le panier");
            }
        };
    }
    // Envoie de la requ�te.
    xhr.send();
}

function rechercher() {
    //alert("123");
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    var nomProd = document.getElementById("zonSaisi").value;
    if (nomProd === "") {
        alert("Veuillez saisir un produit");
    } else {
        // Requ�te au serveur avec les param�tres �ventuels.
        xhr.open("GET", "ServletAffichageProd?nomProd=" + nomProd);
        var elt2 = document.getElementById("prod_ou_sonDetail");
        // On pr�cise ce que l'on va faire quand on aura re�u la r�ponse du serveur.
        xhr.onload = function () {
            // Si la requ�te http s'est bien pass�e.
            if (xhr.status === 200) {
                var elt2 = document.getElementById("prod_ou_sonDetail");
                if (xhr.responseXML.getElementsByTagName("res")[0].firstChild.nodeValue === "reussi") {
                    console.log("Trouv�!");
                    //Modification page

                    elt2.innerHTML = "<h2 class='title text-center' id='nosProds'>NOS PRODUITS</h2>"+
                                "<div id='produitsTous'>";
                    //var elt = document.getElementById("nosProds");
                    for (var i = 0; i < xhr.responseXML.getElementsByTagName("src").length; i++) {
                        var src = xhr.responseXML.getElementsByTagName("src")[i].firstChild.nodeValue;
                        var prixUniteProd = xhr.responseXML.getElementsByTagName("prixUniteProd")[i].firstChild.nodeValue;
                        var libProd = xhr.responseXML.getElementsByTagName("libProd")[i].firstChild.nodeValue;
                        var idProd = xhr.responseXML.getElementsByTagName("idProd")[i].firstChild.nodeValue;
                        var promoProd = xhr.responseXML.getElementsByTagName("promotionProd")[i].firstChild.nodeValue;
                        var text = creerModuleProduit(i, src, prixUniteProd, libProd, idProd, promoProd);
                        // El�ment html que l'on va mettre � jour.
                        elt2.insertAdjacentHTML("beforeend", text);
                    }
                    elt2.insertAdjacentHTML("beforeend","</div>");
                    //var qte1 = 1;
                    for (var i = 0; i < xhr.responseXML.getElementsByTagName("src").length; i++) {
                        var idProd = xhr.responseXML.getElementsByTagName("idProd")[i].firstChild.nodeValue;
                        document.getElementById("btn_ajouter" + idProd).addEventListener("click", function(){ajouter(1);});
                        document.getElementById("btn_detail"+idProd).addEventListener("click", plusDetail);
                    }

                } else {
                    alert("Oups! Aucun r�sultat! ");
                    document.getElementById("zonSaisi").innerHTML = "";
                    elt2.innerHTML = "<h2 class='title text-center' id='nosProds'>NOS PRODUITS</h2>"+
                                "<div id='produitsTous'></div>";
                }
            }
        };
    }
    // Envoie de la requ�te.
    xhr.send();
}

function plusDetail() {
    //r�cup�rer id de produit choisi
    var idProd = event.srcElement.name;
    console.log("id p choisi:" + idProd);

    //vider la partie � droite : Nos Produit
    var eltRight = document.getElementById("prod_ou_sonDetail");

    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();

    xhr.open("GET", "ServletRechercheProd?idProd=" + idProd, true);
    xhr.onload = function () {
        // Si la requ�te http s'est bien pass�e.
        if (xhr.status === 200) {
            //obtenir le tableau de label
            var tabLabel = xhr.responseXML.getElementsByTagName("srcLabel");

            //d�terminer si ce produit poss�de des labels ou pas
            var srcLabel = "";
            if (tabLabel[0].firstChild.nodeValue === "nonlabel") {
                // bu deng yu
            } else {
                for (j = 0; j < tabLabel.length; j++) {
                    srcLabel = srcLabel + "<img src='" +tabLabel[j].firstChild.nodeValue + "' alt='' width='80px' height='80px'/>";
                }
            }
            console.log(srcLabel);
            //d�terminer si ce produit poss�de le nuriScore ou pas
            var srcNS = "";
            if (xhr.responseXML.getElementsByTagName("srcNutriScore")[0].firstChild.nodeValue === "nonNS") {
                // bu deng yu
                console.log("pas de NS");
            } else {
                srcNS = "<img src='" + xhr.responseXML.getElementsByTagName("srcNutriScore")[0].firstChild.nodeValue + "' alt='' width='100px' height='60px'/>";
                console.log("NS "+srcNS);
            }
            //promotion
            var promotion = "";
            var logoPromo = "";
            var pourcent = xhr.responseXML.getElementsByTagName("promotionProd")[0].firstChild.nodeValue;
            if( pourcent !== "nonpromotion"){
                promotion = "<div class='promo'>PROMO : "+ pourcent +"%</div><br/>";
                logoPromo = "<img src='image/logopromo.jpg' class='newarrival' alt='' width='60px' height='60px'/>";
            }
            //composition
            var compo = "";
            if(xhr.responseXML.getElementsByTagName("compositionProd")[0].firstChild.nodeValue !== "noncomposition"){
                console.log(xhr.responseXML.getElementsByTagName("compositionProd")[0].firstChild.nodeValue);
                compo = "<p><b>Composition:</b> " + xhr.responseXML.getElementsByTagName("compositionProd")[0].firstChild.nodeValue + "</p>";
            }
            //taille ref
            var tailleRef = "";
            if(xhr.responseXML.getElementsByTagName("tailleProd")[0].firstChild.nodeValue !== "nontaille"){
                console.log(xhr.responseXML.getElementsByTagName("compositionProd")[0].firstChild.nodeValue !== "nontaille");
                tailleRef = "<p><b>Taille de r&#xE9;f&#xE9;rence:</b> " + xhr.responseXML.getElementsByTagName("tailleProd")[0].firstChild.nodeValue + "</p>";
            }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           xhr.responseXML.getElementsByTagName("promotionProd")[0].firstChild.nodeValue
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
                    "<button type='button' class='btn btn-fefault cart' name='"+xhr.responseXML.getElementsByTagName("idProd")[0].firstChild.nodeValue+
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
            document.getElementById("btn_detail_ajouter").addEventListener("click",function(){ajouter(document.getElementById("detail_qte").value);});

        }
    };

    // Envoie de la requ�te.
    xhr.send();

}


/**
 * Lancement apr�s le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("btnRechercher").addEventListener("click", rechercher);
});
