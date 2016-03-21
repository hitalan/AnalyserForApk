window.showLoadingStatus = function(error) {
   var ele = document.getElementById("mail_master_mail_content_ext");
   if (ele) {
     var loading = ele.querySelector(".mail_master_ext_loading");
     if (loading) {
       var s = error ? "none" : "block";
       loading.style.setProperty("display", s, "important");
     }
     var loadingErr =  ele.querySelector(".mail_master_ext_loading_error");
     if (loadingErr) {
       var s = error ? "block" : "none";
       loadingErr.style.setProperty("display", s, "important");
     }
   }
}

window.showExtContent = function(content, cToken) {
  var ele = document.getElementById("mail_master_mail_content_ext");
  if (ele) {
    ele.innerHTML = content;
    ele.cToken = cToken;
    var scripts = ele.getElementsByTagName("script");
    for (var i = 0; i < scripts.length; i++) {
        if (scripts[i].src != "") {
            var tag = document.createElement("script");
            tag.src = scripts[i].src;
            document.getElementsByTagName("head")[0].appendChild(tag);
        } else {
            eval(scripts[i].innerHTML);
        }
    }
    
  }
}

window.appHostOnLoad = function() {
    loadExtContent();
}

window.loadExtContent = function() {
  var ele = document.getElementById("mail_master_mail_content_ext");
  if (ele) {
    var key = ele.getAttribute("contentKey");
     if (window.appHost) {
       appHost.invoke("loadExtContent", {"contentKey" : key}, function (params) {
         if (params["error"]) {
           showLoadingStatus(true);
         } else {
           showExtContent(params["content"], params["cToken"]);
         }
       });
     }
     showLoadingStatus(false);
  }
}

