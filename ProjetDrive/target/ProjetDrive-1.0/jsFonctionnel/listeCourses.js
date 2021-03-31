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
                document.getElementById("lcId_"+lcId).addEventListener("click", afficherPostEtproduit);
            }            

            console.log("reussi------");

        }
    };

    // Envoie de la requ?te.
    xhr.send();
}

function afficherPostEtproduit(){
    alert(event.srcElement.name);
    
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
//function creerListeDeroulante(){
//    var lstDeroulante = "";
//    // Objet XMLHttpRequest.
//    var xhr = new XMLHttpRequest();
//    // Requete au serveur avec les param?tres ?ventuels.
//    xhr.open("GET", "ServletListeCourses?method=ObtenirPostIts");
//    // On pr?cise ce que l'on va faire quand on aura re?u la r?ponse du serveur.
//    xhr.onload = function () {
//        // Si la requ?te http s'est bien pass?e.
//        if (xhr.status === 200) {
//            lstDeroulante = "<select id='lstDeroulante'>";
//            var tabIng = xhr.responseXML.getElementsByTagName("libIng");
//            for(i=0; i<tabIng.length; i++){
//                lstDeroulante = lstDeroulante+"<option name ='ing' id='"+xhr.responseXML.getElementsByTagName("idIng")[i].firstChild.nodeValue+"'>"
//                    +xhr.responseXML.getElementsByTagName("libIng")[i].firstChild.nodeValue+"</option>";
//            }
//            lstDeroulante = lstDeroulante+"</select>";
//            console.log(lstDeroulante);
//        }
//    }; 
//    xhr.send();
//    return lstDeroulante;
//}
function ajouterListe(){
    //r?cup?rer le nom de la liste
    //var listeDeroulante = creerListeDeroulante();
    //console.log(listeDeroulante);
    var nomLst = document.getElementById("zonSaisiListe").value;
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requ?te au serveur avec les param?tres ?ventuels.
    xhr.open("GET", "ServletListeCourses?method=AjouterListeCourses&nomLst="+nomLst);
    alert(nomLst);
    // On pr?cise ce que l'on va faire quand on aura re?u la r?ponse du serveur.
    xhr.onload = function () {
        // Si la requ?te http s'est bien pass?e.
        if (xhr.status === 200) {
            alert("200");
            //obtenir liste deroulante
            lstDeroulante = "<select id='lstDeroulante'>";
            var tabIng = xhr.responseXML.getElementsByTagName("libIng");
            for(i=0; i<tabIng.length; i++){
                lstDeroulante = lstDeroulante+"<option name ='ing' value='"+xhr.responseXML.getElementsByTagName("idIng")[i].firstChild.nodeValue+"'>"
                    +xhr.responseXML.getElementsByTagName("libIng")[i].firstChild.nodeValue+"</option>";
            }
            lstDeroulante = lstDeroulante+"</select><button id='btnAjouterPostIt'>Ajouter</button>";
            //inserer la liste derouante a HTML
            var elt = document.getElementById("post-it-context");
            
            elt.insertAdjacentHTML("beforeend",creerModulePostIt(lstDeroulante));
            document.getElementById("btnAjouterPostIt").disabled = true;
            document.getElementById("lstDeroulante").addEventListener("change", verifierListeChoisie);
            
        }
    }; 
    xhr.send();
}
//fonction pour ajouter un post-it a une liste de courses
function ajouterPostIt(idIng){
    alert(idIng);
//    xhr.open("GET", "ServletListeCourses?method=AjouterListeCourses&nomLst="+nomLst);
//    // On pr?cise ce que l'on va faire quand on aura re?u la r?ponse du serveur.
//    xhr.onload = function () {
//        // Si la requ?te http s'est bien pass?e.
//        if (xhr.status === 200) {
//           
//        }
//    }; 
//    xhr.send();
}
function creerModulePostIt(libPostIt) {
    return ("<div class='col-sm-4'><div class='post-it'>"+libPostIt+"</div></div>");
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


