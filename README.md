# JavaSecureLoginEncDec

An application made 100% in java, demonastrating among others,  knowledge about security mechanisms. (symmetric and asymmetric key encryption, integrity validation, etc) 


The application has a pair of public and private key.
Through the application , users can create an account. After creation, the application assigns to that user a specific space.
In that space, the user can upload files. Those files can be encrypted or decrypted using the user's key (which is encrypted by the public key of the app) that gets created upon registration.
An integrity check mechanisms is applied to those files every time they are modified.
If the actor is not the user that owns those files, the legimate user is notified upon his next login. 

---
Login

![Login](https://user-images.githubusercontent.com/51244823/159293008-c18ab7af-edef-48f2-b4ac-4456e858f600.png)
---
Register

![register](https://user-images.githubusercontent.com/51244823/159293073-a6b79147-dffc-490f-b384-7da313c87d16.png)
---
Encrypt

![encrypt](https://user-images.githubusercontent.com/51244823/159293110-b3ceb270-479d-49c7-bd19-5e2d1731d4f8.png)
