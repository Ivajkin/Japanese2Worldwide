/*var gameTable;
document.write("!");
function writeTable() {
	document.write('<table id="gameTable">');
	for(int i = 0; i<16; i++) {
		document.write('	<tr>');
		for(int j = 0; j<16; j++) {
			document.write('		<td>');
			document.write('			<img src="a.png"></img>');
			document.write('		</td>');
			gameTable[i][j].element = "!";
		}
		document.write('	</tr>');	
	}
	document.write('</table>');
}
writeTable();*/
/*var startgameform = document.getElementById("startgameform");
var gamelevellabel = document.createElement("input");
gamelevellabel.type = "text";
gamelevellabel.name = "gamelevellabel";
startgameform.insertAdjacentElement("afterBegin",gamelevellabel);
var startgameBtn = document.createElement("input");
startgameBtn.type = "button";
startgameBtn.name = "startgameBtn";
startgameform.insertAdjacentElement("afterEnd",startgameBtn);*/
var backgroundvariant = Math.round(Math.random()*3);
document.body.setAttribute("background","background"+backgroundvariant+".jpg");
//document.write(backgroundvariant);
var startgameBtn = document.getElementById("startgameBtn");
startgameBtn.onclick = 
function createGame(){
	var labelVal = document.getElementById("gamelevellabel");
	if (isNaN(labelVal.value)) {
		alert("Размер игрового поля указан неверно!");
		return;
	}
	if(labelVal.value%2!=0) {		
		labelVal.value++;
		window.alert("Размер игрового поля должен быть чётным числом.\nНовый размер: " + labelVal.value + "x" + labelVal.value);
	}
	var gamecardnumX = labelVal.value;
	var gamecardnumY = gamecardnumX;
	var gamecards = new Array(gamecardnumX);
	
	var fullgamecardvariantsChecked = new Array(
												document.getElementById("АВыбран").checked,
												document.getElementById("ЭВыбран").checked,
												document.getElementById("ИВыбран").checked,
												document.getElementById("УВыбран").checked,
												document.getElementById("ОВыбран").checked,
												document.getElementById("КАВыбран").checked,
												document.getElementById("КЭВыбран").checked,
												document.getElementById("КИВыбран").checked,
												document.getElementById("КУВыбран").checked,
												document.getElementById("КОВыбран").checked
												);
	document.body.removeChild(document.getElementById("startgameform"));
	// Проверка - выбран ли хоть один символ.
	{
		var someSymbolChecked = false;
		for(var i = 0; i<fullgamecardvariantsChecked.length; ++i) {
			if(fullgamecardvariantsChecked[i]) {
				someSymbolChecked = true;
				break;
			}
		}
		if(!someSymbolChecked) {
			window.alert("Нужно выбрать по крайней мере один символ.");
			document.getElementById("АВыбран").checked = true;
		}
	}
	var fullgamecardvariants = new Array("А", "Э", "И", "У", "О",
									"КА", "КЭ", "КИ", "КУ", "КО");
	//var gamecardvariants = new Array(3);
	//gamecardvariants[0] = fullgamecardvariants[Math.round(Math.random() * (fullgamecardvariants.length - 1))];
	//gamecardvariants[1] = fullgamecardvariants[Math.round(Math.random() * (fullgamecardvariants.length - 1))]; 
	//gamecardvariants[2] = fullgamecardvariants[Math.round(Math.random() * (fullgamecardvariants.length - 1))];
	// Храним набор случайно сгенерированных вариантов, каждой по паре.
	// Имена - ka ke ko...
	var cardnames = new Array(gamecardnumX*gamecardnumY);
	// Перевернута ли текущая карта - true/false.
	var cardflipped = new Array(gamecardnumX*gamecardnumY);
	// Существует ли карта - true/false.
	var cardexist = new Array(gamecardnumX*gamecardnumY);
	// Сначала всё поле делаем пустым.
	for(var i = 0; i<gamecardnumX*gamecardnumY; ++i) {
		cardexist[i] = false;
	}
	// Ищем пустую карту, заполняем, ищем следующую, заполняем её парой. Делаем пока поле не заполнится.
	var curr = 0;
	var gamefieldsize = gamecardnumX*gamecardnumY;
	for(var i = 0; i<gamefieldsize/2; ++i) {
		var currvariantNum = Math.round(Math.random() * (fullgamecardvariants.length - 1));
		while(!fullgamecardvariantsChecked[currvariantNum])
			currvariantNum = Math.round(Math.random() * (fullgamecardvariants.length - 1));
		var currname = fullgamecardvariants[currvariantNum];
		
		var currflipped = (Math.random()<0.5);
		curr += Math.round(Math.random()*gamefieldsize);
		curr %= gamefieldsize;
		while(cardexist[curr])
			curr = (curr+1)%gamefieldsize;
		cardnames[curr] = currname;
		cardflipped[curr] = currflipped;
		cardexist[curr] = true;
		
		
		curr += Math.round(Math.random()*gamefieldsize);
		curr %= gamefieldsize;
		while(cardexist[curr])
			curr = (curr+1)%gamefieldsize;
		cardnames[curr] = currname;
		cardflipped[curr] = !currflipped;
		cardexist[curr] = true;
		
		
		/*DEBUG*/
		/*var parentDiv = document.createElement("div");
				{
					gamecard = document.createElement("input");
					gamecard.setAttribute("type", "button");
					gamecard.setAttribute("align", "center");
					gamecard.width = 35;
					gamecard.height = 35;
					gamecard.src = null;
					gamecard.value = ". " + currname + " .";
					parentDiv.id = "flipped";
				}*/
	}
	var gamecardamount = gamecardnumX*gamecardnumY;
	// Проверяем, все ли поля заполнили.
	for(var i = 0; i<gamecardnumX*gamecardnumY; ++i) {
		if(!cardexist[i])
			window.alert("Не все игровые поля заполнены!");
	}
	
	var gamecardcurrentselected = null;
	function writeTable(){
		var table = document.getElementById("gameTable");
		gamecards[i] = new Array(gamecardnumY);
		for (var i = 0; i < gamecardnumX; i++) {
			var row = table.insertRow(i);
			row.height = 35;
			row.width = 35;
			for (var j = 0; j < gamecardnumY; j++) {
				var td = row.insertCell(j);
				td.width = 35;
				td.height = 35;
				var gamecard = null;
				var curr = gamecardnumX*j + i;
				var gamecardtype = cardnames[curr];//gamecardvariants[Math.round(Math.random() * (gamecardvariants.length - 1))];
				var parentDiv = document.createElement("div");
				// Перевёрнута ли текущая карта.
				if (cardflipped[curr]) {
					var img = document.createElement("img");
					img.src = gamecardtype + ".png";
					gamecard = img;
					parentDiv.id = "not flipped";
				}
				else {
					gamecard = document.createElement("input");
					gamecard.setAttribute("type", "button");
					gamecard.setAttribute("align", "center");
					gamecard.width = 35;
					gamecard.height = 35;
					gamecard.src = null;
					gamecard.value = gamecardtype;//". " + gamecardtype + " .";
					parentDiv.id = "flipped";
				}
				gamecard.id = gamecardtype;
				parentDiv.insertAdjacentElement("afterBegin", gamecard);
				td.insertAdjacentElement("afterBegin", parentDiv);
				gamecards[i,j] = gamecard;
				gamecard.onclick = function cardclicked(){
					if (gamecardcurrentselected == null) {
						/*gamecardtype = "";
						 for (var i = 0; i < img.src.length - 4; i++) {
						 gamecardtype += img.src[i];
						 }*/
						gamecardcurrentselected = this;
					}
					else {
						if (gamecardcurrentselected.id == this.id) {
							if(gamecardcurrentselected.parentElement.id!=this.parentElement.id) {
								gamecardcurrentselected.src = "";
								gamecardcurrentselected.value = "";
								this.src = "";
								this.value = "";
								this.parentNode.removeChild(this);
								gamecardcurrentselected.parentNode.removeChild(gamecardcurrentselected);
								// Карты на игровом поле заканиваются.
								gamecardamount -= 2;
								if(0 == gamecardamount) {
									window.alert("Победа!");
								}
							}
						}
						gamecardcurrentselected = null;
					}
				}
			}
		}
	}
	writeTable();
}