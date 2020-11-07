<?php

$host = "303.itpwebdev.com";
$user = "yunhaoz_db_user";
$password = "20023303*Zyh";
$db = "yunhaoz_201timeline_db";

$connect = new mysqli($host, $user, $password, $db);


$query = "SELECT * FROM timeline ORDER BY id ASC";

// $statement = $connect->prepare($query);
// $statement->execute();
$result = $connect->query($query);
if(!$result) {
    echo $connect->error;
    exit();
}

$connect->close();

?>

<html>  
<head>  
    <title>Timeline</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="icon" type="image/png" href="images/favicons/event.ico">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css" integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2" crossorigin="anonymous">
    
    <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>
    <script src="js/timeline.min.js"></script>
    <link rel="stylesheet" href="css/timeline.min.css" />
    <link rel="stylesheet" type="text/css" href="style.css">
    <style type="text/css">
        .container
        {
            height: 200px;
        }
        h2
        {
            font-size: 10px;
        }
        p
        {
            font-size: 10px;
        }
    </style>
	
</head>  
<body> 
    <nav class="navbar navbar-light fixed-top" style="background-color: #025f80;">
        <a class="navbar-brand d-flex justify-content-center" href="timeline.php" style="font-family: GrenzeGotisch; padding: 0; font-size:27px; color: #c3d6e7;">
            <img src="images/timeline.png" width="40" height="40" class="d-inline-block align-top" alt="" loading="lazy" style="margin-right: 15px;">
            ShowTime
        </a>
        <div class="dropdown">
            <img src="images/account.png" width="40" height="40" class="dropdown-toggle" type="button" id="dropdownMenu2" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenu2">
                <a href="#" class="dropdown-item" type="button">
                    <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-person-square" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd" d="M14 1H2a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/>
                        <path fill-rule="evenodd" d="M2 15v-1c0-1 1-4 6-4s6 3 6 4v1H2zm6-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
                    </svg>
                    My Account
                </a>
                <a href="#" class="dropdown-item" type="button">
                    <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-box-arrow-left" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd" d="M6 12.5a.5.5 0 0 0 .5.5h8a.5.5 0 0 0 .5-.5v-9a.5.5 0 0 0-.5-.5h-8a.5.5 0 0 0-.5.5v2a.5.5 0 0 1-1 0v-2A1.5 1.5 0 0 1 6.5 2h8A1.5 1.5 0 0 1 16 3.5v9a1.5 1.5 0 0 1-1.5 1.5h-8A1.5 1.5 0 0 1 5 12.5v-2a.5.5 0 0 1 1 0v2z"/>
                        <path fill-rule="evenodd" d="M.146 8.354a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L1.707 7.5H10.5a.5.5 0 0 1 0 1H1.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3z"/>
                    </svg>
                    Log Out
                </a>
            </div>
        </div>
    </nav>
    <div style="height: 55px;"></div>

    <div class="container">
            	<div class="timeline">
                    <div class="timeline__wrap">
                        <div class="timeline__items">
                        <?php
                        foreach($result as $row)
                        {
                        ?>
                        	<div class="timeline__item">
                                <div class="timeline__content">
                                	<h2><?php echo $row["year"]; ?></h2>
                                	<p><?php echo $row["comment"]; ?></p>
                                </div>
                            </div>
                        <?php
                        }
                        ?>
                        </div>
                    </div>
                </div>
	</div>
	
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ho+j7jyWK8fNQe+A12Hb8AhRq26LrZ/JpcUGGOn+Y7RsweNrtN/tE3MoK7ZeZDyx" crossorigin="anonymous"></script>
	<script>
    	$(document).ready(function(){
    	    jQuery('.timeline').timeline();
    	});
	</script>
</body>  
</html>

