<?php
include_once("SERVICE/tools.php");
define("GOOGLE_API_KEY", "AIzaSyBIf1mwajJhFLhfgqoYXsrVw3LRlk6MOgw");
class GCM 
{
 
    function __construct() 
	{
 	}
 
    /**
     * Sending Push Notification
     */
    public function send_notification($registatoin_ids, $message) 
	{
		$url = 'https://android.googleapis.com/gcm/send';
		$fields = array('registration_ids' => $registatoin_ids,'data' => $message);
		$headers = array('Authorization: key=' . GOOGLE_API_KEY,'Content-Type: application/json');
		$ch = curl_init(); 
		curl_setopt($ch, CURLOPT_URL, $url);
		curl_setopt($ch, CURLOPT_POST, true);
		curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
		curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
		curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
		$result = curl_exec($ch);
		if ($result === FALSE) 
		{
		    die('Curl failed: ' . curl_error($ch));
		}
		curl_close($ch);
		echo $result;
	}
 
}


   $gcm = new GCM();
   $gcm_regid = "APA91bELPeJTjIsTQXcg99M2LXVQA1u81yMWgnZQASiOZ45Q-CW9WNcFHtJ6rUqCLfeJqIEMidZdNJpDJgMdyR0axTstAEkZhIvrNdQMEVd6jeG3rvrhy9Z67k1akZPCt4fuhyIga-onGZD-gexe_r3MYsqO1Hd_Bw";
   $gcm_regid = get_regid($argv[1]);
   $registatoin_ids = array($gcm_regid);
   $message=$argv[2];
   $message = array("data" => $message);
   $result = $gcm->send_notification($registatoin_ids, $message);
  
?>
