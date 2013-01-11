<?php
	include_once("tools.php");
	global $bd;	

	function se_logger($login,$pwd)
	{
		global $bd;	
		 if(have_the_right($login))
		 {
		  try 
			{
			 $statement = $bd->prepare("SELECT * FROM users WHERE login=:login");
				/* COntrer la faille SQLI */
			 $statement->bindParam('login',$login,PDO::PARAM_STR);
			 $statement->execute();
			 $row=$statement->fetchAll(PDO::FETCH_ASSOC);				
			 if(isset($row[0]))
			  {	
					/* Decryptage+desalage */
				if(decryptage_is_ok($row[0]['pwd'],$_POST['pwd']))
				 {
						/* Generation du token */
					$token=get_sel();
					$date=date("U");
					$statement = $bd->prepare('UPDATE users set date_token=now() ,token="'.$token.'" WHERE login=:login');
					$statement->bindParam('login',$_POST['login'], PDO::PARAM_STR);
					$statement->execute();
					$date+=(60*60*2+300);					
					$result='{token:"'.$token.'",Date_expiration:"'.date('H:i:s',$date).'"';
					remise_a_zero($_POST['login']);
					$result.=controleID($_POST['login']);
					$result.='}';
					return $result;
				 }
				else
				 {
					incrementer_compteur($_POST['login']);
					return '{err:3}';//pwd erronée
				 }
			   }
		   }
		  catch(PDOException $msg)
		   {
			 return '{err:-1}';//CRASH exterieure
			 die("Erreur : ".$msg->getMessage());		
		   }
		}
		else
		{
			 if (is_a_user($_POST['login']))
				{
	 				return '{err:1,interval:"'.interval($_POST['login']).'s"}';//user banned
				}
			else 
				{
					return '{err:2}';//user n'existe pas
				}
		}	
        }




	function get_keywords($token,$email)
	{
		$login=token_valid($token);
		if($login==1)
		{
			return shell_exec('./SERVER/fonction_mails -get_key_words '.$email);;
		}
		else	return '{err:4}';//token expiré ou inexistant!depasser 15 min	
	}



	function add_keyword($token,$email,$key_word)
	{
		$login=token_valid($token);
		if($login==1)
		{
			shell_exec('./SERVER/fonction_mails -add_key_word '.$email.' '.$key_word);;
			return '{etat:ok}';
		}
		else	return '{err:4}';//token expiré ou inexistant!depasser 15 min
	}


	function remove_keyword($token,$email,$key_word)
	{
		$login=token_valid($token);
		if($login==1)
		{
			shell_exec('./SERVER/fonction_mails -remove_key_word '.$email.' '.$key_word);;
			return '{etat:ok}';
		}
		else return '{err:4}';//token expiré ou inexistant!depasser 15 min
	}

	function new_account($email,$pwd,$IDGCM)
	{
	global $bd;	
		if(filtre_ok($email,$pwd))
		{
		  try 
				{
					$statement = $bd->prepare("INSERT INTO users(login,pwd,IDGCM) VALUES (:login,:pwd,:idgcm)");
					$statement->bindParam(':login',$email, PDO::PARAM_STR);
					$x=cryptage_salage($pwd);
					$statement->bindParam(':pwd',$x, PDO::PARAM_STR);
					$statement->bindParam(':idgcm',$IDGCM, PDO::PARAM_STR);
					$statement->execute();
					$l=shell_exec('./SERVER/fonction_mails -add_login '.$email.' '.$pwd);;
					return '{etat:'.$l.'}';
				}
			 catch(PDOException $msg)
				{
					return '{err:-1}';		
				}
		}
		else return '{err:5}';
	}
	function alter_account($token,$email,$pwd)
	{
		$login=token_valid($token);
		if($login==1)
		{
			shell_exec('./SERVER/fonction_mails -alter_login '.$email.' '.$pwd);;
			set_pwd($token,$pwd);
			return '{etat:ok}';
		}
		else return '{err:4}';//token expiré ou inexistant!depasser 15 min
	}

	function check_account($token,$email)
	{
		$login=token_valid($token);
		if($login==1)
		{
			return shell_exec('./SERVER/fonction_mails -test_authentification '.$email);;
		}
		else return '{err:4}';//token expiré ou inexistant!depasser 15 min
	}



?>
