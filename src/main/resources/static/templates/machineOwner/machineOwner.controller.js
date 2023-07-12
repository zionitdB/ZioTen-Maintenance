(function() {
	'use strict';

	angular.module('myApp.machineOwner').controller('MachineOwnerController', MachineOwnerController);


	MachineOwnerController.$inject = [ '$state','$uibModal','$log',
			'$scope', 'toastr','localStorageService','$filter','genericFactory','machine_mstService'];
	
	/* @ngInject */
	function MachineOwnerController($state,$uibModal,$log, $scope, toastr,localStorageService,$filter,genericFactory,machine_mstService) {
		
		var machineUrl = staticUrlMaintenance+"/machine_mst";
		var userUrl = staticUrlMaintenance+"/user";
		var machineOwnerUrl = staticUrlMaintenance+"/machineOwner";
		var vm = angular.extend(this, {
			save:save,
			delet:delet,
			edit:edit,
		});

		(function activate() {
			//loadMachines()
			loadUsers()
			loadMachineOwners()
			loadMachineNames();
		})();

		// ******************************************************
		function edit(machOwner){
			$scope.machOwner=machOwner
		}
		function loadMachines(){
			console.log("machines : ")
			var msg=""
				 var url =machineUrl+"/list";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.machines= response.data;
				console.log("machines : "+JSON.stringify(vm.machines))
								
			});
			
			
		}
		function loadMachineNames() {
			machine_mstService.getMachineNames().then(function(data) {
				vm.machineNames = data;
				console.log(JSON.stringify(vm.machineNames));
			});
		}
		
		function loadUsers(){
			
			var msg=""
				 var url =userUrl+"/getAlluser";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.users= response.data;
				console.log("users : "+JSON.stringify(vm.users))
								
			});
			
			
		}
		function loadMachineOwners(){
		
			var msg=""
				 var url =machineOwnerUrl+"/getAllMachineOwners";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.machineOwners= response.data;
				console.log("machineOwners : "+JSON.stringify(vm.machineOwners))
								
			});
			
			
		}
	function delet(machOwner){
			
			console.log("machOwner : "+JSON.stringify(machOwner))
			var msg=""
				 var url =machineOwnerUrl+"/deletMachineOwner";
				genericFactory.add(msg,url,machOwner).then(function(response) {
					loadMachineOwners()
				
								
			});
			
		}
		function save(machOwner){
			if(machOwner==undefined){
				toastr.error('Please Enter Machine Name ');
				
				return;
			}
			if(!machOwner.machineName || machOwner.machineName == ''){
				toastr.error('Please Enter Machine Name ');
				
				return;
			}
			if(!machOwner.user|| machOwner.user== ''){
				toastr.error('Please Enter User Name ');
				
				return;
			}
			console.log("machOwner : "+JSON.stringify(machOwner))
			var msg=""
				 var url =machineOwnerUrl+"/addMachineOwner";
				genericFactory.add(msg,url,machOwner).then(function(response) {
					loadMachineOwners()
				
								
			});
			
		}
		
	}
})();
