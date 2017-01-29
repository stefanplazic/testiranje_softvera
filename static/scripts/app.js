angular.module('myApp',
		[ 'ngResource', 'ngRoute', 'ngCookies', 'ui.bootstrap', 'lodash','bootstrapLightbox'])
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
			}).when('/messages', {
				templateUrl : '/views/messages.html',
				controller : 'messageController',
				controllerAs : 'messageCtrl'
			}).when('/notifications', {
				templateUrl : '/views/notifications.html',
				controller: 'notificationController',
				controllerAs: 'notificationCtrl'
			}).when('/registerAdminOrModerator', {
				templateUrl: '/views/registerAdminOrModerator.html',
				controller: 'registerAdminOrModeratorController',
				controllerAs: 'regAdminOrModCtrl'
			}).when('/advertisementReports', {
				templateUrl: '/views/advertisementReports.html',
				controller: 'advertisementReportsController',
				controllerAs: 'adReportsCtrl'
			}).when('/adminsDashboard', {
				templateUrl: '/views/adminsDashboard.html',
				controller: 'adminDashboardController',
				controllerAs: 'adminDashboardCtrl'
			}).when('/estate/:id',{
				templateUrl: '/views/estate.html',
				controller: 'estateController',
				controllerAs: 'estateCtrl'
			}).when('/CompanyCall', {
				templateUrl: '/views/callToCompany.html',
				controller: 'companyController',
				controllerAs: 'companyCtrl'
			}).when('/advertProfile/:id', {
				templateUrl: '/views/advertiserProfile.html',
				controller: 'advertiseProfileController',
				controllerAs: 'profileCtrl'
			}).when('/viewAdvertisement/:id', {
				templateUrl: '/views/viewAdvertisement.html',
				controller: 'viewAdvertController',
				controllerAs: 'adversCtrl'
			}).when('/myEstates',{
				templateUrl: '/views/myEstates.html',
				controller: 'myEstatesController',
				controllerAs: 'myEstatesCtrl'
			})
			.otherwise({
				redirectTo : '/'
			});
		} ]);