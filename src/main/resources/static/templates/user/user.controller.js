(function() {
	'use strict';

	angular.module('myApp.user').controller('UserController', UserController);
	UserController.$inject = [ '$state', 'userService', '$uibModal', '$log', '$scope', 'toastr'];

	/* @ngInject */
	function UserController($state, userService, $uibModal, $log, $scope, toastr) {
		var vm = angular.extend(this, {
			users : [],
			deleteUser : deleteUser,
			editUser	:	editUser,
			createUser	:	createUser,
			addNewUser	:	addNewUser,
			validateUserId : validateUserId,
			editUserInformation : editUserInformation
		});

		(function activate() {
			$scope.user = {};
			$scope.user.gender = 'selectGender';
			$scope.showEditButton = false;
			
			loadUsers();
		})();

		// ******************************************************
		
		function validateUserId(id){
			if(!id || id=="")
				return;
			userService.checkExistingUserId(id).then(function(data){
				if(data == 'error'){
					$scope.error = true;
					//document.getElementById('employeeid').focus();
				}else{
					$scope.error = false;
				}
			});
		}
		
		function addNewUser(){
			$scope.disableEmployeeId = false;
			$scope.showEditButton = false;
			$scope.user = {};
			$scope.user.gender = 'selectGender';
		}

		function loadUsers() {
			userService.getUsers().then(function(data) {
				vm.users = data;
//				console.log(JSON.stringify(vm.users));
			});
		}

		function deleteUser(user) {
			$scope.disableEmployeeId = false;
			$scope.user = {};
			$scope.user.gender = 'selectGender';
			$scope.showEditButton = false;
			userService.deleteUser(user).then(function() {
				loadUsers();
			});
		}
		
		function validations(){
			if(!$scope.user.firstName || $scope.user.firstName == ''){
				toastr.error('Please enter first name');
				//document.getElementById('firstname').focus();
				return true;
			}
			if(!$scope.user.lastName || $scope.user.lastName == ''){
				toastr.error('Please enter last name');
				//document.getElementById('lastname').focus();
				return true;
			}
			if(!$scope.user.emailId || $scope.user.emailId == ''){
				toastr.error('Please enter email id');
				//document.getElementById('emailid').focus();
				return true;
			}
			if($scope.user.gender == 'selectGender'){
				toastr.error('Please select gender');
				//document.getElementById('gender').focus();
				return true;
			}
			if(!$scope.user.password || $scope.user.password == ''){
				toastr.error('Please enter password');
				//document.getElementById('password').focus();
				return true;
			}
			if(!$scope.user.contactNo || $scope.user.contactNo == ''){
				toastr.error('Please enter contact No');
				//document.getElementById('contactno').focus();
				return true;
			}
			if(!$scope.user.id || $scope.user.id == ''){
				toastr.error('Please enter emp id');
				//document.getElementById('employeeid').focus();
				return true;
			}
			if($scope.error == true){
				//document.getElementById('employeeid').focus();
				return true;
			}
			return false;
		}
		
		function createUser(iObj){
			if(validations())
				return;
			
			$scope.user.roles = [];
			
			/*{
		        "roleId": 18,                 
		        "name": "Super Admin"         
		    }*/
			
			var createObj = Object.assign({}, $scope.user);
			userService.createUser(createObj).then(function(data) {
				$scope.user = {};
				$scope.user.gender = 'selectGender';
//				console.log(data);
				loadUsers();
			});
		}
		
		function editUserInformation(iObj){
			if(validations())
				return;
			
			var createObj = Object.assign({}, $scope.user);
			userService.editUser(createObj).then(function(data) {
				$scope.user = {};
				$scope.user.gender = 'selectGender';
//				console.log(data);
				loadUsers();
				$scope.showEditButton = false;
			});
		}
		
		function editUser(iObj){
			$scope.showEditButton = true;
			$scope.user = iObj;
			$scope.disableEmployeeId = true;
		}

	}

})();
