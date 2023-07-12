(function() {
	'use strict';

	angular.module('myApp.shift').controller('ShiftController', ShiftController)
			.controller('ShiftModalCtrl', ShiftModalCtrl).controller(
					'ShiftModalAddEditCtrl', ShiftModalAddEditCtrl);

	ShiftController.$inject = [ '$state', 'shiftService', '$uibModal', '$log',
			'$scope', 'toastr', 'categoryService'];
	ShiftModalCtrl.$inject = [ '$uibModalInstance', 'items', '$scope' ];
	ShiftModalAddEditCtrl.$inject = [ '$uibModalInstance', '$filter', 'shift', '$scope', 'shiftService'];

	/* @ngInject */
	function ShiftController($state, shiftService, $uibModal, $log, $scope, toastr, categoryService) {
		var vm = angular.extend(this, {
			shifts : [],
			targets : [],
			view : view,
			add : add,
			delet : delet,
			ok : ok
		});

		(function activate() {
			$scope.shift = {};
			$scope.target = {};
			loadShifts();
			loadTargets();
			loadCategories();
			
			$scope.yearArr = [];
			
			var currentYear = new Date().getFullYear();
			
			$scope.yearArr.push(currentYear);
			$scope.yearArr.push(currentYear+1);
			
			
		//	$scope.target.year = currentYear;
			
			
		})();

		// ******************************************************

		
		function ok(target) {
//			var ele = document.getElementById('shiftName').value = '';
		
			/*debugger;*/
			
			if(!target.category || !target.category.cat_id){
				toastr.error('Please select category');
				return;
			}
			if(!target.year || target.year == ''){
				toastr.error('Please select year');
				return;
			}
			if(!target.month || target.month == ''){
				toastr.error('Please select month');
				return;
			}
			if(!target.type || target.type == ''){
				toastr.error('Please select type');
				return;
			}
			if(!target.hour || target.hour == ''){
				toastr.error('Please enter hours');
				return;
			}
			
			shiftService.addShift(target).then(function(){
				$scope.target = {};
				loadTargets();
//				$uibModalInstance.close(shift);
				
			});
		}
		
		
		
		function loadCategories() {
			
			categoryService.getCategorys().then(function(data) {
				vm.categories = data;
				
			});
		}

		function loadShifts() {
			shiftService.getShifts().then(function(data) {
				vm.shifts = data;
			});
		}
		
		
		function loadTargets() {
			shiftService.getTargets().then(function(data) {
				vm.targets = data;
			});
		}
		
		
		function delet(target){
			shiftService.deleteShift(target).then(function(){
				loadTargets();
			});
		}

		function view(shift) {
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/shift/shiftModelView.html',
				controller : 'ShiftModalCtrl',
				controllerAs : 'vm',
				size : 'md',
				resolve : {
					items : function() {
						return shift;
					}
				}
			});

			modalInstance.result.then(function() {
				
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
		}

		function add(target) {
//			$scope.shift.shift_name = shift.shift_name;
//			$scope.shift.deletes = shift.deletes;
//			$scope.shift.shift_id = shift.shift_id;
			$scope.target = {};
			$scope.target.category = target.category;
//			$scope.target.category.cat_id = target.category.cat_id;
			$scope.target.year = target.year;
			$scope.target.month = target.month;
			$scope.target.type = target.type;
			$scope.target.hour = target.hour;
			$scope.target.target_id = target.target_id;
			
			setTimeout(function(){
				window.scroll({
					  top: 0, 
					  left: 0, 
					  behavior: 'smooth' 
				});
			}, 0);
			
			/*var dept = shift ? shift : {};
			// alert(JSON.stringify(usr));
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/shift/shiftModelAddEdit.html',
				controller : 'ShiftModalAddEditCtrl',
				controllerAs : 'vm',
				size : 'lg',
				resolve : {
					shift : function() {
						return dept;
					}
				}
			});

			modalInstance.result.then(function() {
				loadShifts();
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});*/
		}

	}

	function ShiftModalCtrl($uibModalInstance, items, $scope) {
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

	function ShiftModalAddEditCtrl($uibModalInstance, $filter, shift, $scope, shiftService) {
		var vm = angular.extend(this, {
			ok : ok,
			cancel : cancel,
		});

		(function activate() {
			$scope.shift = shift;
			
			/*$scope.setDeletes = function(){
				
				$scope.shift.deletes = 1;
				
			}*/
			$scope.setFormDate = function() {

				$scope.st = $scope.shift.shift_start;
				$scope.gt = $scope.shift.shift_end;

				
				$scope.shift.shift_start = $filter('date')($scope.st,
						"hh:mm:ss a");
				$scope.shift.shift_end = $filter('date')($scope.gt,
						"hh:mm:ss a");
			
			}
			
			
			
		})();

		// ******************************************************
		
		function ok(shift) {
			
			/*debugger;*/
			shiftService.addShift(shift).then(function(){
				$uibModalInstance.close(shift);
				
			});
		}

		
		
		
		function cancel() {
			$uibModalInstance.dismiss('cancel');
		}
	}
})();
