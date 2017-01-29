(function() {
	angular.module("myApp").controller('notificationController',
			notificationController);

	// login page controller
	function notificationController($http, $scope, $cookies) {

		var vm = this;
		vm.showNotification = showNotification;
		vm.formatDate = formatDate;
		vm.notifications = [];
		vm.unreadNotifications = 0;
		$("#notDiv").hide();

		var sock = new SockJS('http://' + window.location.hostname
				+ ':8080/notification');

		sock.onopen = function() {
			sock.send($cookies.getObject("userdata").id);
		};

		sock.onmessage = function(e) {

			var data = JSON.parse(e.data);

			vm.notifications.splice(0);
			vm.unreadNotifications = 0;
			for (var i = 0; i < data.length; i++) {
				var authority = data[i].fromUser.authority;
				data[i].fromUser.authority = authority.charAt(0).toUpperCase()
						+ authority.slice(1).toLowerCase()

				vm.notifications.push(data[i]);
				if (!data[i].seen)
					vm.unreadNotifications++;

			}
			vm.notifications.reverse();
			if (vm.unreadNotifications > 0) {
				$("#notDiv").show();
				document.getElementById("notDiv").innerHTML = vm.unreadNotifications;
			}else{
				$("#notDiv").hide();
			}

			$scope.$apply();

		};

		sock.onclose = function() {
			sock.close();
		};

		function showNotification(n) {
			vm.notification = n;

			if (!n.seen) {
				n.seen = true;
				vm.unreadNotifications--;
				if (vm.unreadNotifications > 0) {
					document.getElementById("notDiv").innerHTML = vm.unreadNotifications;
				} else {
					$("#notDiv").hide();
				}
				
				$http.post('api/advertiser/updateSeenToTrue/' + vm.notification.id, {}, {
					headers : {
						'X-Auth-Token' : $cookies.get("token")
					}
				}).then(function(response) {
					

				}, function(error) {
					console.log(error);
				})
			}
		}

		/**
		 * function for showing date in nice format : day/month/year
		 */
		function formatDate(myDate) {

			if (myDate == undefined)
				return;
			var date = new Date(myDate);
			return date.getUTCDate() + "/" + date.getUTCMonth() + 1 + "/"
					+ date.getFullYear() + " " + date.getHours() + ":"
					+ date.getMinutes();
		}

	}
})();