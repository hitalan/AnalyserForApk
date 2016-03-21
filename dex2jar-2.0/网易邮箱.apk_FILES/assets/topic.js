function checkQuote(tips, imageUrl) {
	var oDoc = document;
	var oQuote = checkCommon() || checkQQ() || checkYahoo() || checkLiveWebmail() || checkLiveMail();
	var sOldDisplay = 'none';

	if (typeof oQuote === 'object') {
		oPanel = document.createElement('div');
		oPanel.id = 'ntes-mail-android-quote-controller';
		oPanel.setAttribute('style', 'height:20px;line-height:20px;margin-top:20px;margin-bottom:20px;color:#b0bac5;background-repeat:no-repeat');
    //oPanel.style.backgroundImage = "url("+imageUrl+")";
		oQuote.parentNode.insertBefore(oPanel, oQuote);
		togglePanel();

		var oController = document.getElementById('ntes-mail-android-quote-controller');
		oController.addEventListener("click", function() {
            togglePanel();
            reScale();
		});
	}

    function togglePanel() {
        var sNewDisplay = sOldDisplay;
        sOldDisplay = oQuote.style.display;
        oQuote.style.display = sNewDisplay;
        if (sNewDisplay == "none") {
        	oPanel.innerText = "显示引用邮件";
        } else {
        	oPanel.innerHTML = "隐藏引用邮件";
        }
    }

    function getStyle(oDiv) {
        try {
            var oStyle = oDiv.getAttribute('style');
            return (oStyle.cssText || oStyle).toLowerCase().replace(/[ \']/gi, '');
        } catch (e) {
        }
        return '';
    }

    function checkCommon() {
        try {
            var aQuotes = oDoc.getElementsByTagName('BLOCKQUOTE');
            for (var i = 0, oQuote; i < aQuotes.length; i += 1) {
                oQuote = aQuotes[i];
                // Netease
                if (oQuote.id === 'isReplyContent') {
                    return oQuote;
                }
                // Netease mail clients
                if (oQuote.id === 'ntes-flashmail-quote' ||
                    oQuote.id === 'ntes-iosmail-quote' ||
                    oQuote.id === 'ntes-andriodmail-quote') {
                    return oQuote;
                }
                // Gmail
                if (oQuote.className === 'gmail_quote') {
                    return oQuote;
                }
                // Thunder bird
                if (oQuote.parentNode === oDoc.body && oQuote.type === 'cite' && oQuote.getAttribute('cite').indexOf('mid') === 0) {
                    return oQuote;
                }
            }
        } catch (e) {
        }
        return false;
    }

    function checkQQ() {
        try {
            var aDivList = oDoc.getElementsByTagName('DIV');
            for (var i = 0, oDiv, oParent; i < aDivList.length; i += 1) {
                oDiv = aDivList[i];
                if (oDiv.innerHTML === '------------------&nbsp;原始邮件&nbsp;------------------') {
                    oParent = oDiv.parentNode;
                    if (oParent.parentNode === oDoc.body.childNodes[1]) {
                        return oParent;
                    }
                }
            }
        } catch (e) {
        }
        return false;
    }

    function checkYahoo() {
        try {
            var oFirstChild = oDoc.body.firstChild;
            var sStyle = 'font-family:timesnewroman,newyork,times,serif;font-size:12pt';
            if (getStyle(oFirstChild) === sStyle && getStyle(oFirstChild.childNodes[1]) === sStyle && getStyle(oFirstChild.childNodes[1].childNodes[1]) === sStyle) {
                return oFirstChild.childNodes[1].childNodes[1];
            }
            return false;
        } catch (e) {
        }
        return false;
    }

    function checkLiveWebmail() {
        try {
            var oHr = oDoc.getElementsByTagName('HR')[0],
                oQuote;

            if (oHr && oHr.id === 'stopSpelling') {
                var aChildList = oDoc.body.childNodes;

                for (var j = 0, nLen = aChildList.length, oItem; j < nLen; j += 1) {
                    oItem = aChildList[j];
                    if (oItem === oHr) {
                        oQuote = oDoc.createElement('DIV');
                        oHr.parentNode.insertBefore(oQuote, oHr);
                        oQuote.appendChild(oItem);
                    }
                    else {
                        if (oQuote) {
                            oQuote.appendChild(oItem);
                        }
                    }
                }
            }

            return oQuote;
        } catch (e) {
        }
        return false;
    }

    function checkLiveMail() {
        try {
            var oDiv = oDoc.body.childNodes[1],
                oQuote;

            if (getStyle(oDiv) === 'font:10pttahoma' && getStyle(oDiv.childNodes[1]) === 'background:#f5f5f5' && oDiv.childNodes[1].childNodes.length === 4) {
                var aChildList = oDoc.body.childNodes;

                for (var j = 0, nLen = aChildList.length, oItem; j < nLen; j += 1) {
                    oItem = aChildList[j];
                    if (oItem === oDiv) {
                        oQuote = oDoc.createElement('DIV');
                        oDiv.parentNode.insertBefore(oQuote, oDiv);
                        oQuote.appendChild(oItem);
                    }
                    else {
                        if (oQuote) {
                            oQuote.appendChild(oItem);
                        }
                    }
                }
            }

            return oQuote;
        } catch (e) {
        }
        return false;
    }
}
