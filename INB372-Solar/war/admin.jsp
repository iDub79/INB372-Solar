<!DOCTYPE html>
<html id="home" lang="en">
<head>
<title>Solar Power Calculator</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<style type="text/css"> body { padding-top: 60px; } </style>

<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap-responsive.min.css" />
<link rel="stylesheet" type="text/css" href="css/skin.css" />

<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/AjaxCalls.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>

<!--[if lt IE 9]> <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script> <![endif]-->

<script type="text/javascript">
	$(document).ready(function() {
	    $("#navAdmin").addClass("active"); 
	    getPanelList();
	});
</script>

</head>
<body>
    <%@ include file="nav.jsp" %>
    
    <div class="container">
        <div class="row">
            <div class="span6">
                <div class="form-horizontal">
                    <fieldset style="height: 250px;">
                        <legend>Panel Specifications</legend>
                        <div class="control-group" id="grpPanelManufacturer">
                            <label class="control-label" for="txtPanelManufacturer">Panel Manufacturer</label>
                            <div class="controls">
                                <input type="number" class="input-xlarge" id="txtPanelManufacturer" placeholder="Enter manufacturer" />
                            </div>
                        </div>
                        <div class="control-group" id="grpPanelModel">
                            <label class="control-label" for="txtPanelModel">Model</label>
                            <div class="controls">
                                <input type="number" class="input-xlarge" id="txtPanelModel" placeholder="Enter model" />
                            </div>
                        </div>
                        <div class="control-group" id="grpPanelPower">
                            <label class="control-label" for="txtPanelPower">Max Peak Power</label>
                            <div class="controls">
                                <input type="number" class="input-xlarge" id="txtPanelPower" placeholder="Enter max. peak power in watts" />
                            </div>
                        </div>
                        <div class="control-group" id="grpPanelLength">
                            <label class="control-label" for="txtPanelNewLength">Panel Length</label>
                            <div class="controls">
                                <input type="number" class="input-xlarge" id="txtPanelNewLength" placeholder="Enter length in mm" />
                            </div>
                        </div>
                        <div class="control-group" id="grpPanelWidth">
                            <label class="control-label" for="txtPanelNewWidth">Panel Width</label>
                            <div class="controls">
                                <input type="number" class="input-xlarge" id="txtPanelNewWidth" placeholder="Enter width in mm" />
                            </div>
                        </div>
                    </fieldset>
                    <div class="form-actions">
                        <button class="btn btn-large btn-primary" id="btnAddPanel">Add Panel</button>
                        <button class="btn btn-large" id="btnReset">Cancel</button>                 
                    </div>
                    <div class="alert alert-success" id="pnlResults" style="display: none;">
	                    <span id="lblPanel"></span>
	                </div>
	                
                </div>
            </div>
            <div class="span6">
                <div class="form-horizontal">
                    <fieldset style="height: 250px;">
                        <legend>Inverter Specifications</legend>
                        <div class="control-group" id="grpInverterlManufacturer">
                            <label class="control-label" for="txtInverterManufacturer">Inverter Manufacturer</label>
                            <div class="controls">
                                <input type="number" class="input-xlarge" id="txtInverterManufacturer" placeholder="Enter manufacturer" />
                            </div>
                        </div>
                        <div class="control-group" id="grpInverterModel">
                            <label class="control-label" for="txtInverterModel">Model</label>
                            <div class="controls">
                                <input type="number" class="input-xlarge" id="txtInverterModel" placeholder="Enter model" />
                            </div>
                        </div>
                        <div class="control-group" id="grpInverterEfficiency">
                            <label class="control-label" for="txtInverterEfficiency">Efficiency</label>
                            <div class="controls">
                                <input type="number" class="input-xlarge" id="txtInverterEfficiency" placeholder="Enter efficiency as %" />
                            </div>
                        </div>
                    </fieldset>
                    <div class="form-actions">
                        <button class="btn btn-large btn-primary" id="btnAddInverter">Add Inverter</button>
                        <button class="btn btn-large" id="btnReset">Cancel</button>                 
                    </div>
                    <div class="alert alert-success" id="pnlResults" style="display: none;">
                        <span id="lblInverter"></span>
                    </div>
                    
                </div>
            </div>
            <div class="span12">
                <div class="alert alert-error" id="pnlErrors" style="display: none;">
                    <span id="lblErrors">Please correct the fields highlighted in red.</span>
                </div>
            </div>
        </div>
    </div>
</body>
</html>