window.onload = function () {
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET", "ServletLirePanier");
    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function ()
    {
        // Si la requête http s'est bien passée.
        if (xhr.status === 200)
        {   // Objet XMLHttpRequest.
            var xhr2 = new XMLHttpRequest();
            // Requête au serveur avec les paramètres éventuels.
            xhr2.open("GET", "ServletAfficherSession");
            // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
            xhr2.onload = function () {
                if (xhr2.status === 200) {
                    var eltMag = document.getElementById("magasin");
                    eltMag.innerHTML = "idMag : " + xhr2.responseXML.getElementsByTagName("idMag")[0].firstChild.nodeValue;
                    var eltCre = document.getElementById("heure");
                    eltCre.innerHTML = "Creneau : " + xhr2.responseXML.getElementsByTagName("creneau")[0].firstChild.nodeValue;
                    var eltDate = document.getElementById("date");
                    eltDate.innerHTML = "Date : " + xhr2.responseXML.getElementsByTagName("date")[0].firstChild.nodeValue;
                }
            };
            xhr2.send();

            var elt2 = document.getElementById("J_userInfo");
            elt2.innerHTML = "Bonjour " + xhr.responseXML.getElementsByTagName("emailCli")[0].firstChild.nodeValue;
            // Elément html que l'on va mettre à jour.
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
                oDiv.innerHTML += '<div class="price left"><span>' + PrixUnitaireP[i].firstChild.nodeValue + ' euro</span></div>';
                oDiv.innerHTML += ' <div class="item_count_i"><div class="num_count"><div class="c_num">' + Qte[i].firstChild.nodeValue + '</div></div> </div>';
                oDiv.innerHTML += '<div class="subtotal left"><span>' + PrixUnitaireP[i].firstChild.nodeValue * Qte[i].firstChild.nodeValue + ' euro</span></div>';
                oDiv.innerHTML += '<div class="ctrl left"><span>disponibilite</span></div>';
                oCar.appendChild(oDiv);
                getAmount();

                var i_btn = document.getElementsByClassName("count_i");
                for (var k = 0; k < i_btn.length; k++) {
                    i_btn[k].onclick = function () {
                        bt = this;
                        //获取小计节点 sous total
                        at = this.parentElement.parentElement.nextElementSibling;
                        //获取单价节点 prix unitaire
                        pt = this.parentElement.parentElement.previousElementSibling;
                        //获取数量值  cliquer + pour augmenter la quantite de produit
                        node = bt.parentNode.childNodes[1];
                        //console.log(node);
                        num = node.innerText;
                        num = parseInt(num);
                        num++;
                        node.innerText = num;
                        //获取单价
                        price = pt.innerText;
                        price = price.substring(0, price.length - 5);
                        //price = (price).toFixed(2);
                        //计算小计值
                        var soustotal = toDecimal2(price * num);
                        at.innerText = soustotal + " euro";
                        //计算总计值
                        getAmount();
                    };
                }
                //获取所有的数量减号按钮
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
            document.getElementById("price_eco").innerText = toDecimal2(xhr.responseXML.getElementsByTagName("economie")[0].firstChild.nodeValue);
            document.getElementById("pointspan").innerText = xhr.responseXML.getElementsByTagName("pointfi")[0].firstChild.nodeValue;
        }

    }
    xhr.send();
}

// calculer le prix total
function getAmount() {
    // console.log(ys);
    ns = document.getElementsByClassName("name left");
    //console.log(ns);
    sum = 0;
    //选中框
    document.getElementById("price_num").innerText = sum;
    //sum = (sum).toFixed(2);
    for (y = 1; y < ns.length; y++) {
        //小计
        amount_info = ns[y].parentElement.lastElementChild.previousElementSibling;
        num = parseFloat(amount_info.innerText);
        //sum += num;
        sum = parseFloat(sum + num);
        document.getElementById("price_num").innerText = sum;
        console.log(sum);
        //sum = Math.round(sum*100)/100;
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

function genererCmd() {
    var economie = document.getElementById("price_eco").innerText;
    var xhr = new XMLHttpRequest();    
    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET", "ServletGenererCommande?economie="+economie);
    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function () {

        // Si la requête http s'est bien passée.
        if (xhr.status === 200) {
            alert("La facture est ete envoye. ");
        } else {
            alert("envoye. ");
        }
    };
    xhr.send();
}
/**
 * Lancement après le chargement du DOM.
 */
document.addEventListener("DOMContentLoaded", () => {

    document.getElementById("valider").addEventListener("click", genererCmd);
});

