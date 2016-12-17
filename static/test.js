$( document ).ready(function() {
	webSocket = null;   
    openSocket();
});

function openSocket(){

	var sock = new SockJS('http://localhost:8080/notification');
	
	sock.onopen = function() {
	     console.log('open');
	     sock.send('2');
	     alert("open")
	 };
	sock.onmessage = function(e) {
		alert(JSON.stringify(JSON.parse(e.data)))
	    console.log('message', e.data);
	 };
	 
	sock.onclose = function() {
	    alert('close')
		console.log('close');
	    sock.close();
	 };

}
