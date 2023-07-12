(function() {
	'use strict';

	angular.module('myApp.machine_mst').controller('Machine_mstController', Machine_mstController)
			.controller('Machine_mstModalCtrl', Machine_mstModalCtrl);
//	.controller('Machine_mstModalAddEditCtrl', Machine_mstModalAddEditCtrl);

	Machine_mstController.$inject = [ '$state', 'machine_mstService', 'categoryService', '$uibModal', '$log',
			'$scope', 'toastr','ApiEndpoint','$http' ];
	Machine_mstModalCtrl.$inject = [ '$uibModalInstance', 'items', '$scope' ];
//	Machine_mstModalAddEditCtrl.$inject = [ '$uibModalInstance', 'machine_mst', '$scope', 'machine_mstService', '$filter' ];

	/* @ngInject */
	function Machine_mstController($state, machine_mstService, categoryService, $uibModal, $log, $scope, toastr,ApiEndpoint,$http) {
		
			var uploadsUrl = staticUrl+"/upload";
		
		var vm = angular.extend(this, {
			machine_msts : [],
			categories : [],
			view : view,
			add : add,
			delet : delet,
			ok : ok,
			addNew:addNew,
			viewQr:viewQr,
			print:print,
			upload:upload,
			uploadNew:uploadNew,
			
			
		});

		(function activate() {
			$scope.machine_mst = {};
			loadMachine_msts();
			loadCategories();
			$scope.addNewTab=false;
			$scope.viewQrTab=false;
			$scope.uploadTab=false;
			$scope.qrcodeData="Quipment";
		})();

		// ******************************************************
		function addNew(){
		
			$scope.addNewTab=true;
			$scope.viewQrTab=false;
			$scope.uploadTab=false;
		}
		function upload(){
			$scope.uploadTab=true;
			$scope.addNewTab=false;
			$scope.viewQrTab=false;
		}
		function viewQr(machine){
			$scope.viewQrTab=true;
			$scope.addNewTab=false;
			$scope.uploadTab=false;
			$scope.qrcodeData=machine.eqid;
			console.log("hello Log : "+JSON.stringify(machine))
		}
		
		function uploadNew(){
			var file = document.getElementById('uploadMachine').files[0];
			var url = uploadsUrl + "/uploadMachine";
			if (file == undefined) {
				toastr.error('Please Select a xlsx File');
				return;
			}
			var fd = new FormData();
			fd.append('file', file);
		
			console.log("URL :: "+url)
			$http.post(url, fd, {
				transformRequest: angular.identity,
				headers: {
					'Content-Type': undefined
				}
			})
			
			.then(function successCallback(response) {
			
			$('.loading').hide();
			//window.alert("File uploaded successfully!");
		
			toastr.success('Uploaded....', 'Succesful !!',{ timeOut: 10000 });					
			$scope.uploadTab=false;
			loadMachine_msts();
		}, function errorCallback(response) {
	    	$('.loading').hide();
			//window.alert("File upload - unsuccessfull!");
			//init();
	    	$scope.uploadTab=false;
			loadMachine_msts();
			toastr.error('Upload....', 'UnSuccesful !!');
			//loadDesks();
				    });
			
			
			
		}
		function print(){
			var dataUrl = document.getElementById('anycanvas').innerHTML

			//var windowContent = '<div style="page-break-after: always ;important;"><div style="width:188px;height:68px; margin-top:70px"><span style=" "><span style="padding:5px;margin-left:40px" src="'  + dataUrl + '</span><span style=" font-size: 30px;"></span></div>';
			var windowContent='<div id="anycanvas" style="padding: 5px;display: inline-block;border:2px solid #e67817;margin-left:5px;margin-top:15px">       <span style="padding:5px;margin-left:10px" src="'  + dataUrl + '</span><img src="img/kf-logo.png" class="logo"><br>'+$scope.qrcodeData+'</div>'
			var popupWinindow = window.open('','_blank','width=600,height=700,scrollbars=no,menubar=no,toolbar=no,location=no,status=no,titlebar=no');
			popupWinindow.document.write('<html><body onload="window.print()">' + windowContent + '</html>');
			popupWinindow.document.write('<style> @page {  margin: 15;} </style>');
			popupWinindow.document.close();
		}
		function loadMachine_msts() {
			machine_mstService.getMachine_msts().then(function(data) {
				vm.machine_msts = data;
				
			});
		}
		
		
		function loadCategories() {
			
			categoryService.getCategorys().then(function(data) {
				vm.categories = data;
				
			});
		}
		
		function delet(machine_mst){
			machine_mstService.deleteMachine_mst(machine_mst).then(function(){
				loadMachine_msts();
			});
		}

		
	function ok(machine_mst) {
		if(!machine_mst.machine_name || machine_mst.machine_name == ''){
			toastr.error('Please enter machine name');
			return;
		}
		if(!machine_mst.category || !machine_mst.category.cat_id){
			toastr.error('Please select category');
			return;
		}
		
		if(!machine_mst.eqid || machine_mst.eqid == ''){
			toastr.error('Please enter equipment id name');
			machine_mst.eqid = null;
			return;
		}
		
		if(!machine_mst.model || machine_mst.model == ''){
			toastr.error('Please enter model name');
			return;
		}
		if(!machine_mst.make || machine_mst.make == ''){
			toastr.error('Please enter make name');
			return;
		}
		if(!machine_mst.srNo || machine_mst.srNo == ''){
			toastr.error('Please enter sr no name');
			return;
		}
		if(!machine_mst.capacity || machine_mst.capacity == ''){
			toastr.error('Please enter capacity name');
			return;
		}
		if(!machine_mst.location || machine_mst.location == ''){
			toastr.error('Please enter location name');
			return;
		}
		
		if(!machine_mst.type || machine_mst.type == ''){
			toastr.error('Please select type');
			return;
		}
		
		
//			debugger;
			machine_mstService.addMachine_mst(machine_mst).then(function(){
//				$uibModalInstance.close(machine_mst);
				$scope.machine_mst = {};
				loadMachine_msts();
				$scope.addNewTab=false;
			});
		}
		
	
	
		function view(machine_mst) {
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/machine_mst/machine_mstModelView.html',
				controller : 'Machine_mstModalCtrl',
				controllerAs : 'vm',
				size : 'md',
				resolve : {
					items : function() {
						return machine_mst;
					}
				}
			});

			modalInstance.result.then(function() {
				
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
		}

		function add(machine_mst) {
			$scope.addNewTab=true;
			$scope.machine_mst = Object.assign({}, machine_mst);
			$scope.machine_mst.type = $scope.machine_mst.type.toString();
			
			setTimeout(function(){
				window.scroll({
					  top: 0, 
					  left: 0, 
					  behavior: 'smooth' 
				});
			},0);
			
			/*var usr = machine_mst ? machine_mst : {};
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/machine_mst/machine_mstModelAddEdit.html',
				controller : 'Machine_mstModalAddEditCtrl',
				controllerAs : 'vm',
				size : 'lg',
				resolve : {
					machine_mst : function() {
						return usr;
					}
				}
			});

			modalInstance.result.then(function() {
				loadMachine_msts();
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});*/
		}

	}

	function Machine_mstModalCtrl($uibModalInstance, items, $scope) {
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

	/*function Machine_mstModalAddEditCtrl($uibModalInstance, machine_mst, $scope, machine_mstService, $filter) {
		var vm = angular.extend(this, {
			ok : ok,
			cancel : cancel,
			roles : [],
			machine_mstTypes : [],
			machine_msts : [],
		});

		(function activate() {
			$scope.machine_mst = machine_mst;
			loadRoles();
			loadMachine_mstTypes();
			loadMachine_msts();
			
			$scope.setTime = function(){
				
				$scope.CurrentDate = new Date();
				
				$scope.cd = $filter('date')($scope.CurrentDate, "hh:mm:ss a");
				
				$scope.machine_mst.added_time =  $scope.cd; 
			}
			
			
		})();

		// ******************************************************
		

		function loadMachine_msts() {
			machine_mstService.getMachine_msts().then(function(data) {
				vm.machine_msts = data;

				console.log(JSON.stringify(vm.machine_msts));
			});
		}

		
		function loadRoles(){
			machine_mstService.getRoles().then(function(data){
				vm.roles = data;
			});			
		}
		
		function loadMachine_mstTypes(){
			machine_mstService.getMachine_mstTypes().then(function(data){
				vm.machine_mstTypes = data;
			});			
		}
		
		function ok(machine_mst) {
			
			debugger;
			machine_mstService.addMachine_mst(machine_mst).then(function(){
				$uibModalInstance.close(machine_mst);
				sendMail(machine_mst);
				sendSms(machine_mst);
			});
		}
		
		function sendMail(machine_mst){
			machine_mstService.sendMail(machine_mst).then(function(){
				
			});
		}
		
		function sendSms(machine_mst){
			machine_mstService.sendSms(machine_mst).then(function(){
				
			});
		}

		function cancel() {
			$uibModalInstance.dismiss('cancel');
		}
	}*/
})();
