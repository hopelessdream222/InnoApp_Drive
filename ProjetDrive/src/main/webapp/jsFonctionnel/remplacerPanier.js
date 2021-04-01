window.onload = afficherPanier();
function afficherPanier()
{
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requ�te au serveur avec les param�tres �ventuels.
    xhr.open("GET", "ServletLireFinalPanier");
    // On pr�cise ce que l'on va faire quand on aura re�u la r�ponse du serveur.
    xhr.onload = function ()
    {
        // Si la requ�te http s'est bien pass�e.
        if (xhr.status === 200)
        {

            var elt2 = document.getElementById("J_userInfo");
            elt2.innerHTML = "Bonjour " + xhr.responseXML.getElementsByTagName("emailCli")[0].firstChild.nodeValue;


            // El�ment html que l'on va mettre � jour.
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
                        //pour trouver sous total
                        at = this.parentElement.parentElement.nextElementSibling;
                        //pour trouver prix unitaire
                        pt = this.parentElement.parentElement.previousElementSibling;
                        //cliquer + pour augmenter la quantite de produit
                        node = bt.parentNode.childNodes[1];
                        //console.log(node);
                        num = node.innerText;
                        num = parseInt(num);
                        num++;
                        node.innerText = num;
                        idpele = pt.previousElementSibling.previousElementSibling.previousElementSibling;
                        idpmodifier=idpele.childNodes[0].textContent;
                        //console.log(idp);
                        //console.log(num);
                        var xhrajouter = new XMLHttpRequest();
                        // Requête au serveur avec les paramètres éventuels.
                        xhrajouter.open("GET", "ServletModifierQte?idP=" + idpmodifier+"&qte="+num);
                        // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
                        xhrajouter.onload = function () {
                            // Si la requête http s'est bien passée.
                            if (xhrajouter.status === 200) {
                                //alert("Attention ! Il faut actualiser votre web !");
                            }
                        };
                        xhrajouter.send();
                        //le prix unitaire sous le format string
                        price = pt.innerText;
                        price = price.substring(0, price.length - 5);
                        //calculer soustotal
                        var soustotal = toDecimal2(price * num);
                        at.innerText = soustotal + " euro";
                        //metter a jour le prix total
                        getAmount();
                    };
                }


                // bouton pour diminuer la quantite et calculer prixsoustotal
                var d_btn = document.getElementsByClassName("count_d");
                for (k = 0; k < i_btn.length; k++) {
                    d_btn[k].onclick = function () {
                        bt = this;
                        //获取小计节点
                        at = this.parentElement.parentElement.nextElementSibling;
                        //获取单价节点
                        pt = this.parentElement.parentElement.previousElementSibling;
                        //获取c_num节点
                        node = bt.parentNode.childNodes[1];
                        num = node.innerText;
                        num = parseInt(num);
                        if (num > 1) {
                            num--;
                        }
                        node.innerText = num;
                        idpele = pt.previousElementSibling.previousElementSibling.previousElementSibling;
                        idpmodifier=idpele.childNodes[0].textContent;
                        //console.log(idp);
                        //console.log(num);
                        var xhrdiminuer = new XMLHttpRequest();
                        // Requête au serveur avec les paramètres éventuels.
                        xhrdiminuer.open("GET", "ServletModifierQte?idP=" + idpmodifier+"&qte="+num);
                        // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
                        xhrdiminuer.onload = function () {
                            // Si la requête http s'est bien passée.
                            if (xhrdiminuer.status === 200) {
                                //alert("Attention ! Il faut actualiser votre web !");
                            }
                        };
                        xhrdiminuer.send();
                        //获取单价
                        price = pt.innerText;
                        price = price.substring(0, price.length - 5);
                        //price = (price).toFixed(2);
                        //计算小计值
                        var soustotal = toDecimal2(price * num);
                        at.innerText = soustotal + " euro";
                        //计算总计值
                        getAmount();
                    }
                }
            }
        }
// Envoie de la requ�te.
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




