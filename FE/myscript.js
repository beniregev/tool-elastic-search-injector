var hostname = "localhost";
var port = "9006";

function InjectCalls(){
    numOfInteractions = Number(document.getElementById("numOfInteractions").value);
    numberOfBulks = Number(document.getElementById("numberOfBulks").value);
    var xhr = new XMLHttpRequest();
    var url = 'http://' + hostname + ':' + port +'/Elastic-Search-Injector/send-data';
    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-type', 'application/json');
    var json_text = "{\n" +
        "  \"numberOfBulks\": "+ numberOfBulks+ ",\n" +
        "  \"numOfInteractions\": "+ numOfInteractions +"\n" +
        "}";
    xhr.send(json_text);


}