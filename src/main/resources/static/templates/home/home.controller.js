(function() {
	'use strict';

	angular.module('myApp.home').controller('HomeController', HomeController);

	/*HomeController.$inject = [ 'localStorageService', '$scope', 'ApiEndpoint',
			'$state', 'userService', 'supplierService', 'fpyService','otdService', 'ppmService', 'escalationService' ];*/
	HomeController.$inject = ['$scope', 'ApiEndpoint','$state'];

	/* @ngInject */
	/*function HomeController(localStorageService, $scope, ApiEndpoint, $state,
			userService, supplierService, fpyService, otdService, ppmService,
			escalationService) {*/
	function HomeController($scope, ApiEndpoint, $state) {

//		var userDetail = localStorageService.get(ApiEndpoint.userKey);

		// console.log(JSON.stringify(userDetail));
		var vm = angular.extend(this, {
//			user : userDetail,
//			users : [],

		});

		(function activate() {

			/*$scope.c = sessionStorage.getItem("countss");
			$scope.c2 = sessionStorage.getItem("count");
			$scope.c3 = sessionStorage.getItem("count3");

			$scope.l1 = sessionStorage.getItem("low1");
			$scope.l2 = sessionStorage.getItem("low2");
			$scope.l3 = sessionStorage.getItem("low3");

			if ($scope.l1 == null) {
				$scope.l1 = 0;
			}
			if ($scope.l2 == null) {
				$scope.l2 = 0;
			}
			if ($scope.l3 == null) {
				$scope.l3 = 0;
			}
			if ($scope.c == null) {
				$scope.c = 0;
			}
			if ($scope.c2 == null) {
				$scope.c2 = 0;
			}
			if ($scope.c3 == null) {
				$scope.c3 = 0;
			}

			$scope.count = parseInt($scope.c) + parseInt($scope.c2) + parseInt($scope.c3);
			$scope.low = parseInt($scope.l1) + parseInt($scope.l2) + parseInt($scope.l3);*/

			/*loadFpys();
			loadOtds();
			loadPpms();
			loadEscalations();
			loadUsers();

			loadSuppliers();*/

		})();
		
		/*$scope.$on('$locationChangeStart', function(evnt, next, current){            
//		    alert("Your, browsers back button is disabled!");

		    //Prevent browser's back button default action.
		    evnt.preventDefault();            
		});*/

		function loadUsers() {
			userService.getUsers().then(function(data) {
				vm.users = data;

			});
		}

		function loadEscalations() {
			escalationService.getEscalations().then(function(data) {
				vm.escalations = data;
				console.log(JSON.stringify(vm.escalations));
			});
		}

		function loadSuppliers() {
			supplierService.getSuppliers().then(function(data) {
				vm.suppliers = data;
				console.log(JSON.stringify(vm.suppliers));
			});
		}

		function loadFpys() {

			debugger;
			fpyService.getFpys().then(function(data) {
				vm.fpys = data;
				console.log(JSON.stringify(vm.fpys));
			});
		}

		function loadOtds() {
			otdService.getOtds().then(function(data) {
				vm.otds = data;
				console.log(JSON.stringify(vm.otds));
			});
		}

		function loadPpms() {
			ppmService.getPpms().then(function(data) {
				vm.ppms = data;
				console.log(JSON.stringify(vm.ppms));
			});
		}

		// ******************************************************

	}
})();
