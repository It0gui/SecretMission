<?php
//------------>Fonction BD    
	function & connexion_bd()
	{
		$user='root';
		$pwd='toor';
		$host="localhost";
		$dbname="IF26";
		$DataSourceName="mysql:host=".$host.";dbname=".$dbname;
		try 
		{
			$base=new PDO ($DataSourceName,$user,$pwd);
			$base->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);		
			return $base;
		}
		catch(PDOException $msg)
		{
			die("Erreur : ".$msg->getMessage());		
		}
	}
	function deconnexion_bd()
	{
			global $bd;
			$bd=NULL;
			if(isset($_SESSION['logged']))
				unset($_SESSION['logged']);
	}
//-----------> Fonction Bannissement + validation de token
	function remise_a_zero($user_login)
	{
		  global $bd;
		    try 
			 {
						$statement = $bd->prepare("UPDATE users set coef=0 WHERE login=:login");
						$statement->bindParam('login',$user_login, PDO::PARAM_STR);
						$statement->execute();
			 }
		    catch(PDOException $msg)
			{
				die("Erreur : ".$msg->getMessage());		
			}
	}
	function token_valid($token)
	{
		global $bd;
		try 
		 {
			$statement = $bd->prepare("SELECT * FROM users WHERE token=:token");
			$statement->bindParam('token',$token, PDO::PARAM_STR);
			$statement->execute();
			$row=$statement->fetchAll(PDO::FETCH_ASSOC);
			if(isset($row[0]))
			 {
			    return 1;
			 }
			 else return 0;
		}
		catch(PDOException $msg)
			{
				die("Erreur : ".$msg->getMessage());		
			}
	}
	function incrementer_compteur($user_login)
	{
		global $bd;
		try 
		 {
		 
		 	$statement = $bd->prepare("SELECT * FROM users WHERE login=:login");
			$statement->bindParam('login',$user_login, PDO::PARAM_STR);
			$statement->execute();
			$row=$statement->fetchAll(PDO::FETCH_ASSOC);
			if(isset($row[0]))
			 {
			  if($row[0]['coef']==0)
			  {
						$statement = $bd->prepare("UPDATE users set coef=1 WHERE login=:login");
						$statement->bindParam('login',$user_login, PDO::PARAM_STR);
						$statement->execute();

						$statement = $bd->prepare("UPDATE users set date_debut=NOW() WHERE login=:login");
						$statement->bindParam('login',$user_login, PDO::PARAM_STR);
						$statement->execute();
			  }
			  else
			  {
						$statement = $bd->prepare("UPDATE users set coef=coef+1 WHERE login=:login");
						$statement->bindParam('login',$user_login, PDO::PARAM_STR);
						$statement->execute();
			  }
			 }
		 }
		catch(PDOException $msg)
			{
				die("Erreur : ".$msg->getMessage());		
			}
	}
	function have_the_right($user_login)
	{
	//coef =1 -> blocage 0 min
	//coef =2 -> blogace 5 min
	//coef =3 -> blocage 10min..
	//blocage = coef*5min
		global $bd;
		try 
		 {
			$statement = $bd->prepare("SELECT * FROM users WHERE login=:login");
			$statement->bindParam('login',$user_login, PDO::PARAM_STR);
			$statement->execute();
			$row=$statement->fetchAll(PDO::FETCH_ASSOC);
			if(isset($row[0]))
			 {
			  $coef=$row[0]['coef'];
			  $date_debut=$row[0]['date_debut'];
			  $date_debut=date("U",strtotime($date_debut))-60*60*2;
			  $date_now=date("U");
			  $date_diff=$date_now-$date_debut;
			  return ($date_diff>=$coef*60*5) ;
			 }
		}
		catch(PDOException $msg)
			{
				die("Erreur : ".$msg->getMessage());		
			}
	}
	
	function interval($user_login)
	{
		global $bd;
		try 
		 {
			$statement = $bd->prepare("SELECT * FROM users WHERE login=:login");
			$statement->bindParam('login',$user_login, PDO::PARAM_STR);
			$statement->execute();
			$row=$statement->fetchAll(PDO::FETCH_ASSOC);
			if(isset($row[0]))
			 {
			  $coef=$row[0]['coef'];
			  $date_debut=$row[0]['date_debut'];
			  $date_debut=date("U",strtotime($date_debut))-60*60*2;
			  $date_now=date("U");
			  $date_diff=$date_now-$date_debut;
			  return ($coef*60*5-$date_diff) ;
			 }
			 else return 0;
		}
		catch(PDOException $msg)
			{
				die("Erreur : ".$msg->getMessage());		
			}
	}
	
	
function set_pwd($token,$pwd)
	{
		global $bd;
		try
		 {	
			$result=cryptage_salage($pwd);
		 	$statement = $bd->prepare('UPDATE users set pwd="'.$result.'" WHERE token=:token');
			$statement->bindParam('token',$token, PDO::PARAM_STR);
			$statement->execute();
		 }
		catch(PDOException $msg)
			{
				die("Erreur : ".$msg->getMessage());		
			}
	}
//---------------->Fonction IDGCM
	function controleID($user_login)
	{
		global $bd;
		try 
		 {
		 
		 	$statement = $bd->prepare("SELECT * FROM users WHERE login=:login");
			$statement->bindParam('login',$user_login, PDO::PARAM_STR);
			$statement->execute();
			$row=$statement->fetchAll(PDO::FETCH_ASSOC);
			$s="";
			if(isset($row[0]))
			 {
			  if($row[0]['IDGCM']==null)
			  {
				$s=',ID="required"';
			  }
			 }
			return $s;
		 }
		catch(PDOException $msg)
			{
				die("Erreur : ".$msg->getMessage());		
			}
	}
	function get_regid($user_login)
	{
		global $bd;
		try 
		 {
			$statement = $bd->prepare("SELECT * FROM users WHERE login=:login");
			$statement->bindParam('login',$user_login, PDO::PARAM_STR);
			$statement->execute();
			$row=$statement->fetchAll(PDO::FETCH_ASSOC);
			if(isset($row[0]))
				 {
				  if($row[0]['IDGCM']==null)
				  {
					return "err1";
				  }
					return $row[0]['IDGCM'];
				 }
			else return "err2";
			
		 }
		catch(PDOException $msg)
		{
				die("Erreur : ".$msg->getMessage());		
		}
	}
	function set_IDGCM($token,$IDGCM)
	{
		global $bd;
		try
		 {	
		 	$statement = $bd->prepare('UPDATE users set IDGCM="'.$IDGCM.'" WHERE token=:token');
			$statement->bindParam('token',$token, PDO::PARAM_STR);
			$statement->execute();
		 }
		catch(PDOException $msg)
			{
				die("Erreur : ".$msg->getMessage());		
			}
	}
	

	
//------------>Fonction cryptage+salage	
	
	function cryptage_salage ($pwd)
	{
	 $sel=get_sel();
	 return md5($pwd.$sel).$sel;
	}
	function decryptage_is_ok ($chaine,$pwd)
	{
		$sel=strrev($chaine);
		$sel=substr($sel,0,25);
		$sel=strrev($sel);
		return ($chaine==md5($pwd.$sel).$sel) ;
	}
	function get_sel()//Sel lenght = 25 char
	{
	 $graine=uniqid(rand(), true);
	 if(strlen($graine)<=25)
		return str_pad($graine, 25 , "XX");
	else
	    return substr ($graine,0,25);
	}   
    //echo decryptage_is_ok('191bb6383a9a3243d38949c89dae35b329781506f72a73ba840.94634','pa$$word');

//------------>Fonction Divers 
	
	function is_a_user($user_login)
	{
		global $bd;
		try 
		 {
			$statement = $bd->prepare("SELECT * FROM users WHERE login=:login");
			$statement->bindParam('login',$user_login, PDO::PARAM_STR);
			$statement->execute();
			$row=$statement->fetchAll(PDO::FETCH_ASSOC);
			return isset($row[0]);
		}
		catch(PDOException $msg)
			{
				die("Erreur : ".$msg->getMessage());		
			}
	}


	function filtre_ok($email,$pwd)
	{
		if(filter_var($email, FILTER_VALIDATE_EMAIL))
		{
			if(strripos($pwd,' ')||strripos($pwd,'|')) return false;
			else return true;
		}
		else return false;
	}

//------------->Reinitialistation
	session_start();
	$bd=connexion_bd();
?>
