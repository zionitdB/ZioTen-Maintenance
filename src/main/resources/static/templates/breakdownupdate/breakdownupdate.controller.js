(function() {
	'use strict';

	angular.module('myApp.breakdownupdate').controller('BreakdownupdateController', BreakdownupdateController)
			.controller('BreakdownupdateModalCtrl', BreakdownupdateModalCtrl).controller(
					'BreakdownupdateModalAddEditCtrl', BreakdownupdateModalAddEditCtrl);

	BreakdownupdateController.$inject = [ '$state', '$filter', 'breakdownupdateService', '$uibModal', '$log',
			'$scope', 'toastr', 'machine_mstService', 'shiftService','localStorageService', 'breakdownService'];
	BreakdownupdateModalCtrl.$inject = [ '$uibModalInstance', 'items', '$scope' ];
	BreakdownupdateModalAddEditCtrl.$inject = [ '$uibModalInstance', '$filter', 'breakdownupdate', '$scope', 'breakdownupdateService' ];

	/* @ngInject */
	function BreakdownupdateController($state,$filter, breakdownupdateService, $uibModal, $log, $scope, toastr, machine_mstService, shiftService,localStorageService, breakdownService) {
		var vm = angular.extend(this, {
			breakdownupdates : [],
			breakdowns : [],
			view : view,
			add : add,
			delet : delet,
			ok : ok,
			addbuttonn	:	addbuttonn,
			removebuttonn	:	removebuttonn
		});

		(function activate() {
			$scope.breakdownupdate = {};
			$scope.userDetails = sessionStorage.getItem('renataLoggedInUser'); //localStorageService.get('myeplanAdminUser');
			$scope.userDetails = JSON.parse($scope.userDetails);
			$scope.breakdownupdate.action_by = $scope.userDetails.firstName;
			$scope.selectedEquipment = 'selectEquipment';
			$scope.selectedShift = 'select shift';
			$scope.date = new Date();
			$scope.showEdit = false;
			$scope.machine_mst = {
				type : ""
			};
			loadBreakdownupdates();
			loadShifts();
			loadMachines();
			loadBreakdowns();
		})();
		
		var counter = 1;
	
		// ******************************************************

		
		function loadBreakdowns() {
			breakdownService.getBreakdowns().then(function(data) {
				vm.breakdowns = data;
			});
		}
		
		
		
		function validateBeforeTrial(breakdownupdate){
			if(!breakdownupdate.bd_slip){
				toastr.error('Please enter slip no');
				return true;
			}
			if($scope.selectedShift == 'select shift'){
				toastr.error('Please select shift');
				return true;	
			}
			if(!$scope.date){
				toastr.error('Please enter date');
				return true;	
			}
			if($scope.machine_mst.type == ''){
				toastr.error('Please select type');
				return true;
			}
			if($scope.selectedEquipment == 'selectEquipment'){
				toastr.error('Please select Equipment');
				return true;
			}
			if(!breakdownupdate.observation || breakdownupdate.observation == ''){
				toastr.error('Please enter observation');
				return true;
			}
			if(!breakdownupdate.root_cause || breakdownupdate.root_cause == ''){
				toastr.error('Please enter root cause');
				return true;
			}
			if(!breakdownupdate.action_taken || breakdownupdate.action_taken == ''){
				toastr.error('Please enter action taken');
				return true;
			}
			/*if(!breakdownupdate.prev_action_plan || breakdownupdate.prev_action_plan == ''){
				toastr.error('Please enter action taken');
				return true;
			}*/
			/*if(!document.getElementById('namespa0').value || document.getElementById('namespa0').value==''){
				toastr.error('Please enter spare');
				document.getElementById('namespa0').focus();
				return true;
			}
			if(!document.getElementById('qtyspa0').value || document.getElementById('qtyspa0').value==''){
				toastr.error('Please enter quantity for spare');
				document.getElementById('qtyspa0').focus();
				return true;
			}
			*/
			return false;
		}
		
		function ok(breakdownupdate) {
			console.log("OK BREAKDWON ")
			var br ={}
			br = $scope.breakDwn
			br.action_taken=breakdownupdate.action_taken
			br.prev_action_plan=breakdownupdate.prev_action_plan
			br.root_cause=breakdownupdate.root_cause
			br.observation=$scope.breakdownupdate.observation
			console.log("BR :: "+JSON.stringify(br))
			
			breakdownupdateService.updateBreakDown(br);
			if(validateBeforeTrial(breakdownupdate)){
				return;
			}
			
			var arr = [];
			for(var i=0; i < counter; i++){
				if(document.getElementById('namespa'+i).value && document.getElementById('namespa'+i).value!='' && document.getElementById('qtyspa'+i).value && document.getElementById('qtyspa'+i).value!=''){
					var obj = {
							spare : document.getElementById('namespa'+i).value,
							qty : document.getElementById('qtyspa'+i).value
					};
					arr.push(obj);
				}
			}
//			var dd = $scope.date.getDate() < 10 ? '0' + $scope.date.getDate() : $scope.date.getDate();
//			var mm = $scope.date.getMonth() + 1;
//			mm = mm < 10 ? '0' + mm : mm;
			//var date = new Date();
			var yy = $scope.date.getFullYear();
			breakdownupdate.deletes = 1;
			breakdownupdate.shift = JSON.parse($scope.selectedShift);
			breakdownupdate.machine = JSON.parse($scope.selectedEquipment);
			breakdownupdate.breakdown_date = $scope.date	//dd + '-' + mm + '-' + yy;
			breakdownupdate.breakdownupdate = arr;
			breakdownupdate.breakdown = $scope.breakdownupdate.breakdown;
			
			breakdownupdateService.addBreakdownupdate(breakdownupdate).then(function(){
				$scope.breakdownupdate = {};
				loadBreakdownupdates();
				loadBreakdowns();
//				$uibModalInstance.close(breakdownupdate);
				$scope.showEdit = false;
			});
		}
		
		function addbuttonn(){
			var newTextBoxDiv = $(document.createElement('tr'))
		    .attr("id", 'TextBoxDiv' + counter);
		newTextBoxDiv.after().html('<td><input type="text" name="namespa' + counter +'" id="namespa' + counter + '" value="" style="width:150px" ></td><td><input type="text" name="qtyspa' + counter +'" id="qtyspa' + counter + '" value="" style="width:63px" ></td>');        
		newTextBoxDiv.appendTo("#spaceuse");		
		counter++;
		}
			
		   
		function removebuttonn(){
			if(counter==1){
		        alert("No more textbox to remove");
		        return false;
		     }          
			counter--;
		      $("#TextBoxDiv" + counter).remove();
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

		function loadBreakdownupdates() {
			breakdownupdateService.getBreakdownupdates().then(function(data) {
				vm.breakdownupdates = data;
			});
		}
		
		function delet(breakdownupdate){
			breakdownupdateService.deleteBreakdownupdate(breakdownupdate).then(function(){
				loadBreakdowns();
			});
		}

		function view(breakdownupdate) {
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/breakdownupdate/breakdownupdateModelView.html',
				controller : 'BreakdownupdateModalCtrl',
				controllerAs : 'vm',
				size : 'md',
				resolve : {
					items : function() {
						return breakdownupdate;
					}
				}
			});

			modalInstance.result.then(function() {
				
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
		}

		function add(breakdownupdate) {
			$scope.breakdownupdate.action_by=$scope.userDetails.firstName;
			$scope.breakDwn=breakdownupdate;
			$scope.showEdit = true;
			$scope.breakdownupdate.breakdown = breakdownupdate;
			$scope.breakdownupdate.breakdownupdate_name = breakdownupdate.breakdownupdate_name;
//			$scope.breakdownupdate.deletes = breakdownupdate.deletes;
			$scope.breakdownupdate.breakdownupdate_id = breakdownupdate.breakdownupdate_id;
			$scope.breakdownupdate.bd_slip = breakdownupdate.bd_slip;
			$scope.breakdownupdate.observation = breakdownupdate.observation;
			$scope.selectedShift = JSON.stringify(breakdownupdate.shift);
			$scope.selectedEquipment = JSON.stringify(breakdownupdate.machine);
			$scope.machine_mst.type = breakdownupdate.machine.type.toString();
			
			
			setTimeout(function(){
				window.scroll({
				  top: document.body.scrollHeight, 
				  left: 0, 
				  behavior: 'smooth' 
				});
			},0);
			
			
			/*var dept = breakdownupdate ? breakdownupdate : {};
			// alert(JSON.stringify(usr));
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/breakdownupdate/breakdownupdateModelAddEdit.html',
				controller : 'BreakdownupdateModalAddEditCtrl',
				controllerAs : 'vm',
				size : 'lg',
				resolve : {
					breakdownupdate : function() {
						return dept;
					}
				}
			});

			modalInstance.result.then(function() {
				loadBreakdownupdates();
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});*/
		}

	}

	function BreakdownupdateModalCtrl($uibModalInstance, items, $scope) {
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

	function BreakdownupdateModalAddEditCtrl($uibModalInstance, $filter, breakdownupdate, $scope, breakdownupdateService) {
		var vm = angular.extend(this, {
			ok : ok,
			cancel : cancel,
		});

		(function activate() {
			$scope.breakdownupdate = breakdownupdate;
			
			/*$scope.setDeletes = function(){
				
				$scope.breakdownupdate.deletes = 1;
				
			}*/
			$scope.setFormDate = function() {

				$scope.st = $scope.breakdownupdate.breakdownupdate_start;
				$scope.gt = $scope.breakdownupdate.breakdownupdate_end;

				
				$scope.breakdownupdate.breakdownupdate_start = $filter('date')($scope.st,
						"hh:mm:ss a");
				$scope.breakdownupdate.breakdownupdate_end = $filter('date')($scope.gt,
						"hh:mm:ss a");
			
			}
			
			
			
		})();

		// ******************************************************
		
		function ok(breakdownupdate) {
			
			/*debugger;*/
			breakdownupdateService.addBreakdownupdate(breakdownupdate).then(function(){
				$uibModalInstance.close(breakdownupdate);
				
			});
		}

		
		
		
		function cancel() {
			$uibModalInstance.dismiss('cancel');
		}
	}
})();
