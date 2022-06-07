<!-- Inicio cabecalho-->
<div class="jumbotron"><h1>BookStoreWeb</h1></div>



<center>
    <p>
        <a href="<%=request.getContextPath()%>/logout" class="btn btn-primary">
            <span class="glyphicon glyphicon-off"></span> Logout</a>
        <a href="<%=request.getContextPath()%>/bsuser/initial" class="btn btn-primary">
            <span class="glyphicon glyphicon-th-list"></span> Lista de usuarios</a>
    </p>

    <p>
        <a href="<%=request.getContextPath()%>/bstore/new" class="btn btn-default">
            <span class="glyphicon glyphicon-plus"></span> Adicionar novo Livro</a>

        <a href="<%=request.getContextPath()%>/bstore/list" class="btn btn-default">
            <span class="glyphicon glyphicon-th-list"></span>
            Lista todos Livros</a>
    </p>
</center>

<!-- Fim cabecalho-->
