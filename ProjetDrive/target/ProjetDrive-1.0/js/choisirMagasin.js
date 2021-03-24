window.onload = afficherMag();
function afficherMag (){
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();

    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET","ServletAfficherMagasins");

    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function(){
        // Si la requête http s'est bien passée.
        if (xhr.status === 200){  
           var elt = document.getElementById("lstMagasin");
           
           var tabId = xhr.responseXML.getElementsByTagName("idMag");
           var tabNom = xhr.responseXML.getElementsByTagName("nomMag");
           var tabAdr = xhr.responseXML.getElementsByTagName("adrMag");
           var tabCp = xhr.responseXML.getElementsByTagName("cpMag");
           var tabTel = xhr.responseXML.getElementsByTagName("telMag");
           
           for(var i=0; i<tabId.length; i++){
               var detail = tabNom[i].firstChild.nodeValue+"<br/>"+tabAdr[i].firstChild.nodeValue+"<br/>"+tabCp[i].firstChild.nodeValue+"<br/>Tel: "+tabTel[i].firstChild.nodeValue+"<br/>";
               var id = tabId[i].firstChild.nodeValue;
               
               elt.insertAdjacentHTML("beforeend","<div class='magasin'>"+
                       "<input type='radio' name='magasin' id='"+id+"' value='"+id+"'/>"+
                       "<label for='"+id+"'>"+detail+"<label/></div><br/>");
           }
        }
    };
	
    // Envoie de la requête.
    xhr.send();
    }
    
function choisirMag(){
    var radio = document.getElementsByName("magasin");
    var idMag = 0;
    for(j=0; j<radio.length;j++){
        if(radio[j].checked){
            idMag = radio[j].value;          
        }
    }
    
    alert(idMag);
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();

    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET","ServletChoisirMagasin?idMag="+idMag);

    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function(){
        // Si la requête http s'est bien passée.
        if (xhr.status === 200){  
   //...
        }
    };
    xhr.send();
    }


/**
 * Lancement après le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {

    document.getElementById("btnChoisirMag").addEventListener("click",choisirMag);
});

