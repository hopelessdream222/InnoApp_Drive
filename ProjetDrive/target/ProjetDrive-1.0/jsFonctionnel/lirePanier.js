window.onload = afficherPanier();
function afficherPanier()
{
    // Objet XMLHttpRequest.
    var xhr = new XMLHttpRequest();
    // Requête au serveur avec les paramètres éventuels.
    xhr.open("GET", "ServletLirePanier");
    // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
    xhr.onload = function ()
    {
        // Si la requête http s'est bien passée.
        if (xhr.status === 200)
        {

            var elt2 = document.getElementById("J_userInfo");
            elt2.innerHTML = "Bienvenue! " + xhr.responseXML.getElementsByTagName("emailCli")[0].firstChild.nodeValue;


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
                oDiv.innerHTML += '<div class="ctrl left"><button id="' + idP[i].firstChild.nodeValue + '" name="supprimerun">x</button></div>';
                oCar.appendChild(oDiv);
                getAmount();

                var delBtnTous = document.getElementById("supprimertous");
                delBtnTous.onclick = function () {
                    var result = confirm("Vous voulez supprimer tous ?");
                    if (result) {
                        oCar.remove(oDiv);
                        var xhr3 = new XMLHttpRequest();
                        // Requête au serveur avec les paramètres éventuels.
                        //var produitchoisi = event.srcElement.name;
                        //var produitsupprimer = document.getElementsByClassName("name left");
                        //console.log(document.getElementById("idpLeftSpan").innerText);
                        xhr3.open("GET", "ServletSupprimerTousProdPanier");
                        // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
                        xhr3.onload = function () {
                            // Si la requête http s'est bien passée.
                            if (xhr3.status === 200) {
                            }
                        };
                        xhr3.send();
                        window.location.href = "Accueil";
                    }
                }
                var delBtn = document.getElementsByName("supprimerun");
                for (var s = 0; s < delBtn.length; s++) {
                    delBtn[s].onclick = function () {
                        var result = confirm("Vous voulez le supprimer ?");
                        if (result) {
                            var produitsup = event.target.id;
                            console.log(produitsup);
                            var xhr2 = new XMLHttpRequest();
                            // Requête au serveur avec les paramètres éventuels.

                            //console.log(document.getElementById("idpLeftSpan").innerText);
                            xhr2.open("GET", "ServletSupprimerProdPanier?idP=" + produitsup);
                            // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
                            xhr2.onload = function () {
                                // Si la requête http s'est bien passée.
                                if (xhr2.status === 200) {
                                    alert("Attention ! Il faut actualiser votre web !");
                                }
                            };
                            xhr2.send();
                            oCar.removeChild(oDiv);
                            getAmount();
                        }

                        //window.parent.location.reload();
                    }
                }
                ;

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

    };
    // Envoie de la requête.
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
        //console.log(sum);
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

