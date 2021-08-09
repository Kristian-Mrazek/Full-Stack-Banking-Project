/**
 *
 */

window.onload = function() {
	getUnvalidatedAccounts();
	
	let approveButton = document.getElementById("approveButton");
	approveButton.addEventListener('click', approve);
	
	let rejectButton = document.getElementById("rejectButton");
	rejectButton.addEventListener('click', reject);
	
}

function approve() {
	
	let xhr = new XMLHttpRequest();
	
	const url = "validate";
	
	let accountId = document.getElementById("accountIdValidate").value;
	
	xhr.onreadystatechange = function() {
		
		switch (xhr.readyState) {
			
			case 0:
				console.log("Nothing");
				break;
			case 1:
				console.log("Connection established");
				break;
			case 2:
				console.log("Request sent");
				break;
			case 3:
				console.log("Awaiting response");
				break;
			case 4:
				console.log("All done!");
				console.log(xhr.status);
				
				if (xhr.status == 200) {
					getUnvalidatedAccounts();
				}
			
		}
		
	}
	
	console.log(accountId);
	
	xhr.open("PUT", url);
	
	let account = {
		id: accountId,
	};
	
	xhr.send(JSON.stringify(account));
	
}

function reject() {
	
	let xhr = new XMLHttpRequest();
	
	const url = "validate";
	
	let accountId = document.getElementById("accountIdValidate").value;
	
	xhr.onreadystatechange = function() {
		
		switch (xhr.readyState) {
			
			case 0:
				console.log("Nothing");
				break;
			case 1:
				console.log("Connection established");
				break;
			case 2:
				console.log("Request sent");
				break;
			case 3:
				console.log("Awaiting response");
				break;
			case 4:
				console.log("All done!");
				console.log(xhr.status);
				
				if (xhr.status == 200) {
					getUnvalidatedAccounts();
				}
			
		}
		
	}
	
	console.log(accountId);
	
	xhr.open("DELETE", url);
	
	let account = {
		id: accountId,
	};
	
	xhr.send(JSON.stringify(account));
	
}

function getUnvalidatedAccounts() {
	
	let xhr = new XMLHttpRequest();
	
	const url = "validate";
	
	xhr.onreadystatechange = function() {
		
		switch (xhr.readyState) {
			
			case 0:
				console.log("Nothing");
				break;
			case 1:
				console.log("Connection established");
				break;
			case 2:
				console.log("Request sent");
				break;
			case 3:
				console.log("Awaiting response");
				break;
			case 4:
				console.log("All done!");
				console.log(xhr.status);
				
				if (xhr.status == 200) {
					
					let accountList = JSON.parse(xhr.responseText);
					
					accountList.forEach(
						element => {
							addRow(element);
						}
					)
					
				}
			
		}
		
	}
	
	xhr.open("GET", url);
	xhr.send();
	
}

function addRow(account) {
	
	let table = document.getElementById("validate-account-table");
	
	let tableRow = document.createElement("tr");
	let iDCol = document.createElement("td");
	let balanceCol = document.createElement("td");
	let statusCol = document.createElement("td");
	
	tableRow.appendChild(iDCol);
	tableRow.appendChild(balanceCol);
	tableRow.appendChild(statusCol);
	
	table.appendChild(tableRow);
	
	iDCol.innerHTML = account.id;
	balanceCol.innerHTML = "$" + (Math.round(account.balance * 100) / 100);
	statusCol.innerHTML = account.isApproved;
	
	
}