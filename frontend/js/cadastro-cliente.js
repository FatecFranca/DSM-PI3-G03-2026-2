console.log('cadastro-cliente.js carregado!');
const form = document.getElementById("formCadastro");

form.addEventListener("submit", function (evento) {
   evento.preventDefault();

   const cliente = {
      nome: document.getElementById("nome").value,
      cpf: document.getElementById("cpf").value,
      telefone: document.getElementById("telefone").value,
      email: document.getElementById("email").value,
      enderecos: [
         {
            logradouro: document.getElementById("logradouro").value,
            numero: document.getElementById("numero").value,
            municipio: document.getElementById("municipio").value,
            uf: document.getElementById("uf").value,
            cep: document.getElementById("cep").value
         }
      ]
   };
   console.log(cliente);

   const mensagem = document.getElementById("mensagem");

   fetch("http://localhost:8888/clientes", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(cliente)
   })
      .then(function (resposta) {
         if (resposta.ok) {
            mensagem.textContent = "Cadastro realizado com sucesso!";
            form.reset();
         } else {
            mensagem.textContent = "Erro ao cadastrar. Confira os dados.";
         }
      })
      .catch(function () {
         mensagem.textContent = "Não foi possível conectar ao servidor.";
      });
});