(function() {
	'use strict';

	angular.module('myApp.maintlist').controller('MaintreportController', MaintreportController);

	MaintreportController.$inject = [ '$state', 'maintlistService', '$uibModal', '$log',
			'$scope', 'toastr', 'machineService','localStorageService', 'machine_mstService', '$filter','ApiEndpoint','genericFactory'];
	/* @ngInject */
	function MaintreportController($state, maintlistService, $uibModal, $log, $scope, toastr, machineService,localStorageService, machine_mstService, $filter,ApiEndpoint,genericFactory) {
		var maintUrl = ApiEndpoint.url+"maint";
		var vm = angular.extend(this, {
			maintlists : [],
			
			machines : [],
			changeMachine:changeMachine
			
		});

		(function activate() {
			
			loadMachines();
			
			

		
			
		})();

		// ******************************************************

		
		
		
	
		
		function loadMachines() {
			machine_mstService.getMachine_msts().then(function(data) {
				vm.machines = data;
				
			});
		}
		
		function changeMachine(machine ){
			console.log("machine "+JSON.stringify(machine))
			machine_mstService.getDoneMaintenanceByMachine(machine.machine_id).then(function(data) {
				vm.maintlists = data;
				console.log("maintlists: "+JSON.stringify(vm.maintlists))
				
			});
			/*var msg="";
			var url=maintUrl+"/getDoneMaintenanceByMachine?machineId="+machine.machine_id;
			genericFactory.getAll(msg,url).then(function(response) {
				vm.maintlists = response.data;
				$rootScope.loader=false;
				console.log("maintlists: "+JSON.stringify(vm.maintlists))
								
			});*/
		}
	}


})();
