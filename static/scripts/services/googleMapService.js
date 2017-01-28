(function() {
	angular.module("myApp").service('googleMap', function(){
		
		this.showMap = function (mapDiv, address) {
	        
			var geocoder = new google.maps.Geocoder();
			geocoder.geocode({
				'address' : address
			}, function(results) {
				var latitude = results[0].geometry.location.lat();
				var longitude = results[0].geometry.location.lng();

				var latLng = new google.maps.LatLng(latitude, longitude);

				//var mapDiv = document.getElementById('map');
				var map = new google.maps.Map(mapDiv, {
					center : latLng,
					zoom : 16,
					mapTypeId : google.maps.MapTypeId.ROADMAP
				});

				var marker = new google.maps.Marker({
					position : latLng,
					map : map,
					title : address
				});
			});
		
		}
	});
}
)();
