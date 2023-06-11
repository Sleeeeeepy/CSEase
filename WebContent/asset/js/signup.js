function Check(host, userId){
    fetch(host + "/check/" + userId)
        .then(response => response.json())
        .then(json => json.result).then(result => {
            if (result = true) {
                //do if exist
            }
            else {
                //do if not exist
            }
        })
}

function ChangeTooltip(id, tooltip) {
    document.getElementById(id).setAttribute("title", tooltip);
}
