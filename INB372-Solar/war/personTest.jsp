<!DOCTYPE html>
<html id="home" lang="en">
<head>
	<title>Person Example</title>
	
	<link rel="stylesheet" type="text/css" href="css/ui-lightness/jquery-ui-1.8.22.custom.css" />
	<link rel="stylesheet" type="text/css" href="css/skin.css" />
	
	<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="js/jquery-ui-1.8.22.custom.min.js"></script>
	<script type="text/javascript" src="js/AjaxCalls.js"></script>
    <script type="text/javascript" src="js/scripts.js"></script>
</head>
<body>
	<form>
		<p>
			<label for="personName">Name:</label>
			<input type="text" id="personName" size="20" placeholder="Enter your name" autofocus="autofocus" />
			<span id="reqPersonName" style="display: none;" class="requiredMsg">* Required</span>
		</p>
		<p>
			<label for="personAge">Age:</label>
			<input type="number" id="personAge" size="20" placeholder="Enter your age" />
			<span id="reqPersonAge" style="display: none;" class="requiredMsg">* Required</span>
		</p>		
	</form>	
	<button id="btnSubmitPersonTest">Submit</button>
    <p id="lblResult"></p>
</body>
</html>