const toasts = document.getElementById('toasts');

function toast(message, type) {
    if (message == "null")
        message = "Something went wrong";
    if (type == "null")
        type = "info"

    createNotification(message, type);
}

function createNotification(message, type) {
    const notif = document.createElement('div');
    notif.classList.add('toast-custom');
    notif.classList.add(type);
    notif.innerText = message;
    toasts.appendChild(notif);
    setTimeout(() => {
        notif.remove()
    }, 3000)
}
function reset_all(){
    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };
    fetch("/employee/reset", requestOptions)
        .then(response => response.text())
        .then(result => {
            result => console.log(result)
            localStorage.setItem("employee", "");
            localStorage.setItem("salary", "");
            localStorage.setItem("company", "");
            window.location.href = '/employee/new';
        })
        .catch(error => console.log('error', error));
    location.reload();
}

function reset_employee(key){
    var requestOptions = {
        method: 'GET',
        redirect: 'follow'
    };
    fetch("/employee/reset", requestOptions)
        .then(response => response.text())
        .then(result => {
            result => console.log(result)
            localStorage.setItem(key, "");
            location.reload();
        })
        .catch(error => console.log('error', error));
    location.reload();
}