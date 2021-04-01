window.onload = afficherPanier();
function afficherPanier()
{
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET", "ServletLireFinalPanier");
    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function ()
    {
        // Si la requête http s'est bien passée.
        if (xhr.status === 200)
        {

            var elt2 = document.getElementById("J_userInfo");
            elt2.innerHTML = "Bonjour " + xhr.responseXML.getElementsByTagName("emailCli")[0].firstChild.nodeValue;


            // Elément html que l'on va mettre à jour.
            //var elt= document.getElementById("lnom");
            var image = xhr.responseXML.getElementsByTagName("src");
            var libelleP = xhr.responseXML.getElementsByTagName("libelleP");
            var PrixUnitaireP = xhr.responseXML.getElementsByTagName("PrixUnitaireP");
            var Qte = xhr.responseXML.getElementsByTagName("Qte");
            var oCar = document.getElementById("car");
            var idP = xhr.responseXML.getElementsByTagName("idP");
            var promotion = xhr.responseXML.getElementsByTagName("promotion");



            for (var i = 0; i < libelleP.length; i++) {
                var oDiv = document.createElement("div");
                oDiv.className = "row hid";
                oDiv.innerHTML += '<div class="idP left"  id="idpLeftSpan"><span>' + idP[i].firstChild.nodeValue + '</span></div>';
                oDiv.innerHTML += '<div class="img left"><img src="' + image[i].firstChild.nodeValue + '" width="80" height="80"></div>';
                oDiv.innerHTML += '<div class="name left"><span>' + libelleP[i].firstChild.nodeValue + '<br/>' + promotion[i].firstChild.nodeValue + '</span></div>';
                oDiv.innerHTML += '<div class="price left"><span>' + toDecimal2(PrixUnitaireP[i].firstChild.nodeValue) + ' euro</span></div>';
                oDiv.innerHTML += ' <div class="item_count_i left"><div class="num_count"><div class="count_d">-</div><div class="c_num">' + Qte[i].firstChild.nodeValue + '</div><div class="count_i">+</div></div> </div>';
                oDiv.innerHTML += '<div class="subtotal left"><span>' + toDecimal2(PrixUnitaireP[i].firstChild.nodeValue * Qte[i].firstChild.nodeValue) + ' euro</span></div>';
                oDiv.innerHTML += '<div class="ctrl left" style="visibility:hidden;"><a href="javascript:;">x</a></div>';
                oCar.appendChild(oDiv);
                getAmount();

                };

                // bouton pour augmenter la quantite et calculer prixsoustotal
                var i_btn = document.getElementsByClassName("count_i");
                for (var k = 0; k < i_btn.length; k++) {
                    i_btn[k].onclick = function () {
                        bt = this;
                        //?????? sous total
                        at = this.parentElement.parentElement.nextElementSibling;
                        //?????? prix unitaire
                        pt = this.parentElement.parentElement.previousElementSibling;
                        //?????  cliquer + pour augmenter la quantite de produit
                        node = bt.parentNode.childNodes[1];
                        //console.log(node);
                        num = node.innerText;
                        num = parseInt(num);
                        num++;
                        node.innerText = num;
                        //????
                        price = pt.innerText;
                        price = price.substring(0, price.length - 5);
                        //price = (price).toFixed(2);
                        //?????
                        var soustotal = toDecimal2(price * num);
                        at.innerText = soustotal + " euro";
                        //?????
                        getAmount();
                    };
                }


                // bouton pour diminuer la quantite et calculer prixsoustotal
                var d_btn = document.getElementsByClassName("count_d");
                for (k = 0; k < i_btn.length; k++) {
                    d_btn[k].onclick = function () {
                        bt = this;
                        //??????
                        at = this.parentElement.parentElement.nextElementSibling;
                        //??????
                        pt = this.parentElement.parentElement.previousElementSibling;
                        //??c_num??
                        node = bt.parentNode.childNodes[1];
                        num = node.innerText;
                        num = parseInt(num);
                        if (num > 1) {
                            num--;
                        }
                        node.innerText = num;
                        //????
                        price = pt.innerText;
                        price = price.substring(0, price.length - 5);
                        //price = (price).toFixed(2);
                        //?????
                        var soustotal = toDecimal2(price * num);
                        at.innerText = soustotal + " euro";
                        //?????
                        getAmount();
                    }
                }
            }
        }
// Envoie de la requête.
    xhr.send();
    };
    


// calculer le prix total
function getAmount() {
    ns = document.getElementsByClassName("name left");
    sum = 0;
    document.getElementById("price_num").innerText = sum;
    for (y = 1; y < ns.length; y++) {
        amount_info = ns[y].parentElement.lastElementChild.previousElementSibling;
        num = parseFloat(amount_info.innerText);
        sum = parseFloat(sum + num);
        console.log(sum);
        document.getElementById("price_num").innerText = sum;
    }
    sum = toDecimal2(sum);
    document.getElementById("price_num").innerText = sum;
}
function toDecimal2(x) {
    //var f = parseFloat(x); 
    if (isNaN(x)) {
        return false;
    }
    var f = Math.round(x * 100) / 100;
    var s = f.toString();
    var rs = s.indexOf('.');
    if (rs < 0) {
        rs = s.length;
        s += '.';
    }
    while (s.length <= rs + 2) {
        s += '0';
    }
    return s;
}




