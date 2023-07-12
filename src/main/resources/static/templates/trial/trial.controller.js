(function() {
	'use strict';

	angular.module('myApp.trial').controller('TrialController', TrialController)
			.controller('TrialModalCtrl', TrialModalCtrl).controller(
					'TrialModalAddEditCtrl', TrialModalAddEditCtrl);

	TrialController.$inject = [ '$state', 'trialService', '$uibModal', '$log',
			'$scope', 'toastr', 'machine_mstService', 'shiftService', 'breakdownupdateService', '$filter'];
	TrialModalCtrl.$inject = [ '$uibModalInstance', 'items', '$scope' ];
	TrialModalAddEditCtrl.$inject = [ '$uibModalInstance', '$filter', 'trial', '$scope', 'trialService' ];

	/* @ngInject */
	function TrialController($state, trialService, $uibModal, $log, $scope, toastr, machine_mstService, shiftService, breakdownupdateService, $filter) {
		var vm = angular.extend(this, {
			trials : [],
			breakdownupdates : [],
			shifts: [],
			machines : [],
			view : view,
			getMaintData : getMaintData,
			add : add,
			delet : delet,
			ok : ok,
			ok1 : ok1,
			ok2 : ok2,
			delet	:	delet
		});

		(function activate() {
			$scope.trial = {};
			$scope.showTrialSheet = false;
			$scope.selectedMachine = 'selectMachine';
			$scope.selectedShift = 'selectShift';
			$scope.trial.machine = {};
			$scope.trial.trial_date = new Date();
//			/loadTrials();
		//	loadShifts();
		//	loadMachines();
			
			loadBreakdownupdates();
			
			
			

			$scope.fetchdata = function() {

				/* $scope.arr */

				debugger;

				$scope.sd = $scope.trial.start_date;
				$scope.ed = $scope.trial.end_date;

				$scope.formattedStartDate = 0;

				$scope.formattedEndDate = 0;

				$scope.formattedStartDate = $filter('date')($scope.sd,
						"yyyy-MM-dd");

				$scope.formattedEndDate = $filter('date')($scope.ed,
						"yyyy-MM-dd");

				$scope.trial.start_date = $scope.sd;
				$scope.trial.end_date = $scope.ed;

			}
			
			
			
			
		})();

		// ******************************************************

		
		function loadBreakdownupdates() {
			breakdownupdateService.getBreakdownupdates().then(function(data) {
				vm.breakdownupdates = data;
//				debugger;
				for(var i = 0; i < vm.breakdownupdates.length; i++){
					if(vm.breakdownupdates[i].breakdown.status == 3 || vm.breakdownupdates[i].breakdown.status == 4){
						vm.breakdownupdates[i].disableAddTrial = true;
					}else{
						vm.breakdownupdates[i].disableAddTrial = false;
						
						if(i > 0){
							if(vm.breakdownupdates[i].breakdown.bd_slip == vm.breakdownupdates[i - 1].breakdown.bd_slip){
								if(vm.breakdownupdates[i].breakdown_update_id < vm.breakdownupdates[i - 1].breakdown_update_id){
									vm.breakdownupdates[i].disableAddTrial = true;
								}
							}
						}
					}
				}
			});
		}
		
		

		function getMaintData(tiral) {
			/*if(trial.status == '' && !trial.start_date && !trial.end_date){
				toastr.error("please select ")
			}*/
			trialService.getMaintData(tiral).then(function(data) {
//				debugger;
				vm.breakdownupdates = data;
				
				for(var i = 0; i < vm.breakdownupdates.length; i++){
					if(vm.breakdownupdates[i].breakdown.status == 3 || vm.breakdownupdates[i].breakdown.status == 4){
						vm.breakdownupdates[i].disableAddTrial = true;
					}else{
						vm.breakdownupdates[i].disableAddTrial = false;
						
						if(i > 0){
							if(vm.breakdownupdates[i].breakdown.bd_slip == vm.breakdownupdates[i - 1].breakdown.bd_slip){
								if(vm.breakdownupdates[i].breakdown_update_id < vm.breakdownupdates[i - 1].breakdown_update_id){
									vm.breakdownupdates[i].disableAddTrial = true;
								}
							}
						}
					}
				}
			});
		}
		
		var validation = function(){
			if(!$scope.trial.trial_date){
				toastr.error('Please select date');
				return true;
			}
			if(!$scope.trial.trial_result || $scope.trial.trial_result == ''){
				toastr.error('Please enter result');
				return true;
			}
			if(!$scope.trial.comment || $scope.trial.comment == ''){
				toastr.error('Please enter comment');
				return true;
			}
			
			return false;
		}
		
		function ok(trial) {
			
			if(validation())
				return;
			else{
					trial.deletes = 1;
					trialService.addTrial(trial).then(function(){
						$scope.trial = {};
						loadTrials();
						loadBreakdownupdates();
					});
			}
		}
		
		function ok1(trial) {
			if(validation())
				return;
			else{
					trial.deletes = 1;
					trialService.addTrial1(trial).then(function(){
						$scope.trial = {};
						loadTrials();
						loadBreakdownupdates();
					});
			}
		}
		
		function ok2(trial) {
			if(!$scope.trial.devialtion_comment){
				toastr.error('Please select devialtion comment');
				return true;
			}
			if(validation())
				return;
			else{
					trial.deletes = 1;
					trialService.addTrial2(trial).then(function(){
						$scope.trial = {};
						loadTrials();
						loadBreakdownupdates();
					});
			}
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
		
		function loadTrials() {
			trialService.getTrials().then(function(data) {
				vm.trials = data;
			});
		}
		
		function delet(trial){
			trialService.deleteTrial(trial).then(function(){
				loadBreakdownupdates();
				loadTrials();
			});
		}

		function view(trial) {
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/trial/trialModelView.html',
				controller : 'TrialModalCtrl',
				controllerAs : 'vm',
				size : 'md',
				resolve : {
					items : function() {
						return trial;
					}
				}
			});

			modalInstance.result.then(function() {
				
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
		}

		function add(trial) {
			console.log("tail........")
			$scope.trial.breakdownupdate = trial;
			
			$scope.trial.breakdown = trial.breakdown;
		
			$scope.trial.machine = trial.breakdown.machine;
			$scope.trial.category = trial.breakdown.machine.category;
			
			
			$scope.trial.ticket_raised_time = trial.breakdown.ticket_raised_time ;
			$scope.trial.sent_to_trial_time = trial.breakdown_date; 
			
			$scope.showTrialSheet = true;
			
			vm.trialHistory = [];
			for(var i = 0; i < vm.trials.length; i++){
				if(vm.trials[i].breakdownupdate.breakdown.breakdown_id == trial.breakdown.breakdown_id)
					vm.trialHistory.push(vm.trials[i]);
			}
			
			setTimeout(function(){
				window.scroll({
					  top: document.body.scrollHeight, 
					  left: 0, 
					  behavior: 'smooth' 
					});
			},0);
			
//			$scope.trial.trial_name = trial.trial_name;
//			$scope.trial.deletes = trial.deletes;
//			$scope.trial.trial_id = trial.trial_id;
			
			/*var dept = trial ? trial : {};
			// alert(JSON.stringify(usr));
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/trial/trialModelAddEdit.html',
				controller : 'TrialModalAddEditCtrl',
				controllerAs : 'vm',
				size : 'lg',
				resolve : {
					trial : function() {
						return dept;
					}
				}
			});

			modalInstance.result.then(function() {
				loadTrials();
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});*/
		}

	}

	function TrialModalCtrl($uibModalInstance, items, $scope) {
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

	function TrialModalAddEditCtrl($uibModalInstance, $filter, trial, $scope, trialService) {
		var vm = angular.extend(this, {
			ok : ok,
			cancel : cancel,
		});

		(function activate() {
			$scope.trial = trial;
			
			/*$scope.setDeletes = function(){
				
				$scope.trial.deletes = 1;
				
			}*/
			$scope.setFormDate = function() {

				$scope.st = $scope.trial.trial_start;
				$scope.gt = $scope.trial.trial_end;

				
				$scope.trial.trial_start = $filter('date')($scope.st,
						"hh:mm:ss a");
				$scope.trial.trial_end = $filter('date')($scope.gt,
						"hh:mm:ss a");
			
			}
			
			
			
		})();

		// ******************************************************
		
		function ok(trial) {
			
			/*debugger;*/
			trialService.addTrial(trial).then(function(){
				$uibModalInstance.close(trial);
				
			});
		}

		
		
		
		function cancel() {
			$uibModalInstance.dismiss('cancel');
		}
	}
})();
