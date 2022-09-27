<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<script  src="${pageContext.request.contextPath}/dist/js/custom/jquery.min.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<select class="selectRound" name="criteriaRound[]" onclick="save()">
		<option selected="true" disabled="true" value="">- select -</option>
		<option value="16">value1</option>
		<option value="17">value2</option>
		<option value="18">value3</option>
		<option value="19">value4</option>
	</select>

	<select class="selectRound" name="criteriaRound[]" onselect="save()">
		<option selected="true" disabled="true">- select -</option>
		<option value="16">value1</option>
		<option value="17">value2</option>
		<option value="18">value3</option>
		<option value="19">value4</option>
	</select>

	<select class="selectRound" name="criteriaRound[] " onselect="save()">
		<option selected="true" disabled="true">- select -</option>
		<option value="16">value1</option>
		<option value="17">value2</option>
		<option value="18">value3</option>
		<option value="19">value4</option>
	</select>

	<select class="selectRound" name="criteriaRound[]" onselect="save()">
		<option selected="true" disabled="true">- select -</option>
		<option value="16">value1</option>
		<option value="17">value2</option>
		<option value="18">value3</option>
		<option value="19">value4</option>
	</select>

	<script type="text/javascript">
	$(document).on('change', '.selectRound', function(e) {
		  var tralse = true;
		  var selectRound_arr = []; // for contestant name
		  $('.selectRound').each(function(k, v) {
		    var getVal = $(v).val();
		    //alert(getVal);
		    if (getVal && $.trim(selectRound_arr.indexOf(getVal)) != -1) {
		      tralse = false;
		      //it should be if value 1 = value 1 then alert, and not those if -select- = -select-. how to avoid those -select-
		      alert('Contestant cannot be same name');
		      $(v).val("");
		      return false;
		    } else {
		      selectRound_arr.push($(v).val());
		    }

		  });
		  if (!tralse) {
		    return false;
		  }
		});


	</script>
</body>
</html>