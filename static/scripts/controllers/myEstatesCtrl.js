(function(){
	angular.module("myApp")
	    .controller('myEstatesController', myEstatesController);

    function myEstatesController($http, $scope,$cookies){
		var vm = this;
		vm.estate={};
		vm.estate.images=[];
		vm.uploadFile = uploadFile;

		

		$http.get('api/estate/user',{headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response){
			vm.estates=response.data;
		
		})


		vm.a=function(id){
			$window.location='#/estate/'+id
		}

		vm.addEstate=function(){
			


			uploadFile().then(function (){
				
			})


			
			

		}

		function uploadFile(){
			var file=document.getElementById("fileImage").files[0];
			if(file == undefined){
				vm.estate.images=[]
				alert(JSON.stringify(vm.estate))
			      $http.post('api/estate/add',vm.estate,{headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response){
				
				alert(JSON.stringify(response.data.response))
			})
			}else{
				var formData = new FormData(),
			    xhr = new XMLHttpRequest(),
			    cloudName = "webp";

			  formData.append("file", file);
			  formData.append("upload_preset", "cwlqmqiq"); // REQUIRED
			  xhr.open("POST", "https://api.cloudinary.com/v1_1/" +
			    cloudName +
			    "/image/upload");

			  xhr.onload = function() { 
				  if (xhr.status === 200) {
				      // Success! You probably want to save the URL somewhere
				      var response = JSON.parse(xhr.response);
				      response.secure_url // https address of uploaded file
				      $('#image').attr('src',response.secure_url);
				      var image = response.secure_url;
				      vm.estate.images.push({'url':image})
				      alert(JSON.stringify(vm.estate))
				      $http.post('api/estate/add',vm.estate,{headers : {'X-Auth-Token' : $cookies.get("token")}}).then(function(response){
					
					alert(JSON.stringify(response.data.response))
				})
				    } else {
				    	toastr.error("Upload failed. Please try agian.");
				    }
			  }
			  
			  xhr.send(formData);
			}
		}

	}

})();