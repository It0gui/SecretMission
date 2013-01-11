<?php

/**
* type d'Erreurs:
*      -1 : Crash d'un compostant(exceptions..)
*	0 : Paramètres erronés
*	1 : User bani pour un interval defini
*	2 : User n'existe pas
*	3 : Password errone
*	4 : Token expiré ou faux
*	5 : Format Password erroné
**/

 include_once("SERVICE/functions_service.php");
 $result="";
 if(isset($_POST['type']))
   {
	if($_POST['type']=="login")
	{
		if (isset($_POST['login'])&&isset($_POST['pwd']))
		{
			$result=se_logger($_POST['login'],$_POST['pwd']);
		}
		else $result='{err:0}';
	}
	elseif($_POST['type']=='get_keyword')
	{
		if(isset($_POST['token'])&&isset($_POST['email']))
		{
			$result=get_keywords($_POST['token'],$_POST['email']);
		}
		else $result='{err:0}';
	}
	elseif($_POST['type']=='add_keyword')
	{
		if(isset($_POST['token'])&&isset($_POST['key_word'])&&isset($_POST['email']))
		{
			$result=add_keyword($_POST['token'],$_POST['email'],$_POST['key_word']);
		}
		else $result='{err:0}';
	}		
	elseif($_POST['type']=='remove_keyword')
	{
		if(isset($_POST['token'])&&isset($_POST['key_word'])&&isset($_POST['email']))
		{
			$result=remove_keyword($_POST['token'],$_POST['email'],$_POST['key_word']);
		}
		else $result='{err:0}';
	}
	elseif($_POST['type']=='new_account')
	{
		if(isset($_POST['email'])&&isset($_POST['pwd'])&&isset($_POST['id']))
		{
			$result=new_account($_POST['email'],$_POST['pwd'],$_POST['id']);
		}
		else $result='{err:0}';
	}
	elseif($_POST['type']=='alter_account')
	{
		if(isset($_POST['token'])&&isset($_POST['email'])&&isset($_POST['pwd']))
		{
			$result=alter_account($_POST['token'],$_POST['email'],$_POST['pwd']);
		}
		else $result='{err:0}';
	}
	elseif($_POST['type']=='check_account')
	{
	 	if(isset($_POST['token'])&&isset($_POST['email']))
		{
			$result=check_account($_POST['token'],$_POST['email']);
		}
		else $result='{err:0}';
	}
	elseif($_POST['type']=='set_IDGCM')
	{
		if(isset($_POST['token'])&&isset($_POST['IDGCM'])&&isset($_POST['email']))
		{
			$result=set_IDGCM($_POST['token'],$_POST['email'],$_POST['IDGCM']);
		}
		else $result='{err:0}';
	}		

  }
  else $result='{err:0}';
  echo $result;	
?>
