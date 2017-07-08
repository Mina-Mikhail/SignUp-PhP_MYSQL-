# Sign Up and Sign In using PhP and MySQL
This is a simple application to sign up and sign in using PhP and MySQL database, You can use any server you prefer to upload your php files and create your own database.
* Demo:

<img src="https://user-images.githubusercontent.com/17712116/27987860-34ba220a-6415-11e7-9293-886ad324b88e.png" width="33%"></img> 
<img src="https://user-images.githubusercontent.com/17712116/27987863-41a894d8-6415-11e7-9fbc-f9ade2977aa2.png" width="33%"></img> 
<img src="https://user-images.githubusercontent.com/17712116/27987823-8ee93b04-6414-11e7-86d2-2e9cfe6eac73.png" width="33%"></img> 

## Features:
* The application ui is fully responsive it based on ConstraintLayout.
* The application works good on low api (min. minSdkVersion 15).
* The application uses Shared Preferences to save the user data.
* The application contains Session Manager, any time you close the application and open it at any time you goes directly to profile activity without log in again.

## Usage:

1. You have to create account on any hosting websites (like, www.000webhost.com).
2. Create the database:

<img src="https://user-images.githubusercontent.com/17712116/27988008-2bd827ba-6418-11e7-954d-5e7efbb541ff.png"></img> 

3. Create the following PhP files and upload it to your server:

dbconfig.php
```php
<?php
  //Define your host here.
  $servername = "localhost";
  //Define your database username here.
  $username = "database_UserName";
  //Define your database password here.
  $password = "database_Password";
  //Define your database name here.
  $dbname = "database_Name";
?>
```

insert-registration-data.php
```php
<?php
  include 'dbconfig.php';
  $con = mysqli_connect($servername,$username,$password,$dbname);

  $name = $_POST['name'];
  $email = $_POST['email'];
  $password = $_POST['password'];

  $CheckSQL = "SELECT mail FROM user_data WHERE mail='$email'";
  $check = mysqli_fetch_array(mysqli_query($con,$CheckSQL));

  if(isset($check)) {
    echo 'Email Already Exist';
  }
  else { 
    $Sql_Query = "insert into user_data (name,mail,password) values ('$name','$email','$password')";
    if(mysqli_query($con,$Sql_Query)) {
      echo 'Data Inserted Successfully';
    }
    else {
      echo 'Try Again';
    }
  }
  mysqli_close($con);
?>
```

android-login.php
```php
<?php
  if($_SERVER['REQUEST_METHOD']=='POST') {
    include 'dbconfig.php';
    $con = mysqli_connect($servername,$username,$password,$dbname);

    $email = $_POST['email'];
    $password = $_POST['password'];

    $Sql_Query = "select mail,password from user_data where mail = '$email' and password = '$password' ";
    $check = mysqli_fetch_array(mysqli_query($con,$Sql_Query));

    if(isset($check)) {
      echo "Data Matched";
    }
    else {
      echo "Invalid Username or Password Please Try Again";
    }
  }
  else {
    echo "Check Again";
  }
  mysqli_close($con);
?>
```

4. After uploading `insert-registration-data.php` and `android-login.php` to the server, copy the links of the files and paste it into `RegistrationActivity` and `UserLoginActivity` respectively to connect your android application with the database.

## Libraries:
* Material Design.
* Material Text Field.
* Volley.

==================

developed to make programming easy.

by Mina Mikhail (mano_samir@outlook.com) (https://www.linkedin.com/in/minasamirgerges)
