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
                for(var i=2; i<=5;i++){
                    // Elément html que l'on va mettre à jour.
                    var elt = document.getElementById("image"+i);
                    elt.innerHTML = "<img src='"+xhr.responseXML.getElementsByTagName("src")[i-2].firstChild.nodeValue+"' width=200px hight=150px><div>"+xhr.responseXML.getElementsByTagName("libProd")[i-2].firstChild.nodeValue+"</div>";
                    //alert("<img src='"+xhr.responseXML.getElementsByTagName("src")[i-2].firstChild.nodeValue+"'>");
                    document.getElementById("image"+i+"_prodId").innerHTML=xhr.responseXML.getElementsByTagName("idProd")[i-2].firstChild.nodeValue;
                    //alert("reussi"+i);
                }
                if(xhr.responseXML.getElementsByTagName("client")[0]===null){
                }else{
                    var elt2 = document.getElementById("connexion");

                    elt2.innerHTML = xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue;
                    //elt2.insertAdjacentHTML("afterbegin",xhr.responseXML.getElementsByTagName("client")[0].firstChild.nodeValue);
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
