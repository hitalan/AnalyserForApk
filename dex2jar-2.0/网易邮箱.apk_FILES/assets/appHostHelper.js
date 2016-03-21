(function(){
  var appHostHelper = {};
  var nativeEventPrefix = 'appHostEvent_';
  appHostHelper.isReady = false;

  var triggerReady = function() {
    if (appHostHelper.readyCB) {
      appHostHelper.readyCB();
    }
  }

  appHostHelper.onReady = function(cb) {
    appHostHelper.readyCB = cb;
    if (appHostHelper.isReady) {
      triggerReady();
    }
  }

  var setInitEvent = function() {
    document.addEventListener(nativeEventPrefix + "ready", function() {
      appHostHelper.isReady = true;
      triggerReady();
    }, false);
  }
  window.appHostHelper = appHostHelper;
  setInitEvent();
})()
