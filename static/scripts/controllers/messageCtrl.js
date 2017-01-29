(function () {
    angular.module("myApp")
        .controller('messageController', messageController);

    
    function messageController($http, $scope, $cookies) {
    	var vm = this;
		vm.showMessages = showMessages;
		vm.formatDate = formatDate;
		vm.messages = [];
		vm.unreadMessages = 0;
		$("#messagesDiv").hide();

		var sock = new SockJS('http://' + window.location.hostname
				+ ':8080/messages');

		sock.onopen = function() {
			sock.send($cookies.getObject("userdata").id);
		};

		sock.onmessage = function(e) {

			var data = JSON.parse(e.data);
			vm.messages.splice(0);
			vm.unreadMessages = 0;
			for (var i = 0; i < data.length; i++) {
				var authority = data[i].fromUser.authority;
				data[i].fromUser.authority = authority.charAt(0).toUpperCase()
						+ authority.slice(1).toLowerCase()

				vm.messages.push(data[i]);
				if (!data[i].seen && data[i].fromUser.id!=$cookies.getObject("userdata").id)
					vm.unreadMessages++;

			}
			vm.messages.reverse();
			if (vm.unreadMessages > 0) {
				$("#messagesDiv").show();
				document.getElementById("messagesDiv").innerHTML = vm.unreadMessages;
			}else{
				$("#messagesDiv").hide();
			}

			$scope.$apply();

		};

		sock.onclose = function() {
			sock.close();
		};

		function showMessages(m) {
			vm.message = m;
			vm.user = $cookies.getObject("userdata").id;

			if (!m.seen) {
				m.seen = true;
				vm.unreadMessages--;
				if (vm.unreadMessages > 0) {
					document.getElementById("messagesDiv").innerHTML = vm.unreadMessages;
				} else {
					$("#messagesDiv").hide();
				}
				
				$http.post('api/advertiser/updateSeenToTrue/' + vm.message.id, {}, {
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