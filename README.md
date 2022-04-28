# JwtSpringSecurity

Mise en oeuvre de la sécurisation d'une application avec une JWT.

## Usage
### POST - S'authentifier

* **List des utilisateurs** <br>

| username | password |
| -------- | -------- |
|   toto   |   123    |      
|   tata   |   123    |     
|   tutu   |   456    |     

![image](https://user-images.githubusercontent.com/44904128/165738920-041aa80b-7fbb-43ca-9479-9852ace933e6.png)

### GET - S'assurer que le JWT token fournie par l'utilisateur est le bon.

```diff
- ⚠️ Ne pas oublier de remplacer le token après le namespace Bearer.
```
![image](https://user-images.githubusercontent.com/44904128/165741141-f24ec77a-987e-411c-8141-6c113318d9e4.png)
