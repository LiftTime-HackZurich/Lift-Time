let host = "http://192.168.221.251:8080", methodPrefix = "/lift/api/v1", key,_roomId;
async function getKey() {
  if (key === undefined) {
    key = localStorage.getItem("key");
    let urlString = `${host}/lift/api/v1/getKey` + (key ? "/" + key : "");
    const url = new URL(urlString);
    const response = await fetch(url);
    key = await response.json();
    localStorage.setItem("key", key.text());
  }
}
getKey();
console.log(key);

async function selectTheme(themeValue) {
  if (_roomId) {
    let urlString = `${host}${methodPrefix}/meetings/${key}/themes/${themeValue}/roomId/${_roomId}`;
    const url = new URL(urlString);
    const response = await fetch(url);
    let _res = await response.text();
    if (_res !== "OK") {
      //Match another visitor
      _roomId = _res;
      // hangUp();

      joinRoom(_res);

      waiting(false);
    } else {
      _roomId = await createRoom(function () {
        waiting(false);
      });
    }
  }
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
