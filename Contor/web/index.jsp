<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Database test</title>
  <link rel="stylesheet" href="css/bootstrap.min.css">
  <link href="http://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <!--Import materialize.css-->
  <link type="text/css" rel="stylesheet" href="css/materialize.css" media="screen,projection"/>
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <link type="text/css" rel="stylesheet" href="css/styles.css"/>
</head>
<body>
<div class="centerDiv">

</div>
<ul id="slide-out" class="side-nav">
  <li>
    <div class="userView">
      <img class="background" src="img/material.jpg">
      <a href="#!user"><img class="circle" src="img/circle.png"></a>
      <a href="#!name"><span class="white-text name">John Doe</span></a>
      <a href="#!email"><span class="white-text email">jdandturk@gmail.com</span></a>
    </div>
  </li>
  <li><a href="#!" class="addBtn"><i class="material-icons addBtn">add</i>Add Entry</a></li>
  <li><a href="#!" class="removeBtn"><i class="material-icons removeBtn">delete</i>Delete Entry</a></li>
  <li><a href="#!" class="printBtn"><i class="material-icons printBtn">print</i>Print Database</a></li>
  <li>
    <div class="divider"></div>
  </li>
  <li><a class="subheader">Subheader</a></li>
  <li><a class="waves-effect" href="#!">Third Link With Waves</a></li>
</ul>
<a href="#" data-activates="slide-out" class="button-collapse menuBtn"><i class="medium material-icons menuBtn">menu</i></a>
</body>
<script src="https://code.jquery.com/jquery-3.1.0.min.js"
        integrity="sha256-cCueBR6CsyA4/9szpPfrX3s49M9vUU5BgtiJj06wt/s="
        crossorigin="anonymous"></script>
<script src="js/input.js"></script>
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/materialize.min.js"></script>
<script>
  $(document).ready(function () {
    $('.button-collapse').sideNav({
      closeOnClick: true
    });
  });
</script>
</html>