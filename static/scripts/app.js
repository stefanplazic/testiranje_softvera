angular
  .module('myApp', [
    'ngResource',
    'ngRoute',
    'ngCookies',
    'ui.bootstrap',
    'lodash'
  ])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: '/views/login.html',
        controller: "loginController",
        controllerAs: "loginCtrl"
        
      })
      .when('/profile',{
    	  templateUrl: '/views/profile.html',
    	  controller: "profileController",
    	  controllerAs: "profileCtrl"
      })
      .when('/register',{
    	  templateUrl: '/views/registration.html',
    	  controller:  'registrationController',
    	  controllerAs:'registerCtrl'
      })
      .otherwise({
        redirectTo: '/'
      });
  }]);