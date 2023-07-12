(function() {
	'use strict';

	angular.module('myApp.checklist').controller('checklistController', checklistController)
			.controller('checklistModalCtrl', checklistModalCtrl).controller('checklistPopupCtrl', checklistPopupCtrl);

	checklistController.$inject = [ '$http','$state', 'checklistService', '$uibModal', '$log',
			'$scope', 'toastr', 'ApiEndpoint','machine_mstService', 'fileUpload','$filter', '$window','genericFactory'];
	checklistModalCtrl.$inject = [ '$uibModalInstance', 'items', '$scope','$filter'];
	checklistPopupCtrl.$inject = [ '$uibModalInstance', 'items', '$scope','$filter','$rootScope'];
	

	/* @ngInject */
	function checklistController($http,$state, checklistService, $uibModal, $log, $scope, toastr, ApiEndpoint, machine_mstService, fileUpload,$filter, $window,genericFactory) {
		var uploadsUrl = staticUrl+"/upload";
		var checklistUrl = staticUrlMaintenance+"checklist";
		var machineUrl = staticUrlMaintenance+"/machine_mst";

		var vm = angular.extend(this, {
			checklists : [],
			ok : ok,
			uploadXlxs : uploadXlxs,
			view : view,
			add : add,
			delet : delet,
			changeFile : changeFile,
			changeMode : changeMode,
			submitChecklist : submitChecklist,
			increment : increment,
			decrement : decrement,
			uploadNew:uploadNew,
			upload:upload,
			getMachinesByName:getMachinesByName
			
		});
	//	vm.myFile = 'dcfvsdg';

		(function activate() {
			$scope.uploadTab=false
			$scope.selectedMachine = 'selectMachine';
			$scope.selectedMode	= 'selectFrequency';
			$scope.selectedType	=	'selectType';
			$scope.checkpointCounter = 0;
			$scope.myFile = '';
			loadchecklists();
			//loadMachines();
			loadMachineNames();
		})();
		function loadMachineNames() {
			machine_mstService.getMachineNames().then(function(data) {
				vm.machineNames = data;
				console.log(JSON.stringify(vm.machineNames));
			});
		}
		function getMachinesByName(machineName) {
		/*	machine_mstService.getMachine_msts().then(function(data) {
				vm.machines = data;
//				console.log(JSON.stringify(vm.machines));
			});*/
			console.log("machine Names : "+machineName)
			var obj={};
			obj.machine_name=machineName
			var msg=""
				 var url =machineUrl+"/getGetMachinesByName";
				genericFactory.add(msg,url,obj).then(function(response) {
				vm.machines= response.data;
				console.log("machines : "+JSON.stringify(vm.machines))
								
			});
		}
		function uploadNew(){
			$scope.uploadTab=true
		}
		
		
		function upload(){
			
			var file = document.getElementById('uploadMachine').files[0];
			var url = uploadsUrl + "/uploadCheckList";
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
			
		}
		
		
		function ok(checklist) {
			debugger;
			checklistService.addchecklist(checklist).then(function() {
				$uibModalInstance.close(checklist);
			});
			loadchecklists();
			
		}
		
		function changeMode(selectedMode){
			$scope.selectedMode = selectedMode;
		}
		
		function loadMachines() {
			machine_mstService.getMachine_msts().then(function(data) {
				vm.machines = data;
//				console.log(JSON.stringify(vm.machines));
			});
		}
		
		function increment(){
			$scope.checkpointCounter++;
		}
		
		function decrement(){
			$scope.checkpointCounter--;
		}
		
		function submitChecklist(){
			
			var arr = [];
			if($scope.selectedMachine == 'selectMachine'){
				toastr.error('Please select machine');
				return;
			}
			if($scope.selectedType	==	'selectType'){
				toastr.error('Please select type');
				return;
			}
			if($scope.selectedMode	== 'selectFrequency'){
				toastr.error('Please select frequency');
				return;
			}
			
			for(var i = 0; i <= $scope.checkpointCounter; i++){
				var val = document.getElementById('checkpoint' + i).value;
				var op = document.getElementById('operation' + i).value;
				var range = document.getElementById('acceptableRange' + i).value;
				if((!val && val == '') || (!op && op =='')){
					if((!val && val == '')){
						toastr.error('Please enter checkpoint');
						document.getElementById('checkpoint' + i).focus();
					}else{
						toastr.error('Please enter operation');
						document.getElementById('operation' + i).focus();
					}
					
					return;
				}else{
					var iObj = {
							task : val,
							operation : op,
							acceptableRange : range
					}
					arr.push(iObj);
				}
			}
			var machineObj = JSON.parse($scope.selectedMachine);
			
			var iObj = {
					"machine" : {
						"machine_id" : machineObj.machine_id
					},
					"checklist" : arr,
					"frequency" : $scope.selectedMode,
					"type"	:	$scope.selectedType
			};
			
//			debugger;
			
			checklistService.addchecklist(iObj).then(function(response) {
//				vm.checklist = data;
//				console.log(JSON.stringify(vm.machines));
//				if(response.qrList && response.qrList.length > 0){
				//$scope.machine_mst.type = "";
				$scope.selectedMachine = "selectMachine";
				$scope.selectedType = 'selectType';
				$scope.selectedMode = 'selectFrequency';
				var count = $scope.checkpointCounter;
				loadchecklists();
				while(count > 0){
					decrement();
			        $("#TextBoxDiv" + count ).remove();
			        $("#labelbox" + count ).remove();
			        count --;
				}
					viewPopup(response);
//				}
			});
		}
		
		/*$scope.$on('done', function(event, data) {
			loadchecklists();
		});*/
		
		function changeFile(file){
			console.log(file);
		}
		
		function viewPopup(data) {
			var asset = data ? data : {};
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/checklist/checklistPopup.html',
				controller : 'checklistPopupCtrl',
				controllerAs : 'vm',
				size : 'md',
				resolve : {
					items : function() {
						return asset;
					}
				}
			});

			modalInstance.result.then(function() {

			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
		}
		
		//************************************************
		function uploadXlxs() {
			
			debugger;	
			var file = document.getElementById('fileAsset').files[0];
//			$scope.a = vm.myFile;
		
//			file = file;
		
			var uploadUrl = checklistUrl + "/uploadFile";
			fileUpload.uploadFileToUrl(file, uploadUrl);
		
			angular.element("input[type='file']").val(null);
		}
		
		function loadchecklists() {
			checklistService.getchecklists().then(function(data) {
				vm.checklists = data;
			
			});
		}
		
		function delet(checklist){
			checklistService.deleteChecklist(checklist).then(function(){
				loadchecklists();
			});
		}

		function view(checklist) {
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/checklist/checklistModelView.html',
				controller : 'checklistModalCtrl',
				controllerAs : 'vm',
				size : 'md',
				resolve : {
					items : function() {
						return checklist;
					}
				}
			});

			modalInstance.result.then(function() {
				loadchecklists();
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
			loadchecklists();
		}

		function add(checklist) {
			var loc = checklist ? checklist : {};
			// alert(JSON.stringify(usr));
			var modalInstance = $uibModal.open({
				animation : true,
				ariaLabelledBy : 'modal-title',
				ariaDescribedBy : 'modal-body',
				templateUrl : 'templates/checklist/checklist.html',
				controller : 'checklistController',
				controllerAs : 'vm',
				size : 'lg',
				resolve : {
					checklist : function() {
						return loc;
					}
				}
			});

			modalInstance.result.then(function() {
				loadchecklists();
			}, function() {
				$log.info('Modal dismissed at: ' + new Date());
			});
			loadchecklists();
		}

	}

	function checklistModalCtrl($uibModalInstance, items, $scope) {
		var vm = angular.extend(this, {
			items : items,
			ok : ok,
			cancel : cancel
		});

		(function activate() {

		})();

		// ******************************************************
		console.log(vm.items);
		function ok() {
			$uibModalInstance.close();
		}

		function cancel() {
			$uibModalInstance.dismiss('cancel');
		}
	}
	
	function checklistPopupCtrl($uibModalInstance, items, $scope, $rootScope) {
		var vm = angular.extend(this, {
			items : items,
			ok : ok,
			cancel : cancel
		});
		console.log(items);
		(function activate() {

		})();

		// ******************************************************

		function ok() {
			$uibModalInstance.close();
		}

		function cancel() {
			/*$rootScope.$broadcast('done', {
                str: 'something'
            });*/
			$uibModalInstance.dismiss('cancel');
		}
	}

	
})();

