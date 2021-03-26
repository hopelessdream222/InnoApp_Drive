/**
 * Cette méthode affiche les details de la page d'acceuil.
 *
 * On utilise la propriété 'responseText' de l'objet XMLHttpRequest afin
 * de récupérer sous forme de texte le flux envoyé par le serveur.
 */

window.onload=afficheDetail();
function afficheDetail (){
	// Objet XMLHttpRequest.
	var xhr = new XMLHttpRequest();
	// Requête au serveur avec les paramètres éventuels.
	xhr.open("GET","ServletDetailProd");

	// On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
	xhr.onload = function(){
            // Si la requête http s'est bien passée.
            if (xhr.status === 200){
                alert("reussi");
                var elt = document.getElementById("produitsTous");
                elt.innerText="";
                for(var i=1; i<=xhr.responseXML.getElementsByTagName("src").length;i++){
                    var src=xhr.responseXML.getElementsByTagName("src")[i-1].firstChild.nodeValue;
                    var prixUniteProd=xhr.responseXML.getElementsByTagName("prixUniteProd")[i-1].firstChild.nodeValue;
                    var libProd=xhr.responseXML.getElementsByTagName("libProd")[i-1].firstChild.nodeValue;
                    var idProd=xhr.responseXML.getElementsByTagName("idProd")[i-1].firstChild.nodeValue;
                    var text = creerModuleProduit(i,src,prixUniteProd,libProd,idProd);
                    // Elément html que l'on va mettre à jour.
                    elt.insertAdjacentHTML("beforeend",text);            
                }
            
                for(var i=1; i<=xhr.responseXML.getElementsByTagName("src").length;i++){
                    document.getElementById("btn_ajouter"+xhr.responseXML.getElementsByTagName("idProd")[i-1].firstChild.nodeValue).addEventListener("click",ajouter);   
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
                                    "<div class='panel-heading' id='rayon_"+rayonId+"'>"+
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

function creerModuleProduit(i,src,prixUniteProd,libProd,idProd){
    return ("<div class='col-sm-4'>"
                        +"<div class='product-image-wrapper'>"
                            +"<div class='single-products'>"
                                    +"<div class='productinfo text-center'>"
                                        +"<div id='image"+i+"'><img src='"+src+"' width=200px hight=150px>"
                                                    +"<br/>Prix Unitaire: "+prixUniteProd+"</h2>"
                                                    +"<div height='50px'><p>"+libProd+"</p></div></div>"
                                    +"</div>"
                                    +"<div class='product-overlay'>"
                                            +"<div class='overlay-content'>"
                                                    +"<a href='#' class='btn btn-default add-to-cart' name='"+idProd+"' id='btn_ajouter"+idProd+"'><i class='fa fa-shopping-cart'></i>Ajouter au panier</a>"
                                            +"</div>"
                                    +"</div>"
                            +"</div>"
                            +"<div class='choose'>"
                                    +"<ul class='av nav-pills nav-justified'>"
                                            +"<li><a href='DetailProd'><i class='fa fa-plus-square'></i>Plus de Détail</a></li>"
                                    +"</ul>"
                            +"</div>"
                        +"</div>"
                    +"</div>");
}
        
function afficherCategories(rayonChoisi) {
    //this get VALUE
    
    var xhr = new XMLHttpRequest();
    
    xhr.open("GET","ServletRechercheCate?rayon="+rayonChoisi);
    //alert(rayonChoisi);
    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function(){
        // Si la requête http s'est bien passée.
        if (xhr.status === 200){
            //alert(" cat 200");
            var elt= document.getElementById("rayon_"+rayonChoisi);
//            elt.insertAdjacentHTML("afterend","</ul></div></div>");
            var tab = xhr.responseXML.getElementsByTagName("categorie");
            
            var text ="<div id='rayon"+rayonChoisi+"' class='panel-collapse collapse'><div class='panel-body'><ul>";
            
            for(var i=0; i<tab.length;i++ ){
                var categorieId = xhr.responseXML.getElementsByTagName("categorieId")[i].firstChild.nodeValue;
//                elt.insertAdjacentHTML("afterend","<li><a href='#'>"+tab[i].firstChild.nodeValue+"</a></li>");                
                text = text+"<li><a href='#' name='lienCate' value='"+ categorieId +"' id='"+ categorieId +"'>"+tab[i].firstChild.nodeValue+"</a></li>";
                
            }
//            elt.insertAdjacentHTML("afterend","<div id='sportswear' class='panel-collapse collapse'><div class='panel-body'><ul>");          
            elt.insertAdjacentHTML("afterend",text+"</ul></div></div>");
            
            var elt4 = document.getElementsByName("lienCate");
            for(j=0; j<elt4.length; j++){
                elt4[j].addEventListener("click",afficherProduits);                               
            } 

        }
    };
    
    // Envoie de la requête.
    xhr.send();
}

function afficherProduits() {
 
    var xhr = new XMLHttpRequest();
    var cateChoisi = event.srcElement.id;
    alert(cateChoisi);
    xhr.open("GET","ServletRechercheProdUnCate?cateId="+cateChoisi);

    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function(){
        // Si la requête http s'est bien passée.
        if (xhr.status === 200){            
            var elt2 = document.getElementById("produitsTous");
            elt2.innerText="";
            //var elt = document.getElementById("nosProds");
            for(var i=1; i<=xhr.responseXML.getElementsByTagName("src").length;i++){
                var src=xhr.responseXML.getElementsByTagName("src")[i-1].firstChild.nodeValue;
                var prixUniteProd=xhr.responseXML.getElementsByTagName("prixUniteProd")[i-1].firstChild.nodeValue;
                var libProd=xhr.responseXML.getElementsByTagName("libProd")[i-1].firstChild.nodeValue;
                var idProd=xhr.responseXML.getElementsByTagName("idProd")[i-1].firstChild.nodeValue;
                var text = creerModuleProduit(i,src,prixUniteProd,libProd,idProd);
                // Elément html que l'on va mettre à jour.
                elt2.insertAdjacentHTML("beforeend",text);            
            }
            
            for(var i=1; i<=xhr.responseXML.getElementsByTagName("src").length;i++){
                document.getElementById("btn_ajouter"+xhr.responseXML.getElementsByTagName("idProd")[i-1].firstChild.nodeValue).addEventListener("click",ajouter);   
            }
            
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
        alert("produit"+produitchoisi);
        xhr.open("GET", "ServletAjouterPanier?idP=" + produitchoisi, true);

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
            xhr.open("GET","ServletAffichageProd?nomProd="+nomProd);

            // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
            xhr.onload = function(){
                // Si la requête http s'est bien passée.
                if (xhr.status === 200){
                    alert("200");
                    if(xhr.responseXML.getElementsByTagName("res")[0].firstChild.nodeValue==="reussi"){
                        alert("zhaodaole!");
                        //Modification page
                        var elt2 = document.getElementById("produitsTous");
                        elt2.innerText="";
                        //var elt = document.getElementById("nosProds");
                        for(var i=1; i<=xhr.responseXML.getElementsByTagName("src").length;i++){
                            var src=xhr.responseXML.getElementsByTagName("src")[i-1].firstChild.nodeValue;
                            var prixUniteProd=xhr.responseXML.getElementsByTagName("prixUniteProd")[i-1].firstChild.nodeValue;
                            var libProd=xhr.responseXML.getElementsByTagName("libProd")[i-1].firstChild.nodeValue;
                            var idProd=xhr.responseXML.getElementsByTagName("idProd")[i-1].firstChild.nodeValue;
                            var text = creerModuleProduit(i,src,prixUniteProd,libProd,idProd);
                            // Elément html que l'on va mettre à jour.
                            elt2.insertAdjacentHTML("beforeend",text);            
                        }

                        for(var i=1; i<=xhr.responseXML.getElementsByTagName("src").length;i++){
                            document.getElementById("btn_ajouter"+xhr.responseXML.getElementsByTagName("idProd")[i-1].firstChild.nodeValue).addEventListener("click",ajouter);   
                        }
                    }else{
                        alert("meizhaodao!");
                        document.getElementById("zonSaisi").innerHTML="";
                        elt2.innerText="";
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

});
