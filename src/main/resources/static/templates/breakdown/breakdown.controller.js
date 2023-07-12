(function() {
	'use strict';

	angular.module('myApp.breakdown').controller('BreakdownController', BreakdownController)
			.controller('BreakdownModalCtrl', BreakdownModalCtrl).controller(
					'BreakdownModalAddEditCtrl', BreakdownModalAddEditCtrl);

	BreakdownController.$inject = [ '$state', '$stateParams', 'breakdownService', '$uibModal', '$log',
			'$scope', 'toastr', 'machine_mstService', 'shiftService','localStorageService','ApiEndpoint','genericFactory'];
	BreakdownModalCtrl.$inject = [ '$uibModalInstance', 'items', '$scope' ];
	BreakdownModalAddEditCtrl.$inject = [ '$uibModalInstance', '$filter', 'breakdown', '$scope', 'breakdownService' ];

	/* @ngInject */
	function BreakdownController($state, $stateParams, breakdownService, $uibModal, $log, $scope, toastr, machine_mstService, shiftService,localStorageService,ApiEndpoint,genericFactory) {
		var departmentUrl = staticUrlMaintenance+"/department";
		var vm = angular.extend(this, {
			breakdowns : [],
			shifts: [],
			machines : [],
			view : view,
			add : add,
			delet : delet,
			ok : ok,
			changeMachineNames:changeMachineNames
		});

		(function activate() {
			$scope.breakdown = {};
			$scope.machine_mst = {};
			$scope.selectedEquipment = 'selectEquipment';
			$scope.selectedShift = 'selectShift';
			$scope.breakdown.machine = {};
		//	loadBreakdowns();
			loadShifts();
			//loadMachines();
			loadDepartments();
			loadMachineNames()
//			console.log($stateParams);
			if($stateParams && $stateParams.machine){
				$scope.machine_mst.type = $stateParams.machine.type.toString();
				$scope.selectedEquipment = JSON.stringify($stateParams.machine);
			}
			$scope.date = new Date();
			$scope.userDetails = localStorageService.get(ApiEndpoint.userKey);
		//	$scope.userDetails = sessionStorage.getItem('renataLoggedInUser'); 
			
			$scope.breakdown.detected_by= $scope.userDetails.firstName
			
		})();
		
		// ******************************************************
		function loadMachineNames() {
			machine_mstService.getMachineNames().then(function(data) {
				vm.machineNames = data;
				console.log(JSON.stringify(vm.machineNames));
			});
		}
		function changeMachineNames(machineName) {
			console.log("machineName  "+JSON.stringify(machineName));

			machine_mstService.getMachineByName(machineName).then(function(data) {
				vm.machines = data;
				console.log("machinesId  "+JSON.stringify(vm.machines));
			});
		}
		function loadDepartments(){
			console.log("dddd")
			var msg=""
				 var url =departmentUrl+"/getAllDepartments";
				genericFactory.getAll(msg,url).then(function(response) {
				vm.departments= response.data;
				console.log("departments : "+JSON.stringify(vm.departments))
								
			});
		}
		
		function ok(breakdown) {
			console.log("SAVE BREAK DWON  OK  :: ")
			if($scope.selectedShift == 'selectShift'){
				toastr.error('Please select shift');
				return;
			}
			if($scope.selectedEquipment == 'selectEquipment'){
				toastr.error('Please select equipment');
				return;
			}
			
			breakdown.shift = JSON.parse($scope.selectedShift);
		//	breakdown.type = $scope.machine_mst.type;
			breakdown.machine = {
					machine_id : JSON.parse($scope.selectedEquipment).machine_id
			}
			
			
			breakdown.deletes = 1;
			
			breakdown.status = 1;
			
			breakdown.ticket_raised_time = $scope.date
			breakdown.department=$scope.department;
			breakdown.detected_by= $scope.userDetails.id
			console.log("breakdown : "+JSON.stringify(breakdown))
			/*debugger;*/
			breakdownService.addBreakdown(breakdown).then(function(){
//				toastr.success('breakdown created successfully')
				$scope.breakdown = {};
				$scope.selectedEquipment = 'selectEquipment';
				$scope.selectedShift = 'selectShift';
				$scope.machine_mst = {};
				$scope.breakdown.detected_by= $scope.userDetails.firstName
				//loadBreakdowns();
//				$uibModalInstance.close(breakdown);
				
			});
		}

		function loadShifts() {
			shiftService.getShifts().then(function(data) {
				vm.shifts = data;
			});
		}
		
		function loadMachines() {
			machine_mstService.getMachine_msts().then(function(data) {
				vm.machines = data;
			});
		}
		
		

		function loadBreakdowns() {
			breakdownService.getBreakdowns2().then(function(data) {
				vm.breakdowns = data;
				if(vm.breakdowns.length == 0)
					$scope.breakdown.bd_slip = '000001';
				else{
					var slip_number = vm.breakdowns[0].bd_slip
					var incrementvalue = (+slip_number) + 1;
					incrementvalue = ("000000" + incrementvalue).slice(-6);
					$scope.breakdown.bd_slip = incrementvalue;
				}
			});
		}
		
		
		
		function delet(breakdown){
			breakdownService.deleteBreakdown(breakdown).then(function(){
				//loadBreakdowns();
			});
		}

		function view(breakdown) {
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/breakdown/breakdownModelView.html',
				controller : 'BreakdownModalCtrl',
				controllerAs : 'vm',
				size : 'md',
				resolve : {
					items : function() {
						return breakdown;
					}
				}
			});

			modalInstance.result.then(function() {
				
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
		}

		function add(breakdown) {
			console.log("SAVE BREAK DWON  add :: ")
			return ;
			$scope.breakdown.breakdown_name = breakdown.breakdown_name;
//			$scope.breakdown.deletes = breakdown.deletes;
			$scope.breakdown.breakdown_id = breakdown.breakdown_id;
			/*var dept = breakdown ? breakdown : {};
			// alert(JSON.stringify(usr));
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/breakdown/breakdownModelAddEdit.html',
				controller : 'BreakdownModalAddEditCtrl',
				controllerAs : 'vm',
				size : 'lg',
				resolve : {
					breakdown : function() {
						return dept;
					}
				}
			});

			modalInstance.result.then(function() {
				loadBreakdowns();
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});*/
		}

	}

	function BreakdownModalCtrl($uibModalInstance, items, $scope) {
		var vm = angular.extend(this, {
			items : items,
			ok : ok,
			cancel : cancel
		});

		(function activate() {

		})();

		// ******************************************************

		function ok() {
			$uibModalInstance.close();
		}

		function cancel() {
			$uibModalInstance.dismiss('cancel');
		}
	}

	function BreakdownModalAddEditCtrl($uibModalInstance, $filter, breakdown, $scope, breakdownService) {
		var vm = angular.extend(this, {
			ok : ok,
			cancel : cancel,
		});

		(function activate() {
			$scope.breakdown = breakdown;
			
			/*$scope.setDeletes = function(){
				
				$scope.breakdown.deletes = 1;
				
			}*/
			$scope.setFormDate = function() {

				$scope.st = $scope.breakdown.breakdown_start;
				$scope.gt = $scope.breakdown.breakdown_end;

				
				$scope.breakdown.breakdown_start = $filter('date')($scope.st,
						"hh:mm:ss a");
				$scope.breakdown.breakdown_end = $filter('date')($scope.gt,
						"hh:mm:ss a");
			
			}
			
			
			
		})();

		// ******************************************************
		
		function ok(breakdown) {
			
			/*debugger;*/
			breakdownService.addBreakdown(breakdown).then(function(){
				$uibModalInstance.close(breakdown);
				
			});
		}

		
		
		
		function cancel() {
			$uibModalInstance.dismiss('cancel');
		}
	}
})();
