 femaleImagesApp.controller("FemaleImagesController", function($scope, $http) {
	var urlStr = '/femaleImages';
	
	var pageNum = $.urlParam("pageNum");
	if (pageNum) {
		urlStr += "?pageNum=" + pageNum;
	}
	
    $http({
    	method: 'GET',
    	url: urlStr
    }).then(function successCallback(response) {
    	$scope.images = response.data; 
    }, function errorCallback(response) {
    	alert("I got a error");
    });
    
    
    $scope.showingResults = function(startingResult, endingResult) {
    	return "Showing Results " + startingResult + " - " + endingResult;
    }
 });
 