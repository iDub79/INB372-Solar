<!DOCTYPE html>
<html id="home" lang="en">
<head>
    <title>QUnit Tests</title>

    <link rel="stylesheet" type="text/css" href="/css/skin.css" />
    
    <script type="text/javascript" src="/js/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="/js/AjaxCalls.js"></script>
    <script type="text/javascript" src="/js/scripts.js"></script>
    
    <!-- QUnit Unit Testing -->
    <link rel="stylesheet" type="text/css" href="qunit.css" />
    <script type="text/javascript" src="qunit.js"></script>
    <script type="text/javascript" src="tests.js"></script>
</head>
<body>
    <div id="qunit"></div>
    <div id="qunit-fixture">
        <div class="alert alert-success" id="pnlResults">
             <span id="lblSavings">Result</span>
         </div>
         <div class="alert alert-error" id="pnlErrors" style="display: none;">
             <span id="lblErrors">Please correct the fields highlighted in red.</span>
         </div>
    </div>
</body>
</html>