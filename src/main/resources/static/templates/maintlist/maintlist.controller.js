(function() {
	'use strict';

	angular.module('myApp.maintlist').controller('MaintlistController', MaintlistController)
			.controller('MaintlistModalCtrl', MaintlistModalCtrl).controller(
					'MaintlistModalAddEditCtrl', MaintlistModalAddEditCtrl);

	MaintlistController.$inject = [ '$state', 'maintlistService', '$uibModal', '$log',
			'$scope', 'toastr', 'machineService','localStorageService', 'machine_mstService', '$filter'];
	MaintlistModalCtrl.$inject = [ '$uibModalInstance', 'items', '$scope' ];
	MaintlistModalAddEditCtrl.$inject = [ '$uibModalInstance', 'maintlist', '$scope', 'maintlistService', '$filter'];

	/* @ngInject */
	function MaintlistController($state, maintlistService, $uibModal, $log, $scope, toastr, machineService,localStorageService, machine_mstService, $filter) {
		var vm = angular.extend(this, {
			maintlists : [],
			checklists : [],
			checklists2 : [],
			machines : [],
			view : view,
			add : add,
			delet : delet,
			getChecklist : getChecklist,
			getChecklist2 : getChecklist2,
			saveMaintenanceReport : saveMaintenanceReport,
			takeAction	:	takeAction,
			getRecord : getRecord
		});

		(function activate() {
			$scope.maint = {};
			$scope.showEdit = false;
			vm.observation = '';
//			$scope.userDetails = localStorageService.get('myeplanAdminUser');
			$scope.userDetails = sessionStorage.getItem('renataLoggedInUser'); //localStorageService.get('renataLoggedInUser');
			$scope.userDetails = JSON.parse($scope.userDetails);
			
			$scope.actionDoneBy = $scope.userDetails.firstName;
			$scope.selectMachine = 'selectMachine';
			
			loadMaintlists();
			loadMachines();
			
			

			$scope.fetchdata = function() {

				/* $scope.arr */

//				debugger;

				$scope.sd = $scope.trial.start_date;
				$scope.ed = $scope.trial.end_date;

				$scope.formattedStartDate = 0;

				$scope.formattedEndDate = 0;

				$scope.formattedStartDate = $filter('date')($scope.sd,
						"yyyy-MM-dd");

				$scope.formattedEndDate = $filter('date')($scope.ed,
						"yyyy-MM-dd");

				$scope.trial.start_date = $scope.formattedStartDate;
				$scope.trial.end_date = $scope.formattedEndDate;

			}
			
		})();

		// ******************************************************

		
		function getChecklist(maint) {
			
//			debugger;
			vm.observation = maint.overall_status ? maint.overall_status : null;
			maintlistService.getChecklist(maint).then(function(data) {
				vm.checklists = data;
//				if(vm.checklists.length > 0){
					$scope.showEdit = true;
				/*}else{
					$scope.showEdit = false;
				}*/
				$scope.machineDetails = maint;
				$scope.maint_id = maint.maint_id;
				
				$scope.equipment = maint.machine.machine_name+' - '+maint.machine.eqid;
//				console.log(JSON.stringify(vm.checklists));
				
				setTimeout(function(){
					window.scroll({
						  top: document.body.scrollHeight, 
						  left: 0, 
						  behavior: 'smooth' 
					});
				},0);
			/*	if(vm.checklists && vm.checklists.length > 0){
					$scope.showEdit = true;
					vm.observation = vm.checklists[0].maint.overall_status ? vm.checklists[0].maint.overall_status : '';
					$scope.actionDoneBy = $scope.userDetails.fname;
					$scope.equipment = vm.checklists[0].checklist.machine.machine_name;
					for(var i in vm.checklists){
						if(vm.checklists[i].status && vm.checklists[i].status == 'ok') 
							vm.checklists[i].action = true;
						else
							vm.checklists[i].action = false;
					}
					setTimeout(function(){
						window.scroll({
							  top: document.body.scrollHeight, 
							  left: 0, 
							  behavior: 'smooth' 
							});
					},10);
					
				}else
					$scope.showEdit = false;
				console.log(JSON.stringify(vm.checklists));*/
			});
		}
		
		
		

		function getChecklist2(maint) {
			
			debugger;
			
			maintlistService.getChecklist2(maint).then(function(data) {
				vm.checklists2 = data;
				
					for(var j = 0; j < vm.checklists2.length; j++){
						if($scope.maint_id == vm.checklists2[j].maint.maint_id){
							if(vm.checklists2[j].status == 'ok')
								vm.checklists[j].action = true;
							else
								vm.checklists[j].action = false;
						}
					}
				
					setTimeout(function(){
						window.scroll({
							  top: document.body.scrollHeight, 
							  left: 0, 
							  behavior: 'smooth' 
						});
					},0);
//				console.log(vm.checklists2);
				
			});
		}
		
		
		function loadMaintlists() {
			machineService.getMachines().then(function(data) {
				vm.maintlists = data;
//				console.log(JSON.stringify(vm.maintlists));
			});
		}
		
		
		function getRecord(maint) {
			if($scope.selectMachine && $scope.selectMachine != 'selectMachine')				
				maint.machine = JSON.parse($scope.selectMachine);
			else
				maint.machine = null;
			maintlistService.getrecords(maint).then(function(data) {
				vm.maintlists = data;
				console.log(JSON.stringify(vm.maintlists));
			});
		}
		
		
		
		function loadMachines() {
			machine_mstService.getMachine_msts().then(function(data) {
				vm.machines = data;
				
			});
		}
		
		function delet(maint){
			maintlistService.deleteMaintlist(maint).then(function(){
				loadMaintlists();
			});
		}
		
		/**
		 * @author	ABS
		 * @date	July 24, 2018
		 * @name	takeAction
		 * @pupose	to handle action taken by user
		 */
		function takeAction(iIndex){
			vm.checklists[iIndex].action = !vm.checklists[iIndex].action; 
		}
		
		/**
		 * @author	ABS
		 * @date	July 24, 2018
		 * @name	saveMaintenanceReport
		 * @pupose	to save edited checklist report 
		 */
		function saveMaintenanceReport(){
			var arr = [];
			for(var i in vm.checklists){
				var obj = {
					
						checkpoint : {
							checklist_id : vm.checklists[i].checklist_id,
					},
					status : vm.checklists[i].status
				};
				
				arr.push(obj);
			}
			
			/*var iObj = {
				machine_id : vm.checklists[0].checklist.machine.machine_id,	
				checklist : arr,
				overall_status : $scope.observation ? $scope.observation : '',
					done_by : $scope.actionDoneBy
			};*/
			
			var iObj =  {
			        "checkpointlist": arr,
			        "maint_id" : $scope.maint_id,
			        "mode": "bjh",
			        "frequency": "bj hn",
			        "overall_status": vm.observation,
			        "done_by": $scope.actionDoneBy,
			        "machine": {
			            "machine_id": $scope.machineDetails.machine.machine_id,
			            "machine_name": $scope.equipment
			        }
			 }
			
			console.log("REQ "+JSON.stringify(iObj));
			//return;
			maintlistService.saveChecklists(iObj).then(function(iObj) {
				$scope.showEdit = false;
				toastr.success('Successfully submitted');
				loadMaintlists();
			});
		}

		function view(maintlist) {
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/maintlist/maintlistModelView.html',
				controller : 'MaintlistModalCtrl',
				controllerAs : 'vm',
				size : 'md',
				resolve : {
					items : function() {
						return maintlist;
					}
				}
			});

			modalInstance.result.then(function() {
				
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
		}

		function add(maintlist) {
			var usr = maintlist ? maintlist : {};
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/maintlist/maintlistModelAddEdit.html',
				controller : 'MaintlistModalAddEditCtrl',
				controllerAs : 'vm',
				size : 'lg',
				resolve : {
					maintlist : function() {
						return usr;
					}
				}
			});

			modalInstance.result.then(function() {
				loadMaintlists();
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
		}

	}

	function MaintlistModalCtrl($uibModalInstance, items, $scope) {
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

	function MaintlistModalAddEditCtrl($uibModalInstance, maintlist, $scope, maintlistService, $filter) {
		var vm = angular.extend(this, {
			ok : ok,
			cancel : cancel,
			roles : [],
			maintlistTypes : [],
			maintlists : [],
		});

		(function activate() {
			$scope.maintlist = maintlist;
			loadRoles();
			loadMaintlistTypes();
			loadMaintlists();
			/*
			$scope.setTime = function(){
				
				$scope.CurrentDate = new Date();
				
				$scope.cd = $filter('date')($scope.CurrentDate, "hh:mm:ss a");
				
				$scope.maintlist.added_time =  $scope.cd; 
			}
			*/
			
		})();

		// ******************************************************
		

		function loadMaintlists() {
			maintlistService.getMaintlists().then(function(data) {
				vm.maintlists = data;

				console.log(JSON.stringify(vm.maintlists));
			});
		}

		
		function loadRoles(){
			maintlistService.getRoles().then(function(data){
				vm.roles = data;
			});			
		}
		
		function loadMaintlistTypes(){
			maintlistService.getMaintlistTypes().then(function(data){
				vm.maintlistTypes = data;
			});			
		}
		
		function ok(maintlist) {
			
			debugger;
			maintlistService.addMaintlist(maintlist).then(function(){
				$uibModalInstance.close(maintlist);
				sendMail(maintlist);
				sendSms(maintlist);
			});
		}
		
		function sendMail(maintlist){
			maintlistService.sendMail(maintlist).then(function(){
				
			});
		}
		
		function sendSms(maintlist){
			maintlistService.sendSms(maintlist).then(function(){
				
			});
		}

		function cancel() {
			$uibModalInstance.dismiss('cancel');
		}
	}
})();
