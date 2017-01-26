angular.module('myApp',
		[ 'ngResource', 'ngRoute', 'ngCookies', 'ui.bootstrap', 'lodash'])
		.config([ '$routeProvider', function($routeProvider) {
			$routeProvider
			.when('/', {
				templateUrl : '/views/search.html',
				controller : "searchController",
				controllerAs : "searchCtrl"
			}).when('/login', {
				templateUrl : '/views/login.html',
				controller : "loginController",
				controllerAs : "loginCtrl"
			}).when('/register', {
				templateUrl : '/views/registration.html',
				controller : 'registrationController',
				controllerAs : 'registerCtrl'
			}).when('/profile', {
				templateUrl : '/views/profile.html',
				controller : 'profileController',
				controllerAs : 'profileCtrl'
			}).when('/company', {
				templateUrl : '/views/company.html',
				controller : 'companyController',
				controllerAs : 'companyCtrl'
			}).when('/boughtEstates', {
				templateUrl: '/views/boughtEst.html',
				controller: 'BoughtEstateController',
				controllerAs: 'EstCtrl'
			}).when('/registerAdminOrModerator', {
				templateUrl: '/views/registerAdminOrModerator.html',
				controller: 'registerAdminOrModeratorController',
				controllerAs: 'regAdminOrModCtrl'
			}).when('/advertisementReports', {
				templateUrl: '/views/advertisementReports.html',
				controller: 'advertisementReportsController',
				controllerAs: 'adReportsCtrl'
			}).when('/estate/:id',{
				templateUrl: '/views/estate.html',
				controller: 'estateController',
				controllerAs: 'estateCtrl'
			}).when('/CompanyCall', {
				templateUrl: '/views/callToCompany.html',
				controller: 'callToCompanyController',
				controllerAs: 'companyCtrl'
			}).otherwise({
				redirectTo : '/'
			});
		} ]);