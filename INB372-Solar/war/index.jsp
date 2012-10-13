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
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyBbIURPjdlsOMzJagYMlrMe7BDRAOiprN0&libraries=places&sensor=false"></script>
<script type="text/javascript" src="js/GoogleMaps.js"></script>
<script type="text/javascript" src="js/date-en-AU.js"></script>
<script type="text/javascript" src="js/AjaxCalls.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>

<%@ include file="jqPlot.jsp" %>

<!--[if lt IE 9]>
    <script type="text/javascript" src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <script type="text/javascript" src="js/excanvas.js"></script>
<![endif]-->

<script type="text/javascript">
	$(function() {
	    $("#navHome").addClass("active");
	    getPanelManufacturers();
	    getInverterManufacturers();
	    getStateTariffs();
	});
</script>

</head>
<body>
	<%@ include file="nav.jsp" %>
	
	<div class="container">
		<div class="row">
			<div class="span6">
				<div class="form-horizontal">
					<fieldset>
						<legend>Panel Specifications</legend>
						<div class="control-group" id="grpPanelManufacturer">
                            <label class="control-label" for="ddlPanelManufacturer">Manufacturer</label>
                            <div class="controls">
                                <select id="ddlPanelManufacturer">
                                    <option value="-1">-- Select Panel Manufacturer --</option>
                                </select>
                            </div>
                        </div>
                        <div class="control-group" id="grpPanelModel">
                            <label class="control-label" for="ddlPanelModel">Model</label>
                            <div class="controls">
                                <select id="ddlPanelModel">
                                    <option value="-1">-- Select Panel Model --</option>
                                </select>
                            </div>
                        </div>                        
						<div class="control-group" id="grpPanelEfficiency">
							<label class="control-label" for="txtPanelEfficiency">Panel max output</label>
							<div class="controls">
								<input type="number" class="input-large" id="txtPanelEfficiency" placeholder="Enter output in watts" />
							</div>
						</div>
						<div class="control-group" id="grpPanelQty">
                            <label class="control-label" for="txtPanelQty">Quantity of panels</label>
                            <div class="controls">
                                <input type="number" class="input-large" id="txtPanelQty" placeholder="Enter quantity" />
                            </div>
                        </div>
                        
						<legend>Inverter Specifications</legend>
						<div class="control-group" id="grpInverterManufacturer">
                            <label class="control-label" for="ddlInverterManufacturer">Manufacturer</label>
                            <div class="controls">
                                <select id="ddlInverterManufacturer">
                                    <option value="-1">-- Select Inverter Manufacturer --</option>
                                </select>
                            </div>
                        </div>
                        <div class="control-group" id="grpInverterModel">
                            <label class="control-label" for="ddlInverterModel">Model</label>
                            <div class="controls">
                                <select id="ddlInverterModel">
                                    <option value="-1">-- Select Inverter Model --</option>
                                </select>
                            </div>
                        </div>
						<div class="control-group" id="grpInverterEfficiency">
							<label class="control-label" for="txtInverterEfficiency">Inverter efficiency</label>
							<div class="controls">
								<input type="number" class="input-large" id="txtInverterEfficiency" placeholder="Enter efficiency as %" />
							</div>
						</div>
                        <legend>Power Consumption</legend>
                        <div class="control-group" id="grpPowerConsumption">
                            <label class="control-label" for="txtPowerConsumption">Power Consumption</label>
                            <div class="controls">
                                <input type="number" class="input-large" id="txtPowerConsumption" placeholder="Enter consumption in kilowatts " />
                                <span class="help-block">Average daily consumption during sunlight hours.</span>
                            </div>
                        </div>
					</fieldset>
				</div>
			</div>			
			<div class="span6">
				<div class="form-horizontal">
					<fieldset>
						<legend>Property Location</legend>
						<div class="form-actions">
                            <button class="btn btn-info" id="btnSearchAddress">Get Address Co-ordinates</button>
						</div>
						<div class="control-group" id="grpLatitude">						    
							<label class="control-label" for="txtLatitude">Latitude</label>
							<div class="controls">
                                <input type="number" class="input-large" id="txtLatitude" disabled="disabled" />																
							</div>
						</div>
                        <div class="control-group" id="grpLongitude">
							<label class="control-label" for="txtLongitude">Longitude</label>
                            <div class="controls">
                                <input type="number" class="input-large" id="txtLongitude" disabled="disabled" />                                                                
                            </div>
						</div>
                        <div class="control-group" id="grpPanelAngle">
                            <label class="control-label" for="txtPanelAngle">Panel Angle</label>
                            <div class="controls">
                                <input type="number" class="input-large" id="txtPanelAngle" placeholder="Enter angle (0 - 90) in degrees" />
                            </div>
                        </div>
					</fieldset>
				</div>
			</div>
			<div class="span6">
                <div class="form-horizontal">
                    <fieldset>
                        <legend>Tariff Value</legend>
                        <div class="control-group" id="grpTariff">
                            <label class="control-label" for="ddlTariff">Tariff Value</label>
                            <div class="controls">
                                <select id="ddlTariff">
                                    <option value="-1">-- Select Tariff Value --</option>                                    
                                    <!-- <option value="0.08">8c (from July 10, 2012)</option>  -->
                                    <!-- <option value="0.44">44c (pre July 10, 2012)</option>  -->
                                </select>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
			<div class="span12">			    
				<div class="form-actions">
					<button class="btn btn-large btn-primary" id="btnCalculate">Calculate</button>
					<button class="btn btn-large" id="btnCancelCalculate">Cancel</button>									
				</div>				
			</div>			
			<div class="span12">
                <div class="alert alert-success" id="pnlResults" style="display: none;">
                    <span id="lblSavings"></span>
                </div>
                <div class="alert alert-error" id="pnlErrors" style="display: none;">
				    <span id="lblErrors">Please correct the fields highlighted in red.</span>
			    </div>
			</div>
			<div class="span12">
                <div id="chartdiv" style="height: 600px; margin-bottom: 20px; visibility: hidden;"></div>
            </div>
			<div class="span12">
                <div class="alert alert-info">
                     <p style="text-align: center; font-style: italic;">Please note that this calculator is based on values for Brisbane, Australia and currently only gives an approximation.</p>
                </div>
            </div>
		</div>
	</div>
	
	
	<!-- Address Modal -->
	<div id="addressModal" class="modal hide fade">
	    <div class="modal-header">
	       <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	       <h3>Property Search and Panel Orientation</h3>
	    </div>
	    <div class="modal-body">
            <input type="text" class="input-large" id="searchTextField" placeholder="Enter address" />                             
            <div id="map_canvas"></div>
            <div class="control-group" id="grpPanelOrientation">
	            <label class="control-label" for="ddlPanelOrientation">Panel Orientation</label>
	            <div class="controls">
	                <select id="ddlPanelOrientation">
	                    <option value="-1">-- Select Panel Orientation --</option>
	                    <option value="N">North</option>
	                    <option value="NE">Northeast</option>
	                    <option value="E">East</option>
	                    <option value="SE">Southeast</option>
	                    <option value="S">South</option>
	                    <option value="SW">Southwest</option>
	                    <option value="W">West</option>
	                    <option value="NW">Northwest</option>
	                </select>
	            </div>
	        </div>
	    </div>
	    <div class="modal-footer">
	       <a href="#" data-dismiss="modal" class="btn btn-large btn-primary">Close</a>
	    </div>
    </div>
	<!-- End Address Modal -->
	
	
</body>
</html>
