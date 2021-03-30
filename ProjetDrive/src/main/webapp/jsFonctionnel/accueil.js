/**
 * Cette mï¿½thode affiche les details de la page d'acceuil.
 *
 * On utilise la propriï¿½tï¿½ 'responseText' de l'objet XMLHttpRequest afin
 * de rï¿½cupï¿½rer sous forme de texte le flux envoyï¿½ par le serveur.
 */

window.onload = afficheDetail();
function afficheDetail() {
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requï¿½te au serveur avec les paramï¿½tres ï¿½ventuels.
    //xhr.open("GET", "ServletDetailProd");
    xhr.open("GET", "ServletAccueil?method=afficherAccueil");

    // On prï¿½cise ce que l'on va faire quand on aura reï¿½u la rï¿½ponse du serveur.
    xhr.onload = function () {
        // Si la requï¿½te http s'est bien passï¿½e.
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
                var prixPromo = xhr.responseXML.getElementsByTagName("prixPromo")[i].firstChild.nodeValue;
                var tabLabel = xhr.responseXML.getElementsByTagName("label")[i];
                //determiner si ce produit possede des labels ou pas
                var srcLabel = "nonlabel";
                if (tabLabel.getElementsByTagName("srcLabel")[0].firstChild.nodeValue !== "nonlabel") {
                    for (j = 0; j < tabLabel.getElementsByTagName("srcLabel").length; j++) {
                        srcLabel = "";
                        srcLabel = srcLabel + "<img src='" +tabLabel.getElementsByTagName("srcLabel")[j].firstChild.nodeValue + "' width='50px' height='50px'/>";
                    }
                }
                
                var text = creerModuleProduit(i, src, prixUniteProd, libProd, idProd, promoProd,prixPromo,srcLabel);
                // Elï¿½ment html que l'on va mettre ï¿½ jour.
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
                afficherQte();
            }

            var elt3 = document.getElementById("accordian");

            for (var x = 0; x < xhr.responseXML.getElementsByTagName("rayonProd").length; x++) {
                // Elï¿½ment html que l'on va mettre ï¿½ jour.
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
            for (var y = 0; y < 3; y++) {
                // Elï¿½ment html que l'on va mettre ï¿½ jour.
                var recetteId = xhr.responseXML.getElementsByTagName("recetteId")[y].firstChild.nodeValue;
                console.log(recetteId+"---id re");
                var recetteSrc = xhr.responseXML.getElementsByTagName("recetteSrc")[y].firstChild.nodeValue;
                var recetteLib = xhr.responseXML.getElementsByTagName("recetteNom")[y].firstChild.nodeValue;
                var text2 = creerModuleRecette(recetteId, recetteSrc, recetteLib) ;
                elt4.insertAdjacentHTML("beforeend", text2);
            }
            
            for (var z = 3; z < 6; z++) {
                // Elï¿½ment html que l'on va mettre ï¿½ jour.
                var recetteId = xhr.responseXML.getElementsByTagName("recetteId")[z].firstChild.nodeValue;
                console.log(recetteId+"---id re");
                var recetteSrc = xhr.responseXML.getElementsByTagName("recetteSrc")[z].firstChild.nodeValue;
                var recetteLib = xhr.responseXML.getElementsByTagName("recetteNom")[z].firstChild.nodeValue;
                var text3 = creerModuleRecette(recetteId, recetteSrc, recetteLib) ;              
                elt5.insertAdjacentHTML("beforeend", text3);
            }

            for (var z = 0; z < xhr.responseXML.getElementsByTagName("recetteId").length; z++) {
                var idRe = xhr.responseXML.getElementsByTagName("recetteId")[z].firstChild.nodeValue;
                document.getElementById("btn_voir_detail" + idRe).addEventListener("click", allerDetailRecette);    
            }
        }
    };

    // Envoie de la requï¿½te.
    xhr.send();
}

function creerModuleRecette(recetteId, recetteSrc, recetteLib) {
    return ("<div class='col-sm-4'>"
                +"<div class='product-image-wrapper'>"
                    +"<div class='single-products'>"
                        +"<div class='productinfo text-center'>"
                            +"<img src='"+recetteSrc+"' alt='' />"
                            //+"<div>"+recetteId+"</div>"
                            +"<p>"+recetteLib+"</p>"
                            +"<a href='#' class='btn btn-default add-to-cart' name='" + recetteId + "' id='btn_voir_detail" + recetteId + "'><i class='fa fa-plus-square'></i>Plus de D&#xE9;tail</a>"
                        +"</div>"

                    +"</div>"
                +"</div>"
            +"</div>");
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
        console.log(idRecette+"---id re aller");
	// RequÃªte au serveur avec les paramÃ¨tres Ã©ventuels.
	xhr.open("GET","ServletAccueil?method=ChoisirRecette&idRecette="+idRecette);

	// On prÃ©cise ce que l'on va faire quand on aura reÃ§u la rÃ©ponse du serveur.
	xhr.onload = function(){
            // Si la requÃªte http s'est bien passÃ©e.
            if (xhr.status === 200){
                console.log("guoqule!");
                window.location.href="DetailRecette";

            }
	};

	// Envoie de la requÃªte.
	xhr.send();
	}

function afficherCategories(rayonChoisi) {
    //this get VALUE

    var xhr = new XMLHttpRequest();

    xhr.open("GET", "ServletAccueil?method=rechercherCate&rayon=" + rayonChoisi);
    //alert(rayonChoisi);
    // On prï¿½cise ce que l'on va faire quand on aura reï¿½u la rï¿½ponse du serveur.
    xhr.onload = function () {
        // Si la requï¿½te http s'est bien passï¿½e.
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

    // Envoie de la requï¿½te.
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

function afficherProduits() {

    var xhr = new XMLHttpRequest();
    var cateChoisi = event.srcElement.id;
    console.log("categorie:"+cateChoisi);
    xhr.open("GET", "ServletAccueil?method=rechercherProdParCate&cateId=" + cateChoisi);

    // On prï¿½cise ce que l'on va faire quand on aura reï¿½u la rï¿½ponse du serveur.
    xhr.onload = function () {
        // Si la requï¿½te http s'est bien passï¿½e.
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
                var prixPromo = xhr.responseXML.getElementsByTagName("prixPromo")[i].firstChild.nodeValue;
                var tabLabel = xhr.responseXML.getElementsByTagName("label")[i];
                //determiner si ce produit possede des labels ou pas
                var srcLabel = "nonlabel";
                if (tabLabel.getElementsByTagName("srcLabel")[0].firstChild.nodeValue !== "nonlabel") {
                    for (j = 0; j < tabLabel.getElementsByTagName("srcLabel").length; j++) {
                        srcLabel = "";
                        srcLabel = srcLabel + "<img src='" +tabLabel.getElementsByTagName("srcLabel")[j].firstChild.nodeValue + "' width='50px' height='50px'/>";
                    }
                }
                
                var text = creerModuleProduit(i, src, prixUniteProd, libProd, idProd, promoProd,prixPromo,srcLabel);
                // Elï¿½ment html que l'on va mettre ï¿½ jour.
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

    // Envoie de la requï¿½te.
    xhr.send();
}

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

function rechercher() {
    //alert("123");
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    var nomProd = document.getElementById("zonSaisi").value;
    if (nomProd === "") {
        alert("Veuillez saisir un produit");
    } else {
        // Requï¿½te au serveur avec les paramï¿½tres ï¿½ventuels.
        xhr.open("GET", "ServletAccueil?method=afficherProdParRecherche&nomProd=" + nomProd);
        var elt2 = document.getElementById("prod_ou_sonDetail");
        // On prï¿½cise ce que l'on va faire quand on aura reï¿½u la rï¿½ponse du serveur.
        xhr.onload = function () {
            // Si la requï¿½te http s'est bien passï¿½e.
            if (xhr.status === 200) {
                var elt2 = document.getElementById("prod_ou_sonDetail");
                if (xhr.responseXML.getElementsByTagName("res")[0].firstChild.nodeValue === "reussi") {
                    console.log("Trouvé!");
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
                        var prixPromo = xhr.responseXML.getElementsByTagName("prixPromo")[i].firstChild.nodeValue;
                
                        var tabLabel = xhr.responseXML.getElementsByTagName("label")[i];
                        //determiner si ce produit possede des labels ou pas
                        var srcLabel = "nonlabel";
                        if (tabLabel.getElementsByTagName("srcLabel")[0].firstChild.nodeValue !== "nonlabel") {
                            for (j = 0; j < tabLabel.getElementsByTagName("srcLabel").length; j++) {
                                srcLabel = "";
                                srcLabel = srcLabel + "<img src='" +tabLabel.getElementsByTagName("srcLabel")[j].firstChild.nodeValue + "' width='50px' height='50px'/>";
                            }
                        }

                        var text = creerModuleProduit(i, src, prixUniteProd, libProd, idProd, promoProd,prixPromo,srcLabel);
                                // Elï¿½ment html que l'on va mettre ï¿½ jour.
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
                    alert("Oups! Aucun résultat! ");
                    document.getElementById("zonSaisi").innerHTML = "";
                    elt2.innerHTML = "<h2 class='title text-center' id='nosProds'>NOS PRODUITS</h2>"+
                                "<div id='produitsTous'></div>";
                }
            }
        };
    }
    // Envoie de la requï¿½te.
    xhr.send();
}

function plusDetail() {
    //rï¿½cupï¿½rer id de produit choisi
    var idProd = event.srcElement.name;
    console.log("id p choisi:" + idProd);

    //vider la partie ï¿½ droite : Nos Produit
    var eltRight = document.getElementById("prod_ou_sonDetail");

    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();

    xhr.open("GET", "ServletAccueil?method=plusDetail&idProd=" + idProd, true);
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
                    srcLabel = srcLabel + "<img src='" +tabLabel[j].firstChild.nodeValue + "' alt='' width='80px' height='80px'/>";
                }
            }
            console.log(srcLabel);
            //dï¿½terminer si ce produit possï¿½de le nuriScore ou pas
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
            
            var prixUniteProd = xhr.responseXML.getElementsByTagName("prixUniteProd")[0].firstChild.nodeValue+"&#0128";
            var infoPromo = xhr.responseXML.getElementsByTagName("promotionProd")[0].firstChild.nodeValue;
            var prixApresPromo = "";
            if( infoPromo !== "nonpromotion"){
                promotion = "<div class='promo'>"+ infoPromo +"</div><br/>";
                logoPromo = "<img src='image/logopromo.jpg' class='newarrival' alt='' width='60px' height='60px'/>";
                prixUniteProd = "<s>" + prixUniteProd + "</s>";
                var prixApresPromo = xhr.responseXML.getElementsByTagName("prixPromo")[0].firstChild.nodeValue+"&#0128";
        }       
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
            }  
            
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
                    "<h2>" +xhr.responseXML.getElementsByTagName("libProd")[0].firstChild.nodeValue+ "</h2>" +
                    srcNS +
                    "<br/><span><span>" +prixUniteProd +" "+ prixApresPromo+ "</span>" +
                    "<label>Quantit&#xE9;:</label>" +
                    "<input type='text' value='1' id='detail_qte'/>" +
                    "<button type='button' class='btn btn-fefault cart' name='"+xhr.responseXML.getElementsByTagName("idProd")[0].firstChild.nodeValue+
                            "' id='btn_detail_ajouter'>" +
                    "<i class='fa fa-shopping-cart'></i>" +
//                    "Ajouter" +
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

        };
    

    // Envoie de la requï¿½te.
    xhr.send();

}


/**
 * Lancement aprï¿½s le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("btnRechercher").addEventListener("click", rechercher);
});
