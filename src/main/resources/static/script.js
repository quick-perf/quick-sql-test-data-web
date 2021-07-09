const loadDoc = function() {
    debugger;
    let xhttp = new XMLHttpRequest();
    let queryArgument = document.getElementById("requestField").value;
    const url = "v0/api/request";

    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            let tr = document.createElement("div");
            tr.setAttribute("class", "row")
            let input = document.createElement("div");
            input.setAttribute("class", "cell");
            input.innerHTML = queryArgument;
            let outPut = document.createElement("div");
            outPut.setAttribute("class", "cell");
            outPut.innerHTML = this.responseText;

            document.getElementById("code").append(tr);
            tr.append(input);
            tr.append(outPut);
        }
    };
    xhttp.open("GET", url + "?query=" + queryArgument, true);
    xhttp.send(url);
}
