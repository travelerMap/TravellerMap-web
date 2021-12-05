<?php

    include'dataBaseConnection.php';

    $nome = $_GET['nome'];
    $email = $_GET['email'];
    $senha = $_GET['senha'];

    $query="insert into cadastro values('','$nome','$email','senha')";
    mysqli_query($connection, $query) or die("ERRO: falha na inserção dos dados no banco de dados");

    if($query){echo("<script>alert('dados cadastrados com sucesso!!');</script>");}

    mysqli_close($connection);

?>    