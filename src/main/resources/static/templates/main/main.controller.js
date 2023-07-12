(function() {
	'use strict';

	angular.module('myApp.main').controller('mainController', mainController);

	mainController.$inject = [ '$scope', 'localStorageService', 'ApiEndpoint',
			'$state','$location'];
	/* @ngInject */
	function mainController($scope, localStorageService, ApiEndpoint, $state,$location) {
		$scope.openLogout = false;
		var userDetail = localStorageService.get(ApiEndpoint.userKey);
		var currentUrl = ApiEndpoint.url;
		console.log("LOGIN USER DETIALS "+JSON.stringify(userDetail));
		var vm = angular.extend(this, {
//			doLogout : doLogout,
			user : userDetail,
			logout : logout
		});

		(function activate() {
		
		})();
		
		function logout(){
			sessionStorage.removeItem(ApiEndpoint.userKey);
			sessionStorage.removeItem('permissions');
			$state.go('login_form');
		}
		
	
		
		$scope.openLogout = false;
		$scope.expandLogout = function() {
			$scope.openLogout = !$scope.openLogout;
		}

		$scope.closeDropdown = function() {
			$scope.openLogout = false;
		}	
		
		$scope.expand = function(item){
			console.log("clicked"+item);
			$scope.item.show = true;
		}
		
		$scope.toggle = function() {
	        $scope.myVar = !$scope.myVar;
	    };
	    
	    $scope.IsVisible = false;
        $scope.ShowHide = function () {
            //If DIV is visible it will be hidden and vice versa.
            $scope.IsVisible = $scope.IsVisible ? false : true;
        }
	}
	
	
})();


