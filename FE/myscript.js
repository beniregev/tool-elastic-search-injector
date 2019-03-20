var hostname = "carmitd-t460";
var port = "9005"

function InjectCalls(){
    numOfInteractions = Number(document.getElementById("numOfInteractions").value);
    bulkSize = Number(document.getElementById("bulkSize").value);
    var xhr = new XMLHttpRequest();
    var url = 'http://' + hostname + ':' + port +'/Elastic-Search-Injector/send-data';
    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-type', 'application/json');
    var json_text = "{\n" +
        "  \"bulkSize\": "+ bulkSize+ ",\n" +
        "  \"numOfInteractions\": "+ numOfInteractions +"\n" +
        "}";
    xhr.send(json_text);


}