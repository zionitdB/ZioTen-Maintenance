(function() {
	'use strict';

	angular.module('myApp.breakdownhistory').controller('BreakdownHistoryController', BreakdownHistoryController);

	BreakdownHistoryController.$inject = [ '$state', 'breakdownhistoryService', '$uibModal', '$log', '$scope', 'toastr', 'machine_mstService'];

	/* @ngInject */
	function BreakdownHistoryController($state, breakdownhistoryService, $uibModal, $log, $scope, toastr, machine_mstService) {
		var vm = angular.extend(this, {
			breakdownHistory : [],
			loadHistory : loadHistory
		});
		
		(function activate() {
			$scope.selectMachine = 'selectMachine';
			$scope.startDate = new Date();
			$scope.endDate = new Date();
			
			
			loadHistory();
			loadMachines();
			
		})();

		// ******************************************************
		
		function loadHistory(){
			
			var obj = {
			}
			if($scope.endDate && $scope.startDate){
				var sdd = $scope.startDate.getDate() < 10 ? '0' + ($scope.startDate.getDate()) : $scope.startDate.getDate();
				var smm = $scope.startDate.getMonth() + 1 < 10 ? '0' + ($scope.startDate.getMonth() + 1) : ($scope.startDate.getMonth() + 1);
				var syy = $scope.startDate.getFullYear();
				
				var edd = $scope.endDate.getDate() < 10 ? '0' + ($scope.endDate.getDate()) : $scope.endDate.getDate();
				var emm = $scope.endDate.getMonth() + 1 < 10 ? '0' + ($scope.endDate.getMonth() + 1) : ($scope.endDate.getMonth() + 1);
				var eyy = $scope.endDate.getFullYear();

				obj.startDate = syy + '-' + smm + '-' + sdd;
				obj.endDate = eyy + '-' + emm + '-' + edd;
			}
			/*if($scope.selectMachine != 'selectMachine'){
				var machineObj = $scope.selectMachine;
				obj.machine = {
						machine_name : machineObj
				}
			}*/
			console.log("obj"+JSON.stringify(obj ))
			breakdownhistoryService.getbreakdownhistory(obj).then(function(data){
				vm.breakdownHistory = data;
				console.log("Sssss"+JSON.stringify(vm.breakdownHistory ))
				setTimeout(function(){
					window.scroll({
						  top: document.body.scrollHeight, 
						  left: 0, 
						  behavior: 'smooth' 
					});
				},0);
			});
		}
		
		function loadMachines() {
			machine_mstService.getMachineNames().then(function(data) {
				vm.machines = data;
				console.log("Machine Name :: "+JSON.stringify(vm.machines ))
				
			});
		}		
			
		

		
		
	}

})();
