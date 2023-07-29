
window.addEventListener('load', function () {
    var empId = localStorage.getItem('empId');
    document.getElementById('empId').value = empId;

    changeMaxValue();
    changeTemplate();

    var emp = localStorage.getItem("employee");
    var salary = localStorage.getItem("salary");
    var company = localStorage.getItem("company");
    if (emp != undefined && salary != undefined  && company != undefined  && company != "" && emp != "" && salary != "") {
        document.getElementById('reset-btn-id').innerHTML = `<div class="form-row submit-btn reset-btn" >
                    <div class="input-data">
                        <div class="inner"></div>
                        <input type="button" value="Reset" onclick="reset_all()"/>
                    </div>
                </div>`;
    }
})

function changeTemplate(){
    var template = document.getElementById('template').value;
    var html =`<div class="input-data-template">
                    <div class="inner"></div>
                    <p><img src="/image/templates/payslip_${template}.png" alt=${template}></p>
                </div>`;

    document.getElementById('template_pdf').innerHTML=html;

}

function changePaidDayValue() {
    var max = document.getElementById('paidDays').max;
    var current = document.getElementById('paidDays').value;
    var min = max - current;
    document.getElementById('unpaidDays').value = min;
}

function changeUnPaidDayValue() {
    var max = document.getElementById('unpaidDays').max;
    var current = document.getElementById('unpaidDays').value;
    var min = max - current;
    document.getElementById('paidDays').value = min;
}

function changeMaxValue() {
    // document.getElementById('month').value=1;
    var paidDays = document.getElementById('paidDays');
    var unpaidDays = document.getElementById('unpaidDays');
    var m = document.getElementById('month').value;
    if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
        paidDays.setAttribute('max', 31.0);
        unpaidDays.setAttribute('max', 31.0);
        paidDays.value = 31.0;
        unpaidDays.value = 0.0;
    }
    if (m == 4 || m == 6 || m == 9 || m == 11) {
        paidDays.setAttribute('max', 30.0);
        unpaidDays.setAttribute('max', 30.0);
        paidDays.value = 30.0;
        unpaidDays.value = 0.0;
    }
    if (m == 2) {
        if (document.getElementById('year').value % 4 == 0) {
            paidDays.setAttribute('max', 29.0);
            unpaidDays.setAttribute('max', 29.0);
            paidDays.value = 29.0;
            unpaidDays.value = 0.0;
        } else {
            paidDays.setAttribute('max', 28.0);
            unpaidDays.setAttribute('max', 28.0);
            paidDays.value = 28.0;
            unpaidDays.value = 0.0;
        }
    }
}

const form = document.getElementById("form");
form.addEventListener("submit", generateSlip);

function generateSlip(event) {
    event.preventDefault();

    var myHeaders = new Headers();
    myHeaders.append("accept", "*/*");
    myHeaders.append("Content-Type", "application/json");

    var raw = JSON.stringify({
        "empId": document.getElementById('empId').value,
        "month": document.getElementById('month').value,
        "year": document.getElementById('year').value,
        "paidDays": document.getElementById('paidDays').value,
        "unpaidDays": document.getElementById('unpaidDays').value,
        "template": document.getElementById("template").value
    });

    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    fetch("/api/v1/salarySlip/generate", requestOptions)
        .then(response => response.text())
        .then(result => {
            console.log(result)
            result = JSON.parse(result);
            if (result.status == '201') {
                window.open(result.data, '_blank');
            } else {
                if (result.code == "EM003")
                    window.location.href = '/employee/new';
                if (result.code == "EM008")
                    window.location.href = '/employee/salary';
                result.message.map((message) => {
                    toast(message, "error");
                    // document.getElementById("loader").style.display = "none";
                });
            }
        })
        .catch(error => {
            console.log('error', error)
            toast("Failed to Generate PaySlip", "error");
        });
}