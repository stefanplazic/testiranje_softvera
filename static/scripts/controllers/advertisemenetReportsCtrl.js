(function() {
	angular.module("myApp").controller('advertisementReportsController',
			advertisementReportsController);

	function advertisementReportsController($cookies, $http, $window, $scope) {

		var vm = this;
		var sock = new SockJS('http://localhost:8080/moderatorDashboard');
		vm.reports = [];

		vm.acceptReport = acceptReport;
		vm.rejectReport = rejectReport;

		sock.onopen = function() {
			sock.send('1');
		};

		sock.onmessage = function(e) {
			var data = JSON.parse(e.data);
			for (var i = 0; i < data.length; i++) {
				vm.reports.push(data[i]);
			}
			$scope.$apply();
		};

		sock.onclose = function() {
			sock.close();
		};

		function acceptReport(report) {

			$http.post('/api/moderator/acceptReport/' + report.id, {}, {
				headers : {
					'X-Auth-Token' : $cookies.get("token")
				}
			}).then(function(response) {
				var index = vm.reports.indexOf(report);
				vm.reports.splice(index, 1);

			}, function(error) {
				console.log(error);
			})

		}

		function rejectReport(report) {

			$http.post('/api/moderator/rejectReport/' + report.id, {}, {
				headers : {
					'X-Auth-Token' : $cookies.get("token")
				}
			}).then(function(response) {
				var index = vm.reports.indexOf(report);
				vm.reports.splice(index, 1);

			}, function(error) {
				console.log(error);
			})

		}
	}

})();