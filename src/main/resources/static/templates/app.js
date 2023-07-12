angular.module('myApp', [
	'ui.router',
	'LocalStorageModule',
	'ui.bootstrap',
	'toastr',
	'chart.js',
	'ja.qr',
	'myApp.common',
	'myApp.common1',
	'myApp.common2',
	'myApp.generic',
	'myApp.machineOwner',
	'myApp.main',
	'myApp.home',
	'myApp.user',
	'myApp.login_form',
	

	

	'myApp.roleToUser',
	'myApp.userToRole',

	'myApp.roleManagement',
	'myApp.reports',
	
	
	
	/*maintenance*/
	'myApp.machine',
	'myApp.machine_mst',
	'myApp.checklist',
	'myApp.maintlist',
	'myApp.shift',
	'myApp.category',
	'myApp.breakdownupdate',
	'myApp.breakdown',
	'myApp.trial',
	'myApp.ftr',
	'myApp.mttr',
	'myApp.mtbf',
	'myApp.breakdownhistory',
	'myApp.maintDepertment',
	'myApp.cunsumption_device',
	'myApp.consumptionReport',
	'myApp.maintreport',
	
	
	
	
])

.value('_', window._)

.constant('ApiEndpoint', {
  url: '',// 162.251.83.105
  userKey : 'renataLoggedInUser'
})

.run(function(localStorageService, $location, $rootScope, $state){
	
	$rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams){
		
		//console.log(JSON.stringify(localStorageService.get('myeplanAdminUser')));
//		$location.url("/main/home");
		
		if(sessionStorage.getItem('renataLoggedInUser') == null){
//		if(localStorageService.get('renataLoggedInUser') == null){
			$location.url('/login_form');
		}
		
	});
	
})
.config(function($urlRouterProvider) {
	// if none of the above states are matched, use this as the fallback
	
	$urlRouterProvider.otherwise("/main/home");
});