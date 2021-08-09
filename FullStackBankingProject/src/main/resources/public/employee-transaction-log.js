/**
 * 
 */
 
window.onload = function() {
	
	document.getElementById('inputFile').addEventListener('change', function() {
	
	let fileRead = new FileReader();
	fileRead.onload = function() {
		document.getElementById('transactionLog').textContent = fileRead.result;
	}
	
	console.log(this.files[0]);
	
	fileRead.readAsText(this.files[0]);
	
	});
	
}