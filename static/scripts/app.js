angular.module('myApp',
		[ 'ngResource', 'ngRoute', 'ngCookies', 'ui.bootstrap', 'lodash' ])
		.config([ '$routeProvider', function($routeProvider) {
			$routeProvider
			.when('/', {
				templateUrl : '/views/profile.html',
				controller : "profileController",
				controllerAs : "profileCtrl"
			}).when('/login', {
				templateUrl : '/views/login.html',
				controller : "loginController",
				controllerAs : "loginCtrl"

			}).when('/register', {
				templateUrl : '/views/registration.html',
				controller : 'registrationController',
				controllerAs : 'registerCtrl'
			}).otherwise({
				redirectTo : '/'
			});
		} ]);