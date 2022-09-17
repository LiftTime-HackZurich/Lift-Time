

$('a.nav-link').on('hidden.bs.tab', function (e) {
   if($(e.target).attr("href")  === "#v-pills-profile"){
      $("video").get(0).play();
   }
   // newly activated tab
  e.relatedTarget // previous active tab
})

$(document).ready(function(){
  $("video").attr("autoplay","autoplay" )
})


function getCookie(name) {
  var dc = document.cookie;
  var prefix = name + "=";
  var begin = dc.indexOf("; " + prefix);
  if (begin == -1) {
      begin = dc.indexOf(prefix);
      if (begin != 0) return null;
  }
  else
  {
      begin += 2;
      var end = document.cookie.indexOf(";", begin);
      if (end == -1) {
      end = dc.length;
      }
  }
  // because unescape has been deprecated, replaced with decodeURI
  //return unescape(dc.substring(begin + prefix.length, end));
  return decodeURI(dc.substring(begin + prefix.length, end));
} 
function setCookie(name,value,days) {
  var expires = "";
  if (days) {
      var date = new Date();
      date.setTime(date.getTime() + (days*24*60*60*1000));
      expires = "; expires=" + date.toUTCString();
  }
  document.cookie = name + "=" + (value || "")  + expires + "; path=/";
}
function eraseCookie(name) {   
  document.cookie = name +'=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
}

async function checkCookie() {
  let key = getCookie("Key");

  if (key == null) {
    const response = await fetch("/getKey");
    var data = await response.json();
    setCookie("Key", data);
  }
  
}

checkCookie();
