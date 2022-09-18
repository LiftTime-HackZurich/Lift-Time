let host = "http://192.168.228.251:8080", methodPrefix = "/lift/api/v1", key,_roomId;
async function getKey() {
  if (!key ) {
    key = localStorage.getItem("key");
    if(!(typeof key === 'string' || key instanceof String))
      key = undefined;

    let urlString = `${host}/lift/api/v1/getKey` + (key ?"/" +  key : "");
    const url = new URL(urlString);
    const response = await fetch(url);
    key = await response.json();
    if(typeof key === 'string' || key instanceof String)
      localStorage.setItem("key", key);

      
  }

  if (key !== undefined)
    activateButtons();

    $("#spanLiftId").text(key);
}
getKey();
console.log(key);

async function selectTheme(themeValue) {
    const getPairs = await fetch(new URL(`${host}${methodPrefix}/getPair/${key}/themes/${themeValue}`));
    const pair =await  getPairs.text()
    if(!pair){
      _roomId = await createRoom();

      const response = await fetch(new URL(`${host}${methodPrefix}/setRoomId/${key}/themes/${themeValue}/roomId/${_roomId}`));
      await response.text();
      waiting(true);
    }
    else{
       _roomId = pair;
       joinRoom(_roomId);
       //$(".localVideoContainer").addClass("makeSmall");
       waiting(false);
    }
   
    
  
}

function activateButtons(){
  $.each($("#pillsTheme button") , function(i,val){
    $(val).removeAttr("disabled");
  })
}

function waiting(status) {
  if (status) $("div#waitingDiv").show();
  else $("div#waitingDiv").hide();
}

$("#pillsTheme button").on("click", async function () {
  selectTheme($(this).attr("value"));
});

$("a.nav-link").on("hidden.bs.tab", function (e) {
  if ($(e.target).attr("href") === "#v-pills-profile") {
    let vid = document.getElementById("myVideo");
    if (vid) vid.pause();
  }
  // newly activated tab
  e.relatedTarget; // previous active tab
});

$("a.nav-link").on("shown.bs.tab", function (e) {
  if ($(e.target).attr("href") === "#v-pills-profile") {
    let vid = document.getElementById("myVideo");
    if (vid) vid.play();
  }
  // newly activated tab
  e.relatedTarget; // previous active tab
});
$(document).ready(function () {
  //createRoom();
  openUserMedia();
});
