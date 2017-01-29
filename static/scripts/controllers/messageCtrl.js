(function () {
    angular.module("myApp")
        .controller('messageController', messageController);

    
    function messageController($http, $scope, $cookies) {
    	var vm = this;
		vm.showMessages = showMessages;
		vm.sendMessage = sendMessage;
		vm.formatDate = formatDate;
		vm.messages = [];
		vm.unreadMessages = 0;
		vm.message = null;
		vm.data = $cookies.getObject("userdata");
		
		$("#messagesDiv").hide();

		var sock = new SockJS('http://' + window.location.hostname
				+ ':8080/messages');

		sock.onopen = function() {
			sock.send(vm.data.id);
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
				if (!data[i].seen && data[i].fromUser.id!=data.id)
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
			vm.user = vm.data.id;

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
		function sendMessage() {
			
			
			if(vm.data.authority=="CUSTOMER"){
				$http.post("/api/customer/sendMessageToAdvertiser/", {
					"message" : $("#answer").val(),
					"advertisementId": vm.message.advertisement.id }, {
					headers : {
						'X-Auth-Token' : $cookies.get("token")
					}
				}).then(function(response) {
					toastr.success("Message has been sent to the Advertiser.");
				}, function(error) {
					// log error response and maybe send it to
					// error monitor app
					console.error("Error ocurred: " + error);
				});
			}else{
				$http.post("/api/advertiser/sendMessageToCustomer/", {
					"message" : $("#answer").val(),
					"toUserId" : vm.message.fromUser.id,
					"advertisementId": vm.message.advertisement.id }, {
					headers : {
						'X-Auth-Token' : $cookies.get("token")
					}
				}).then(function(response) {
					toastr.success("Message has been sent to the Customer.");
				}, function(error) {
					// log error response and maybe send it to
					// error monitor app
					console.error("Error ocurred: " + error);
				});
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