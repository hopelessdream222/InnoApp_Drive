/**
 * Cette méthode affiche les details de la page d'acceuil.
 *
 * On utilise la propriété 'responseText' de l'objet XMLHttpRequest afin
 * de récupérer sous forme de texte le flux envoyé par le serveur.
 */

window.onload=afficheDetail();
function afficheDetail (){
         //alert("123");
	// Objet XMLHttpRequest.
	var xhr = new XMLHttpRequest();
	// Requête au serveur avec les paramètres éventuels.
	xhr.open("GET","ServletDetailProd");

	// On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
	xhr.onload = function(){
            // Si la requête http s'est bien passée.
            if (xhr.status === 200){
                alert("reussi");
                for(var i=1; i<=9;i++){
                   
                    // Elément html que l'on va mettre à jour.
                    var elt = document.getElementById("image"+i);
                    elt.innerHTML = "<img src='"+xhr.responseXML.getElementsByTagName("src")[i-1].firstChild.nodeValue+"' width=200px hight=150px>"; 
                    elt.insertAdjacentHTML("afterend", "<h2>Prix/KG: "+xhr.responseXML.getElementsByTagName("prixKGProd")[i-1].firstChild.nodeValue+
                                                        "<br/>Prix Unitaire: "+xhr.responseXML.getElementsByTagName("prixUniteProd")[i-1].firstChild.nodeValue+"</h2>"+
                                                        "<div height='50px'><p>"+xhr.responseXML.getElementsByTagName("libProd")[i-1].firstChild.nodeValue+"</p></div>");
                   }               
                //Client
                if(xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue==="horsConnection"){
                }
                else{
                    var elt2 = document.getElementById("connexion");
                    elt2.innerHTML = "Bienvenue! "+xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue;
                    //elt2.insertAdjacentHTML("afterbegin",xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue);
                }
                
                var elt3 = document.getElementById("accordian");
                
                for(var x=0; x<xhr.responseXML.getElementsByTagName("rayonProd").length;x++){
                    // Elément html que l'on va mettre à jour.
                    var rayonId = xhr.responseXML.getElementsByTagName("rayonId")[x].firstChild.nodeValue;
                    //elt3.insertAdjacentHTML("beforeend","<div name='lien' id='"+ xhr.responseXML.getElementsByTagName("rayonProd")[x].firstChild.nodeValue +"'>"+xhr.responseXML.getElementsByTagName("rayonProd")[x].firstChild.nodeValue+"</div><br/>");
                    var str = "<div class='panel panel-default'>"+
                                    "<div class='panel-heading' id='"+rayonId+"'>"+
                                        "<h4 class='panel-title'><a data-toggle='collapse' data-parent='#accordian' href='#rayon"+rayonId+"' name='lien' id='"+ rayonId +"'><span class='badge pull-right'><i class='fa fa-plus'></i></span>"+xhr.responseXML.getElementsByTagName("rayonProd")[x].firstChild.nodeValue+"</a></h4>"+
                                    "</div>"+
                               "</div>";                 
                    elt3.insertAdjacentHTML("beforeend",str);
                    afficherCategories(xhr.responseXML.getElementsByTagName("rayonId")[x].firstChild.nodeValue);
     
                }
                alert("reussi------");
//                var elt4 = document.getElementsByName("lien");
//                for(j=0; j<elt4.length; j++){
//                    elt4[j].addEventListener("click",afficherCategories);                               
//                }   
            }
        };

	// Envoie de la requête.
	xhr.send();
	}
        
        
function afficherCategories(rayonChoisi) {
    //this get VALUE
    
    var xhr = new XMLHttpRequest();
    
    //var rayonChoisi = event.srcElement.id;
    
    //alert("categorie");

    //document.getElementById("categorie").innerText="";
    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET","ServletRechercheCate?rayon="+rayonChoisi);

    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function(){
        // Si la requête http s'est bien passée.
        if (xhr.status === 200){
            //alert(" cat 200");
            var elt= document.getElementById(rayonChoisi);
//            elt.insertAdjacentHTML("afterend","</ul></div></div>");
            var tab = xhr.responseXML.getElementsByTagName("categorie");
            var text ="<div id='rayon"+rayonChoisi+"' class='panel-collapse collapse'><div class='panel-body'><ul>";
            
            for(var i=0; i<tab.length;i++ ){
//                elt.insertAdjacentHTML("afterend","<li><a href='#'>"+tab[i].firstChild.nodeValue+"</a></li>");                
                text = text+"<li><a href='#'>"+tab[i].firstChild.nodeValue+"</a></li>";
            }
//            elt.insertAdjacentHTML("afterend","<div id='sportswear' class='panel-collapse collapse'><div class='panel-body'><ul>");          
            elt.insertAdjacentHTML("afterend",text+"</ul></div></div>");
            console.log(document.getElementById("accordian"));
            
            
            
            
            
        }
    };
    
    // Envoie de la requête.
    xhr.send();
}
function ajouter() {
    var result = confirm("Vous voulez l'ajouter au panier ?");
    if (result) {
        // Objet XMLHttpRequest.
        var xhr = new XMLHttpRequest();
        // Requête au serveur avec les paramètres éventuels.
        var produitchoisi = event.srcElement.name;
        xhr.open("GET", "ServletAjouterPanier?idP=" + document.getElementById(produitchoisi).innerText, true);

        // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
        xhr.onload = function () {
            // Si la requête http s'est bien passée.
            if (xhr.status === 200) {
                var result2 = alert("Le produit est bien ajoute dans le panier");
            }
        };
    }
    // Envoie de la requête.
    xhr.send();
}

function rechercher (){
         //alert("123");
	// Objet XMLHttpRequest.
	var xhr = new XMLHttpRequest();
        var nomProd = document.getElementById("zonSaisi").value;
        if(nomProd===""){
            alert("Veuillez saisir un produit");
        }else{
            // Requête au serveur avec les paramètres éventuels.
            xhr.open("GET","ServletRechercheProd?nomProd="+nomProd);

            // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
            xhr.onload = function(){
                // Si la requête http s'est bien passée.
                if (xhr.status === 200){
                    alert("200");
                    if(xhr.responseXML.getElementsByTagName("res")[0].firstChild.nodeValue==="reussi"){
                        alert("zhaodaole!");
                        window.location.href="ProduitRecherche";
                    }else{
                        alert("meizhaodao!");
                        document.getElementById("zonSaisi").innerHTML="";
                    }
                }
            };
        }
	// Envoie de la requête.
	xhr.send();
    }
/**
 * Lancement après le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {

	//document.getElementById("btn_image2_voirDetail").addEventListener("click",afficheXML);
        document.getElementById("btnRechercher").addEventListener("click",rechercher);
        document.getElementById("btn_image2_ajout").addEventListener("click",ajouter);
        document.getElementById("btn_image3_ajout").addEventListener("click",ajouter);
        document.getElementById("btn_image4_ajout").addEventListener("click",ajouter);
        document.getElementById("btn_image5_ajout").addEventListener("click",ajouter);

});
