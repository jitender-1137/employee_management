window.addEventListener('load', function () {
    let base64 = "";

    var company = localStorage.getItem("company");
    if (company != undefined) {
        emp2 = JSON.parse(company);
        document.getElementById('companyName').value = emp2.companyName;
        document.getElementById('companyMail').value = emp2.companyMail;
        document.getElementById('companyAddress').value = emp2.companyAddress;
        document.getElementById('contactNo').value = emp2.contactNo;
        filePath = emp2.companyLogo;

        imageToBase64(filePath);

        document.getElementById('reset-btn-id').innerHTML = `<div class="form-row submit-btn reset-btn" >
                    <div class="input-data">
                        <div class="inner"></div>
                        <input type="button" value="Reset" onclick="reset_employee('company')"/>
                    </div>
                </div>`;
    }
})

const form = document.getElementById("form");
form.addEventListener("submit", saveCompany);

function saveCompany(event) {
    event.preventDefault();

    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var raw = JSON.stringify({
        "companyName": document.getElementById('companyName').value,
        "companyMail": document.getElementById('companyMail').value,
        "companyAddress": document.getElementById('companyAddress').value,
        "companyLogo": filePath,
        "contactNo": document.getElementById('contactNo').value
    });
    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    fetch("/api/v1/company/save", requestOptions)
        .then(response => response.text())
        .then(result => {
            console.log(result)
            result = JSON.parse(result);
            if (result.status == 201) {
                localStorage.setItem("company", raw);
                window.location.href = '/employee/slip';
            } else {
                result.message.map((message) => {
                    toast(message, "error");
                    // document.getElementById("loader").style.display = "none";
                });
            }
        })
        .catch(error => {
            console.log('error', error)
            toast("Failed to Save Employee", "error");
        });
}

var filePath = "";

function uploadFile(id) {
    filePath = "";
    if (document.getElementById(id).files.length == 0) {
        toast("Please choose file", "info");
        return null;
    }
    var fileInput = document.getElementById(id);

    var myHeaders = new Headers();

    var formdata = new FormData();
    formdata.append("file", fileInput.files[0]);

    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: formdata,
        redirect: 'follow'
    };

    fetch("/api/v1/company/uploadLogo", requestOptions)
        .then(response => response.text())
        .then((result) => {
            console.log(result);
            result = JSON.parse(result);
            if (result.status == 200) {
                toast(result.message, "success");
                filePath = result.data;
                imageToBase64(filePath);
            } else {
                result.message.map((message) => {
                    toast(message, "error");
                });
            }
        })
        .catch((error) => {
            console.log("error", error),
                toast("Fail to upload logo, please try after some time", "error");
        });
}

function imageToBase64(path) {
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        redirect: 'follow'
    };

    fetch("/api/v1/company/imageToBase64?path=" + path, requestOptions)
        .then(response => response.text())
        .then(result => {
            result = JSON.parse(result);
            if (result.status == 200) {
                ShowimgEdit(result.data.image);
            }

        })
        .catch(error => {
            toast("Fail to show logo", "error");
        });
}

function ShowimgEdit(data) {
    html = ''
    html = `</p><div class="mx-2 my-2"><img class="mx-2" src="data:image/png;base64, ${data}" style="width:100px"/></div>`
    console.log(html);
    document.getElementById('Edit-images').innerHTML = html;
}
