(function() {
  var NE_CLS_SCALE = "NE-SCALED";
  var NE_CLS_CH_HEIGHT = "NE-CH-HEIGHT";
  var NE_CLS_CH_TOP = "NE-CH-TOP";
  var NE_CLS_CH_WIDTH = "NE-CH-WIDTH";
  var firstLoad = true;
  var originW = "originW";
  var needCheckQuote = true;
  var imgsMap = {};

  function queryAll(doc, selector) {
    return (doc || document.documentElement).querySelectorAll(selector);
  }

  function query(doc, selector) {
    return (doc || document.documentElement).querySelector(selector);
  }

  function attr(el, name, value) {
    if (!!value) {
      el.setAttribute(name, value);
    } else {
      return el.getAttribute(name);
    }
  }

  function getStyleRuleValue(style, selector, sheet) {
      var sheets = typeof sheet !== 'undefined' ? [sheet] : document.styleSheets;
      for (var i = 0, l = sheets.length; i < l; i++) {
          var sheet = sheets[i];
          if( !sheet.cssRules ) { continue; }
          for (var j = 0, k = sheet.cssRules.length; j < k; j++) {
              var rule = sheet.cssRules[j];
              if (rule.selectorText && rule.selectorText.split(',').indexOf(selector) !== -1) {
                  return rule.style[style];
              }
          }
      }
      return null;
  }

  function getElementOriWidthOrHeight(ele, name) {
  	var w = ele.style[name] || attr(ele, name);
  	if (w && w.indexOf("%") == -1) {
  		return parseInt(w);
  	}
    var cls = ele.classList;
    for (var i = 0; i < cls.length; ++i) {
      var w = getStyleRuleValue(name, "." + cls[i]);
  	  if (w && w.indexOf("%") == -1) {
  	  	return parseInt(w);
  	  }
    }
  	return null;
  }

  function addImportantStyle(ele, name, value) {
    ele.style.setProperty(name, value, "important");
  }

  function cleanStyle(ele, name) {
    ele.style.setProperty(name, "");
  }

  function addClass(ele, clsName) {
    var classes = ele.classList;
    if (!classes.contains(clsName)) {
      classes.add(clsName);
    }
  }

  function removeClass(ele, clsName) {
    var classes = ele.classList;
    if (classes.contains(clsName)) {
      classes.remove(clsName);
    } 
  }

  function callNative(n, p) {
    prompt(JSON.stringify({name:n, text:p}));
  }

 
  function needScale(ele) {
    return ele.id != 'ntes-mail-android-quote-controller';
  }

  function shouldScaleSelf(ele) {
    var tagName = ele.tagName.toLowerCase();
    return ("table" == tagName) || hasBkImage(ele);
  }

  function hasBkImage(ele) {
    var style = getComputedStyle(ele);
    var bkimg =style.backgroundImage;
    var bkr =style.backgroundRepeatX;
    return ("none" != bkimg) && (bkr != "repeat-x");
  }

  function canSetHeight(ele) {
    var tags = ["font", "blockquote", "img"];
    var tagName = ele.tagName.toLowerCase();
    return tags.indexOf(tagName) == -1;
  }


  function tryRemoveWidth(ele, dw, hasFloat) {
    if (hasFloat) {
      return false;   
    }
    if (!shouldScaleSelf(ele) && (query(ele, ["[style*='float']"]) == null)) {
      var w = getElementOriWidthOrHeight(ele, "width") || 0;
      attr(ele, originW, w);
      addClass(ele, NE_CLS_CH_WIDTH);
      addImportantStyle(ele, "width", dw + "px");
      addImportantStyle(ele, "height", "auto");
      return true;
    }
    return false;
  }

  function scaleElement(ele, docWidth, pbw, pwFixed, pzindex, cInfo) {
    var w = ele.scrollWidth;
    var doScale = shouldScaleSelf(ele);
    var cc = ele.children;
    var cch = 0;
    var pw = getElementOriWidthOrHeight(ele, "width");
    var zIndex = ele.style.zIndex;
    zIndex = "" == zIndex ? pzindex : zIndex;
    var curWFixed = pwFixed || (null != pw);
    if (cInfo) {
      cInfo.hasFloat = getComputedStyle(ele).float != "none";
    }
    if (doScale) {
       lh = scaleNode(ele, docWidth, cch, pbw, pwFixed, null != pw, false); 
    } else {
      var info = {hasFloat:false};
      for (var i = 0; i < cc.length; ++i) {
        cch += scaleElement(cc[i], docWidth, w, curWFixed, zIndex, info);
      }
      lh = scaleNode(ele, docWidth, cch, pbw, pwFixed, null != pw, info.hasFloat);    
    }
    if (zIndex != pzindex) {
      return 0;
    } else {
      return lh;
    }
  }

  function changeHeight(ele, delta) {
    var h = getElementOriWidthOrHeight(ele, "height");
    if (!h) {
      h = ele.scrollHeight;
    } else {
      h = ele.offsetHeight;
    }
    addImportantStyle(ele, "height", h + delta + "px");
  }

  function scaleNode(ele, docWidth, cch, pbw, pwFixed, curWFixed, hasFloat) {
    var h = ele.offsetHeight;
    var w = ele.scrollWidth
    if (needScale(ele) && w > docWidth && (pbw < w || !pwFixed)) {
      var modify = tryRemoveWidth(ele, docWidth, hasFloat);
      if (modify) {
        var h2 = ele.offsetHeight;
        if (h2 > h) {
          cch += (h2 - h);
        }
      }
      // the scroll width after scaled maybe not right.
      if (modify && (cch != 0) && (ele.scrollWidth == w)) {
        w = ele.offsetWidth;
      } else {
        w = ele.scrollWidth;
      }
      if (canSetHeight(ele)) {
        changeHeight(ele, cch);
        //addImportantStyle(ele, "height", h + cch + "px");
        addClass(ele, NE_CLS_CH_HEIGHT);
        cch = 0;
      }
      if (w > docWidth) {
        if (!modify) {
          var ow = getElementOriWidthOrHeight(ele, "width") || 0;
          attr(ele, originW, ow);
          addClass(ele, NE_CLS_CH_WIDTH);
        }
        var s = docWidth / w;
        var oldph = ele.parentElement.offsetHeight;
        addImportantStyle(ele, "-webkit-transform", "scale(" + s + ")");
        addImportantStyle(ele, "-webkit-transform-origin", "0px 0px");
        addImportantStyle(ele, "width", w + "px");
        h = ele.offsetHeight;
        var mr = (Math.round(w * (s - 1)) -1), mb = (Math.round(h * (s - 1)) -1);
        addImportantStyle(ele, "margin-right", mr + "px");
        addImportantStyle(ele, "margin-bottom", mb + "px");
        addImportantStyle(ele, "height", h + "px");
        addClass(ele, NE_CLS_SCALE);
        var newph = ele.parentElement.offsetHeight;
        return (oldph - newph + mb + cch + 1);
      }
    } else {
       if (canSetHeight(ele)) {
        changeHeight(ele, cch);
        //addImportantStyle(ele, "height", h + cch + "px");
        addClass(ele, NE_CLS_CH_HEIGHT);
        cch = 0;
      }
    }
    return cch;
  }

  function shouldScale() {
    var needScale = attr(document.documentElement, "need_scale");
    if ("false" == needScale) {
        return false;
    }
    // 特殊邮件不进行缩放
    var ele = document.getElementById("mail_master_mail_content_ext");
    return !!!ele;
  }

  function doCheckQuote() {
    if (needCheckQuote) {
      needCheckQuote = false;
      checkQuote("显示引用内容", 'file:///android_asset/topic.png');
    }
  }

  function startScale() {
    doCheckQuote();
    if (!shouldScale()) {
      return;
    }
    var docWidth = document.body.offsetWidth;
    if (docWidth <= 0) {
      return;
    }
    var msgDiv = query(undefined, "#ntes-mail-content");
    msgDiv.style.overflowY = "";
    docWidth = msgDiv.offsetWidth;
    var orgScrollW = msgDiv.scrollWidth;
    var cch = scaleElement(msgDiv.children[0], docWidth, docWidth, false, msgDiv.style.zIndex);
    var h = msgDiv.scrollHeight;
    if (cch < 0) {
        addImportantStyle(msgDiv, "height", h + cch + "px");
        addClass(msgDiv, NE_CLS_CH_HEIGHT);
        cch = 0;
    }
    msgDiv.style.overflowY = "hidden";
    return msgDiv.scrollWidth >= orgScrollW;
  }

  window.onload = function() {
    //reScale();
    processBigAttachment();
    loadExtContent();
  }

  var contentWidth = 0;
  var waitBodyTimer = 0;
  var curKKTitleHeight = 0;
  var firstScale = true;
  function tryToResacle() {
    var times = 0;
    waitBodyTimer = setInterval(function() {
      var bodyW = document.documentElement.clientWidth;
      if ((bodyW == contentWidth) || (++times >= 5)) {
        reScale();
        clearInterval(waitBodyTimer);
      }
    }, 100)
  }

  function adjustAbsoluteTop(nodes, d) {
    for (var i = 0; i < nodes.length; ++i) {
      var node = nodes[i];
      adjustAbsoluteTop(nodes[i].children, d);
      if (node.style.position == "absolute" && node.offsetParent == document.body) {
        var t  = parseInt(node.style.top) || d;
        if (!firstLoad || t < d) {
          addClass(node, NE_CLS_CH_TOP);
          node.style.top = d + "px";
        }
      }
    }
  }
  ////Java<--->JS////
  window.adjustKKTitleBar = function(kkHeight) {
    if (undefined === kkHeight) {
      kkHeight = parseFloat(attr(document.documentElement, "kitkat_title_height")) || 0;
    }
    if (kkHeight > 0 && curKKTitleHeight != kkHeight) {
      //curKKTitleHeight = kkHeight;
      //var id = "163mail-kktitle-bar";
      //var kkBar = document.getElementById(id);
      //if (!kkBar) {
      //  kkBar = document.createElement("div");
      //  kkBar.style.width = "1px";
      //  kkBar.style.background = "red";
      //  kkBar.id = id;
      //  document.documentElement.insertBefore(kkBar, document.body);
      //}
      //kkBar.style.height = kkHeight + "px";
      addImportantStyle(document.documentElement, "margin-top", kkHeight + "px");
      var nodes;
      if (firstLoad) {
        nodes = document.body.children;
      } else {
        nodes = queryAll(document.body, "." + NE_CLS_CH_TOP);
      }
      adjustAbsoluteTop(nodes, kkHeight);
      firstLoad = false;
    }
  }

  function cleanAllStyle() {
    var nodes = queryAll(document.documentElement, "." + NE_CLS_SCALE);
    for (var i = 0; i < nodes.length; ++i) {
      var node = nodes[i];
      removeClass(node, NE_CLS_SCALE);
      cleanStyle(node, "-webkit-transform");
      cleanStyle(node, "-webkit-transform-origin");
      cleanStyle(node, "margin-right");
      cleanStyle(node, "margin-bottom");
      cleanStyle(node, "height");
      cleanStyle(node, "width");
    }
    nodes = queryAll(document.body, "." + NE_CLS_CH_HEIGHT);
    for (var i = 0; i < nodes.length; ++i) {
      var node = nodes[i];
      removeClass(node, NE_CLS_CH_HEIGHT);
      cleanStyle(node, "height");
    }
    nodes =  queryAll(document.body, "." + NE_CLS_CH_WIDTH);
    for (var i = 0; i < nodes.length; ++i) {
      var node = nodes[i];
      removeClass(node, NE_CLS_CH_WIDTH);
      var w = attr(node, originW);
      if ((parseInt(w) || 0) > 0) {
         addImportantStyle(node, "width", w + "px");
      }
    }
  }

  function handleAllImages() {
    var imgs = queryAll(document.body, "img");
    var r = /^cid:(.*)/i
    for (var i = 0; i < imgs.length; ++i) {
      var img = imgs[i];
      var src = img.src;
      if (r.test(src)) {
        var tokens = src.match(r);
        var cid = tokens[1];
        if (cid && cid.length > 0) {
          if (!imgsMap[cid]) {
            imgsMap[cid] = [];
          }
          img.src = "file:///android_asset/bg.png?r=" + i;
          addClass(img, 'ntes-wait-for-download-img');
          imgsMap[cid].push(img);
        }
      }
    }
  }
  
  var delayScaleTaskCount = 0;

  function inlineOnloaded() {
    if (delayScaleTaskCount == 0) {
      delayScaleTaskCount++;
      window.setTimeout(function() {
        delayScaleTaskCount--;
        reScale();
      }, 500);
    }
  }

  function processBigAttachment() {
    var div = document.getElementById("QQMailBigAttach");
    if (!!div) {
      div.style.display="none";
    }
    div = document.getElementById("divNeteaseBigAttach");
    if (!!div) {
      div.style.display="none";
    }
  }
  

  window.reScale = function() {
    cleanAllStyle();
    var retry = startScale();
    // 一些变态的邮件，第一次缩放后，scroll width不会变小，导致页面
    // 显示过小
    if (retry) {
      cleanAllStyle();
      startScale();
    }
    if (firstScale) {
      firstScale = false;
      // 延时100ms，防止4.4系统的title调整未完成
      setTimeout(function() {
        prompt(JSON.stringify({name:"$NE-MAIL-APP-ADJUST-DONE$"}));
      }, 100);
    }
  }

  window.setBodyPadding = function(top, bottom) {
    addImportantStyle(document.body, "padding-bottom", bottom + "px");
  }

  window.orientationChanged = function(newContentWidth, newScreenWidth) {
   contentWidth = newContentWidth;
   clearInterval(waitBodyTimer);
   tryToResacle();
  }

  window.getText = function() {
    var bodyText = document.body.innerText;
    if (undefined === bodyText) {
      bodyText = "";
    }
    prompt(JSON.stringify({name:"$NE-MAIL-APP-SET-TEXT$", text:bodyText}));
  }
  window.showInline = function(cid, src) {
    var imgs = imgsMap[cid];
    if (imgs) {
      for (var i = 0; i < imgs.length; ++i) {
        var img = imgs[i];
        removeClass(img, 'ntes-wait-for-download-img');
        img.onload = inlineOnloaded;
        img.src = "file:///" + src;
      }
    }
  }
  ////Java<--->JS////
  adjustKKTitleBar();
  handleAllImages();
})()

