This part will concern the communication in between the server and the application running on our smartphone. We値l use for it Google痴 Cloud Messaging Service. 
We値l also need to make sure the application can send its Registration ID to the server taking into account that most probably it will not be possible to be done via GCM and we値l need to find our own way to do it.
A important thing to mention is that this special kind of message should be sent secure way.

Most probably we値l use http client for this purpose.
