(function() {
  var version = 1.6;
  var urlPrefix = 'js://invoke?';
  var nativeEventPrefix = 'appHostEvent_';
  var proxyUrlPrefix = 'js://proxyInvoke?';

  var isAndroid = (function() {
    return navigator.userAgent.match( /android/i );
  })();

  var appHost = function () {
    this.serialNo = 0;
    this.callbacks = {};
  };

  appHost.prototype.version = version;

  appHost.prototype.invoke = function(name, param, cb) {
    ++this.serialNo;
    var serialNo = this.serialNo;
    if (!!cb) {
      this.callbacks[serialNo] = cb;
    }
    if (isAndroid) {
      this.invokeOnAndroid(name, param, this.serialNo);
    } else {
      this.invokeOnIos(name, param, this.serialNo);
    }
  };

  appHost.prototype.invokeOnAndroid = function (name, param, serialNo) {
    var obj = {"name": name, "params": param, "version":version, "serial":serialNo};
    var command = encodeURIComponent(JSON.stringify(obj));
    if(command) {
      window.prompt(urlPrefix + command);
    }
  };

  appHost.prototype.invokeOnIos = function (name, param, serialNo) {
    var location = urlPrefix + 'version=' + version + '&name=' + encodeURIComponent(name) + '&param=' + encodeURIComponent(JSON.stringify(param)) + '&serial=' + serialNo;
    window.console.log(location);
    window.location = location; 
  };

  appHost.prototype.proxyInvoke = function(product, name, param, cb) {
    ++this.serialNo;
    var serialNo = this.serialNo;
    if (!!cb) {
      this.callbacks[serialNo] = cb;
    }
    if (isAndroid) {
      this.proxyInvokeOnAndroid(product, name, param, this.serialNo);
    } else {
      this.proxyInvokeOnIos(product, name, param, this.serialNo);
    }
  };

  appHost.prototype.proxyInvokeOnAndroid = function (product, name, param, serialNo) {
    var obj = {"product": product, "name": name, "params": param, "version":version, "serial":serialNo};
    var command = encodeURIComponent(JSON.stringify(obj));
    if(command) {
      window.prompt(proxyUrlPrefix + command);
    }
  };

  appHost.prototype.proxyInvokeOnIos = function (product, name, param, serialNo) {
    var location = proxyUrlPrefix + 'version=' + version + '&product=' + encodeURIComponent(product) + '&name=' + encodeURIComponent(name) + '&param=' + encodeURIComponent(JSON.stringify(param)) + '&serial=' + serialNo;
    window.console.log(location);
    window.location = location;
  };

  appHost.prototype.onInvokeResult = function(serailNo, param) {
    var cb = this.callbacks[serailNo];
    if (!!!cb) {
        window.console.log("non-existed serail: " + serailNo);
        return;
    }
    delete this.callbacks[serailNo];
    cb(param);
  };

  appHost.prototype.onProxyInvokeResult = function(serailNo, param) {
    var cb = this.callbacks[serailNo];
    if (!!!cb) {
      window.console.log("non-existed serail: " + serailNo);
      return;
    }
      delete this.callbacks[serailNo];
      cb(param);
  };

  appHost.prototype.onNativeCall = function(obj) {
    if (obj) {
      var name = obj["name"];
      var params = obj["params"];
      var ver = obj["version"];
      if ("onInvokeResult" === name) {
        var id = params["serial"];
        delete params["serial"];
        this.onInvokeResult(id, params);
      } else if ("onProxyInvokeResult" === name) {
        var id = params["serial"];
        delete params["serial"];
        this.onProxyInvokeResult(id, params);
      }else if ("trigger" == name) {
        appHost.event.trigger(params["type"], params["detail"]);
      }
    }
  };

  var appHostEvent = function() {};

  appHostEvent.prototype.on = function(type, cb) {
    document.addEventListener(nativeEventPrefix + type, cb, false);
  };

  appHostEvent.prototype.trigger = function(type, detail) {
    var ev = document.createEvent('Event');
    ev.initEvent(nativeEventPrefix + type, true, true);
    ev.detail = detail;
    document.dispatchEvent(ev);
  };

  appHost.prototype.launchActivity = function (url) {
    this.invoke('launchActivity', {'url' : url});
  };

  appHost.prototype.log = function (msg) {
    this.invoke('log', {'msg' : msg});
  };

  if (!!!window.appHost) {
    var appHost = new appHost;
    appHost.event = new appHostEvent;
    window.appHost = appHost;
    if (!!!window.console || !!!window.console.log) {
      window.console.log = window.appHost.log;
    }
    if (!!window.appHostOnLoad) {
      window.appHostOnLoad();
    }
  }
})();

