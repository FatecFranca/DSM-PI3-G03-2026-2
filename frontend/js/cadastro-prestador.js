console.log('cadastro-prestador.js carregado!');

const form = document.getElementById("formCadastro");

form.addEventListener("submit", function (evento) {
        evento.preventDefault();

        const marcados = document.querySelectorAll('input[name="especialidade"]:checked');
        const especialidades = Array.from(marcados).map(function (caixa) {
                return caixa.value;
        });

        const prestador = {
                nome: document.getElementById("nome").value,
                cpf: somenteNumeros(document.getElementById("cpf").value),
      telefone: somenteNumeros(document.getElementById("telefone").value),
                email: document.getElementById("email").value,
                especialidade: especialidades,
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
        console.log(prestador);

        const mensagem = document.getElementById("mensagem");

        fetch("http://127.0.0.1:8888/prestadores", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(prestador)
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