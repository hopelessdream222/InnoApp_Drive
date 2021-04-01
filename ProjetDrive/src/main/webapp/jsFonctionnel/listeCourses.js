/**
 * Cette m?thode affiche les details de la page d'acceuil.
 *
 * On utilise la propri?t? 'responseText' de l'objet XMLHttpRequest afin
 * de r?cup?rer sous forme de texte le flux envoy? par le serveur.
 */

window.onload = afficheListe();
function afficheListe() {
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requ?te au serveur avec les param?tres ?ventuels.
    //xhr.open("GET", "ServletDetailProd");
    xhr.open("GET", "ServletListeCourses?method=afficherListeCourses");

    // On pr?cise ce que l'on va faire quand on aura re?u la r?ponse du serveur.
    xhr.onload = function () {
        // Si la requ?te http s'est bien pass?e.
        if (xhr.status === 200) {
            console.log("reussi");
            
            //Client
            if (xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue === "horsConnection") {
            } else {
                var elt = document.getElementById("connexion");
                elt.innerHTML = "Bienvenue! " + xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue;
                //elt.insertAdjacentHTML("afterbegin",xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue);
                afficherQte();
            }
            afficherListeGauche();
            afficherNomListe();
            afficherPostIt();
            afficherProduit();
                       
            console.log("reussi------");
        }
    };

    // Envoie de la requ?te.
    xhr.send();
}

function afficherListeGauche(){
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requ?te au serveur avec les param?tres ?ventuels.
    //xhr.open("GET", "ServletDetailProd");
    xhr.open("GET", "ServletListeCourses?method=afficherListeCourses");

    // On pr?cise ce que l'on va faire quand on aura re?u la r?ponse du serveur.
    xhr.onload = function () {
        // Si la requ?te http s'est bien pass?e.
        if (xhr.status === 200) {
            console.log("reussi");

            var elt2 = document.getElementById("accordian");
            elt2.innerHTML="";
            for (var x = 0; x < xhr.responseXML.getElementsByTagName("lcLib").length; x++) {
                // El?ment html que l'on va mettre ? jour.
                var lcId = xhr.responseXML.getElementsByTagName("idLc")[x].firstChild.nodeValue;
                //elt3.insertAdjacentHTML("beforeend","<div name='lien' id='"+ xhr.responseXML.getElementsByTagName("rayonProd")[x].firstChild.nodeValue +"'>"+xhr.responseXML.getElementsByTagName("rayonProd")[x].firstChild.nodeValue+"</div><br/>");
                var str = "<div class='panel panel-default'>" +
                        "<div class='panel-heading' id='rayon_" + lcId + "'>" +
                        "<h4 class='panel-title'><a href='#' data-toggle='collapse' name='"+lcId+"' id='lcId_" + lcId + "'><span class='badge pull-right'><i class='fa fa-plus'></i></span>" + xhr.responseXML.getElementsByTagName("lcLib")[x].firstChild.nodeValue + "</a></h4>" +
                        "</div>" +
                        "</div>";
                elt2.insertAdjacentHTML("beforeend", str);
            }
            
            for (var y = 0; y < xhr.responseXML.getElementsByTagName("lcLib").length; y++) {
                var lcId = xhr.responseXML.getElementsByTagName("idLc")[y].firstChild.nodeValue;
                document.getElementById("lcId_"+lcId).addEventListener("click", saisirSession);
            } 
            console.log("reussi------");
        }
    };

    // Envoie de la requ?te.
    xhr.send();
}

function saisirSession(){
    //récupérer le nom de la liste
    var idLst = event.srcElement.name;
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requï¿½te au serveur avec les paramï¿½tres ï¿½ventuels.
    xhr.open("GET", "ServletListeCourses?method=SaisirSession&idLst="+idLst);
    //alert(idLst);
    // On prï¿½cise ce que l'on va faire quand on aura reï¿½u la rï¿½ponse du serveur.
    xhr.onload = function () {
        // Si la requï¿½te http s'est bien passï¿½e.
        if (xhr.status === 200) {
            afficherNomListe();
            afficherPostIt();
            afficherProduit();
            
        }
    }; 
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

function ajouterListe(){
    //r?cup?rer le nom de la liste
    //var listeDeroulante = creerListeDeroulante();
    //console.log(listeDeroulante);
    var nomLst = document.getElementById("zonSaisiListe").value;
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requ?te au serveur avec les param?tres ?ventuels.
    xhr.open("GET", "ServletListeCourses?method=AjouterListeCourses&nomLst="+nomLst);
    console.log(nomLst);
    // On pr?cise ce que l'on va faire quand on aura re?u la r?ponse du serveur.
    xhr.onload = function () {
        // Si la requ?te http s'est bien pass?e.
        if (xhr.status === 200) {
            console.log("200");
            afficherListeGauche();
//            //obtenir liste deroulante
//            lstDeroulante = "<select id='lstDeroulante'>";
//            var tabIng = xhr.responseXML.getElementsByTagName("libIng");
//            for(i=0; i<tabIng.length; i++){
//                lstDeroulante = lstDeroulante+"<option name ='ing' value='"+xhr.responseXML.getElementsByTagName("idIng")[i].firstChild.nodeValue+"'>"
//                    +xhr.responseXML.getElementsByTagName("libIng")[i].firstChild.nodeValue+"</option>";
//            }
//            lstDeroulante = lstDeroulante+"</select><br/><br/><button class='btn' id='btnAjouterPostIt'>Ajouter</button>";
//            //inserer la liste derouante a HTML
//            var elt = document.getElementById("post-it-context");
//            
//            elt.insertAdjacentHTML("beforeend",creerModulePostIt(lstDeroulante));
//            document.getElementById("btnAjouterPostIt").disabled = true;
//            document.getElementById("lstDeroulante").addEventListener("change", verifierListeChoisie);
//            
        }
    }; 
    xhr.send();
}

function afficherNomListe(){
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requï¿½te au serveur avec les paramï¿½tres ï¿½ventuels.
    xhr.open("GET", "ServletListeCourses?method=AfficherNomListe");
    //alert(idLst);
    // On prï¿½cise ce que l'on va faire quand on aura reï¿½u la rï¿½ponse du serveur.
    xhr.onload = function () {
        console.log("hqokun");
        // Si la requï¿½te http s'est bien passï¿½e.
        if (xhr.status === 200) {
            console.log("200 afficher post it");
            var libLst = xhr.responseXML.getElementsByTagName("libListe")[0].firstChild.nodeValue;
            document.getElementById("libListe-h2").innerHTML = "Liste : "+libLst;
        }
    }; 
    xhr.send();
}


function afficherPostIt(){
    // Objet XMLHttpRequest.
    var xhr2 = new XMLHttpRequest();
    // Requï¿½te au serveur avec les paramï¿½tres ï¿½ventuels.
    xhr2.open("GET", "ServletListeCourses?method=AfficherPostIt");
    //alert(idLst);
    // On prï¿½cise ce que l'on va faire quand on aura reï¿½u la rï¿½ponse du serveur.
    xhr2.onload = function () {
        // Si la requï¿½te http s'est bien passï¿½e.
        if (xhr2.status === 200) {
            console.log("200 afficher post it");
            var elt = document.getElementById("post-it-context");
            
            elt.innerHTML = "";
            var tab = xhr2.responseXML.getElementsByTagName("idIng");
            console.log(tab);
            for (var x = 0; x < tab.length; x++) {
                // Elï¿½ment html que l'on va mettre ï¿½ jour.
                var idIng = xhr2.responseXML.getElementsByTagName("idIng")[x].firstChild.nodeValue;
                //elt3.insertAdjacentHTML("beforeend","<div name='lien' id='"+ xhr.responseXML.getElementsByTagName("rayonProd")[x].firstChild.nodeValue +"'>"+xhr.responseXML.getElementsByTagName("rayonProd")[x].firstChild.nodeValue+"</div><br/>");
                var libIng = xhr2.responseXML.getElementsByTagName("ingLib")[x].firstChild.nodeValue;
                var txt = creerModulePostIt(libIng, idIng);
                elt.insertAdjacentHTML("beforeend", txt);
            }
            
            for (var y = 0; y < xhr2.responseXML.getElementsByTagName("idIng").length; y++) {
                var idIng = xhr2.responseXML.getElementsByTagName("idIng")[y].firstChild.nodeValue;
                document.getElementById("idPostit_" + idIng).addEventListener("click", saisirSessionIng);    
            }
            
            //obtenir liste deroulante
            lstDeroulante = "<select id='lstDeroulante'>";
            var tabIng = xhr2.responseXML.getElementsByTagName("tousIng");
            for(i=0; i<tabIng[0].getElementsByTagName("TidIng").length; i++){
                lstDeroulante = lstDeroulante+"<option name ='ing' value='"+tabIng[0].getElementsByTagName("TidIng")[i].firstChild.nodeValue+"'>"
                    +tabIng[0].getElementsByTagName("TlibIng")[i].firstChild.nodeValue+"</option>";
            }
            lstDeroulante = lstDeroulante+"</select><br/><br/><button class='btn' id='btnAjouterPostIt'>Ajouter</button>";
            //inserer la liste derouante a HTML
            elt.insertAdjacentHTML("beforeend",creerModulePostIt(lstDeroulante,0));
            document.getElementById("btnAjouterPostIt").disabled = true;
            document.getElementById("lstDeroulante").addEventListener("change", verifierListeChoisie);    
        }
    }; 
    xhr2.send();
}

function saisirSessionIng(){
    //récupérer le nom de la liste
    var idIng = event.srcElement.name;
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requï¿½te au serveur avec les paramï¿½tres ï¿½ventuels.
    xhr.open("GET", "ServletListeCourses?method=SaisirSessionIng&idIng="+idIng);
    //alert(idLst);
    // On prï¿½cise ce que l'on va faire quand on aura reï¿½u la rï¿½ponse du serveur.
    xhr.onload = function () {
        // Si la requï¿½te http s'est bien passï¿½e.
        if (xhr.status === 200) {           
            console.log(idIng);
            window.location.href="ProduitPropose";    
        }
    }; 
    xhr.send();
}

function afficherProduit(){
    var xhr3 = new XMLHttpRequest();
    // Requï¿½te au serveur avec les paramï¿½tres ï¿½ventuels.
    xhr3.open("GET", "ServletListeCourses?method=AfficherProduit");
    //alert(idLst);
    // On prï¿½cise ce que l'on va faire quand on aura reï¿½u la rï¿½ponse du serveur.
    xhr3.onload = function () {
        // Si la requï¿½te http s'est bien passï¿½e.
        if (xhr3.status === 200) {
                       
            var elt = document.getElementById("produit-context");
            
            elt.innerHTML = " ";
            
            for (var i = 0; i < xhr3.responseXML.getElementsByTagName("src").length; i++) {
                var src = xhr3.responseXML.getElementsByTagName("src")[i].firstChild.nodeValue;
                var prixUniteProd = xhr3.responseXML.getElementsByTagName("prixUniteProd")[i].firstChild.nodeValue;
                var libProd = xhr3.responseXML.getElementsByTagName("libProd")[i].firstChild.nodeValue;
                var idProd = xhr3.responseXML.getElementsByTagName("idProd")[i].firstChild.nodeValue;
                var promoProd = xhr3.responseXML.getElementsByTagName("promotionProd")[i].firstChild.nodeValue;
                var prixPromo = xhr3.responseXML.getElementsByTagName("prixPromo")[i].firstChild.nodeValue;
                var tabLabel = xhr3.responseXML.getElementsByTagName("label")[i];
                
                //determiner si ce produit possede des labels ou pas
                var srcLabel = "nonlabel";
                if (tabLabel.getElementsByTagName("srcLabel")[0].firstChild.nodeValue !== "nonlabel") {
                    for (j = 0; j < tabLabel.getElementsByTagName("srcLabel").length; j++) {
                        srcLabel = "";
                        srcLabel = srcLabel + "< img src='" +tabLabel.getElementsByTagName("srcLabel")[j].firstChild.nodeValue + "' width='50px' height='50px'/>";
                    }
                }
                
                var text = creerModuleProduit(i, src, prixUniteProd, libProd, idProd, promoProd,prixPromo,srcLabel);
                // Elï¿½ment html que l'on va mettre ï¿½ jour.
                elt.insertAdjacentHTML("beforeend",text);
            }
            elt.insertAdjacentHTML("beforeend","</div>");
            //var qte1 = 1;
            for (var i = 0; i < xhr3.responseXML.getElementsByTagName("src").length; i++) {
                var idProd = xhr3.responseXML.getElementsByTagName("idProd")[i].firstChild.nodeValue;
                document.getElementById("btn_ajouter" + idProd).addEventListener("click", function(){ajouter(1);});
                //document.getElementById("btn_detail"+idProd).addEventListener("click", plusDetail);
            }
        }
    }; 
    xhr3.send();
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

//fonction pour ajouter un post-it a une liste de courses
function ajouterPostIt(idPostIt) {
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requï¿½te au serveur avec les paramï¿½tres ï¿½ventuels.
    xhr.open("GET", "ServletListeCourses?method=AjouterPostIt&idPostIt=" + idPostIt);
    //alert(idLst);
    // On prï¿½cise ce que l'on va faire quand on aura reï¿½u la rï¿½ponse du serveur.
    xhr.onload = function () {
        // Si la requï¿½te http s'est bien passï¿½e.
        if (xhr.status === 200) {
            var res = xhr.responseXML.getElementsByTagName("res")[0].firstChild.nodeValue;
            if (res === "existe") {
                alert("Ce post-it est d&#xE9;j&#xE0; exist!");
            } else {
                afficherPostIt();
            }
        }
    };
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

function creerModulePostIt(libPostIt, idIng) {
    var postIt = "";
    if(idIng === 0){
        postIt = ("<div class='col-sm-4'><div class='post-it'>"+libPostIt+"</div></div>");
    }else{
        postIt = ("<div class='col-sm-4'><div class='post-it'>"+
            "<a href='#' name='"+idIng+"' id='idPostit_" + idIng + "'style='color:white;'>"+libPostIt+"</a>"    
            +"</div></div>");
    }
    return postIt;
}

function verifierListeChoisie(){
        //determiner si la liste deroulante a ete choisie ou pas
//        var mySelect = document.getElementById("lstDeroulante");
        var index = this.selectedIndex;
        console.log("selected index"+index);

        document.getElementById("btnAjouterPostIt").disabled = false;
        var idIng = this.options[index].value;
        document.getElementById("btnAjouterPostIt").addEventListener("click", function(){ajouterPostIt(idIng);});
        
}
               
/**
 * Lancement apr?s le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("btnAjouterListe").addEventListener("click", ajouterListe);
    
});


