<!DOCTYPE html>
<html id="home" lang="en">
<head>
<title>Solar Power Calculator</title>
<!-- This is a comment -->
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<style type="text/css"> body { padding-top: 60px; } </style>

<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="css/bootstrap-responsive.min.css" />
<link rel="stylesheet" type="text/css" href="css/skin.css" />

<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyBbIURPjdlsOMzJagYMlrMe7BDRAOiprN0&libraries=places&sensor=false"></script>
<script type="text/javascript" src="js/GoogleMaps.js"></script>
<script type="text/javascript" src="js/AjaxCalls.js"></script>
<script type="text/javascript" src="js/scripts.js"></script>

<!--[if lt IE 9]> <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script> <![endif]-->

</head>
<body>
	<div class="navbar navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				    <span class="icon-bar"></span>
				    <span class="icon-bar"></span>
				    <span class="icon-bar"></span>
				</a>
				<a class="brand" href="#">Solar Power Calculator</a>
				<div class="nav-collapse">
					<ul class="nav">
						<li class="active"><a href="#">Home</a></li>
						<li><a href="#about">About</a></li>
						<li><a href="#contact">Contact</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="span6">
				<div class="form-horizontal">
					<fieldset>
						<legend>Solar Panel & Inverter Specifications</legend>
						<div class="control-group" id="grpPanelSize">
							<label class="control-label" for="txtPanelSize">Total size of panels</label>
							<div class="controls">
								<input type="text" class="input-xlarge" id="txtPanelSize" placeholder="Enter size in square metres" />
							</div>
						</div>
						<div class="control-group" id="grpPanelEfficiency">
							<label class="control-label" for="txtPanelEfficiency">Panel efficiency</label>
							<div class="controls">
								<input type="text" class="input-xlarge" id="txtPanelEfficiency" placeholder="Enter efficiency in watts per square metre" />
							</div>
						</div>
						<div class="control-group" id="grpInverterEfficiency">
							<label class="control-label" for="txtInverterEfficiency">Inverter efficiency</label>
							<div class="controls">
								<input type="text" class="input-xlarge" id="txtInverterEfficiency" placeholder="Enter efficiency as %" />
							</div>
						</div>
						<div class="control-group" id="grpPanelOrientation">
							<label class="control-label" for="txtPanelOrientation">Panel Orientation</label>
							<div class="controls">
								<input type="text" class="input-xlarge" id="txtPanelOrientation" placeholder="Enter orientation (e.g. N, NE, SW etc.)" />
							</div>
						</div>
						<div class="control-group" id="grpPanelAngle">
							<label class="control-label" for="txtPanelAngle">Panel Angle</label>
							<div class="controls">
								<input type="text" class="input-xlarge" id="txtPanelAngle" placeholder="Enter angle" />
							</div>
						</div>
						<legend>Power Consumption</legend>
                        <div class="control-group" id="grpPowerConsumption">
                            <label class="control-label" for="txtPowerConsumption">Power Consumption</label>
                            <div class="controls">
                                <input type="text" class="input-xlarge" id="txtPowerConsumption" placeholder="Enter consumption in kilowatts" />
                            </div>
                        </div>
					</fieldset>
				</div>
			</div>
			<div class="span6">
				<div class="form-horizontal">
					<fieldset>
						<legend>Property Location</legend>
						<div class="control-group" id="grpAddress">
							<label class="control-label" for="searchTextField">Address</label>
							<div class="controls">
								<input type="text" class="input-xlarge" id="searchTextField" placeholder="Enter address" />
								<p id="lblCoordinates" class="help-block"></p>
								<div id="map_canvas"></div>
							</div>
						</div>
						<div class="control-group" id="grpDailySunlight">
							<label class="control-label" for="txtDailySunlight">Daily Sunlight</label>
							<div class="controls">
								<input type="text" class="input-xlarge" id="txtDailySunlight" placeholder="Enter sunlight in kilowatts" />
							</div>
						</div>						
					</fieldset>
				</div>
			</div>
			<div class="span12">
				<div class="form-actions">
					<button class="btn btn-large btn-primary" id="btnCalculate">Calculate</button>
					<button class="btn btn-large" id="btnReset">Cancel</button>					
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
		</div>
	</div>
</body>
</html>