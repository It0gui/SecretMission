This part will concern the communication in between the server and the application running on our smartphone. We�ll use for it Google�s Cloud Messaging Service. 
We�ll also need to make sure the application can send its Registration ID to the server taking into account that most probably it will not be possible to be done via GCM and we�ll need to find our own way to do it.
A important thing to mention is that this special kind of message should be sent secure way.

Most probably we�ll use http client for this purpose.
