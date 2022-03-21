# JavaSecureLoginEncDec

An application made 100% in java, demonastrating among others,  knowledge about security mechanisms. (symmetric and asymmetric key encryption, integrity validation, etc) 


The application has a pair of public and private key.
Through the application , users can create an account. After creation, the application assigns to that user a specific space.
In that space, the user can upload files. Those files can be encrypted or decrypted using the user's key (which is encrypted by the public key of the app) that gets created upon registration.
An integrity check mechanisms is applied to those files every time they are modified.
If the actor is not the user that owns those files, the legimate user is notified upon his next login. 
