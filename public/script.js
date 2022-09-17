
let host = "http://localhost:8080", methodPrefix = "/lift/api/v1", key , _roomId;
async function getKey() {
  if(key === undefined) {
    let urlString = `${host}/lift/api/v1/getKey`;
    const url = new URL(urlString);
    const response = await fetch(url);
    key = await response.json();
  }
}
key= getKey();
console.log(key);

async function selectTheme(themeValue){
  _roomId = await createRoom();
  if(_roomId)
  {
    let urlString = `${host}${methodPrefix}/meetings/${key}/themes/${themeValue}/roomId/${_roomId}`;
    const url = new URL(urlString);
    const response = await fetch(url);
    if(response.text !== "OK") {//Match another visitor
      roomId = response.text;
      hangUp();
      joinRoom(_roomId);
      waiting(false);
    }
    else
      waiting(true);
  }
}

function waiting(status){
  if(status)
    $("div#waitingDiv").show();
  else
    $("div#waitingDiv").hide();    
}

$("#pillsTheme button").on("click" ,async function(){
  selectTheme($(this).attr("value"));
});

$('a.nav-link').on('hidden.bs.tab', function (e) {
  if ($(e.target).attr("href") === "#v-pills-profile") {
    let vid = document.getElementById("myVideo");
    if(vid)
    vid.pause();

  }
  // newly activated tab
  e.relatedTarget // previous active tab
})

$('a.nav-link').on('shown.bs.tab', function (e) {
  if ($(e.target).attr("href") === "#v-pills-profile") {
    let vid = document.getElementById("myVideo");
    if(vid)
    vid.play();

  }
  // newly activated tab
  e.relatedTarget // previous active tab
})
$(document).ready(function(){
  //createRoom();
  openUserMedia();
});

