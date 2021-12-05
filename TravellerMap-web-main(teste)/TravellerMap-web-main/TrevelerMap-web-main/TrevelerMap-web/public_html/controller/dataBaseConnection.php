<?php
    $mysql_server="localhost";
    $mysql_user="";
    $mysql_passworld="";
    $mysql_database="";

    $connection = mysqli_connect(
        $mysql_server, 
        $mysql_user, 
        $mysql_passworld, 
        $mysql_database
        ) or die (
            "Erro ao conectar no banco de dados"
        );

        if($connection){
            echo("<script>alert('conexão bem sucedida!');</script>");
        }

        //falta colocar os parametros nas variaveis para que essa classe funcione

?>