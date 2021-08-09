/**
 * 
 */

window.onload = function() {
	
	getAccountsByUsername();
	
}

function getAccountsByUsername() {
	
	console.log("QUESTIOM MARK???");
	
	let xhr = new XMLHttpRequest();
	
	const url = "bank";
	
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
	
	let table = document.getElementById("account-table");
	
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