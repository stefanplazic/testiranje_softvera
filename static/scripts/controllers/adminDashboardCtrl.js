
(function() {
	angular.module("myApp").controller('adminDashboardController',
			adminDashboardController);
	
	
	function adminDashboardController($cookies, $http, $scope, $window) {

		var vm = this;
		vm.requests = [];
		vm.acceptRequest = acceptRequest;
		vm.rejectRequest = rejectRequest;
		
		
		
		var sock = new SockJS('http://' + window.location.hostname + ':8080/adminDashboard');
		
		sock.onopen = function() {
			sock.send('1');
		};

		sock.onmessage = function(e) {
			
			var data = JSON.parse(e.data);
			vm.requests.splice(0);
			for (var i = 0; i < data.length; i++) {
				vm.requests.push(data[i]);
			}
			$scope.$apply();
			
		};
		
		sock.onclose = function() {
			sock.close();
		};

		function acceptRequest(request) {
			
			$http.post('/api/administrator/acceptCompany/' + request.id, {}, {
				headers : {
					'X-Auth-Token' : $cookies.get("token")
				}
			}).then(function(response) {
				var index = vm.requests.indexOf(request);
				vm.requests.splice(index, 1);

			}, function(error) {
				console.log(error);
			})

		}

		function rejectRequest(request) {
		
			$http.post('/api/administrator/declineCompany/' + request.id, {}, {
				headers : {
					'X-Auth-Token' : $cookies.get("token")
				}
			}).then(function(response) {
				var index = vm.requests.indexOf(request);
				vm.requests.splice(index, 1);

			}, function(error) {
				console.log(error);
			})

		}
	}

})();