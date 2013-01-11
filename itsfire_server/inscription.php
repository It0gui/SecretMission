
<!doctype html>
<html lang="fr">

<head>
 	<title>Page d'inscription</title>
    	<link rel="stylesheet" href="CSS/css.css" type="text/css">
</head>

<body>

<!-- Header -->
<header>	
	<center><h1>INSCRIPTION AU SERVICE</h1></center>
</header>

<div id="wrapper">
<center>
	<!-- Main -->
	<section id="main">
		<article>
		 <center>

<?php
	include_once("TOOLS/tools.php");
	global $bd;
	if(isset($_POST['email'])&&isset($_POST['pwd']))
	{		
		if(filtre_ok($_POST['email'],$_POST['pwd']))
		{	
			if(is_a_user($_POST['email']))
			 {
			  	echo "L'email existe déja!";
			 }
			 else
			 {
			
			   try 
				{
					$statement = $bd->prepare("INSERT INTO users(login,pwd) VALUES (:login,:pwd)");
					$statement->bindParam(':login',$_POST['email'], PDO::PARAM_STR);
					$x=cryptage_salage($_POST['pwd']);
					$statement->bindParam(':pwd',$x, PDO::PARAM_STR);
					$statement->execute();
					// Traiter les injection ici!
					shell_exec('./SERVER/fonction_mails -add_login '.$_POST['email'].' '.$_POST['pwd']);;
					echo "Email ajouté au service!";
				}
			 catch(PDOException $msg)
				{
					die("Erreur : ".$msg->getMessage());		
				}
				
	
			 }
		}
		else
		{
			 echo '<form method="post">';
				echo 'Mot de passe contient des caractères intérdits!';
			 	echo '<input type="submit" value="Go back!">';
			 echo '</form>';
		}
	}
	else
	{
		 echo '<form method="post">';
		 echo 'Email : <input type="email" name="email" /></br>';
		 echo 'Password : <input type="password" name="pwd"/></br>';
		 echo '<input type="submit" value="OK">';
		 echo '</form>';
	}
?>
		 </center>
		</article>	
	</section>
</center>
</div>
	
<!-- footer -->
<footer>
	<CENTER>
		Projet IF26 (2012/2O13)
	</center>
</footer>
	
</body>
</html>
