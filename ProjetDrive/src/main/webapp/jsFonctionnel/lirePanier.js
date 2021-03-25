window.onload = afficherPanier();
function afficherPanier ()
	{
         // Objet XMLHttpRequest.
         var xhr = new XMLHttpRequest();
         // Requête au serveur avec les paramètres éventuels.
         xhr.open("GET","ServletLirePanier");
         // On précise ce que l'on va faire quand on aura reçu la réponse du serveur.
         xhr.onload = function()
          {
          // Si la requête http s'est bien passée.
          if (xhr.status === 200)
           {
               
             var elt2 = document.getElementById("J_userInfo");
             elt2.innerHTML = xhr.responseXML.getElementsByTagName("emailCli")[0].firstChild.nodeValue;   
             
             
            // Elément html que l'on va mettre à jour.
            //var elt= document.getElementById("lnom");
            var image = xhr.responseXML.getElementsByTagName("src");
            var libelleP = xhr.responseXML.getElementsByTagName("libelleP");
            var PrixUnitaireP = xhr.responseXML.getElementsByTagName("PrixUnitaireP");
            var Qte = xhr.responseXML.getElementsByTagName("Qte");
            var oCar = document.getElementById("car");
            
            
                         
            for(var i=0;i<libelleP.length;i++){
                var oDiv = document.createElement("div");
                oDiv.className = "row hid";
                oDiv.innerHTML += '<div class="check left"> <i class="i_check" id="i_check" onclick="i_check()" >*</i></div>';
                oDiv.innerHTML += '<div class="img left"><img src="' + image[i].firstChild.nodeValue + '" width="80" height="80"></div>';              
                oDiv.innerHTML += '<div class="name left"><span>' + libelleP[i].firstChild.nodeValue + '</span></div>';
                oDiv.innerHTML += '<div class="price left"><span>' + PrixUnitaireP[i].firstChild.nodeValue + ' euro</span></div>';
                oDiv.innerHTML +=' <div class="item_count_i"><div class="num_count"><div class="count_d">-</div><div class="c_num">'+Qte[i].firstChild.nodeValue+'</div><div class="count_i">+</div></div> </div>';
                oDiv.innerHTML += '<div class="subtotal left"><span>' + PrixUnitaireP[i].firstChild.nodeValue*Qte[i].firstChild.nodeValue + ' euro</span></div>';
                oDiv.innerHTML += '<div class="ctrl left"><a href="javascript:;">x</a></div>';
                oCar.appendChild(oDiv);
            
            var flag = true;
            var check = oDiv.firstChild.getElementsByTagName("i")[0];
            check.onclick = function() {
                // console.log(check.className);
                if (check.className == "i_check i_acity") {
                    check.classList.remove("i_acity");

                } else {
                    check.classList.add("i_acity");
                }
                getAmount();
            };
            
            var delBtn = oDiv.lastChild.getElementsByTagName("a")[0];
            delBtn.onclick = function() {
                var result = confirm("Vous voulez le supprimer ?");
                if (result) {
                    oCar.removeChild(oDiv);
                    number--;
                    getAmount();
                }
            };
            
            // bouton pour augmenter la quantite et calculer prixsoustotal
            var i_btn = document.getElementsByClassName("count_i");
            for (var k = 0; k < i_btn.length; k++) {
                i_btn[k].onclick = function() {
                    bt = this;
                    //获取小计节点 sous total
                    at = this.parentElement.parentElement.nextElementSibling;
                    //获取单价节点 prix unitaire
                    pt = this.parentElement.parentElement.previousElementSibling;
                    //获取数量值  cliquer + pour augmenter la quantite de produit
                    node = bt.parentNode.childNodes[1];
                    console.log(node);
                    num = node.innerText;
                    num = parseInt(num);
                    num++;
                    node.innerText = num;
                    //获取单价
                    price = pt.innerText;
                    price = price.substring(0, price.length - 5);
                    //计算小计值
                    at.innerText = price * num + " euro";
                    //计算总计值
                    getAmount();
                };
            }
            
            
            // bouton pour diminuer la quantite et calculer prixsoustotal
            var d_btn = document.getElementsByClassName("count_d");
            for (k = 0; k < i_btn.length; k++) {
                d_btn[k].onclick = function() {
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
                    //计算小计值     
                    at.innerText = price * num + "euro";
                    //计算总计值
                    getAmount();
                }
            }
            
            //  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! completer
            delBtn.onclick = function() {
                var result = confirm("Vous voulez le supprimer ?");
                if (result) {
                    oCar.removeChild(oDiv);
                    number--;
                    getAmount();
                }
            }

        }
            }

        };
 // Envoie de la requête.
 xhr.send();  
	}

/*function getClass(oBox, tagname) { //应该是点了button之后加入panier页面里
    var aTag = oBox.getElementsByTagName("*");
    var aBox = [];
    for (var i = 0; i < aTag.length; i++) {
        if (aTag[i].className === tagname) {
            aBox.push(aTag[i]);
        }
    }
    return aBox;
}*/

var index = false;
function checkAll() {
    var choose = document.getElementById("car").getElementsByTagName("i");
    if (choose.length !== 1) {
        for (i = 1; i < choose.length; i++) {
            if (!index) {
                choose[0].classList.add("i_acity2");
                choose[i].classList.add("i_acity");
            } else {
                choose[i].classList.remove("i_acity");
                choose[0].classList.remove("i_acity2");
            }
        }
        index = !index;
    }
    getAmount();
}

// calculer le prix total
function getAmount() {
    // console.log(ys);
    ns = document.getElementsByClassName("i_acity");
    console.log(ns);
    sum = 0;
    //选中框
    document.getElementById("price_num").innerText = sum;
    for (y = 0; y < ns.length; y++) {
        //小计
        amount_info = ns[y].parentElement.parentElement.lastElementChild.previousElementSibling;
        num = parseFloat(amount_info.innerText);
        sum += num;
        document.getElementById("price_num").innerText = sum;
    }
}

