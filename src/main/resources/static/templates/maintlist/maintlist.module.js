(function() {
	'use strict';

	angular
	.module('myApp.maintlist', [ 'datatables' ])
	.config(function($stateProvider) {
				$stateProvider
				.state('main.maintlist', {
					url : "/maintlist",
					views : {
						"sub" : {
							templateUrl : "templates/maintlist/maintlist.html",
							controller : "MaintlistController as vm"
						}
					}
				})
			});

})();