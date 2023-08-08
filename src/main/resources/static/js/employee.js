
window.addEventListener('load', function () {
    const date = moment();
    document.getElementById('doj').value = date.format('yyyy-MM-DD');

    var emp = localStorage.getItem("employee");
    if (emp != undefined) {
        emp2 = JSON.parse(emp);
        console.log(emp2);
        document.getElementById('name').value = emp2.name;
        document.getElementById('employeeMail').value = emp2.employeeMail;
        document.getElementById('contactNo').value = emp2.contactNo;
        document.getElementById('designation').value = emp2.designation;
        document.getElementById('companyEmpId').value = emp2.companyEmpId;
        document.getElementById('department').value = emp2.department;
        document.getElementById('address').value = emp2.address;
        document.getElementById('panNo').value = emp2.panNo;
        document.getElementById('doj').value = emp2.doj;

        document.getElementById('reset-btn-id').innerHTML=`<div class="form-row submit-btn reset-btn" >
                    <div class="input-data">
                        <div class="inner"></div>
                        <input type="button" value="Reset" onclick="reset_employee('employee')"/>
                    </div>
                </div>`;
    }
})

const form = document.getElementById("form");
form.addEventListener("submit", saveEmployee);

function saveEmployee(event) {
    event.preventDefault();
    localStorage.setItem('empId', "");

    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    var raw = JSON.stringify({
        "name": document.getElementById('name').value,
        "employeeMail": document.getElementById('employeeMail').value,
        "contactNo": document.getElementById('contactNo').value,
        "designation": document.getElementById('designation').value,
        "companyEmpId": document.getElementById('companyEmpId').value,
        "department": document.getElementById('department').value,
        "address": document.getElementById('address').value,
        "panNo": document.getElementById('panNo').value,
        "doj": document.getElementById('doj').value,
    });
    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    fetch("/api/v1/employee/save", requestOptions)
        .then(response => response.text())
        .then(result => {
            console.log(result)
            result = JSON.parse(result);
            if (result.status == 201) {
                localStorage.setItem("employee", raw);
                localStorage.setItem('empId', result.data.companyEmpId);
                window.location.href = '/employee/salary';
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

function checkPanNo(){
    let panNo = document.getElementById('panNo').value;
    document.getElementById('panNo').value = panNo.toUpperCase();
}