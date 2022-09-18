const dom = {};

function hideAll(){
  $(".displayInnerDiv").hide();
}


dom.createDiv = (type = "div", className) => {
  const element = document.createElement(type);
  element.className = className;
  return element;
};

dom.appendChild = (element, parent) => {
  document.querySelector(parent).innerHTML = element;
};

dom.setElevatorButtons = (count = 10) => {
  const buttons = new Array(count).fill(1).map((_, index) => {
    return `
        <div class="xs-pd">
            <button type="button" class="btn btn-block btn-lg">
              ${index}
            </button>
        </div>
    `;
  });

  const buttonsHtml = buttons.join("");

  dom.appendChild(buttonsHtml, ".button-container");
};

dom.setMainMenu = () => {
  /*const content = document.querySelector("#main-menu").innerHTML;
  $("#chat_video").hide();
  dom.appendChild(content, ".innerDisplay");*/

  hideAll();
  $(".mainMenuContainer").show();
};

dom.openChatMenu = () => {
  /*const content = document.querySelector("#chat-menu").innerHTML;
  dom.appendChild(content, ".innerDisplay");*/
  hideAll();
  $("#chat-menu").show();
};

dom.getRandomMatchFromSelectedTopic = (targetId) => {
  
    const data = targetId.replace("chat_", '').toUpperCase();
    console.log("Selected Topic", data);

    hideAll();
    $("#chat_video").show();
    /*const content = document.querySelector("#chat_video").innerHTML;
    //dom.appendChild(content, ".display");

    $("#chat_video").show();
    $(".innerDisplay").empty();*/
    selectTheme(data);
};

dom.setEventListeners = () => {
  document.querySelector(".display").addEventListener("click", (e) => {
    switch (e.target.id) {
      case "funny_videos":
        break;
      case "news":
        break;
      case "say_hi":
        dom.openChatMenu();
        break;
      case "traffic":
        break;
      case "weather":
        break;
      case "close-main-menu":
      case "close-chat-menu":
        location.reload();  
      //dom.setMainMenu();
        break;

      case "chat_sport":
      case "chat_magazin":
      case "chat_hoby":
      case "chat_disabilities":
        dom.getRandomMatchFromSelectedTopic(e.target.id);
        break;
    }
  });
};

dom.init = () => {
  dom.setElevatorButtons();
  dom.setMainMenu();
  dom.setEventListeners();
};

dom.init();
