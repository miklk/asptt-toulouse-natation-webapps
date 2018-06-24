/**
 * 
 */
var files = null;
var documentController = angular.module('DocumentController', ['ngRoute', 'documentServices', 'libelleServices']);

documentController.controller('DocumentController', ['$http', '$scope', '$location', 'DocumentService', 'LibelleService', function($http, $scope, $location, DocumentService, LibelleService) {
	function init() {
		$scope.searchText = "";
		$scope.showCreationPanel = false;
		DocumentService.list.query({}, function (data) {
			$scope.documents = data.documents;
		});
		$("#panel-documents").toggleClass("col-md-11", true);
		$("#panel-documents").toggleClass("col-md-8", false);
		$("#panel-edit").toggleClass("hidden", true);
		$("#panel-edit").toggleClass("col-md-4", false);
		$("#panel-upload").toggleClass("col-md-4", false);
		$("#panel-upload").toggleClass("hidden", true);
	}
	init();
	$scope.refresh = function() {
		init();
	};
	
	$scope.selectDocument = function(document) {
		$scope.currentDocument = document;
		$scope.documentUpdateAction = {
				title: $scope.currentDocument.title,
				summary: $scope.currentDocument.summary,
				libelles: $scope.currentDocument.libelles
		};
		LibelleService.list.query({}, function(data) {
			$scope.libelles = data.wholeLibelles;
		});
		
		$("#panel-documents").toggleClass("col-md-11", false);
		$("#panel-documents").toggleClass("col-md-8", true);
		$("#panel-edit").toggleClass("hidden", false);
		$("#panel-edit").toggleClass("col-md-4", true);
		$scope.showCreationPanel = false;
		$('html, body').animate({  
            scrollTop:$("#panel-edit").offset().top  
        }, 'slow');
	};
	
	$scope.hideEdit = function() {
		$scope.currentDocument = null;
		$("#panel-documents").toggleClass("col-md-11", true);
		$("#panel-documents").toggleClass("col-md-8", false);
		$("#panel-edit").toggleClass("hidden", true);
		$("#panel-edit").toggleClass("col-md-4", false);
	};
	
	$scope.showUploadPanel = function() {
		$scope.showCreationPanel = true;
		$("#panel-documents").toggleClass("col-md-11", false);
		$("#panel-documents").toggleClass("col-md-8", true);
		$("#panel-upload").toggleClass("hidden", false);
		$("#panel-upload").toggleClass("col-md-4", true);
		$scope.documentUploadAction = {
				title: '',
				summary: '',
				libelles: null
		};
		LibelleService.list.query({}, function(data) {
			$scope.libelles = data.wholeLibelles;
		});
		
		document.querySelector('#drop-zone').addEventListener('dragover', function(e) {
		    e.preventDefault();
		    $("#drop-zone").addClass("upload-drop-zone-drop");
		    $("#drop-zone").removeClass("upload-drop-zone");
		}, false);
		document.querySelector('#drop-zone').addEventListener('drop', function(e) {
		    e.preventDefault();
		    files = e.dataTransfer.files;
		    var output = [];
		    for(var i = 0 ; i < files.length ; i++) {
		    	output.push('<li>' + files[i].name + '</li>');
		    }
		    $("#drop-zone-list").html('<ul>' + output.join('') + '</ul>');
		}, false);
	};
	
	$scope.hideUpload = function() {
		$scope.currentDocument = null;
		$("#panel-documents").toggleClass("col-md-11", true);
		$("#panel-documents").toggleClass("col-md-6", false);
		$("#panel-upload").toggleClass("col-md-6", false);
		$("#panel-upload").toggleClass("hidden", true);
		$scope.showCreationPanel = false;
	};
	
	$scope.editDocument = function() {
		console.log($scope.documentUpdateAction);
		DocumentService.update.query({documentId: $scope.currentDocument.id}, $scope.documentUpdateAction, function (data) {
			$scope.documentUpdateResult = data;
			init();
		});
	};
	
	$scope.remove = function() {
		if(confirm("Supprimer définitivement ce document ?")) {
			DocumentService.remove.query({documentId: $scope.currentDocument.id}, function(data) {
				console.log(data);
				init();
			});
		}
	};
	
	$scope.upload = function() {
		var formData = new FormData();
		var file = null;
		if(files == null) {
			var elt = document.getElementById('fileUploadInput');
			file = elt.files[0]
		} else {
			file = files[0];
		}
		formData.append("file", file);
        formData.append("action", angular.toJson($scope.documentUploadAction));
        $http.post("/resources/documents/upload", formData, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).
        success(function (data, status, headers, config) {
           $scope.documentUploadResult = {
        		   success: true
           };
           init();
        }).
        error(function (data, status, headers, config) {
        	$scope.documentUploadResult = {
         		   success: false
            };
        });
	}
}]);