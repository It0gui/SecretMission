<?php
/*
Utilisation:
 
 http://localhost/PROJET_IF26/service.php?type=login&login=test.if26@gmail.com&pwd=pa$$word => token+date expiration

 
						switch(erreurs)
							 		{
							 		case 1:
							 			"vous etes banni (durée :interval)"
							 			
							 		case 2:
							 			"cet utilisateur n'existe pas.."
							 			
							 		case 3:
							 			"Password erronée"
							 			
									case 4:
										"token expiré.."
																			
								    default:
								    	"Erreur d'entrées.."	
							 		}
*/
include_once("tools.php");
global $bd;
if(isset($_GET['type']))
   {
	if($_GET['type']=="login")
	{
	 if(isset($_GET['login'])&&isset($_GET['pwd']))
	  {
	   if(have_the_right($_GET['login']))
		 {
		  try 
			{
			 $statement = $bd->prepare("SELECT * FROM users WHERE login=:login");
/* COntrer la faille SQLI */$statement->bindParam('login',$_GET['login'], PDO::PARAM_STR);
			 $statement->execute();
			 $row=$statement->fetchAll(PDO::FETCH_ASSOC);				
			 if(isset($row[0]))
			  {	
/* Decryptage+desalage */
				if(decryptage_is_ok($row[0]['pwd'],$_GET['pwd']))
				 {
								//Generation du token
					$token=get_sel();
					$date=date("U");
					$statement = $bd->prepare('UPDATE users set date_token=now() ,token="'.$token.'" WHERE login=:login');
					$statement->bindParam('login',$_GET['login'], PDO::PARAM_STR);
					$statement->execute();
					$date+=(60*60*2+300);
					
					$result='{token:"'.$token.'",Date_expiration:"'.date('H:i:s',$date).'"';
					remise_a_zero($_GET['login']);
					$result.=controleID($_GET['login']);
					$result.='}';
				 }
				else
				 {
					$result='{err:3}';//pwd erronée
					incrementer_compteur($_GET['login']);
				 }
			   }
		   }
		  catch(PDOException $msg)
		   {
			 die("Erreur : ".$msg->getMessage());		
		   }
		}
		else
		{
			 if (is_a_user($_GET['login']))
				{
	 				$result='{err:1,interval:"'.interval($_GET['login']).'s"}';//user banned
				}
			else 
				{
					$result='{err:2}';//user n'existe pas
				}
		}	
       }
     		else
			{
				$result='{err:0}';//Erreur d'entrées
			}
	}
	elseif($_GET['type']=='get_keyword')
	{
		//@param token
		//return la liste des keyword	
		if(isset($_GET['token']))
			{
				$login=token_valid($_GET['token']);
				if($login==1)
				{
					$result=shell_exec('./fonction_mails -get_key_words');;
				}
				else
					$result='{err:4}';//token expiré ou inexistant!depasser 15 min
			}
			else
			 $result='{err:0}';//Erreur d'entrées	
	}
	elseif($_GET['type']=='add_keyword')
	{
		//@param arg,token

		//ajouter keyword

		if(isset($_GET['token'])&&isset($_GET['key_word']))
			{
				$login=token_valid($_GET['token']);
				if($login==1)
				{
					$result=shell_exec('./fonction_mails -add_key_word '.$_GET["key_word"]);;
				}
				else
					$result='{err:4}';//token expiré ou inexistant!depasser 15 min
			}
			else
			 $result='{err:0}';//Erreur d'entrées	
	}
	elseif($_GET['type']=='remove_keyword')
	{
		//@param arg,token
		//rm keyword
		if(isset($_GET['token'])&&isset($_GET['key_word']))
			{
				$login=token_valid($_GET['token']);
				if($login==1)
				{
					$result=shell_exec('./fonction_mails -remove_key_word '.$_GET["key_word"]);;
				}
				else
					$result='{err:4}';//token expiré ou inexistant!depasser 15 min
			}
		else
			 $result='{err:0}';//Erreur d'entrées	
		
	}
	elseif($_GET['type']=='set_account')
	{
		//@param email,token,pwd
		// return si il est ok !
		if(isset($_GET['token'])&&isset($_GET['email'])&&isset($_GET['pwd']))
			{
				$login=token_valid($_GET['token']);
				if($login==1)
				{
					$result=shell_exec('./fonction_mails -set_login '.$_GET["email"].' '.$_GET["pwd"]);;
					$result='{}';
				}
				else
					$result='{err:4}';//token expiré ou inexistant!depasser 15 min
			}
		else
			 $result='{err:0}';//Erreur d'entrées	
		
	}
	elseif($_GET['type']=='check_account')
	{
		//@param email,token,pwd
		// return si il est ok !
		if(isset($_GET['token']))
			{
				$login=token_valid($_GET['token']);
				if($login==1)
				{
					$result=shell_exec('./fonction_mails -test_authentification');;
				}
				else
					$result='{err:4}';//token expiré ou inexistant!depasser 15 min
			}
		else
			 $result='{err:0}';//Erreur d'entrées	
		
	}
	elseif($_GET['type']=='set_IDGCM')
	{
		//@param token,IDGCM
		// return si il est ok !
		if(isset($_GET['token']))
			{
				$login=token_valid($_GET['token']);
				if($login==1)
				{
					set_IDGCM($_GET['token'],$_GET['IDGCM']);
				}
				else
					$result='{err:4}';//token expiré ou inexistant!depasser 15 min
			}
		else
			 $result='{err:0}';//Erreur d'entrées	
		
	}
	else
		{
			$result='{err:0}';//Erreur d'entrées
		}
	}
	else
		{
			$result='{err:0}';//Erreur d'entrées
		}
	echo $result;
?>
