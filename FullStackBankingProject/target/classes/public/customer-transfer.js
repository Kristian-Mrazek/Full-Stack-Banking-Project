/**
 * 
 */

window.onload = function() {
	getAccountsByUsername();
	
	let transferButton = document.getElementById("transferButton");
	transferButton.addEventListener('click', transfer);
	
}

function transfer() {
	
	//event.preventDefault();
	
	let xhr = new XMLHttpRequest();
	
	const url = "transfer";
	
	let accountId1 = document.getElementById("accountIdTransferOut").value;
	let accountId2 = document.getElementById("accountIdTransferIn").value;
	let amount = document.getElementById("transferAmount").value;
	
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
					getAccountsByUsername();
				}
			
		}
		
	}
	
	xhr.open("PUT", url);
	
	let account1 = {
		id: accountId1,
		balance: amount
	};
	
	let account2 = {
		id: accountId2,
		balance: amount
	};
	
	xhr.send(JSON.stringify([account1, account2]));
	
}

function getAccountsByUsername() {
	
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
					console.log(xhr.responseText);
					
					let accountList = JSON.parse(xhr.responseText);
					
					console.log(accountList);
					
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