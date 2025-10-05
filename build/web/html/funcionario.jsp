<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">
  <title>Área do Funcionário - Don Maia</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/funcionario.css">
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/imagens/favicon.ico" type="image/x-icon">
</head>
<body>
  <header>
    <h1>Painel de Funcionários</h1> 
    <nav><a href="../index.html">Voltar</a></nav>
  </header>

  <main class="container">
    <h2>Gerenciar Veículos</h2>

    <!-- Mensagens de Sucesso/Erro -->
    <c:if test="${not empty sucesso}">
      <div class="alert alert-success">${sucesso}</div>
    </c:if>
    <c:if test="${not empty erro}">
      <div class="alert alert-danger">${erro}</div>
    </c:if>

    <!-- Formulário de Cadastro -->
    <div class="card mb-4">
      <div class="card-header">
        <h4>${empty automovel ? 'Cadastrar Novo Veículo' : 'Editar Veículo'}</h4>
      </div>
      <div class="card-body">
        <form action="automoveis?action=${empty automovel ? 'cadastrar' : 'atualizar'}" method="post">
          <c:if test="${not empty automovel}">
            <input type="hidden" name="id" value="${automovel.id}">
          </c:if>
          
          <div class="row">
            <div class="col-md-6">
              <div class="form-group">
                <label for="marca">Marca *</label>
                <input type="text" id="marca" name="marca" class="form-control" 
                       value="${automovel.marca}" required placeholder="Ex: Toyota, Honda">
              </div>
              
              <div class="form-group">
                <label for="ano">Ano *</label>
                <input type="number" id="ano" name="ano" class="form-control" 
                       value="${automovel.ano}" required min="1900" max="2030">
              </div>
              
              <div class="form-group">
                <label for="motor">Motor</label>
                <input type="text" id="motor" name="motor" class="form-control" 
                       value="${automovel.motor}" placeholder="Ex: 2.0 Turbo">
              </div>
              
              <div class="form-group">
                <label for="cavalos">Cavalos</label>
                <input type="number" id="cavalos" name="cavalos" class="form-control" 
                       value="${automovel.cavalos}" min="0">
              </div>
              
              <div class="form-group">
                <label for="preco">Preço (R$) *</label>
                <input type="number" id="preco" name="preco" class="form-control" 
                       value="${automovel.preco}" step="0.01" min="0" required>
              </div>
            </div>
            
            <div class="col-md-6">
              <div class="form-group">
                <label for="combustivel">Combustível</label>
                <select id="combustivel" name="combustivel" class="form-control">
                  <option value="Gasolina" ${automovel.combustivel == 'Gasolina' ? 'selected' : ''}>Gasolina</option>
                  <option value="Álcool" ${automovel.combustivel == 'Álcool' ? 'selected' : ''}>Álcool</option>
                  <option value="Flex" ${automovel.combustivel == 'Flex' ? 'selected' : ''}>Flex</option>
                  <option value="Diesel" ${automovel.combustivel == 'Diesel' ? 'selected' : ''}>Diesel</option>
                  <option value="Elétrico" ${automovel.combustivel == 'Elétrico' ? 'selected' : ''}>Elétrico</option>
                  <option value="Híbrido" ${automovel.combustivel == 'Híbrido' ? 'selected' : ''}>Híbrido</option>
                </select>
              </div>
              
              <div class="form-group">
                <label for="cambio">Câmbio</label>
                <select id="cambio" name="cambio" class="form-control">
                  <option value="Manual" ${automovel.cambio == 'Manual' ? 'selected' : ''}>Manual</option>
                  <option value="Automático" ${automovel.cambio == 'Automático' ? 'selected' : ''}>Automático</option>
                  <option value="CVT" ${automovel.cambio == 'CVT' ? 'selected' : ''}>CVT</option>
                </select>
              </div>
              
              <div class="form-group">
                <label for="tracao">Tração</label>
                <select id="tracao" name="tracao" class="form-control">
                  <option value="Dianteira" ${automovel.tracao == 'Dianteira' ? 'selected' : ''}>Dianteira</option>
                  <option value="Traseira" ${automovel.tracao == 'Traseira' ? 'selected' : ''}>Traseira</option>
                  <option value="Integral" ${automovel.tracao == 'Integral' ? 'selected' : ''}>Integral</option>
                </select>
              </div>
              
              <div class="form-group">
                <label for="cor_externa">Cor Externa</label>
                <input type="text" id="cor_externa" name="cor_externa" class="form-control" 
                       value="${automovel.cor}" placeholder="Ex: Prata, Preto">
              </div>
              
              <div class="form-group">
                <label for="cor_interna">Cor Interna</label>
                <input type="text" id="cor_interna" name="cor_interna" class="form-control" 
                       value="${automovel.corInterna}" placeholder="Ex: Preta, Bege">
              </div>
              
              <div class="form-group">
                <label for="categoria">Categoria</label>
                <select id="categoria" name="categoria" class="form-control">
                  <option value="SUV" ${automovel.categoria == 'SUV' ? 'selected' : ''}>SUV</option>
                  <option value="Sport" ${automovel.categoria == 'Sport' ? 'selected' : ''}>Sport</option>
                  <option value="Sedan" ${automovel.categoria == 'Sedan' ? 'selected' : ''}>Sedan</option>
                  <option value="Hatch" ${automovel.categoria == 'Hatch' ? 'selected' : ''}>Hatch</option>
                </select>
              </div>
            </div>
          </div>
          
          <button type="submit" class="btn btn-success">
            ${empty automovel ? 'Cadastrar Veículo' : 'Atualizar Veículo'}
          </button>
          <c:if test="${not empty automovel}">
            <a href="automoveis?action=listar" class="btn btn-secondary">Cancelar</a>
          </c:if>
        </form>
      </div>
    </div>

    <!-- Consultas Rápidas - AGORA AQUI EM CIMA DA LISTA -->
    <div class="card mb-4">
      <div class="card-header">
        <h4>Consultas Rápidas</h4>
      </div>
      <div class="card-body">
        <div class="row">
          <div class="col-md-4">
            <form action="automoveis" method="get" class="mb-3">
              <input type="hidden" name="action" value="buscarId">
              <div class="form-group">
                <label for="searchId" class="form-label"><strong>Buscar por ID</strong></label>
                <input type="number" id="searchId" name="id" class="form-control" placeholder="Digite o ID" required>
              </div>
              <button type="submit" class="btn btn-primary w-100">Buscar ID</button>
            </form>
          </div>
          
          <div class="col-md-4">
            <form action="automoveis" method="get" class="mb-3">
              <input type="hidden" name="action" value="buscarMarca">
              <div class="form-group">
                <label for="searchMarca" class="form-label"><strong>Buscar por Marca</strong></label>
                <input type="text" id="searchMarca" name="marca" class="form-control" placeholder="Digite a marca" required>
              </div>
              <button type="submit" class="btn btn-primary w-100">Buscar Marca</button>
            </form>
          </div>
          
          <div class="col-md-4">
            <div class="form-group">
              <label class="form-label"><strong>Listagem Completa</strong></label>
              <div>
                <a href="automoveis?action=listar" class="btn btn-secondary w-100">Listar Todos os Veículos</a>
              </div>
            </div>
          </div>
        </div>
        
        <!-- Mensagem de busca atual -->
        <c:if test="${not empty marcaBuscada}">
          <div class="alert alert-info mt-3">
            Mostrando resultados para: <strong>${marcaBuscada}</strong>
            <a href="automoveis?action=listar" class="btn btn-sm btn-outline-info ml-2">Limpar Filtro</a>
          </div>
        </c:if>
        
        <c:if test="${not empty automovelEncontrado}">
          <div class="alert alert-info mt-3">
            Veículo encontrado: <strong>${automovelEncontrado.marca} - ${automovelEncontrado.ano}</strong>
            <a href="automoveis?action=listar" class="btn btn-sm btn-outline-info ml-2">Ver Todos</a>
          </div>
        </c:if>
      </div>
    </div>

    <!-- Tabela de Veículos -->
    <div class="card">
      <div class="card-header">
        <h4>Veículos Cadastrados (${empty listaAutomoveis ? 0 : listaAutomoveis.size()})</h4>
      </div>
      <div class="card-body">
        <c:choose>
          <c:when test="${not empty listaAutomoveis && !listaAutomoveis.isEmpty()}">
            <div class="table-responsive">
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>Marca</th>
                    <th>Ano</th>
                    <th>Motor</th>
                    <th>Preço</th>
                    <th>Categoria</th>
                    <th>Ações</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="auto" items="${listaAutomoveis}">
                    <tr>
                      <td>${auto.id}</td>
                      <td>${auto.marca}</td>
                      <td>${auto.ano}</td>
                      <td>${auto.motor}</td>
                      <td>R$ ${auto.preco}</td>
                      <td>${auto.categoria}</td>
                      <td>
                        <a href="automoveis?action=editar&id=${auto.id}" class="btn btn-sm btn-warning">Editar</a>
                        <a href="automoveis?action=excluir&id=${auto.id}" class="btn btn-sm btn-danger" 
                           onclick="return confirm('Tem certeza que deseja excluir este veículo?')">Excluir</a>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
          </c:when>
          <c:otherwise>
            <p class="text-muted">Nenhum veículo cadastrado.</p>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </main>
</body>
</html>