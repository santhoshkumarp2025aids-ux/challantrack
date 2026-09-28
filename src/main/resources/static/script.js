/* =====================================================
   API URL
===================================================== */

const API = "http://localhost:8080/api";



/* =====================================================
   PAGE LOAD
===================================================== */

window.onload = function () {

    loadDashboard();

};



/* =====================================================
   SHOW SECTION
===================================================== */

function showSection(sectionName) {

    event.preventDefault();


    const sections = [

        "dashboardSection",

        "issueChallanSection",

        "vehiclesSection",

        "violationsSection",

        "challansSection",

        "paymentsSection"

    ];


    sections.forEach(function (section) {

        const element =
            document.getElementById(section);

        if (element) {

            element.classList.add("hidden");

        }

    });



    /* DASHBOARD */

    if (sectionName === "dashboard") {

        document
            .getElementById("dashboardSection")
            .classList.remove("hidden");

        loadDashboard();

    }



    /* VEHICLES */

    if (sectionName === "vehicles") {

        document
            .getElementById("vehiclesSection")
            .classList.remove("hidden");

        loadVehicles();

    }



    /* VIOLATIONS */

    if (sectionName === "violations") {

        document
            .getElementById("violationsSection")
            .classList.remove("hidden");

        loadViolations();

    }



    /* CHALLANS */

    if (sectionName === "challans") {

        document
            .getElementById("challansSection")
            .classList.remove("hidden");

        loadChallans();

    }



    /* PAYMENTS */

    if (sectionName === "payments") {

        document
            .getElementById("paymentsSection")
            .classList.remove("hidden");

        loadPayments();

    }

}



/* =====================================================
   ISSUE CHALLAN FORM
===================================================== */

function issueChallan() {


    /* Hide all sections */

    const sections = [

        "dashboardSection",

        "issueChallanSection",

        "vehiclesSection",

        "violationsSection",

        "challansSection",

        "paymentsSection"

    ];


    sections.forEach(function (section) {

        const element =
            document.getElementById(section);

        if (element) {

            element.classList.add("hidden");

        }

    });



    /* Show Issue Challan section */

    document
        .getElementById("issueChallanSection")
        .classList.remove("hidden");



    /* Load vehicles */

    loadVehiclesForForm();



    /* Load violations */

    loadViolationsForForm();



    /* Set today's date */

    setTodayDate();

}



/* =====================================================
   LOAD VEHICLES FOR ISSUE CHALLAN FORM
===================================================== */

async function loadVehiclesForForm() {

    try {

        const response =
            await fetch(`${API}/vehicles`);


        if (!response.ok) {

            throw new Error(
                "Unable to load vehicles"
            );

        }


        const vehicles =
            await response.json();


        const select =
            document.getElementById(
                "vehicleId"
            );


        select.innerHTML =
            `<option value="">
                Select Vehicle
            </option>`;


        vehicles.forEach(function (vehicle) {

            const option =
                document.createElement("option");


            option.value =
                vehicle.id;


            option.textContent =
                vehicle.vehicleNumber +
                " - " +
                vehicle.ownerName;


            select.appendChild(option);

        });

    }
    catch (error) {

        console.error(
            "Vehicle form error:",
            error
        );


        alert(
            "Unable to load vehicles."
        );

    }

}



/* =====================================================
   LOAD VIOLATIONS FOR ISSUE CHALLAN FORM
===================================================== */

async function loadViolationsForForm() {

    try {

        const response =
            await fetch(
                `${API}/violation-types`
            );


        if (!response.ok) {

            throw new Error(
                "Unable to load violations"
            );

        }


        const violations =
            await response.json();


        const select =
            document.getElementById(
                "violationTypeId"
            );


        select.innerHTML =
            `<option value="">
                Select Violation
            </option>`;


        violations.forEach(function (violation) {

            const option =
                document.createElement("option");


            option.value =
                violation.id;


            option.textContent =
                violation.violationName +
                " - ₹" +
                violation.baseFine;


            select.appendChild(option);

        });

    }
    catch (error) {

        console.error(
            "Violation form error:",
            error
        );


        alert(
            "Unable to load violation types."
        );

    }

}



/* =====================================================
   SET TODAY'S DATE
===================================================== */

function setTodayDate() {

    const dateInput =
        document.getElementById(
            "challanDate"
        );


    const today =
        new Date()
            .toISOString()
            .split("T")[0];


    dateInput.value = today;

}



/* =====================================================
   SUBMIT ISSUE CHALLAN FORM
===================================================== */

document.addEventListener(
    "DOMContentLoaded",
    function () {


        const form =
            document.getElementById(
                "challanForm"
            );


        if (!form) {

            return;

        }


        form.addEventListener(
            "submit",
            async function (event) {


                event.preventDefault();



                /* Get selected values */

                const vehicleId =
                    document.getElementById(
                        "vehicleId"
                    ).value;


                const violationTypeId =
                    document.getElementById(
                        "violationTypeId"
                    ).value;


                const challanDate =
                    document.getElementById(
                        "challanDate"
                    ).value;



                /* Validate */

                if (!vehicleId) {

                    alert(
                        "Please select a vehicle."
                    );

                    return;

                }


                if (!violationTypeId) {

                    alert(
                        "Please select a violation."
                    );

                    return;

                }


                if (!challanDate) {

                    alert(
                        "Please select a challan date."
                    );

                    return;

                }



                /* Create request body */

                const challanData = {

                    vehicle: {

                        id: Number(vehicleId)

                    },

                    violationType: {

                        id: Number(violationTypeId)

                    },

                    challanDate:
                    challanDate

                };



                try {


                    const response =
                        await fetch(
                            `${API}/challans`,
                            {

                                method: "POST",

                                headers: {

                                    "Content-Type":
                                        "application/json"

                                },

                                body:
                                    JSON.stringify(
                                        challanData
                                    )

                            }
                        );



                    const result =
                        await response.json();



                    /* Backend error */

                    if (!response.ok) {

                        console.error(
                            "Backend error:",
                            result
                        );


                        let message =
                            "Unable to issue challan.";


                        if (result.error) {

                            message =
                                result.error;

                        }


                        alert(message);

                        return;

                    }



                    /* Success */

                    alert(

                        "Challan issued successfully!\n\n" +

                        "Challan ID: #" +
                        result.id +

                        "\nVehicle: " +
                        result.vehicle.vehicleNumber +

                        "\nViolation: " +
                        result.violationType.violationName +

                        "\nFine: ₹" +
                        result.fineAmount +

                        "\nStatus: " +
                        result.paymentStatus

                    );



                    /* Clear form */

                    document
                        .getElementById(
                            "challanForm"
                        )
                        .reset();



                    /* Return dashboard */

                    document
                        .getElementById(
                            "issueChallanSection"
                        )
                        .classList.add("hidden");


                    document
                        .getElementById(
                            "dashboardSection"
                        )
                        .classList.remove("hidden");


                    /* Refresh dashboard */

                    loadDashboard();

                }
                catch (error) {

                    console.error(
                        "Issue challan error:",
                        error
                    );


                    alert(
                        "Unable to connect to the backend."
                    );

                }

            }
        );

    }
);



/* =====================================================
   DASHBOARD
===================================================== */

async function loadDashboard() {

    try {


        /* Get vehicles */

        const vehicleResponse =
            await fetch(
                `${API}/vehicles`
            );


        const vehicles =
            await vehicleResponse.json();



        /* Get challans */

        const challanResponse =
            await fetch(
                `${API}/challans`
            );


        const challans =
            await challanResponse.json();



        /* Vehicle count */

        document
            .getElementById(
                "vehicleCount"
            )
            .textContent =
            vehicles.length;



        /* Remove invalid old test records */

        const validChallans =
            challans.filter(
                function (challan) {

                    return challan.vehicle !== null &&
                        challan.vehicle !== undefined &&
                        challan.violationType !== null &&
                        challan.violationType !== undefined;

                }
            );



        /* Challan count */

        document
            .getElementById(
                "challanCount"
            )
            .textContent =
            validChallans.length;



        /* Paid */

        const paid =
            validChallans.filter(
                function (challan) {

                    return challan.paymentStatus ===
                        "PAID";

                }
            );



        document
            .getElementById(
                "paidCount"
            )
            .textContent =
            paid.length;



        /* Unpaid */

        const unpaid =
            validChallans.filter(
                function (challan) {

                    return challan.paymentStatus ===
                        "UNPAID";

                }
            );



        document
            .getElementById(
                "unpaidCount"
            )
            .textContent =
            unpaid.length;



        /* Recent challans */

        displayRecentChallans(
            validChallans
        );

    }
    catch (error) {

        console.error(
            "Dashboard error:",
            error
        );

    }

}



/* =====================================================
   RECENT CHALLANS
===================================================== */

function displayRecentChallans(
    challans
) {


    const tableBody =
        document.getElementById(
            "challanTableBody"
        );


    tableBody.innerHTML = "";



    if (challans.length === 0) {

        tableBody.innerHTML = `

            <tr>

                <td colspan="6">

                    <div class="message">
                        No challans found.
                    </div>

                </td>

            </tr>

        `;

        return;

    }



    challans.forEach(
        function (challan) {


            const row =
                document.createElement(
                    "tr"
                );


            const statusClass =
                challan.paymentStatus ===
                "PAID"
                    ? "paid"
                    : "unpaid";



            row.innerHTML = `

                <td>
                    #${challan.id}
                </td>


                <td>
                    ${challan.vehicle.vehicleNumber}
                </td>


                <td>
                    ${challan.violationType.violationName}
                </td>


                <td>
                    ₹${challan.fineAmount}
                </td>


                <td>

                    <span
                        class="status ${statusClass}">

                        ${challan.paymentStatus}

                    </span>

                </td>


                <td>

                    <button
                        class="view-btn"
                        onclick="viewChallan(${challan.id})">

                        View

                    </button>

                </td>

            `;


            tableBody.appendChild(
                row
            );

        }
    );

}



/* =====================================================
   VEHICLES
===================================================== */

async function loadVehicles() {

    try {

        const response =
            await fetch(
                `${API}/vehicles`
            );


        const vehicles =
            await response.json();


        const tableBody =
            document.getElementById(
                "vehicleTableBody"
            );


        tableBody.innerHTML = "";


        if (vehicles.length === 0) {

            tableBody.innerHTML = `

                <tr>

                    <td colspan="4">

                        <div class="message">
                            No vehicles found.
                        </div>

                    </td>

                </tr>

            `;

            return;

        }


        vehicles.forEach(
            function (vehicle) {


                const row =
                    document.createElement(
                        "tr"
                    );


                row.innerHTML = `

                    <td>
                        ${vehicle.id}
                    </td>

                    <td>
                        ${vehicle.vehicleNumber}
                    </td>

                    <td>
                        ${vehicle.ownerName}
                    </td>

                    <td>
                        ${vehicle.vehicleType}
                    </td>

                `;


                tableBody.appendChild(
                    row
                );

            }
        );

    }
    catch (error) {

        console.error(
            "Vehicle loading error:",
            error
        );

    }

}



/* =====================================================
   VIOLATIONS
===================================================== */

async function loadViolations() {

    try {

        const response =
            await fetch(
                `${API}/violation-types`
            );


        const violations =
            await response.json();


        const tableBody =
            document.getElementById(
                "violationTableBody"
            );


        tableBody.innerHTML = "";


        if (violations.length === 0) {

            tableBody.innerHTML = `

                <tr>

                    <td colspan="3">

                        <div class="message">
                            No violation types found.
                        </div>

                    </td>

                </tr>

            `;

            return;

        }


        violations.forEach(
            function (violation) {


                const row =
                    document.createElement(
                        "tr"
                    );


                row.innerHTML = `

                    <td>
                        ${violation.id}
                    </td>

                    <td>
                        ${violation.violationName}
                    </td>

                    <td>
                        ₹${violation.baseFine}
                    </td>

                `;


                tableBody.appendChild(
                    row
                );

            }
        );

    }
    catch (error) {

        console.error(
            "Violation loading error:",
            error
        );

    }

}



/* =====================================================
   ALL CHALLANS
===================================================== */

async function loadChallans() {

    try {

        const response =
            await fetch(
                `${API}/challans`
            );


        const challans =
            await response.json();


        displayChallanTable(
            challans
        );

    }
    catch (error) {

        console.error(
            "Challan loading error:",
            error
        );

    }

}



/* =====================================================
   DISPLAY CHALLANS
===================================================== */

function displayChallanTable(
    challans
) {


    const tableBody =
        document.getElementById(
            "allChallanTableBody"
        );


    tableBody.innerHTML = "";



    const validChallans =
        challans.filter(
            function (challan) {

                return challan.vehicle !== null &&
                    challan.vehicle !== undefined &&
                    challan.violationType !== null &&
                    challan.violationType !== undefined;

            }
        );



    if (validChallans.length === 0) {

        tableBody.innerHTML = `

            <tr>

                <td colspan="6">

                    <div class="message">
                        No challans found.
                    </div>

                </td>

            </tr>

        `;

        return;

    }



    validChallans.forEach(
        function (challan) {


            const row =
                document.createElement(
                    "tr"
                );


            const statusClass =
                challan.paymentStatus ===
                "PAID"
                    ? "paid"
                    : "unpaid";



            row.innerHTML = `

                <td>
                    #${challan.id}
                </td>


                <td>
                    ${challan.vehicle.vehicleNumber}
                </td>


                <td>
                    ${challan.violationType.violationName}
                </td>


                <td>
                    ₹${challan.fineAmount}
                </td>


                <td>
                    ${challan.challanDate}
                </td>


                <td>

                    <span
                        class="status ${statusClass}">

                        ${challan.paymentStatus}

                    </span>

                </td>

            `;


            tableBody.appendChild(
                row
            );

        }
    );

}



/* =====================================================
   OLD UNPAID CHALLANS
===================================================== */

async function loadOldUnpaid() {

    try {

        const response =
            await fetch(
                `${API}/challans/old-unpaid`
            );


        const challans =
            await response.json();


        displayChallanTable(
            challans
        );


        if (challans.length === 0) {

            alert(
                "No unpaid challans older than 30 days."
            );

        }

    }
    catch (error) {

        console.error(
            "Old unpaid challan error:",
            error
        );

    }

}



/* =====================================================
   PAYMENTS
===================================================== */

async function loadPayments() {

    try {

        const response =
            await fetch(
                `${API}/payments`
            );


        const payments =
            await response.json();


        const tableBody =
            document.getElementById(
                "paymentTableBody"
            );


        tableBody.innerHTML = "";


        if (payments.length === 0) {

            tableBody.innerHTML = `

                <tr>

                    <td colspan="5">

                        <div class="message">
                            No payment records found.
                        </div>

                    </td>

                </tr>

            `;

            return;

        }


        payments.forEach(
            function (payment) {


                const row =
                    document.createElement(
                        "tr"
                    );


                row.innerHTML = `

                    <td>
                        ${payment.id}
                    </td>


                    <td>
                        #${payment.challan.id}
                    </td>


                    <td>
                        ₹${payment.amountPaid}
                    </td>


                    <td>
                        ${payment.paymentDate}
                    </td>


                    <td>

                        <span class="status paid">
                            PAID
                        </span>

                    </td>

                `;


                tableBody.appendChild(
                    row
                );

            }
        );

    }
    catch (error) {

        console.error(
            "Payment loading error:",
            error
        );

    }

}



/* =====================================================
   VIEW CHALLAN
===================================================== */

async function viewChallan(id) {

    try {

        const response =
            await fetch(
                `${API}/challans/${id}`
            );


        const challan =
            await response.json();


        alert(

            "Challan ID: #" +
            challan.id +

            "\nVehicle: " +
            challan.vehicle.vehicleNumber +

            "\nOwner: " +
            challan.vehicle.ownerName +

            "\nViolation: " +
            challan.violationType.violationName +

            "\nFine: ₹" +
            challan.fineAmount +

            "\nDate: " +
            challan.challanDate +

            "\nStatus: " +
            challan.paymentStatus

        );

    }
    catch (error) {

        console.error(
            "View challan error:",
            error
        );


        alert(
            "Unable to load challan."
        );

    }

}