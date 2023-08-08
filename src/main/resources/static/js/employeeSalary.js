
window.addEventListener('load', function () {
    var empId = localStorage.getItem('empId');
    document.getElementById('empId').value = empId;

    // const date = moment();
    // document.getElementById('txnDate').value = date.format('yyyy-MM-DD');
    var salary = localStorage.getItem("salary");
    if (salary != undefined) {
        salary2 = JSON.parse(salary);
        console.log(salary2);
        // document.getElementById('salary').value = salary2.salary;
        document.getElementById('basicSalary').value = salary2.basicSalary;
        document.getElementById('hra').value = salary2.hra;
        document.getElementById('conveyanceAllowance').value = salary2.conveyanceAllowance;
        document.getElementById('performanceBased').value = salary2.performanceBased;
        document.getElementById('statutoryAllowance').value = salary2.statutoryAllowance;
        document.getElementById('medical').value = salary2.medical;
        document.getElementById('accountNo').value = salary2.accountNo;
        document.getElementById('uanNo').value = salary2.uanNo;
        document.getElementById('bankName').value = salary2.bankName;
        // document.getElementById('txnDate').value = salary2.txnDate;
        // document.getElementById('ctc').value = salary2.ctc;
        document.getElementById('tds').value = salary2.tds;
        document.getElementById('pfPercent').value = salary2.pfPercent;
        document.getElementById('location').value = salary2.location;

        document.getElementById('reset-btn-id').innerHTML=`<div class="form-row submit-btn reset-btn" >
                    <div class="input-data">
                        <div class="inner"></div>
                        <input type="button" value="Reset" onclick="reset_employee('salary')"/>
                    </div>
                </div>`;
    }
})

const form = document.getElementById("form");
form.addEventListener("submit", saveEmployeeSalary);


function saveEmployeeSalary(event) {
    event.preventDefault();

    var myHeaders = new Headers();
    myHeaders.append("accept", "*/*");
    myHeaders.append("Content-Type", "application/json");

    var raw = JSON.stringify({
        "empId": document.getElementById('empId').value,
        // "salary": document.getElementById('salary').value,
        "basicSalary": document.getElementById('basicSalary').value,
        "hra": document.getElementById('hra').value,
        "conveyanceAllowance": document.getElementById('conveyanceAllowance').value,
        "performanceBased": document.getElementById('performanceBased').value,
        "statutoryAllowance": document.getElementById('statutoryAllowance').value,
        "medical": document.getElementById('medical').value,
        "accountNo": document.getElementById('accountNo').value,
        "uanNo": document.getElementById('uanNo').value,
        "bankName": document.getElementById('bankName').value,
        // "txnDate": document.getElementById('txnDate').value,
        // "ctc": document.getElementById('ctc').value,
        "tds": document.getElementById('tds').value,
        "pfPercent": document.getElementById('pfPercent').value,
        "location": document.getElementById('location').value,
    });

    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    fetch("/api/v1/employeeSalary/save", requestOptions)
        .then(response => response.text())
        .then(result => {
            console.log(result)
            result = JSON.parse(result);
            if (result.status == "201") {
                localStorage.setItem("salary", raw);
                window.location.href = '/employee/company';
            } else {
                result.message.map((message) => {
                    toast(message, "error");
                    // document.getElementById("loader").style.display = "none";
                });
            }
        })
        .catch(error => {
            console.log('error', error)
            toast("Failed to Save Employee Salary Details", "error");
        });
}