(function() {
	angular.module("myApp").service('errorService', function($http){
		
		this.sendEvent = function (event) {
	        var data = {"key":"0.2503538840489665", "event": event};
	        console.log(data);
			$http.post("http://localhost:3000/api/app/errorlog",data,{
			    headers : {
			        'Content-Type' : 'application/json'
			    }}).then(function(response){},function(response){console.log(response)});
		
		}
	});
})();